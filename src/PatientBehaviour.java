import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class PatientBehaviour extends CyclicBehaviour {

    @Override
    public void action() {
        ACLMessage msg= myAgent.receive();

        if (msg != null) {
            // Message received. Process it
            String msgContents = msg.getContent();

            System.out.println("Agent Patient " + myAgent.getName() + " received " + msgContents);
        }
        else{
           // System.out.println("no message");
        }
    }




}
