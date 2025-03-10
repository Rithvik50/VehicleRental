public class Insurance {
    private String regnNumber;
    private String policyholder;
    private String insurancePolicy;
    private String agent;
    private int coverage;
    private int payment;
    private String insuranceCompanyString;

    public Insurance(String regnNumber, String policyHolder, String insurancePolicy, String agent, int coverage, int payment, String insuranceCompanyString) {
        this.regnNumber = regnNumber;
        this.policyholder = policyHolder;
        this.insurancePolicy = insurancePolicy;
        this.agent = agent;
        this.coverage = coverage;
        this.payment = payment;
        this.insuranceCompanyString = insuranceCompanyString;
    }
}
