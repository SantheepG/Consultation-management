package GUI.Main_Frames.SubFrames;

import GUI.MainFrame;
import GUI.Main_Frames.SessionsPanel;
import GUI.GUIModels.DatePicker;
import Classes.Person;
import Classes.Session;
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

public class AddSession extends JDialog implements ActionListener {
    final JFrame mainFrame;
    JPanel mainPanel;
    JPanel[] panels = new JPanel[5];
    JPanel[] mainBorderLayouts = new JPanel[2];
    JLabel[] labels = new JLabel[5];
    JTextField sessionIDField;
    JComboBox doctorDropDown, maximumPatientsDropDown, timeDropDown;
    final JTextField sessionDate = new JTextField();
    JButton addSession, cancel;


    public AddSession() {
        // Main Frame
        mainFrame = new JFrame();
        mainFrame.setVisible(true);
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
        for (int i = 0; i < 5; i++) {
            panels[i] = new JPanel(new FlowLayout());
            panels[i].setPreferredSize(new Dimension(220, 70));
            panels[i].setOpaque(false);
        }

        // Initialise labels
        for (int i = 0; i < 5; i++) {
            labels[i] = new JLabel();
            labels[i].setPreferredSize(new Dimension(200, 20));
            labels[i].setOpaque(false);
            labels[i].setFont(new Font("Arial", Font.PLAIN, 16));
        }
        labels[0].setText("Session ID");
        labels[1].setText("Doctor");
        labels[2].setText("Date");
        labels[3].setText("Time");
        labels[4].setText("Maximum Patients");

        // Initialise text fields
        sessionIDField = new JTextField();
        sessionIDField.setPreferredSize(new Dimension(220, 30));
        sessionIDField.setOpaque(true);
        sessionIDField.setFont(new Font("Arial", Font.PLAIN, 16));


        // Add labels to panels
        for (int i = 0; i < 5; i++) {
            panels[i].add(labels[i]);
        }
        // Add text fields to panels
        panels[0].add(sessionIDField);

        // Add Time Drop Down
        String[] times = {"Select", "9:00", "9:30", "10:00", "10:30", "11:00", "11:30", "12:00", "12:30", "13:00", "13:30", "14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30", "22:00", "22:30", "23:00", "23:30", "00:00", "00:30", "01:00", "01:30", "02:00", "02:30", "03:00", "03:30", "04:00", "04:30", "05:00", "05:30", "06:00", "06:30", "07:00", "07:30", "08:00", "08:30"};
        timeDropDown = new JComboBox(times);
        timeDropDown.setPreferredSize(new Dimension(220, 30));
        timeDropDown.setOpaque(true);
        timeDropDown.setFont(new Font("Arial", Font.PLAIN, 16));
        panels[3].add(timeDropDown);


        // Date picker
        sessionDate.setPreferredSize(new Dimension(190, 35));
        sessionDate.setFont(new Font("Arial", Font.PLAIN, 16));
        sessionDate.setFocusable(false);
        ImageIcon icon = new ImageIcon("src/GUI/Assets/calendar.png");
        icon = scaleImage(icon, 20, 20);

        JButton dateSelectButton = new JButton(icon);
        dateSelectButton.setPreferredSize(new Dimension(30, 30));
        JPanel selectDatePanel = new JPanel(new BorderLayout());
        selectDatePanel.setOpaque(true);
        selectDatePanel.setSize(new Dimension(220, 30));
        selectDatePanel.add(sessionDate, BorderLayout.CENTER);
        selectDatePanel.add(dateSelectButton, BorderLayout.EAST);
        panels[2].add(selectDatePanel);

        dateSelectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                sessionDate.setText(new DatePicker(mainFrame).setPickedDate());
            }
        });

        // Add specialisation drop down menu
        int availableDoctors = WestminsterSkinConsultationManager.getNumberOfDoctors(WestminsterSkinConsultationManager.getDoctorArray());
        String[] doctors = new String[availableDoctors];

        int j = 0;
        while (j < availableDoctors) {
            if (WestminsterSkinConsultationManager.getDoctorArray()[j] != null) {
                doctors[j] = WestminsterSkinConsultationManager.getDoctorArray()[j].getName() + " " + WestminsterSkinConsultationManager.getDoctorArray()[j].getSurName();
                j++;
            }
        }
        for (int i = 0; i < availableDoctors; i++) {
        }

        doctorDropDown = new JComboBox(doctors);
        doctorDropDown.setPreferredSize(new Dimension(220, 30));
        doctorDropDown.setFont(new Font("Arial", Font.PLAIN, 16));
        doctorDropDown.setBorder(null);
        panels[1].add(doctorDropDown);

        // Add maximum patients drop down menu
        String[] maximumPatients = {"Select", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50"};
        maximumPatientsDropDown = new JComboBox(maximumPatients);
        maximumPatientsDropDown.setPreferredSize(new Dimension(220, 30));
        maximumPatientsDropDown.setFont(new Font("Arial", Font.PLAIN, 16));
        maximumPatientsDropDown.setBorder(null);
        panels[4].add(maximumPatientsDropDown);

        // Add buttons
        JPanel addSessionBtnContainer = new JPanel();
        addSessionBtnContainer.setPreferredSize(new Dimension(500, 50));
        addSession = new JButton("Add Session");
        addSession.setPreferredSize(new Dimension(220, 40));
        addSession.setFont(new Font("Arial", Font.PLAIN, 16));
        addSession.setBorder(BorderFactory.createLineBorder(new Color(164, 92, 255), 2));
        addSession.setBackground(new Color(0, 0, 0, 0));
        addSession.setForeground(new Color(164, 92, 255));
        addSession.addActionListener(this);
        addSession.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                addSession.setOpaque(true);
                addSession.setBackground(new Color(164, 92, 255));
                addSession.setForeground(new Color(255, 255, 255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                addSession.setOpaque(false);
                addSession.setForeground(new Color(164, 92, 255));
            }
        });
        addSessionBtnContainer.add(addSession);

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

        // Add panels to main frame
        mainPanel.add(mainBorderLayouts[0]);
        mainPanel.add(mainBorderLayouts[1]);
        mainPanel.add(panels[4]);

        mainPanel.add(addSpace(520, 30));
        mainPanel.add(addSessionBtnContainer);
        mainPanel.add(addAndCancelButtons);
        mainPanel.add(addSpace(520, 50));
        mainFrame.add(mainPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addSession) {
            // Check if all fields are filled
            if (sessionIDField.getText().equals("") || Objects.requireNonNull(doctorDropDown.getSelectedItem()).toString().equals("") || sessionDate.getText().equals("") || Objects.requireNonNull(timeDropDown.getSelectedItem()).toString().equals("Select") || Objects.requireNonNull(maximumPatientsDropDown.getSelectedItem()).toString().equals("Select")) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                boolean isSessionIDUnique = true;
                for (Session session : WestminsterSkinConsultationManager.sessionArrayList) {
                    if (session.getSessionId().equals(sessionIDField.getText())) {
                        isSessionIDUnique = false;
                    }
                }
                if (isSessionIDUnique) {
                    Date date = WestminsterSkinConsultationManager.strToDate(sessionDate.getText());
                    Person doctor = null;
                    for (int i = 0; i < WestminsterSkinConsultationManager.getDoctorArray().length; i++) {
                        if (WestminsterSkinConsultationManager.doctorArray[i] != null) {
                            if (Objects.requireNonNull(doctorDropDown.getSelectedItem()).toString().equals(WestminsterSkinConsultationManager.getDoctorArray()[i].getName() + " " + WestminsterSkinConsultationManager.getDoctorArray()[i].getSurName())) {
                                doctor = WestminsterSkinConsultationManager.getDoctorArray()[i];
                            }
                        }
                    }
                    Date time = WestminsterSkinConsultationManager.strToTime(Objects.requireNonNull(timeDropDown.getSelectedItem()).toString());
                    int maximumPatients = Integer.parseInt(Objects.requireNonNull(maximumPatientsDropDown.getSelectedItem()).toString());

                    Session session = new Session(sessionIDField.getText(), doctor, date, time, maximumPatients, "Active");

                    WestminsterSkinConsultationManager.sessionArrayList.add(session);

                    JOptionPane.showMessageDialog(null, "Session added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    SessionsPanel.tableReRender(WestminsterSkinConsultationManager.getSessionsArrayList());
                    mainFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Session ID already exists", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        if (e.getSource() == cancel) {
            mainFrame.dispose();
        }
    }
}
