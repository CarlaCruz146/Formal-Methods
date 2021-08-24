package at.ac.tuwien.inso.sqm.service;

import at.ac.tuwien.inso.sqm.entity.Grade;
import at.ac.tuwien.inso.sqm.entity.StudyPlanEntity;
import at.ac.tuwien.inso.sqm.entity.Subject;
import at.ac.tuwien.inso.sqm.entity.SubjectForStudyPlanEntity;
import at.ac.tuwien.inso.sqm.entity.SubjectType;
import at.ac.tuwien.inso.sqm.entity.SubjectWithGrade;
import at.ac.tuwien.inso.sqm.exception.BusinessObjectNotFoundException;
import at.ac.tuwien.inso.sqm.repository.StudyPlanRepository;
import at.ac.tuwien.inso.sqm.repository.SubjectForStudyPlanRepository;
import at.ac.tuwien.inso.sqm.validator.StudyPlanValidator;
import at.ac.tuwien.inso.sqm.validator.ValidatorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class StudyPlanServiceImpl implements StudyPlanService {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(StudyPlanServiceImpl.class);

    private final StudyPlanRepository studyPlanRepository;
    private final SubjectForStudyPlanRepository subjectForStudyPlanRepository;
    private final SubjectIService subjectService;
    private final MessageSource messageSource;
    private final GradeIService gradeService;
    private final ValidatorFactory validatorFactory = new ValidatorFactory();
    private final StudyPlanValidator validator =
            validatorFactory.getStudyPlanValidator();

    @Autowired
    public StudyPlanServiceImpl(StudyPlanRepository studyPlanRepository,
            SubjectForStudyPlanRepository subjectForStudyPlanRepository,
            SubjectIService subjectService, GradeIService gradeService,
            MessageSource messageSource) {
        this.studyPlanRepository = studyPlanRepository;
        this.subjectForStudyPlanRepository = subjectForStudyPlanRepository;
        this.subjectService = subjectService;
        this.gradeService = gradeService;
        this.messageSource = messageSource;
    }

    @Override
    @Transactional
    public StudyPlanEntity create(StudyPlanEntity studyPlan) {
        LOGGER.info("creating stduyplan " + studyPlan.toString());
        validator.validateNewStudyPlan(studyPlan);
        return studyPlanRepository.save(studyPlan);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StudyPlanEntity> findAll() {
        LOGGER.info("getting all studyplans");
        Iterable<StudyPlanEntity> studyplans = studyPlanRepository.findAll();

        return StreamSupport.stream(studyplans.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public StudyPlanEntity findOne(Long id) {
        LOGGER.info("trying to find one stduyplan by id " + id);
        validator.validateStudyPlanId(id);
        StudyPlanEntity studyPlan = studyPlanRepository.findOne(id);
        if (studyPlan == null) {
            String msg = messageSource
                    .getMessage("error.studyplan.notfound", null,
                            LocaleContextHolder.getLocale());
            LOGGER.warn("no stduyplan was found by the given id " + id);
            throw new BusinessObjectNotFoundException(msg);
        }
        return studyPlan;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubjectForStudyPlanEntity> getSubjectsForStudyPlan(Long id) {
        validator.validateStudyPlanId(id);
        LOGGER.info("get subjects for studypolan by id " + id);
        return subjectForStudyPlanRepository
                .findByStudyPlanIdOrderBySemesterRecommendation(id);
    }

    /**
     * Gets all subjects for a certain study plan.
     * @param id the id of the study plan, should not be null and not <1
     * @return all subjects for the study plan.
     */
    @Override
    @Transactional(readOnly = true)
    public List<SubjectWithGrade> getSubjectsWithGradesForStudyPlan(Long id) {
        validator.validateStudyPlanId(id);
        LOGGER.info("getting subjects with grades for stduyplan width id " + id);
        List<SubjectForStudyPlanEntity> subjectsForStudyPlan =
                subjectForStudyPlanRepository
                        .findByStudyPlanIdOrderBySemesterRecommendation(id);
        List<Grade> grades = gradeService.getGradesForLoggedInStudent();
        List<SubjectWithGrade> subjectsWithGrades = new ArrayList<>();

        for (SubjectForStudyPlanEntity subjectForStudyPlan :
                subjectsForStudyPlan) {

            if (grades.isEmpty()) {
                // means there are no (more) grades at all
                if (subjectForStudyPlan.getMandatory()) {
                    subjectsWithGrades
                            .add(new SubjectWithGrade(subjectForStudyPlan,
                                    SubjectType.mandatory));
                } else {
                    subjectsWithGrades
                            .add(new SubjectWithGrade(subjectForStudyPlan,
                                    SubjectType.optional));
                }
            }

            //look for grades belonging to the actual subject
            for (int i = 0; i < grades.size(); i++) {
                Grade grade = grades.get(i);
                if (grade.getCourse().getSubject()
                        .equals(subjectForStudyPlan.getSubject())) {
                    // add to mandatory or optional subjects
                    if (subjectForStudyPlan.getMandatory()) {
                        subjectsWithGrades
                                .add(new SubjectWithGrade(subjectForStudyPlan,
                                        grade, SubjectType.mandatory));
                    } else {
                        subjectsWithGrades
                                .add(new SubjectWithGrade(subjectForStudyPlan,
                                        grade, SubjectType.optional));
                    }
                    grades.remove(grade);
                    break;
                } else if (i == grades.size() - 1) {
                    // means we reached the end of the list. there is no
                    // grade for this subject
                    if (subjectForStudyPlan.getMandatory()) {
                        subjectsWithGrades
                                .add(new SubjectWithGrade(subjectForStudyPlan,
                                        SubjectType.mandatory));
                    } else {
                        subjectsWithGrades
                                .add(new SubjectWithGrade(subjectForStudyPlan,
                                        SubjectType.optional));
                    }
                }
            }
        }

        //remaining unassigned grades are used as free choice subjects
        for (Grade grade : grades) {
            subjectsWithGrades
                    .add(new SubjectWithGrade(grade, SubjectType.FREE_CHOICE));
        }

        return subjectsWithGrades;
    }

    @Override
    @Transactional
    public void addSubjectToStudyPlan(
            SubjectForStudyPlanEntity subjectForStudyPlan) {

        validator.validateNewSubjectForStudyPlan(subjectForStudyPlan);
        LOGGER.info("adding subject to stduyplan with " + subjectForStudyPlan);

        subjectForStudyPlanRepository.save(subjectForStudyPlan);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Subject> getAvailableSubjectsForStudyPlan(Long id,
                                                          String query) {
        LOGGER.info("getting available subjects for stduyplan with id " + id +
                " and search word " + query);
        validator.validateStudyPlanId(id);
        List<SubjectForStudyPlanEntity> subjectsForStudyPlan =
                subjectForStudyPlanRepository
                        .findByStudyPlanIdOrderBySemesterRecommendation(id);
        List<Subject> subjectsOfStudyPlan = subjectsForStudyPlan.stream()
                .map(SubjectForStudyPlanEntity::getSubject)
                .collect(Collectors.toList());
        List<Subject> subjects = subjectService.searchForSubjects(query);

        return subjects.stream().filter(it -> !subjectsOfStudyPlan.contains(it))
                .collect(Collectors.toList());

    }

    @Override
    @Transactional
    public StudyPlanEntity disableStudyPlan(Long id) {

        LOGGER.info("disabling study plan with id " + id);
        validator.validateStudyPlanId(id);
        StudyPlanEntity studyPlan = findOne(id);
        if (studyPlan == null) {
            String msg = messageSource
                    .getMessage("error.studyplan.notfound", null,
                            LocaleContextHolder.getLocale());
            LOGGER.warn(msg);
            throw new BusinessObjectNotFoundException(msg);
        }
        studyPlan.setEnabled(false);
        studyPlanRepository.save(studyPlan);
        return studyPlan;
    }

    @Override
    @Transactional
    public void removeSubjectFromStudyPlan(StudyPlanEntity sp, Subject s) {
        validator.validateRemovingSubjectFromStudyPlan(sp, s);
        LOGGER.info("removing subject " + s.toString() + " from stduyplan " +
                sp.getName());

        List<SubjectForStudyPlanEntity> sfsp = subjectForStudyPlanRepository
                .findByStudyPlanIdOrderBySemesterRecommendation(sp.getId());
        for (SubjectForStudyPlanEntity each : sfsp) {
            if (each.getSubject().getId().equals(s.getId())) {
                subjectForStudyPlanRepository.delete(each);
                break;
            }
        }
    }
}
