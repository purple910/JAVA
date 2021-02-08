package com.example.demo.excel;

import jxl.Sheet;
import jxl.Workbook;
import jxl.format.UnderlineStyle;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author: 杨登柳
 * @description: 读写Excel
 * @date: 2021/2/7  9:18
 **/
public class ExcelUtil {

    public static void main(String[] args) {
        ExcelUtil obj = new ExcelUtil();
        // 此处为我创建Excel路径：E:/zhanhj/studysrc/jxl下
        Map excelList = readExcel("src/com/tfswx/res/2.0所有表的分类.xls");
        System.out.println("list中的数据打印出来");
        System.out.println("excelList = " + excelList);
        System.out.println("excelList = " + excelList.size());

    }

    /**
     * 去读Excel的方法readExcel，该方法的入口参数为一个File对象
     *
     * @param path
     * @return
     */
    public static Map<String, String> readExcel(String path) {
        Map<String, String> map = new HashMap<>();
        try {
//            String currentJarPath = null;
//            try {
//                // 获取当前Jar文件名，并对其解码，防止出现中文乱码
//                currentJarPath = URLDecoder.decode(com.example.demo.excel.ExcelUtil.class.getProtectionDomain().getCodeSource().getLocation().getFile(), "UTF-8");
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }
//            JarFile currentJar = null;
//            try {
//                currentJar = new JarFile(currentJarPath);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            // 这里写上配置文件所在的包路径
//            JarEntry dbEntry = currentJar.getJarEntry(path);
//            // 创建输入流，读取Excel
//            InputStream is = null;
//            try {
//                is = currentJar.getInputStream(dbEntry);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

            // 创建输入流，读取Excel
            File file = new File(path);
            InputStream is = new FileInputStream(file.getAbsolutePath());
            // jxl提供的Workbook类
            Workbook wb = Workbook.getWorkbook(is);
            // Excel的页签数量
            int sheet_size = wb.getNumberOfSheets();
            for (int index = 0; index < sheet_size; index++) {
                List<List> outerList = new ArrayList<List>();
                // 每个页签创建一个Sheet对象
                Sheet sheet = wb.getSheet(index);
                // sheet.getRows()返回该页的总行数
                String s = "";
                for (int i = 1; i < sheet.getRows(); i++) {
                    if ((s = sheet.getCell(2, i).getContents()).isEmpty()) {
                        continue;
                    }
                    if (s.contains("、")) {
                        String[] split = s.split("、");
                        for (int j = 0; j < split.length; j++) {
                            map.put(split[j], sheet.getCell(1, i).getContents());
                        }
                    } else {
                        map.put(s, sheet.getCell(1, i).getContents());
                    }
                }
                return map;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 写入excel
     *
     * @param list
     * @param filePath
     */
    public static void writeExcel(List<RecordWorld> list, String filePath) {
        //开始写入excel,创建模型文件头
//        String[] titleA = {"文件夹", "空", "模型", "缘由", "信息"};
        //创建Excel文件，B库CD表文件
        File fileA = new File(filePath);
        if (fileA.exists()) {
            //如果文件存在就删除
            fileA.delete();
        }
        try {
            fileA.createNewFile();
            //创建工作簿
            WritableWorkbook workbookA = Workbook.createWorkbook(fileA);
            //创建sheet
            WritableSheet sheetA = workbookA.createSheet("sheet1", 0);
            sheetA.mergeCells(0, 0, 4, 0);
            // 行
            sheetA.setRowView(0, 800);
            // 列
            sheetA.setColumnView(4, 100);
            // 定义格式 字体 下划线 斜体 粗体 颜色
            WritableFont wf_title = new WritableFont(WritableFont.ARIAL, 22,
                    WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
                    jxl.format.Colour.BLACK);
            // 单元格定义
            WritableCellFormat wcf_title = new WritableCellFormat(wf_title);
            // 设置单元格的背景颜色
            wcf_title.setBackground(jxl.format.Colour.WHITE);
            // 设置对齐方式
            wcf_title.setAlignment(jxl.format.Alignment.CENTRE);
            //设置边框
            wcf_title.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN, jxl.format.Colour.BLACK);

            Label labelA = null;
            //设置列名 第一行
            // 列, 行, 数据
            labelA = new Label(0, 0, "TYYW2.0数据库变更记录档", wcf_title);
            sheetA.addCell(labelA);

            //获取数据源
            for (int i = 1; i < list.size(); i++) {
                labelA = new Label(0, i, list.get(i - 1).getFilepath());
                sheetA.addCell(labelA);
                labelA = new Label(1, i, "");
                sheetA.addCell(labelA);
                labelA = new Label(2, i, list.get(i - 1).getTableModel());
                sheetA.addCell(labelA);
                labelA = new Label(3, i, "开发需要");
                sheetA.addCell(labelA);
                labelA = new Label(4, i, list.get(i - 1).getMsg());
                sheetA.addCell(labelA);
            }
            workbookA.write();    //写入数据
            workbookA.close();  //关闭连接
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
