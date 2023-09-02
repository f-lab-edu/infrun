package com.flab.infrun.lecture.infrastructure.storage.aws.config;

import java.time.Duration;
import software.amazon.awssdk.regions.Region;

public class S3Const {

    public static final String bucketName = "infrun";
    public static final String profileName = "InfrunManager";
    public static final Region region = Region.AP_NORTHEAST_2;
    public static final Duration duration = Duration.ofMinutes(30);
}
