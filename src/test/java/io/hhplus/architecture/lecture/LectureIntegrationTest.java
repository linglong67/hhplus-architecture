package io.hhplus.architecture.lecture;

import io.hhplus.architecture.lecture.application.LectureService;
import io.hhplus.architecture.lecture.domain.Lecture;
import io.hhplus.architecture.lecture.domain.LectureOption;
import io.hhplus.architecture.lecture.domain.repository.LectureOptionRepository;
import io.hhplus.architecture.lecture.domain.repository.LectureRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class LectureIntegrationTest {

    @Autowired
    private LectureService lectureService;

    @Autowired
    private LectureRepository lectureRepository;

    @Autowired
    private LectureOptionRepository lectureOptionRepository;

    @Test
    @DisplayName("특강 신청 - 비관적 락 테스트")
    void lecture_apply_with_pessimistic_lock() throws InterruptedException {
        //given
        String title = "항해플러스 특강";
        String speaker = "OO 연사님";
        Long lectureOptionId = 1L;
        LocalDate lectureDate = LocalDate.of(2024, 7, 1);
        LocalTime startTime = LocalTime.of(13, 0);
        LocalTime endTime = LocalTime.of(18, 0);
        Long maxCapacity = 10L;
        Long appliedCount = 0L;

        Lecture lecture = new Lecture(title, speaker);
        lectureRepository.save(lecture);

        LectureOption lectureOption = new LectureOption(
                lecture, lectureDate, startTime, endTime, maxCapacity, appliedCount);
        lectureOptionRepository.save(lectureOption);

        //when
        final AtomicLong userId = new AtomicLong(1L);
        final int threadCount = 100;
        final ExecutorService executorService = Executors.newFixedThreadPool(32);
        final CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    lectureService.applyLecture(lectureOptionId, userId.getAndIncrement());
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        //then
        final LectureOption lo = lectureOptionRepository.findById(1L).get();
        assertThat(lo.getAppliedCount()).isEqualTo(lo.getMaxCapacity());
    }
}