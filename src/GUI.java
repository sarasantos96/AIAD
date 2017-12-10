import javafx.scene.input.MouseEvent;
import properties.Treatment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;

import static java.awt.Component.LEFT_ALIGNMENT;

public class GUI {

    private JFrame thisGUI;
    private JMenuBar menubar;
    private JTabbedPane tabbedPane;

    private JList<String> resourceList;
    private JList<String> rTreatmentsList;
    private JList<String> rPatientsList;

    private JLabel rCTreatment;
    private JLabel rCStatus;
    private JLabel rNextPatient;
    private JLabel rEmergency;

    

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
            h.createNewAgent();
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

        String[] data = new String[30];
        for(int i = 0; i< 30; i++){
            data[i] = "r" + i;
        }

        //Obtaining all Resources

        JList list = createResourceList(data);
        JScrollPane listScroller = createScroller(list);
        resourceList.setLayout(new BoxLayout(resourceList, BoxLayout.PAGE_AXIS));
        JLabel label = new JLabel("Resource List");
        label.setLabelFor(list);

        resourceList.add(label);
        resourceList.add(Box.createRigidArea(new Dimension(0,5)));
        resourceList.add(listScroller);
        resourceList.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));



        //All Characteristics


        resourceCharacters.setPreferredSize(new Dimension(300,225));
        //resourceCharacters.setBackground(Color.BLUE);


        //List of treatments
        JList list2 = createList(data);
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

        //Last Patients Treated

        JList list3 = createList(null);
        JLabel patientsLabel = new JLabel("Last treatment");
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

        //Current Status

        JLabel currentPatientLabel = new JLabel("Current Patient:");
        currentPatientLabel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel currentPatient = new JLabel("None");
        currentPatient.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        resourceStatus.add(currentPatientLabel);
        resourceStatus.add(currentPatient);

        //Next Patient

        JLabel nextPatientLabel = new JLabel("Next Patient:");
        nextPatientLabel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JLabel nextPatient = new JLabel("None");
        nextPatient.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        resourceStatus.add(nextPatientLabel);
        resourceStatus.add(nextPatient);

        //Emergency

        JLabel EmergencyLabel = new JLabel("Emergency!");
        nextPatientLabel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        EmergencyLabel.setVisible(false);
        resourceStatus.add(EmergencyLabel);


        resourceTab.add(resourceList);
        resourceTab.add(resourceCharacters);
        resourceTab.add(resourceStatus);
        return resourceTab;
    }

    private void createTabbedPane(){
        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel resourceTab = createResourceTab();

        tabbedPane.addTab("Resources", null, resourceTab,
                "Does nothing");
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);


        //tabbedPane.addTab("Tab 2", null, paneleiro,
             //   "Does twice as much nothing");
        //tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);
        //tabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
        this.tabbedPane = tabbedPane;
    }

    private JList<String> createResourceList(String[] data){
        DefaultListModel<String> listModel = new DefaultListModel<>();
        if(data != null){
        for (String aData : data) {
            listModel.addElement(aData);
        }}
        JList<String> list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);
        list.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                   //Add function for list
                }
            }
        });
        return list;
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

}
