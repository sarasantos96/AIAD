package agents;

import behaviours.FindResourcesBehaviour;
import behaviours.PatientBehaviour;
import jade.core.*;

import java.util.ArrayList;

public class Patient extends Agent{
    private String medical_condition;
    private ArrayList<String> treatments = new ArrayList<>();

    protected void setup(){
        PatientBehaviour r = new PatientBehaviour();
        treatments.add("test");
        FindResourcesBehaviour f = new FindResourcesBehaviour(treatments);
        addBehaviour(f);
        System.out.println("Patient start " + getAID().getName());
    }

    protected void takeDown() {
        System.out.println("agents.Resource agent " + getAID().getName() + " is terminating");
    }

}
