import agents.Patient;
import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

import java.util.Scanner;

public class Hospital {
    public static void main(String [] args)
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("How many patients?");
        int num_patients = scanner.nextInt();

        //Cria container
        jade.core.Runtime runtime = jade.core.Runtime.instance();
        Profile profile = new ProfileImpl();
        profile.setParameter(Profile.MAIN_HOST, "localhost");
        profile.setParameter(Profile.GUI, "true");
        ContainerController containerController = runtime.createMainContainer(profile);

        AgentController ac = null;
        try {
            for(int i = 1; i <= num_patients; i++){
                ac = containerController.createNewAgent("P"+i, "agents.Patient",  null);
            }
            ac.start();
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
        return;
    }
}