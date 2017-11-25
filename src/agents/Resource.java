package agents;

import agents.Patient;
import behaviours.ResourceBehaviour;
import jade.core.*;
import javafx.util.Pair;

import java.util.ArrayList;

public class Resource extends Agent{
    private String name;
    private ArrayList<Treatment> available_treatments;
    private ArrayList<Pair<Integer, Patient>> reserves;
    private ArrayList<AID> all_patients = new ArrayList<>();

    protected void setup() {
        System.out.println("agents.Resource agent " + getAID().getName() + " is ready.");

        Object[] args = getArguments();
        if (args != null && args.length > 0) {
         /*   this.name = (String) args[0];
            System.out.println("agents.Resource name is " + this.name);
            //TODO Definir como vão ser passados os tratamentos disponíveis e os pares
        */
         for(int i = 0; i < args.length; i++){
             AID p = new AID((String) args[i],AID.ISLOCALNAME);
             all_patients.add(p);
         }
        }else{
            AID p = new AID("p",AID.ISLOCALNAME);
            p.getName();
            all_patients.add(p);
            //System.out.println("No resource specified");
            //doDelete();
        }
        ResourceBehaviour r = new ResourceBehaviour(all_patients);
        addBehaviour(r);
    }

    protected void takeDown() {
        System.out.println("agents.Resource agent " + getAID().getName() + " is terminating");
    }
}
