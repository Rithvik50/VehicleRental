public class Insurance {
    private String insuranceCompany;
    private double coverage;

    public Insurance(String insuranceCompany, double coverage) {
        this.insuranceCompany = insuranceCompany;
        this.coverage = coverage;
    }

    public String getInsuranceCompany() {
        return insuranceCompany;
    }

    public double getCoverage() {
        return coverage;
    }

    public void setCoverage(double cost) {
        coverage -= cost;
    }
}
