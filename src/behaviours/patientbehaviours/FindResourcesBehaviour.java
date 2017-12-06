package behaviours.patientbehaviours;

import agents.Patient;
import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;
import java.util.LinkedList;

public class FindResourcesBehaviour extends SimpleBehaviour {

    Patient p;

    public FindResourcesBehaviour(Patient p){
        this.p = p;
    }

    @Override
    public void action() {
        p.setAvailability(true);
        DFAgentDescription template = new DFAgentDescription();
        //for(int i = 0; i< treatmentsList.size(); i++){

            ServiceDescription sd = new ServiceDescription();
            sd.setType("treatment");
            sd.setName(p.getTreatments().peek().name());
            template.addServices(sd);

        //}
        try {
            ACLMessage msg = new ACLMessage(ACLMessage.SUBSCRIBE);
            if(p.isEmergency()){
                msg.setPerformative(ACLMessage.INFORM);
            }
            msg.setLanguage("English");
            msg.setContent("Suscribe");

            DFAgentDescription[] result = DFService.search(myAgent, template);
            for (int i = 0; i < result.length; ++i) {
                System.out.println(result[i].getName().getLocalName());
                msg.addReceiver(result[i].getName());
                p.addResourceUse(result[i].getName());

            }
            msg.setConversationId("subscriber-process");
            myAgent.send(msg);


        }
        catch (FIPAException fe) {
            fe.printStackTrace();
        }
    }

    @Override
    public boolean done() {
        return true;
    }
}
