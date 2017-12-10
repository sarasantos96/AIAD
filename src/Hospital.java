import agents.Patient;
import agents.Resource;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Hospital {
    private GUI GUI;
    private ContainerController containerController;
    private ArrayList<Resource> allResources = new ArrayList<>();

    private ArrayList<Patient> allPatients = new ArrayList<>();

    public void createNewAgent(boolean s){
        String[] ob = new String[2];
        ob[0] = "test";
        ob[1]="test2";
        AgentController ac = null;
        if(true){
        Resource r = new Resource(ob,GUI.getGUI());
        try {
            ac = containerController.acceptNewAgent("r", r);
            ac.start();
            ac.activate();
        } catch (StaleProxyException e) {
            e.printStackTrace();
        } /*}else{
            Patient p = new Patient(ob,GUI.getGUI());
            try {
                ac = containerController.acceptNewAgent("r", p);
                ac.start();
                ac.activate();
            } catch (StaleProxyException e) {
                e.printStackTrace();
            }
        }*/

    }}

    private void startSim(){
        int num_patients = 2;

        //Cria container
        jade.core.Runtime runtime = jade.core.Runtime.instance();
        Profile profile = new ProfileImpl();
        profile.setParameter(Profile.MAIN_HOST, "localhost");
        profile.setParameter(Profile.GUI, "true");
        containerController = runtime.createMainContainer(profile);
        AgentController ac = null;

        try {
            String[] ob = new String[2];
            ob[0] = "test";
            Object[] ob2 = new Object[4];
            ob[1]="test2";
            ob2[0] = "DISEASE1";
            ob2[1] = "test";
            ob2[2] = "test2";
            ob2[3] = "false";
            for(int i = 1; i <= num_patients; i++){

                Resource r = new Resource(ob,GUI.getGUI());

                ac = containerController.acceptNewAgent("r"+i, r);
                ac.start();
                ac.activate();
            }
            //ac = containerController.createNewAgent("rFalso", "agents.Resource",  ob2);
            //ac.start();
            AgentController ac2 = containerController.createNewAgent("P", "agents.Patient",  ob2);
            ac2.start();
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
    }

    public Hospital()
    {
        this.GUI = new GUI(this);
        GUI.runGUI(null);

        startSim();


    }

    public static void main(String args[]){
        Hospital h = new Hospital();
    }
}

