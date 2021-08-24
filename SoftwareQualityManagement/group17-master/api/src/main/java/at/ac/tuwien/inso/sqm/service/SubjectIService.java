package at.ac.tuwien.inso.sqm.service;

import at.ac.tuwien.inso.sqm.entity.LecturerEntity;
import at.ac.tuwien.inso.sqm.entity.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface SubjectIService {

    /**
     * returns all Subjects that match the search text.
     * <p>
     * only works if user is authenticated.
     *
     * @param search the search string
     * @param pageable the pageable object
     * @return the pages with subjects
     */
    @PreAuthorize("isAuthenticated()")
    Page<Subject> findBySearch(String search, Pageable pageable);

    /**
     * returns a Subjcet by id.
     *
     * @param id should not be null and not <1
     * @return the subject
     */
    @PreAuthorize("isAuthenticated()")
    Subject findOne(Long id);

    /**
     * returns a list of subjects that fit the search word. The filter has to
     * be findByNameContainingIgnoreCase
     *
     * @param word the search word
     * @return a list of subjects that fit the search word
     */
    @PreAuthorize("isAuthenticated()")
    List<Subject> searchForSubjects(String word);

    /**
     * creates a new subject in the database. returns the object with a
     * filled id
     *
     * @param subject should not be null, should have a name that is not
     *                empty, and a ects value that is not emtpy and bigger
     *                than 0.
     * @return the created subject
     */
    @PreAuthorize("hasRole('ADMIN')")
    Subject create(Subject subject);

    /**
     * adds a lecturer to be responsible for a subject. if the lecturer
     * already was responsible, the lectuerer object that was responsible in
     * the past will be returned. otherwise the lecturer that was now added
     * will be returned.
     * may throw a LecturerNotFoundException or a SubjectNotFoundException
     *
     * @param subjectId         should not be null and not <1.
     * @param lecturerUisUserId the lecturer id
     * @return if the lecturer
     * already was responsible, the lecturer object that was responsible in
     * the past will be returned. otherwise the lecturer that was now added
     * will be returned.
     */
    @PreAuthorize("hasRole('ADMIN')")
    LecturerEntity addLecturerToSubject(Long subjectId, Long lecturerUisUserId);

    /**
     * returns a list of lectuerers that are responsible for a subject and
     * match the search string. search startegy should be string.contains.
     * <p>
     * may throw SubjectNotFoundException if no subject was found
     *
     * @param subjectId should not be null and not <1.
     * @param search    will be changed to "" if is null.
     * @return a maximum of 10 lecturers
     */
    @PreAuthorize("hasRole('ADMIN')")
    List<LecturerEntity> getAvailableLecturersForSubject(Long subjectId,
            String search);

    /**
     * Removes a lecturer from a subject.
     * <p>
     * only works if the user is ADMIN
     *
     * @param subjectId the subject id
     * @param lecturerUisUserId the lecturer id
     * @return the removed lecturer
     */
    @PreAuthorize("hasRole('ADMIN')")
    LecturerEntity removeLecturerFromSubject(Long subjectId,
            Long lecturerUisUserId);

    /**
     * removes a subject from the system. is only possible if there exist no
     * courses for this subject.
     * if courses exist, a validation exception will be thrown.
     * <p>
     * only works if the user is ADMIN
     *
     * @param subject the subject
     * @return the removed subject
     */
    @PreAuthorize("hasRole('ADMIN')")
    boolean remove(Subject subject);
}
