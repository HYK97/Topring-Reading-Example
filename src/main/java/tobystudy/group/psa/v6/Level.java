package tobystudy.group.psa.v6;

public enum Level {
    GOLD(3, null), SILVER(2, GOLD), BASIC(1, SILVER);

    private final int value;
    private final Level next;

    Level(int value, Level next) {
        this.value = value;
        this.next = next;
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

    public Level nextLevel() {
        return this.next;
    }
}
