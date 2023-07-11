package GUI.Main_Frames.SubFrames;

import GUI.GUIModels.StatusColumnCellRenderer;
import GUI.MainFrame;
import GUI.GUIModels.DatePicker;
import Classes.Person;
import Classes.Session;
import Classes.WestminsterSkinConsultationManager;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import static GUI.MainFrame.scaleImage;

public class CheckConsultationAvailability implements ActionListener {
    String date;
    JFrame mainFrame;
    JLabel lblDoctorName, lblDate;
    JButton checkAvailability;
    JComboBox doctorName;
    final JTextField dateField = new JTextField();
    JPanel doctorNamePanel, datePanel;
    JScrollPane scrollPane;
    static JTable table;
    JPanel topPanel;


    public CheckConsultationAvailability() {
        mainFrame = new JFrame();
        mainFrame.setTitle("Check Consultation");
        mainFrame.setSize(800, 500);
        mainFrame.setLayout(new FlowLayout());
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(MainFrame.getFrames()[0]);

        // Top Panel
        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        topPanel.setPreferredSize(new Dimension(800, 100));
        topPanel.setOpaque(false);

        // Doctor Name Panel
        doctorNamePanel = new JPanel();
        doctorNamePanel.setLayout(new FlowLayout());
        doctorNamePanel.setPreferredSize(new Dimension(200, 100));
        doctorNamePanel.setOpaque(false);

        lblDoctorName = new JLabel("  Doctor Name");
        lblDoctorName.setPreferredSize(new Dimension(200, 20));
        doctorNamePanel.add(lblDoctorName);

        String[] doctorNames = new String[WestminsterSkinConsultationManager.getNumberOfDoctors(WestminsterSkinConsultationManager.getDoctorArray()) + 1];
        doctorNames[0] = "Select Doctor";
        int j = 1;
        for (Person doctor : WestminsterSkinConsultationManager.getDoctorArray()) {
            if (doctor != null) {
                doctorNames[j] = doctor.getName() + " " + doctor.getSurName();
                j++;
            }
        }
        doctorName = new JComboBox(doctorNames);
        doctorName.setPreferredSize(new Dimension(200, 40));
        doctorNamePanel.add(doctorName);

        // Date Panel
        datePanel = new JPanel();
        datePanel.setLayout(new FlowLayout());
        datePanel.setPreferredSize(new Dimension(240, 100));
        datePanel.setOpaque(false);

        lblDate = new JLabel("   Date");
        lblDate.setPreferredSize(new Dimension(240, 20));
        datePanel.add(lblDate);

        // Date Picker
        dateField.setPreferredSize(new Dimension(200, 40));
        dateField.setFont(new Font("Arial", Font.PLAIN, 16));
        dateField.setFocusable(false);
        ImageIcon icon = new ImageIcon("src/GUI/Assets/calendar.png");
        icon = scaleImage(icon, 20, 20);

        JButton dateSelectButton = new JButton(icon);
        dateSelectButton.setPreferredSize(new Dimension(30, 30));
        JPanel selectDatePanel = new JPanel(new BorderLayout());
        selectDatePanel.setOpaque(true);
        selectDatePanel.setSize(new Dimension(220, 30));
        selectDatePanel.add(dateField, BorderLayout.CENTER);
        selectDatePanel.add(dateSelectButton, BorderLayout.EAST);
        datePanel.add(selectDatePanel);

        dateSelectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                dateField.setText(new DatePicker(mainFrame).setPickedDate());
            }
        });
        dateField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                date = dateField.getText();
                Date dateTypeDate = WestminsterSkinConsultationManager.strToDate(date);
                if (doctorName.getSelectedItem() != "Select Doctor") {
                    String name = Objects.requireNonNull(doctorName.getSelectedItem()).toString();
                    updateTable(name, dateTypeDate);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                checkAvailability.setEnabled(true);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                checkAvailability.setEnabled(true);
            }
        });

        // Check Availability Button
        checkAvailability = new JButton("Check Availability");
        checkAvailability.setPreferredSize(new Dimension(200, 40));
        checkAvailability.setFont(new Font("Arial", Font.PLAIN, 16));
        checkAvailability.setBorder(BorderFactory.createLineBorder(new Color(164, 92, 255), 2));
        checkAvailability.setBackground(new Color(0, 0, 0, 0));
        checkAvailability.setForeground(new Color(164, 92, 255));
        checkAvailability.setCursor(new Cursor(Cursor.HAND_CURSOR));
        checkAvailability.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                checkAvailability.setOpaque(true);
                checkAvailability.setBackground(new Color(164, 92, 255));
                checkAvailability.setForeground(new Color(255, 255, 255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                checkAvailability.setOpaque(false);
                checkAvailability.setForeground(new Color(164, 92, 255));
            }
        });

        // Add Table
        String[] sessionsTableColumns = {"Session ID", "Doctor Name", "Date", "Time", "Maximal Patients", "Availability", "Status"};

        ArrayList<Session> sessions = WestminsterSkinConsultationManager.getSessionsArrayList();
        String[][] sessionData = new String[sessions.size()][7];


        for (int i = 0; i < sessions.size(); i++) {
            Session session = WestminsterSkinConsultationManager.getSessionsArrayList().get(i);
            sessionData[i][0] = session.getSessionId();
            sessionData[i][1] = session.getDoctor().getName() + " " + session.getDoctor().getSurName();
            sessionData[i][2] = session.getStringDate();
            sessionData[i][3] = session.getStringTime();
            sessionData[i][4] = String.valueOf(session.getMaxPatients());
            sessionData[i][5] = String.valueOf(session.getAvailableConsultations());
            sessionData[i][6] = session.getSessionStatus();
        }

        table = new JTable();
        table.setPreferredScrollableViewportSize(new Dimension(750, 300));
        table.setRowHeight(30);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setShowGrid(true);
        table.setGridColor(new Color(234, 214, 255));
        table.setModel(new DefaultTableModel(sessionData, sessionsTableColumns));

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(new StatusColumnCellRenderer());
        }

        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(30, 0, 70));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.PLAIN, 14));
        header.setPreferredSize(new Dimension(750, 30));

        scrollPane = new JScrollPane(table);

        topPanel.add(doctorNamePanel);
        topPanel.add(datePanel);
        topPanel.add(checkAvailability);
        mainFrame.add(topPanel);
        mainFrame.add(scrollPane);
        mainFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public void updateTable(String doctorName, Date date) {
        String fName = doctorName.split(" ")[0];
        String lName = doctorName.split(" ")[1];

        ArrayList<Session> sessions = WestminsterSkinConsultationManager.getSessionsArrayList();

        ArrayList<Session> filteredSessions = new ArrayList<>();

        for (Session session : sessions) {
            if (session.getDoctor().getName().equalsIgnoreCase(fName) && session.getDoctor().getSurName().equalsIgnoreCase(lName) && session.getDate().equals(date)) {
                filteredSessions.add(session);
            }
        }
        tableReRender(filteredSessions);
    }

    public static void tableReRender(ArrayList<Session> sessions) {
        String[] sessionsTableColumns = {"Session ID", "Doctor Name", "Date", "Time", "Maximal Patients", "Availability", "Status"};
        String[][] sessionData = new String[sessions.size()][7];

        for (int i = 0; i < sessions.size(); i++) {
            sessionData[i][0] = sessions.get(i).getSessionId();
            sessionData[i][1] = sessions.get(i).getDoctor().getName() + " " + sessions.get(i).getDoctor().getSurName();
            sessionData[i][2] = sessions.get(i).getStringDate();
            sessionData[i][3] = sessions.get(i).getStringTime();
            sessionData[i][4] = String.valueOf(sessions.get(i).getMaxPatients());
            sessionData[i][5] = String.valueOf(sessions.get(i).getAvailableConsultations());
            sessionData[i][6] = sessions.get(i).getSessionStatus();
        }

        DefaultTableModel model = new DefaultTableModel(sessionData, sessionsTableColumns);
        table.setModel(model);

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(new StatusColumnCellRenderer());
        }
    }
}
