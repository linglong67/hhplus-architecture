package io.hhplus.architecture.lecture.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.hhplus.architecture.lecture.application.LectureService;
import io.hhplus.architecture.lecture.domain.Lecture;
import io.hhplus.architecture.lecture.domain.LectureApplicant;
import io.hhplus.architecture.lecture.domain.LectureOption;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// TODO: andExpect 부분에 추가 검증을 할 것인가..?

/**
 * MockMvc 사용하여 엔드포인트 요청 테스트 및 정상 응답에 대한 검증
 */
@WebMvcTest(LectureController.class)
class LectureControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    LectureService lectureService;

    @Test
    @DisplayName("특강 신청 성공")
    void lecture_apply_success() throws Exception {
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

        Lecture lecture = new Lecture(lectureId, title, speaker);
        LectureOption lectureOption = new LectureOption(
                lectureOptionId, lecture, lectureDate, startTime, endTime, maxCapacity, appliedCount);

        //when
        when(lectureService.applyLecture(anyLong(), anyLong())).thenReturn(new LectureApplicant(lectureOption, userId));

        //then
        LectureApplyRequestDto requestDto =
                LectureApplyRequestDto.builder()
                                      .lectureOptionId(lectureOptionId)
                                      .userId(userId)
                                      .build();

        mockMvc.perform(post("/lectures/apply")
               .contentType(MediaType.APPLICATION_JSON)
               .content(objectMapper.writeValueAsString(requestDto)))
               .andExpect(status().isOk());

        verify(lectureService).applyLecture(lectureOptionId, userId);
    }

    @Test
    @DisplayName("특강 목록조회 성공")
    void lecture_list_fetch_success() throws Exception {
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
        when(lectureService.getLectures()).thenReturn(expected);

        //then
        mockMvc.perform(get("/lectures"))
               .andExpect(status().isOk());

        verify(lectureService).getLectures();
    }

    @Test
    @DisplayName("신청완료 특강 목록조회 성공")
    void applied_lecture_list_fetch_success() throws Exception {
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
        when(lectureService.getAppliedLecturesByUser(anyLong())).thenReturn(expected);

        //then
        mockMvc.perform(get("/lectures/application/{userId}", userId))
               .andExpect(status().isOk());

        verify(lectureService).getAppliedLecturesByUser(userId);
    }
}