package GUI.Main_Frames.SubFrames;

import GUI.MainFrame;
import GUI.Main_Frames.SessionsPanel;
import GUI.GUIModels.DatePicker;
import Classes.Session;
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
import java.util.Date;

import static GUI.MainFrame.scaleImage;

public class FindSession extends JFrame implements ActionListener, MouseListener, DocumentListener {
    String doctorName, sessionID;
    Date sessionDate;
    JFrame mainFrame;
    JLabel doctorNameLabel, dateLabel, sessionIdLabel;
    JTextField doctorNameField, sessionIdField;
    final JTextField sessionDateField = new JTextField();
    JPanel topPanel, topPanelUp, topPanelDown, bottomPanel;
    JButton findSession;
    static JTable sessionTable;
    JScrollPane scrollPane;
    JButton edit, delete;


    public FindSession() {
        doctorName = "";
        sessionID = "";
        sessionDate = null;

        mainFrame = new JFrame();
        mainFrame.setTitle("Find Session");
        mainFrame.setSize(800, 500);
        mainFrame.setLayout(new FlowLayout());
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(MainFrame.getFrames()[0]);


        // Top Panel
        topPanel = new JPanel(new FlowLayout());
        topPanel.setPreferredSize(new Dimension(800, 100));
        topPanel.setOpaque(true);

        // Top Panel Up
        doctorNameLabel = new JLabel("Doctor Name");
        doctorNameLabel.setPreferredSize(new Dimension(150, 20));
        dateLabel = new JLabel("Session Date");
        dateLabel.setPreferredSize(new Dimension(150, 20));
        sessionIdLabel = new JLabel("Session ID");
        sessionIdLabel.setPreferredSize(new Dimension(250, 20));

        topPanelUp = new JPanel(new FlowLayout());
        topPanelUp.setPreferredSize(new Dimension(800, 20));
        topPanelUp.add(doctorNameLabel);
        topPanelUp.add(dateLabel);
        topPanelUp.add(sessionIdLabel);
        topPanel.add(topPanelUp);

        // Top Panel Down
        topPanelDown = new JPanel(new FlowLayout());
        topPanelDown.setPreferredSize(new Dimension(800, 35));
        doctorNameField = new JTextField();
        doctorNameField.setPreferredSize(new Dimension(150, 30));
        doctorNameField.getDocument().addDocumentListener(this);

        sessionIdField = new JTextField();
        sessionIdField.setPreferredSize(new Dimension(150, 30));
        sessionIdField.getDocument().addDocumentListener(this);


        // Date picker
        sessionDateField.setPreferredSize(new Dimension(190, 35));
        sessionDateField.setFont(new Font("Arial", Font.PLAIN, 16));
        sessionDateField.setFocusable(false);
        sessionDateField.getDocument().addDocumentListener(this);
        ImageIcon icon = new ImageIcon("src/GUI/Assets/calendar.png");
        icon = scaleImage(icon, 20, 20);

        JButton dateSelectButton = new JButton(icon);
        dateSelectButton.setPreferredSize(new Dimension(30, 30));
        JPanel selectDatePanel = new JPanel(new BorderLayout());
        selectDatePanel.setOpaque(true);
        selectDatePanel.setSize(new Dimension(220, 30));
        selectDatePanel.add(sessionDateField, BorderLayout.CENTER);
        selectDatePanel.add(dateSelectButton, BorderLayout.EAST);

        dateSelectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                sessionDateField.setText(new DatePicker(mainFrame).setPickedDate());
            }
        });

        topPanelDown.add(doctorNameField);
        topPanelDown.add(selectDatePanel);
        topPanelDown.add(sessionIdField);
        topPanel.add(topPanelDown);

        // Find Doctor Button
        findSession = new JButton("Find Session");
        findSession.setPreferredSize(new Dimension(150, 30));
        findSession.addActionListener(this);
        findSession.addMouseListener(this);
        findSession.setForeground(new Color(164, 92, 255));
        findSession.setFont(new Font("Arial", Font.PLAIN, 14));
        findSession.setBorder(BorderFactory.createLineBorder(new Color(164, 92, 255), 2));
        topPanel.add(findSession);
        mainFrame.add(topPanel);


        // Bottom Panel
        bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.setPreferredSize(new Dimension(800, 400));

        String[] sessionsTableColumns = {"Session ID", "Doctor Name", "Date", "Time", "Maximal Patients", "Status", "Availability"};

        ArrayList<Session> sessions = WestminsterSkinConsultationManager.getSessionsArrayList();
        String[][] sessionData = new String[sessions.size()][7];

        for (int i = 0; i < sessions.size(); i++) {
            Session session = WestminsterSkinConsultationManager.getSessionsArrayList().get(i);
            sessionData[i][0] = session.getSessionId();
            sessionData[i][1] = session.getDoctor().getName() + " " + session.getDoctor().getSurName();
            sessionData[i][2] = session.getStringDate();
            sessionData[i][3] = session.getStringTime();
            sessionData[i][4] = String.valueOf(session.getMaxPatients());
            sessionData[i][5] = session.getSessionStatus();
            sessionData[i][6] = String.valueOf(session.getAvailableConsultations());
        }

        sessionTable = new JTable();
        sessionTable.setPreferredScrollableViewportSize(new Dimension(750, 300));
        sessionTable.setRowHeight(30);
        sessionTable.setFont(new Font("Arial", Font.PLAIN, 14));
        sessionTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        sessionTable.setShowGrid(true);
        sessionTable.setGridColor(new Color(234, 214, 255));
        sessionTable.setModel(new DefaultTableModel(sessionData, sessionsTableColumns));
        JTableHeader header = sessionTable.getTableHeader();
        header.setBackground(new Color(30, 0, 70));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.PLAIN, 14));
        header.setPreferredSize(new Dimension(750, 30));

        /**
         * This is to get values from selected row from the table
         */
        sessionTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int viewRow = sessionTable.getSelectedRow();

                if (!e.getValueIsAdjusting() && viewRow != -1) {
                    int columnIndex = 0;
                    int modelRow = sessionTable.convertRowIndexToModel(viewRow);
                    Object sessionId = sessionTable.getModel().getValueAt(modelRow, columnIndex);
                    JDialog dialog = new JDialog(mainFrame, "Session Details", true);
                    dialog.setSize(400, 100);
                    dialog.setLayout(new FlowLayout());
                    dialog.setResizable(false);
                    dialog.setLocationRelativeTo(FindSession.getFrames()[0]);

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
                            new EditSession(sessionId.toString());
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
                            Session session = null;
                            for (int i = 0; i < WestminsterSkinConsultationManager.sessionArrayList.size(); i++) {
                                Session ses = WestminsterSkinConsultationManager.sessionArrayList.get(i);
                                if (ses.getSessionId().equals(sessionId)) {
                                    session = WestminsterSkinConsultationManager.sessionArrayList.get(i);
                                }
                            }
                            int dialogButton = JOptionPane.YES_NO_OPTION;
                            int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete Session " + session.getSessionId() + " ?", "Warning", dialogButton);

                            if (dialogResult == JOptionPane.YES_OPTION) {
                                WestminsterSkinConsultationManager manager = new WestminsterSkinConsultationManager();
                                manager.deleteASession(sessionId.toString());
                                SessionsPanel.tableReRender(WestminsterSkinConsultationManager.getSessionsArrayList());
                                dialog.dispose();
                                mainFrame.dispose();
                                new FindSession();
                            }
                        }
                    });
                    dialog.add(edit);
                    dialog.add(delete);
                    dialog.setVisible(true);
                }
            }
        });

        scrollPane = new JScrollPane(sessionTable);

        bottomPanel.add(scrollPane);
        mainFrame.add(bottomPanel);

        mainFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == findSession) {
            ArrayList<Session> sessions = WestminsterSkinConsultationManager.getSessionsArrayList();
            ArrayList<Session> filteredSessions = new ArrayList<>();
            if (!sessionDateField.getText().isEmpty()) {
                sessionDate = WestminsterSkinConsultationManager.strToDate(sessionDateField.getText());
            }
            doctorName = doctorNameField.getText();
            sessionID = sessionIdField.getText();

            for (Session session : sessions) {
                if (session.getDoctor().getName().equalsIgnoreCase(doctorName)) {
                    filteredSessions.add(session);
                }
                if (session.getSessionId().equalsIgnoreCase(sessionID)) {
                    filteredSessions.add(session);
                }
                if (session.getDate().equals(sessionDate)) {
                    filteredSessions.add(session);
                }
            }
            tableReRender(filteredSessions);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == findSession) {
            findSession.setForeground(new Color(164, 92, 255));
            findSession.setBackground(Color.WHITE);
            findSession.setOpaque(true);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getSource() == findSession) {
            findSession.setForeground(Color.WHITE);
            findSession.setBackground(new Color(164, 92, 255));
            findSession.setOpaque(true);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == findSession) {
            findSession.setForeground(Color.WHITE);
            findSession.setBackground(new Color(164, 92, 255));
            findSession.setOpaque(true);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == findSession) {
            findSession.setForeground(new Color(164, 92, 255));
            findSession.setBackground(Color.WHITE);
            findSession.setOpaque(true);
        }
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        if (e.getDocument() == doctorNameField.getDocument()) {
            doctorName = doctorNameField.getText();
            updateTable(doctorName, sessionDate, sessionID);
        } else if (e.getDocument() == sessionIdField.getDocument()) {
            sessionID = sessionIdField.getText();
            updateTable(doctorName, sessionDate, sessionID);
        } else if (e.getDocument() == sessionDateField.getDocument()) {
            String date = sessionDateField.getText();
            sessionDate = WestminsterSkinConsultationManager.strToDate(date);
            updateTable(doctorName, sessionDate, sessionID);
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        if (e.getDocument() == doctorNameField.getDocument()) {
            doctorName = doctorNameField.getText();
            updateTable(doctorName, sessionDate, sessionID);
        } else if (e.getDocument() == sessionIdField.getDocument()) {
            sessionID = sessionIdField.getText();
            updateTable(doctorName, sessionDate, sessionID);
        } else if (e.getDocument() == sessionDateField.getDocument()) {
            String date = sessionDateField.getText();
            sessionDate = WestminsterSkinConsultationManager.strToDate(date);
            updateTable(doctorName, sessionDate, sessionID);
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        if (e.getDocument() == doctorNameField.getDocument()) {
            doctorName = doctorNameField.getText();
            updateTable(doctorName, sessionDate, sessionID);
        } else if (e.getDocument() == sessionIdField.getDocument()) {
            sessionID = sessionIdField.getText();
            updateTable(doctorName, sessionDate, sessionID);
        } else if (e.getDocument() == sessionDateField.getDocument()) {
            String date = sessionDateField.getText();
            sessionDate = WestminsterSkinConsultationManager.strToDate(date);
            updateTable(doctorName, sessionDate, sessionID);
        }
    }

    public void updateTable(String doctorName, Date sessionDate, String sessionId) {
        ArrayList<Session> sessions = WestminsterSkinConsultationManager.getSessionsArrayList();
        ArrayList<Session> filteredSessions = new ArrayList<>();

        for (Session session : sessions) {
            if (session.getDoctor().getName().contains(doctorName) || session.getDate().equals(sessionDate) || session.getSessionId().equalsIgnoreCase(sessionId)) {
                filteredSessions.add(session);
            }
        }
        if (doctorName.isEmpty() && sessionDate == null && sessionId.isEmpty()) {
            tableReRender(sessions);
        } else {
            tableReRender(filteredSessions);
        }
    }

    public static void tableReRender(ArrayList<Session> filteredSessionList) {
        String[][] filteredSessionData = new String[filteredSessionList.size()][7];

        for (int i = 0; i < filteredSessionList.size(); i++) {
            Session ses = filteredSessionList.get(i);
            filteredSessionData[i][0] = ses.getSessionId();
            filteredSessionData[i][1] = ses.getDoctor().getName() + " " + ses.getDoctor().getSurName();
            filteredSessionData[i][2] = ses.getStringDate();
            filteredSessionData[i][3] = ses.getStringTime();
            filteredSessionData[i][4] = String.valueOf(ses.getMaxPatients());
            filteredSessionData[i][5] = ses.getSessionStatus();
            filteredSessionData[i][6] = String.valueOf(ses.getAvailableConsultations());
        }

        String[] sessionsTableColumns = {"Session ID", "Doctor Name", "Date", "Time", "Maximal Patients", "Status", "Availability"};
        sessionTable.setModel(new DefaultTableModel(filteredSessionData, sessionsTableColumns));
    }
}
