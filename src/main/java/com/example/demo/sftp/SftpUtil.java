package com.example.demo.sftp;

import com.jcraft.jsch.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Arrays;

/**
 * @author: fate
 * @description:
 * @date: 2021/4/26  11:00
 **/
@Slf4j
@Component
public class SftpUtil {


    private static SftpProp config;

    @Autowired
    public void setConfig(SftpProp config) {
        SftpUtil.config = config;
    }

    /**
     * 设置第一次登陆的时候提示，可选值：(ask | yes | no)
     */
    private static final String SESSION_CONFIG_STRICT_HOST_KEY_CHECKING = "StrictHostKeyChecking";

    /**
     * 创建SFTP连接
     *
     * @return
     * @throws Exception
     */
    private static ChannelSftp createSftp() throws Exception {
        JSch jsch = new JSch();
        log.info("Try to connect sftp[" + config.getUsername() + "@" + config.getHostName() + "], use password[" + config.getPassword() + "]");

        Session session = createSession(jsch, config.getHostName(), config.getUsername(), config.getPort());
        session.setPassword(config.getPassword());
        session.connect(config.getSessionConnectTimeout());

        log.info("Session connected to {}.", config.getHostName());

        Channel channel = session.openChannel(config.getServiceType());
        channel.connect();

        log.info("Channel created to {}.", config.getHostName());

        return (ChannelSftp) channel;
    }


    /**
     * 创建session
     *
     * @param jsch
     * @param host
     * @param username
     * @param port
     * @return
     * @throws Exception
     */
    private static Session createSession(JSch jsch, String host, String username, Integer port) throws Exception {
        Session session = null;

        if (port <= 0) {
            session = jsch.getSession(username, host);
        } else {
            session = jsch.getSession(username, host, port);
        }

        if (session == null) {
            throw new Exception(host + " session is null");
        }

        session.setConfig(SESSION_CONFIG_STRICT_HOST_KEY_CHECKING, config.getSessionStrictHostKeyChecking());
        return session;
    }

    /**
     * 关闭连接
     *
     * @param sftp
     */
    private static void disconnect(ChannelSftp sftp) {
        try {
            if (sftp != null) {
                if (sftp.isConnected()) {
                    sftp.disconnect();
                } else if (sftp.isClosed()) {
                    log.info("sftp is closed already");
                }
                if (null != sftp.getSession()) {
                    sftp.getSession().disconnect();
                }
            }
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传文件流
     *
     * @param targetPath
     * @param inputStream
     * @return
     * @throws Exception
     */
    public static boolean uploadFile(String targetPath, InputStream inputStream) throws Exception {
        ChannelSftp sftp = createSftp();
        try {
            sftp.cd(config.getDirectory());
            log.info("Change path to {}", config.getDirectory());

            int index = targetPath.lastIndexOf("/");
            String fileDir = targetPath.substring(0, index);
            String fileName = targetPath.substring(index + 1);
            boolean dirs = createDirs(fileDir, sftp);
            if (!dirs) {
                log.error("Remote path error. path:{}", targetPath);
                throw new Exception("Upload File failure");
            }
            sftp.put(inputStream, fileName);
            return true;
        } catch (Exception e) {
            log.error("Upload file failure. TargetPath: {}", targetPath, e);
            throw new Exception("Upload File failure");
        } finally {
            disconnect(sftp);
        }
    }

    /**
     * 创建目录
     *
     * @param dirPath
     * @param sftp
     * @return
     */
    private static boolean createDirs(String dirPath, ChannelSftp sftp) {
        if (dirPath != null && !dirPath.isEmpty()
                && sftp != null) {
            String[] dirs = Arrays.stream(dirPath.split("/"))
                    .filter(StringUtils::isNotBlank)
                    .toArray(String[]::new);

            for (String dir : dirs) {
                try {
                    sftp.cd(dir);
                    log.info("Change directory {}", dir);
                } catch (Exception e) {
                    try {
                        sftp.mkdir(dir);
                        log.info("Create directory {}", dir);
                    } catch (SftpException e1) {
                        log.error("Create directory failure, directory:{}", dir, e1);
                        e1.printStackTrace();
                    }
                    try {
                        sftp.cd(dir);
                        log.info("Change directory {}", dir);
                    } catch (SftpException e1) {
                        log.error("Change directory failure, directory:{}", dir, e1);
                        e1.printStackTrace();
                    }
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 上传文件
     *
     * @param targetPath
     * @param file
     * @return
     * @throws Exception
     */
    public static boolean uploadFile(String targetPath, File file) throws Exception {
        return uploadFile(targetPath, new FileInputStream(file));
    }

    /**
     * 上传文件
     *
     * @param targetPath
     * @param json
     * @return
     * @throws Exception
     */
    public static boolean uploadFile(String targetPath, String json) throws Exception {
        ByteArrayInputStream stream = new ByteArrayInputStream(json.getBytes());
        return uploadFile(targetPath, stream);
    }

    /**
     * 下载文件
     *
     * @param targetPath
     * @return
     * @throws Exception
     */
    public static File downloadFile(String targetPath) throws Exception {
        ChannelSftp sftp = createSftp();
        OutputStream outputStream = null;
        try {
            sftp.cd(config.getDirectory());
            log.info("Change path to {}", config.getDirectory());

            File file = new File(targetPath.substring(targetPath.lastIndexOf("/") + 1));

            outputStream = new FileOutputStream(file);
            sftp.get(targetPath, outputStream);
            log.info("Download file success. TargetPath: {}", targetPath);
            return file;
        } catch (Exception e) {
            log.error("Download file failure. TargetPath: {}", targetPath, e);
            throw new Exception("Download File failure");
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
            disconnect(sftp);
        }
    }

    /**
     * 删除文件
     *
     * @param targetPath
     * @return
     * @throws Exception
     */
    public static boolean deleteFile(String targetPath) throws Exception {
        ChannelSftp sftp = null;
        try {
            sftp = createSftp();
            sftp.cd(config.getDirectory());
            sftp.rm(targetPath);
            return true;
        } catch (Exception e) {
            log.error("Delete file failure. TargetPath: {}", targetPath, e);
            throw new Exception("Delete File failure");
        } finally {
            disconnect(sftp);
        }
    }

    /**
     * 判断文件是否存在
     *
     * @param targetPath
     * @return
     * @throws Exception
     */
    public static boolean checkFile(String targetPath) {
        ChannelSftp sftp = null;
        try {
            sftp = createSftp();
            sftp.cd(config.getDirectory());
            SftpATTRS attrs = sftp.lstat(targetPath);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            disconnect(sftp);
        }
    }

    public static String readFileContent(File file) {
        StringBuilder sbf = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file));) {

            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                sbf.append(tempStr);
            }
            reader.close();
            return sbf.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sbf.toString();
    }


}