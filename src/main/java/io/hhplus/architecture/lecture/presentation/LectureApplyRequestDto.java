package io.hhplus.architecture.lecture.presentation;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LectureApplyRequestDto {
    private long lectureOptionId;
    private long userId;
}