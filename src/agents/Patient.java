package agents;

import behaviours.FindResourcesBehaviour;
import behaviours.PatientBehaviour;
import jade.core.*;
import sun.awt.image.ImageWatched;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Patient extends Agent{
    private String medical_condition;
    private LinkedList<String> treatments = new LinkedList<>();
    private LinkedList<AID> currentResources = new LinkedList<>();
    private boolean availability = true;

    protected void setup(){
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
}
