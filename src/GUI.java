import agents.Patient;
import agents.Resource;
import javafx.scene.input.MouseEvent;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;

import static java.awt.Component.LEFT_ALIGNMENT;

public class GUI {


    private JFrame thisGUI;
    private JMenuBar menubar;
    private JTabbedPane tabbedPane;

    private JList<String> rList;
    private JList<String> rTreatmentsList;
    private JList<String> rPatientsList;

    private JLabel rCTreatment;
    private JLabel rCStatus;
    private JLabel rNextPatient;
    private JLabel rEmergency;

    private JList<String> pList;
    private JList<String> pTreatmentsList;
    private JList<String> pSubscribedList;

    private JLabel pDisease;
    private JLabel pPriority;
    private JLabel pEmergency;
    private JLabel pCResource;
    private JLabel pCStatus;
    private JLabel pNTreatment;



    private Hospital h;

    public GUI(Hospital h){
        this. h =h;
    }

    public JFrame getGUI() {
        return thisGUI;
    }

    private JComponent makeTextPanel(String text) {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
    }

    private void createMenuBar(){
        JMenuBar menubar = new JMenuBar();
        JMenu file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);

        JMenuItem addResource = new JMenuItem("Add Resource");
        addResource.addActionListener((ActionEvent event) -> {
            //h.createNewAgent(true);
        });
        file.add(addResource);

        JMenuItem eMenuItem = new JMenuItem("Exit");
        eMenuItem.setMnemonic(KeyEvent.VK_E);
        eMenuItem.setToolTipText("Exit application");
        eMenuItem.addActionListener((ActionEvent event) -> {
            System.exit(0);
        });

        file.add(eMenuItem);

        menubar.add(file);
        this.menubar = menubar;
    }

    private JPanel createResourceTab(){
        JPanel resourceTab = new JPanel();
        resourceTab.setPreferredSize(new Dimension(610,290));

        JPanel resourceList = new JPanel();
        JPanel resourceCharacters = new JPanel();
        JPanel resourceStatus = new JPanel();

        resourceTab.setLayout(new FlowLayout());

        resourceList.setPreferredSize(new Dimension(300,225));
        // resourceList.setBackground(Color.RED);

        String[] allNames = new String[this.h.getAllResources().size()];
        for (int i =0; i < this.h.getAllResources().size(); i++){
            allNames[i] = this.h.getAllResources().get(i).getLocalName();
        }

        //Obtaining all Resources

        JList list = createList(allNames);
        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                setResourceFields(list.getSelectedIndex());
            }
        });
        JScrollPane listScroller = createScroller(list);
        resourceList.setLayout(new BoxLayout(resourceList, BoxLayout.PAGE_AXIS));
        JLabel label = new JLabel("Resource List");
        label.setLabelFor(list);

        resourceList.add(label);
        resourceList.add(Box.createRigidArea(new Dimension(0,5)));
        resourceList.add(listScroller);
        resourceList.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        this.rList = list;



        //All Characteristics


        resourceCharacters.setPreferredSize(new Dimension(300,225));
        //resourceCharacters.setBackground(Color.BLUE);


        //List of treatments
        JList list2 = createList(allNames);
        JLabel treatmentsLabel = new JLabel("Available Treatments");
        JScrollPane listScroller2 = createScroller(list2);
        JPanel treatmentListPanel = new JPanel();
        treatmentListPanel.setLayout(new BoxLayout(treatmentListPanel, BoxLayout.PAGE_AXIS));
        treatmentsLabel.setLabelFor(list2);

        treatmentListPanel.setPreferredSize(new Dimension(270,115));
        treatmentListPanel.add(treatmentsLabel);
        treatmentListPanel.add(Box.createRigidArea(new Dimension(0,5)));
        treatmentListPanel.add(listScroller2);
        treatmentListPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        resourceCharacters.add(treatmentListPanel);
        this.rTreatmentsList = list2;

        //Last Patients Treated

        JList list3 = createList(null);
        JLabel patientsLabel = new JLabel("Subscribed Patients");
        JScrollPane listScroller3 = createScroller(list3);
        JPanel patientsListPanel = new JPanel();
        patientsListPanel.setLayout(new BoxLayout(patientsListPanel, BoxLayout.PAGE_AXIS));
        patientsLabel.setLabelFor(list3);

        patientsListPanel.setPreferredSize(new Dimension(270,100));
        patientsListPanel.add(patientsLabel);
        patientsListPanel.add(Box.createRigidArea(new Dimension(0,5)));
        patientsListPanel.add(listScroller3);
        patientsListPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        resourceCharacters.add(patientsListPanel);
        this.rPatientsList = list3;

        //LIst of status

        resourceStatus.setPreferredSize(new Dimension(605,50));
        //resourceStatus.setBackground(Color.GREEN);
        resourceStatus.setLayout(new FlowLayout(FlowLayout.LEFT));

        //Current Treatment
        JLabel currentTreatmentLabel = new JLabel("Current Treatment:");
        currentTreatmentLabel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel currentTreatment = new JLabel("None");
        currentTreatment.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        resourceStatus.add(currentTreatmentLabel);
        resourceStatus.add(currentTreatment);
        this.rCTreatment = currentTreatment;

        //Current Status

        JLabel currentStatusLabel = new JLabel("Current Status:");
        currentStatusLabel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel currentStatus = new JLabel("None");
        currentStatus.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        resourceStatus.add(currentStatusLabel);
        resourceStatus.add(currentStatus);
        this.rCStatus = currentStatus;



        //Next Patient

        JLabel nextPatientLabel = new JLabel("Next Patient:");
        nextPatientLabel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel nextPatient = new JLabel("None");
        nextPatient.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        resourceStatus.add(nextPatientLabel);
        resourceStatus.add(nextPatient);
        this.rNextPatient = nextPatient;

        //pEmergency

        JLabel EmergencyLabel = new JLabel("pEmergency!");
        nextPatientLabel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        EmergencyLabel.setVisible(false);
        resourceStatus.add(EmergencyLabel);
        this.rEmergency= EmergencyLabel;


        resourceTab.add(resourceList);
        resourceTab.add(resourceCharacters);
        resourceTab.add(resourceStatus);
        return resourceTab;
    }

    private JPanel createPatientTab(){
        JPanel patientTab = new JPanel();
        patientTab.setPreferredSize(new Dimension(610,290));

        JPanel patientList = new JPanel();
        JPanel patientCharacters = new JPanel();
        JPanel patientStatus = new JPanel();

        patientTab.setLayout(new FlowLayout());

        patientList.setPreferredSize(new Dimension(300,225));

        String[] data = new String[this.h.getAllPatients().size()];
        for (int i =0; i < this.h.getAllPatients().size(); i++){
            data[i] = this.h.getAllPatients().get(i).getLocalName();
        }

        //Obtaining all Resources

        JList list = createList(data);
        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                setPatientFields(list.getSelectedIndex());
            }
        });
        JScrollPane listScroller = createScroller(list);
        patientList.setLayout(new BoxLayout(patientList, BoxLayout.PAGE_AXIS));
        JLabel label = new JLabel("Patient List");
        label.setLabelFor(list);

        patientList.add(label);
        patientList.add(Box.createRigidArea(new Dimension(0,5)));
        patientList.add(listScroller);
        patientList.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        this.pList = list;



        //All Characteristics


        patientCharacters.setPreferredSize(new Dimension(300,225));
        patientCharacters.setLayout(new FlowLayout(FlowLayout.LEFT));
        //resourceCharacters.setBackground(Color.BLUE);


        //List of treatments
        JList list2 = createList(data);
        JLabel treatmentsLabel = new JLabel("Treatments Needed");
        JScrollPane listScroller2 = createScroller(list2);
        JPanel treatmentListPanel = new JPanel();
        treatmentListPanel.setLayout(new BoxLayout(treatmentListPanel, BoxLayout.PAGE_AXIS));
        treatmentsLabel.setLabelFor(list2);

        treatmentListPanel.setPreferredSize(new Dimension(270,80));
        treatmentListPanel.add(treatmentsLabel);
        treatmentListPanel.add(Box.createRigidArea(new Dimension(0,5)));
        treatmentListPanel.add(listScroller2);
        treatmentListPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        patientCharacters.add(treatmentListPanel);
        this.pTreatmentsList = list2;

        //Resources Subscribed to

        JList list3 = createList(null);
        JLabel aubscribedLabel = new JLabel("Resources Subscribed to:");
        JScrollPane listScroller3 = createScroller(list3);
        JPanel subscribedListPanel = new JPanel();
        subscribedListPanel.setLayout(new BoxLayout(subscribedListPanel, BoxLayout.PAGE_AXIS));
        aubscribedLabel.setLabelFor(list3);

        subscribedListPanel.setPreferredSize(new Dimension(270,80));
        subscribedListPanel.add(aubscribedLabel);
        subscribedListPanel.add(Box.createRigidArea(new Dimension(0,5)));
        subscribedListPanel.add(listScroller3);
        subscribedListPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        patientCharacters.add(subscribedListPanel);
        this.pSubscribedList = list3;

        //Disease
        JLabel diseaseLabel = new JLabel("Disease:");
        diseaseLabel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel disease = new JLabel("None");
        disease.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        patientCharacters.add(diseaseLabel);
        patientCharacters.add(disease);
        this.pDisease = disease;

        //Priority

        JLabel priorityLabel = new JLabel("Priority:");
        priorityLabel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel priority = new JLabel("None");
        priority.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        patientCharacters.add(priorityLabel);
        patientCharacters.add(priority);
        this.pPriority = priority;


        patientStatus.setPreferredSize(new Dimension(605,50));
        patientStatus.setLayout(new FlowLayout(FlowLayout.LEFT));

        //Next Treatment
        JLabel currentTreatmentLabel = new JLabel("Next Treatment:");
        currentTreatmentLabel.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));

        JLabel currentTreatment = new JLabel("None");
        currentTreatment.setBorder(BorderFactory.createEmptyBorder(3,3,3,3));
        patientStatus.add(currentTreatmentLabel);
        patientStatus.add(currentTreatment);
        this.pNTreatment = currentTreatment;

        //Current Status

        JLabel currentStatusLabel = new JLabel("Current Status:");
        currentStatusLabel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel currentStatus = new JLabel("None");
        currentStatus.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        patientStatus.add(currentStatusLabel);
        patientStatus.add(currentStatus);
        this.pCStatus = currentStatus;



        //CurrentResource

        JLabel currentResourceLabel = new JLabel("Current Resource:");
        currentResourceLabel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel currentResource = new JLabel("None");
        currentResource.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        patientStatus.add(currentResourceLabel);
        patientStatus.add(currentResource);
        this.pCResource = currentResource;

        //pEmergency

        JLabel EmergencyLabel = new JLabel("pEmergency!");
        EmergencyLabel.setVisible(false);
        patientCharacters.add(EmergencyLabel);
        this.pEmergency = EmergencyLabel;


        patientTab.add(patientList);
        patientTab.add(patientCharacters);
        patientTab.add(patientStatus);
        return patientTab;
    }

    private void createTabbedPane(){
        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel resourceTab = createResourceTab();

        tabbedPane.addTab("Resources", null, resourceTab,
                "Resource Tab");
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        JPanel patientTab = createPatientTab();

        tabbedPane.addTab("Patients", null, patientTab, "Patient Tab");
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
        tabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
        this.tabbedPane = tabbedPane;
    }

    private JList<String> createList(String[] data){
        DefaultListModel<String> listModel = new DefaultListModel<>();
        if(data != null){
            for (String aData : data) {
                listModel.addElement(aData);
            }}
        JList<String> list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);

        return list;
    }

    private JScrollPane createScroller (JList<String> list){
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setPreferredSize(new Dimension(250, 80));
        listScroller.setAlignmentX(LEFT_ALIGNMENT);
        return  listScroller;
    }

    private  void createAndShowGUI() {
        //Create and set up the window.

        JFrame frame = new JFrame("HospitalUI");
        thisGUI = frame;
        thisGUI.setResizable(false);
        thisGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createTabbedPane();



        createMenuBar();
        thisGUI.getContentPane().add(tabbedPane);
        thisGUI.setJMenuBar(menubar);

        //Display the window.
        thisGUI.pack();
        thisGUI.setVisible(true);
    }

    public void runGUI(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
                //createGUI();
            }
        });
    }

    public void addResource(String name){
        DefaultListModel l = (DefaultListModel<String>)this.rList.getModel();
        l.addElement(name);
        this.rList.setModel(l);
        this.thisGUI.revalidate();

    }



    private void setResourceFields(int index){
        DefaultListModel l = new DefaultListModel();
        Resource r = h.getAllResources().get(index);
        for(int i =0; i< r.getAvailableTreatments().size(); i++){
            l.addElement(r.getAvailableTreatments().get(i).name());
        }
        rTreatmentsList.setModel(l);
        l = new DefaultListModel();
        for(int i =0; i< r.getAllSubscribedPatients().size(); i++){
            l.addElement(r.getAllSubscribedPatients().get(i).getLocalName());
        }
        rPatientsList.setModel(l);

        if(r.getNextTreatment()!= null){
        rCTreatment.setText(r.getNextTreatment().name());}
        else{
            rCTreatment.setText("None");
        }

        rCStatus.setText(String.valueOf(r.getStatus()));

        if(r.getNextPatient()!= null){
        rNextPatient.setText(r.getNextPatient().getLocalName());}
        else{
            rNextPatient.setText("None");
        }

        if(r.getTreatingEmergency()){
            rEmergency.setVisible(true);
        }else{
            rEmergency.setVisible(false);
        }

        this.thisGUI.revalidate();


    }



    public void addPatient(String name){
        DefaultListModel l = (DefaultListModel<String>)this.pList.getModel();
        l.addElement(name);
        this.pList.setModel(l);
        this.thisGUI.revalidate();
    }

    private void setPatientFields(int index){
        DefaultListModel l = new DefaultListModel();
        Patient p = h.getAllPatients().get(index);
        for(int i =0; i< p.getTreatments().size(); i++){
            l.addElement(p.getTreatments().get(i).name());
        }
        pTreatmentsList.setModel(l);
        l = new DefaultListModel();
        for(int i =0; i< p.getCurrentResources().size(); i++){
            l.addElement(p.getCurrentResources().get(i).getLocalName());
        }
        pSubscribedList.setModel(l);


        pDisease.setText(p.getDisease().name());


        pPriority.setText(String.valueOf(p.getPriority()));

        if(p.getcResource()!= null){
            pCResource.setText(p.getcResource().getLocalName());}
        else{
            pCResource.setText("None");
        }

        pCStatus.setText(String.valueOf(p.getAvailability()));

        if(p.getTreatments().isEmpty()){
            pNTreatment.setText("Cured");
        }else{
            pNTreatment.setText(p.getTreatments().peek().name());
        }

        if(p.isEmergency()){
            pEmergency.setVisible(true);
        }else{
            rEmergency.setVisible(false);
        }

        this.thisGUI.revalidate();


    }

}
