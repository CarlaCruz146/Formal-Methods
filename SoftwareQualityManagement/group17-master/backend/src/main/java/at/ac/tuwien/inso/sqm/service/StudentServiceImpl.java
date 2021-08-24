package at.ac.tuwien.inso.sqm.service;

import at.ac.tuwien.inso.sqm.dto.SemesterDto;
import at.ac.tuwien.inso.sqm.entity.Semester;
import at.ac.tuwien.inso.sqm.entity.StudyPlanEntity;
import at.ac.tuwien.inso.sqm.entity.StudentEntity;
import at.ac.tuwien.inso.sqm.entity.StudyPlanRegistration;
import at.ac.tuwien.inso.sqm.entity.UserAccountEntity;
import at.ac.tuwien.inso.sqm.repository.StduentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentServiceInterface {


    private static final Logger LOGGER =
            LoggerFactory.getLogger(StudentServiceImpl.class);

    @Autowired
    private StduentRepository stduentRepository;

    @Autowired
    private SemesterServiceInterface semesterService;

    @Override
    @Transactional
    public StudentEntity findOne(Long id) {
        LOGGER.info("finding student by id " + id);
        return stduentRepository.findOne(id);
    }

    @Override
    public StudentEntity findOne(UserAccountEntity account) {
        LOGGER.info("finding student by account " + account.toString());
        return stduentRepository.findByAccount(account);
    }

    @Override
    public StudentEntity findByUsername(String username) {
        LOGGER.info("finding student by username " + username);
        return stduentRepository.findByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudyPlanRegistration> findStudyPlanRegistrationsFor(
            StudentEntity student) {
        LOGGER.info("finding studyplanregistrations for student  " +
                student.toString());
        return student.getStudyplans();
    }

    @Override
    @Transactional
    public void registerStudentToStudyPlan(StudentEntity student,
            StudyPlanEntity studyPlan) {
        LOGGER.info("for current semester, registering student " +
                student.toString() + " to StduyPlanEntity " +
                studyPlan.toString());

        SemesterDto semester = semesterService.getOrCreateCurrentSemester();

        registerStudentToStudyPlan(student, studyPlan, semester);
    }

    @Override
    @Transactional
    public void registerStudentToStudyPlan(StudentEntity student, StudyPlanEntity studyPlan,
                                                                SemesterDto currentSemesterDto) {
        LOGGER.info(
                "for semester " + currentSemesterDto + " registering student " +
                        student.toString() + " to StduyPlanEntity " +
                        studyPlan.toString());

        Semester currentSemester = currentSemesterDto.toEntity();

        StudyPlanRegistration studyPlanRegistration =
                new StudyPlanRegistration(studyPlan, currentSemester);

        student.addStudyplans(studyPlanRegistration);

        stduentRepository.save(student);
    }
}
