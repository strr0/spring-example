package com.strr.minio.service;

import com.strr.api.IOssService;
import com.strr.api.constant.Constant;
import com.strr.api.model.OssConfig;
import io.minio.*;
import io.minio.errors.*;
import org.springframework.stereotype.Service;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class MinioOssService implements IOssService {
    private final MinioClient minioClient;

    public MinioOssService(OssConfig ossConfig) {
        this.minioClient = MinioClient.builder()
                .endpoint(ossConfig.getUrl())
                .credentials(ossConfig.getAccessKey(), ossConfig.getSecretKey())
                .build();
    }

    @Override
    public boolean existBucket(String name) {
        boolean exists;
        try {
            exists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(name).build());
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException
                 | InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException | XmlParserException e) {
            e.printStackTrace();
            exists = false;
        }
        return exists;
    }

    @Override
    public void createBucket(String name) {
        try {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(name).build());
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException
                 | InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException | XmlParserException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeBucket(String name) {
        try {
            minioClient.removeBucket(RemoveBucketArgs.builder().bucket(name).build());
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException
                 | InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException | XmlParserException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void uploadFile(String bucket, File file) {
        try (FileInputStream in = new FileInputStream(file)) {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(Constant.MINIO_BASE_PATH + file.getName())
                    .stream(in, in.available(), -1)
                    .build());
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException
                 | InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException | XmlParserException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void downloadFile(String bucket, String filename, String target) {
        InputStream in = null;
        FileOutputStream out = null;
        try {
            in = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucket)
                    .object(Constant.MINIO_BASE_PATH + filename)
                    .build());
            out = new FileOutputStream(target);
            in.transferTo(out);
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException
                 | InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException | XmlParserException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void removeFile(String bucket, String filename) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(bucket)
                    .object(Constant.MINIO_BASE_PATH + filename)
                    .build());
        } catch (ErrorResponseException | InsufficientDataException | InternalException | InvalidKeyException
                 | InvalidResponseException | IOException | NoSuchAlgorithmException | ServerException | XmlParserException e) {
            e.printStackTrace();
        }
    }
}
