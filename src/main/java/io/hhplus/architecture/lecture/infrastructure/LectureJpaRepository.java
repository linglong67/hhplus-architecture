package io.hhplus.architecture.lecture.infrastructure;

import io.hhplus.architecture.lecture.domain.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LectureJpaRepository extends JpaRepository<Lecture, Long> {
}