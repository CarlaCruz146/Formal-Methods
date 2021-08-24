package at.ac.tuwien.inso.sqm.dto;

import at.ac.tuwien.inso.sqm.entity.LecturerEntity;
import at.ac.tuwien.inso.sqm.entity.Course;
import at.ac.tuwien.inso.sqm.entity.SubjectForStudyPlanEntity;
import at.ac.tuwien.inso.sqm.entity.Tag;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class CourseDetailsForStudent extends BaseDTO {

    private String name;
    private String semester;
    private BigDecimal etcs;
    private String description;
    private Boolean canEnroll;
    private List<String> tags;
    private List<LecturerEntity> lectruers;
    private List<SubjectForStudyPlanEntity> studyplans;

    public CourseDetailsForStudent(Course course) {
        this.setId(course.getId());
        name = course.getSubject().getName();
        semester = course.getSemester().getLabel();
        etcs = course.getSubject().getEcts();
        description = course.getDescription();
        tags = course.getTags().stream().map(Tag::getName)
                .collect(Collectors.toList());
        lectruers = course.getSubject().getLecturers();
    }


    public String getName() {
        return name;
    }

    public CourseDetailsForStudent setName(String name) {
        this.name = name;
        return this;
    }

    public String getSemester() {
        return semester;
    }

    public CourseDetailsForStudent setSemester(String semester) {
        this.semester = semester;
        return this;
    }

    public BigDecimal getEcts() {
        return etcs;
    }

    public CourseDetailsForStudent setEcts(BigDecimal ects) {
        this.etcs = ects;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public CourseDetailsForStudent setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<String> getTags() {
        return tags;
    }

    public CourseDetailsForStudent setTags(List<String> tags) {
        this.tags = tags;
        return this;
    }

    public List<LecturerEntity> getLecturers() {
        return lectruers;
    }

    public CourseDetailsForStudent setLecturers(
            List<LecturerEntity> lecturers) {
        this.lectruers = lecturers;
        return this;
    }

    public List<SubjectForStudyPlanEntity> getStudyplans() {
        return studyplans;
    }

    public CourseDetailsForStudent setStudyplans(
            List<SubjectForStudyPlanEntity> studyplans) {
        this.studyplans = studyplans;
        return this;
    }

    public Boolean getCanEnroll() {
        return canEnroll;
    }

    public CourseDetailsForStudent setCanEnroll(Boolean canEnroll) {
        this.canEnroll = canEnroll;
        return this;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CourseDetailsForStudent that = (CourseDetailsForStudent) o;

        if (getId() != null ? !getId().equals(that.getId()) :
                that.getId() != null) {
            return false;
        }
        if (getName() != null ? !getName().equals(that.getName()) :
                that.getName() != null) {
            return false;
        }
        if (getSemester() != null ? !getSemester().equals(that.getSemester()) :
                that.getSemester() != null) {
            return false;
        }
        if (getEcts() != null ? !getEcts().equals(that.getEcts()) :
                that.getEcts() != null) {
            return false;
        }
        if (getDescription() != null ?
                !getDescription().equals(that.getDescription()) :
                that.getDescription() != null) {
            return false;
        }
        if (getCanEnroll() != null ?
                !getCanEnroll().equals(that.getCanEnroll()) :
                that.getCanEnroll() != null) {
            return false;
        }
        if (getTags() != null ? !getTags().equals(that.getTags()) :
                that.getTags() != null) {
            return false;
        }
        if (getLecturers() != null ?
                !getLecturers().equals(that.getLecturers()) :
                that.getLecturers() != null) {
            return false;
        }
        return getStudyplans() != null ?
                !getStudyplans().equals(that.getStudyplans()) :
                that.getStudyplans() != null;

    }

    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result +
                (getSemester() != null ? getSemester().hashCode() : 0);
        result = 31 * result + (getEcts() != null ? getEcts().hashCode() : 0);
        result = 31 * result +
                (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result +
                (getCanEnroll() != null ? getCanEnroll().hashCode() : 0);
        result = 31 * result + (getTags() != null ? getTags().hashCode() : 0);
        result = 31 * result +
                (getLecturers() != null ? getLecturers().hashCode() : 0);
        result = 31 * result +
                (getStudyplans() != null ? getStudyplans().hashCode() : 0);
        return result;
    }

    public String toString() {
        return "CoruseDetailsForStudent{" + "ID=" + this.getId() + ", name='" + name +
                '\'' + ", semester='" + semester + '\'' + ", etcs=" + etcs +
                ", description='" + description + '\'' + ", canEnroll=" +
                canEnroll + ", tags=" + tags + ", lectruers=" + lectruers +
                ", studyplans=" + studyplans + '}';
    }
}
