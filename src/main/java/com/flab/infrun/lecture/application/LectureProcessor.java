package com.flab.infrun.lecture.application;

import com.flab.infrun.lecture.application.command.LectureRegisterCommand;
import com.flab.infrun.lecture.application.exception.DuplicateLectureFileNameException;
import com.flab.infrun.lecture.application.fileCommand.StorageUpload;
import com.flab.infrun.lecture.domain.Lecture;
import com.flab.infrun.lecture.domain.LectureDetail;
import com.flab.infrun.lecture.domain.LectureFile;
import com.flab.infrun.lecture.domain.LectureFileRepository;
import com.flab.infrun.lecture.domain.LectureRepository;
import com.flab.infrun.lecture.infrastructure.persistence.LectureDetailRepositoryAdapter;
import com.google.common.annotations.VisibleForTesting;
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
    private final LectureFileRepository lectureFileRepository;
    private final StorageUpload storageUpload;

    public long registerLecture(LectureRegisterCommand lectureRegisterCommand) {

        validateLectureFile(lectureRegisterCommand.lectureFileList());

        List<LectureFile> uploadedFileId = uploadFile(lectureRegisterCommand);
        Map<String, LectureFile> mappingFileId = mappingFileId(uploadedFileId);

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

    private Map<String, LectureFile> mappingFileId(List<LectureFile> uploadedFile) {
        Map<String, LectureFile> result = new HashMap<>();
        uploadedFile.forEach(
            file -> result.put(file.getName(), file));
        return result;
    }

    private void savedDetail(LectureRegisterCommand lectureRegisterCommand, long savedLectureId,
        Map<String, LectureFile> mappingFileId) {
        lectureRegisterCommand.lectureDetailCommandList().forEach(
            detail -> lectureDetailRepository.save(
                LectureDetail.of(detail.chapter(), detail.name(),
                    savedLectureId, mappingFileId.get(detail.fileName()))));
    }

    public List<LectureFile> uploadFile(LectureRegisterCommand lectureRegisterCommand) {
        List<LectureFile> lectureVideoFileIdList = new ArrayList<>();
        lectureRegisterCommand.lectureFileList()
            .forEach(file -> lectureVideoFileIdList.add(
                lectureFileRepository.save(storageUpload.upload(file))));
        return lectureVideoFileIdList;
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
