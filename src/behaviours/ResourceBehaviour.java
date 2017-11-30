package behaviours;

import agents.Resource;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;

public class ResourceBehaviour extends SimpleBehaviour{
    private int t = 0;

    Resource r;

    public ResourceBehaviour(Resource r){

        this.r = r;
    }

    @Override
    public void action() {
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.setLanguage("English");
        msg.setContent("When can you come?");
        msg.setPerformative(ACLMessage.PROPOSE);
        for(int i = 0; i < r.getAllPatients().size(); i++){

        msg.addReceiver( r.getAllPatients().get(i));

        }
        myAgent.send(msg);
        System.out.println(myAgent.getName() + " sent a message");

    }

    @Override
    public boolean done() {

        return true;
    }
}
