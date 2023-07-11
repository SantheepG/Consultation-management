package GUI.Main_Frames.SubFrames;

import GUI.MainFrame;
import GUI.Main_Frames.PatientsPanel;
import Classes.Patient;
import Classes.Person;
import Classes.WestminsterSkinConsultationManager;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class FindPatient extends JFrame implements ActionListener, MouseListener, DocumentListener {
    String fName, lName, patientId;
    JFrame mainFrame;
    JLabel fNameLabel, lNameLabel, patientIdLabel;
    JTextField fNameField, lNameField, patientIdField;
    JPanel topPanel, topPanelUp, topPanelDown, bottomPanel;
    JButton findPatient;
    static JTable patientTable;
    JScrollPane scrollPane;
    JButton edit, delete;
    public FindPatient() {
        fName = "";
        lName = "";
        patientId = "";

        mainFrame = new JFrame();
        mainFrame.setTitle("Find Patient");
        mainFrame.setSize(800, 500);
        mainFrame.setLayout(new FlowLayout());
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(MainFrame.getFrames()[0]);


        // Top Panel
        topPanel = new JPanel(new FlowLayout());
        topPanel.setPreferredSize(new Dimension(800, 100));
        topPanel.setOpaque(true);

        // Top Panel Up
        fNameLabel = new JLabel("First Name");
        fNameLabel.setPreferredSize(new Dimension(150, 20));
        lNameLabel = new JLabel("Last Name");
        lNameLabel.setPreferredSize(new Dimension(150, 20));
        patientIdLabel = new JLabel("Patient ID");
        patientIdLabel.setPreferredSize(new Dimension(250, 20));

        topPanelUp = new JPanel(new FlowLayout());
        topPanelUp.setPreferredSize(new Dimension(800, 20));
        topPanelUp.add(fNameLabel);
        topPanelUp.add(lNameLabel);
        topPanelUp.add(patientIdLabel);
        topPanel.add(topPanelUp);

        // Top Panel Down
        topPanelDown = new JPanel(new FlowLayout());
        topPanelDown.setPreferredSize(new Dimension(800, 35));
        fNameField = new JTextField();
        fNameField.setPreferredSize(new Dimension(150, 30));
        fNameField.getDocument().addDocumentListener(this);

        lNameField = new JTextField();
        lNameField.setPreferredSize(new Dimension(150, 30));
        lNameField.getDocument().addDocumentListener(this);

        patientIdField = new JTextField();
        patientIdField.setPreferredSize(new Dimension(250, 30));
        patientIdField.getDocument().addDocumentListener(this);

        topPanelDown.add(fNameField);
        topPanelDown.add(lNameField);
        topPanelDown.add(patientIdField);
        topPanel.add(topPanelDown);

        // Find Doctor Button
        findPatient = new JButton("Find Patient");
        findPatient.setPreferredSize(new Dimension(150, 30));
        findPatient.addActionListener(this);
        findPatient.addMouseListener(this);
        findPatient.setForeground(new Color(164, 92, 255));
        findPatient.setFont(new Font("Arial", Font.PLAIN, 14));
        findPatient.setBorder(BorderFactory.createLineBorder(new Color(164, 92, 255), 2));
        topPanel.add(findPatient);
        mainFrame.add(topPanel);


        // Bottom Panel
        bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setPreferredSize(new Dimension(800, 400));

        String[] patientsTableColumns = {"Patient ID", "First Name", "Last Name","Age","Gender", "Phone Number"};
        ArrayList<Person> patients = WestminsterSkinConsultationManager.getPatientArrayList();
        String[][] patientsData = new String[patients.size()][6];

        for (int i = 0; i < patients.size(); i++) {
            Patient patient = (Patient) WestminsterSkinConsultationManager.getPatientArrayList().get(i);
            patientsData[i][0] = patient.getPatientId();
            patientsData[i][1] = patient.getName();
            patientsData[i][2] = patient.getSurName();
            patientsData[i][3] = String.valueOf(patient.getAge());
            patientsData[i][4] = patient.getGender();
            patientsData[i][5] = patient.getMobileNumber();
        }

        patientTable = new JTable();
        patientTable.setPreferredScrollableViewportSize(new Dimension(750, 300));
        patientTable.setModel(new DefaultTableModel(patientsData, patientsTableColumns));
        patientTable.setRowHeight(30);
        patientTable.setFont(new Font("Arial", Font.PLAIN, 14));
        patientTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        patientTable.setShowGrid(true);
        patientTable.setGridColor(new Color(234, 214, 255));
        patientTable.setModel(new DefaultTableModel(patientsData, patientsTableColumns));
        JTableHeader header = patientTable.getTableHeader();
        header.setBackground(new Color(30, 0, 70));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.PLAIN, 14));
        header.setPreferredSize(new Dimension(750, 30));

        /**
         * This is to get values from selected row from the table
         */
        patientTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int viewRow = patientTable.getSelectedRow();

                if (!e.getValueIsAdjusting() && viewRow != -1) {
                    int columnIndex = 0;
                    int modelRow = patientTable.convertRowIndexToModel(viewRow);
                    Object patientId = patientTable.getModel().getValueAt(modelRow, columnIndex);
                    JDialog dialog = new JDialog(mainFrame, "Patient Details", true);
                    dialog.setSize(400, 100);
                    dialog.setLayout(new FlowLayout());
                    dialog.setResizable(false);
                    dialog.setLocationRelativeTo(FindPatient.getFrames()[0]);

                    // Edit and Delete Buttons
                    edit = new JButton("Edit");
                    edit.setPreferredSize(new Dimension(150, 50));
                    edit.setForeground(new Color(0, 122, 31));
                    edit.setOpaque(true);
                    edit.setFont(new Font("Arial", Font.PLAIN, 16));
                    edit.setBorder(BorderFactory.createLineBorder(new Color(0, 122, 31), 2));
                    edit.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            edit.setForeground(Color.WHITE);
                            edit.setBackground(new Color(0, 122, 31));
                            edit.setOpaque(true);
                        }
                        @Override
                        public void mouseExited(MouseEvent e) {
                            edit.setForeground(new Color(0, 122, 31));
                            edit.setBackground(Color.WHITE);
                            edit.setOpaque(true);
                        }
                    });
                    edit.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            dialog.dispose();
                            new EditPatient(patientId.toString());
                        }
                    });

                    delete = new JButton("Delete");
                    delete.setPreferredSize(new Dimension(150, 50));
                    delete.setForeground(Color.RED);
                    delete.setOpaque(true);
                    delete.setFont(new Font("Arial", Font.PLAIN, 16));
                    delete.setBorder(BorderFactory.createLineBorder(new Color(255, 0, 0), 2));
                    delete.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            delete.setForeground(Color.WHITE);
                            delete.setBackground(Color.RED);
                            delete.setOpaque(true);
                        }
                        @Override
                        public void mouseExited(MouseEvent e) {
                            delete.setForeground(Color.RED);
                            delete.setBackground(Color.WHITE);
                            delete.setOpaque(true);
                        }
                    });
                    delete.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Person patient = null;
                            for (int i = 0; i < WestminsterSkinConsultationManager.patientArrayList.size(); i++) {
                                Patient p = (Patient) WestminsterSkinConsultationManager.patientArrayList.get(i);
                                if (p.getPatientId().equals(patientId)) {
                                    patient = WestminsterSkinConsultationManager.patientArrayList.get(i);
                                }
                            }
                            int dialogButton = JOptionPane.YES_NO_OPTION;
                            int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete Patient "+ patient.getName()+" "+patient.getSurName()+ " ?", "Warning", dialogButton);

                            if (dialogResult == JOptionPane.YES_OPTION) {
                                WestminsterSkinConsultationManager manager = new WestminsterSkinConsultationManager();
                                manager.deleteAPatient(patientId.toString());
                                PatientsPanel.tableReRender(WestminsterSkinConsultationManager.getPatientArrayList());
                                dialog.dispose();
                                mainFrame.dispose();
                                new FindPatient();
                            }
                        }
                    });
                    dialog.add(edit);
                    dialog.add(delete);
                    dialog.setVisible(true);
                }
            }
        });

        scrollPane = new JScrollPane(patientTable);

        bottomPanel.add(scrollPane);
        mainFrame.add(bottomPanel);

        mainFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == findPatient) {
            ArrayList<Person> patients = WestminsterSkinConsultationManager.getPatientArrayList();
            ArrayList<Person> filteredPatients = new ArrayList<>();

            for (Person patient : patients) {
                Patient tempPatient = (Patient) patient;
                if (patient.getName().equalsIgnoreCase(fName) && patient.getSurName().equalsIgnoreCase(lName) || tempPatient.getPatientId().equalsIgnoreCase(patientId)) {
                    filteredPatients.add(patient);
                }
            }
            tableReRender(filteredPatients);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == findPatient) {
            findPatient.setForeground(new Color(164, 92, 255));
            findPatient.setBackground(Color.WHITE);
            findPatient.setOpaque(true);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getSource() == findPatient) {
            findPatient.setForeground(Color.WHITE);
            findPatient.setBackground(new Color(164, 92, 255));
            findPatient.setOpaque(true);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == findPatient) {
            findPatient.setForeground(Color.WHITE);
            findPatient.setBackground(new Color(164, 92, 255));
            findPatient.setOpaque(true);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == findPatient) {
            findPatient.setForeground(new Color(164, 92, 255));
            findPatient.setBackground(Color.WHITE);
            findPatient.setOpaque(true);
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        if (e.getDocument() == fNameField.getDocument()) {
            fName = fNameField.getText();
            updateTable(fName, lName, patientId);
        } else if (e.getDocument() == lNameField.getDocument()) {
            lName = lNameField.getText();
            updateTable(fName, lName, patientId);
        } else if (e.getDocument() == patientIdField.getDocument()) {
            patientId = patientIdField.getText();
            updateTable(fName, lName, patientId);
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {

    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        if (e.getDocument() == fNameField.getDocument()) {
            fName = fNameField.getText();
            updateTable(fName, lName, patientId);
        } else if (e.getDocument() == lNameField.getDocument()) {
            lName = lNameField.getText();
            updateTable(fName, lName, patientId);
        } else if (e.getDocument() == patientIdField.getDocument()) {
            patientId = patientIdField.getText();
            updateTable(fName, lName, patientId);
        }
    }

    public void updateTable(String fName, String lName, String patientId) {
        ArrayList<Person> patients = WestminsterSkinConsultationManager.getPatientArrayList();
        ArrayList<Person> filteredPatients = new ArrayList<>();

        for (Person patient : patients) {
            Patient p = (Patient) patient;
            if (patient.getName().equalsIgnoreCase(fName) && patient.getSurName().equalsIgnoreCase(lName) || p.getPatientId().equalsIgnoreCase(patientId)) {
                filteredPatients.add(patient);
            }
        }
        tableReRender(filteredPatients);
    }

    public static void tableReRender(ArrayList<Person> filteredPatients){
        String[][] filteredPatientsTableData = new String[filteredPatients.size()][6];
        for (int i = 0; i < filteredPatients.size(); i++) {
            Patient patient = (Patient) filteredPatients.get(i);
            filteredPatientsTableData[i][0] = patient.getPatientId();
            filteredPatientsTableData[i][1] = patient.getName();
            filteredPatientsTableData[i][2] = patient.getSurName();
            filteredPatientsTableData[i][3] = String.valueOf(patient.getAge());
            filteredPatientsTableData[i][4] = patient.getGender();
            filteredPatientsTableData[i][5] = patient.getMobileNumber();
        }
        String[] patientsTableColumns = {"Patient ID", "First Name", "Last Name","Age","Gender", "Phone Number"};
        patientTable.setModel(new DefaultTableModel(filteredPatientsTableData, patientsTableColumns));
    }
}
