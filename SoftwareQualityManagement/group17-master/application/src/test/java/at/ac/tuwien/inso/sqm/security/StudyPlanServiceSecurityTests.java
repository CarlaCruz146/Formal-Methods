package at.ac.tuwien.inso.sqm.security;

import at.ac.tuwien.inso.sqm.entity.EctsDistributionEntity;
import at.ac.tuwien.inso.sqm.entity.StudyPlanEntity;
import at.ac.tuwien.inso.sqm.entity.Subject;
import at.ac.tuwien.inso.sqm.entity.SubjectForStudyPlanEntity;
import at.ac.tuwien.inso.sqm.exception.BusinessObjectNotFoundException;
import at.ac.tuwien.inso.sqm.exception.ValidationException;
import at.ac.tuwien.inso.sqm.repository.StudyPlanRepository;
import at.ac.tuwien.inso.sqm.service.StudyPlanService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class StudyPlanServiceSecurityTests {

    @Autowired
    private StudyPlanService studyPlanService;

    @Autowired
    private StudyPlanRepository studyPlanRepository;


    private StudyPlanEntity studyPlan;

    @Before
    public void setUp() {
        studyPlan = studyPlanRepository
                .save(new StudyPlanEntity("TestStudyPlan",
                        new EctsDistributionEntity(BigDecimal.ONE,
                                BigDecimal.ONE, BigDecimal.ONE)));
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void createNotAuthenticated() {
        studyPlanService.create(studyPlan);
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(roles = "STUDENT")
    public void createAuthenticatedAsStudent() {
        studyPlanService.create(studyPlan);
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(roles = "LECTURER")
    public void createAuthenticatedAsLecturer() {
        studyPlanService.create(studyPlan);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void createAuthenticatedAsAdmin() {
        studyPlanService.create(studyPlan);
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void findAllNotAuthenticated() {
        studyPlanService.findAll();
    }

    @Test
    @WithMockUser
    public void findAllAuthenticated() {
        studyPlanService.findAll();
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void findOneNotAuthenticated() {
        studyPlanService.findOne(Long.valueOf(1));
    }

    @Test(expected = BusinessObjectNotFoundException.class)
    @WithMockUser
    public void findOneAuthenticated() {
        studyPlanService.findOne(Long.valueOf(1));
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void getSubjectsForStudyPlanNotAuthenticated() {
        studyPlanService.getSubjectsForStudyPlan(Long.valueOf(1));
    }

    @Test
    @WithMockUser
    public void getSubjectsForStudyPlanAuthenticated() {
        studyPlanService.getSubjectsForStudyPlan(Long.valueOf(1));
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void getAvailableSubjectsForStudyPlanNotAuthenticated() {
        studyPlanService.getAvailableSubjectsForStudyPlan(Long.valueOf(1), "");
    }

    @Test
    @WithMockUser
    public void getAvailableSubjectsForStudyPlanAuthenticated() {
        studyPlanService.getAvailableSubjectsForStudyPlan(Long.valueOf(1), "");
    }

    @Test(expected = AuthenticationCredentialsNotFoundException.class)
    public void addSubjectToStudyPlanNotAuthenticated() {
        studyPlanService.addSubjectToStudyPlan(
                new SubjectForStudyPlanEntity(new Subject(), studyPlan, true));
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(roles = "STUDENT")
    public void addSubjectToStudyPlanAuthenticatedAsStudent() {
        studyPlanService.addSubjectToStudyPlan(
                new SubjectForStudyPlanEntity(new Subject(), studyPlan, true));
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(roles = "LECTURER")
    public void addSubjectToStudyPlanAuthenticatedAsLecturer() {
        studyPlanService.addSubjectToStudyPlan(
                new SubjectForStudyPlanEntity(new Subject(), studyPlan, true));
    }

    @Test(expected = ValidationException.class)
    @WithMockUser(roles = "ADMIN")
    public void addSubjectToStudyPlanAuthenticatedAsAdmin() {
        studyPlanService.addSubjectToStudyPlan(
                new SubjectForStudyPlanEntity(new Subject(), studyPlan, true));
    }

    @Test(expected = ValidationException.class)
    @WithMockUser(roles = "ADMIN")
    public void removeSubjectFromStudyPlanAuthenticatedAsAdmin() {
        studyPlanService.removeSubjectFromStudyPlan(new StudyPlanEntity(),
                new Subject());
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(roles = "STUDENT")
    public void removeSubjectFromStudyPlanAuthenticatedAsStudent() {
        studyPlanService.removeSubjectFromStudyPlan(new StudyPlanEntity(),
                new Subject());
    }

    @Test(expected = AccessDeniedException.class)
    @WithMockUser(roles = "LECTURER")
    public void removeSubjectFromStudyPlanAuthenticatedAsLecturer() {
        studyPlanService.removeSubjectFromStudyPlan(new StudyPlanEntity(),
                new Subject());
    }

}
