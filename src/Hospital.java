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

        System.out.print("How many resources?");
        int num_patients = scanner.nextInt();

        //Cria container
        jade.core.Runtime runtime = jade.core.Runtime.instance();
        Profile profile = new ProfileImpl();
        profile.setParameter(Profile.MAIN_HOST, "localhost");
        profile.setParameter(Profile.GUI, "true");
        ContainerController containerController = runtime.createMainContainer(profile);
        AgentController ac = null;

        try {
            Object[] ob = new Object[2];
            ob[0] = "test";
            Object[] ob2 = new Object[4];
            ob[1]="test2";
            ob2[0] = "DISEASE1";
            ob2[1] = "test";
            ob2[2] = "test2";
            ob2[3] = "false";
            for(int i = 1; i <= num_patients; i++){
                ac = containerController.createNewAgent("r"+i, "agents.Resource",  ob);
                ac.start();
            }
           //ac = containerController.createNewAgent("rFalso", "agents.Resource",  ob2);
           //ac.start();
           AgentController ac2 = containerController.createNewAgent("P", "agents.Patient",  ob2);
           ac2.start();
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }

    }
}
