package com.flab.infrun.lecture.application.fileCommand;

import com.flab.infrun.lecture.domain.LectureFile;
import org.springframework.web.multipart.MultipartFile;


public interface StorageUpload {

    LectureFile upload(MultipartFile lectureVideoFile);
}
