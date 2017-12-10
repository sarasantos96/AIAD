package behaviours.resourcebehaviours;

import agents.Resource;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import properties.Treatment;

import java.util.Date;


public class FCFS extends SimpleBehaviour {

    private MessageTemplate mt = MessageTemplate.MatchConversationId("subscriber-process");
    private MessageTemplate mt2 = MessageTemplate.MatchConversationId("finalize-process");
    Resource r;
    private boolean bs = false;
    int step =0;

    public FCFS(Resource r){
        this.r = r;
    }

    @Override
    public void action() {

        switch(step){
            case 0:
            ACLMessage msg= myAgent.receive(mt);
             if (msg != null) {
                 bs = false;
                 // Message received. Process it
                 String msgContents = msg.getContent();

                 System.out.println("I, " + myAgent.getName() + " justt received " + msgContents);
                 ACLMessage reply = msg.createReply();
                 if(!r.isFCFSReady()){
                     reply.setPerformative(ACLMessage.REFUSE);
                     reply.setContent("I am not ready...");
                     System.out.println("I " + myAgent.getLocalName() + " am not ready!");
                     step = 2;
                 }else{
                     reply.setPerformative(ACLMessage.AGREE);
                     reply.setConversationId("FCFS");
                     reply.setContent("I am ready!");

                     myAgent.send(reply);
                     System.out.println("I " + myAgent.getLocalName() + " am ready!");
                     r.setNextTreatment(Treatment.valueOf(msg.getContent()));

                     r.setFCFSReady(false);
                     step = 1;

                 }

             }
             else{
                 block();
             }
             break;
            case 1:
                ACLMessage msg2= myAgent.receive(mt2);
                if(bs ){
                    step = 0;
                    r.setFCFSReady(true);
                }
                if (msg2 != null) {
                    String msgContents = msg2.getContent();

                    System.out.println("I, " + myAgent.getName() + " just received " + msgContents);

                    if(msg2.getPerformative()== ACLMessage.ACCEPT_PROPOSAL){
                        r.FCFSStartTreatment();
                        step = 2;
                    }else{
                        step =0;
                        r.setFCFSReady(true);
                    }
                }else{

                    block(3000);
                    bs = true;
                }
        }
    }

    @Override
    public boolean done() {
        return step == 2;
    }
}
