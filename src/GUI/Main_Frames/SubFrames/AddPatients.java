package GUI.Main_Frames.SubFrames;

import GUI.MainFrame;
import GUI.Main_Frames.PatientsPanel;
import GUI.GUIModels.DatePicker;
import Classes.Patient;
import Classes.Person;
import Classes.WestminsterSkinConsultationManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.Objects;

import static GUI.MainFrame.addSpace;
import static GUI.MainFrame.scaleImage;

public class AddPatients extends JDialog implements ActionListener {
    final JFrame mainFrame;
    JPanel mainPanel;
    JPanel[] panels = new JPanel[6];
    JPanel[] mainBorderLayouts = new JPanel[3];
    JLabel[] labels = new JLabel[6];
    JTextField fNameField, lNameField, mobileNumField, patientIdField;
    JComboBox genderDropDown;
    final JTextField dateOfBirthField = new JTextField();
    JTextField[] textFields = new JTextField[4];
    JButton addPatient, cancel;

    public AddPatients() {
        // Main Frame
        mainFrame = new JFrame();
        mainFrame.setSize(520, 400);
        mainFrame.setLocationRelativeTo(MainFrame.getFrames()[0]);
        mainPanel = new JPanel(new FlowLayout());

        // Initialise 3 border panels
        for (int i = 0; i < mainBorderLayouts.length; i++) {
            mainBorderLayouts[i] = new JPanel(new BorderLayout());
            mainBorderLayouts[i].setPreferredSize(new Dimension(500, 70));
            mainBorderLayouts[i].setOpaque(false);
        }

        // Initialise 6 panels
        for (int i = 0; i < 6; i++) {
            panels[i] = new JPanel(new FlowLayout());
            panels[i].setPreferredSize(new Dimension(220, 200));
            panels[i].setOpaque(false);
        }

        // Initialise labels
        for (int i = 0; i < 6; i++) {
            labels[i] = new JLabel();
            labels[i].setPreferredSize(new Dimension(200, 20));
            labels[i].setOpaque(false);
            labels[i].setFont(new Font("Arial", Font.PLAIN, 16));
        }
        labels[0].setText("First Name");
        labels[1].setText("Last Name");
        labels[2].setText("Date of Birth");
        labels[3].setText("Mobile Number");
        labels[4].setText("Patient ID");
        labels[5].setText("Gender");

        // Initialise text fields
        fNameField = new JTextField();
        lNameField = new JTextField();
        mobileNumField = new JTextField();
        patientIdField = new JTextField();

        textFields[0] = fNameField;
        textFields[1] = lNameField;
        textFields[2] = mobileNumField;
        textFields[3] = patientIdField;

        // Text field settings
        for (int i = 0; i < 4; i++) {
            textFields[i].setPreferredSize(new Dimension(220, 30));
            textFields[i].setOpaque(true);
            textFields[i].setFont(new Font("Arial", Font.PLAIN, 16));
        }

        // Add labels to panels
        for (int i = 0; i < 6; i++) {
            panels[i].add(labels[i]);
        }
        // Add text fields to panels
        panels[0].add(textFields[0]);
        panels[1].add(textFields[1]);
        panels[3].add(textFields[2]);
        panels[4].add(textFields[3]);

        // Date picker
        dateOfBirthField.setPreferredSize(new Dimension(190, 35));
        dateOfBirthField.setFont(new Font("Arial", Font.PLAIN, 16));
        dateOfBirthField.setFocusable(false);
        ImageIcon icon = new ImageIcon("src/GUI/Assets/calendar.png");
        icon = scaleImage(icon, 20, 20);

        JButton dateSelectButton = new JButton(icon);
        dateSelectButton.setPreferredSize(new Dimension(30, 30));
        JPanel selectDatePanel = new JPanel(new BorderLayout());
        selectDatePanel.setOpaque(true);
        selectDatePanel.setSize(new Dimension(220, 30));
        selectDatePanel.add(dateOfBirthField, BorderLayout.CENTER);
        selectDatePanel.add(dateSelectButton, BorderLayout.EAST);
        panels[2].add(selectDatePanel);

        dateSelectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                dateOfBirthField.setText(new DatePicker(mainFrame).setPickedDate());
            }
        });

        // Add specialisation drop down menu
        String[] genders = {"Male", "Female"};

        genderDropDown = new JComboBox(genders);
        genderDropDown.setPreferredSize(new Dimension(220, 30));
        genderDropDown.setFont(new Font("Arial", Font.PLAIN, 16));
        genderDropDown.setBorder(null);
        panels[5].add(genderDropDown);

        // Add buttons
        JPanel addDoctorBtnContainer = new JPanel();
        addDoctorBtnContainer.setPreferredSize(new Dimension(500, 50));
        addPatient = new JButton("Add Patient");
        addPatient.setPreferredSize(new Dimension(220, 40));
        addPatient.setFont(new Font("Arial", Font.PLAIN, 16));
        addPatient.setBorder(BorderFactory.createLineBorder(new Color(164, 92, 255), 2));
        addPatient.setBackground(new Color(0, 0, 0, 0));
        addPatient.setForeground(new Color(164, 92, 255));
        addPatient.addActionListener(this);
        addPatient.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                addPatient.setOpaque(true);
                addPatient.setBackground(new Color(164, 92, 255));
                addPatient.setForeground(new Color(255, 255, 255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                addPatient.setOpaque(false);
                addPatient.setForeground(new Color(164, 92, 255));
            }
        });
        addDoctorBtnContainer.add(addPatient);

        // Cancel button
        JPanel addAndCancelButtons = new JPanel(new FlowLayout());
        JLabel cancelDescription = new JLabel("No need add? ");
        cancelDescription.setFont(new Font("Arial", Font.PLAIN, 16));
        cancelDescription.setForeground(new Color(164, 92, 255));

        cancel = new JButton("Cancel");
        cancel.setFont(new Font("Arial", Font.BOLD, 16));
        cancel.setOpaque(false);
        cancel.setBorder(null);
        cancel.setForeground(new Color(164, 92, 255));
        cancel.addActionListener(this);
        cancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                cancel.setForeground(new Color(51, 0, 115));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                cancel.setForeground(new Color(164, 92, 255));
            }
        });
        addAndCancelButtons.add(cancelDescription);
        addAndCancelButtons.add(cancel);

        // Add panels to border panels
        mainBorderLayouts[0].add(panels[0], BorderLayout.WEST);
        mainBorderLayouts[0].add(panels[1], BorderLayout.EAST);

        mainBorderLayouts[1].add(panels[2], BorderLayout.WEST);
        mainBorderLayouts[1].add(panels[3], BorderLayout.EAST);

        mainBorderLayouts[2].add(panels[4], BorderLayout.WEST);
        mainBorderLayouts[2].add(panels[5], BorderLayout.EAST);

        // Add panels to main frame
        mainPanel.add(mainBorderLayouts[0]);
        mainPanel.add(mainBorderLayouts[1]);
        mainPanel.add(mainBorderLayouts[2]);
        mainPanel.add(addSpace(520, 30));
        mainPanel.add(addDoctorBtnContainer);
        mainPanel.add(addAndCancelButtons);
        mainPanel.add(addSpace(520, 50));
        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addPatient) {
            // Check if all fields are filled
            if (fNameField.getText().equals("") || lNameField.getText().equals("") || dateOfBirthField.getText().equals("") || mobileNumField.getText().equals("") || patientIdField.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // Check if mobile number is valid
                if (mobileNumField.getText().length() != 10) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid mobile number", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Check if medical licence number is valid
                    if (patientIdField.getText().length() != 6) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid Patient ID: EX- P00XXX", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        // Check if patient ID is already taken
                        boolean isPatientIdUnique = true;
                        for (Person patient : WestminsterSkinConsultationManager.getPatientArrayList()) {
                            Patient pat = (Patient) patient;
                            if (pat.getPatientId().equals(patientIdField.getText())) {
                                isPatientIdUnique = false;
                                break;
                            }
                        }
                        if (isPatientIdUnique) {
                            // Add doctor to array list
                            Date dateOfBirth = WestminsterSkinConsultationManager.strToDate(dateOfBirthField.getText());

                            Patient patient = new Patient(fNameField.getText(), lNameField.getText(), dateOfBirth, mobileNumField.getText(), patientIdField.getText(), Objects.requireNonNull(genderDropDown.getSelectedItem()).toString());

                            WestminsterSkinConsultationManager.patientArrayList.add(patient);

                            JOptionPane.showMessageDialog(null, "Patient added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                            PatientsPanel.tableReRender(WestminsterSkinConsultationManager.getPatientArrayList());
                            mainFrame.dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "Patient ID already taken", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        }
        if (e.getSource() == cancel) {
            mainFrame.dispose();
        }
    }
}
