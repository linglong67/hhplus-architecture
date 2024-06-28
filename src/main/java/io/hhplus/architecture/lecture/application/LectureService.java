package io.hhplus.architecture.lecture.application;

import io.hhplus.architecture.lecture.domain.LectureApplicant;
import io.hhplus.architecture.lecture.domain.LectureOption;
import io.hhplus.architecture.lecture.domain.repository.LectureApplicantRepository;
import io.hhplus.architecture.lecture.domain.repository.LectureOptionRepository;
import io.hhplus.architecture.lecture.domain.repository.LectureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureService {

    private final LectureRepository lectureRepository;
    private final LectureOptionRepository lectureOptionRepository;
    private final LectureApplicantRepository lectureApplicantRepository;

    @Transactional
    public LectureApplicant applyLecture(long lectureOptionId, long userId) {
        // 예외 - 기존에 신청한 경우
        lectureApplicantRepository.findByUserIdAndLectureOptionId(userId, lectureOptionId)
                                  .ifPresent(applicant -> {
                                      throw new IllegalStateException("이미 신청한 특강입니다.");
                                  });

        // 예외 - 존재하지 않는 특강 옵션인 경우
        LectureOption lectureOption =
                lectureOptionRepository.findByIdWithPessimisticLock(lectureOptionId)
                                       .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 특강입니다."));

        // 예외 - 특강 최대 정원만큼 신청된 경우
        lectureOption.isFull();

        // 예외 - 신청할 수 없는 날짜인 경우
        lectureOption.validateLectureDate();

        lectureOption.increaseAppliedCount(lectureOption.getAppliedCount() + 1);
        lectureOptionRepository.save(lectureOption);

        return lectureApplicantRepository.apply(new LectureApplicant(lectureOption, userId));
    }

    @Transactional(readOnly = true)
    public List<LectureOption> getLectures() {
        return lectureOptionRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<LectureApplicant> getAppliedLecturesByUser(long userId) {
        return lectureApplicantRepository.findAllByUserId(userId);
    }
}
