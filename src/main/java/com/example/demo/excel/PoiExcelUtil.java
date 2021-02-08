package com.example.demo.excel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: administer
 * @description: xlsx
 * @date: 2021/2/7  15:34
 **/
public class PoiExcelUtil {

    public static void main(String[] args) {
//        Map<String, String> map = readExcel("src/com/tfswx/res/2.0所有表的分类.xls");
//        System.out.println("map = " + map);

    }


    /**
     * 去读Excel的方法readExcel，该方法的入口参数为一个File对象
     *
     * @param path
     * @return
     */
    public static Map<String, String> readExcel(String path) {

        File file = new File(path);
        Map<String, String> map = new HashMap<>();
        try {
            //.是特殊字符，需要转义！！！！！
            String[] split = file.getName().split("\\.");
            Workbook wb;
            if ("xls".equals(split[split.length - 1])) {
                //文件流对象
                FileInputStream fis = new FileInputStream(file);
                wb = new HSSFWorkbook(fis);
            } else if ("xlsx".equals(split[split.length - 1])) {
                wb = new XSSFWorkbook(file);
            } else {
                return null;
            }
            // 开始解析//读取sheet 0
            Sheet sheet = wb.getSheetAt(0);
            // 第一行是列名，所以不读
            int firstRowIndex = sheet.getFirstRowNum() + 1;
            int lastRowIndex = sheet.getLastRowNum();
            // 遍历行
            for (int rIndex = firstRowIndex; rIndex <= lastRowIndex; rIndex++) {
                Row row = sheet.getRow(rIndex);
                if (row != null) {
                    int firstCellIndex = row.getFirstCellNum();
                    int lastCellIndex = row.getLastCellNum();
                    // 遍历列
                    String s = row.getCell(2).toString();
                    if (s.contains("、")) {
                        String[] strings = s.split("、");
                        for (int i = 0; i < strings.length; i++) {
                            map.put(strings[i], row.getCell(1).toString());
                        }
                    } else {
                        map.put(s, row.getCell(1).toString());
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
        return map;
    }


    /**
     * 写入excel
     *
     * @param list
     * @param filePath
     */
    public static void writeExcel(List<RecordWorld> list, String filePath) {
        // 转为Excel
        File file = new File(filePath);
        if (!file.exists()) {
            file.delete();
        }
        String[] split = file.getName().split("\\.");
        if ("xls".equals(split[split.length - 1])) {
            writeExcelByXls(list, filePath);
        } else if ("xlsx".equals(split[split.length - 1])) {
            writeExcelByXlsx(list, filePath);
        } else {
            return;
        }

    }

    /**
     * 导出为xls
     *
     * @param list
     * @param filePath
     */
    private static void writeExcelByXls(List<RecordWorld> list, String filePath) {
        try (HSSFWorkbook workbook = new HSSFWorkbook()) {
            HSSFSheet sheet = workbook.createSheet("个人工作日志");
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
            sheet.setColumnWidth(4, 256 * 75);
            sheet.setColumnWidth(3, 256 * 10);
            sheet.setColumnWidth(2, 256 * 15);
            sheet.setColumnWidth(1, 256 * 15);
            sheet.setColumnWidth(0, 256 * 10);

            // 表头样式
            CellStyle headStyle = getHeadStyle(workbook);
            // 表体样式
            CellStyle bodyStyle = getBodyStyle(workbook);

            HSSFCell cell = null;
            HSSFRow hssfRow = null;
            hssfRow = sheet.createRow(0);
            hssfRow.setHeight((short) 800);
            cell = hssfRow.createCell(0);
            cell.setCellValue("TYYW2.0数据库变更记录档");
            cell.setCellStyle(headStyle);
            for (int i = 0; i < list.size(); i++) {
                hssfRow = sheet.createRow(i + 1);
                hssfRow.setHeight((short) 500);
                cell = hssfRow.createCell(0);
                cell.setCellValue(list.get(i).getFilepath());
                cell.setCellStyle(bodyStyle);
                cell = hssfRow.createCell(1);
                cell.setCellValue("");
                cell.setCellStyle(bodyStyle);
                cell = hssfRow.createCell(2);
                cell.setCellValue(list.get(i).getTableModel());
                cell.setCellStyle(bodyStyle);
                cell = hssfRow.createCell(3);
                cell.setCellValue("开发需要");
                cell.setCellStyle(bodyStyle);
                cell = hssfRow.createCell(4);
                cell.setCellValue(list.get(i).getMsg());
                cell.setCellStyle(bodyStyle);
            }

//            try (ByteArrayOutputStream out = new ByteArrayOutputStream();
//                 FileOutputStream fileOutputStream = new FileOutputStream(file)
//            ) {
//                workbook.write(out);
//                out.writeTo(fileOutputStream);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
                workbook.write(fileOutputStream);
                fileOutputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 导出为xlsx
     *
     * @param list
     * @param filePath
     */
    private static void writeExcelByXlsx(List<RecordWorld> list, String filePath) {
        // 声明一个工作薄//缓存
        try (SXSSFWorkbook workbook = new SXSSFWorkbook(1000);) {
            workbook.setCompressTempFiles(true);

            SXSSFSheet sheet = workbook.createSheet("个人工作日志");
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
            sheet.setColumnWidth(4, 256 * 75);
            sheet.setColumnWidth(3, 256 * 10);
            sheet.setColumnWidth(2, 256 * 15);
            sheet.setColumnWidth(1, 256 * 15);
            sheet.setColumnWidth(0, 256 * 10);

            // 表头样式
            CellStyle headStyle = getHeadStyle(workbook);
            // 表体样式
            CellStyle bodyStyle = getBodyStyle(workbook);

            // 写入数据
            SXSSFCell cell = null;
            SXSSFRow sxssfRow = null;
            sxssfRow = sheet.createRow(0);
            sxssfRow.setHeight((short) 800);
            cell = sxssfRow.createCell(0);
            cell.setCellValue("TYYW2.0数据库变更记录档");
            cell.setCellStyle(headStyle);
            for (int i = 0; i < list.size(); i++) {
                sxssfRow = sheet.createRow(i + 1);
                sxssfRow.setHeight((short) 500);
                cell = sxssfRow.createCell(0);
                cell.setCellValue(list.get(i).getFilepath());
                cell.setCellStyle(bodyStyle);
                cell = sxssfRow.createCell(1);
                cell.setCellValue("");
                cell.setCellStyle(bodyStyle);
                cell = sxssfRow.createCell(2);
                cell.setCellValue(list.get(i).getTableModel());
                cell.setCellStyle(bodyStyle);
                cell = sxssfRow.createCell(3);
                cell.setCellValue("开发需要");
                cell.setCellStyle(bodyStyle);
                cell = sxssfRow.createCell(4);
                cell.setCellValue(list.get(i).getMsg());
                cell.setCellStyle(bodyStyle);
            }

            // 保存
//            try (ByteArrayOutputStream out = new ByteArrayOutputStream();
//                 FileOutputStream fileOutputStream = new FileOutputStream(new File(filePath))
//            ) {
//                workbook.write(out);
//                out.writeTo(fileOutputStream);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            try (FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
                workbook.write(fileOutputStream);
                fileOutputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置表头的单元格样式
     *
     * @param wb
     * @return
     */
    public static CellStyle getHeadStyle(HSSFWorkbook wb) {
        // 设置样式
        HSSFCellStyle headStyle = wb.createCellStyle();
        // 创建单元格内容显示自动换行
        headStyle.setWrapText(true);
        // 设置单元格居中对齐
        headStyle.setAlignment(HorizontalAlignment.CENTER);
        // 设置单元格垂直居中对齐
        headStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        HSSFFont head_font = wb.createFont();
        //设置字体
        head_font.setFontName(HSSFFont.FONT_ARIAL);
        head_font.setBold(true);
        //设置字体大小
        head_font.setFontHeightInPoints((short) 20);
        headStyle.setFont(head_font);

        // 设置单元格边框为细线条（上下左右）
        headStyle.setBorderLeft(BorderStyle.THIN);
        headStyle.setBorderBottom(BorderStyle.THIN);
        headStyle.setBorderRight(BorderStyle.THIN);
        headStyle.setBorderTop(BorderStyle.THIN);
        headStyle.setFont(head_font);

        return headStyle;
    }

    /**
     * 设置表体的单元格样式
     *
     * @param wb
     * @return
     */
    public static CellStyle getBodyStyle(HSSFWorkbook wb) {
        // 设置样式
        HSSFCellStyle headStyle = wb.createCellStyle();
        // 创建单元格内容显示自动换行
        headStyle.setWrapText(true);
        // 设置单元格居中对齐
        headStyle.setAlignment(HorizontalAlignment.LEFT);
        // 设置单元格垂直居中对齐
        headStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        HSSFFont head_font = wb.createFont();
        //设置字体
        head_font.setFontName(HSSFFont.FONT_ARIAL);
//        head_font.setBold(true);
        //设置字体大小
        head_font.setFontHeightInPoints((short) 10);
        headStyle.setFont(head_font);

        // 设置单元格边框为细线条（上下左右）
        headStyle.setBorderLeft(BorderStyle.THIN);
        headStyle.setBorderBottom(BorderStyle.THIN);
        headStyle.setBorderRight(BorderStyle.THIN);
        headStyle.setBorderTop(BorderStyle.THIN);
        headStyle.setFont(head_font);

        return headStyle;
    }

    /**
     * 设置表头的单元格样式
     *
     * @param workbook
     * @return
     */
    public static CellStyle getHeadStyle(SXSSFWorkbook workbook) {
        // 设置样式
        CellStyle head_style = workbook.createCellStyle();
        head_style.setWrapText(true);
        // 设置单元格居中对齐
        head_style.setAlignment(HorizontalAlignment.CENTER);
        // 设置单元格垂直居中对齐
        head_style.setVerticalAlignment(VerticalAlignment.CENTER);
        // 设置单元格字体样式
        XSSFFont font = (XSSFFont) workbook.createFont();
        font.setBold(true);
        // 设置字体的样式
        font.setFontName("宋体");
        // 设置字体的大小
        font.setFontHeight(20);
        // 将字体填充到表格中去
        head_style.setFont(font);

        // 设置单元格边框为细线条（上下左右）
        head_style.setBorderLeft(BorderStyle.THIN);
        head_style.setBorderBottom(BorderStyle.THIN);
        head_style.setBorderRight(BorderStyle.THIN);
        head_style.setBorderTop(BorderStyle.THIN);
        head_style.setFont(font);

        return head_style;

    }

    /**
     * 设置表体的单元格样式
     *
     * @param workbook
     * @return
     */
    public static CellStyle getBodyStyle(SXSSFWorkbook workbook) {
        // 设置样式
        CellStyle head_style = workbook.createCellStyle();
        head_style.setWrapText(true);
        // 设置单元格居中对齐
        head_style.setAlignment(HorizontalAlignment.LEFT);
        // 设置单元格垂直居中对齐
        head_style.setVerticalAlignment(VerticalAlignment.CENTER);
        // 设置单元格字体样式
        XSSFFont font = (XSSFFont) workbook.createFont();
//        font.setBold(true);
        // 设置字体的样式
        font.setFontName("宋体");
        // 设置字体的大小
        font.setFontHeight(10);
        // 将字体填充到表格中去
        head_style.setFont(font);

        // 设置单元格边框为细线条（上下左右）
        head_style.setBorderLeft(BorderStyle.THIN);
        head_style.setBorderBottom(BorderStyle.THIN);
        head_style.setBorderRight(BorderStyle.THIN);
        head_style.setBorderTop(BorderStyle.THIN);
        head_style.setFont(font);

        return head_style;

    }


}
