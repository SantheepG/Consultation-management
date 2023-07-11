package GUI;

import GUI.GUIModels.RoundedBorder;
import GUI.Main_Frames.*;
import Classes.WestminsterSkinConsultationManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame implements ActionListener {
    JPanel leftPanel, rightPanel;
    DashBoardPanel dashBoardPanel;
    DoctorsPanel doctorsPanel;
    PatientsPanel patientsPanel;
    SessionsPanel sessionsPanel;
    ConsultationsPanel consultationsPanel;
    JPanel operatorContainer, logoContainer, logoutContainer;
    JButton dashboardButton, doctorButton, patientButton, appointmentButton, sessionButton, btn1, btn2, btn3;
    JButton logoutButton;

    JButton[] leftPanelOperatorButtons = new JButton[6];
    String[] leftPanelIconPaths = new String[6];

    ImageIcon logo;

    public MainFrame() {
        setLayout(new BorderLayout());

        // Main left panel
        leftPanel = new JPanel(new BorderLayout()) {
            protected void paintComponent(Graphics g) {
                if (g instanceof Graphics2D g2d) {
                    Paint p =
                            new GradientPaint(0.0f, 0.0f, new Color(106, 0, 212, 255),
                                    getWidth(), getHeight(), new Color(0, 21, 96, 255), true);
                    g2d.setPaint(p);
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                } else {
                    super.paintComponent(g);
                }
            }
        };
        leftPanel.setBackground(new Color(0, 0, 0, 0));
        leftPanel.setPreferredSize(new Dimension(270, 600));
        add(leftPanel, BorderLayout.WEST);

        // Left panel logo
        logoContainer = new JPanel(new BorderLayout());
        logoContainer.setOpaque(true);
        logoContainer.setBackground(new Color(0, 0, 0, 0));
        logoContainer.setPreferredSize(new Dimension(300, 45));

        logo = new ImageIcon("src/GUI/Assets/uow.png");
        logo = scaleImage(logo, 50, 50);

        JLabel logoLabel = new JLabel(logo);
        JLabel logoTextUp = new JLabel("Westminster");
        logoTextUp.setForeground(Color.WHITE);
        logoTextUp.setFont(new Font("Arial", Font.BOLD, 20));
        JLabel logoTextDown = new JLabel("Skin Consultation Manager");
        logoTextDown.setForeground(Color.WHITE);
        logoTextDown.setFont(new Font("Arial", Font.BOLD, 10));

        JPanel logoTextContainer = new JPanel(new GridLayout(2, 1));
        logoTextContainer.setOpaque(true);
        logoTextContainer.setBackground(new Color(0, 0, 0, 0));
        logoTextContainer.setPreferredSize(new Dimension(300, 50));
        logoTextContainer.add(logoTextUp);
        logoTextContainer.add(logoTextDown);

        JPanel leftTopPanel = new JPanel(new BorderLayout());
        leftTopPanel.setOpaque(true);
        leftTopPanel.setBackground(new Color(0, 0, 0, 0));
        leftTopPanel.setPreferredSize(new Dimension(300, 65));

        logoContainer.add(addSpace(300, 20), BorderLayout.NORTH);
        logoContainer.add(logoLabel, BorderLayout.WEST);
        logoContainer.add(logoTextContainer, BorderLayout.CENTER);

        leftTopPanel.add(addSpace(50, 65), BorderLayout.WEST);
        leftTopPanel.add(logoContainer, BorderLayout.CENTER);

        leftPanel.add(leftTopPanel, BorderLayout.NORTH);


        // Container for left panel operators
        operatorContainer = new JPanel();
        operatorContainer.setOpaque(false);
        operatorContainer.setPreferredSize(new Dimension(300, 500));
        leftPanel.add(operatorContainer, BorderLayout.CENTER);

        // left panel operator buttons
        dashboardButton = new JButton("Dashboard");
        doctorButton = new JButton("Doctor");
        patientButton = new JButton("Patient");
        appointmentButton = new JButton("Appointment");
        sessionButton = new JButton("Session");
        logoutButton = new JButton("Logout");

        leftPanelOperatorButtons[0] = dashboardButton;
        leftPanelOperatorButtons[1] = doctorButton;
        leftPanelOperatorButtons[2] = patientButton;
        leftPanelOperatorButtons[3] = appointmentButton;
        leftPanelOperatorButtons[4] = sessionButton;
        leftPanelOperatorButtons[5] = logoutButton;

        leftPanelIconPaths[0] = "src/GUI/Assets/dashboard.png";
        leftPanelIconPaths[1] = "src/GUI/Assets/doctor.png";
        leftPanelIconPaths[2] = "src/GUI/Assets/patient.png";
        leftPanelIconPaths[3] = "src/GUI/Assets/appointments.png";
        leftPanelIconPaths[4] = "src/GUI/Assets/sessions.png";
        leftPanelIconPaths[5] = "src/GUI/Assets/logout.png";


        for (int i = 0; i < leftPanelOperatorButtons.length; i++) {
            leftPanelOperatorButtons[i].setPreferredSize(new Dimension(250, 50));
            try {
                Image buttonIcon = new ImageIcon(leftPanelIconPaths[i]).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
                leftPanelOperatorButtons[i].setIcon(new ImageIcon(buttonIcon));
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }

            leftPanelOperatorButtons[i].setBorderPainted(false);
            leftPanelOperatorButtons[i].setContentAreaFilled(false);
            leftPanelOperatorButtons[i].setFocusPainted(false);
            leftPanelOperatorButtons[i].setOpaque(false);
            leftPanelOperatorButtons[i].setForeground(Color.WHITE);
            leftPanelOperatorButtons[i].setFocusable(false);
            leftPanelOperatorButtons[i].setFont(new Font("Arial", Font.PLAIN, 20));
            leftPanelOperatorButtons[i].setHorizontalAlignment(SwingConstants.LEFT);

            leftPanelOperatorButtons[i].addActionListener(this);
        }
        dashboardButton.setBorderPainted(true);
        dashboardButton.setBorder(new RoundedBorder(20));

        int buttonGap = 2;

        operatorContainer.add(addSpace(300, 60));
        operatorContainer.add(dashboardButton);
        operatorContainer.add(addSpace(300, buttonGap));
        operatorContainer.add(doctorButton);
        operatorContainer.add(addSpace(300, buttonGap));
        operatorContainer.add(patientButton);
        operatorContainer.add(addSpace(300, buttonGap));
        operatorContainer.add(appointmentButton);
        operatorContainer.add(addSpace(300, buttonGap));
        operatorContainer.add(sessionButton);

        logoutContainer = new JPanel();
        logoutContainer.setOpaque(false);

        logoutContainer.add(logoutButton);
        logoutContainer.setBackground(new Color(0, 0, 0, 0));
        logoutButton.setHorizontalAlignment(SwingConstants.CENTER);

        leftPanel.add(logoutContainer, BorderLayout.SOUTH);

        //Addition of the right panel
        rightPanel = new JPanel(new BorderLayout());
        rightPanel.setPreferredSize(new Dimension(1000, 800));
        btn1 = new JButton("Dashboard");
        btn2 = new JButton("Doctor");
        btn3 = new JButton("Patient");

        rightPanel.add(btn1);


        dashBoardPanel = new DashBoardPanel();
        //uncomment the line below to add default dashboard**********
        changeRightPanel(dashBoardPanel);
        rightPanel.add(dashBoardPanel, BorderLayout.CENTER);

//        doctorsPanel = new DoctorsPanel();
//        changeRightPanel(doctorsPanel);
//        rightPanel.add(doctorsPanel, BorderLayout.CENTER);

        doctorsPanel = new DoctorsPanel();
        patientsPanel = new PatientsPanel();
        sessionsPanel = new SessionsPanel();
        consultationsPanel = new ConsultationsPanel();

        add(rightPanel, BorderLayout.CENTER);

    }
    // End of constructor


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == dashboardButton) {
            changeClickedButtonStyle(dashboardButton);
            changeRightPanel(dashBoardPanel);
        } else if (e.getSource() == doctorButton) {
            changeClickedButtonStyle(doctorButton);
            changeRightPanel(doctorsPanel);
        } else if (e.getSource() == patientButton) {
            changeClickedButtonStyle(patientButton);
            changeRightPanel(patientsPanel);
        } else if (e.getSource() == appointmentButton) {
            changeClickedButtonStyle(appointmentButton);
            changeRightPanel(consultationsPanel);
            ConsultationsPanel.tableReRender(WestminsterSkinConsultationManager.getConsultationsArrayList());

        } else if (e.getSource() == sessionButton) {
            changeClickedButtonStyle(sessionButton);
            changeRightPanel(sessionsPanel);

        } else if (e.getSource() == logoutButton) {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(null, "Are you logout?", "Warning", dialogButton);

            if (dialogResult == JOptionPane.YES_OPTION) {
                dispose();
                new LoginFrame();
            }
        }
    }

    /**
     * This method is used to scale ImageIcons
     *
     * @param icon   ImageIcon to be scaled
     * @param width  width of the scaled ImageIcon
     * @param height height of the scaled ImageIcon
     * @return scaled ImageIcon
     */
    public static ImageIcon scaleImage(ImageIcon icon, int width, int height) {
        int newWidth = icon.getIconWidth();
        int newHeight = icon.getIconHeight();

        if (icon.getIconWidth() > width) {
            newWidth = width;
            newHeight = (newWidth * icon.getIconHeight()) / icon.getIconWidth();
        }

        if (newHeight > height) {
            newHeight = height;
            newWidth = (icon.getIconWidth() * newHeight) / icon.getIconHeight();
        }

        return new ImageIcon(icon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT));
    }

    /**
     * This method is used to add space between components
     *
     * @param width  width of the space
     * @param height height of the space
     * @return space
     */
    public static JPanel addSpace(int width, int height) {
        JPanel space = new JPanel();
        space.setPreferredSize(new Dimension(width, height));
        space.setOpaque(true);
        space.setBackground(new Color(0, 0, 0, 0));

        return space;
    }

    /**
     * This method is used to change the style of the clicked button
     *
     * @param button button to be changed
     */
    public void changeClickedButtonStyle(JButton button) {
        for (int i = 0; i < leftPanelOperatorButtons.length; i++) {
            if (leftPanelOperatorButtons[i] == button) {
                leftPanelOperatorButtons[i].setBorderPainted(true);
                leftPanelOperatorButtons[i].setBorder(new RoundedBorder(20));

            } else {
                leftPanelOperatorButtons[i].setBorderPainted(false);
                leftPanelOperatorButtons[i].setBorder(null);
            }
        }
    }

    public void changeRightPanel(JPanel panel) {
        rightPanel.removeAll();
        rightPanel.add(panel, BorderLayout.CENTER);
        rightPanel.revalidate();
        rightPanel.repaint();
    }

}
