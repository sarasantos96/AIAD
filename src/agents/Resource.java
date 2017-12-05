package agents;

import behaviours.resourcebehaviours.ReceiveSubscriberBehaviour;
import behaviours.resourcebehaviours.ReceiveUnsubscriptionBehaviour;
import behaviours.resourcebehaviours.ResourceBehaviour;
import jade.core.*;
import jade.core.behaviours.SequentialBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import javafx.util.Pair;
import properties.Treatment;

import java.util.ArrayList;

public class Resource extends Agent{
    private String name;
    private ArrayList<Treatment> availableTreatments;
    private ArrayList<Pair<Integer, Patient>> reserves;
    private ArrayList<AID> allSubscribedPatients = new ArrayList<>();
    private AID nextPatient;

    private SequentialBehaviour inTreatmentBehaviour = new SequentialBehaviour() {
        public int onEnd() {
            reset();
            myAgent.addBehaviour(this);
            return super.onEnd();
        }
    };


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
             //allSubscribedPatients.add(p);

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
            allSubscribedPatients.add(p);

            //System.out.println("No resource specified");
            //doDelete();
        }
        ReceiveSubscriberBehaviour r = new ReceiveSubscriberBehaviour(this);
        ReceiveUnsubscriptionBehaviour uR = new ReceiveUnsubscriptionBehaviour(this);
        ResourceBehaviour r2 = new ResourceBehaviour(this);

        inTreatmentBehaviour.addSubBehaviour( new WakerBehaviour( this, 20000 )
        {
            protected void onWake() {
                System.out.println( "About to ask for times");
            }
        });
        inTreatmentBehaviour.addSubBehaviour(r2);

        addBehaviour(r);
        addBehaviour(uR);
        addBehaviour( inTreatmentBehaviour );


    }

    public void addSubscribedPatient(AID patient){
        this.allSubscribedPatients.add(patient);
    }

    public void removeSubscribedPatient(AID patient){
        if(this.allSubscribedPatients.contains(patient)){
            this.allSubscribedPatients.remove(patient);
        }
    }

    protected void takeDown() {
        System.out.println("agents.Resource agent " + getAID().getName() + " is terminating");
    }

    public AID getNextPatient() {
        return nextPatient;
    }

    public void setNextPatient(AID nextPatient) {
        this.nextPatient = nextPatient;
    }

    public ArrayList<AID> getAllSubscribedPatients() {
        return allSubscribedPatients;
    }
}
