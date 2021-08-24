package at.ac.tuwien.inso.sqm.entity;


import java.util.Calendar;

public enum SemesterTypeEnum {
    //TODO not sure if this is right, should semester start be hardcoded here?
    WinterSemester("WS", Calendar.OCTOBER, 1),
    SummerSemester("SS", Calendar.MARCH, 1);

    /**
     * Name of the semester: WS or SS.
     */
    private String name;

    /**
     * Month the semester starts.
     */
    private int statrMonth;

    /**
     * Day in month the semester starts.
     */
    private int startDay;


    SemesterTypeEnum(String name, int startMonth, int startDay) {
        this.name = name;
        this.statrMonth = startMonth;
        this.startDay = startDay;
    }

    /**
     * Reverse of toString.
     * @param name the string to convert to enum type
     * @return the enum type
     */
    public static SemesterTypeEnum fromString(String name) {
        for (SemesterTypeEnum type : SemesterTypeEnum.values()) {
            if (type.toString().equals(name)) {
                return type;
            }
        }

        throw new IllegalArgumentException(
                "Type '" + name + "' is not a valid SemesterTypeEnum");
    }

    public int getStartMonth() {
        return statrMonth;
    }

    public void setStartMonth(int startMonth) {
        this.statrMonth = startMonth;
    }

    public int getStartDay() {
        return startDay;
    }

    public void setStartDay(int startDay) {
        this.startDay = startDay;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
