package behaviours.patientbehaviours;

import agents.Patient;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class PriorityBehaviour extends CyclicBehaviour{
    private MessageTemplate mt = MessageTemplate.MatchConversationId("licitation-process2");
    private Patient p;

    public PriorityBehaviour(Patient p){
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
            reply.setPerformative(ACLMessage.PROPOSE);
            reply.setContent(String.valueOf(p.getPriority(10)));
            System.out.println("Sending my Priority, I am " + myAgent.getLocalName());
            myAgent.send(reply);
        }
        else{
            block();
        }
    }
}
