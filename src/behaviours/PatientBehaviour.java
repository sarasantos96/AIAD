package behaviours;

import agents.Patient;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.LinkedList;

public class PatientBehaviour extends Behaviour {
    Patient p;
    int step = 0;
    public PatientBehaviour(Patient p){
        this.p = p;
    }

    @Override
    public void action() {
        switch(step){
            case 0:
                ACLMessage msg= myAgent.receive();

                if (msg != null) {
                    // Message received. Process it
                    String msgContents = msg.getContent();
                    ACLMessage reply = msg.createReply();
                    System.out.println("Agent agents.Patient " + myAgent.getName() + " received " + msgContents);

                    if(p.getAvailability()) {

                         p.addResourceUse(msg.getSender());
                        reply.setPerformative(ACLMessage.AGREE);
                        reply.setContent("I am ready!");


                    }else{

                        reply.setPerformative(ACLMessage.REFUSE);
                        reply.setContent("I am not ready...");

                    }
                    myAgent.send(reply);

                }
                else{
                   block();
                }
            break;
            case 1:

            break;
        }
    }

    @Override
    public boolean done() {

        return false;
    }




}
