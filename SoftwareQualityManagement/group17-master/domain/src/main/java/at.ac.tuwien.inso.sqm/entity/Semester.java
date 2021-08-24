package at.ac.tuwien.inso.sqm.entity;

import at.ac.tuwien.inso.sqm.dto.SemesterDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Semester {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private int year;

    /**
     * WS or SS.
     */
    @Column
    @NotNull
    @Enumerated(EnumType.STRING)
    private SemesterTypeEnum type;

    public Semester() {
    }

    public Semester(int year, SemesterTypeEnum type) {
        this.year = year;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public SemesterTypeEnum getType() {
        return type;
    }

    public void setType(SemesterTypeEnum type) {
        this.type = type;
    }

    public String getLabel() {
        return getType() + " " + getYear();
    }

    public String toString() {
        return getLabel();
    }

    public SemesterDto toDto() {
        SemesterDto dto = new SemesterDto(year, type);

        if (id != null) {
            dto.setId(id);
        }

        return dto;
    }
}
