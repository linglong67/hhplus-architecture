package io.hhplus.architecture.lecture.infrastructure;

import io.hhplus.architecture.lecture.domain.LectureApplicant;
import io.hhplus.architecture.lecture.domain.repository.LectureApplicantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class LectureApplicantRepositoryImpl implements LectureApplicantRepository {

    private final LectureApplicantJpaRepository repository;

    @Override
    public List<LectureApplicant> findAllByUserId(Long userId) {
        return repository.findAllByUserId(userId);
    }

    @Override
    public LectureApplicant apply(LectureApplicant applicant) {
        return repository.save(applicant);
    }

    @Override
    public Optional<LectureApplicant> findByUserIdAndLectureOptionId(Long userId, Long lectureOptionId) {
        return repository.findByUserIdAndLectureOption_Id(userId, lectureOptionId);
    }
}