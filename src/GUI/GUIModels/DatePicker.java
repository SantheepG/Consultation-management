/**
 * A simple calendar component for choosing a date.
 */
package GUI.GUIModels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DatePicker {
    int month = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH);
    int year = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
    JLabel monthYearText = new JLabel("", JLabel.CENTER);
    String day = "";
    JDialog calenderDialog;
    JButton[] button = new JButton[49];
    JButton[] controlButton = new JButton[4];

    public DatePicker(JFrame parent) {
        calenderDialog = new JDialog();
        calenderDialog.setPreferredSize(new Dimension(400, 420));
        calenderDialog.setModal(true);
        String[] header = {"Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat"};
        JPanel p1 = new JPanel(new GridLayout(7, 7));
        p1.setPreferredSize(new Dimension(430, 120));

        for (int x = 0; x < button.length; x++) {
            final int selection = x;
            button[x] = new JButton();
            button[x].setFocusPainted(false);
            button[x].setBackground(Color.white);
            if (x > 6)
                button[x].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        day = button[selection].getActionCommand();
                        calenderDialog.dispose();
                    }
                });
            if (x < 7) {
                button[x].setText(header[x]);
                button[x].setForeground(new Color(164, 92, 255));
                button[x].setFont(new Font("Arial", Font.BOLD, 14));
                button[x].setOpaque(false);
                button[x].setBorder(null);
            }
            p1.add(button[x]);
        }

        // Calendar month switching
        JPanel p2 = new JPanel(new GridLayout(1, 4));
        //Previous Month
        JButton previous = new JButton("< Month");
        previous.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                month--;
                displayDate();
            }
        });

        // Next Month
        JButton next = new JButton("Month >");
        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                month++;
                displayDate();
            }
        });

        // Calendar year switching
        JButton previousYear = new JButton("< Year");
        previousYear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                year--;
                displayDate();
            }
        });
        JButton nextYear = new JButton("Year >");
        nextYear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                year++;
                displayDate();
            }
        });

        controlButton[0] = previousYear;
        controlButton[1] = previous;
        controlButton[2] = next;
        controlButton[3] = nextYear;

        for (int i = 0; i < controlButton.length; i++) {
            controlButton[i].setFocusPainted(false);
            controlButton[i].setBackground(new Color(164, 92, 255));
            controlButton[i].setForeground(Color.white);
            controlButton[i].setFont(new Font("Arial", Font.BOLD, 14));
            controlButton[i].setOpaque(true);
            controlButton[i].setBorder(null);
            controlButton[i].setPreferredSize(new Dimension(100, 50));
        }
        p2.add(previousYear);
        p2.add(previous);

        p2.add(next);
        p2.add(nextYear);

        JPanel panelBottom = new JPanel(new FlowLayout());
        panelBottom.setPreferredSize(new Dimension(430, 50));
        panelBottom.add(p2);

        monthYearText.setPreferredSize(new Dimension(430, 40));
        monthYearText.setFont(new Font("Arial", Font.BOLD, 14));
        monthYearText.setBackground(new Color(164, 92, 255));
        monthYearText.setForeground(Color.white);
        monthYearText.setOpaque(true);

        calenderDialog.add(monthYearText, BorderLayout.NORTH);
        calenderDialog.add(p1, BorderLayout.CENTER);
        calenderDialog.add(panelBottom, BorderLayout.SOUTH);
        calenderDialog.pack();
        calenderDialog.setLocationRelativeTo(parent);
        displayDate();
        calenderDialog.setVisible(true);
    }

    public void displayDate() {
        for (int x = 7; x < button.length; x++) {
            button[x].setText("");
        }
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMMM yyyy");
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(year, month, 1);
        int dayOfWeek = cal.get(java.util.Calendar.DAY_OF_WEEK);
        int daysInMonth = cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);
        for (int x = 6 + dayOfWeek, day = 1; day <= daysInMonth; x++, day++) {
            button[x].setText("" + day);
        }
        monthYearText.setText(sdf.format(cal.getTime()));
    }

    public String setPickedDate() {
        if (day.equals("")) {
            return day;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        Calendar cal = Calendar.getInstance();

        cal.set(year, month, Integer.parseInt(day));
        return sdf.format(cal.getTime());
    }
}
