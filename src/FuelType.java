public enum FuelType {
    PETROL("Petrol"), DIESEL("Diesel"), ELECTRIC("Electric");

    private final String VALUE;

    private FuelType(String value) {
        this.VALUE = value;
    }

    public String getValue() {
        return VALUE;
    }

    public static FuelType fromValue(String value) {
        for (FuelType type : values()) {
            if (type.VALUE == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid FuelType value: " + value);
    }
}
