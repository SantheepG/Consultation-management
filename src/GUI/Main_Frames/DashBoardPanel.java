package GUI.Main_Frames;

import GUI.GUIModels.StatusColumnCellRenderer;
import GUI.MainFrame;
import Classes.Session;
import Classes.WestminsterSkinConsultationManager;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;

import static GUI.MainFrame.addSpace;

public class DashBoardPanel extends JPanel {
    JButton doctorPanel, patientPanel, sessionsPanel;
    JPanel cardPanel;

    JPanel topPanel, tablePanel, middlePanel;
    JButton[] cardPanels = new JButton[3];
    String[] iconPaths = new String[3];
    String[] cardTitles = new String[3];
    String[] cardDescriptions = new String[3];
    String[] cardCounts = new String[3];

    static JTable sessionsTable;
    JScrollPane sessionsTableScroll;

    public DashBoardPanel() {

        // Table
        String[] sessionsTableColumns = {"Session ID", "Doctor Name", "Date", "Time", "Maximum Patients", "Status"};

        ArrayList<Session> sessionArrayList = WestminsterSkinConsultationManager.getSessionsArrayList();
        WestminsterSkinConsultationManager manager = new WestminsterSkinConsultationManager();
        manager.sortSession(sessionArrayList);

        String[][] data = new String[WestminsterSkinConsultationManager.getSessionsArrayList().size()][6];
        for (int i = 0; i < WestminsterSkinConsultationManager.getSessionsArrayList().size(); i++) {
            data[i][0] = WestminsterSkinConsultationManager.getSessionsArrayList().get(i).getSessionId();
            data[i][1] = WestminsterSkinConsultationManager.getSessionsArrayList().get(i).getDoctor().getName() + " " + WestminsterSkinConsultationManager.getSessionsArrayList().get(i).getDoctor().getSurName();
            data[i][2] = WestminsterSkinConsultationManager.getSessionsArrayList().get(i).getStringDate();
            data[i][3] = WestminsterSkinConsultationManager.getSessionsArrayList().get(i).getStringTime();
            data[i][4] = String.valueOf(WestminsterSkinConsultationManager.getSessionsArrayList().get(i).getMaxPatients());
            data[i][5] = WestminsterSkinConsultationManager.getSessionsArrayList().get(i).getSessionStatus();
        }

        topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(1000, 200));
        topPanel.setOpaque(false);
        ImageIcon imageIcon = new ImageIcon("src/GUI/Assets/bg2.png");
        Image image = imageIcon.getImage();
        Image newImg = image.getScaledInstance(1000, 200, java.awt.Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(newImg);
        JLabel bg = new JLabel(imageIcon);
        topPanel.add(bg);


        setSize(1000, 800);
        setLayout(new FlowLayout());

        sessionsTable = new JTable();
        sessionsTable.setRowHeight(40);
        sessionsTable.setModel(new DefaultTableModel(data, sessionsTableColumns));
        sessionsTable.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        sessionsTable.setShowGrid(true);
        sessionsTable.setGridColor(new Color(239, 209, 255, 255));
        JTableHeader header = sessionsTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        header.setBackground(new Color(51, 0, 101, 255));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(100, 40));
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < sessionsTable.getColumnCount(); i++) {
            sessionsTable.getColumnModel().getColumn(i).setCellRenderer(new StatusColumnCellRenderer());
        }

        sessionsTableScroll = new JScrollPane(sessionsTable);

        tablePanel = new JPanel();
        tablePanel.setPreferredSize(new Dimension(1000, 340));
        TitledBorder titledBorder = BorderFactory.createTitledBorder("Upcoming Sessions");
        titledBorder.setTitleFont(new Font("Arial", Font.PLAIN, 30));
        tablePanel.setBorder(titledBorder);
        tablePanel.setLayout(new BorderLayout());
        tablePanel.add(sessionsTableScroll, BorderLayout.CENTER);

        // Cards
        iconPaths[0] = "src/GUI/Assets/doctorsGrad.png";
        iconPaths[1] = "src/GUI/Assets/patientGrad.png";
        iconPaths[2] = "src/GUI/Assets/sessionsGrad.png";

        cardTitles[0] = "Doctors";
        cardTitles[1] = "Patients";
        cardTitles[2] = "Sessions";

        cardDescriptions[0] = "Total Doctors";
        cardDescriptions[1] = "Total Patients";
        cardDescriptions[2] = "Total Sessions";

        int doctorCount = 0;
        for (int i = 0; i < WestminsterSkinConsultationManager.doctorArray.length; i++) {
            if (WestminsterSkinConsultationManager.doctorArray[i] != null) {
                doctorCount++;
            }
        }

        cardCounts[0] = String.valueOf(doctorCount);
        cardCounts[1] = String.valueOf(WestminsterSkinConsultationManager.patientArrayList.size());
        cardCounts[2] = String.valueOf(WestminsterSkinConsultationManager.sessionArrayList.size());


        cardPanel = new JPanel(new FlowLayout());
        cardPanel.setOpaque(true);

        doctorPanel = new JButton() {
            protected void paintComponent(Graphics g) {
                if (g instanceof Graphics2D g2d) {
                    Paint p =
                            new GradientPaint(1.0f, 1.0f, new Color(184, 44, 255),
                                    getWidth(), getHeight(), new Color(235, 129, 255), true);
                    g2d.setPaint(p);
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                } else {
                    super.paintComponent(g);
                }
            }
        };
        patientPanel = new JButton() {
            protected void paintComponent(Graphics g) {
                if (g instanceof Graphics2D g2d) {
                    Paint p =
                            new GradientPaint(0.0f, 0.0f, new Color(24, 77, 255),
                                    getWidth(), getHeight(), new Color(128, 218, 255), true);
                    g2d.setPaint(p);
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                } else {
                    super.paintComponent(g);
                }
            }
        };
        sessionsPanel = new JButton() {
            protected void paintComponent(Graphics g) {
                if (g instanceof Graphics2D g2d) {
                    Paint p =
                            new GradientPaint(0.0f, 0.0f, new Color(255, 123, 55),
                                    getWidth(), getHeight(), new Color(255, 208, 113), true);
                    g2d.setPaint(p);
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                } else {
                    super.paintComponent(g);
                }
            }
        };
        cardPanels[0] = doctorPanel;
        cardPanels[1] = patientPanel;
        cardPanels[2] = sessionsPanel;

        for (int i = 0; i < 3; i++) {
            cardPanels[i].setLayout(new FlowLayout(FlowLayout.LEFT));
            cardPanels[i].setPreferredSize(new Dimension(312, 150));
            // Card texts
            JPanel cardEastPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            cardEastPanel.setPreferredSize(new Dimension(140, 150));


            cardEastPanel.setOpaque(false);
            JLabel cardTitle = new JLabel(cardTitles[i]);
            cardTitle.setForeground(Color.WHITE);
            cardTitle.setPreferredSize(new Dimension(200, 20));
            cardTitle.setFont(new Font("Arial", Font.BOLD, 20));
            cardEastPanel.add(cardTitle);

            JLabel cardDescription = new JLabel(cardDescriptions[i]);
            cardDescription.setPreferredSize(new Dimension(250, 20));
            cardDescription.setFont(new Font("Arial", Font.PLAIN, 15));
            cardDescription.setForeground(new Color(255, 255, 255, 200));
            cardEastPanel.add(cardDescription);
            cardEastPanel.add(addSpace(100, 10));


            JLabel cardCount = new JLabel(cardCounts[i]);
            cardCount.setFont(new Font("Arial", Font.BOLD, 60));
            cardCount.setPreferredSize(new Dimension(200, 60));
            cardCount.setForeground(Color.WHITE);
            cardEastPanel.add(cardCount);

            try {
                Image cardIcon = new ImageIcon(iconPaths[i]).getImage().getScaledInstance(140, 100, Image.SCALE_AREA_AVERAGING);

                cardPanels[i].add(cardEastPanel);
                cardPanels[i].add(new JLabel(new ImageIcon(cardIcon)));
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
        }
        cardPanel.add(cardPanels[0]);
        cardPanel.add(addSpace(20, 20));
        cardPanel.add(cardPanels[1]);
        cardPanel.add(addSpace(20, 20));
        cardPanel.add(cardPanels[2]);

        middlePanel = new JPanel(new FlowLayout());
        middlePanel.setPreferredSize(new Dimension(1200, 180));
        middlePanel.add(cardPanel);

        add(topPanel);
        add(MainFrame.addSpace(1000, 10));
        add(middlePanel);
        add(tablePanel);
//        add(addSpace(1000, 20));
    }

    public static void tableReRender(ArrayList<Session> sessions) {
        String[] sessionsTableColumns = {"Session ID", "Doctor Name", "Date", "Time", "Maximum Patients", "Status"};

        ArrayList<Session> sessionArrayList = WestminsterSkinConsultationManager.getSessionsArrayList();
        WestminsterSkinConsultationManager manager = new WestminsterSkinConsultationManager();
        manager.sortSession(sessionArrayList);

        String[][] data = new String[WestminsterSkinConsultationManager.getSessionsArrayList().size()][6];
        for (int i = 0; i < WestminsterSkinConsultationManager.getSessionsArrayList().size(); i++) {
            data[i][0] = WestminsterSkinConsultationManager.getSessionsArrayList().get(i).getSessionId();
            data[i][1] = WestminsterSkinConsultationManager.getSessionsArrayList().get(i).getDoctor().getName() + " " + WestminsterSkinConsultationManager.getSessionsArrayList().get(i).getDoctor().getSurName();
            data[i][2] = WestminsterSkinConsultationManager.getSessionsArrayList().get(i).getStringDate();
            data[i][3] = WestminsterSkinConsultationManager.getSessionsArrayList().get(i).getStringTime();
            data[i][4] = String.valueOf(WestminsterSkinConsultationManager.getSessionsArrayList().get(i).getMaxPatients());
            data[i][5] = WestminsterSkinConsultationManager.getSessionsArrayList().get(i).getSessionStatus();
        }

        DefaultTableModel model = new DefaultTableModel(data, sessionsTableColumns);
        sessionsTable.setModel(model);

        for (int i = 0; i < sessionsTable.getColumnCount(); i++) {
            sessionsTable.getColumnModel().getColumn(i).setCellRenderer(new StatusColumnCellRenderer());
        }
    }
}
