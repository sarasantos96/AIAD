package agents;

import behaviours.patientbehaviours.*;
import jade.core.*;
import jade.core.behaviours.SequentialBehaviour;
import jade.core.behaviours.WakerBehaviour;
import properties.Disease;
import org.apache.commons.math3.distribution.NormalDistribution;
import properties.Treatment;

import java.util.ArrayList;
import java.util.LinkedList;

public class Patient extends Agent{
    private LinkedList<Treatment> treatments = new LinkedList<>();
    private ArrayList<AID> currentResources = new ArrayList<>();
    private boolean availability = true;
    private Disease disease;
    private AID cResource;
    long tStart;
    double tFinish = -1;
    private boolean emergency = false;
    private boolean FCFSB;

    public Patient(String disease, String[] treatments,boolean emergency, boolean FCFSB){
        this.disease = Disease.valueOf(disease);
        this.emergency = emergency;
        this.FCFSB = FCFSB;
        for(int i = 0; i< treatments.length; i++){
            this.treatments.add(Treatment.valueOf((String) treatments[i]));}
    }

    protected void setup(){

        if(!FCFSB) {
            Object[] args = getArguments();
            //disease = Disease.DISEASE1;
            tStart = System.currentTimeMillis();

            PatientBehaviour r = new PatientBehaviour(this);

            if (args != null && args.length > 2) {
                try {
                    disease = Disease.valueOf((String) args[0]);
                    emergency = Boolean.valueOf((String) args[1]);
                    for (int i = 2; i < args.length; i++) {
                        treatments.add(Treatment.valueOf((String) args[i]));
                    }
                } catch (IllegalArgumentException e) {
                    System.err.println(e);
                }
            }


            PriorityBehaviour p = new PriorityBehaviour(this);
            AcceptResourceBehaviour a = new AcceptResourceBehaviour(this);

            System.out.println("Patient start " + getAID().getName());

            subscribeTreatments();
            addBehaviour(r);
            addBehaviour(p);
            addBehaviour(a);
        }else{
            tStart = System.currentTimeMillis();

            SequentialBehaviour seq = new SequentialBehaviour();
            seq.addSubBehaviour( new WakerBehaviour( this, 20000 )
            {
                protected void onWake() {
                    System.out.println( "Trying to start new treatment.");
                }
            });
            FindResourcesBehaviour f = new FindResourcesBehaviour(this);
            seq.addSubBehaviour(f);
            FCFSPatient fp = new FCFSPatient(this);

            addBehaviour(seq);
            addBehaviour(fp);
        }



    }

    protected void takeDown(){
        availability = false;
        System.out.println("I, " + getAID().getLocalName() + " am cured.");
        tFinish = currTimeSeconds();
    }

    public void addResourceUse(AID p){
        currentResources.add(p);
    }

    public boolean getAvailability(){
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public void subscribeTreatments(){
        FindResourcesBehaviour f = new FindResourcesBehaviour(this);
        addBehaviour(f);
    }

    public void startTreatment(int duration){
        FindResourcesBehaviour f = new FindResourcesBehaviour(this);
        SequentialBehaviour seq = new SequentialBehaviour();
        seq.addSubBehaviour( new WakerBehaviour( this, duration )
        {
            protected void onWake() {
                System.out.println( "Trying to start new treatment.");
            }
        });
        seq.addSubBehaviour(f);
        addBehaviour(seq);
    }

    public void FCFSStartTreatment(){

        SequentialBehaviour seq = new SequentialBehaviour();
        seq.addSubBehaviour( new WakerBehaviour( this, this.getTreatments().poll().getDuration() )
        {
            protected void onWake() {
                System.out.println( "Trying to start new treatment.");
            }
        });
        if(!this.getTreatments().isEmpty()){
        FindResourcesBehaviour f = new FindResourcesBehaviour(this);
        seq.addSubBehaviour(f);
        addBehaviour(seq);
        }else{
            System.out.println("I, "+ this.getLocalName() + " am cured");
            tFinish = currTimeSeconds();
        }

    }

    public double getPriority(){

        double t = currTimeSeconds();
        NormalDistribution d = new NormalDistribution(t/2,1);


        int prior = 0;
        for(int i = 0; i< t; i++){

            double a = disease.getSeverity()*i;
            double b = (disease.getCriticality()/2) *(i*i);
            double p = d.probability(i-1,i);
            prior += p*(a+b);

        }
        return prior;
    }

    private double currTimeSeconds(){
        long tEnd = System.currentTimeMillis();
        long tDelta = tEnd - tStart;
        double elapsedSeconds = tDelta / 1000.0;
        return elapsedSeconds;
    }

    public LinkedList<Treatment> getTreatments() {
        return treatments;
    }

    public ArrayList<AID> getCurrentResources() {
        return currentResources;
    }

    public void finishTreatment(){
        currentResources.clear();
        if(!treatments.isEmpty()){
            startTreatment(20000);
        }else{
            this.takeDown();
        }
    }

    public Disease getDisease() {
        return disease;
    }

    public AID getcResource() {
        return cResource;
    }

    public void setcResource(AID cResource) {
        this.cResource = cResource;
    }

    public boolean isEmergency() {
        return emergency;
    }

    public void setEmergency(boolean emergency) {
        this.emergency = emergency;
    }

    public double gettFinish() {
        return tFinish;
    }
}
