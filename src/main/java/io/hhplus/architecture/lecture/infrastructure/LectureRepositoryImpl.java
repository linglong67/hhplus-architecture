package io.hhplus.architecture.lecture.infrastructure;

import io.hhplus.architecture.lecture.domain.Lecture;
import io.hhplus.architecture.lecture.domain.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LectureRepositoryImpl implements LectureRepository {

    private final LectureJpaRepository repository;

    @Override
    public Lecture save(Lecture lecture) {
        return repository.save(lecture);
    }
}
