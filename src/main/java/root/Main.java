package root;

import javax.swing.*;
import java.awt.*;

public final class Main {
    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
        JPanel jPanel = new JPanel();
        jPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        jPanel.setLayout(new GridLayout(0, 2));
        jFrame.add(jPanel, BorderLayout.CENTER);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setTitle("Expense generator");

        JPasswordField partnerUserIDField = new JPasswordField();
        partnerUserIDField.setEchoChar('*');
        JLabel partnerUserIDLabel = new JLabel("partnerUserID: ");
        jPanel.add(partnerUserIDLabel);
        jPanel.add(partnerUserIDField);

        JPasswordField partnerUserSecretField = new JPasswordField();
        partnerUserSecretField.setEchoChar('*');
        JLabel partnerUserSecretLabel = new JLabel("partnerUserSecret: ");
        jPanel.add(partnerUserSecretLabel);
        jPanel.add(partnerUserSecretField);

        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }
}
