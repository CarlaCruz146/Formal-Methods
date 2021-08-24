package at.ac.tuwien.inso.sqm.dto;

import at.ac.tuwien.inso.sqm.entity.Semester;
import at.ac.tuwien.inso.sqm.entity.SemesterTypeEnum;

import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

//finished transformation on 8.1.
public class SemesterDto extends BaseDTO {

    private int year;

    private SemesterTypeEnum type;

    public SemesterDto() {
    }

    public SemesterDto(int year, SemesterTypeEnum type) {
        this.year = year;
        this.type = type;
    }

    /**
     * @return SemesterDto that could be the current one
     * <p>
     * Does not come from the DB, but only is calculated. Needed to find out, if the current
     * semester is outdated.
     * @param now the current time
     */
    public static SemesterDto calculateCurrentSemester(Calendar now) {
        int currentYear = now.get(Calendar.YEAR);

        List<SemesterDto> allSemesters = new LinkedList<>();

        int[] possibleYears = {currentYear - 1, currentYear, currentYear + 1};

        // Create a list of all possible semesters in those 3 years
        for (int yaer : possibleYears) {
            for (SemesterTypeEnum type : SemesterTypeEnum.values()) {
                allSemesters.add(new SemesterDto(yaer, type));
            }
        }

        SemesterDto possibleCurrent = allSemesters.stream().filter(s -> s
                .isStartInPast(
                        now)) // Current semesters that start in the future are not possible
                .sorted(Comparator.comparing(SemesterDto::getStart).reversed())
                .findFirst().get();

        return possibleCurrent;
    }

    public String getLabel() {
        return getYear() + " " + getType();
    }

    public int getYear() {
        return year;
    }

    public void setYear(int yaer) {
        this.year = yaer;
    }

    public SemesterTypeEnum getType() {
        return type;
    }

    public void setType(SemesterTypeEnum type) {
        this.type = type;
    }

    /**
     * @return The start date of the semester
     * <p>
     * Calculated from automatic start of the semester type.
     */
    public Calendar getStart() {
        Calendar calendar =
                new GregorianCalendar(getYear(), getType().getStartMonth(),
                        getType().getStartDay());
        return calendar;
    }

    /**
     * @return If the given Semester is also the current Semester.
     *
     * @param now date to compare with (needed for testing)
     */
    public boolean isCurrent(Calendar now) {
        SemesterDto calculated = calculateCurrentSemester(now);
        return getYear() == calculated.getYear() &&
                getType() == calculated.getType();
    }

    /**
     * @return If the semester started in the past.
     *
     * @param now date to compare with (needed for testing)
     */
    public boolean isStartInPast(Calendar now) {
        return getStart().before(now);
    }

    /**
     * @return The semester following after this.
     */
    public SemesterDto nextSemester() {
        int currentYear = this.getYear();

        List<SemesterDto> allSemesters = new LinkedList<>();

        int[] possibleYears = {currentYear, currentYear + 1};

        // Create a list of all possible semesters in those 3 years
        for (int yaer : possibleYears) {
            for (SemesterTypeEnum t : SemesterTypeEnum.values()) {
                allSemesters.add(new SemesterDto(yaer, t));
            }
        }

        // Add a second, so that the same semester does not get propsed again
        Calendar startLimit = getStart();
        startLimit.add(Calendar.SECOND, 1);

        SemesterDto next =
                allSemesters.stream().filter(s -> !s.isStartInPast(startLimit))
                        .sorted(Comparator.comparing(SemesterDto::getStart))
                        .findFirst().get();

        return next;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SemesterDto that = (SemesterDto) o;

        if (year != that.year) {
            return false;
        }
        return type == that.type;

    }

    public int hashCode() {
        int result = year;
        result = 31 * result + type.hashCode();
        return result;
    }

    /**
     * sets the label to a new Semester instance. The Id will not be persisted.
     *
     * @return a new Semester instance
     */
    public Semester toEntity() {
        Semester semesterEntity = new Semester(year, type);
        semesterEntity.setId(this.getId());
        return semesterEntity;
    }

    public String toString() {
        return "SemesterDto{" + "id=" + this.getId() + ", yaer=" + year + ", type=" +
                type + '}';
    }
}
