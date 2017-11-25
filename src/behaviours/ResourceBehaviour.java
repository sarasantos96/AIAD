package behaviours;

import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;

public class ResourceBehaviour extends SimpleBehaviour{
    private int t = 0;

    ArrayList<AID> agentsSend;

    public ResourceBehaviour(ArrayList<AID> agentsSend){

        this.agentsSend = agentsSend;
    }

    @Override
    public void action() {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.setLanguage("English");
        msg.setContent("Receive this!");
        for(int i = 0; i < agentsSend.size(); i++){

        msg.addReceiver( agentsSend.get(i));

        }
        myAgent.send(msg);
        System.out.println("Sent a message");

    }

    @Override
    public boolean done() {

        return true;
    }
}
