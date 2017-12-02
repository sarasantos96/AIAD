package agents;

import behaviours.FindResourcesBehaviour;
import behaviours.PatientBehaviour;
import jade.core.*;
import properties.Disease;
import sun.awt.image.ImageWatched;
import org.apache.commons.math3.distribution.NormalDistribution;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Patient extends Agent{
    private String medical_condition;
    private LinkedList<String> treatments = new LinkedList<>();
    private LinkedList<AID> currentResources = new LinkedList<>();
    private boolean availability = true;
    private Disease disease;

    protected void setup(){

        Object[] args = getArguments();
        disease = new Disease((String) args[0],Integer.parseInt((String)args[1]),Integer.parseInt((String)args[2]));
        getPriority(10);
        PatientBehaviour r = new PatientBehaviour(this);
        treatments.add("test");
        treatments.add("test2");
        FindResourcesBehaviour f = new FindResourcesBehaviour(treatments);
        System.out.println("Patient start " + getAID().getName());
        addBehaviour(f);
        addBehaviour(r);



    }

    protected void takeDown() {
        System.out.println("agents.Resource agent " + getAID().getName() + " is terminating");
    }
    public void addResourceUse(AID p){currentResources.add(p); }
    public boolean getAvailability(){
        return availability;
    }

    public int getPriority(int t){

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
}
