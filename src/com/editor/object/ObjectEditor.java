package com.editor.object;

import com.alex.loaders.objects.ObjectDefinitions;
import com.editor.npc.NPCSelection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ObjectEditor extends JFrame {
    private ObjectDefinitions defs;
    private ObjectSelection os;

    private JTextField objectName;
    private JTextField sizeX, sizeY;
    private JTextField objectAnimation;
    private JTextField modelIds;  // Wrong
    private JTextField originalColorsField, modifiedColorsField; // Wrong
    private JTextField objectOptions;
    private JCheckBox projectileClipped;
    private JButton saveButton, cancelButton;

    public ObjectEditor(ObjectSelection os, ObjectDefinitions defs) {
        this.defs = defs;
        this.os = os;
        initComponents();
        load();
        setTitle("Object Editor");
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // Object Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Name"), gbc);
        objectName = new JTextField(20);
        gbc.gridx = 1;
        panel.add(objectName, gbc);

        // Size X
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Size X"), gbc);
        sizeX = new JTextField(20);
        gbc.gridx = 1;
        panel.add(sizeX, gbc);

        // Size Y
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Size Y"), gbc);
        sizeY = new JTextField(20);
        gbc.gridx = 1;
        panel.add(sizeY, gbc);

        // Animation
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Animation"), gbc);
        objectAnimation = new JTextField(20);
        gbc.gridx = 1;
        panel.add(objectAnimation, gbc);

        // Model IDs
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Model IDs"), gbc);
        modelIds = new JTextField(20);
        gbc.gridx = 1;
        panel.add(modelIds, gbc);

        // Original Colors
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Original Colors"), gbc);
        originalColorsField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(originalColorsField, gbc);

        // Modified Colors
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(new JLabel("Modified Colors"), gbc);
        modifiedColorsField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(modifiedColorsField, gbc);

        // Options
        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(new JLabel("Options"), gbc);
        objectOptions = new JTextField(20);
        gbc.gridx = 1;
        panel.add(objectOptions, gbc);

        // Projectile Clipped Checkbox
        gbc.gridx = 0;
        gbc.gridy = 8;
        panel.add(new JLabel("Projectile Clipped"), gbc);
        projectileClipped = new JCheckBox();
        gbc.gridx = 1;
        panel.add(projectileClipped, gbc);

        // Save and Cancel buttons
        JPanel buttonPanel = new JPanel();
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        // Button listeners
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });
        cancelButton.addActionListener(e -> dispose());

        // Add panel and buttonPanel to the frame
        this.setLayout(new BorderLayout());
        this.add(panel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);

        pack();
    }

    private void load() {
        objectName.setText(defs.getName());
        sizeX.setText(String.valueOf(defs.getSizeX()));
        sizeY.setText(String.valueOf(defs.getSizeY()));
        objectAnimation.setText(String.valueOf(defs.getObjectAnimation()));

        if (defs.getModelIds() != null) {
            StringBuilder modelIdStr = new StringBuilder();
            for (int[] modelArray : defs.getModelIds()) {
                for (int id : modelArray) {
                    modelIdStr.append(id).append(";");
                }
            }
            modelIds.setText(modelIdStr.toString().trim());
        }

        if (defs.getOriginalColors() != null && defs.getModifiedColors() != null) {
            originalColorsField.setText(arrayToString(defs.getOriginalColors()));
            modifiedColorsField.setText(arrayToString(defs.getModifiedColors()));
        }

        objectOptions.setText(arrayToString(defs.getOpts()));
        projectileClipped.setSelected(defs.isProjectileCliped());
    }

    private void save() {
        try {
            this.defs.setName(objectName.getText().trim());
            this.defs.setSizeX(Integer.parseInt(sizeX.getText().trim()));
            this.defs.setSizeY(Integer.parseInt(sizeY.getText().trim()));
            this.defs.setObjectAnimation(Integer.parseInt(objectAnimation.getText().trim()));

            if (!modelIds.getText().trim().isEmpty()) {
                String[] modelIdStrs = modelIds.getText().split(";");
                int[][] modelIdArray = new int[1][modelIdStrs.length];
                for (int i = 0; i < modelIdStrs.length; i++) {
                    modelIdArray[0][i] = Integer.parseInt(modelIdStrs[i]);
                }
                this.defs.setModelIds(modelIdArray);
            }

            if (!originalColorsField.getText().trim().isEmpty() && !modifiedColorsField.getText().trim().isEmpty()) {
                String[] originalStrs = originalColorsField.getText().split(";");
                String[] modifiedStrs = modifiedColorsField.getText().split(";");
                short[] originalColors = new short[originalStrs.length];
                short[] modifiedColors = new short[modifiedStrs.length];

                for (int i = 0; i < originalStrs.length; i++) {
                    originalColors[i] = Short.parseShort(originalStrs[i]);
                    modifiedColors[i] = Short.parseShort(modifiedStrs[i]);
                }

                this.defs.setOriginalColors(originalColors);
                this.defs.setModifiedColors(modifiedColors);
            }

            if (!objectOptions.getText().trim().isEmpty()) {
                this.defs.invOptions(objectOptions.getText().split(";"));
            }

            this.defs.projectileCliped = projectileClipped.isSelected();
            this.defs.write(NPCSelection.STORE);
            this.os.updateObjectDefs(this.defs);

            JOptionPane.showMessageDialog(this, "Object saved.");
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving object: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String arrayToString(Object[] array) {
        if (array == null) return "";
        StringBuilder sb = new StringBuilder();
        for (Object obj : array) {
            sb.append(obj == null ? "null" : obj.toString()).append(";");
        }
        return sb.toString().trim();
    }

    private String arrayToString(short[] array) {
        if (array == null) return "";
        StringBuilder sb = new StringBuilder();
        for (short num : array) {
            sb.append(num).append(";");
        }
        return sb.toString().trim();
    }
}
