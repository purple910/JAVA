package com.example.demo.compression;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtobufIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.example.demo.bean.CosmosInitializationData;
import com.example.demo.bean.CosmosY;
import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.GZIPOutputStream;

/**
 * @author: fate
 * @description:
 * @date: 2021/4/25  9:02
 **/
public class Test {

    public static void main(String[] args) {

//        String read = Files.read("test.txt");
//        System.out.println(read.length());
//        String g = gzip.compress(read);
//        System.out.println("g.length() = " + g.length());
//        String b = base.compress(read);
//        System.out.println("b.length() = " + b.length());

        CosmosY cosmosY = CosmosInitializationData.getCosmosY();
        byte[] bytes = serialize(cosmosY, CosmosY.class);
        System.out.println("bytes.length = " + bytes.length);
        System.out.println(bytes);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = null;
        try {
            gzip = new GZIPOutputStream(out);
            gzip.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (gzip != null) {
                try {
                    gzip.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String gg = new BASE64Encoder().encode(out.toByteArray());
        System.out.println("g.length() = " + gg.length());
        System.out.println(gg);
//
        String s = com.example.demo.compression.gzip.uncompress(gg);
        System.out.println("s = " + s);
        System.out.println("s.length() = " + s.length());

//        String s = Base64.encodeBase64String(bytes);
//        System.out.println("s = " + s);
//        System.out.println("s.length() = " + s.length());
//        String s1 = Arrays.toString(Base64.decodeBase64(s));
//        System.out.println("s1 = " + s1);
//        System.out.println("s1.length() = " + s1.length());


//        CosmosY deSerialize = deSerialize(s1.getBytes(), CosmosY.class);
//        System.out.println("deSerialize = " + deSerialize);

    }

    public static <T> byte[] serialize(T t, Class<T> clazz) {
        return ProtobufIOUtil.toByteArray(t, RuntimeSchema.createFrom(clazz),
                LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
    }

    public static <T> T deSerialize(byte[] data, Class<T> clazz) {
        RuntimeSchema<T> runtimeSchema = RuntimeSchema.createFrom(clazz);
        T t = runtimeSchema.newMessage();
        ProtobufIOUtil.mergeFrom(data, t, runtimeSchema);
        return t;
    }
}
