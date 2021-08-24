package at.ac.tuwien.inso.sqm.service.study_progress; //FIXME package naming
// convention?!

import at.ac.tuwien.inso.sqm.entity.StudentEntity;

public interface StudyProgressService {

    /**
     * Gibt den aktuellen Studienverlauf eines Studenten zurück.
     *
     * @param student the student
     * @return the study progress.
     */
    StudyProgress studyProgressFor(StudentEntity student);
}
