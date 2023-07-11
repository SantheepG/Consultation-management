package GUI.Main_Frames.SubFrames;

import GUI.MainFrame;
import GUI.Main_Frames.ConsultationsPanel;
import GUI.Main_Frames.PatientsPanel;
import GUI.GUIModels.DatePicker;
import Classes.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import static GUI.MainFrame.scaleImage;

public class AddConsultation extends JFrame implements ActionListener {
    // Variables
    private String patientId;
    Person patient;
    Person doctor;
    Session session;
    Date date;
    String note;
    ArrayList<String> imagePaths = new ArrayList<>();
    double duration;


    // Main Frame
    private JFrame mainFrame;

    // Top Panel
    JPanel panelTop, patientContainer, panelBottom;
    JButton fromExistingPatientButton, fromNewPatientButton;

    // Bottom Panel
    JLabel patientNameLabel, selectPatientLabel, patientGenderLabel, patientAgeLabel, patientDOBLabel, patientMobileNumberLabel, doctorLabel, dateLabel, hoursLabel, availableBtnLabel;
    JLabel[] jLabelsStyleArray = new JLabel[10];
    // ContainerPanels
    JPanel[] containerPanelArray = new JPanel[10];

    // TextFields
    JTextField patientNameTextField, patientGenderTextField, patientAgeTextField, patientDOBTextField, patientMobileNumberTextField, timeTextField, numberTextField;
    JButton checkAvailabilityButton;
    JComboBox patientNameComboBox, doctorComboBox, hoursComboBox;
    final JTextField sessionDate = new JTextField();
    JTextArea notesTextArea;
    JButton attachImageButton;
    JPanel imageAttachmentPanel;
    ArrayList<File> imageFiles = new ArrayList<>();
    JPanel imagePreviewPanel;
    JPanel patientDetails, doctorDetails, consultationDetails;
    JPanel patientDetailsTop, patientDetailsBottom;
    JPanel doctorDetailsTop, doctorDetailsBottom;
    JButton find, addConsultationButton, cancelButton;

    // New Patient components
    JPanel newPatientPanel, newPatientPanelTop, newPatientPanelBottom;
    JLabel newPatientFNameLabel, newPatientLNameLabel, newPatientDOBLabel, newPatientMobileNumberLabel, newPatientIDLabel, newPatientGenderLabel;
    JLabel[] newPatientLabelsArray = new JLabel[6];
    JPanel[] newPatientContainerPanelArray = new JPanel[6];
    JTextField newPatientFNameTextField, newPatientLNameTextField, newPatientMobileNumberTextField, newPatientIDTextField;
    final JTextField newPatientDOBTextField = new JTextField();

    JButton addNewPatientButton;
    JComboBox newPatientGenderComboBox;


    public AddConsultation() {
        // Main Frame
        mainFrame = new JFrame("Add Consultation");
        mainFrame.setSize(700, 1000);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(MainFrame.getFrames()[0]);

        // Labels
        patientNameLabel = new JLabel("  Patient Name");
        selectPatientLabel = new JLabel("  Select Patient");
        patientGenderLabel = new JLabel("  Gender");
        patientAgeLabel = new JLabel("  Age");
        patientDOBLabel = new JLabel("  Date of Birth");
        patientMobileNumberLabel = new JLabel("  Mobile Number");
        doctorLabel = new JLabel("  Doctor");
        dateLabel = new JLabel("  Date");
        hoursLabel = new JLabel("Session Duration");
        availableBtnLabel = new JLabel("");

        jLabelsStyleArray[0] = patientNameLabel;
        jLabelsStyleArray[1] = selectPatientLabel;
        jLabelsStyleArray[2] = patientGenderLabel;
        jLabelsStyleArray[3] = patientAgeLabel;
        jLabelsStyleArray[4] = patientDOBLabel;
        jLabelsStyleArray[5] = patientMobileNumberLabel;
        jLabelsStyleArray[6] = doctorLabel;
        jLabelsStyleArray[7] = dateLabel;
        jLabelsStyleArray[8] = hoursLabel;
        jLabelsStyleArray[9] = availableBtnLabel;

        for (JLabel jLabel : jLabelsStyleArray) {
            jLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            jLabel.setPreferredSize(new Dimension(200, 17));
        }

        for (int i = 0; i < containerPanelArray.length; i++) {
            containerPanelArray[i] = new JPanel();
            containerPanelArray[i].setLayout(new FlowLayout());
            containerPanelArray[i].setPreferredSize(new Dimension(200, 80));
        }
        // Panels
        // Top Panel
        panelTop = new JPanel();
        panelTop.setLayout(new BorderLayout());
        panelTop.setPreferredSize(new Dimension(700, 50));
        panelTop.setOpaque(false);

        fromExistingPatientButton = new JButton("From Existing Patients");
        fromExistingPatientButton.setPreferredSize(new Dimension(350, 50));
        fromExistingPatientButton.setBorder(null);
        fromExistingPatientButton.setOpaque(false);
        fromExistingPatientButton.setFont(new Font("Arial", Font.BOLD, 14));
        fromExistingPatientButton.addActionListener(this);

        fromNewPatientButton = new JButton("New Patient");
        fromNewPatientButton.setPreferredSize(new Dimension(350, 50));
        fromNewPatientButton.setFont(new Font("Arial", Font.PLAIN, 12));
        fromNewPatientButton.setOpaque(true);
        fromNewPatientButton.setBorderPainted(false);
        fromNewPatientButton.setFocusable(false);
        fromNewPatientButton.setBorder(null);
        fromNewPatientButton.setBackground(new Color(194, 194, 194, 255));
        fromNewPatientButton.setForeground(new Color(138, 138, 138, 255));
        fromNewPatientButton.addActionListener(this);

        panelTop.add(fromExistingPatientButton, BorderLayout.WEST);
        panelTop.add(fromNewPatientButton, BorderLayout.EAST);

        // Bottom Panel
        panelBottom = new JPanel();
        panelBottom.setLayout(new FlowLayout());
        panelBottom.setPreferredSize(new Dimension(700, 550));
        panelBottom.setOpaque(false);

        // Patient Details Panel
        patientDetails = new JPanel();
        patientDetails.setLayout(new FlowLayout());
        patientDetails.setPreferredSize(new Dimension(700, 220));
        patientDetails.setOpaque(true);

        patientDetailsTop = new JPanel();
        patientDetailsTop.setLayout(new FlowLayout());
        patientDetailsTop.setPreferredSize(new Dimension(700, 80));
        patientDetailsTop.setOpaque(false);

        patientDetailsBottom = new JPanel();
        patientDetailsBottom.setLayout(new FlowLayout());
        patientDetailsBottom.setPreferredSize(new Dimension(700, 80));
        patientDetailsBottom.setOpaque(false);

        patientNameTextField = new JTextField();
        patientNameTextField.setPreferredSize(new Dimension(200, 40));
        patientNameTextField.setFont(new Font("Arial", Font.PLAIN, 16));
        patientNameTextField.setEditable(false);

        patientGenderTextField = new JTextField();
        patientGenderTextField.setPreferredSize(new Dimension(200, 40));
        patientGenderTextField.setFont(new Font("Arial", Font.PLAIN, 16));
        patientGenderTextField.setEditable(false);

        patientAgeTextField = new JTextField();
        patientAgeTextField.setPreferredSize(new Dimension(200, 40));
        patientAgeTextField.setFont(new Font("Arial", Font.PLAIN, 16));
        patientAgeTextField.setEditable(false);

        patientDOBTextField = new JTextField();
        patientDOBTextField.setPreferredSize(new Dimension(200, 40));
        patientDOBTextField.setFont(new Font("Arial", Font.PLAIN, 16));
        patientDOBTextField.setEditable(false);

        patientMobileNumberTextField = new JTextField();
        patientMobileNumberTextField.setPreferredSize(new Dimension(200, 40));
        patientMobileNumberTextField.setFont(new Font("Arial", Font.PLAIN, 16));
        patientMobileNumberTextField.setEditable(false);

        String[] patientNames = new String[WestminsterSkinConsultationManager.getPatientArrayList().size()];
        if (WestminsterSkinConsultationManager.getPatientArrayList().size() > 0) {
            patientNames[0] = "Select Patient";
            for (int i = 1; i < WestminsterSkinConsultationManager.getPatientArrayList().size(); i++) {
                Patient patient = (Patient) WestminsterSkinConsultationManager.getPatientArrayList().get(i);
                patientNames[i] = patient.getPatientId() + " " + patient.getName() + " " + patient.getSurName();
            }
        }
        patientNameComboBox = new JComboBox(patientNames);
        patientNameComboBox.setPreferredSize(new Dimension(140, 40));
        JPanel patientNameComboBoxPanel = new JPanel(new FlowLayout());
        find = new JButton("Find");
        find.addActionListener(this);
        find.setFont(new Font("Arial", Font.PLAIN, 12));
        find.setPreferredSize(new Dimension(60, 30));
        patientNameComboBoxPanel.add(patientNameComboBox);
        patientNameComboBoxPanel.add(find);


        containerPanelArray[0].add(selectPatientLabel);
        containerPanelArray[0].add(patientNameComboBoxPanel);

        containerPanelArray[1].add(patientNameLabel);
        containerPanelArray[1].add(patientNameTextField);

        containerPanelArray[2].add(patientGenderLabel);
        containerPanelArray[2].add(patientGenderTextField);

        containerPanelArray[3].add(patientAgeLabel);
        containerPanelArray[3].add(patientAgeTextField);

        containerPanelArray[4].add(patientDOBLabel);
        containerPanelArray[4].add(patientDOBTextField);

        containerPanelArray[5].add(patientMobileNumberLabel);
        containerPanelArray[5].add(patientMobileNumberTextField);

        patientDetailsTop.add(containerPanelArray[0]);
        patientDetailsTop.add(containerPanelArray[1]);
        patientDetailsTop.add(containerPanelArray[2]);

        patientDetailsBottom.add(containerPanelArray[3]);
        patientDetailsBottom.add(containerPanelArray[4]);
        patientDetailsBottom.add(containerPanelArray[5]);

        patientDetails.add(patientDetailsTop);
        patientDetails.add(patientDetailsBottom);
        patientDetails.add(MainFrame.addSpace(700, 20));
        patientDetails.setBorder(BorderFactory.createTitledBorder("Patient Details"));

        // New Patient Details Panel
        newPatientPanel = new JPanel();
        newPatientPanel.setLayout(new FlowLayout());
        newPatientPanel.setPreferredSize(new Dimension(700, 220));
        newPatientPanel.setOpaque(true);

        newPatientPanelTop = new JPanel();
        newPatientPanelTop.setLayout(new FlowLayout());
        newPatientPanelTop.setPreferredSize(new Dimension(700, 65));
        newPatientPanelTop.setOpaque(false);

        newPatientPanelBottom = new JPanel();
        newPatientPanelBottom.setLayout(new FlowLayout());
        newPatientPanelBottom.setPreferredSize(new Dimension(700, 65));
        newPatientPanelBottom.setOpaque(false);

        newPatientFNameTextField = new JTextField();
        newPatientFNameTextField.setPreferredSize(new Dimension(200, 40));
        newPatientFNameTextField.setFont(new Font("Arial", Font.PLAIN, 16));
        newPatientFNameTextField.setEditable(true);

        newPatientLNameTextField = new JTextField();
        newPatientLNameTextField.setPreferredSize(new Dimension(200, 40));
        newPatientLNameTextField.setFont(new Font("Arial", Font.PLAIN, 16));
        newPatientLNameTextField.setEditable(true);

        newPatientMobileNumberTextField = new JTextField();
        newPatientMobileNumberTextField.setPreferredSize(new Dimension(200, 40));
        newPatientMobileNumberTextField.setFont(new Font("Arial", Font.PLAIN, 16));
        newPatientMobileNumberTextField.setEditable(true);

        newPatientIDTextField = new JTextField();
        newPatientIDTextField.setPreferredSize(new Dimension(200, 40));
        newPatientIDTextField.setFont(new Font("Arial", Font.PLAIN, 16));
        newPatientIDTextField.setEditable(true);

        // Labels
        newPatientFNameLabel = new JLabel("First Name");
        newPatientLNameLabel = new JLabel("Last Name");
        newPatientDOBLabel = new JLabel("Date of Birth");
        newPatientMobileNumberLabel = new JLabel("Mobile Number");
        newPatientIDLabel = new JLabel("Patient ID");
        newPatientGenderLabel = new JLabel("Gender");

        // Label style
        newPatientLabelsArray[0] = newPatientFNameLabel;
        newPatientLabelsArray[1] = newPatientLNameLabel;
        newPatientLabelsArray[2] = newPatientDOBLabel;
        newPatientLabelsArray[3] = newPatientMobileNumberLabel;
        newPatientLabelsArray[4] = newPatientIDLabel;
        newPatientLabelsArray[5] = newPatientGenderLabel;

        for (int i = 0; i < newPatientLabelsArray.length; i++) {
            newPatientLabelsArray[i].setFont(new Font("Arial", Font.PLAIN, 16));
            newPatientLabelsArray[i].setPreferredSize(new Dimension(200, 17));
        }
        for (int i = 0; i < newPatientContainerPanelArray.length; i++) {
            newPatientContainerPanelArray[i] = new JPanel();
            newPatientContainerPanelArray[i].setLayout(new FlowLayout());
            newPatientContainerPanelArray[i].setPreferredSize(new Dimension(200, 60));
        }

        newPatientDOBTextField.setPreferredSize(new Dimension(170, 40));
        newPatientDOBTextField.setFont(new Font("Arial", Font.PLAIN, 16));
        newPatientDOBTextField.setFocusable(false);
        ImageIcon icon = new ImageIcon("src/GUI/Assets/calendar.png");
        icon = scaleImage(icon, 20, 20);

        JButton dateSelectButton = new JButton(icon);
        dateSelectButton.setPreferredSize(new Dimension(30, 30));
        JPanel selectDatePanel = new JPanel(new BorderLayout());
        selectDatePanel.setOpaque(true);
        selectDatePanel.setSize(new Dimension(220, 30));
        selectDatePanel.add(newPatientDOBTextField, BorderLayout.CENTER);
        selectDatePanel.add(dateSelectButton, BorderLayout.EAST);

        dateSelectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                newPatientDOBTextField.setText(new DatePicker(mainFrame).setPickedDate());
            }
        });

        String[] genderTypes = {"Select", "Male", "Female"};
        newPatientGenderComboBox = new JComboBox(genderTypes);
        newPatientGenderComboBox.setPreferredSize(new Dimension(200, 40));

        newPatientContainerPanelArray[0].add(newPatientFNameLabel);
        newPatientContainerPanelArray[0].add(newPatientFNameTextField);

        newPatientContainerPanelArray[1].add(newPatientLNameLabel);
        newPatientContainerPanelArray[1].add(newPatientLNameTextField);

        newPatientContainerPanelArray[2].add(newPatientDOBLabel);
        newPatientContainerPanelArray[2].add(selectDatePanel);

        newPatientContainerPanelArray[3].add(newPatientMobileNumberLabel);
        newPatientContainerPanelArray[3].add(newPatientMobileNumberTextField);

        newPatientContainerPanelArray[4].add(newPatientIDLabel);
        newPatientContainerPanelArray[4].add(newPatientIDTextField);

        newPatientContainerPanelArray[5].add(newPatientGenderLabel);
        newPatientContainerPanelArray[5].add(newPatientGenderComboBox);

        newPatientPanelTop.add(newPatientContainerPanelArray[0]);
        newPatientPanelTop.add(newPatientContainerPanelArray[1]);
        newPatientPanelTop.add(newPatientContainerPanelArray[2]);

        newPatientPanelBottom.add(newPatientContainerPanelArray[3]);
        newPatientPanelBottom.add(newPatientContainerPanelArray[4]);
        newPatientPanelBottom.add(newPatientContainerPanelArray[5]);

        addNewPatientButton = new JButton("Add Patient");
        addNewPatientButton.setPreferredSize(new Dimension(200, 40));
        addNewPatientButton.setFont(new Font("Arial", Font.PLAIN, 16));
        addNewPatientButton.setOpaque(true);
        addNewPatientButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addNewPatientButton.setBorder(BorderFactory.createLineBorder(new Color(164, 92, 255), 2));
        addNewPatientButton.setBackground(new Color(0, 0, 0, 0));
        addNewPatientButton.setForeground(new Color(164, 92, 255));
        addNewPatientButton.addActionListener(this);
        addNewPatientButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                addNewPatientButton.setOpaque(true);
                addNewPatientButton.setBackground(new Color(164, 92, 255));
                addNewPatientButton.setForeground(new Color(255, 255, 255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                addNewPatientButton.setOpaque(false);
                addNewPatientButton.setForeground(new Color(164, 92, 255));
            }
        });

        newPatientPanel.add(newPatientPanelTop);
        newPatientPanel.add(newPatientPanelBottom);
        newPatientPanel.add(addNewPatientButton);
        newPatientPanel.setBorder(BorderFactory.createTitledBorder("Add New Patient"));

        patientContainer = new JPanel();
        patientContainer.setLayout(new FlowLayout());
        patientContainer.setPreferredSize(new Dimension(700, 230));
        patientContainer.setOpaque(false);
        patientContainer.add(patientDetails);

        panelBottom.add(patientContainer);

        // Doctor Details Panel
        doctorDetails = new JPanel();
        doctorDetails.setLayout(new FlowLayout());
        doctorDetails.setPreferredSize(new Dimension(700, 190));
        doctorDetails.setOpaque(true);
        doctorDetails.setBorder(BorderFactory.createTitledBorder("Patient Details"));

        doctorDetailsTop = new JPanel();
        doctorDetailsTop.setLayout(new FlowLayout());
        doctorDetailsTop.setPreferredSize(new Dimension(700, 80));
        doctorDetailsTop.setOpaque(false);

        doctorDetailsBottom = new JPanel();
        doctorDetailsBottom.setLayout(new FlowLayout());
        doctorDetailsBottom.setPreferredSize(new Dimension(700, 80));
        doctorDetailsBottom.setOpaque(false);

        String[] doctorNames = new String[WestminsterSkinConsultationManager.getNumberOfDoctors(WestminsterSkinConsultationManager.getDoctorArray()) + 1];
        doctorNames[0] = "Select Doctor";
        int j = 1;
        for (Person doctor : WestminsterSkinConsultationManager.getDoctorArray()) {
            if (doctor != null) {
                doctorNames[j] = doctor.getName() + " " + doctor.getSurName();
                j++;
            }
        }
        doctorComboBox = new JComboBox(doctorNames);
        doctorComboBox.setPreferredSize(new Dimension(200, 40));
        doctorComboBox.setBounds(200, 200, 200, 40);

        // Date picker
        sessionDate.setPreferredSize(new Dimension(170, 35));
        sessionDate.setFont(new Font("Arial", Font.PLAIN, 16));
        sessionDate.setFocusable(false);
        ImageIcon icon2 = new ImageIcon("src/GUI/Assets/calendar.png");
        icon2 = scaleImage(icon2, 20, 20);

        JButton dateSelectButton2 = new JButton(icon2);
        dateSelectButton2.setPreferredSize(new Dimension(30, 30));
        JPanel selectDatePanel2 = new JPanel(new BorderLayout());
        selectDatePanel2.setOpaque(true);
        selectDatePanel2.setSize(new Dimension(200, 40));
        selectDatePanel2.add(sessionDate, BorderLayout.CENTER);
        selectDatePanel2.add(dateSelectButton2, BorderLayout.EAST);

        dateSelectButton2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                sessionDate.setText(new DatePicker(mainFrame).setPickedDate());
            }
        });

        // Check Availability Button
        checkAvailabilityButton = new JButton("Check Availability");
        checkAvailabilityButton.setPreferredSize(new Dimension(200, 40));
        checkAvailabilityButton.setFont(new Font("Arial", Font.PLAIN, 16));
        checkAvailabilityButton.setBorder(BorderFactory.createLineBorder(new Color(164, 92, 255), 2));
        checkAvailabilityButton.setBackground(new Color(0, 0, 0, 0));
        checkAvailabilityButton.setForeground(new Color(164, 92, 255));
        checkAvailabilityButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        checkAvailabilityButton.addActionListener(this);
        checkAvailabilityButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                checkAvailabilityButton.setOpaque(true);
                checkAvailabilityButton.setBackground(new Color(164, 92, 255));
                checkAvailabilityButton.setForeground(new Color(255, 255, 255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                checkAvailabilityButton.setOpaque(false);
                checkAvailabilityButton.setForeground(new Color(164, 92, 255));
            }
        });

        String[] hours = {"Select", "0.25", "0.50", "1", "2"};
        hoursComboBox = new JComboBox(hours);
        hoursComboBox.setPreferredSize(new Dimension(200, 40));
        hoursComboBox.setFont(new Font("Arial", Font.PLAIN, 16));

        timeTextField = new JTextField();
        timeTextField.setPreferredSize(new Dimension(200, 40));
        timeTextField.setFont(new Font("Arial", Font.PLAIN, 16));
        timeTextField.setEditable(false);

        numberTextField = new JTextField();
        numberTextField.setPreferredSize(new Dimension(200, 40));
        numberTextField.setFont(new Font("Arial", Font.PLAIN, 16));
        numberTextField.setEditable(false);

        containerPanelArray[6].add(doctorLabel);
        containerPanelArray[6].add(doctorComboBox);

        containerPanelArray[7].add(dateLabel);
        containerPanelArray[7].add(selectDatePanel2);

        containerPanelArray[8].add(MainFrame.addSpace(200, 10));
        containerPanelArray[8].add(checkAvailabilityButton);

        containerPanelArray[9].setPreferredSize(new Dimension(410, 40));
        hoursLabel.setPreferredSize(new Dimension(130, 17));
        containerPanelArray[9].add(hoursLabel);
        containerPanelArray[9].add(hoursComboBox);


        doctorDetailsTop.add(containerPanelArray[6]);
        doctorDetailsTop.add(containerPanelArray[7]);
        doctorDetailsTop.add(containerPanelArray[8]);
        doctorDetailsBottom.add(containerPanelArray[9]);
        doctorDetailsBottom.setPreferredSize(new Dimension(400, 40));

        doctorDetails.add(doctorDetailsTop);
        doctorDetails.add(doctorDetailsBottom);


        // Consultation Details Panel
        consultationDetails = new JPanel();
        consultationDetails.setLayout(new FlowLayout());
        consultationDetails.setPreferredSize(new Dimension(700, 350));
        consultationDetails.setOpaque(false);
        consultationDetails.setBorder(BorderFactory.createTitledBorder("Consultation Details"));

        JLabel noteLabel = new JLabel("Notes");
        noteLabel.setPreferredSize(new Dimension(600, 17));
        noteLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        notesTextArea = new JTextArea();
        notesTextArea.setPreferredSize(new Dimension(600, 70));
        notesTextArea.setFont(new Font("Arial", Font.PLAIN, 16));
        notesTextArea.setLineWrap(true);
        notesTextArea.setWrapStyleWord(true);

        consultationDetails.add(noteLabel);
        consultationDetails.add(notesTextArea);

        // Image Attachment Panel
        imageAttachmentPanel = new JPanel(new BorderLayout());
        JPanel imageAttachmentTop = new JPanel(new BorderLayout());
        imageAttachmentTop.setPreferredSize(new Dimension(600, 40));
        JLabel imageLabel = new JLabel("Skin Images");
        imageLabel.setPreferredSize(new Dimension(300, 17));
        imageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        imageAttachmentTop.add(imageLabel, BorderLayout.WEST);

        JLabel label = new JLabel();
        label.setPreferredSize(new Dimension(300, 17));

        // Image Preview Panel
        imagePreviewPanel = new JPanel(new GridLayout(2, 3));
        imagePreviewPanel.setPreferredSize(new Dimension(600, 150));
        imagePreviewPanel.setOpaque(true);
        imagePreviewPanel.setBackground(new Color(255, 255, 255));

        attachImageButton = new JButton("Attach Image");
        attachImageButton.setPreferredSize(new Dimension(200, 40));
        attachImageButton.setFont(new Font("Arial", Font.PLAIN, 16));
        attachImageButton.setBorder(BorderFactory.createLineBorder(new Color(164, 92, 255), 2));
        attachImageButton.setBackground(new Color(0, 0, 0, 0));
        attachImageButton.setForeground(new Color(164, 92, 255));
        attachImageButton.setOpaque(false);
        attachImageButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                attachImageButton.setOpaque(true);
                attachImageButton.setBackground(new Color(164, 92, 255));
                attachImageButton.setForeground(new Color(255, 255, 255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                attachImageButton.setOpaque(false);
                attachImageButton.setForeground(new Color(164, 92, 255));
            }
        });
        attachImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Image Files", "jpg", "png", "gif", "jpeg"));
                fileChooser.setAcceptAllFileFilterUsed(false);

                if (patient == null) {
                    JOptionPane.showMessageDialog(null, "Please add the Patient.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    int option = fileChooser.showOpenDialog(mainFrame);
                    if (option == JFileChooser.APPROVE_OPTION) {
                        File file = fileChooser.getSelectedFile();
                        imageFiles.add(file);
                        ImageIcon imageIcon = new ImageIcon(file.getAbsolutePath());
                        Image image = imageIcon.getImage();
                        Image newImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                        imageIcon = new ImageIcon(newImage);
                        imagePreviewPanel.add(new JLabel(imageIcon));
                        label.setText("Attached " + imageFiles.size() + " image(s)");
                        Patient p = (Patient) patient;
                        try {
                            copyFile(file, new File("src/GUI/SkinImages/" + p.getPatientId() + file.getName()));
                            imagePaths.add("src/GUI/SkinImages/" + p.getPatientId() + file.getName());
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }

                    } else {
                        label.setText("Open command canceled");
                    }
                }
            }
        });
        imageAttachmentTop.add(attachImageButton, BorderLayout.EAST);
        imagePreviewPanel.add(label);

        imageAttachmentPanel.add(imageAttachmentTop, BorderLayout.NORTH);

        imageAttachmentPanel.add(imagePreviewPanel, BorderLayout.CENTER);

        consultationDetails.add(MainFrame.addSpace(600, 10));
        consultationDetails.add(imageAttachmentPanel);

        panelBottom.add(doctorDetails);
        panelBottom.add(consultationDetails);


        // Add Consultation Button
        addConsultationButton = new JButton("Add Consultation");
        addConsultationButton.setPreferredSize(new Dimension(220, 40));
        addConsultationButton.setFont(new Font("Arial", Font.PLAIN, 16));
        addConsultationButton.setBorder(BorderFactory.createLineBorder(new Color(164, 92, 255), 2));
        addConsultationButton.setBackground(new Color(0, 0, 0, 0));
        addConsultationButton.setForeground(new Color(164, 92, 255));
        addConsultationButton.addActionListener(this);
        addConsultationButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                addConsultationButton.setOpaque(true);
                addConsultationButton.setBackground(new Color(164, 92, 255));
                addConsultationButton.setForeground(new Color(255, 255, 255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                addConsultationButton.setOpaque(false);
                addConsultationButton.setForeground(new Color(164, 92, 255));
            }
        });

        // Cancel Consultation button
        JPanel addAndCancelButtons = new JPanel(new FlowLayout());
        JLabel cancelDescription = new JLabel("No need add? ");
        cancelDescription.setFont(new Font("Arial", Font.PLAIN, 16));
        cancelDescription.setForeground(new Color(164, 92, 255));

        cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 16));
        cancelButton.setOpaque(false);
        cancelButton.setBorder(null);
        cancelButton.setForeground(new Color(164, 92, 255));
        cancelButton.addActionListener(this);
        cancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                cancelButton.setForeground(new Color(51, 0, 115));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                cancelButton.setForeground(new Color(164, 92, 255));
            }
        });
        addAndCancelButtons.add(cancelDescription);
        addAndCancelButtons.add(cancelButton);

        JPanel addAndCancelPanel = new JPanel(new GridLayout(2, 1));
        JPanel addContainer = new JPanel(new FlowLayout());
        addContainer.setPreferredSize(new Dimension(600, 50));
        addContainer.add(addConsultationButton);
        addAndCancelPanel.add(addContainer);
        addAndCancelPanel.add(addAndCancelButtons);


        mainFrame.add(panelTop, BorderLayout.NORTH);
        mainFrame.add(panelBottom, BorderLayout.CENTER);
        mainFrame.add(addAndCancelPanel, BorderLayout.SOUTH);
        mainFrame.setVisible(true);
    }

    /**
     * Copies a given file to a given destination
     *
     * @param sourceFile the file to be copied
     * @param destFile   the destination of the file
     * @throws IOException if an I/O error occurs
     */
    private static void copyFile(File sourceFile, File destFile) throws IOException {
        if (!sourceFile.exists()) {
            return;
        }
        if (!destFile.exists()) {
            destFile.createNewFile();
        }
        FileChannel source = null;
        FileChannel destination = null;
        source = new FileInputStream(sourceFile).getChannel();
        destination = new FileOutputStream(destFile).getChannel();
        if (destination != null && source != null) {
            destination.transferFrom(source, 0, source.size());
        }
        if (source != null) {
            source.close();
        }
        if (destination != null) {
            destination.close();
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == find) {
            if (patientNameComboBox.getSelectedItem().equals("Select Patient")) {
                JOptionPane.showMessageDialog(null, "Please select a Patient.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                patientId = patientNameComboBox.getSelectedItem().toString().split(" ")[0];
                for (Person tempPatient : WestminsterSkinConsultationManager.getPatientArrayList()) {
                    Patient p = (Patient) tempPatient;
                    if (patientId.equals(p.getPatientId())) {
                        patient = tempPatient;
                    }
                }
                Patient p = (Patient) patient;
                patientNameTextField.setText(p.getName() + " " + p.getSurName());
                patientGenderTextField.setText(p.getGender());
                patientAgeTextField.setText(String.valueOf(p.getAge()));
                patientDOBTextField.setText(p.getStringDateOfBirth());
                patientMobileNumberTextField.setText(p.getMobileNumber());
            }
        }
        if (e.getSource() == checkAvailabilityButton) {
            if (Objects.equals(doctorComboBox.getSelectedItem(), "Select Doctor")) {
                JOptionPane.showMessageDialog(null, "Please select a Doctor.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (sessionDate.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Please select a Date.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String doctorStr = Objects.requireNonNull(doctorComboBox.getSelectedItem()).toString();
                String fName = doctorStr.split(" ")[0];
                String lName = doctorStr.split(" ")[1];
                for (Person tempDoctor : WestminsterSkinConsultationManager.getDoctorArray()) {
                    if (tempDoctor != null) {
                        Doctor d = (Doctor) tempDoctor;
                        if (fName.equals(d.getName()) && lName.equals(d.getSurName())) {
                            doctor = tempDoctor;
                        }
                    }
                }

                String error = "";
                boolean sessionAvailable = false;
                String strDate = sessionDate.getText();
                Doctor doc = (Doctor) doctor;

                Date dateType = WestminsterSkinConsultationManager.strToDate(strDate);
                for (Session tempSession : WestminsterSkinConsultationManager.getSessionsArrayList()) {
                    Doctor d = (Doctor) tempSession.getDoctor();
                    if (d.getMedicalLicenceNumber().equals(doc.getMedicalLicenceNumber()) && tempSession.getDate().equals(dateType)) {
                        if (tempSession.getSessionStatus().equals("Active") || tempSession.getSessionStatus().equals("OnGoing")) {
                            if (tempSession.getAvailableConsultations() != 0) {
                                sessionAvailable = true;
                                session = tempSession;
                                date = dateType;
                                break;
                            } else {
                                error = "Full";
                            }
                        } else {
                            error = "not Available";
                        }
                    }
                }

                if (sessionAvailable) {
                    JOptionPane.showMessageDialog(null, "Session is available.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JDialog dialog = new JDialog(mainFrame, "Error", true);
                    dialog.setSize(400, 150);
                    dialog.setLayout(new FlowLayout());
                    dialog.setResizable(false);
                    dialog.setLocationRelativeTo(AddConsultation.getFrames()[0]);

                    JLabel label1 = new JLabel("Session is " + error + ". But you can choose");
                    label1.setFont(new Font("Arial", Font.PLAIN, 15));
                    label1.setPreferredSize(new Dimension(330, 50));

                    String speciality = doc.getSpecialisation();

                    JComboBox<String> doctorComboBox2 = new JComboBox<>();
                    doctorComboBox2.setPreferredSize(new Dimension(200, 30));
                    for (Session tempSession : WestminsterSkinConsultationManager.getSessionsArrayList()) {
                        Doctor d = (Doctor) tempSession.getDoctor();
                        if (d.getSpecialisation().equals(speciality) && tempSession.getDate().equals(dateType)) {
                            if (tempSession.getSessionStatus().equals("Active") || tempSession.getSessionStatus().equals("OnGoing")) {
                                if (tempSession.getAvailableConsultations() != 0) {
                                    doctorComboBox2.addItem(d.getName() + " " + d.getSurName());
                                }
                            }
                        }
                    }
                    JButton okButton = new JButton("OK");
                    okButton.addActionListener(e1 -> {
                        if (!Objects.equals(doctorComboBox2.getSelectedItem(), null)) {
                            String doctorStr1 = doctorComboBox2.getSelectedItem().toString();
                            String fName1 = doctorStr1.split(" ")[0];
                            String lName1 = doctorStr1.split(" ")[1];
                            for (Person tempDoctor : WestminsterSkinConsultationManager.getDoctorArray()) {
                                if (tempDoctor != null) {
                                    Doctor d = (Doctor) tempDoctor;
                                    if (fName1.equals(d.getName()) && lName1.equals(d.getSurName())) {
                                        doctor = tempDoctor;
                                    }
                                }
                            }
                            Doctor doc1 = (Doctor) doctor;
                            for (Session tempSession : WestminsterSkinConsultationManager.getSessionsArrayList()) {
                                Doctor d = (Doctor) tempSession.getDoctor();
                                if (d.getMedicalLicenceNumber().equals(doc1.getMedicalLicenceNumber()) && tempSession.getDate().equals(dateType)) {
                                    if (tempSession.getSessionStatus().equals("Active") || tempSession.getSessionStatus().equals("OnGoing")) {
                                        if (tempSession.getAvailableConsultations() != 0) {
                                            session = tempSession;
                                            date = dateType;
                                            break;
                                        }
                                    }
                                }
                            }
                            doctorComboBox.setSelectedItem(session.getDoctor().getName() + " " + session.getDoctor().getSurName());
                            dialog.dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "No doctors available on this date.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    });
                    dialog.add(label1);
                    dialog.add(doctorComboBox2);
                    dialog.add(okButton);
                    dialog.setVisible(true);
                }

            }
        }
        if (e.getSource() == addConsultationButton) {
            if (patient == null) {
                JOptionPane.showMessageDialog(null, "Please select a Patient.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (Objects.equals(doctorComboBox.getSelectedItem(), "Select Doctor")) {
                JOptionPane.showMessageDialog(null, "Please select a Doctor.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (Objects.equals(hoursComboBox.getSelectedItem(), "Select")) {
                JOptionPane.showMessageDialog(null, "Please select a Session Duration.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                if (patient == null) {
                    JOptionPane.showMessageDialog(null, "Please add the Patient.", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (session == null) {
                    JOptionPane.showMessageDialog(null, "Check availability.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    note = notesTextArea.getText();
                    duration = Double.parseDouble(Objects.requireNonNull(hoursComboBox.getSelectedItem()).toString());
                    try {
                        String consultationID = generateConsultationID();
                        Consultation consultation = new Consultation(consultationID, session, doctor, patient, duration, note, imagePaths);
                        WestminsterSkinConsultationManager.consultationArrayList.add(consultation);
                        session.addConsultation(consultation);
                        String time = consultation.getStringTime();
                        String tokenNumber = String.valueOf(consultation.getTokenNumber());
                        new ConsultationSummary(consultation, patient, doctor, session, time, tokenNumber);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    ConsultationsPanel.tableReRender(WestminsterSkinConsultationManager.getConsultationsArrayList());
                    mainFrame.dispose();
                }
            }
        }
        if (e.getSource() == cancelButton) {
            mainFrame.dispose();
        }
        if (e.getSource() == fromExistingPatientButton) {
            patientContainer.removeAll();
            patientContainer.add(patientDetails);
            patientContainer.revalidate();
            patientContainer.repaint();

            fromNewPatientButton.setOpaque(true);
            fromNewPatientButton.setBackground(new Color(194, 194, 194, 255));
            fromNewPatientButton.setForeground(new Color(138, 138, 138, 255));
            fromNewPatientButton.setFont(new Font("Arial", Font.PLAIN, 12));

            fromExistingPatientButton.setOpaque(false);
            fromExistingPatientButton.setForeground(new Color(0, 0, 0));
            fromExistingPatientButton.setFont(new Font("Arial", Font.BOLD, 14));
        }
        if (e.getSource() == fromNewPatientButton) {
            patientContainer.removeAll();
            patientContainer.add(newPatientPanel);
            patientContainer.revalidate();
            patientContainer.repaint();

            fromExistingPatientButton.setOpaque(true);
            fromExistingPatientButton.setBackground(new Color(194, 194, 194, 255));
            fromExistingPatientButton.setForeground(new Color(138, 138, 138, 255));
            fromExistingPatientButton.setFont(new Font("Arial", Font.PLAIN, 12));

            fromNewPatientButton.setForeground(new Color(0, 0, 0));
            fromNewPatientButton.setOpaque(false);
            fromNewPatientButton.setBorderPainted(false);
            fromNewPatientButton.setBorder(null);
            fromNewPatientButton.setFocusable(false);
            fromNewPatientButton.setFont(new Font("Arial", Font.BOLD, 14));
        }
        if (e.getSource() == addNewPatientButton) {
            // Check if all fields are filled
            if (newPatientFNameTextField.getText().equals("") || newPatientLNameTextField.getText().equals("") || newPatientDOBTextField.getText().equals("") || newPatientMobileNumberTextField.getText().equals("") || newPatientIDTextField.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // Check if mobile number is valid
                if (newPatientMobileNumberTextField.getText().length() != 10) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid mobile number", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Check if medical licence number is valid
                    if (newPatientIDTextField.getText().length() != 6) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid Patient ID: EX- P00XXX", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        // Add doctor to array list
                        String fName = newPatientFNameTextField.getText();
                        String lName = newPatientLNameTextField.getText();
                        String mobileNumber = newPatientMobileNumberTextField.getText();
                        String patientId = newPatientIDTextField.getText();
                        String gender = Objects.requireNonNull(newPatientGenderComboBox.getSelectedItem()).toString();
                        Date dateOfBirth = WestminsterSkinConsultationManager.strToDate(newPatientDOBTextField.getText());

                        Patient patient1 = new Patient(fName, lName, dateOfBirth, mobileNumber, patientId, gender);
                        WestminsterSkinConsultationManager.patientArrayList.add(patient1);

                        patient = patient1;
                        JOptionPane.showMessageDialog(null, "Patient added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                        PatientsPanel.tableReRender(WestminsterSkinConsultationManager.getPatientArrayList());
                    }
                }
            }
        }
    }

    public String generateConsultationID() {
        String id = "CONS";
        int count = 0;
        for (Session session : WestminsterSkinConsultationManager.getSessionsArrayList()) {
            for (Consultation consultation : session.getConsultations()) {
                if (consultation != null) {
                    count++;
                }
            }
        }
        if (count < 10) {
            id += "00" + count;
        } else if (count < 100) {
            id += "0" + count;
        } else {
            id += count;
        }
        return id;
    }
}
