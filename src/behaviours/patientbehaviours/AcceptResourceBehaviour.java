package behaviours.patientbehaviours;

import agents.Patient;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class AcceptResourceBehaviour extends CyclicBehaviour {
    Patient p;
    private MessageTemplate mt = MessageTemplate.MatchConversationId("licitation-process3");
    public AcceptResourceBehaviour(Patient p){
        this.p = p;
    }

    @Override
    public void action() {
        ACLMessage msg= myAgent.receive(mt);
        if (msg != null) {
            ACLMessage reply = msg.createReply();
            System.out.println("Agent agents.Patient " + myAgent.getName() + " received " + msg.getContent());
            if(p.getAvailability()){
                reply.setPerformative(ACLMessage.AGREE);
                reply.setContent(p.getTreatments().remove().name());
                System.out.println("I am " + myAgent.getLocalName() + " and I agree to start my treatment");
                myAgent.send(reply);
                ACLMessage unMsg = new ACLMessage(ACLMessage.INFORM);
                unMsg.setContent("Unsuscribe");
                unMsg.setConversationId("unSubscription-process");
                for (int i = 0; i < p.getCurrentResources().size(); ++i) {
                    unMsg.addReceiver(p.getCurrentResources().get(i));
                }
                myAgent.send(unMsg);
                p.setAvailability(false);
                p.setcResource(msg.getSender());
                p.finishTreatment();
            }else{
                reply.setPerformative(ACLMessage.REFUSE);
                reply.setContent("Not right now, sorry...");
                System.out.println("I am " + myAgent.getLocalName() + " and I will not start my treatment");
                myAgent.send(reply);
            }
        }else{
            block();
        }
    }
}
