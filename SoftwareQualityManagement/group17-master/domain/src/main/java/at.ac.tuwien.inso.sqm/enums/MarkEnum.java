package at.ac.tuwien.inso.sqm.enums;

public enum MarkEnum {

    excellent(1), good(2), satisfactory(3), sufficient(4), failes(5);

    private int mark;

    MarkEnum(int mark) {
        this.mark = mark;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public String toString() {
        return "MarkEntity" + mark;
    }

    public MarkEnum toEnumConstant(int markToConvert) {
        for (MarkEnum m : values()) {
            if (m.getMark() == markToConvert) {
                return m;
            }
        }

        return null;
    }
}
