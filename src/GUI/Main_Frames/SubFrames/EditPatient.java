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

import static GUI.MainFrame.addSpace;
import static GUI.MainFrame.scaleImage;

public class EditPatient extends JDialog implements ActionListener {
    String patientId;
    Person patient;
    final JFrame mainFrame;
    JPanel mainPanel;
    JPanel[] panels = new JPanel[6];
    JPanel[] mainBorderLayouts = new JPanel[3];
    JLabel[] labels = new JLabel[6];
    JTextField fNameField, lNameField, mobileNumField, patientIdField;
    JComboBox genderDropDown;
    final JTextField dateOfBirthField = new JTextField();
    JTextField[] textFields = new JTextField[4];
    JButton editPatient, cancel;

    public EditPatient(String patientId) {
        this.patientId = patientId;
        for (int i = 0; i < WestminsterSkinConsultationManager.patientArrayList.size(); i++) {
            Patient p = (Patient) WestminsterSkinConsultationManager.patientArrayList.get(i);
            if (p.getPatientId().equals(patientId)) {
                patient = WestminsterSkinConsultationManager.patientArrayList.get(i);
            }
        }
        Patient p = (Patient) patient;

        // Main Frame
        mainFrame = new JFrame();
        mainFrame.setVisible(true);
        mainFrame.setSize(520, 450);
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
        fNameField.setText(patient.getName());
        lNameField = new JTextField();
        lNameField.setText(patient.getSurName());
        mobileNumField = new JTextField();
        mobileNumField.setText(patient.getMobileNumber());
        patientIdField = new JTextField();
        patientIdField.setText(p.getPatientId());

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
        dateOfBirthField.setText(patient.getStringDateOfBirth());

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

        // Add gender drop down menu
        String[] genders = {"Male", "Female"};

        genderDropDown = new JComboBox(genders);
        genderDropDown.setPreferredSize(new Dimension(220, 30));
        genderDropDown.setFont(new Font("Arial", Font.PLAIN, 16));
        genderDropDown.setBorder(null);
        genderDropDown.setSelectedItem(p.getGender());
        panels[5].add(genderDropDown);

        // Update buttons
        JPanel updatePatientBtnContainer = new JPanel();
        updatePatientBtnContainer.setPreferredSize(new Dimension(500, 50));
        editPatient = new JButton("Update");
        editPatient.setPreferredSize(new Dimension(220, 40));
        editPatient.setFont(new Font("Arial", Font.PLAIN, 16));
        editPatient.setBorder(BorderFactory.createLineBorder(new Color(164, 92, 255), 2));
        editPatient.setBackground(new Color(0, 0, 0, 0));
        editPatient.setForeground(new Color(164, 92, 255));
        editPatient.addActionListener(this);
        editPatient.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                editPatient.setOpaque(true);
                editPatient.setBackground(new Color(164, 92, 255));
                editPatient.setForeground(new Color(255, 255, 255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                editPatient.setOpaque(false);
                editPatient.setForeground(new Color(164, 92, 255));
            }
        });
        updatePatientBtnContainer.add(editPatient);

        // Cancel button
        JPanel addAndCancelButtons = new JPanel(new FlowLayout());
        JLabel cancelDescription = new JLabel("No need Edit? ");
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
        mainPanel.add(updatePatientBtnContainer);
        mainPanel.add(addAndCancelButtons);
        mainPanel.add(addSpace(520, 50));
        mainFrame.add(mainPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == editPatient) {
            if (textFields[0].getText().equals("") || textFields[1].getText().equals("") || textFields[2].getText().equals("") || textFields[3].getText().equals("") || dateOfBirthField.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Please fill in all the fields", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
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
                            // Change doctor details
                            String firstName = textFields[0].getText();
                            String lastName = textFields[1].getText();
                            String dateOfBirth = dateOfBirthField.getText();
                            String mobileNumber = textFields[2].getText();
                            String patientID = textFields[3].getText();
                            String gender = (String) genderDropDown.getSelectedItem();

                            patient.setName(firstName);
                            patient.setSurName(lastName);
                            patient.setDateOfBirth(WestminsterSkinConsultationManager.strToDate(dateOfBirth));
                            patient.setMobileNumber(mobileNumber);
                            Patient p = (Patient) patient;
                            p.setPatientId(patientID);
                            p.setGender(gender);

                            JOptionPane.showMessageDialog(null, "Patient Edited Successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                            FindPatient.tableReRender(WestminsterSkinConsultationManager.getPatientArrayList());
                            PatientsPanel.tableReRender(WestminsterSkinConsultationManager.getPatientArrayList());
                            mainFrame.dispose();
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
