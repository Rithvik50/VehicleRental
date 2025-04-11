public class Insurance {
    private double coverage;

    public Insurance(double coverage) {
        this.coverage = coverage;
    }

    public double getCoverage() {
        return coverage;
    }

    public void setCoverage(double cost) {
        coverage -= cost;
    }
}
