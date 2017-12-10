package behaviours.patientbehaviours;

import agents.Patient;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class FCFSPatient extends CyclicBehaviour {
    private MessageTemplate mt = MessageTemplate.MatchConversationId("FCFS");
    Patient p;

    public FCFSPatient(Patient p){
        this.p = p;
    }

    @Override
    public void action() {
        ACLMessage msg= myAgent.receive(mt);


        if (msg != null) {
            String msgContents = msg.getContent();
            ACLMessage reply = msg.createReply();
            System.out.println("I, " + myAgent.getName() + " justt received " + msgContents);
            if(msg.getPerformative() == ACLMessage.AGREE && p.getAvailability()){
                if(p.getAvailability()){
                    System.out.println("I " + myAgent.getLocalName() + " am gonna start my Treatment!");
                    reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
                    reply.setConversationId("finalize-process");
                    reply.setContent("Starting Treatment");
                    myAgent.send(reply);
                    p.FCFSStartTreatment();
                    p.setAvailability(false);

                }else{
                    reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
                    reply.setContent("Not Starting Treatment");
                    myAgent.send(reply);
                }
            }
        }else{
            block();
        }
    }
}
