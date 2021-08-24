package at.ac.tuwien.inso.sqm.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity
public class Grade {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Course course;

    @ManyToOne
    private LecturerEntity lecturer;

    @ManyToOne
    private StudentEntity student;

    @Embedded
    private MarkEntity mark;

    private String urlIdentifier =
            UUID.randomUUID().toString().replace("-", "");

    public Grade() {

    }

    public Grade(Course course, LecturerEntity lecturer,
                 StudentEntity student, MarkEntity mark) {
        this.course = course;
        this.lecturer = lecturer;
        this.student = student;
        this.mark = mark;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public LecturerEntity getLecturer() {
        return lecturer;
    }

    public void setLecturer(LecturerEntity lecturer) {
        this.lecturer = lecturer;
    }

    public StudentEntity getStudent() {
        return student;
    }

    public void setStudent(StudentEntity student) {
        this.student = student;
    }

    public MarkEntity getMark() {
        return mark;
    }

    public void setMark(MarkEntity mark) {
        this.mark = mark;
    }

    public String getUrlIdentifier() {
        return urlIdentifier;
    }

    public void setUrlIdentifier(String urlIdentifier) {
        this.urlIdentifier = urlIdentifier;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Grade grade = (Grade) o;

        if (id != null) {
            if (!id.equals(grade.id)) {
                return false;
            }
        } else {
            if (grade.id != null) {
                return false;
            }
        }
        if (!course.equals(grade.course)) {
            return false;
        }
        if (!lecturer.equals(grade.lecturer)) {
            return false;
        }
        if (!student.equals(grade.student)) {
            return false;
        }
        if (!urlIdentifier.equals(grade.urlIdentifier)) {
            return false;
        }
        return mark.equals(grade.mark);

    }

    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = course != null ? 31 * result + course.hashCode() : 0;
        result = lecturer != null ? 31 * result + lecturer.hashCode() : 0;
        result = student != null ? 31 * result + student.hashCode() : 0;
        result = mark != null ? 31 * result + mark.hashCode() : 0;
        result =
                urlIdentifier != null ? 31 * result + urlIdentifier.hashCode() :
                        0;
        return result;
    }

    public String toString() {
        return "Grade{" + "ID=" + id + ", course=" + course + ", lecturer=" +
                lecturer + ", student=" + student + ", mark=" + mark +
                ", urlIdentifier=" + urlIdentifier + '}';
    }
}
