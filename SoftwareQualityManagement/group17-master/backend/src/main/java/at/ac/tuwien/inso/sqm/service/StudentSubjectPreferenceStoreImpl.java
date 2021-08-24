package at.ac.tuwien.inso.sqm.service;

import at.ac.tuwien.inso.sqm.entity.Feedback;
import at.ac.tuwien.inso.sqm.entity.Course;
import at.ac.tuwien.inso.sqm.entity.StudentEntity;
import at.ac.tuwien.inso.sqm.repository.StudentSubjectPreferenceRepository;
import at.ac.tuwien.inso.sqm.service.student_subject_prefs.StudentSubjectPreference;
import at.ac.tuwien.inso.sqm.service.student_subject_prefs.StudentSubjectPreferenceStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentSubjectPreferenceStoreImpl
        implements StudentSubjectPreferenceStore {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(StudentSubjectPreferenceStoreImpl.class);

    private static final Double REGISTER_PREF_VALUE = 3.0;
    private static final Double LIKE_PREF_VALUE = 5.0;
    private static final Double UNLIKE_PREF_VALUE = 1.0;

    @Autowired
    private StudentSubjectPreferenceRepository preferenceRepository;

    @Override
    public void studentRegisteredCourse(StudentEntity student,
            Course course) {
        StudentSubjectPreference preference =
                new StudentSubjectPreference(student.getId(),
                        course.getSubject().getId(), REGISTER_PREF_VALUE);

        LOGGER.debug(
                "Storing student subject preference due to course " +
                        "registration: " +
                        preference);

        preferenceRepository.insert(preference);
    }

    @Override
    public void studentUnregisteredCourse(StudentEntity student,
            Course course) {
        LOGGER.debug(
                "Remove student subject preference due to course " +
                        "unregistration: " +
                        student + ", " + course);

        preferenceRepository.deleteByStudentIdAndSubjectId(student.getId(),
                course.getSubject().getId());
    }

    @Override
    public void studentGaveCourseFeedback(StudentEntity student,
            Feedback feedback) {
        StudentSubjectPreference preference = preferenceRepository
                .findByStudentIdAndSubjectId(student.getId(),
                        feedback.getCourse().getSubject().getId());

        preference.setPreferenceValue(
                feedback.getType() == Feedback.Type.LIKE ? LIKE_PREF_VALUE :
                        UNLIKE_PREF_VALUE);

        LOGGER.debug(
                "Updating student subject preference due to course feedback: " +
                        preference);

        preferenceRepository.save(preference);
    }
}
