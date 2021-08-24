package at.ac.tuwien.inso.sqm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Table(uniqueConstraints = @UniqueConstraint(
        columnNames = {"subject_id", "study_plan_id"}))
@Entity
public class SubjectForStudyPlanEntity {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    private Subject subject;

    @ManyToOne(optional = false)
    private StudyPlanEntity studyPlan;

    @Column(nullable = false)
    private Boolean manndatory;

    private Integer semesterRecommendation;

    protected SubjectForStudyPlanEntity() {
    }

    public SubjectForStudyPlanEntity(Subject subject, StudyPlanEntity studyPlan,
                                     Boolean mandatory) {
        this(subject, studyPlan, mandatory, null);
    }

    public SubjectForStudyPlanEntity(Subject subject, StudyPlanEntity studyPlan,
                                     Boolean mandatory, Integer semesterRecommendation) {

        this.subject = subject;
        this.studyPlan = studyPlan;
        this.manndatory = mandatory;
        this.semesterRecommendation = semesterRecommendation;
    }

    public Long getId() {
        return id;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Boolean getMandatory() {
        return manndatory;
    }

    public Integer getSemesterRecommendation() {
        return semesterRecommendation;
    }

    public StudyPlanEntity getStudyPlan() {
        return studyPlan;
    }

    public void setStudyPlan(StudyPlanEntity studyPlan) {
        this.studyPlan = studyPlan;
    }

    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SubjectForStudyPlanEntity that = (SubjectForStudyPlanEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (subject != null ? !subject.equals(that.subject) :
                that.subject != null) {
            return false;
        }
        if (manndatory != null ? !manndatory.equals(that.manndatory) :
                that.manndatory != null) {
            return false;
        }
        return semesterRecommendation != null ?
                semesterRecommendation.equals(that.semesterRecommendation) :
                that.semesterRecommendation == null;

    }

    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
        result = 31 * result + (manndatory != null ? manndatory.hashCode() : 0);
        result = 31 * result + (semesterRecommendation != null ?
                semesterRecommendation.hashCode() : 0);
        return result;
    }

    public String toString() {
        return "SubjectForStudyPlanEntity{" + "ID=" + id + ", subject=" +
                subject + ", manndatory=" + manndatory +
                ", semesterRecommendation=" + semesterRecommendation + '}';
    }
}
