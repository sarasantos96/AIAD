import agents.Patient;
import agents.Resource;
import com.sun.org.apache.xpath.internal.operations.Bool;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Hospital {
    private GUI GUI;
    private ContainerController containerController;
    private ArrayList<Resource> allResources = new ArrayList<>();

    private ArrayList<Patient> allPatients = new ArrayList<>();

    public ArrayList<Resource> getAllResources() {
        return allResources;
    }

    public ArrayList<Patient> getAllPatients() {
        return allPatients;
    }

    public void createNewAgent(boolean s, String name , String[] args){

        AgentController ac = null;
        if(s){

        Resource r = new Resource(args,GUI.getGUI());
        try {
            ac = containerController.acceptNewAgent(name, r);
            ac.start();
            ac.activate();
            allResources.add(r);
            GUI.addResource(r.getLocalName());
        } catch (StaleProxyException e) {
            e.printStackTrace();
        } }else{
            String[] x = Arrays.copyOfRange(args,2,args.length);
            Patient p = new Patient(args[0], x , Boolean.getBoolean(args[1]));
            try {
                ac = containerController.acceptNewAgent(name, p);
                ac.start();
                ac.activate();
                allPatients.add(p);
                GUI.addPatient(p.getLocalName());
            } catch (StaleProxyException e) {
                e.printStackTrace();
            }
        }

    }

    private void startSim(){
        int num_resource = 2;
        int num_patients = 5;

        jade.core.Runtime runtime = jade.core.Runtime.instance();
        Profile profile = new ProfileImpl();
        profile.setParameter(Profile.MAIN_HOST, "localhost");
        profile.setParameter(Profile.GUI, "true");
        containerController = runtime.createMainContainer(profile);
        AgentController ac = null;


        String[] ob = new String[2];
        ob[0] = "test";
        String[] ob2 = new String[4];
        ob[1]="test2";
        ob2[0] = "DISEASE1";
        ob2[1] = "false";
        ob2[2] = "test";
        ob2[3] = "test2";
        for(int i = 0; i < num_resource; i++){
            createNewAgent(true,"r" + i, ob);
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < num_patients; i++){
                createNewAgent(false,"p" + i, ob2);
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

