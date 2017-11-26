package behaviours;

import jade.core.AID;
import jade.core.behaviours.SimpleBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

import java.util.ArrayList;

public class FindResourcesBehaviour extends SimpleBehaviour {

    ArrayList<String> treatmentsList = new ArrayList<>();

    public FindResourcesBehaviour(ArrayList<String> treatmentsList){
        this.treatmentsList = treatmentsList;
    }

    @Override
    public void action() {
        DFAgentDescription template = new DFAgentDescription();
        for(int i = 0; i< treatmentsList.size(); i++){

            ServiceDescription sd = new ServiceDescription();
            sd.setType("treatment");
            sd.setName(treatmentsList.get(i));
            template.addServices(sd);

        }
        try {
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            msg.setLanguage("English");
            msg.setContent("Suscribe");

            DFAgentDescription[] result = DFService.search(myAgent, template);
            for (int i = 0; i < result.length; ++i) {
                System.out.println(result[i].getName().getLocalName());
                msg.addReceiver(result[i].getName());

            }
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
