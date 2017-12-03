package behaviours;

import agents.Resource;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.ArrayList;

public class ResourceBehaviour extends Behaviour {
    private int t = 0;

    Resource r;
    int step = 0;
    int getAllProposals = r.getAllPatients().size();
    ArrayList<AID> allPreparedPatients = new ArrayList<>();
    private MessageTemplate mt;
    public ResourceBehaviour(Resource r){

        this.r = r;
    }

    @Override
    public void action() {
        switch(step){
            case 0:
                ACLMessage msg = new ACLMessage(ACLMessage.PROPOSE);
                msg.setLanguage("English");
                msg.setContent("When can you come?");
                msg.setPerformative(ACLMessage.PROPOSE);
                msg.setConversationId("licitation-process1-" + myAgent.getLocalName());
                for(int i = 0; i < r.getAllPatients().size(); i++){
                    msg.addReceiver( r.getAllPatients().get(i));
                }
                myAgent.send(msg);
                System.out.println(myAgent.getName() + " sent a message");
                mt = MessageTemplate.MatchConversationId("licitation-process1-" + myAgent.getLocalName());
                step = 1;
                break;
            case 1:
                if(getAllProposals == 0){
                    step = 2;
                }
                else{
                    ACLMessage reply = myAgent.receive(mt);
                    if(reply != null){
                        if(reply.getPerformative() == ACLMessage.AGREE && r.getAllPatients().contains(reply.getSender())){
                            getAllProposals--;
                            allPreparedPatients.add(reply.getSender());

                        }else if(reply.getPerformative() == ACLMessage.REFUSE && r.getAllPatients().contains(reply.getSender())){
                            getAllProposals--;
                        }
                    }else{
                        block();
                    }
                }
                break;
            case 2:
                ACLMessage msg2 = new ACLMessage(ACLMessage.CFP);
                msg2.setLanguage("English");
                msg2.setContent("What is your priority?");
                msg2.setConversationId("licitation-process2-" + myAgent.getLocalName());
                for(int i = 0; i < allPreparedPatients.size(); i++){
                    msg2.addReceiver( allPreparedPatients.get(i));
                }
                myAgent.send(msg2);
                mt = MessageTemplate.MatchConversationId("licitation-process2-" + myAgent.getLocalName());
                step = 3;
                break;
        }
    }

    @Override
    public boolean done() {

        return step==3;
    }
}
