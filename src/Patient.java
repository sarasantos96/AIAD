import jade.core.*;

import java.util.ArrayList;

public class Patient extends Agent{
    private String medical_condition;
    private ArrayList<String> treatments;

    protected void setup(){
        PatientBehaviour r = new PatientBehaviour();
        addBehaviour(r);
        System.out.println("Message receiver start");
    }

    protected void takeDown() {
        System.out.println("Resource agent " + getAID().getName() + " is terminating");
    }

}
