package root.templates;

import com.squareup.moshi.Moshi;

import javax.swing.*;
import java.util.Set;

public final class TemplatesJPanel extends JPanel {
    private final TemplateManager templateManager;

    public TemplatesJPanel(Moshi moshi) {
        templateManager = new TemplateManager(moshi);
        setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel titlePanel = new JPanel();
        JLabel credentialsLabel = new JLabel("TEMPLATES");
        titlePanel.add(credentialsLabel);
        add(titlePanel);

        JPanel existingPanel = new JPanel();
        JComboBox<Template> templateDropdown = new JComboBox<>();
        JButton viewAndEditButton = new JButton("View/edit…");
        JButton duplicateButton = new JButton("New from this one…");
        JButton deleteButton = new JButton("Delete");
        templateDropdown.addActionListener(e -> {
            if (e.getActionCommand().contentEquals("comboBoxChanged")) {
                boolean toEnable = !Template.NULL.equals(templateDropdown.getSelectedItem());
                viewAndEditButton.setEnabled(toEnable);
                duplicateButton.setEnabled(toEnable);
                deleteButton.setEnabled(toEnable);
            }
        });
        viewAndEditButton.addActionListener(e -> {
            if (e.getActionCommand().contentEquals("View/edit…")) {
                new TemplateJDialog(
                        "View/edit template",
                        templateManager,
                        true,
                        ((Template) templateDropdown.getSelectedItem()),
                        null
                ).setVisible(true);
            }
        });
        duplicateButton.addActionListener(e -> {
            if (e.getActionCommand().contentEquals("New from this one…")) {
                new TemplateJDialog(
                        "New template",
                        templateManager,
                        false,
                        ((Template) templateDropdown.getSelectedItem()),
                        () -> updateTemplateDropdown(
                                templateDropdown, viewAndEditButton, duplicateButton, deleteButton
                        )
                ).setVisible(true);
            }
        });
        updateTemplateDropdown(templateDropdown, viewAndEditButton, duplicateButton, deleteButton);
        templateDropdown.setSelectedItem(Template.NULL);
        deleteButton.addActionListener(e -> {
            if (e.getActionCommand().contentEquals("Delete")) {
                //noinspection ConstantConditions
                templateManager.deleteTemplate(((Template) templateDropdown.getSelectedItem()).name);
                updateTemplateDropdown(templateDropdown, viewAndEditButton, duplicateButton, deleteButton);
            }
        });
        existingPanel.add(templateDropdown);
        existingPanel.add(viewAndEditButton);
        existingPanel.add(duplicateButton);
        existingPanel.add(deleteButton);
        add(existingPanel);

        JPanel newPanel = new JPanel();
        JButton newButton = new JButton("New…");
        newButton.addActionListener(e -> {
                    if (e.getActionCommand().contentEquals("New…")) {
                        new TemplateJDialog(
                                "New template",
                                templateManager,
                                () -> updateTemplateDropdown(
                                        templateDropdown, viewAndEditButton, duplicateButton, deleteButton
                                )
                        ).setVisible(true);
                    }
                }
        );
        newPanel.add(newButton);
        add(newPanel);
    }

    private void updateTemplateDropdown(
            JComboBox<Template> target,
            JButton viewAndEditButton,
            JButton duplicateButton,
            JButton deleteButton
    ) {
        Set<Template> templateSet = templateManager.readTemplates();
        if (!templateSet.contains(((Template) target.getSelectedItem()))) {
            target.setSelectedItem(Template.NULL);
        }
        Template[] templateArray = new Template[templateSet.size()];
        int index = 0;
        for (Template eachTemplate : templateSet) {
            templateArray[index++] = eachTemplate;
        }
        target.setModel(new DefaultComboBoxModel<>(templateArray));
        boolean toEnable = !Template.NULL.equals(target.getSelectedItem());
        viewAndEditButton.setEnabled(toEnable);
        duplicateButton.setEnabled(toEnable);
        deleteButton.setEnabled(toEnable);
    }
}
