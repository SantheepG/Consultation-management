package GUI;

import Classes.WestminsterSkinConsultationManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame implements ActionListener {
    static JFrame mainFrame = null;
    JPanel leftPanel, rightPanel;

    JTextField userNameField;
    JPasswordField passwordField;
    JButton loginButton;
    JButton cancelButton;

    public LoginFrame() {
        mainFrame = new JFrame();
        mainFrame.setSize(500, 700);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setTitle("Login");
        mainFrame.setResizable(false);


        JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        mainPanel.setPreferredSize(new Dimension(450, 700));
        mainPanel.setBackground(Color.WHITE);

        mainPanel.add(MainFrame.addSpace(450, 10));

        ImageIcon image = new ImageIcon("src/GUI/Assets/loginBG_02.png");
        Image img = image.getImage();
        Image newImg = img.getScaledInstance(450, 150, Image.SCALE_SMOOTH);
        ImageIcon newImc = new ImageIcon(newImg);
        JLabel label = new JLabel(newImc);
        mainPanel.add(label);

        mainPanel.add(MainFrame.addSpace(450, 20));

        JLabel loginLabel = new JLabel("Login to system");
        loginLabel.setFont(new Font("Arial", Font.BOLD, 20));
        loginLabel.setForeground(Color.decode("#400000"));
        mainPanel.add(loginLabel);

        mainPanel.add(MainFrame.addSpace(450, 10));
        // Text field

        userNameField = new JTextField("  Username");
        userNameField.setPreferredSize(new Dimension(250, 40));
        userNameField.setFont(new Font("Arial", Font.PLAIN, 16));
        userNameField.setForeground(Color.decode("#400000"));
        userNameField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.decode("#400000")));
        userNameField.setOpaque(false);
        userNameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (userNameField.getText().equals("  Username")) {
                    userNameField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (userNameField.getText().equals("")) {
                    userNameField.setText("  Username");
                }
            }
        });
        mainPanel.add(userNameField);
        //.add(MainFrame.addSpace(450, 10));


        passwordField = new JPasswordField("  Password");
        passwordField.setPreferredSize(new Dimension(250, 40));
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordField.setForeground(Color.decode("#400000"));
        passwordField.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.decode("#400000")));
        passwordField.setOpaque(false);
        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (passwordField.getText().equals("  Password")) {
                    passwordField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (passwordField.getText().equals("")) {
                    passwordField.setText("  Password");
                }
            }
        });
        mainPanel.add(passwordField);
        mainPanel.add(MainFrame.addSpace(450, 10));

        JTextField temp = new JTextField();
        temp.setPreferredSize(new Dimension(250, 1));
        temp.setBorder(null);
        temp.setOpaque(false);

        // Login Button
        loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(150, 40));
        loginButton.setFont(new Font("Arial", Font.PLAIN, 16));
        loginButton.setBorder(BorderFactory.createLineBorder(Color.decode("#400000"), 2));
        loginButton.setBackground(Color.decode("#400000"));
        loginButton.setForeground(new Color(255, 255, 255));
        loginButton.setOpaque(true);
        loginButton.addActionListener(this);
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                loginButton.setOpaque(false);
                loginButton.setForeground(Color.decode("#400000"));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                loginButton.setOpaque(true);
                loginButton.setBackground(Color.decode("#400000"));
                loginButton.setForeground(new Color(255, 255, 255));
            }
        });
        mainPanel.add(loginButton);
        mainPanel.add(MainFrame.addSpace(450, 10));

        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            if (userNameField.getText().equals("  Username") || passwordField.getText().equals("  Password")) {
                JOptionPane.showMessageDialog(null, "Please enter your username and password");
            } else {
                String username = userNameField.getText();
                String password = passwordField.getText();
                if (username.equals("admin") && password.equals("admin")) {
                    WestminsterSkinConsultationManager manager = new WestminsterSkinConsultationManager();
                    manager.runGUI();
                    mainFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password");
                }
            }
        } else if (e.getSource() == cancelButton) {
            int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to close the application?", "Close Application", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }
}
