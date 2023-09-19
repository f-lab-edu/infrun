package com.flab.infrun.lecture.infrastructure.storage.aws;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CompleteMultipartUploadRequest;
import software.amazon.awssdk.services.s3.model.CompletedMultipartUpload;
import software.amazon.awssdk.services.s3.model.CompletedPart;
import software.amazon.awssdk.services.s3.model.CreateMultipartUploadRequest;
import software.amazon.awssdk.services.s3.model.CreateMultipartUploadResponse;
import software.amazon.awssdk.services.s3.model.UploadPartRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedUploadPartRequest;
import software.amazon.awssdk.services.s3.presigner.model.UploadPartPresignRequest;

@Service
public class SimpleStorageService {

    public String getUploadId(String bucketName, String objectKey, String profileName,
        Region region) {
        ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.create(
            profileName);
        S3Client s3 = S3Client.builder()
            .region(region)
            .credentialsProvider(credentialsProvider)
            .build();
        CreateMultipartUploadRequest createRequest = CreateMultipartUploadRequest.builder()
            .bucket(bucketName)
            .key(objectKey)
            .build();
        CreateMultipartUploadResponse createResponse = s3.createMultipartUpload(createRequest);
        return createResponse.uploadId();
    }

    public List<String> getPreSignedUrl(String bucketName, String objectKey,
        String profileName, Region region, String uploadId, int partCnt, Duration duration) {
        ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.create(
            profileName);
        S3Presigner preSigner = S3Presigner.builder()
            .region(region)
            .credentialsProvider(credentialsProvider)
            .build();
        List<String> preSignedUrlList = new ArrayList<>();
        for (int partNum = 1; partNum <= partCnt; partNum++) {
            preSignedUrlList.add(
                publishPreSignedUrl(bucketName, objectKey, duration, uploadId, preSigner, partNum));
        }
        return preSignedUrlList;
    }

    public void completeUpload(String bucketName, String objectKey,
        String profileName,
        Region region, String uploadId, List<String> etag) {
        ProfileCredentialsProvider credentialsProvider = ProfileCredentialsProvider.create(
            profileName);
        S3Client s3 = S3Client.builder()
            .region(region)
            .credentialsProvider(credentialsProvider)
            .build();
        List<CompletedPart> partList = new ArrayList<>();
        for (int i = 1; i <= etag.size(); i++) {
            partList.add(CompletedPart.builder().partNumber(i).eTag(etag.get(i - 1)).build());
        }
        CompletedMultipartUpload completedMultipartUpload = CompletedMultipartUpload.builder()
            .parts(partList)
            .build();
        CompleteMultipartUploadRequest completeMultipartUploadRequest =
            CompleteMultipartUploadRequest.builder()
                .bucket(bucketName)
                .key(objectKey)
                .uploadId(uploadId)
                .multipartUpload(completedMultipartUpload)
                .build();
        s3.completeMultipartUpload(completeMultipartUploadRequest);
    }

    private String publishPreSignedUrl(String bucketName, String objectKey, Duration duration,
        String uploadId, S3Presigner preSigner, int partNum) {
        UploadPartRequest uploadPartRequest = UploadPartRequest.builder()
            .bucket(bucketName)
            .uploadId(uploadId)
            .key(objectKey)
            .partNumber(partNum)
            .build();
        UploadPartPresignRequest preSignedRequest = UploadPartPresignRequest.builder()
            .signatureDuration(duration)
            .uploadPartRequest(uploadPartRequest)
            .build();
        PresignedUploadPartRequest preSignedUploadPartRequest = preSigner.presignUploadPart(
            preSignedRequest);
        return preSignedUploadPartRequest.url().toString();
    }
}
