package io.hhplus.architecture.lecture.infrastructure;

import io.hhplus.architecture.lecture.domain.LectureOption;
import io.hhplus.architecture.lecture.domain.repository.LectureOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LectureOptionRepositoryImpl implements LectureOptionRepository {

    private final LectureOptionJpaRepository repository;

    @Override
    public List<LectureOption> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<LectureOption> findByIdWithPessimisticLock(long id) {
        return repository.findByIdWithPessimisticLock(id);
    }

    @Override
    public LectureOption save(LectureOption lectureOption) {
        return repository.save(lectureOption);
    }
}