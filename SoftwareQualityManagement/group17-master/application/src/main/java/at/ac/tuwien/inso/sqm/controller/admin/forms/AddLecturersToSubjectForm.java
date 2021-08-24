package at.ac.tuwien.inso.sqm.controller.admin.forms;


public class AddLecturersToSubjectForm {

    private Long lecturerId;

    public AddLecturersToSubjectForm() {
    }

    public AddLecturersToSubjectForm(Long lecturerId) {
        this.lecturerId = lecturerId;
    }

    public Long getLecturerId() {
        return lecturerId;
    }

    public void setLecturerId(Long lecturerId) {
        this.lecturerId = lecturerId;
    }

    public Long toLecturerId() {
        return lecturerId;
    }

    public String toString() {
        return "AddLecturersToSubjectForm{" + "lecturerId=" + lecturerId + '}';
    }
}
