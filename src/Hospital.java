import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

import java.util.Scanner;

public class Hospital {
    public static void main(String [] args)
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("How many patients?");
        int num_patients = scanner.nextInt();

        //Cria container
        jade.core.Runtime runtime = jade.core.Runtime.instance();
        Profile profile = new ProfileImpl();
        profile.setParameter(Profile.MAIN_HOST, "localhost");
        profile.setParameter(Profile.GUI, "true");
        ContainerController containerController = runtime.createMainContainer(profile);
        AgentController ac = null;

        try {
            Object[] ob = new Object[1];
            ob[0] = "test";

            for(int i = 1; i <= num_patients; i++){
                ac = containerController.createNewAgent("r"+i, "agents.Resource",  ob);
                ac.start();
            }
           //ac = containerController.createNewAgent("P", "agents.Patient",  null);
            // ac.start();
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }

    }
}
