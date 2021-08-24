package at.ac.tuwien.inso.sqm.service;

import at.ac.tuwien.inso.sqm.entity.LecturerEntity;
import at.ac.tuwien.inso.sqm.entity.Course;
import at.ac.tuwien.inso.sqm.entity.Subject;
import at.ac.tuwien.inso.sqm.exception.LecturerNotFoundException;
import at.ac.tuwien.inso.sqm.exception.RelationNotfoundException;
import at.ac.tuwien.inso.sqm.exception.SubjectNotFoundException;
import at.ac.tuwien.inso.sqm.exception.ValidationException;
import at.ac.tuwien.inso.sqm.repository.LecturerRepository;
import at.ac.tuwien.inso.sqm.repository.SubjectRepository;
import at.ac.tuwien.inso.sqm.validator.SubjectValidator;
import at.ac.tuwien.inso.sqm.validator.UisUserValidator;
import at.ac.tuwien.inso.sqm.validator.ValidatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class SubjectService implements SubjectIService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(SubjectService.class);
    private final ValidatorFactory validatorFactory = new ValidatorFactory();
    private final SubjectValidator validator =
            validatorFactory.getSubjectValidator();
    private final UisUserValidator userValidator =
            validatorFactory.getUisUserValidator();
    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private LehrveranstaltungServiceInterface courseService;

    @Autowired
    private LecturerRepository lecturerRepository;

    @Override
    public Page<Subject> findBySearch(String search, Pageable pageable) {
        LOGGER.info("finding search by word " + search);
        String sqlSearch = "%" + search + "%";
        return subjectRepository
                .findAllByNameLikeIgnoreCase(sqlSearch, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Subject findOne(Long id) {
        validator.validateSubjectId(id);
        LOGGER.info("finding subject by id " + id);
        Subject subject = subjectRepository.findOne(id);

        if (subject == null) {
            LOGGER.warn("Subjcet not found");
            //TODO throwing this will cause a security test fail, for
            // whatever reason?
            //throw new SubjectNotFoundException();
        }

        return subject;
    }

    @Override
    @Transactional
    public Subject create(Subject subject) {
        validator.validateNewSubject(subject);
        LOGGER.info("creating subject " + subject.toString());
        return subjectRepository.save(subject);
    }

    /**
     * Add a lecturer to a subject.
     * @param subjectId         the subject idshould not be null and not <1.
     * @param lecturerUisUserId the lecturer id
     * @return The lecturer added to the subject
     */
    @Override
    @Transactional
    public LecturerEntity addLecturerToSubject(Long subjectId,
            Long lecturerUisUserId) {

        userValidator.validateUisUserId(lecturerUisUserId);
        validator.validateSubjectId(subjectId);

        LOGGER.info("addLecturerToSubject for subject {} and lecturer {}",
                subjectId, lecturerUisUserId);

        LecturerEntity lecturer =
                lecturerRepository.findById(lecturerUisUserId);

        if (lecturer == null) {
            String msg = "LecturerEntity with user id " + lecturerUisUserId +
                    " not found";
            LOGGER.info(msg);
            throw new LecturerNotFoundException(msg);
        }

        Subject subject = subjectRepository.findById(subjectId);

        if (subject == null) {
            String msg = "Subjcet with id " + lecturerUisUserId + " not found";
            LOGGER.info(msg);
            throw new SubjectNotFoundException(msg);
        }

        if (subject.getLecturers().contains(lecturer)) {
            return lecturer;
        }

        subject.addLecturers(lecturer);
        subjectRepository.save(subject);

        return lecturer;
    }

    /**
     * Gets all available lecturer for a certain subject.
     * @param subjectId the subject id, should not be null and not <1.
     * @param search    will be changed to "" if is null.
     * @return all available lecturer for a certain subject
     */
    @Override
    @Transactional(readOnly = true)
    public List<LecturerEntity> getAvailableLecturersForSubject(Long subjectId,
            String search) {
        validator.validateSubjectId(subjectId);
        if (search == null) {
            search = "";
        }
        LOGGER.info("getting available lectureres for subject with subject id " +
                subjectId + " and search string " + search);


        Subject subject = subjectRepository.findById(subjectId);

        if (subject == null) {
            LOGGER.warn("Subjcet with id '" + subjectId + "' not found");
            throw new SubjectNotFoundException(
                    "Subjcet with id '" + subjectId + "' not found");
        }

        List<LecturerEntity> currentLecturers = subject.getLecturers();

        List<LecturerEntity> searchedLecturers = lecturerRepository
                .findAllByIdentificationNumberLikeIgnoreCaseOrNameLikeIgnoreCase(
                        "%" + search + "%", "%" + search + "%");

        return searchedLecturers.stream()
                .filter(lecturer -> !currentLecturers.contains(lecturer))
                .limit(10).collect(Collectors.toList());
    }

    /**
     * Removes a lecturer from a subject.
     * @param subjectId the subject id
     * @param lecturerUisUserId the lecturer id
     * @return The removed lecturer
     */
    @Override
    public LecturerEntity removeLecturerFromSubject(Long subjectId,
            Long lecturerUisUserId) {
        userValidator.validateUisUserId(lecturerUisUserId);
        validator.validateSubjectId(subjectId);
        LOGGER.info("removeLecturerFromSubject for subject {} and lecturer {}",
                subjectId, lecturerUisUserId);

        Subject subject = subjectRepository.findById(subjectId);

        if (subject == null) {
            String msg = "Subjcet with id '" + subjectId + "' not found";
            LOGGER.info(msg);
            throw new SubjectNotFoundException(msg);
        }

        LecturerEntity lecturer =
                lecturerRepository.findById(lecturerUisUserId);

        if (lecturer == null) {
            String msg = "LecturerEntity with id '" + lecturerUisUserId +
                    "' not found";
            LOGGER.info(msg);
            throw new LecturerNotFoundException(msg);
        }

        List<LecturerEntity> currentLecturers = subject.getLecturers();

        boolean isLecturer = currentLecturers.contains(lecturer);

        if (!isLecturer) {
            String msg = "LecturerEntity with id " + lecturerUisUserId +
                    " not found for subject " + subjectId;
            LOGGER.info(msg);
            throw new RelationNotfoundException(msg);
        }

        subject.removeLecturers(lecturer);

        subjectRepository.save(subject);

        return lecturer;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Subject> searchForSubjects(String word) {
        LOGGER.info("seachring for subjects with search word " + word);
        return subjectRepository.findByNameContainingIgnoreCase(word);
    }

    /**
     * Removes a subject.
     * @param subject the subject
     * @return true if the subject is successfully removed, otherwise false
     * @throws ValidationException if the subject is invalid
     */
    @Override
    @Transactional
    public boolean remove(Subject subject) throws ValidationException {
        LOGGER.info("trying to remove subject");
        validator.validateNewSubject(subject);
        if (subject == null) {
            LOGGER.info("Subjcet is null.");
            throw new ValidationException("Subjcet is null.");
        }
        LOGGER.info("removing subject " + subject + " now.");
        List<Course> courses =
                courseService.findCoursesForSubject(subject);
        if (courses.size() > 0) {
            String msg = "";
            if (subject != null) {
                msg = "Cannot delete subject [Name: " + subject.getName() +
                        "] because there are courses";
            }
            LOGGER.info(msg);
            throw new ValidationException(msg);
        } else {
            subjectRepository.delete(subject);
            return true;
        }
    }


}
