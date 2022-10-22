package root.credentials;

import com.squareup.moshi.Moshi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public final class CredentialsJPanel extends JPanel {

    public CredentialsJPanel(Moshi moshi) {
        setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel titlePanel = new JPanel();
        JLabel credentialsLabel = new JLabel("CREDENTIALS (?)");
        credentialsLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().browse(new URL("https://www.expensify.com/tools/integrations/").toURI());
                } catch (IOException | URISyntaxException ex) {
                    ex.printStackTrace(System.err);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        titlePanel.add(credentialsLabel);
        add(titlePanel);

        JPanel partnerUserIDPanel = new JPanel();
        JPasswordField partnerUserIDField = new JPasswordField(15);
        partnerUserIDField.setEchoChar('*');
        partnerUserIDField.setHorizontalAlignment(JPasswordField.CENTER);
        JLabel partnerUserIDLabel = new JLabel("partnerUserID: ");
        partnerUserIDPanel.add(partnerUserIDLabel);
        partnerUserIDPanel.add(partnerUserIDField);
        add(partnerUserIDPanel);

        JPanel partnerUserSecretPanel = new JPanel();
        JPasswordField partnerUserSecretField = new JPasswordField(15);
        partnerUserSecretField.setEchoChar('*');
        partnerUserSecretField.setHorizontalAlignment(JPasswordField.CENTER);
        JLabel partnerUserSecretLabel = new JLabel("partnerUserSecret: ");
        partnerUserSecretPanel.add(partnerUserSecretLabel);
        partnerUserSecretPanel.add(partnerUserSecretField);
        add(partnerUserSecretPanel);

        JPanel partnerEmployeeEmailPanel = new JPanel();
        JTextField employeeEmailField = new JTextField(15);
        employeeEmailField.setHorizontalAlignment(JPasswordField.CENTER);
        JLabel employeeEmailLabel = new JLabel("Employee e-mail: ");
        partnerEmployeeEmailPanel.add(employeeEmailLabel);
        partnerEmployeeEmailPanel.add(employeeEmailField);
        add(partnerEmployeeEmailPanel);

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
    }
}
