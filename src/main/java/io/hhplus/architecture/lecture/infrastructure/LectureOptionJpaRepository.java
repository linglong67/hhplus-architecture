package io.hhplus.architecture.lecture.infrastructure;

import io.hhplus.architecture.lecture.domain.LectureOption;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface LectureOptionJpaRepository extends JpaRepository<LectureOption, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<LectureOption> findById(long id);
}