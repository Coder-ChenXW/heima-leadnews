package com.heima.minio.test;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MinIOTest {

    public static void main(String[] args) {

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream("D:\\list.html");
            //获取minio的连接信息 创建一个minio的客户端
            MinioClient minioClient = MinioClient.builder().credentials("minio", "minio123").endpoint("http://192.168.200.130:9000").build();

            //上传
            //2.上传
            PutObjectArgs putObjectArgs = PutObjectArgs.builder()
                    .object("list.html")//文件名
                    .contentType("text/html")//文件类型
                    .bucket("leadnews")//桶名词  与minio创建的名词一致
                    .stream(fileInputStream, fileInputStream.available(), -1) //文件流
                    .build();

            minioClient.putObject(putObjectArgs);

            //访问路径
            System.out.println("http://192.168.200.130:9000/leadnews/list.html");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
