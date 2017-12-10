import agents.Patient;
import agents.Resource;
import com.sun.org.apache.xpath.internal.operations.Bool;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import properties.Disease;
import properties.Treatment;

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

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

        Resource r = new Resource(args,false,GUI.getGUI());
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
            Patient p = new Patient(args[0], x , Boolean.getBoolean(args[1]),false);
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

    public void createNewPatient(){
        Disease s = Disease.randomDisease();
        int randomNum = ThreadLocalRandom.current().nextInt(0, 1 + 1);
        int randomNum2 = ThreadLocalRandom.current().nextInt(1, Treatment.values().length + 1);
        int l = randomNum2 + 2;
        String[] args = new String[l];
        args[0] = s.name();
        if(randomNum == 0){
            args[1] = "true";
        }else{
            args[1] = "false";
        }

        for(int i = 0; i < randomNum2; i++){
            args[i + 2] = Treatment.randomTreatment().name();
        }

        createNewAgent(false, "p" + allPatients.size(), args);
    }

    public void createNewResource(){
        int randomNum = ThreadLocalRandom.current().nextInt(1, Treatment.values().length + 1);
        String[] args = new String[randomNum];
        for(int i = 0; i < randomNum; i++){
            args[i] = Treatment.randomTreatment().name();
        }

        createNewAgent(true, "r" + allResources.size(), args);
    }

    public void startFCFSSim(){
        int num_resource = 2;
        int num_patients = 3;

        jade.core.Runtime runtime = jade.core.Runtime.instance();
        Profile profile = new ProfileImpl();
        profile.setParameter(Profile.MAIN_HOST, "localhost");
        profile.setParameter(Profile.GUI, "true");
        containerController = runtime.createMainContainer(profile);
        AgentController ac = null;


        String[] ob = new String[3];
        ob[0] = "Acupunture";
        String[] ob2 = new String[5];
        ob[1]="Adenosine";
        ob[2] = "MCH";
        ob2[0] = "Tonsillitis";
        ob2[1] = "false";
        ob2[2] = "Acupunture";
        ob2[3] = "MCH";
        ob2[4] = "Adenosine";
        try {
            for(int i = 0; i < num_resource; i++){
                Resource r = new Resource(ob,true,GUI.getGUI());
                try {
                    ac = containerController.acceptNewAgent("r" + i, r);
                    ac.start();
                    ac.activate();
                    allResources.add(r);
                    GUI.addResource(r.getLocalName());
                } catch (StaleProxyException e) {
                    e.printStackTrace();
                }
                Thread.sleep(1000);
            }
            for(int i = 0; i < num_patients; i++){
                String[] x = Arrays.copyOfRange(ob2,2,ob2.length);
                Patient p = new Patient(ob2[0], x , Boolean.getBoolean(ob2[1]),true);
                try {
                    ac = containerController.acceptNewAgent("p" + i, p);
                    ac.start();
                    ac.activate();
                    allPatients.add(p);
                    GUI.addPatient(p.getLocalName());
                } catch (StaleProxyException e) {
                    e.printStackTrace();
                }
                Thread.sleep(1000);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }




    }

    public void startSim(){
        int num_resource = 20;
        int num_patients = 20;

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
        try {
        for(int i = 0; i < num_resource; i++){
            createNewResource();
            Thread.sleep(1000);
        }
            for(int i = 0; i < num_patients; i++){
                createNewPatient();
                Thread.sleep(1000);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }




    }

    public Hospital()
    {
        this.GUI = new GUI(this);
        GUI.runGUI(null);

        //startSim();
        startFCFSSim();


    }

    public static void main(String args[]){
        Hospital h = new Hospital();
    }
}

