package behaviours;

import agents.Resource;
import utils.PrioritiesComparator;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class ResourceBehaviour extends Behaviour {
    private int t = 0;

    Resource r;
    int step = 0;
    int getAllProposals = 0;
    double bestPriority = 0;
    Comparator<Pair<AID,Double>> comparator = new PrioritiesComparator();
    PriorityQueue<Pair<AID,Double>> patientPriorities = new PriorityQueue<>(1000,comparator);
    ArrayList<AID> allPreparedPatients = new ArrayList<>();
        private MessageTemplate mt;
    public ResourceBehaviour(Resource r){

        this.r = r;

    }

    @Override
    public void action() {

        switch(step){
            case 0:
                getAllProposals = r.getAllPatients().size();
                ACLMessage msg = new ACLMessage(ACLMessage.PROPOSE);
                msg.setLanguage("English");
                msg.setContent("When can you come?");
                msg.setPerformative(ACLMessage.PROPOSE);
                msg.setConversationId("licitation-process1");
                for(int i = 0; i < r.getAllPatients().size(); i++){
                    msg.addReceiver( r.getAllPatients().get(i));
                }
                myAgent.send(msg);
                System.out.println(myAgent.getName() + " sent a message");
                mt = MessageTemplate.MatchConversationId("licitation-process1");
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
                msg2.setConversationId("licitation-process2");
                for(int i = 0; i < allPreparedPatients.size(); i++){
                    msg2.addReceiver( allPreparedPatients.get(i));
                }
                myAgent.send(msg2);
                mt = MessageTemplate.MatchConversationId("licitation-process2");
                step = 3;
                break;
            case 3:
                if(getAllProposals == allPreparedPatients.size()){
                    step = 4;
                }
                else{
                    ACLMessage reply = myAgent.receive(mt);
                    if(reply != null){
                        if(reply.getPerformative() == ACLMessage.PROPOSE){
                            Pair<AID,Double> patientPair = new Pair<>(reply.getSender(),Double.valueOf(reply.getContent()));
                            /*if(Double.valueOf(reply.getContent()) > bestPriority){
                                r.setNextPatient(reply.getSender());
                                bestPriority = Double.valueOf(reply.getContent());
                                System.out.println("I found a better priority: " + bestPriority);
                            }*/
                            patientPriorities.offer(patientPair);
                            getAllProposals++;
                        }
                    }else{
                        block();
                    }
                }

                break;
            case 4:
                ACLMessage msg3 = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
                msg3.setLanguage("English");
                msg3.setContent("You have been Accepted!");
                msg3.setConversationId("licitation-process3");
                msg3.addReceiver( patientPriorities.poll().getKey());

                myAgent.send(msg3);
                mt = MessageTemplate.MatchConversationId("licitation-process3");
                step = 5;
                break;
            case 5:
                ACLMessage reply = myAgent.receive(mt);
                if(reply != null){
                    if(reply.getPerformative() == ACLMessage.AGREE){
                        r.setNextPatient(reply.getSender());
                        step = 6;
                    }else if(reply.getPerformative() == ACLMessage.REFUSE){
                        step = 4;
                    }
                }else{
                    block();
                }
                break;

        }
    }

    @Override
    public boolean done() {

        return step==6;
    }
}
