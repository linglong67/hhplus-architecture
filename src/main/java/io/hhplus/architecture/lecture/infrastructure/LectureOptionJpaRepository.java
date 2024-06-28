package io.hhplus.architecture.lecture.infrastructure;

import io.hhplus.architecture.lecture.domain.LectureOption;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LectureOptionJpaRepository extends JpaRepository<LectureOption, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT lo FROM LectureOption lo WHERE lo.id = :id")
    Optional<LectureOption> findByIdWithPessimisticLock(@Param("id") long id);
}