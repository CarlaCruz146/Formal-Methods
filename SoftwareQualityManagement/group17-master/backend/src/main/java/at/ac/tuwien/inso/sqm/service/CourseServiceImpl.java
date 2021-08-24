package at.ac.tuwien.inso.sqm.service;

import at.ac.tuwien.inso.sqm.dto.AddCourseForm;
import at.ac.tuwien.inso.sqm.dto.CourseDetailsForStudent;
import at.ac.tuwien.inso.sqm.dto.SemesterDto;
import at.ac.tuwien.inso.sqm.entity.Grade;
import at.ac.tuwien.inso.sqm.entity.LecturerEntity;
import at.ac.tuwien.inso.sqm.entity.Course;
import at.ac.tuwien.inso.sqm.entity.Role;
import at.ac.tuwien.inso.sqm.entity.Semester;
import at.ac.tuwien.inso.sqm.entity.StudentEntity;
import at.ac.tuwien.inso.sqm.entity.Subject;
import at.ac.tuwien.inso.sqm.entity.SubjectForStudyPlanEntity;
import at.ac.tuwien.inso.sqm.entity.Tag;
import at.ac.tuwien.inso.sqm.entity.UserAccountEntity;
import at.ac.tuwien.inso.sqm.exception.BusinessObjectNotFoundException;
import at.ac.tuwien.inso.sqm.exception.ValidationException;
import at.ac.tuwien.inso.sqm.repository.CourseRepository;
import at.ac.tuwien.inso.sqm.repository.StduentRepository;
import at.ac.tuwien.inso.sqm.repository.SubjectForStudyPlanRepository;
import at.ac.tuwien.inso.sqm.repository.SubjectRepository;
import at.ac.tuwien.inso.sqm.service.student_subject_prefs.StudentSubjectPreferenceStore;
import at.ac.tuwien.inso.sqm.validator.CourseValidator;
import at.ac.tuwien.inso.sqm.validator.ValidatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements LehrveranstaltungServiceInterface {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(CourseServiceImpl.class);
    private final ValidatorFactory validatorFactory = new ValidatorFactory();
    private final CourseValidator validator =
            validatorFactory.getCourseValidator();

    @Autowired
    private SemesterServiceInterface semesterService;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private StduentRepository stduentRepository;

    @Autowired
    private UserAccountService userAccountService;

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private GradeIService gradeService;

    @Autowired
    private TgaService tgaService;

    @Autowired
    private SubjectForStudyPlanRepository subjectForStudyPlanRepository;

    @Autowired
    private StudentSubjectPreferenceStore studentSubjectPreferenceStore;

    @Override
    @Transactional(readOnly = true)
    public Page<Course> findCourseForCurrentSemesterWithName(
            @NotNull String name, Pageable pageable) {
        LOGGER.info("try to find course for current semester with semestername: " +
                name + "and pageable " + pageable);
        Semester semester =
                semesterService.getOrCreateCurrentSemester().toEntity();
        return courseRepository
                .findAllBySemesterAndSubjectNameLikeIgnoreCase(semester,
                        "%" + name + "%", pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Course> findCoursesForCurrentSemesterForLecturer(
            LecturerEntity lecturer) {
        LOGGER.info(
                "try finding courses for current semester for lecturer with " +
                        "id " +
                        lecturer.getId());
        Semester semester =
                semesterService.getOrCreateCurrentSemester().toEntity();
        Iterable<Subject> subjectsForLecturer =
                subjectRepository.findByLecturersId(lecturer.getId());
        List<Course> courses = new ArrayList<>();
        subjectsForLecturer.forEach(subject -> courses.addAll(courseRepository
                .findAllBySemesterAndSubject(semester, subject)));
        return courses;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Course> findCoursesForSubject(Subject subject) {
        LOGGER.info("try finding course for subject with id " + subject.getId());
        return courseRepository.findAllBySubject(subject);
    }

    @Override
    public List<Course> findCoursesForSubjectAndCurrentSemester(
            Subject subject) {
        List<Course> result = courseRepository
                .findAllBySemesterAndSubject(
                        semesterService.getCurrentSemester().toEntity(),
                        subject);
        return result;
    }

    @Override
    @Transactional
    public void dismissCourse(StudentEntity student, Long courseId) {
        Course course = findeLehrveranstaltung(courseId);
        student.addDismissedCourse(course);
    }

    /**
     * Saves a course.
     * @param form the form
     * @return the saved course
     */
    @Override
    @Transactional
    public Course saveCourse(AddCourseForm form) {
        LOGGER.info("try saving course");
        Course course = form.getCourse();
        validator.validateNewCourse(course);

        UserAccountEntity u = userAccountService.getCurrentLoggedInUser();

        isLecturerAllowedToChangeCourse(course, u);

        LOGGER.info("try saving course " + course.toString());

        ArrayList<Tag> currentTagsOfCourse =
                new ArrayList<>(form.getCourse().getTags());

        for (String tag : form.getTags()) {
            Tag newTag = tgaService.findByName(tag);

            // tag doesn't exist, so create a new one.
            if (newTag == null) {
                course.addTags(new Tag(tag));
            } else if (!course.getTags().contains(newTag)) { // tag exists, but not in this course
                course.addTags(newTag);
            } else { // tag already exists for this course
                currentTagsOfCourse.remove(newTag);
            }
        }

        // the remaining tags are to be removed
        course.removeTags(currentTagsOfCourse);

        if (!(course.getStudentLimits() > 0)) {
            course.setStudentLimits(1);
        }
        return courseRepository.save(course);
    }

    private void isLecturerAllowedToChangeCourse(Course c,
                                                 UserAccountEntity u) {
        if (c == null || u == null) {
            String msg = messageSource
                    .getMessage("lecturer.course.edit.error.notallowed", null,
                            LocaleContextHolder.getLocale());
            throw new ValidationException(msg);
        }

        if (u.hasRole(Role.ADMIN)) {
            LOGGER.info("user is admin, therefore course modification is allowed");
            return;
        }

        for (LecturerEntity l : c.getSubject().getLecturers()) {
            if (l.getAccount().equals(u)) {
                LOGGER.info(
                        "found equal lecturers, course modification is " +
                                "allowed");
                return;
            }
        }
        LOGGER.warn(
                "suspisious try to modify course. user is not admin or does " +
                        "not own the subject for this course");
        String msg = messageSource
                .getMessage("lecturer.course.edit.error.notallowed", null,
                        LocaleContextHolder.getLocale());
        throw new ValidationException(msg);
    }

    /**
     * Searches a course by its id.
     * @param id Die id sollte nicht null sein und nicht <1
     * @return the course
     */
    @Override
    @Transactional(readOnly = true)
    public Course findeLehrveranstaltung(Long id) {
        LOGGER.info("try finding course with id " + id);
        Course course = courseRepository.findOne(id);
        if (course == null) {
            LOGGER.warn("Lehrveranstaltung with id " + id + " does not exist");
            throw new BusinessObjectNotFoundException(
                    "Lehrveranstaltung with id " + id + " does not exist");
        }
        return course;
    }

    /**
     * Removes a course.
     * @param courseId the course id
     * @return if the course is removed
     * @throws ValidationException if the course id is invalid
     */
    @Override
    @Transactional
    public boolean remove(Long courseId) throws ValidationException {
        LOGGER.info("try removing  course with id " + courseId);
        validator.validateCourseId(courseId); // throws ValidationException
        Course course = courseRepository.findOne(courseId);

        if (course == null) {
            String msg =
                    "Lehrveranstaltung can not be deleted because there is no" +
                            " couse found with id " +
                            courseId;
            LOGGER.warn(msg);
            throw new BusinessObjectNotFoundException(msg);
        }

        isLecturerAllowedToChangeCourse(course,
                userAccountService.getCurrentLoggedInUser());


        List<Grade> grades = gradeService.findAllByCourseId(courseId);

        if (grades != null && grades.size() > 0) {
            String msg = "There are grades for course [id:" + courseId +
                    "], therefore integrationtest can not be removed.";
            LOGGER.warn(msg);
            throw new ValidationException(msg);
        }

        if (course.getStudents().size() > 0) {
            String msg = "There are students for course [id:" + courseId +
                    "], therefore integrationtest can not be removed.";
            LOGGER.warn(msg);
            throw new ValidationException(msg);
        }

        LOGGER.info("successfully validated course removal. removing now!");
        courseRepository.delete(course);
        return true;
    }


    @Override
    @Transactional
    public boolean studentZurLehrveranstaltungAnmelden(
            Course course) {
        validator.validateCourse(course);
        validator.validateCourseId(course.getId());
        StudentEntity student = stduentRepository.findByUsername(
                userAccountService.getCurrentLoggedInUser().getUsername());

        LOGGER.info("try registering currently logged in student with id " +
                student.getId() + " for course with id " +
                course.getId());
        if (course.getStudentLimits() <=
                course.getStudents().size()) {
            return false;
        } else if (course.getStudents().contains(student)) {
            return true;
        } else {
            course.addStudents(student);
            courseRepository.save(course);
            studentSubjectPreferenceStore
                    .studentRegisteredCourse(student, course);
            return true;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Course> findAllForStudent(StudentEntity student) {
        LOGGER.info("finding all courses for student with id " + student.getId());
        return courseRepository.findAllForStudent(student);
    }

    /**
     * Deregister a student from a course.
     * @param student              sollte nicht null sein
     * @param lehrveranstaltungsID sollte nicht null sein und nicht <1
     * @return the course.
     */
    @Override
    @Transactional
    public Course studentVonLehrveranstaltungAbmelden(
            StudentEntity student, Long lehrveranstaltungsID) {
        LOGGER.info("Unregistering student with id {} from course with id {}",
                student.getId(), lehrveranstaltungsID);
        validator.validateCourseId(lehrveranstaltungsID);

        Course course =
                courseRepository.findOne(lehrveranstaltungsID);
        if (course == null) {
            LOGGER.warn(
                    "Lehrveranstaltung with id {} not found. Nothing to " +
                            "unregister",
                    lehrveranstaltungsID);
            throw new BusinessObjectNotFoundException();
        }

        UserAccountEntity currentLoggedInUser =
                userAccountService.getCurrentLoggedInUser();
        //students should only be able to unregister themselves
        if (currentLoggedInUser.hasRole(Role.STUDENT)) {
            if (!student.getAccount().getUsername()
                    .equals(userAccountService.getCurrentLoggedInUser()
                            .getUsername())) {
                LOGGER.warn(
                        "student with id {} and username {} tried to " +
                                "unregister another one with id {} and " +
                                "username {}",
                        userAccountService.getCurrentLoggedInUser().getId(),
                        userAccountService.getCurrentLoggedInUser()
                                .getUsername(), student.getId(),
                        student.getAccount().getUsername());
                String msg = messageSource
                        .getMessage("lecturer.course.edit.error.notallowed",
                                null, LocaleContextHolder.getLocale());
                throw new ValidationException(msg);
            }
        }

        //Lectureres should only be able to remove students from their own
        // courses
        if (currentLoggedInUser != null &&
                currentLoggedInUser.hasRole(Role.LECTURER)) {
            isLecturerAllowedToChangeCourse(course,
                    userAccountService.getCurrentLoggedInUser());
        }


        course.removeStudents(student);
        studentSubjectPreferenceStore
                .studentUnregisteredCourse(student, course);
        return course;
    }

    private boolean checkRole(Role role) {
        return userAccountService.getCurrentLoggedInUser().hasRole(role);
    }

    @Override
    public CourseDetailsForStudent courseDetailsFor(StudentEntity student,
                                                    Long courseId) {
        validator.validateCourseId(courseId);
        validator.validateStudent(student);
        LOGGER.info("reading course details for student with id " +
                student.getId() + " from course with id " + courseId);
        Course course = findeLehrveranstaltung(courseId);
        if (course == null) {
            LOGGER.warn(
                    "Lehrveranstaltung with id {} not found. Nothing to " +
                            "unregister",
                    courseId);
            throw new BusinessObjectNotFoundException();
        }


        return new CourseDetailsForStudent(course)
                .setCanEnroll(canEnrollToCourse(student, course)).setStudyplans(
                        subjectForStudyPlanRepository
                                .findBySubject(course.getSubject()));
    }

    @Override
    public List<SubjectForStudyPlanEntity> getSubjectForStudyPlanList(
            Course course) {
        validator.validateCourse(course);
        return subjectForStudyPlanRepository.findBySubject(course.getSubject());
    }

    private boolean canEnrollToCourse(StudentEntity student,
            Course course) {
        validator.validateCourse(course);
        validator.validateStudent(student);
        return course.getSemester().toDto()
                .equals(semesterService.getOrCreateCurrentSemester()) &&
                !courseRepository.existsCourseRegistration(student, course);
    }

    @Override
    public List<Course> findAllCoursesForCurrentSemester() {
        SemesterDto semester = semesterService.getOrCreateCurrentSemester();
        return courseRepository.findAllBySemester(semester.toEntity());
    }
}
