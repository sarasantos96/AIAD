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

    protected void setup(){
        PatientBehaviour r = new PatientBehaviour();
        treatments.add("test");
        treatments.add("test2");
        FindResourcesBehaviour f = new FindResourcesBehaviour(treatments);
        addBehaviour(f);
        System.out.println("Patient start " + getAID().getName());
    }

    protected void takeDown() {
        System.out.println("agents.Resource agent " + getAID().getName() + " is terminating");
    }

}
