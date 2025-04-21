public enum TransmissionType {
    MANUAL("Manual"), AUTOMATIC("Automatic");

    private final String VALUE;

    private TransmissionType(String value) {
        this.VALUE = value;
    }

    public String getValue() {
        return VALUE;
    }

    public static TransmissionType fromValue(String value) {
        for (TransmissionType type : values()) {
            if (type.VALUE == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid TransmissionType value: " + value);
    }
}
