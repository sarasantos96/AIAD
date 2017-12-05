package behaviours.patientbehaviours;

import agents.Patient;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.LinkedList;

public class PatientBehaviour extends CyclicBehaviour {
    Patient p;
    private MessageTemplate mt = MessageTemplate.MatchConversationId("licitation-process1");
    public PatientBehaviour(Patient p){
        this.p = p;
    }

    @Override
    public void action() {
                ACLMessage msg= myAgent.receive(mt);

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

    }




}
