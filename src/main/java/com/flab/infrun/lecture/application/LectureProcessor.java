package com.flab.infrun.lecture.application;

import com.flab.infrun.lecture.application.command.LectureRegisterCommand;
import com.flab.infrun.lecture.application.exception.DuplicateLectureFileNameException;
import com.flab.infrun.lecture.domain.Lecture;
import com.flab.infrun.lecture.domain.LectureDetail;
import com.flab.infrun.lecture.domain.LectureRepository;
import com.flab.infrun.lecture.domain.LectureVideoFile;
import com.flab.infrun.lecture.domain.LectureVideoFileRepository;
import com.flab.infrun.lecture.infrastructure.persistence.LectureDetailRepositoryAdapter;
import com.google.common.annotations.VisibleForTesting;
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

        validateLectureFile(lectureRegisterCommand.lectureFileList());

        List<Long> uploadedFileId = uploadFile(lectureRegisterCommand);
        Map<String, Long> mappingFileId = mappingFileId(uploadedFileId);

        long savedLectureId = getSavedLecture(lectureRegisterCommand);

        savedDetail(lectureRegisterCommand, savedLectureId, mappingFileId);

        return savedLectureId;
    }

    private long getSavedLecture(LectureRegisterCommand lectureRegisterCommand) {
        return lectureRepository.save(Lecture.of(
            lectureRegisterCommand.name(),
            lectureRegisterCommand.price(),
            lectureRegisterCommand.level(),
            lectureRegisterCommand.skill(),
            lectureRegisterCommand.introduce(),
            lectureRegisterCommand.userId())).getId()
            ;
    }

    private Map<String, Long> mappingFileId(List<Long> uploadedFileId) {
        Map<String, Long> result = new HashMap<>();
        uploadedFileId.forEach(
            id -> result.put(lectureVideoFileRepository.findById(id).get().getName(), id));

        return result;
    }

    private void savedDetail(LectureRegisterCommand lectureRegisterCommand, long savedLectureId,
        Map<String, Long> mappingFileId) {
        lectureRegisterCommand.lectureDetailCommandList().forEach(
            detail -> lectureDetailRepository.save(
                LectureDetail.of(detail.chapter(), detail.name(),
                    savedLectureId, mappingFileId.get(detail.fileName()))));
    }

    public List<Long> uploadFile(LectureRegisterCommand lectureRegisterCommand) {

        List<Long> lectureVideoFileIdList = new ArrayList<>();

        lectureRegisterCommand.lectureFileList()
            .forEach(file -> lectureVideoFileIdList.add(getMultipartFiles(file)));

        return lectureVideoFileIdList;
    }

    private long getMultipartFiles(MultipartFile lectureVideoFile) {

        //todo-반환값, if, 영상처리, saved 관련부분 개선
        if (!lectureVideoFile.isEmpty()) {
            //todo-file 경로 없을 시 강사의 id 값 받아와서 하위 폴더 생성
            Path filepath = Paths.get("lectureVideo", lectureVideoFile.getOriginalFilename());

            LectureVideoFile lectureVideo = LectureVideoFile.of("/lectureVideo",
                lectureVideoFile.getOriginalFilename());

            long savedVideoId;

            try (OutputStream os = Files.newOutputStream(filepath)) {
                os.write(lectureVideoFile.getBytes());
                //todo-중복 file에 대한 중복 저장 issue
                savedVideoId = lectureVideoFileRepository.save(lectureVideo).getId();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return savedVideoId;
        }
        return 0L;
    }

    @VisibleForTesting
    void validateLectureFile(List<MultipartFile> lectureFileList) {

        boolean duplicated = lectureFileList.stream()
            .map(MultipartFile::getOriginalFilename)
            .distinct()
            .count() != lectureFileList.size();

        if (duplicated) {
            throw new DuplicateLectureFileNameException();
        }
    }


}
