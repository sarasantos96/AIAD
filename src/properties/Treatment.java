package properties;

public enum Treatment {
    TREATMENT (10000),
    TREATMENT2 (20000),
    test(20000),
    test2(30000);


    private long duration;

    Treatment(long duration){
        this.duration = duration;
    }





    public long getDuration() {
        return duration;
    }
}
