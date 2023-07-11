package GUI.Main_Frames;

import GUI.GUIModels.SavingDone;
import GUI.GUIModels.SortingDone;
import GUI.GUIModels.StatusColumnCellRenderer;
import GUI.Main_Frames.SubFrames.*;
import Classes.Consultation;
import Classes.WestminsterSkinConsultationManager;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import static GUI.MainFrame.addSpace;
import static GUI.MainFrame.scaleImage;

public class ConsultationsPanel extends JPanel implements ActionListener, MouseListener, DocumentListener {
    JPanel mainPanel1, mainPanel2, mainPanel3;
    JPanel panel1Top, panel1Bottom;
    JPanel panel1BottomWest, panel1BottomEast;
    JPanel searchPanel;
    JTextField searchField;
    ImageIcon searchIcon;
    static int numberOfConsultations;
    JLabel panelTitle;
    static JLabel allConsultations;
    JButton addConsultations, checkAvailability;
    JButton[] purpleButtons = new JButton[2];
    JButton refreshButton;
    static JTable consultationsTable;
    JPanel tablePanel;
    JScrollPane consultationsTableScroll;
    JPanel panel3West, panel3East;
    JButton saveDataButton, sortDataButton;
    JButton[] panel3Buttons = new JButton[2];
    String[] panel3ButtonIconPaths = new String[2];

    public ConsultationsPanel() {
        numberOfConsultations = 0;
        setSize(1300, 800);
        setLayout(new FlowLayout());

        // Main Panel 1
        mainPanel1 = new JPanel();
        mainPanel1.setOpaque(true);
        mainPanel1.setOpaque(false);
        mainPanel1.setPreferredSize(new Dimension(1000, 180));

        // Main Panel 3
        mainPanel2 = new JPanel();
        mainPanel2.setOpaque(true);
        mainPanel2.setPreferredSize(new Dimension(1300, 480));

        // Main Panel 3
        mainPanel3 = new JPanel(new BorderLayout());
        mainPanel3.setOpaque(false);
        mainPanel3.setPreferredSize(new Dimension(1000, 80));

        // Adding Main Panels to Main Panel
        add(mainPanel1);
        add(mainPanel2);
        add(mainPanel3);


        // Panel 1 Components
        // Panel 1 Top
        panel1Top = new JPanel(new BorderLayout());
        panel1Top.setOpaque(false);
        panel1Top.setPreferredSize(new Dimension(1000, 50));
        panelTitle = new JLabel("All Consultations");
        panelTitle.setForeground(new Color(91, 91, 91, 200));
        panelTitle.setFont(new Font("Arial", Font.BOLD, 40));

        searchPanel = new JPanel(new BorderLayout());
        searchPanel.setPreferredSize(new Dimension(300, 50));
        searchPanel.setOpaque(true);
        searchPanel.setBackground(new Color(225, 225, 225));
        searchField = new JTextField("Consultations No...");
        searchField.setOpaque(false);
        searchField.setBorder(null);
        searchField.setFont(new Font("Arial", Font.PLAIN, 20));
        searchField.setForeground(new Color(124, 124, 124, 200));
        searchField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (searchField.getText().equals("Consultations No...")) {
                    searchField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchField.getText().equals("")) {
                    searchField.setText("Consultations No...");
                }
            }
        });
        searchField.setPreferredSize(new Dimension(320, 30));
        searchField.getDocument().addDocumentListener(this);
        searchPanel.add(addSpace(30, 10), BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);

        searchIcon = new ImageIcon("src/GUI/Assets/search.png");
        try {
            searchIcon = new ImageIcon("src/GUI/Assets/search.png");
            searchIcon = scaleImage(searchIcon, 40, 40);
            JLabel searchLabel = new JLabel(searchIcon);
            searchPanel.add(searchLabel, BorderLayout.EAST);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        panel1Top.add(panelTitle, BorderLayout.WEST);
        panel1Top.add(searchPanel, BorderLayout.EAST);

        mainPanel1.add(addSpace(1000, 20));
        mainPanel1.add(panel1Top);

        // Panel 1 Bottom
        addConsultations = new JButton("Add Consultations");
        addConsultations.addMouseListener(this);


        checkAvailability = new JButton("Check Availability");
        checkAvailability.addMouseListener(this);

        purpleButtons[0] = addConsultations;
        purpleButtons[1] = checkAvailability;

        for (int i = 0; i < 2; i++) {
            purpleButtons[i].setPreferredSize(new Dimension(150, 50));
            purpleButtons[i].setForeground(new Color(164, 92, 255));
            purpleButtons[i].setFont(new Font("Arial", Font.PLAIN, 14));
            purpleButtons[i].setBorder(BorderFactory.createLineBorder(new Color(164, 92, 255), 2));
            purpleButtons[i].addActionListener(this);
            purpleButtons[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        panel1Bottom = new JPanel(new BorderLayout());
        panel1Bottom.setOpaque(false);
        panel1Bottom.setPreferredSize(new Dimension(1000, 60));
        // Panel 1 Bottom West
        panel1BottomWest = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel1BottomWest.setOpaque(false);
        panel1BottomWest.add(checkAvailability);
        panel1BottomWest.add(addConsultations);

        panel1Bottom.add(panel1BottomWest, BorderLayout.WEST);

        mainPanel1.add(addSpace(1000, 10));
        mainPanel1.add(panel1Bottom);

        // Panel 1 Bottom East
        panel1BottomEast = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panel1BottomEast.setOpaque(false);
        panel1BottomEast.setPreferredSize(new Dimension(300, 50));

        ImageIcon buttonIcon = new ImageIcon("src/GUI/Assets/reload.png");
        buttonIcon = scaleImage(buttonIcon, 50, 50);

        ImageIcon finalButtonIcon = buttonIcon;

        refreshButton = new JButton() {
            protected void paintComponent(Graphics g) {
                if (g instanceof Graphics2D g2d) {
                    Paint p =
                            new GradientPaint(0.0f, 0.0f, new Color(198, 139, 255, 255),
                                    getWidth(), getHeight(), new Color(100, 143, 255, 255), true);
                    g2d.setPaint(p);
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                } else {
                    super.paintComponent(g);
                }
                g.drawImage(finalButtonIcon.getImage(), 0, 0, null);
            }
        };
        refreshButton.addActionListener(this);
        refreshButton.addMouseListener(this);
        refreshButton.setPreferredSize(new Dimension(50, 50));
        panel1BottomEast.add(refreshButton);

        panel1Bottom.add(panel1BottomEast, BorderLayout.EAST);


        // Main Panel 2 Components
        tablePanel = new JPanel(new BorderLayout());
        tablePanel.setOpaque(true);
        tablePanel.setPreferredSize(new Dimension(1000, 480));

        // Table
        String[] sessionsTableColumns = {"Consultations ID", "Patient Name", "Doctor Name", "Date", "Time", "Token Number"};

        ArrayList<Consultation> consultations = WestminsterSkinConsultationManager.getConsultationsArrayList();
        String[][] consultationsData = new String[consultations.size()][6];

        numberOfConsultations = consultations.size();

        for (int i = 0; i < consultations.size(); i++) {
            Consultation consultation = consultations.get(i);
            consultationsData[i][0] = consultation.getConsultationId();
            consultationsData[i][1] = consultation.getPatient().getName() + " " + consultation.getPatient().getSurName();
            consultationsData[i][2] = consultation.getDoctor().getName() + " " + consultation.getDoctor().getSurName();
            consultationsData[i][3] = consultation.getStringDate();
            consultationsData[i][4] = consultation.getStringTime();
            consultationsData[i][5] = String.valueOf(consultation.getTokenNumber());
        }


        consultationsTable = new JTable();
        consultationsTable.setRowHeight(40);
        consultationsTable.setModel(new DefaultTableModel(consultationsData, sessionsTableColumns));
        consultationsTable.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        consultationsTable.setShowGrid(true);
        consultationsTable.setGridColor(new Color(239, 209, 255, 255));
        JTableHeader header = consultationsTable.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        header.setBackground(new Color(51, 0, 101, 255));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(100, 40));
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        consultationsTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int viewRow = consultationsTable.getSelectedRow();

                if (!e.getValueIsAdjusting() && viewRow != -1) {
                    int columnIndex = 0;
                    int modelRow = consultationsTable.convertRowIndexToModel(viewRow);
                    Object consultationId = consultationsTable.getModel().getValueAt(modelRow, columnIndex);
                    Consultation consultation = null;

                    for (Consultation tempConsultation : WestminsterSkinConsultationManager.getConsultationsArrayList()) {
                        if (tempConsultation.getConsultationId().equals(consultationId)) {
                            consultation = tempConsultation;
                            break;
                        }
                    }

                    if (consultation != null) {
                        new ConsultationView(consultation);
                    }
                }
            }
        });

        consultationsTableScroll = new JScrollPane(consultationsTable);
        consultationsTableScroll.setPreferredSize(new Dimension(1000, 480));
        consultationsTableScroll.setOpaque(true);

        tablePanel.setOpaque(true);
        tablePanel.setPreferredSize(new Dimension(1000, 480));

        for (int i = 0; i < consultationsTable.getColumnCount(); i++) {
            consultationsTable.getColumnModel().getColumn(i).setCellRenderer(new StatusColumnCellRenderer());
        }

        TitledBorder tablePanelBorder = new TitledBorder("Click on the row to view the consultation details.");
        tablePanelBorder.setTitleFont(new Font("Segoe UI", Font.PLAIN, 16));
        tablePanel.setBorder(tablePanelBorder);

        tablePanel.add(consultationsTableScroll, BorderLayout.CENTER);
        mainPanel2.add(tablePanel, BorderLayout.CENTER);

        // Main Panel 3 Components
        panel3West = new JPanel(new FlowLayout());
        panel3West.setOpaque(false);
        panel3West.setPreferredSize(new Dimension(250, 50));

        panel3East = new JPanel(new FlowLayout());
        panel3East.setOpaque(false);
        panel3East.setPreferredSize(new Dimension(340, 50));


        // Panel 3 East Components
        saveDataButton = new JButton("Save Data");
        saveDataButton.addMouseListener(this);
        saveDataButton.addActionListener(this);
        sortDataButton = new JButton("Sort Data");
        sortDataButton.addMouseListener(this);
        sortDataButton.addActionListener(this);

        panel3Buttons[0] = saveDataButton;
        panel3Buttons[1] = sortDataButton;

        panel3ButtonIconPaths[0] = "src/GUI/Assets/save.png";
        panel3ButtonIconPaths[1] = "src/GUI/Assets/sort.png";

        for (int i = 0; i < 2; i++) {
            try {
                ImageIcon panel3buttonIcon = new ImageIcon(panel3ButtonIconPaths[i]);
                panel3buttonIcon = scaleImage(panel3buttonIcon, 20, 20);
                panel3Buttons[i].setIcon(panel3buttonIcon);
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
            panel3Buttons[i].setFont(new Font("Segoe UI", Font.PLAIN, 20));
            panel3Buttons[i].setPreferredSize(new Dimension(150, 50));
            panel3Buttons[i].setOpaque(true);
            panel3Buttons[i].setBackground(new Color(215, 215, 215));
            panel3Buttons[i].setForeground(new Color(107, 107, 107));
            panel3Buttons[i].setBorderPainted(false);
            panel3Buttons[i].setFocusPainted(false);
            panel3Buttons[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        panel3East.add(panel3Buttons[0]);
        panel3East.add(panel3Buttons[1]);

        // Panel 3 West Components
        allConsultations = new JLabel("All Consultations " + numberOfConsultations);
        allConsultations.setPreferredSize(new Dimension(250, 50));
        allConsultations.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        allConsultations.setForeground(Color.GRAY);
        allConsultations.setOpaque(false);

        panel3West.add(allConsultations);

        mainPanel3.add(addSpace(0, 20), BorderLayout.NORTH);
        mainPanel3.add(panel3West, BorderLayout.WEST);
        mainPanel3.add(panel3East, BorderLayout.EAST);
    }

    /**
     * All MAIN Events are handled here
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addConsultations) {
            new AddConsultation();
        }
        if (e.getSource() == checkAvailability) {
            new CheckConsultationAvailability();
        }
        if (e.getSource() == refreshButton) {
            tableReRender(WestminsterSkinConsultationManager.getConsultationsArrayList());
        }
        if (e.getSource() == saveDataButton) {
            WestminsterSkinConsultationManager manager = new WestminsterSkinConsultationManager();
            manager.saveConsultationsToFile();
            new SavingDone();
        }
        if (e.getSource() == sortDataButton) {
            WestminsterSkinConsultationManager manager = new WestminsterSkinConsultationManager();
            manager.sortConsultation(WestminsterSkinConsultationManager.getConsultationsArrayList());
            tableReRender(WestminsterSkinConsultationManager.getConsultationsArrayList());
            new SortingDone();
        }
    }

    /**
     * All MOUSE Events are handled here
     */
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() == addConsultations) {
            addConsultations.setBackground(new Color(164, 92, 255));
            addConsultations.setOpaque(true);
            addConsultations.setForeground(Color.WHITE);
        }

        if (e.getSource() == checkAvailability) {
            checkAvailability.setBackground(new Color(164, 92, 255));
            checkAvailability.setOpaque(true);
            checkAvailability.setForeground(Color.WHITE);
        }
        if (e.getSource() == refreshButton) {
            refreshButton.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255), 4));
        }
        if (e.getSource() == saveDataButton) {
            saveDataButton.setBackground(new Color(164, 92, 255));
            saveDataButton.setOpaque(true);
            saveDataButton.setForeground(Color.WHITE);
            try {
                ImageIcon saveBtnIcon = new ImageIcon("src/GUI/Assets/saveWhite.png");
                saveBtnIcon = scaleImage(saveBtnIcon, 20, 20);
                saveDataButton.setIcon(saveBtnIcon);
            } catch (Exception ee) {
                System.out.println("Error: " + ee);
            }
        }
        if (e.getSource() == sortDataButton) {
            sortDataButton.setBackground(new Color(164, 92, 255));
            sortDataButton.setOpaque(true);
            sortDataButton.setForeground(Color.WHITE);
            try {
                ImageIcon sortBtnIcon = new ImageIcon("src/GUI/Assets/sortWhite.png");
                sortBtnIcon = scaleImage(sortBtnIcon, 20, 20);
                sortDataButton.setIcon(sortBtnIcon);
            } catch (Exception ee) {
                System.out.println("Error: " + ee);
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() == addConsultations) {
            addConsultations.setOpaque(false);
            addConsultations.setForeground(new Color(164, 92, 255));
            addConsultations.setBorder(BorderFactory.createLineBorder(new Color(164, 92, 255), 2));
        }

        if (e.getSource() == checkAvailability) {
            checkAvailability.setOpaque(false);
            checkAvailability.setForeground(new Color(164, 92, 255));
            checkAvailability.setBorder(BorderFactory.createLineBorder(new Color(164, 92, 255), 2));
        }
        if (e.getSource() == refreshButton) {
            refreshButton.setBorder(null);
        }
        if (e.getSource() == saveDataButton) {
            saveDataButton.setOpaque(true);
            saveDataButton.setBackground(new Color(215, 215, 215));
            saveDataButton.setForeground(new Color(107, 107, 107));
            try {
                ImageIcon saveBtnIcon = new ImageIcon("src/GUI/Assets/save.png");
                saveBtnIcon = scaleImage(saveBtnIcon, 20, 20);
                saveDataButton.setIcon(saveBtnIcon);
            } catch (Exception ee) {
                System.out.println("Error: " + ee);
            }
        }
        if (e.getSource() == sortDataButton) {
            sortDataButton.setOpaque(true);
            sortDataButton.setBackground(new Color(215, 215, 215));
            sortDataButton.setForeground(new Color(107, 107, 107));
            try {
                ImageIcon sortBtnIcon = new ImageIcon("src/GUI/Assets/sort.png");
                sortBtnIcon = scaleImage(sortBtnIcon, 20, 20);
                sortDataButton.setIcon(sortBtnIcon);
            } catch (Exception ee) {
                System.out.println("Error: " + ee);
            }
        }
    }

    /**
     * This method is used to re-render the table.
     * Re-rendering the table is necessary when the data is changed.
     * This method is called when the refresh button is clicked.
     * The re-rendering is done by changing the Default Table Model.
     */
    public static void tableReRender(ArrayList<Consultation> consultations) {
        String[] sessionsTableColumns = {"Consultations ID", "Patient Name", "Doctor Name", "Date", "Time", "Token Number"};

        String[][] consultationsData = new String[consultations.size()][6];

        numberOfConsultations = consultations.size();

        for (int i = 0; i < consultations.size(); i++) {
            Consultation consultation = consultations.get(i);
            consultationsData[i][0] = consultation.getConsultationId();
            consultationsData[i][1] = consultation.getPatient().getName() + " " + consultation.getPatient().getSurName();
            consultationsData[i][2] = consultation.getDoctor().getName() + " " + consultation.getDoctor().getSurName();
            consultationsData[i][3] = consultation.getStringDate();
            consultationsData[i][4] = consultation.getStringTime();
            consultationsData[i][5] = String.valueOf(consultation.getTokenNumber());
        }

        DefaultTableModel model = new DefaultTableModel(consultationsData, sessionsTableColumns);
        consultationsTable.setModel(model);

        for (int i = 0; i < consultationsTable.getColumnCount(); i++) {
            consultationsTable.getColumnModel().getColumn(i).setCellRenderer(new StatusColumnCellRenderer());
        }
        allConsultations.setText("All Consultations " + numberOfConsultations);
    }

    /**
     * These methods are used to get changing value from search field
     *
     * @param e the document event
     */
    @Override
    public void insertUpdate(DocumentEvent e) {
        if (e.getDocument() == searchField.getDocument()) {
            filterDataBySearchField();
        }
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        if (e.getDocument() == searchField.getDocument()) {
            filterDataBySearchField();
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        if (e.getDocument() == searchField.getDocument()) {
            filterDataBySearchField();
        }
    }

    /**
     * This method is used to filter the data in the table by the search field.
     * The search field is used to search for a specific consultation.
     */
    public void filterDataBySearchField() {
        ArrayList<Consultation> consultations = WestminsterSkinConsultationManager.getConsultationsArrayList();
        if (!searchField.getText().equals("") && !searchField.getText().equals("Consultation No...")) {
            ArrayList<Consultation> searchedConsultation = new ArrayList<>();
            for (Consultation cons : consultations) {
                if (cons.getConsultationId().toLowerCase().contains(searchField.getText().toLowerCase())) {
                    searchedConsultation.add(cons);
                }
            }
            tableReRender(searchedConsultation);
        } else {
            tableReRender(consultations);
        }
    }
}