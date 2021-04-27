package com.example.demo.minio;

import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

/**
 * @author: fate
 * @description: Minio工具类
 * @date: 2021/4/25  12:35
 **/
@Slf4j
@Component
public class MinIoUtil {

    private static MinioClient minioClient;

    @Autowired
    public void setMinioClient(MinioClient minioClient) {
        MinIoUtil.minioClient = minioClient;
    }

    /**
     * 判断 bucket是否存在
     *
     * @param bucketName: 桶名
     * @return: boolean
     */
    @SneakyThrows(Exception.class)
    public static boolean bucketExists(String bucketName) {
        return minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
    }

    /**
     * 创建 bucket
     *
     * @param bucketName: 桶名
     * @return: void
     */
    @SneakyThrows(Exception.class)
    public static void createBucket(String bucketName) {
        boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        if (!isExist) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }

    /**
     * 获取全部bucket
     *
     * @param :
     * @return: java.util.List<io.minio.messages.Bucket>
     */
    @SneakyThrows(Exception.class)
    public static List<Bucket> getAllBuckets() {
        return minioClient.listBuckets();
    }

    /**
     * 判断桶是否存在
     *
     * @param bucketName
     * @return
     */
    @SneakyThrows(Exception.class)
    public static Boolean checkBucket(String bucketName) {
        List<Bucket> bucketList = minioClient.listBuckets();
        if (bucketList.isEmpty()) {
            return false;
        }
        for (Bucket bucket : bucketList) {
            if (bucketName.equals(bucket.name())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 文件上传
     *
     * @param bucketName: 桶名
     * @param fileName:   文件名
     * @param obj:        内容
     * @return: void
     */
    @SneakyThrows(Exception.class)
    public static void upload(String bucketName, String fileName, byte[] obj) {
        minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(fileName).stream(
                new ByteArrayInputStream(obj), 0, -1)
                .build());
    }

    /**
     * 文件上传
     *
     * @param bucketName: 桶名
     * @param fileName:   文件名
     * @param filePath:   文件路径
     * @return: void
     */
    @SneakyThrows(Exception.class)
    public static Boolean upload(String bucketName, String fileName, String filePath) {
        minioClient.putObject(bucketName, fileName, filePath, null);
        return true;
    }

    /**
     * 文件上传
     *
     * @param bucketName: 桶名
     * @param fileName:   文件名
     * @param stream:     文件流
     * @return: java.lang.String : 文件url地址
     */
    @SneakyThrows(Exception.class)
    public static String upload(String bucketName, String fileName, InputStream stream) {
        minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(fileName).stream(
                stream, -1, 10485760)
                .build());
        return getFileUrl(bucketName, fileName);
    }


    /**
     * 删除文件
     *
     * @param bucketName: 桶名
     * @param fileName:   文件名
     * @return: void
     */
    @SneakyThrows(Exception.class)
    public static void deleteFile(String bucketName, String fileName) {
        minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(fileName).build());
    }


    /**
     * 获取minio文件的下载地址
     *
     * @param bucketName: 桶名
     * @param fileName:   文件名
     * @return: java.lang.String
     * @date : 2020/8/16 22:07
     */
    @SneakyThrows(Exception.class)
    public static String getFileUrl(String bucketName, String fileName) {
        return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                .method(Method.GET)
                .bucket(bucketName)
                .object(fileName)
                .build());
    }

    /**
     * 获取桶中的所有文件
     *
     * @param bucketName: 桶名
     * @return
     */
    @SneakyThrows(Exception.class)
    public static Iterable<Result<Item>> getAllObjects(String bucketName) {
        return minioClient.listObjects(
                ListObjectsArgs.builder().bucket(bucketName).build());
    }

    /**
     * 判断桶中文件是否存在
     *
     * @param bucketName
     * @param fileName
     * @return
     */
    @SneakyThrows(Exception.class)
    public static Boolean checkObject(String bucketName, String fileName) {
        if (!checkBucket(bucketName)) {
            return false;
        }
        Iterable<Result<Item>> allObjects = getAllObjects(bucketName);
        Iterator<Result<Item>> iterator = allObjects.iterator();
        while (iterator.hasNext()) {
            Result<Item> next = iterator.next();
            if (fileName.equals(next.get().objectName())) {
                return true;
            }
        }
        return false;
    }

    @SneakyThrows(Exception.class)
    public static void update(String bucketName, String obj, String filename) {
        minioClient.uploadObject(
                UploadObjectArgs.builder()
                        .bucket(bucketName).object(obj).filename(filename).build());
    }


}