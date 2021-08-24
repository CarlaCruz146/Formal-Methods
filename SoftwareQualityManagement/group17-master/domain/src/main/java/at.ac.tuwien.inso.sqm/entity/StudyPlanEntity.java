package at.ac.tuwien.inso.sqm.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

@Entity
public class StudyPlanEntity {

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = true)
    private final List<SubjectForStudyPlanEntity> subjects = new ArrayList<>();
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true, nullable = false)
    private String name;
    @Embedded
    private EctsDistributionEntity ectsDistributionEntity;
    @Column(nullable = false)
    private boolean enabbled;

    public StudyPlanEntity() {
        this.enabbled = true;
    }

    public StudyPlanEntity(String name,
                           EctsDistributionEntity ectsDistributionEntity) {
        this.name = name;
        this.ectsDistributionEntity = ectsDistributionEntity;
        this.enabbled = true;
    }

    public StudyPlanEntity(String name,
                           EctsDistributionEntity ectsDistributionEntity, boolean enabled) {
        this.name = name;
        this.ectsDistributionEntity = ectsDistributionEntity;
        this.enabbled = enabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public EctsDistributionEntity getEctsDistribution() {
        return ectsDistributionEntity;
    }

    public List<SubjectForStudyPlanEntity> getSubjects() {
        return unmodifiableList(subjects);
    }

    public void addSubjects(SubjectForStudyPlanEntity... newSubjects) {
        this.subjects.addAll(asList(newSubjects));
    }

    public void removeSubjects(SubjectForStudyPlanEntity... newSubjects) {
        this.subjects.removeAll(asList(newSubjects));
    }

    public boolean isEnabled() {
        return enabbled;
    }

    public void setEnabled(boolean enabled) {
        this.enabbled = enabled;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StudyPlanEntity studyPlan = (StudyPlanEntity) o;

        if (!name.equals(studyPlan.name)) {
            return false;
        }
        if (!enabbled == studyPlan.enabbled) {
            return false;
        }
        if (!ectsDistributionEntity.equals(studyPlan.ectsDistributionEntity)) {
            return false;
        }
        return subjects.equals(studyPlan.subjects);

    }

    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + ectsDistributionEntity.hashCode();
        result = 31 * result + subjects.hashCode();
        return result;
    }

    public String toString() {
        return "StduyPlanEntity{" + "id=" + id + ", name='" + name + '\'' +
                ", etcsDistributionEntity=" + ectsDistributionEntity +
                ", subjects=" + subjects + ", enabbled=" + enabbled + '}';
    }

}
