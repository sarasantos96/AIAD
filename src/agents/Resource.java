package agents;

import behaviours.resourcebehaviours.FCFS;
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

import javax.swing.*;
import java.util.ArrayList;

public class Resource extends Agent{
    private ArrayList<Treatment> availableTreatments = new ArrayList<>();
    private Treatment nextTreatment = Treatment.randomTreatment();
    private ArrayList<AID> allSubscribedPatients = new ArrayList<>();
    private AID nextPatient;
    private Boolean treatingEmergency = false;
    private ResourceBehaviour r2 = new ResourceBehaviour(this);
    private int status;
    private JFrame GUI;
    private boolean FCFSReady = true;
    private boolean FCFSB;


    public Resource(String[] args,boolean FCFSB, JFrame GUI){
        this.GUI = GUI;
        this.FCFSB = FCFSB;
        if (args != null && args.length > 0) {
            for(int i = 0; i < args.length; i ++){
                try{
                    availableTreatments.add(Treatment.valueOf((String) args[i]));
                }catch (IllegalArgumentException ex) {
                    System.err.println("Illegal Treatment");
                }

            }
        }

    }

    private SequentialBehaviour inTreatmentBehaviour = new SequentialBehaviour() {


        public int onEnd() {
            this.addSubBehaviour(r2);
            this.addSubBehaviour( new WakerBehaviour( myAgent, nextTreatment.getDuration() )
            {
                protected void onWake() {
                    System.out.println( "I, "+ getLocalName() + " just finished " + nextTreatment.name());
                    status = 6;
                }
            });
            myAgent.addBehaviour(this);
            return super.onEnd();
        }
    };

    public void FCFSStartTreatment(){
        SequentialBehaviour s = new SequentialBehaviour();
        s.addSubBehaviour( new WakerBehaviour( this, nextTreatment.getDuration() )
        {
            protected void onWake() {
                System.out.println( "I, "+ getLocalName() +" just finished treating a patient with " + nextTreatment.name());
                status = 6;
                FCFSReady = true;
            }
        });
        FCFS f = new FCFS(this);
        s.addSubBehaviour(f);
        this.addBehaviour(s);
    }


    protected void setup() {
        System.out.println("agents.Resource agent " + getAID().getLocalName() + " is ready.");

        if(!FCFSB) {

            Object[] args = getArguments();
            if (args != null && args.length > 0) {
                DFAgentDescription dfd = new DFAgentDescription();
                dfd.setName(getAID());
                for (int i = 0; i < args.length; i++) {
                    try {
                        availableTreatments.add(Treatment.valueOf((String) args[i]));
                    } catch (IllegalArgumentException ex) {
                        System.err.println("Illegal Treatment");
                    }
                    ServiceDescription sd = new ServiceDescription();
                    sd.setType("treatment");
                    sd.setName((String) args[i]);
                    dfd.addServices(sd);
                    System.out.println("I, "+ getLocalName() +" have the treatment " + args[i]);
                }
                try {
                    DFService.register(this, dfd);
                } catch (FIPAException fe) {
                    fe.printStackTrace();
                }
            } else {
                DFAgentDescription dfd = new DFAgentDescription();
                dfd.setName(getAID());
                for (int i = 0; i < availableTreatments.size(); i++) {

                    ServiceDescription sd = new ServiceDescription();
                    sd.setType("treatment");
                    sd.setName((String) availableTreatments.get(i).name());
                    dfd.addServices(sd);
                    System.out.println("I, "+ getLocalName() +" have the treatment " + availableTreatments.get(i));
                }
                try {
                    DFService.register(this, dfd);
                } catch (FIPAException fe) {
                    fe.printStackTrace();
                }
            }
            ReceiveSubscriberBehaviour r = new ReceiveSubscriberBehaviour(this);
            ReceiveUnsubscriptionBehaviour uR = new ReceiveUnsubscriptionBehaviour(this);


            inTreatmentBehaviour.addSubBehaviour(new WakerBehaviour(this, 20000) {
                protected void onWake() {
                    System.out.println("I, "+ getLocalName() +" am about to ask for times ");
                }
            });
            inTreatmentBehaviour.addSubBehaviour(r2);

            addBehaviour(r);
            addBehaviour(uR);
            addBehaviour(inTreatmentBehaviour);
        }else{
            DFAgentDescription dfd = new DFAgentDescription();
            dfd.setName(getAID());
            for (int i = 0; i < availableTreatments.size(); i++) {

                ServiceDescription sd = new ServiceDescription();
                sd.setType("treatment");
                sd.setName((String) availableTreatments.get(i).name());
                dfd.addServices(sd);
                System.out.println("I, "+ getLocalName() +" have the treatment " + availableTreatments.get(i));


            }
            try {
                DFService.register(this, dfd);
            } catch (FIPAException fe) {
                fe.printStackTrace();
            }
            FCFS fcfs = new FCFS(this);
            addBehaviour(fcfs);
        }

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

    public void setNextPatient(AID nextPatient) {
        this.nextPatient = nextPatient;
    }

    public ArrayList<AID> getAllSubscribedPatients() {
        return allSubscribedPatients;
    }

    public ArrayList<Treatment> getAvailableTreatments() {
        return availableTreatments;
    }

    public Treatment getNextTreatment() {
        return nextTreatment;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
        this.GUI.revalidate();
    }

    public boolean isFCFSReady() {
        return FCFSReady;
    }

    public void setFCFSReady(boolean FCFSReady) {
        this.FCFSReady = FCFSReady;
    }

    public void setNextTreatment(Treatment nextTreatment) {
        this.nextTreatment = nextTreatment;
    }

    public AID getNextPatient() {
        return nextPatient;
    }

    public Boolean getTreatingEmergency() {
        return treatingEmergency;
    }

    public void setTreatingEmergency(Boolean treatingEmergency) {
        this.treatingEmergency = treatingEmergency;
    }


}
