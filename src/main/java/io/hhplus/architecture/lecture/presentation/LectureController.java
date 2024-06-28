package io.hhplus.architecture.lecture.presentation;

import io.hhplus.architecture.lecture.application.LectureService;
import io.hhplus.architecture.lecture.domain.LectureApplicant;
import io.hhplus.architecture.lecture.domain.LectureOption;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lectures")
@RequiredArgsConstructor
public class LectureController {

    private final LectureService lectureService;

    @PostMapping("apply")
    public LectureApplicant applyLecture(@RequestBody LectureApplyRequestDto request) {
        return lectureService.applyLecture(request.getLectureOptionId(), request.getUserId());
    }

    @GetMapping("")
    public List<LectureOption> getLectures() {
        return lectureService.getLectures();
    }

    @GetMapping("application/{userId}")
    public List<LectureApplicant> getAppliedLecturesByUser(@PathVariable long userId) {
        return lectureService.getAppliedLecturesByUser(userId);
    }
}
