package com.strr.api;

import java.io.File;

public interface IOssService {
    /**
     * 是否存在桶
     */
    boolean existBucket(String name);

    /**
     * 创建桶
     */
    void createBucket(String name);

    /**
     * 删除桶
     */
    void removeBucket(String name);

    /**
     * 上传文件
     */
    void uploadFile(String bucket, File file);

    /**
     * 下载文件
     */
    void downloadFile(String bucket, String filename, String target);

    /**
     * 删除文件
     */
    void removeFile(String bucket, String filename);
}
