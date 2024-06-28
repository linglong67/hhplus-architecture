package io.hhplus.architecture.lecture.domain.repository;

import io.hhplus.architecture.lecture.domain.LectureApplicant;

import java.util.List;
import java.util.Optional;

public interface LectureApplicantRepository {
    List<LectureApplicant> findAllByUserId(Long userId);

    LectureApplicant apply(LectureApplicant applicant);

    Optional<LectureApplicant> findByUserIdAndLectureOptionId(Long userId, Long lectureOptionId);
}