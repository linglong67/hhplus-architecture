package io.hhplus.architecture.lecture.domain.repository;

import io.hhplus.architecture.lecture.domain.LectureOption;

import java.util.List;
import java.util.Optional;

public interface LectureOptionRepository {
    List<LectureOption> findAll();

    Optional<LectureOption> findByIdWithPessimisticLock(long id);

    Optional<LectureOption> findById(long id);

    LectureOption save(LectureOption lectureOption);
}