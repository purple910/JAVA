package com.example.demo.file;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author: administer
 * @description: 文件融合
 * @date: 2021/3/4  9:00
 **/
public class FileUtil {

    private static final Pattern REG_SQL = Pattern.compile("((\\<insert|\\<update|\\<select)([\\s\\S]*?)(\\<\\/insert\\>|\\<\\/update\\>|\\<\\/select\\>))");
    private static final Pattern REG_SQL_ID = Pattern.compile("(id\\=\\\"?)([\\s\\S]*?)(\\\")([\\s\\S]*?)(\\<\\/insert\\>|\\<\\/update\\>|\\<\\/select\\>)");
    private static final Pattern REG_IMPORT = Pattern.compile("(import([\\s\\S]*?(;)))");
    private static final Pattern REG_AUTHOR = Pattern.compile("(/\\*\\*([\\s\\S]*?)\\{)");
    private static final Pattern REG_INTERFACE_METHOD = Pattern.compile("(/\\*\\*)([\\s\\S]*?)(;)");
    private static final Pattern REG_INTERFACE_METHOD_NAME = Pattern.compile("(/\\*)([\\s\\S]*?)(\\*/)([\\s\\S]*?)(\\()([\\s\\S]*?)(;)");

    private FileUtil() {

    }

    /**
     * 读取文件内容
     *
     * @param file
     * @return
     */
    public static String read(File file) {
        BufferedReader reader = null;
        StringBuilder sbf = new StringBuilder();
        // 获取文件的绝对路径
        String absolutePath = file.getAbsolutePath();
        // 获取文件
        File sqlFile = new File(absolutePath);
        try (InputStreamReader isr = new InputStreamReader(new FileInputStream(sqlFile), StandardCharsets.UTF_8);) {
            reader = new BufferedReader(isr);
            String tempStr;
            sbf.append("\n");
            while ((tempStr = reader.readLine()) != null) {
                sbf.append(tempStr + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return sbf.toString();
    }

    /**
     * 匹配两个文件的差异，主要输出新文件与旧文件的差异，并输出
     * newFile 比 oldFile多的字段行
     *
     * @param oldFile 旧的文件
     * @param newFile 当前新文件
     * @throws IOException 异常
     */
    public static List<String> compareTwoFile(String oldFile, String newFile) throws IOException {
        List<String> list1 = Files.readAllLines(Paths.get(oldFile));
        List<String> list2 = Files.readAllLines(Paths.get(newFile));

        List<String> finalList = list2.stream().filter(line ->
                list1.stream().filter(line2 -> line2.equals(line)).count() == 0
        ).collect(Collectors.toList());
        return finalList;
    }

    /**
     * 判断str1中包含str2的个数
     *
     * @param str1
     * @param str2
     * @return counter
     */
    public static int countStr(String str1, String str2) {
        int counter = 0;
        if (str1.indexOf(str2) == -1) {
            return counter;
        } else if (str1.indexOf(str2) != -1) {
            counter++;
            countStr(str1.substring(str1.indexOf(str2) +
                    str2.length()), str2);
            return counter;
        }
        return counter;
    }

    /**
     * 以a文件为主体,将b里面比a多的复制到a里面
     *
     * @param a
     * @param b
     * @throws IOException
     */
    public static void writeJava(String a, String b, String path) throws IOException {
        // a 为新生成的文件
        List<String> list = compareTwoFile(a, b);
        List<String> listb = Files.readAllLines(Paths.get(b));
        List<String> lista = Files.readAllLines(Paths.get(a));

        List<String> sources = new ArrayList<>(Arrays.asList(new String[lista.size()]));
        Collections.copy(sources, lista);

        if (!list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                String str = list.get(i);
                int index = listb.indexOf(str);
                if (sources.size() - 1 < index) {
                    sources.add(str);
                } else {
                    if (str.contains("@author") || str.contains("@description") || str.contains("@date")) {
                        sources.set(index, str);
                    } else if (str.contains("return")) {
                        while (true) {
                            if ("".equals(sources.get(index).trim())) {
                                sources.remove(index);
                            }
                            if (str.contains("@return")) {
                                sources.add(index, str);
                                break;
                            } else if (sources.get(index).contains("return")) {
                                sources.set(index, str);
                                break;
                            }
                        }
                    } else {
                        String s = sources.get(index).trim();
                        if ("/**".equals(str.trim())) {
                            sources.add(index, "\n");
                            sources.add(index, "\n");
                            sources.add((index), str);
                        } else if (!"".equals(s)) {
                            if (str.contains(s)) {
                                sources.set(index, str);
                            } else {
                                sources.add(index, str);
                            }
                        } else {
                            sources.add(index, str);
                        }
                    }
                }
            }

            Files.write(Paths.get(path), sources);
        }

    }

    /**
     * 以a文件为主体,将b里面比a多的复制到a里面
     *
     * @param a
     * @param b
     * @throws IOException
     */
    public static void writeXml(String a, String b, String path) throws IOException {
        List<String> listb = Files.readAllLines(Paths.get(b));
        String strb = StringUtils.join(listb, "\n");
        List<String> lista = Files.readAllLines(Paths.get(a));
        String temp = StringUtils.join(lista, "\n");

        List<String> aa = new ArrayList<>();
        List<String> bb = new ArrayList<>();

        Matcher matcher = REG_SQL.matcher(temp);
        while (matcher.find()) {
            String group = matcher.group();
            aa.add(group);
        }
        matcher = REG_SQL.matcher(strb);
        while (matcher.find()) {
            String group = matcher.group();
            bb.add(group);
        }

        List<String> finalList = bb.stream().filter(line ->
                aa.stream().filter(line2 -> line2.equals(line)).count() == 0
        ).collect(Collectors.toList());

        for (int i = 0; i < finalList.size(); i++) {
            Matcher m = REG_SQL_ID.matcher(finalList.get(i));
            if (m.find()) {
                String group = m.group(2);
                String s = m.group();
                Matcher ma = Pattern.compile("(id\\=\\\"?" + group + "\\\")([\\s\\S]*?)(\\<\\/insert\\>|\\<\\/update\\>|\\<\\/select\\>)").matcher(temp);
                if (ma.find()) {
                    temp = temp.replace(ma.group(), s);
                } else {
                    temp = temp.replace("</mapper>", "\t" + finalList.get(i) + "\n</mapper>");
                }
            }
        }
        String[] split = temp.split("\n");
        ArrayList<String> sources = new ArrayList<>(Arrays.asList(split));


        Files.write(Paths.get(path), sources);
    }

    /**
     * 将接口切分开
     *
     * @param path
     * @return
     * @throws IOException
     */
    public static Map<String, List<String>> getInterface(String path) throws IOException {
        List<String> lista = Files.readAllLines(Paths.get(path));
        String str = StringUtils.join(lista, "\n");
        Map<String, List<String>> map = new HashMap<>(5);

        map.put("package", Collections.singletonList(lista.get(0)));

        Matcher matcher = REG_IMPORT.matcher(str);
        List<String> importa = new ArrayList<>();
        while (matcher.find()) {
            String group = matcher.group();
            importa.add(group);
        }
        map.put("import", importa);

        matcher = REG_AUTHOR.matcher(str);
        while (matcher.find()) {
            String group = matcher.group();
            map.put("note", Collections.singletonList(group));
            str = str.replace(group, "");
        }

        matcher = REG_INTERFACE_METHOD.matcher(str);
        List<String> method = new ArrayList<>();
        while (matcher.find()) {
            String group = matcher.group();
            method.add(group);
        }
        map.put("method", method);

        return map;
    }

    /**
     * 以a文件为主体,将b里面比a多的复制到a里面
     *
     * @param a
     * @param b
     * @throws IOException
     */
    public static void writeInterface(String a, String b, String path) throws IOException {
        Map<String, List<String>> amap = getInterface(a);
        Map<String, List<String>> bmap = getInterface(b);

        List<String> aMethod = amap.get("method");
        List<String> bMethod = bmap.get("method");

        List<String> m = new ArrayList<>();
        List<String> ma = new ArrayList<>();
        for (int i = 0; i < aMethod.size(); i++) {
            Matcher matcher = REG_INTERFACE_METHOD_NAME.matcher(aMethod.get(i));
            if (matcher.find()) {
                String s = matcher.group(4).trim().split("([\\s]+)")[1];
                m.add(s);
                ma.add("\t" + matcher.group() + "\n");
            }
        }
        for (int i = 0; i < bMethod.size(); i++) {
            Matcher matcher = REG_INTERFACE_METHOD_NAME.matcher(bMethod.get(i));
            if (matcher.find()) {
                String s = matcher.group(4).trim().split("([\\s]+)")[1];
                if (m.contains(s)) {
                    int index = m.indexOf(s);
                    ma.set(index, "\t" + matcher.group() + "\n");
                } else {
                    ma.add("\t" + matcher.group() + "\n");
                }
            }
        }

        StringBuilder builder = new StringBuilder();
        builder.append(amap.get("package").get(0) + "\n\n");
        Set<String> set = new HashSet<>();
        List<String> aImport = amap.get("import");
        List<String> bImport = bmap.get("import");
        set.addAll(aImport);
        set.addAll(bImport);
        String im = StringUtils.join(set, "\n");
        builder.append(im + "\n\n");
        builder.append(bmap.get("note").get(0) + "\n\n");
        builder.append(StringUtils.join(ma, "\n"));

        builder.append("\n}");

        Files.write(Paths.get(path), Collections.singletonList(builder.toString()));
    }

    /**
     * 比较两个字符串相识度
     *
     * @param str
     * @param target
     * @return
     */
    public static float getSimilarityRatio(String str, String target) {

        // 矩阵
        int[][] d;
        int n = str.length();
        int m = target.length();
        // 遍历str的
        int i;
        // 遍历target的
        int j;
        // str的
        char ch1;
        // target的
        char ch2;
        // 记录相同字符,在某个矩阵位置值的增量,不是0就是1
        int temp;
        if (n == 0 || m == 0) {
            return 0;
        }
        d = new int[n + 1][m + 1];
        //  // 初始化第一列
        for (i = 0; i <= n; i++) {
            d[i][0] = i;
        }
        // 初始化第一行
        for (j = 0; j <= m; j++) {
            d[0][j] = j;
        }
        // 遍历str
        for (i = 1; i <= n; i++) {
            ch1 = str.charAt(i - 1);
            // 去匹配target
            for (j = 1; j <= m; j++) {
                ch2 = target.charAt(j - 1);
                if (ch1 == ch2 || ch1 == ch2 + 32 || ch1 + 32 == ch2) {
                    temp = 0;
                } else {
                    temp = 1;
                }
                // 左边+1,上边+1, 左上角+temp取最小
                d[i][j] = Math.min(Math.min(d[i - 1][j] + 1, d[i][j - 1] + 1), d[i - 1][j - 1] + temp);
            }
        }

        return (1 - (float) d[n][m] / Math.max(str.length(), target.length())) * 100F;
    }


}
