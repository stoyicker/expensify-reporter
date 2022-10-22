package root.credentials;

import javax.swing.*;

public final class CredentialPersistenceRunnable implements Runnable {
    private final CredentialManager credentialManager;
    private final JPasswordField partnerUserIDField;
    private final JPasswordField partnerUserIDSecretField;
    private final JTextField employeeEmailField;

    public CredentialPersistenceRunnable(
            CredentialManager credentialManager,
            JPasswordField partnerUserIDField,
            JPasswordField partnerUserIDSecretField,
            JTextField employeeEmailField
    ) {
        this.credentialManager = credentialManager;
        this.partnerUserIDField = partnerUserIDField;
        this.partnerUserIDSecretField = partnerUserIDSecretField;
        this.employeeEmailField = employeeEmailField;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1_000);
                credentialManager.writeCredentials(
                        new Credentials(
                                String.valueOf(partnerUserIDField.getPassword()),
                                String.valueOf(partnerUserIDSecretField.getPassword()),
                                employeeEmailField.getText())
                );
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
