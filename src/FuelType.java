public enum FuelType {
    PETROL("Petrol"), DIESEL("Diesel"), ELECTRIC("Electric");

    private final String value;

    private FuelType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static FuelType fromValue(String value) {
        for (FuelType type : values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid FuelType value: " + value);
    }
}
