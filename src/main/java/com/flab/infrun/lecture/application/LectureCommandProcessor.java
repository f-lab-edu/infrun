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
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Component
public class LectureCommandProcessor {

    private final LectureRepository lectureRepository;
    private final LectureDetailRepositoryAdapter lectureDetailRepository;
    private final LectureFileRepository lectureFileRepository;
    private final StorageUpload storageUpload;

    @Transactional
    public long registerLecture(LectureRegisterCommand lectureRegisterCommand) {
        validateLectureFile(lectureRegisterCommand.lectureFileList());
        List<LectureFile> uploadedFile = uploadFile(lectureRegisterCommand);
        Map<String, LectureFile> mappingFileWithName = mappingFileId(uploadedFile);
        return getSavedLecture(lectureRegisterCommand, mappingFileWithName);
    }

    private long getSavedLecture(LectureRegisterCommand lectureRegisterCommand,
        Map<String, LectureFile> mappingFileWithName) {
        Lecture lecture = Lecture.of(
            lectureRegisterCommand.name(),
            lectureRegisterCommand.price(),
            lectureRegisterCommand.level(),
            lectureRegisterCommand.skill(),
            lectureRegisterCommand.introduce(),
            //todo-member getReferenceById or Make Temp Member
            null);

        List<LectureDetail> lectureDetails = lectureRegisterCommand.lectureDetailCommandList()
            .stream()
            .map(
                d -> LectureDetail.of(d.chapter(), d.name(), mappingFileWithName.get(d.fileName())))
            .toList();
        lecture.addLectureDetail(lectureDetails);
        return lectureRepository.save(lecture).getId();
    }

    private Map<String, LectureFile> mappingFileId(List<LectureFile> uploadedFile) {
        return uploadedFile.stream()
            .collect(Collectors.toMap(LectureFile::getName, Function.identity()));
    }

    public List<LectureFile> uploadFile(LectureRegisterCommand lectureRegisterCommand) {
        return lectureRegisterCommand.lectureFileList().stream()
            .map(file -> lectureFileRepository.save(storageUpload.upload(file)))
            .collect(Collectors.toList());
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
