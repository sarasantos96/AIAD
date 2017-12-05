package behaviours.resourcebehaviours;

import agents.Resource;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ReceiveSubscriberBehaviour extends CyclicBehaviour {

    private MessageTemplate mt = MessageTemplate.MatchConversationId("subscriber-process");
    Resource r;

    public ReceiveSubscriberBehaviour(Resource r){
        this.r = r;
    }

    @Override
    public void action() {
        ACLMessage msg= myAgent.receive(mt);

        if (msg != null) {
            // Message received. Process it
            String msgContents = msg.getContent();

            System.out.println("I, " + myAgent.getName() + " justt received " + msgContents);
            r.addSubscribedPatient(msg.getSender());

        }
        else{
            block();
        }
    }
}
