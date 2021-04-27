package com.example.demo.minio;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

/**
 * @author: administer
 * @description: http
 * @date: 2021/3/24  10:02
 **/
@Slf4j
public class HttpUtil {

    /**
     * 处理http请求  requestUrl为请求地址  requestMethod请求方式，值为"GET"或"POST"
     *
     * @param requestUrl    为请求地址
     * @param requestMethod 请求方式，值为"GET"或"POST"
     * @param outputStr     参数
     * @return string
     */
    public static String httpRequest(String requestUrl, String requestMethod, String outputStr) {
        StringBuilder buffer = new StringBuilder();
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod(requestMethod);
            conn.connect();
            //往服务器端写内容 也就是发起http请求需要带的参数
            if (null != outputStr) {
                OutputStream os = conn.getOutputStream();
                os.write(outputStr.getBytes(StandardCharsets.UTF_8));
                os.close();
            }

            //读取服务器端返回的内容
            InputStream is = conn.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);

            String line = null;
            while ((line = br.readLine()) != null) {
                buffer.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

}