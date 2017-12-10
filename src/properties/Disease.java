package properties;

public enum Disease {
    DISEASE1(1,2),
    DISEASE2(2,2),
    DISEASE3(32,2);


    private int severity;
    private int criticality;


     Disease(int severity, int criticality){
        this.severity = severity;
        this.criticality = criticality;
    }

    public int getSeverity() {
        return severity;
    }

    public int getCriticality() {
        return criticality;
    }
}
