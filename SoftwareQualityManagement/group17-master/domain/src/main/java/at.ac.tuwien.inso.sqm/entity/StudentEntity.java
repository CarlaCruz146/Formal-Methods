package at.ac.tuwien.inso.sqm.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

@Entity
public class StudentEntity extends UisUserEntity {

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<StudyPlanRegistration> studyplans = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Course> dismissedCourses = new ArrayList<>();

    protected StudentEntity() {
    }

    public StudentEntity(String identificationNumber, String name,
            String email) {
        this(identificationNumber, name, email, null);
    }

    public StudentEntity(String identificationNumber, String name, String email,
            UserAccountEntity account) {
        super(identificationNumber, name, email, account);
    }

    @Override
    protected void adjustRole(UserAccountEntity account) {
        account.setRole(Role.STUDENT);
    }

    public List<StudyPlanRegistration> getStudyplans() {
        return studyplans;
    }

    public StudentEntity addStudyplans(StudyPlanRegistration... studyPlan) {
        this.studyplans.addAll(asList(studyPlan));
        return this;
    }

    public List<Course> getDismissedCourses() {
        return dismissedCourses;
    }

    public void setDismissedCourses(List<Course> dismissedCourses) {
        this.dismissedCourses = dismissedCourses;
    }

    public StudentEntity addDismissedCourse(
            Course... dismissedCourse) {
        this.dismissedCourses.addAll(asList(dismissedCourse));
        return this;
    }
}
