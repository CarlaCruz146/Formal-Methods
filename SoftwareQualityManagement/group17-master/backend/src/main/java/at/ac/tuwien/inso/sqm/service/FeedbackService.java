package at.ac.tuwien.inso.sqm.service;

import at.ac.tuwien.inso.sqm.entity.Feedback;
import at.ac.tuwien.inso.sqm.entity.Course;
import at.ac.tuwien.inso.sqm.entity.StudentEntity;
import at.ac.tuwien.inso.sqm.exception.ActionNotAllowedException;
import at.ac.tuwien.inso.sqm.repository.CourseRepository;
import at.ac.tuwien.inso.sqm.repository.FeedbackRepository;
import at.ac.tuwien.inso.sqm.service.student_subject_prefs.StudentSubjectPreferenceStore;
import at.ac.tuwien.inso.sqm.validator.FeedbackValidator;
import at.ac.tuwien.inso.sqm.validator.ValidatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FeedbackService implements FeedbackIService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(FeedbackService.class);
    private final ValidatorFactory validatorFactory = new ValidatorFactory();
    private final FeedbackValidator validator =
            validatorFactory.getFeedbackValidator();
    @Autowired
    private StudentSubjectPreferenceStore studentSubjectPreferenceStore;
    @Autowired
    private FeedbackRepository feedbackRepository;
    @Autowired
    private CourseRepository courseRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Feedback> findAllOfStudent(StudentEntity student) {
        return feedbackRepository.findAllOfStudent(student);
    }

    @Override
    @Transactional
    public Feedback save(Feedback feedback) {
        validator.validateNewFeedback(feedback);
        LOGGER.info("Creating feedback from student {} for course {}: {} - {}",
                feedback.getStudent().getId(), feedback.getCourse().getId(),
                feedback.getType(), feedback.getSuggestions());

        guardSingleFeedback(feedback);
        guardStudentRegisteredForCourse(feedback.getStudent(),
                feedback.getCourse());

        studentSubjectPreferenceStore
                .studentGaveCourseFeedback(feedback.getStudent(), feedback);

        return feedbackRepository.save(feedback);
    }

    @Override
    public List<Feedback> findFeedbackForCourse(Long id) {
        LOGGER.info("finding feedback for course " + id);
        validator.validateCourseId(id);
        return feedbackRepository.findByCourseId(id);
    }

    //    @Override
    //  public boolean exists(Feedback feedback) {
    //        log.info("check if feedback for student already exists" +
    //        feedback.getStudent().getId() + ", " + feedback.getCourse()
    //        .getId());
    //        return feedbackRepository.exists(feedback);
    //    }

    private void guardSingleFeedback(Feedback feedback) {
        LOGGER.info("Guarding single feedback, if no error log line follows its fine.");

        if (feedbackRepository.exists(feedback)) {
            LOGGER.error("Giving feedback multiple times for the same course is not allowed");
            throw new ActionNotAllowedException("Giving feedback multiple times for the same course is not allowed");
        }
    }

    private void guardStudentRegisteredForCourse(StudentEntity student,
            Course course) {
        LOGGER.info("guarding student is registered for course already");
        if (!courseRepository.existsCourseRegistration(student, course)) {
            LOGGER.error(
                    "StudentEntity tried to give feedback for course he is " +
                            "not registered for");
            throw new ActionNotAllowedException(
                    "StudentEntity tried to give feedback for course he is " +
                            "not registered for");
        }
    }
}
