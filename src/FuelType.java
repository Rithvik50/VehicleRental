public enum FuelType {
    PETROL(1), DIESEL(2), ELECTRIC(3);

    private final int value;

    FuelType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static FuelType fromValue(int value) {
        for (FuelType type : values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid FuelType value: " + value);
    }
}
