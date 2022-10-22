package root.templates;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;

public final class TemplateJDialog extends JDialog {
    private final TemplateManager templateManager;

    public TemplateJDialog(String title, TemplateManager templateManager, Runnable onSave) {
        this(title, templateManager, false, null, onSave);
    }

    public TemplateJDialog(
            String title,
            TemplateManager templateManager,
            boolean prefillTemplateNameIfPossible,
            Template referenceTemplate,
            Runnable onSave
    ) {
        super(((Frame) null), true);
        this.templateManager = templateManager;

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        JPanel namePanel = new JPanel();
        JLabel nameLabel = new JLabel("Name: ");
        JTextField nameField = new JTextField(15);
        nameField.setHorizontalAlignment(JPasswordField.CENTER);
        namePanel.add(nameLabel);
        namePanel.add(nameField);
        add(namePanel);

        JPanel merchantPanel = new JPanel();
        JLabel merchantLabel = new JLabel("Merchant: ");
        JTextField merchantField = new JTextField(15);
        merchantField.setHorizontalAlignment(JPasswordField.CENTER);
        if (referenceTemplate != null) {
            merchantField.setText(referenceTemplate.merchant);
        }
        merchantPanel.add(merchantLabel);
        merchantPanel.add(merchantField);
        add(merchantPanel);

        JPanel currencyPanel = new JPanel();
        JLabel currencyLabel = new JLabel("Currency: ");
        JTextField currencyField = new JTextField(3);
        currencyField.setDocument(new PlainDocument() {
            @Override
            public void insertString(int offset, String str, AttributeSet a) throws BadLocationException {
                if (str == null) return;

                if ((getLength() + str.length()) <= 3) {
                    super.insertString(offset, str, a);
                }
            }
        });
        if (referenceTemplate != null) {
            currencyField.setText(referenceTemplate.currency);
        }
        currencyPanel.add(currencyLabel);
        currencyPanel.add(currencyField);
        add(currencyPanel);

        JPanel checkBoxPanel = new JPanel();
        JCheckBox reimbursableCheckBox = new JCheckBox("Reimbursable? ", true);
        JCheckBox billableCheckBox = new JCheckBox("Billable? ", false);
        if (referenceTemplate != null) {
            reimbursableCheckBox.setSelected(referenceTemplate.isReimbursable);
            billableCheckBox.setSelected(referenceTemplate.isBillable);
        }
        checkBoxPanel.add(reimbursableCheckBox);
        checkBoxPanel.add(billableCheckBox);
        add(checkBoxPanel);

        JPanel categoryPanel = new JPanel();
        JLabel categoryLabel = new JLabel("Category: ");
        JTextField categoryField = new JTextField(15);
        merchantField.setHorizontalAlignment(JPasswordField.CENTER);
        if (referenceTemplate != null) {
            categoryField.setText(referenceTemplate.category);
        }
        categoryPanel.add(categoryLabel);
        categoryPanel.add(categoryField);
        add(categoryPanel);

        JPanel descriptionPrefixPanel = new JPanel();
        JLabel descriptionPrefixLabel = new JLabel("Description prefix: ");
        JTextField descriptionPrefixField = new JTextField(15);
        merchantField.setHorizontalAlignment(JPasswordField.CENTER);
        if (referenceTemplate != null) {
            descriptionPrefixField.setText(referenceTemplate.category);
        }
        descriptionPrefixPanel.add(descriptionPrefixLabel);
        descriptionPrefixPanel.add(descriptionPrefixField);
        add(descriptionPrefixPanel);

        JPanel savePanel = new JPanel();
        JButton saveButton = new JButton("Save");
        if (nameField.getText().isEmpty()) {
            saveButton.setEnabled(false);
        }
        nameField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateSaveButtonEnabled();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateSaveButtonEnabled();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateSaveButtonEnabled();
            }

            private void updateSaveButtonEnabled() {
                saveButton.setEnabled(!nameField.getText().isBlank());
            }
        });
        saveButton.addActionListener(e -> {
            if (e.getActionCommand().contentEquals("Save")) {
                templateManager.addOrUpdateTemplate(new Template(
                        nameField.getText(),
                        merchantField.getText(),
                        currencyField.getText(),
                        reimbursableCheckBox.isSelected(),
                        billableCheckBox.isSelected(),
                        categoryField.getText(),
                        descriptionPrefixField.getText()
                ));
                if (onSave != null) {
                    onSave.run();
                }
                dispose();
            }
        });
        savePanel.add(saveButton);
        add(savePanel);

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setTitle(title);
        pack();
        setLocationRelativeTo(null);
        getRootPane().requestFocus();
    }
}
