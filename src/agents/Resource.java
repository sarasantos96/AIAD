package agents;

import agents.Patient;
import behaviours.ReceiveSubscriberBehaviour;
import behaviours.ResourceBehaviour;
import jade.core.*;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import javafx.util.Pair;

import java.util.ArrayList;

public class Resource extends Agent{
    private String name;
    private ArrayList<Treatment> available_treatments;
    private ArrayList<Pair<Integer, Patient>> reserves;
    private ArrayList<AID> all_patients = new ArrayList<>();


    protected void setup() {
        System.out.println("agents.Resource agent " + getAID().getLocalName() + " is ready.");

        Object[] args = getArguments();
        if (args != null && args.length > 0) {
         /*   this.name = (String) args[0];
            System.out.println("agents.Resource name is " + this.name);
            //TODO Definir como vão ser passados os tratamentos disponíveis e os pares
        */
            DFAgentDescription dfd = new DFAgentDescription();
            dfd.setName(getAID());
         for(int i = 0; i < args.length; i ++){
           //  AID p = new AID((String) args[i],AID.ISLOCALNAME);
             //all_patients.add(p);

             ServiceDescription sd = new ServiceDescription();
             sd.setType("treatment");
             sd.setName((String)args[i]);
             dfd.addServices(sd);
             System.out.println("I have the treatment " + args[i]);
         }
            try {
                DFService.register(this, dfd);
            }
            catch (FIPAException fe) {
                fe.printStackTrace();
            }
        }else{
            AID p = new AID("p",AID.ISLOCALNAME);
            p.getName();
            all_patients.add(p);

            //System.out.println("No resource specified");
            //doDelete();
        }
        ReceiveSubscriberBehaviour r = new ReceiveSubscriberBehaviour(this);
        //ResourceBehaviour r = new ResourceBehaviour(all_patients);
        addBehaviour(r);
    }

    public void addSubscribedPatient(AID patient){
        this.all_patients.add(patient);
    }
    protected void takeDown() {
        System.out.println("agents.Resource agent " + getAID().getName() + " is terminating");
    }
}
