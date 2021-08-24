package at.ac.tuwien.inso.sqm.service;

import at.ac.tuwien.inso.sqm.dto.SemesterDto;
import at.ac.tuwien.inso.sqm.entity.Feedback;
import at.ac.tuwien.inso.sqm.entity.Grade;
import at.ac.tuwien.inso.sqm.entity.Course;
import at.ac.tuwien.inso.sqm.entity.SemesterTypeEnum;
import at.ac.tuwien.inso.sqm.entity.StudentEntity;
import at.ac.tuwien.inso.sqm.entity.StudyPlanRegistration;
import at.ac.tuwien.inso.sqm.service.study_progress.CourseRegistrationState;
import at.ac.tuwien.inso.sqm.service.study_progress.CuorseRegistration;
import at.ac.tuwien.inso.sqm.service.study_progress.SemesterProgress;
import at.ac.tuwien.inso.sqm.service.study_progress.StudyProgress;
import at.ac.tuwien.inso.sqm.service.study_progress.StudyProgressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudyProgressServiceImpl implements StudyProgressService {

    @Autowired
    private SemesterServiceInterface semesterService;

    @Autowired
    private LehrveranstaltungServiceInterface courseService;

    @Autowired
    private GradeIService gradeService;

    @Autowired
    private FeedbackIService feedbackService;

    @Override
    @Transactional(readOnly = true)
    public StudyProgress studyProgressFor(StudentEntity student) {
        SemesterDto currentSemester =
                semesterService.getOrCreateCurrentSemester();

        List<SemesterDto> semesters = studentSemesters(student);
        List<Course> courses =
                courseService.findAllForStudent(student);
        List<Grade> grades = gradeService.findAllOfStudent(student);
        List<Feedback> feedbacks = feedbackService.findAllOfStudent(student);


        List<SemesterProgress> semestersProgress = semesters.stream()
                .map(it -> new SemesterProgress(it,
                        courseRegistrations(it, currentSemester, courses,
                                grades, feedbacks)))
                .collect(Collectors.toList());

        return new StudyProgress(currentSemester, semestersProgress);
    }

    private List<SemesterDto> studentSemesters(StudentEntity student) {
        SemesterDto firstSem = getFirstSemesterFor(student);
        if (firstSem != null) {
            return semesterService.findAllSince(getFirstSemesterFor(student));
        } else {
            return new ArrayList<SemesterDto>();
        }
    }

    /**
     * Get the first semester the student registered for.
     * <p>
     *
     *
     * @param student the student
     * @return the first semester of the student
     */
    private SemesterDto getFirstSemesterFor(StudentEntity student) {
        List<StudyPlanRegistration> registrations = student.getStudyplans();

        // TODO WTF does this code? Please clean up!
        // TODO refactor: this is not a valid way to treat Semesters
        SemesterDto min = new SemesterDto(Integer.MAX_VALUE,
                SemesterTypeEnum.SummerSemester);
        min.setId(Long.MAX_VALUE);

        for (StudyPlanRegistration spr : registrations) {
            if (min != null & spr != null && spr.getRegisteredSince() != null &&
                    min.getId() > spr.getRegisteredSince().getId()) {
                min = spr.getRegisteredSince().toDto();
            }
        }

        if (min.getId().longValue() == Long.MAX_VALUE) {
            return null;
        }
        return min;

    }

    private List<CuorseRegistration> courseRegistrations(SemesterDto semester,
            SemesterDto currentSemester, List<Course> courses,
            List<Grade> grades, List<Feedback> feedbacks) {
        return courses.stream()
                .filter(it -> it.getSemester().toDto().equals(semester))
                .map(it -> new CuorseRegistration(it,
                        courseRegistrationState(it, currentSemester, grades,
                                feedbacks), courseGrade(grades, it)))
                .collect(Collectors.toList());
    }

    private CourseRegistrationState courseRegistrationState(
            Course course, SemesterDto currentSemester,
            List<Grade> grades, List<Feedback> feedbacks) {
        Optional<Grade> grade =
                grades.stream().filter(it -> it.getCourse().equals(course))
                        .findFirst();
        Optional<Feedback> feedback =
                feedbacks.stream().filter(it -> it.getCourse().equals(course))
                        .findFirst();

        if (feedback.isPresent() && grade.isPresent() && grade.isPresent()) {
            return grade.get().getMark().isPositive() ?
                    CourseRegistrationState.complete_ok :
                    CourseRegistrationState.complete_not_ok;
        } else if (feedback.isPresent()) {
            return CourseRegistrationState.needs_grade;
        } else if (grade.isPresent()) {
            return CourseRegistrationState.needs_feedback;
        } else {
            return course.getSemester().toDto().equals(currentSemester) ?
                    CourseRegistrationState.in_progress :
                    CourseRegistrationState.needs_feedback;
        }
    }

    private Grade courseGrade(List<Grade> grades, Course course) {
        Optional<Grade> grade =
                grades.stream().filter(it -> it.getCourse().equals(course))
                        .findFirst();
        if (grade.isPresent()) {
            return grade.get();
        } else {
            return null;
        }
    }
}
