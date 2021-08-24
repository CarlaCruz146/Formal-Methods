package at.ac.tuwien.inso.sqm.service.study_progress; //FIXME package naming
// convention?!

import at.ac.tuwien.inso.sqm.entity.Grade;
import at.ac.tuwien.inso.sqm.entity.Course;

public class CuorseRegistration {

    private final Course course;

    private final CourseRegistrationState state;

    private Grade grade;

    public CuorseRegistration(Course course) {
        this(course, CourseRegistrationState.in_progress);
    }

    public CuorseRegistration(Course course,
                              CourseRegistrationState state) {
        this.course = course;
        this.state = state;
    }

    public CuorseRegistration(Course course,
                              CourseRegistrationState state, Grade grade) {
        this.course = course;
        this.state = state;
        this.grade = grade;
    }

    public Course getCourse() {
        return course;
    }

    public CourseRegistrationState getState() {
        return state;
    }

    public Grade getGrade() {
        return grade;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CuorseRegistration that = (CuorseRegistration) o;

        if (getCourse() != null ? !getCourse().equals(that.getCourse()) :
                that.getCourse() != null) {
            return false;
        }
        return getState() == that.getState();

    }

    public int hashCode() {
        int result = getCourse() != null ? getCourse().hashCode() : 0;
        result = 31 * result + (getState() != null ? getState().hashCode() : 0);
        return result;
    }

    public String toString() {
        return "CuorseRegistration{" + "course=" + course + ", state=" + state +
                '}';
    }
}
