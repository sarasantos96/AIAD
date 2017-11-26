package behaviours;

import agents.Resource;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class ReceiveSubscriberBehaviour extends CyclicBehaviour {

    Resource r;

    public ReceiveSubscriberBehaviour(Resource r){
        this.r = r;
    }

    @Override
    public void action() {
        ACLMessage msg= myAgent.receive();

        if (msg != null) {
            // Message received. Process it
            String msgContents = msg.getContent();

            System.out.println("I, " + myAgent.getName() + " justt received " + msgContents);
            r.addSubscribedPatient(msg.getSender());

        }
        else{
            // System.out.println("no message");
        }
    }
}
