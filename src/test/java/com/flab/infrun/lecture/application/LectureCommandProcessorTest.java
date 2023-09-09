package com.flab.infrun.lecture.application;

import com.flab.infrun.lecture.domain.StubLectureRepository;
import com.flab.infrun.member.domain.StubMemberRepository;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.mock.web.MockMultipartFile;


class LectureCommandProcessorTest {

    private LectureCommandProcessor processor;
    private final StubLectureRepository lectureRepository = new StubLectureRepository();
    private final StubMemberRepository memberRepository = new StubMemberRepository();

    @BeforeEach
    void setup() {
        processor = new LectureCommandProcessor(
            //todo- utils stub refactor
            lectureRepository,
            null,
            null,
            null
        );
    }

    private MockMultipartFile setupMultiPartTextFile(String fileName, String originFileName,
        String content) {
        return new MockMultipartFile(fileName, originFileName,
            "text/plain", content.getBytes(
            StandardCharsets.UTF_8));
    }
}
