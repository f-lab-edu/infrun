package com.flab.infrun.lecture.application;

import com.flab.infrun.lecture.application.command.LectureRegisterCommand;
import com.flab.infrun.lecture.domain.Lecture;
import com.flab.infrun.lecture.domain.LectureVideoFile;
import com.flab.infrun.lecture.infrastructure.persistance.LectureRepositoryAdapter;
import com.flab.infrun.lecture.infrastructure.persistance.LectureVideoFileRepositoryAdapter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Component
public class LectureProcessor {

    private final LectureRepositoryAdapter lectureRepositoryAdapter;
    private final LectureVideoFileRepositoryAdapter lectureVideoFileRepositoryAdapter;

    public Long registerLecture(LectureRegisterCommand lectureRegisterCommand) {
        Lecture savedLecture = lectureRepositoryAdapter.save(Lecture.of(
            lectureRegisterCommand.name(),
            lectureRegisterCommand.price(),
            lectureRegisterCommand.introduce()));
        return savedLecture.getId();
    }

    public String uploadFile(MultipartFile lectureVideoFile) {
        //todo-반환값, if, 영상처리, saved 관련부분 개선
        if (!lectureVideoFile.isEmpty()) {
            //todo-file 경로 없을 시 강사의 id 값 받아와서 하위 폴더 생성
            Path filepath = Paths.get("lectureVideo", lectureVideoFile.getOriginalFilename());

            LectureVideoFile lectureVideo = LectureVideoFile.of("/lectureVideo",
                lectureVideoFile.getOriginalFilename());
            LectureVideoFile saved;
            try (OutputStream os = Files.newOutputStream(filepath)) {
                os.write(lectureVideoFile.getBytes());
                //todo-중복 file에 대한 중복 저장 issue
                saved = lectureVideoFileRepositoryAdapter.save(lectureVideo);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return saved.getUrl();
        }
        return null;
    }


}
