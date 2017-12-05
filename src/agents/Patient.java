package agents;

import behaviours.patientbehaviours.AcceptResourceBehaviour;
import behaviours.patientbehaviours.FindResourcesBehaviour;
import behaviours.patientbehaviours.PatientBehaviour;
import behaviours.patientbehaviours.PriorityBehaviour;
import jade.core.*;
import properties.Disease;
import org.apache.commons.math3.distribution.NormalDistribution;

import java.util.LinkedList;

public class Patient extends Agent{
    private String medical_condition;
    private LinkedList<String> treatments = new LinkedList<>();
    private LinkedList<AID> currentResources = new LinkedList<>();
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

        FindResourcesBehaviour f = new FindResourcesBehaviour(treatments);
        PriorityBehaviour p = new PriorityBehaviour(this);
        AcceptResourceBehaviour a = new AcceptResourceBehaviour(this);

        System.out.println("Patient start " + getAID().getName());

        addBehaviour(f);
        addBehaviour(r);
        addBehaviour(p);
        addBehaviour(a);



    }

    protected void takeDown() {
        System.out.println("agents.Resource agent " + getAID().getName() + " is terminating");
    }
    public void addResourceUse(AID p){currentResources.add(p); }
    public boolean getAvailability(){
        return availability;
    }



    public double getPriority(){

        double t = currTimeSeconds();
        NormalDistribution d = new NormalDistribution(t/2,1);


        int prior = 0;
        for(int i = 0; i< t; i++){

            double a = disease.getSeverity()*i;
            double b = (disease.getCriticality()/2) *(i*i);
            double p = d.cumulativeProbability(i);
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
}
