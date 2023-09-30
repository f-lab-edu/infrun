package com.flab.infrun.lecture.infrastructure.storage.aws.config;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.regions.Region;

@Component
public class S3Config {

    @Value("${aws.s3.bucketName}")
    String bucketName;

    @Value("${aws.s3.profileName}")
    String profileName;

    @Value("${aws.s3.region}")
    String region;

    @Value("${aws.s3.duration}")
    String duration;

    public String getBucketName() {
        return bucketName;
    }

    public String getProfileName() {
        return profileName;
    }

    public Region getRegion() {
        return Region.of(region);
    }

    public Duration getDuration() {
        return Duration.ofMinutes(Long.parseLong(duration));
    }
}
