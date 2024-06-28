package io.hhplus.architecture.lecture.infrastructure;

import io.hhplus.architecture.lecture.domain.LectureApplicant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LectureApplicantJpaRepository extends JpaRepository<LectureApplicant, Long> {

    List<LectureApplicant> findAllByUserId(long userId);

    Optional<LectureApplicant> findByUserIdAndLectureOption_Id(long userId, long lectureOptionId);
}