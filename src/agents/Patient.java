package agents;

import behaviours.patientbehaviours.AcceptResourceBehaviour;
import behaviours.patientbehaviours.FindResourcesBehaviour;
import behaviours.patientbehaviours.PatientBehaviour;
import behaviours.patientbehaviours.PriorityBehaviour;
import jade.core.*;
import jade.core.behaviours.SequentialBehaviour;
import jade.core.behaviours.WakerBehaviour;
import properties.Disease;
import org.apache.commons.math3.distribution.NormalDistribution;

import java.util.ArrayList;
import java.util.LinkedList;

public class Patient extends Agent{
    private String medical_condition = "Sick";
    private LinkedList<String> treatments = new LinkedList<>();
    private ArrayList<AID> currentResources = new ArrayList<>();
    private boolean availability = true;
    private Disease disease;
    long tStart;

    protected void setup(){

        Object[] args = getArguments();
        disease = new Disease((String) args[0],Integer.parseInt((String)args[1]),Integer.parseInt((String)args[2]));
        tStart = System.currentTimeMillis();

        PatientBehaviour r = new PatientBehaviour(this);

        treatments.add("test");
        treatments.add("test2");


        PriorityBehaviour p = new PriorityBehaviour(this);
        AcceptResourceBehaviour a = new AcceptResourceBehaviour(this);

        System.out.println("Patient start " + getAID().getName());

        subscribeTreatments();
        addBehaviour(r);
        addBehaviour(p);
        addBehaviour(a);



    }

    protected void takeDown(){
        medical_condition = "cured";
        availability = false;
        System.out.println("I, " + getAID().getLocalName() + " am cured.");
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

    public LinkedList<String> getTreatments() {
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



}
