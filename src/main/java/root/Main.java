package root;

import com.squareup.moshi.Moshi;
import root.credentials.CredentialsJPanel;
import root.templates.TemplatesJPanel;

import javax.swing.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class Main {
    public static void main(String[] args) {
        STORAGE_ROOT.toFile().mkdirs();
        JFrame jFrame = new JFrame();
        Moshi moshi = new Moshi.Builder().build();
        JPanel rootPanel = new JPanel();
        rootPanel.add(new CredentialsJPanel(moshi));
        rootPanel.add(new TemplatesJPanel(moshi));

        jFrame.add(rootPanel);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setTitle("Expense generator");
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.getRootPane().requestFocus();
        jFrame.setVisible(true);
    }

    public static final Path STORAGE_ROOT =
            Paths.get(System.getProperty("user.dir"), "expensifyreporter", "storage");
}
