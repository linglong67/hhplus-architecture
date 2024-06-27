package io.hhplus.architecture.lecture.application;

import io.hhplus.architecture.lecture.domain.*;
import io.hhplus.architecture.lecture.domain.repository.LectureApplicantRepository;
import io.hhplus.architecture.lecture.domain.repository.LectureOptionRepository;
import io.hhplus.architecture.lecture.domain.repository.LectureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class LectureServiceTest {

    @InjectMocks
    private LectureService lectureService;

    @Mock
    private LectureRepository lectureRepository;

    @Mock
    private LectureOptionRepository lectureOptionRepository;

    @Mock
    private LectureApplicantRepository lectureApplicantRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("특강 신청 성공")
    void lecture_apply_success() {
        //given
        Long lectureId = 1L;
        String title = "항해플러스 특강";
        String speaker = "OO 연사님";
        Long lectureOptionId = 1L;
        LocalDate lectureDate = LocalDate.of(2024, 6, 29);
        LocalTime startTime = LocalTime.of(13, 0);
        LocalTime endTime = LocalTime.of(18, 0);
        Long maxCapacity = 30L;
        Long appliedCount = 0L;
        Long userId = 1L;
        Long lectureApplicantId = 1L;

        Lecture lecture = new Lecture(lectureId, title, speaker);
        LectureOption lectureOption = new LectureOption(
                lectureOptionId, lecture, lectureDate, startTime, endTime, maxCapacity, appliedCount);
        LectureApplicant expected = new LectureApplicant(lectureApplicantId, lectureOption, userId);

        //when
        when(lectureOptionRepository.findById(lectureOptionId)).thenReturn(Optional.of(lectureOption));
        when(lectureApplicantRepository.apply(new LectureApplicant(lectureOption, userId))).thenReturn(expected);

        //then
        assertThat(lectureService.applyLecture(lectureOptionId, userId)).isEqualTo(expected);
    }

    @Test
    @DisplayName("특강 목록조회 성공")
    void lecture_list_fetch_success() {
        //given
        Long lectureId = 1L;
        String title = "항해플러스 특강";
        String speaker = "OO 연사님";
        Long lectureOptionId = 1L;

        Lecture lecture = new Lecture(lectureId, title, speaker);
        List<LectureOption> expected = List.of(
                new LectureOption(lectureOptionId, lecture, LocalDate.of(2024, 6, 29),
                        LocalTime.of(13, 0), LocalTime.of(18, 0), 30L, 0L),
                new LectureOption(lectureOptionId++, lecture, LocalDate.of(2024, 6, 30),
                        LocalTime.of(12, 0), LocalTime.of(13, 0), 40L, 0L),
                new LectureOption(lectureOptionId++, lecture, LocalDate.of(2024, 7, 1),
                        LocalTime.of(14, 0), LocalTime.of(16, 0), 50L, 0L)
        );

        //when
        when(lectureOptionRepository.findAll()).thenReturn(expected);

        //then
        assertThat(lectureService.getLectures()).isEqualTo(expected);
    }

    @Test
    @DisplayName("신청완료 특강 목록조회 성공")
    void applied_lecture_list_fetch_success() {
        //given
        Long lectureId = 1L;
        String title = "항해플러스 특강";
        String speaker = "OO 연사님";
        Long lectureOptionId = 1L;
        Long lectureApplicantId = 1L;
        Long userId = 1L;

        Lecture lecture = new Lecture(lectureId, title, speaker);
        LectureOption lectureOption = new LectureOption(lectureOptionId++, lecture, LocalDate.of(2024, 6, 29),
                LocalTime.of(13, 0), LocalTime.of(18, 0), 30L, 0L);
        LectureOption lectureOption2 = new LectureOption(lectureOptionId++, lecture, LocalDate.of(2024, 6, 30),
                LocalTime.of(12, 0), LocalTime.of(13, 0), 40L, 0L);

        List<LectureApplicant> expected = List.of(
                new LectureApplicant(lectureApplicantId++, lectureOption, userId),
                new LectureApplicant(lectureApplicantId++, lectureOption2, userId)
        );

        //when
        when(lectureApplicantRepository.findAllByUserId(userId)).thenReturn(expected);

        //then
        assertThat(lectureService.getAppliedLecturesByUser(userId)).isEqualTo(expected);
    }
}