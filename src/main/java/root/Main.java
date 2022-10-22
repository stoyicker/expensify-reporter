package root;

import com.squareup.moshi.Moshi;
import root.credentials.CredentialManager;
import root.credentials.CredentialPersistenceRunnable;
import root.credentials.Credentials;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class Main {
    public static void main(String[] args) {
        STORAGE_ROOT.toFile().mkdirs();
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

        JTextField employeeEmailField = new JTextField();
        partnerUserSecretField.setEchoChar('*');
        JLabel employeeEmailLabel = new JLabel("Employee e-mail: ");
        jPanel.add(employeeEmailLabel);
        jPanel.add(employeeEmailField);

        Moshi moshi = new Moshi.Builder().build();
        CredentialManager credentialManager = new CredentialManager(moshi);
        Credentials credentials = credentialManager.readCredentials();
        if (credentials != null) {
            partnerUserIDField.setText(credentials.partnerUserID);
            partnerUserSecretField.setText(credentials.partnerUserSecret);
            employeeEmailField.setText(credentials.employeeEmail);
        }

        new Thread(new CredentialPersistenceRunnable(
                credentialManager, partnerUserIDField, partnerUserSecretField, employeeEmailField
        )).start();

        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jPanel.requestFocus();
        jFrame.setVisible(true);
    }

    public static final Path STORAGE_ROOT =
            Paths.get(System.getProperty("user.dir"), "expensifyreporter", "storage");
}
