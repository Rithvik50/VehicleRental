public enum TransmissionType {
    MANUAL("Manual"), AUTOMATIC("Automatic");

    private final String value;

    TransmissionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TransmissionType fromValue(String value) {
        for (TransmissionType type : values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid TransmissionType value: " + value);
    }
}
