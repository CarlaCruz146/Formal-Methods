package at.ac.tuwien.inso.sqm.service;

import at.ac.tuwien.inso.sqm.entity.Course;
import at.ac.tuwien.inso.sqm.entity.StudyPlanEntity;
import at.ac.tuwien.inso.sqm.entity.StudentEntity;
import at.ac.tuwien.inso.sqm.entity.StudyPlanRegistration;
import at.ac.tuwien.inso.sqm.entity.Subject;
import at.ac.tuwien.inso.sqm.repository.SemestreRepository;
import at.ac.tuwien.inso.sqm.repository.SubjectForStudyPlanRepository;
import at.ac.tuwien.inso.sqm.service.course_recommendation.CourseRelevanceFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Component
public class SemesterRecommendationCourseRelevanceFilter
        implements CourseRelevanceFilter {

    @Autowired
    private SemestreRepository semesterRepository;

    @Autowired
    private SubjectForStudyPlanRepository subjectForStudyPlanRepository;

    /**
     * Filters the courses according to a certain student.
     * @param courses the courses to be filtered.
     * @param student the student for whom the relevance of the courses is
     *                relatively calculated.
     * @return The filtered list of courses.
     */
    @Override
    public List<Course> filter(List<Course> courses,
                               StudentEntity student) {
        Map<StudyPlanEntity, Integer> studentSemesters =
                student.getStudyplans().stream().collect(
                        toMap(StudyPlanRegistration::getStudyplan,
                                it -> semesterRepository
                                        .findAllSince(it.getRegisteredSince())
                                        .size()));

        List<Subject> subjects =
                courses.stream().map(Course::getSubject)
                        .collect(Collectors.toList());
        Map<StudyPlanEntity, Map<Subject, Integer>>
                courseSemesterRecommendations = student.getStudyplans().stream()
                .map(StudyPlanRegistration::getStudyplan)
                .collect(toMap(identity(), it -> {
                    Map<Subject, Integer> subjectToSemesterRecommendation =
                            new HashMap<>();
                    subjectForStudyPlanRepository
                            .findBySubjectInAndStudyPlan(subjects, it)
                            .forEach(subjectForStudyPlan -> {
                                subjectToSemesterRecommendation
                                        .put(subjectForStudyPlan.getSubject(),
                                                subjectForStudyPlan
                                                        .getSemesterRecommendation());
                            });
                    return subjectToSemesterRecommendation;
                }));

        return courses.stream()
                .filter(course -> studentSemesters.keySet().stream()
                        .anyMatch(studyPlan -> {
                            Integer studentSemester =
                                    studentSemesters.get(studyPlan);
                            Integer courseSemester =
                                    courseSemesterRecommendations.get(studyPlan)
                                            .get(course.getSubject());

                            return courseSemester == null ||
                                    studentSemester >= courseSemester;
                        })).collect(Collectors.toList());
    }
}
