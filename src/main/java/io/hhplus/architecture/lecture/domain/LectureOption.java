package io.hhplus.architecture.lecture.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LectureOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "lecture_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Lecture lecture;

    @Column(nullable = false)
    private LocalDate lectureDate;

    @Column(nullable = false)
    private LocalTime startTime;

    @Column(nullable = false)
    private LocalTime endTime;

    @Column(nullable = false)
    private Long maxCapacity;

    @Column(nullable = false)
    private Long appliedCount;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public LectureOption(Long id, Lecture lecture, LocalDate lectureDate, LocalTime startTime, LocalTime endTime, Long maxCapacity, Long appliedCount) {
        this.id = id;
        this.lecture = lecture;
        this.lectureDate = lectureDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.maxCapacity = maxCapacity;
        this.appliedCount = appliedCount;
    }

    public LectureOption(Lecture lecture, LocalDate lectureDate, LocalTime startTime, LocalTime endTime, Long maxCapacity, Long appliedCount) {
        this.lecture = lecture;
        this.lectureDate = lectureDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.maxCapacity = maxCapacity;
        this.appliedCount = appliedCount;
    }

    public void isFull() {
        if (Objects.equals(appliedCount, maxCapacity)) {
            throw new IllegalStateException("선착순 마감되었습니다.");
        }
    }

    public void validateLectureDate() {
        if (lectureDate.isBefore(LocalDate.now())) {
            throw new IllegalStateException("신청할 수 없는 날짜입니다.");
        }
    }

    public void increaseAppliedCount(long currentCount) {
        appliedCount = currentCount;
    }
}