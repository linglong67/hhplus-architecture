package io.hhplus.architecture.lecture.infrastructure;

import io.hhplus.architecture.lecture.domain.LectureOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureOptionJpaRepository extends JpaRepository<LectureOption, Long> {
}