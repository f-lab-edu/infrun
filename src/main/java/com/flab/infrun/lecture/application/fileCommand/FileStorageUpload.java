package com.flab.infrun.lecture.application.fileCommand;

import com.flab.infrun.lecture.domain.LectureFile;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileStorageUpload implements StorageUpload {

    @Override
    public LectureFile upload(MultipartFile lectureVideoFile) {
        LectureFile savedLectureFile = null;
        //todo-반환값, if, 영상처리, saved 관련부분 개선
        if (!lectureVideoFile.isEmpty()) {
            //todo-file 경로 없을 시 강사의 id 값 받아와서 하위 폴더 생성
            Path filepath = Paths.get("lectureVideo", lectureVideoFile.getOriginalFilename());
            LectureFile lectureVideo = LectureFile.of("/lectureVideo",
                lectureVideoFile.getOriginalFilename());
            try (OutputStream os = Files.newOutputStream(filepath)) {
                os.write(lectureVideoFile.getBytes());
                //todo-중복 file에 대한 중복 저장 issue
                savedLectureFile = lectureVideo;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return savedLectureFile;
    }
}
