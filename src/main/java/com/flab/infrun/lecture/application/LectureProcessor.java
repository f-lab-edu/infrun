package com.flab.infrun.lecture.application;

import com.flab.infrun.lecture.application.command.LectureRegisterCommand;
import com.flab.infrun.lecture.domain.Lecture;
import com.flab.infrun.lecture.domain.LectureDetail;
import com.flab.infrun.lecture.domain.LectureRepository;
import com.flab.infrun.lecture.domain.LectureVideoFile;
import com.flab.infrun.lecture.domain.LectureVideoFileRepository;
import com.flab.infrun.lecture.infrastructure.persistance.LectureDetailRepositoryAdapter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Component
public class LectureProcessor {

    private final LectureRepository lectureRepository;
    private final LectureDetailRepositoryAdapter lectureDetailRepository;
    private final LectureVideoFileRepository lectureVideoFileRepository;

    public long registerLecture(LectureRegisterCommand lectureRegisterCommand) {

        //todo-filName 중복 체크
        List<LectureVideoFile> lectureVideoFiles = uploadFile(lectureRegisterCommand);
        Map<String, Long> mappingFileId = mappingFileId(lectureRegisterCommand, lectureVideoFiles);

        Lecture savedLecture = getSavedLecture(lectureRegisterCommand);

        //todo-fileNameDetail fileId Mapping
        savedDetail(lectureRegisterCommand, savedLecture, mappingFileId);

        return savedLecture.getId();
    }

    private Lecture getSavedLecture(LectureRegisterCommand lectureRegisterCommand) {
        return lectureRepository.save(Lecture.of(
            lectureRegisterCommand.name(),
            lectureRegisterCommand.price(),
            lectureRegisterCommand.introduce()));
    }

    private Map<String, Long> mappingFileId(LectureRegisterCommand lectureRegisterCommand,
        List<LectureVideoFile> lectureVideoFiles) {
        Map<String, Long> result = new HashMap<>();

        //todo-stream 개선
        lectureRegisterCommand.lectureDetailCommandList()
            .forEach(detail -> lectureVideoFiles.forEach(file -> {
                if (file.getName().equals(detail.fileName())) {
                    result.put(detail.fileName(), file.getId());
                }
            }))
        ;

        return result;
    }

    private void savedDetail(LectureRegisterCommand lectureRegisterCommand, Lecture savedLecture,
        Map<String, Long> mappingFileId) {
        lectureRegisterCommand.lectureDetailCommandList().forEach(
            detail -> lectureDetailRepository.save(
                LectureDetail.of(detail.chapter(), detail.name(),
                    savedLecture.getId(), mappingFileId.get(detail.fileName()))));
    }

    public List<LectureVideoFile> uploadFile(LectureRegisterCommand lectureRegisterCommand) {

        List<LectureVideoFile> lectureVideoFileList = new ArrayList<>();

        lectureRegisterCommand.lectureFileList()
            .forEach(file -> lectureVideoFileList.add(getMultipartFiles(file)));

        return lectureVideoFileList;
    }

    private LectureVideoFile getMultipartFiles(MultipartFile lectureVideoFile) {

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
                saved = lectureVideoFileRepository.save(lectureVideo);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return saved;
        }
        return null;
    }
}
