package com.strr.minio;

import com.strr.api.IOssService;
import com.strr.api.constant.Constant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@SpringBootTest
class MinioClientApplicationTests {
    private static final String DOWNLOAD_PATH = "E:\\Users\\lenovo\\Downloads\\";

    @Autowired
    IOssService ossService;

    @Test
    void testUpload() {
        if (!ossService.existBucket(Constant.MINIO_BUCKET)) {
            ossService.createBucket(Constant.MINIO_BUCKET);
        }
        File file = new File(DOWNLOAD_PATH + "test.txt");
        ossService.uploadFile(Constant.MINIO_BUCKET, file);
    }

    @Test
    void testDownload() {
        ossService.downloadFile(Constant.MINIO_BUCKET, "test.txt", DOWNLOAD_PATH + "test.txt");
    }

    @Test
    void testRemove() {
        ossService.removeFile(Constant.MINIO_BUCKET, "test.txt");
    }
}
