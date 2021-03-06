package com.mt.middle.service;

import com.mt.common.core.CodeEnum;
import com.mt.common.exception.SysException;
import com.mt.common.utils.UUIDUtils;
import com.mt.middle.configuration.MinioAutoConfiguration;
import io.minio.MinioClient;
import io.minio.PutObjectOptions;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Minio封装
 */
@Service
public class MinioOssService {

    private MinioClient minioClient;

    @Autowired
    MinioOssService(MinioAutoConfiguration minioAutoConfiguration) {
        this.minioClient = minioAutoConfiguration.getMinioClient();
    }


    public Map<String, String> putObject(String bucketName, MultipartFile file, String isRename) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException, RegionConflictException {
        Assert.isTrue(bucketName.length() > 3, "桶名称长度需要大于3！");
        this.checkState();
        if (!this.minioClient.bucketExists(bucketName)) {
            this.minioClient.makeBucket(bucketName);
        }
        String contentType = file.getContentType();
        String fileName = file.getOriginalFilename();
        fileName = mix(fileName, isRename);
        PutObjectOptions options = new PutObjectOptions(file.getSize(), -1L);
        options.setContentType(StringUtils.hasText(contentType) ? contentType : "application/octet-stream");
        this.minioClient.putObject(bucketName, fileName, file.getInputStream(), options);
        Map<String, String> result = new HashMap<>(2);
        result.put("fileName", fileName);
        result.put("filePath", String.format("/%s/%s", bucketName, fileName));
        return result;
    }

    public Optional<InputStream> getObject(String bucketName, String objectName) throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException {
        this.checkState();
        InputStream inputStream = this.minioClient.getObject(bucketName, objectName);
        return Optional.of(inputStream);
    }

    /**
     * 文件重命名
     *
     * @param origin
     * @param isRename
     * @return
     */
    private String mix(String origin, String isRename) {
        Assert.notNull(origin, "原始文件名丢失！");
        String suffix = origin.replaceAll(".*\\.", "");
        return "0".equals(isRename) ? UUIDUtils.UUID() + "." + suffix : origin;
    }

    /**
     * 检查文件服务开启状态
     *
     * @return
     */
    private void checkState() {
        if (Objects.isNull(this.minioClient)) {
            throw new SysException(CodeEnum.MINIO_CLOSED);
        }
    }
}
