package GUI.Main_Frames.SubFrames;

import GUI.MainFrame;
import GUI.Main_Frames.ConsultationsPanel;
import GUI.GUIModels.DatePicker;
import Classes.*;
import Classes.SubModels.EncryptAndDecrypt;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

import static GUI.MainFrame.scaleImage;

public class EditConsultation extends JFrame implements ActionListener {
    private final Consultation consultation;
    private Person patient;
    private Person doctor;
    private Session session;
    private Date date;
    
    // Main Frame
    private final JFrame mainFrame;

    // Bottom Panel
    JLabel patientNameLabel, selectPatientLabel, patientGenderLabel, patientAgeLabel, patientDOBLabel, patientMobileNumberLabel, doctorLabel, dateLabel, hoursLabel, availableBtnLabel;
    JLabel[] jLabelsStyleArray = new JLabel[10];
    // ContainerPanels
    JPanel[] containerPanelArray = new JPanel[10];

    // TextFields
    JTextField patientNameTextField, patientGenderTextField, patientAgeTextField, patientDOBTextField, patientMobileNumberTextField;
    JButton checkAvailabilityButton;
    JComboBox patientNameComboBox, doctorComboBox, hoursComboBox;
    final JTextField sessionDate = new JTextField();
    JTextArea notesTextArea;
    JButton attachImageButton;
    JPanel imageAttachmentPanel;
    ArrayList<File> imageFiles = new ArrayList<>();
    ArrayList<String> finalImagePaths = new ArrayList<>();
    boolean isImagesChanged = false;
    JPanel imagePreviewPanel;
    JPanel patientDetails, doctorDetails, consultationDetails;
    JPanel patientDetailsTop, patientDetailsBottom;
    JPanel doctorDetailsTop, doctorDetailsBottom;
    JButton find, updateConsultationButton, cancelButton;

    public EditConsultation(Consultation consultation) {
        this.consultation = consultation;
        this.patient = consultation.getPatient();
        this.doctor = consultation.getDoctor();
        this.session = consultation.getSession();

        finalImagePaths.addAll(consultation.getImagesPaths());

        // Main Frame
        mainFrame = new JFrame("Add Consultation");
        mainFrame.setSize(700, 900);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(MainFrame.getFrames()[0]);
        mainFrame.setUndecorated(true);
        mainFrame.setShape(new RoundRectangle2D.Double(0, 0, 700, 900, 50, 50));

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
        // Bottom Panel
        JPanel panelBottom = new JPanel();
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
            patientNames[0] = consultation.getPatient().getName() + " " + consultation.getPatient().getSurName();

            int i = 1;
            while (i < WestminsterSkinConsultationManager.getPatientArrayList().size()) {
                if (patientNames[i] != consultation.getPatient().getName() + " " + consultation.getPatient().getSurName()) {
                    Patient patient = (Patient) WestminsterSkinConsultationManager.getPatientArrayList().get(i);
                    patientNames[i] = patient.getPatientId() + " " + patient.getName() + " " + patient.getSurName();
                    i++;
                }
            }
        }
        patientNameComboBox = new JComboBox(patientNames);
        patientNameComboBox.setPreferredSize(new Dimension(130, 40));
        JPanel patientNameComboBoxPanel = new JPanel(new FlowLayout());
        find = new JButton("Update");
        find.addActionListener(this);
        find.setFont(new Font("Arial", Font.PLAIN, 12));
        find.setPreferredSize(new Dimension(70, 30));
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


        // Top Panel
        JPanel patientContainer = new JPanel();
        patientContainer.setLayout(new FlowLayout());
        patientContainer.setPreferredSize(new Dimension(700, 230));
        patientContainer.setOpaque(false);
        patientContainer.add(patientDetails);

        // Add Patient Details to text fields
        Patient patient = (Patient) consultation.getPatient();

        patientNameTextField.setText(consultation.getPatient().getName() + " " + consultation.getPatient().getSurName());
        patientGenderTextField.setText(patient.getGender());
        patientAgeTextField.setText(String.valueOf(patient.getAge()));
        patientDOBTextField.setText(patient.getStringDateOfBirth());
        patientMobileNumberTextField.setText(patient.getMobileNumber());

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
        doctorNames[0] = consultation.getDoctor().getName() + " " + consultation.getDoctor().getSurName();
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
        sessionDate.setText(consultation.getStringDate());

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

        String consHours = String.valueOf(consultation.getHours());
        String[] hours = {consHours, "0.25", "0.50", "1", "2"};
        hoursComboBox = new JComboBox(hours);
        hoursComboBox.setPreferredSize(new Dimension(200, 40));
        hoursComboBox.setFont(new Font("Arial", Font.PLAIN, 16));

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
        String decryptedNotes = EncryptAndDecrypt.decryptText(consultation.getNotes(), 5);
        notesTextArea.setText(decryptedNotes);

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

        ArrayList<String> imagePaths = consultation.getImagesPaths();
        for (String imagePath : imagePaths) {
            try {
                EncryptAndDecrypt.decryptImage(imagePath, 5);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            ImageIcon imageIcon = new ImageIcon(imagePath);
            Image image = imageIcon.getImage();
            Image newImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            imageIcon = new ImageIcon(newImage);
            imagePreviewPanel.add(new JLabel(imageIcon));
            label.setText("Loaded " + imagePaths.size() + " image(s)");
        }


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
                if (!isImagesChanged) {
                    imageFiles.clear();
                    imagePaths.clear();
                    finalImagePaths.clear();
                    imagePreviewPanel.removeAll();
                    imagePreviewPanel.revalidate();
                    imagePreviewPanel.repaint();
                    isImagesChanged = true;
                }
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
                        Patient p = patient;
                        try {
                            copyFile(file, new File("src/GUI/SkinImages/" + p.getPatientId() + file.getName()));
                            imagePaths.add("src/GUI/SkinImages/" + p.getPatientId() + file.getName());
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
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
        updateConsultationButton = new JButton("Update Consultation");
        updateConsultationButton.setPreferredSize(new Dimension(220, 40));
        updateConsultationButton.setFont(new Font("Arial", Font.PLAIN, 16));
        updateConsultationButton.setBorder(BorderFactory.createLineBorder(new Color(164, 92, 255), 2));
        updateConsultationButton.setBackground(new Color(0, 0, 0, 0));
        updateConsultationButton.setForeground(new Color(164, 92, 255));
        updateConsultationButton.addActionListener(this);
        updateConsultationButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                updateConsultationButton.setOpaque(true);
                updateConsultationButton.setBackground(new Color(164, 92, 255));
                updateConsultationButton.setForeground(new Color(255, 255, 255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                updateConsultationButton.setOpaque(false);
                updateConsultationButton.setForeground(new Color(164, 92, 255));
            }
        });

        // Cancel Consultation button
        JPanel addAndCancelButtons = new JPanel(new FlowLayout());
        JLabel cancelDescription = new JLabel("No need Change? ");
        cancelDescription.setFont(new Font("Arial", Font.PLAIN, 16));
        cancelDescription.setForeground(new Color(164, 92, 255));

        cancelButton = new JButton("Discard");
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
        addContainer.add(updateConsultationButton);
        addAndCancelPanel.add(addContainer);
        addAndCancelPanel.add(addAndCancelButtons);

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
                // Variables
                String patientId = patientNameComboBox.getSelectedItem().toString().split(" ")[0];
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
                        String doctorStr1 = Objects.requireNonNull(doctorComboBox2.getSelectedItem()).toString();
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
                    });
                    dialog.add(label1);
                    dialog.add(doctorComboBox2);
                    dialog.add(okButton);
                    dialog.setVisible(true);
                }

            }
        }
        if (e.getSource() == updateConsultationButton) {
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
                    String note = notesTextArea.getText();
                    double duration = Double.parseDouble(Objects.requireNonNull(hoursComboBox.getSelectedItem()).toString());
                    consultation.setSession(session);
                    consultation.setDoctor(doctor);
                    consultation.setPatient(patient);
                    consultation.setHours(duration);
                    consultation.setNotes(note);
                    consultation.setImagesPaths(finalImagePaths);

                    String time = consultation.getStringTime();
                    String tokenNumber = String.valueOf(consultation.getTokenNumber());
                    new ConsultationSummary(consultation, patient, doctor, session, time, tokenNumber);
                    ConsultationsPanel.tableReRender(WestminsterSkinConsultationManager.getConsultationsArrayList());
                    mainFrame.dispose();
                }
            }
        }
        if (e.getSource() == cancelButton) {
            ArrayList<String> imagePaths = consultation.getImagesPaths();
            for (String imagePath : imagePaths) {
                try {
                    EncryptAndDecrypt.encryptImage(imagePath, 5);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            mainFrame.dispose();
        }
    }
}
