package at.ac.tuwien.inso.sqm.service;

import at.ac.tuwien.inso.sqm.dto.SemesterDto;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface SemesterServiceInterface {

    /**
     * Creates a new semester.
     * <p>
     * User has to be authenticated as admin.
     *
     * @param semester which has to be created.
     * @return the created semester
     */
    @PreAuthorize("hasRole('ADMIN')")
    SemesterDto create(SemesterDto semester);

    /**
     * Returns the actual semester - Use only in Integrationtests when testing!
     * <p>
     * Always use getOrCreateCurrentSemester:
     * An Integrationtest chekcs if a new semester can be started
     * and starts integrationtest automatically.
     * @return the current semester as SemsterDTO
     */
    @PreAuthorize("isAuthenticated()")
    SemesterDto getCurrentSemester();

    /**
     * Returns the actual semester.
     * <p>
     * If a new semester can be started it will be started and returned.
     * @return the created SemesterDTO
     */
    @PreAuthorize("isAuthenticated()")
    SemesterDto getOrCreateCurrentSemester();

    /**
     * Returns all semesters as SemesterDto sorted by ID.
     * <p>
     * User has to be authenticated.
     *
     * @return all semesters as SemesterDto
     */
    @PreAuthorize("isAuthenticated()")
    List<SemesterDto> findAll();

    /**
     * Returns all semesters since the given one.
     * <p>
     * User has to be authenticated.
     *
     * @param semester the semester
     * @return all semesters since the given one.
     */
    @PreAuthorize("isAuthenticated()")
    List<SemesterDto> findAllSince(SemesterDto semester);
}
