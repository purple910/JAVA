package com.example.demo.file;

import net.sf.sevenzipjbinding.ExtractOperationResult;
import net.sf.sevenzipjbinding.IInArchive;
import net.sf.sevenzipjbinding.ISequentialOutStream;
import net.sf.sevenzipjbinding.SevenZip;
import net.sf.sevenzipjbinding.SevenZipException;
import net.sf.sevenzipjbinding.impl.RandomAccessFileInStream;
import net.sf.sevenzipjbinding.simple.ISimpleInArchive;
import net.sf.sevenzipjbinding.simple.ISimpleInArchiveItem;
import org.assertj.core.util.Throwables;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;


/**
 * @program: djapp-master
 * @description: //直接读取exe文件的版本信息
 * @author: Mr.Wang
 * @create: 2020-03-13 10:09
 **/
public class ZipUtils {

    private static Logger logger = Logger.getAnonymousLogger();


    /**
     * 获取.exe文件的版本信息
     *
     * @param file
     * @return
     */
    public static String getVersion(File file) {
        byte[] buffer;
        String str;
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(file, "r");
            buffer = new byte[64];
            raf.read(buffer);
            str = "" + (char) buffer[0] + (char) buffer[1];
            if (!"MZ".equals(str)) {
                return null;
            }

            int peOffset = unpack(new byte[]{buffer[60], buffer[61], buffer[62], buffer[63]});
            if (peOffset < 64) {
                return null;
            }

            raf.seek(peOffset);
            buffer = new byte[24];
            raf.read(buffer);
            str = "" + (char) buffer[0] + (char) buffer[1];
            if (!"PE".equals(str)) {
                return null;
            }
            int machine = unpack(new byte[]{buffer[4], buffer[5]});
            if (machine != 332) {
                return null;
            }

            int noSections = unpack(new byte[]{buffer[6], buffer[7]});
            int optHdrSize = unpack(new byte[]{buffer[20], buffer[21]});
            raf.seek(raf.getFilePointer() + optHdrSize);
            boolean resFound = false;
            for (int i = 0; i < noSections; i++) {
                buffer = new byte[40];
                raf.read(buffer);
                str = "" + (char) buffer[0] + (char) buffer[1] +
                        (char) buffer[2] + (char) buffer[3] + (char) buffer[4];
                if (".rsrc".equals(str)) {
                    resFound = true;
                    break;
                }
            }
            if (!resFound) {
                return null;
            }

            int infoVirt = unpack(new byte[]{buffer[12], buffer[13], buffer[14], buffer[15]});
            int infoSize = unpack(new byte[]{buffer[16], buffer[17], buffer[18], buffer[19]});
            int infoOff = unpack(new byte[]{buffer[20], buffer[21], buffer[22], buffer[23]});
            raf.seek(infoOff);
            buffer = new byte[infoSize];
            raf.read(buffer);
            int numDirs = unpack(new byte[]{buffer[14], buffer[15]});
            boolean infoFound = false;
            int subOff = 0;
            for (int i = 0; i < numDirs; i++) {
                int type = unpack(new byte[]{buffer[i * 8 + 16], buffer[i * 8 + 17], buffer[i * 8 + 18], buffer[i * 8 + 19]});
                if (type == 16) {
                    infoFound = true;
                    subOff = unpack(new byte[]{buffer[i * 8 + 20], buffer[i * 8 + 21], buffer[i * 8 + 22], buffer[i * 8 + 23]});
                    break;
                }
            }
            if (!infoFound) {
                return null;
            }

            subOff = subOff & 0x7fffffff;
            infoOff = unpack(new byte[]{buffer[subOff + 20], buffer[subOff + 21], buffer[subOff + 22], buffer[subOff + 23]});
            infoOff = infoOff & 0x7fffffff;
            infoOff = unpack(new byte[]{buffer[infoOff + 20], buffer[infoOff + 21], buffer[infoOff + 22], buffer[infoOff + 23]});
            int dataOff = unpack(new byte[]{buffer[infoOff], buffer[infoOff + 1], buffer[infoOff + 2], buffer[infoOff + 3]});
            dataOff = dataOff - infoVirt;

            int version1 = unpack(new byte[]{buffer[dataOff + 48], buffer[dataOff + 48 + 1]});
            int version2 = unpack(new byte[]{buffer[dataOff + 48 + 2], buffer[dataOff + 48 + 3]});
            int version4 = unpack(new byte[]{buffer[dataOff + 48 + 6], buffer[dataOff + 48 + 7]});
            return version2 + "." + version1 + "." + version4;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "文件{" + file.getAbsolutePath() + "}版本号解析失败！错误信息：{" + Throwables.getRootCause(e) + "}");
            return null;
        } finally {
            if (raf != null) {
                try {
                    raf.close();
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "关闭随机访问文件失败！" + e);
                }
            }
        }
    }

    public static int unpack(byte[] b) {
        int num = 0;
        for (int i = 0; i < b.length; i++) {
            num = 256 * num + (b[b.length - 1 - i] & 0xff);
        }
        return num;
    }

    /**
     * 从exe file文件中获取icon
     *
     * @param file
     * @return
     */
    public static Image getImgFromFile(File file) {
        return ((ImageIcon) FileSystemView.getFileSystemView()
                .getSystemIcon(file))
                .getImage();
    }


    /**
     * <p>Discription:[将解压文件解压到指定目录下]</p>
     *
     * @param -支持压缩格式: 7Z，ZIP，TAR，RAR，LZMA，ISO，GZIP，BZIP2，CPIO，Z，ARJ，LZH，CAB，CHM，NSIS，DEB，RPM，UDF，WIM
     * @throws SevenZipException
     * @throws IOException
     * @update:[日期YYYY-MM-DD] [更改人姓名][变更描述]
     * @return
     */
    public static String analysisMsg(String file) throws SevenZipException, IOException {
        IInArchive inArchive = null;
        RandomAccessFile randomAccessFile = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            randomAccessFile = new RandomAccessFile(new File(file), "r");
            inArchive = SevenZip.openInArchive(null, new RandomAccessFileInStream(randomAccessFile));
            ISimpleInArchive simpleInArchive = inArchive.getSimpleInterface();
            for (final ISimpleInArchiveItem item : simpleInArchive.getArchiveItems()) {
                if (!item.isFolder()) {
                    ExtractOperationResult result;
                    String path = item.getPath();

                    if (Pattern.matches("manifest\\.json", path)) {
                        result = item.extractSlow(new ISequentialOutStream() {
                            @Override
                            public int write(byte[] data) throws SevenZipException {
                                //写入指定文件
                                stringBuilder.append(new String(data));
                                // Return amount of consumed data
                                return data.length;
                            }
                        });
                    }
                }
            }
        } finally {
            if (inArchive != null) {
                inArchive.close();
            }
            if (randomAccessFile != null) {
                randomAccessFile.close();
            }
        }
        return stringBuilder.toString();
    }
}
 
