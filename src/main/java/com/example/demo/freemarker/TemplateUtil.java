package com.example.demo.freemarker;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/***
 * @author: fate
 * @description: 模板 https://www.cnblogs.com/hmit/p/11608241.html
 * @date: 2021/2/8  12:28
 **/
public class TemplateUtil {

    private TemplateUtil() {

    }

    /**
     * 创建文件
     *
     * @param source       传入的资源
     * @param templateName 模板名
     * @param classType    文件类型
     * @return
     */
    public static boolean writeTemplate(BaseTemplate source, String templateName, String classType) {
        File docFile;
        Template template;
        try {
            // step1 创建freeMarker配置实例
            Configuration configuration = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
            configuration.setEncoding(Locale.getDefault(), "UTF-8");
            // 第一种：基于类路径，HttpWeb包下的framemaker.ftl文件
            configuration.setTemplateLoader(new ClassTemplateLoader(TemplateUtil.class, "/template/"));

            // step4 加载模版文件
            template = configuration.getTemplate(templateName, "UTF-8");
            // step5 生成数据
            String path = "";
            String entityName = source.getPack();

            File file = new File("src/main/java/com/example/demo/freemarker/" + entityName);
            if (!file.exists()) {
                file.mkdirs();
            }
            path = "src/main/java/com/example/demo/freemarker/" + entityName;

            docFile = new File(path + File.separator + source.getClassName() + "." + classType);
            if (docFile.exists()) {
                Files.delete(docFile.toPath());
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        try (Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile), "UTF-8"))) {
            // step6 输出文件
            template.process(source, out);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static Pattern linePattern = Pattern.compile("_(\\w)");

    /**
     * 下划线转驼峰
     *
     * @param str
     * @return string
     */
    public static String lineToHump(String str) {
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        String name = sb.toString();
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        return name;
    }


    /**
     * 获得当前日期 yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getCurrentTime() {
        // 小写的hh取得12小时，大写的HH取的是24小时
        final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        return df.format(date);
    }



    /**
     * 首字母大写
     *
     * @param name
     * @return
     */
    public static String captureNameUp(String name) {
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        return name;
    }

    /**
     * 首字母下写
     *
     * @param name
     * @return
     */
    public static String captureNameDown(String name) {
        name = name.substring(0, 1).toLowerCase() + name.substring(1);
        return name;
    }


}