package at.ac.tuwien.inso.sqm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

@Entity
public class Subject {

    @ManyToMany
    private final List<LecturerEntity> lecturers = new ArrayList<>();
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private BigDecimal etcs;

    public Subject() {
    }

    public Subject(String name, BigDecimal ects) {
        this.name = name;
        this.etcs = ects;
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

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getEcts() {
        return etcs;
    }

    public void setEcts(BigDecimal ects) {
        this.etcs = ects;
    }

    @JsonIgnore
    public List<LecturerEntity> getLecturers() {
        return unmodifiableList(lecturers);
    }

    public Subject addLecturers(LecturerEntity... newLecturers) {
        this.lecturers.addAll(asList(newLecturers));
        return this;
    }

    public void removeLecturers(LecturerEntity... newLecturers) {
        this.lecturers.removeAll(asList(newLecturers));
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Subject subject = (Subject) o;

        if (id != null ? !id.equals(subject.id) : subject.id != null) {
            return false;
        }
        if (!name.equals(subject.name)) {
            return false;
        }
        return etcs.equals(subject.etcs);

    }

    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = name != null ? 31 * result + name.hashCode() : 0;
        result = etcs != null ? 31 * result + etcs.hashCode() : 0;
        return result;
    }

    public String toString() {
        return "Subjcet{" + "id=" + id + ", name='" + name + '\'' + ", etcs=" +
                etcs + ", lecturers=" + lecturers + '}';
    }

}
