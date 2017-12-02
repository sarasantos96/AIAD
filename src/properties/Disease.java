package properties;

public class Disease {

    private String diseaseName;
    private int severity;
    private int criticality;


    public Disease(String diseaseName, int severity, int criticality){
        this.diseaseName = diseaseName;
        this.severity = severity;
        this.criticality = criticality;
    }

    public int getSeverity() {
        return severity;
    }

    public void setSeverity(int severity) {
        this.severity = severity;
    }

    public int getCriticality() {
        return criticality;
    }

    public void setCriticality(int criticality) {
        this.criticality = criticality;
    }
}
