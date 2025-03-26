public enum TransmissionType {
    AUTOMATIC(1), MANUAL(2);

    private final int value;

    TransmissionType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static TransmissionType fromValue(int value) {
        for (TransmissionType type : values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid TransmissionType value: " + value);
    }
}
