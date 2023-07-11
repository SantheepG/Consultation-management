package GUI.Main_Frames.SubFrames;

import GUI.MainFrame;
import Classes.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class ConsultationSummary {
    final JFrame frame = new JFrame("Consultation Summary");
    JLabel[] labels = new JLabel[7];
    String[] labelsText = {"Consultation ID", "Doctor Name", "Patient Name", "Date", "Patient's Time", "Token Number", "Cost"};
    JTextField[] textFields = new JTextField[7];
    JPanel[] containers = new JPanel[7];
    JPanel row1, row2, row3;
    JButton doneBtn;

    public ConsultationSummary(Consultation consultation, Person patient, Person doctor, Session session, String time, String tokenNumber) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 480);
        frame.setLocationRelativeTo(MainFrame.getFrames()[0]);
        frame.setLayout(new FlowLayout());

        // Labels
        for (int i = 0; i < labels.length; i++) {
            labels[i] = new JLabel(labelsText[i]);
            labels[i].setFont(new Font("Arial", Font.PLAIN, 16));
            labels[i].setPreferredSize(new Dimension(200, 17));

            textFields[i] = new JTextField();
            textFields[i].setEditable(false);
            textFields[i].setFont(new Font("Arial", Font.PLAIN, 16));
            textFields[i].setPreferredSize(new Dimension(200, 40));

            containers[i] = new JPanel();
            containers[i].setLayout(new FlowLayout());
            containers[i].setPreferredSize(new Dimension(200, 80));
            containers[i].add(labels[i]);
            containers[i].add(textFields[i]);
        }

        // Rows
        row1 = new JPanel();
        row1.setLayout(new FlowLayout());
        row1.setPreferredSize(new Dimension(670, 80));
        row1.add(containers[0]);
        row1.add(containers[1]);
        row1.add(containers[2]);

        row2 = new JPanel();
        row2.setLayout(new FlowLayout());
        row2.setPreferredSize(new Dimension(670, 80));
        row2.add(containers[3]);
        row2.add(containers[4]);
        row2.add(containers[5]);

        row3 = new JPanel();
        row3.setLayout(new FlowLayout());
        row3.setPreferredSize(new Dimension(670, 80));
        row3.add(containers[6]);


        textFields[0].setText(consultation.getConsultationId());
        textFields[1].setText(doctor.getName() + " " + doctor.getSurName());
        textFields[2].setText(patient.getName() + " " + patient.getSurName());
        textFields[3].setText(session.getStringDate());
        textFields[4].setText(time);
        textFields[5].setText(tokenNumber);
        textFields[6].setText("£ " + String.valueOf(consultation.getPrice()));

        JLabel title = new JLabel("Consultation Added Successfully");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.PLAIN, 20));
        title.setPreferredSize(new Dimension(700, 40));

        doneBtn = new JButton("Done");
        doneBtn.setPreferredSize(new Dimension(200, 40));
        doneBtn.setFont(new Font("Arial", Font.PLAIN, 16));
        doneBtn.setBorder(BorderFactory.createLineBorder(new Color(164, 92, 255), 2));
        doneBtn.setBackground(new Color(0, 0, 0, 0));
        doneBtn.setForeground(new Color(164, 92, 255));
        doneBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        doneBtn.addActionListener(e -> {
            frame.dispose();
        });
        doneBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                doneBtn.setOpaque(true);
                doneBtn.setBackground(new Color(164, 92, 255));
                doneBtn.setForeground(new Color(255, 255, 255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                doneBtn.setOpaque(false);
                doneBtn.setForeground(new Color(164, 92, 255));
            }
        });


        frame.add(title);
        frame.add(MainFrame.addSpace(670, 20));
        frame.add(row1);
        frame.add(row2);
        frame.add(row3);
        frame.add(MainFrame.addSpace(670, 20));
        frame.add(doneBtn);
        frame.setVisible(true);
    }
}
