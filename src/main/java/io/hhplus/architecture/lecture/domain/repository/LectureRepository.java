package io.hhplus.architecture.lecture.domain.repository;

import io.hhplus.architecture.lecture.domain.Lecture;

public interface LectureRepository {

    Lecture save(Lecture lecture);
}
