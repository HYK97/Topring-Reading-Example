package tobystudy.group.psa.v2;

public enum Level {
    BASIC(1), SILVER(2), GOLD(3);

    private final int value;

    Level(int value) {
        this.value = value;
    }

    public static Level valueOf(int value) {
        if (value == 1) {
            return BASIC;
        } else if (value == 2) {
            return SILVER;
        } else if (value == 3) {
            return GOLD;
        } else {
            throw new AssertionError("없는등급" + value);
        }
    }

    public int intValue() {
        return value;
    }
}
