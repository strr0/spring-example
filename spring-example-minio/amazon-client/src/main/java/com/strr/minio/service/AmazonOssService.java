package com.strr.minio.service;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.strr.api.IOssService;
import com.strr.api.constant.Constant;
import com.strr.api.model.OssConfig;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class AmazonOssService implements IOssService {
    private final AmazonS3 client;

    public AmazonOssService(OssConfig ossConfig) {
        AwsClientBuilder.EndpointConfiguration endpointConfig = new AwsClientBuilder.EndpointConfiguration(ossConfig.getUrl(), null);
        AWSCredentials credentials = new BasicAWSCredentials(ossConfig.getAccessKey(), ossConfig.getSecretKey());
        AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setProtocol(Protocol.HTTP);
        AmazonS3ClientBuilder build = AmazonS3Client.builder()
                .withEndpointConfiguration(endpointConfig)
                .withClientConfiguration(clientConfig)
                .withCredentials(credentialsProvider)
                .disableChunkedEncoding()
                .enablePathStyleAccess();
        this.client = build.build();
    }

    @Override
    public boolean existBucket(String name) {
        return client.doesBucketExistV2(name);
    }

    @Override
    public void createBucket(String name) {
        CreateBucketRequest createBucketRequest = new CreateBucketRequest(name);
        createBucketRequest.setCannedAcl(CannedAccessControlList.Private);
        client.createBucket(createBucketRequest);
    }

    @Override
    public void removeBucket(String name) {
        DeleteBucketRequest deleteBucketRequest = new DeleteBucketRequest(name);
        client.deleteBucket(deleteBucketRequest);
    }

    @Override
    public void uploadFile(String bucket, File file) {
        try (FileInputStream in = new FileInputStream(file)) {
            ObjectMetadata metadata = new ObjectMetadata();
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, Constant.MINIO_BASE_PATH + file.getName(), in, metadata);
            putObjectRequest.setCannedAcl(CannedAccessControlList.Private);
            client.putObject(putObjectRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void downloadFile(String bucket, String filename, String target) {
        InputStream in = null;
        FileOutputStream out = null;
        try {
            S3Object object = client.getObject(bucket, Constant.MINIO_BASE_PATH + filename);
            in = object.getObjectContent();
            out = new FileOutputStream(target);
            in.transferTo(out);
        } catch (IOException e) {
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
        client.deleteObject(bucket, Constant.MINIO_BASE_PATH + filename);
    }
}
