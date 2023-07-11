package GUI.GUIModels;

import GUI.MainFrame;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.*;

public class DeletingDone {
    public DeletingDone() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                         UnsupportedLookAndFeelException ex) {
                }
                new BackgroundWorker().execute();
            }

        });
    }

    public static class BackgroundWorker extends SwingWorker<Void, Void> {
        private JProgressBar pb;
        private JDialog dialog;

        public BackgroundWorker() {
            addPropertyChangeListener(new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    if ("progress".equalsIgnoreCase(evt.getPropertyName())) {
                        if (dialog == null) {
                            dialog = new JDialog();
                            dialog.setPreferredSize(new java.awt.Dimension(400, 100));
                            dialog.setTitle("Deleting");
                            dialog.setLayout(new GridBagLayout());
                            dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                            GridBagConstraints gbc = new GridBagConstraints();
                            gbc.insets = new Insets(2, 2, 2, 2);
                            gbc.weightx = 1;
                            gbc.gridy = 0;
                            dialog.add(new JLabel("Consultation and related image files deleted successfully"), gbc);
                            gbc.gridy = 1;
                            dialog.pack();
                            dialog.setLocationRelativeTo(MainFrame.getFrames()[0]);
                            dialog.setVisible(true);
                        }
                    }
                }

            });
        }

        @Override
        protected void done() {
            if (dialog != null) {
                dialog.dispose();
            }
        }

        @Override
        protected Void doInBackground() throws Exception {
            for (int index = 0; index < 100; index++) {
                setProgress(index);
                Thread.sleep(15);
            }
            return null;
        }
    }
}
