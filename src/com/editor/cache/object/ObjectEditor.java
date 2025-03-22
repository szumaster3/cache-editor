package com.editor.cache.object;

import com.alex.defs.objects.ObjectDefinitions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class ObjectEditor extends JFrame {
    private ObjectDefinitions defs;
    private ObjectSelection os;

    private JTextField objectName;
    private JTextField sizeX, sizeY;
    private JTextField objectAnimation;
    private JTextField modelIds;
    private JTextField originalColorsField;
    private JTextField objectOptions;
    private JCheckBox projectileClipped;
    private JTextField clipType;
    private JButton saveButton, cancelButton;

    public ObjectEditor(ObjectSelection os, ObjectDefinitions defs) {
        this.defs = defs;
        this.os = os;
        initComponents();
        load();
        setTitle("Object Editor");
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(1);
        setVisible(true);
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Name"), gbc);
        objectName = new JTextField(20);
        gbc.gridx = 1;
        panel.add(objectName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Size X"), gbc);
        sizeX = new JTextField(20);
        gbc.gridx = 1;
        panel.add(sizeX, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Size Y"), gbc);
        sizeY = new JTextField(20);
        gbc.gridx = 1;
        panel.add(sizeY, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Model IDs"), gbc);
        modelIds = new JTextField(20);
        gbc.gridx = 1;
        panel.add(modelIds, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Model Colors"), gbc);
        originalColorsField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(originalColorsField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Options"), gbc);
        objectOptions = new JTextField(20);
        gbc.gridx = 1;
        panel.add(objectOptions, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(new JLabel("Projectile Clipped"), gbc);
        projectileClipped = new JCheckBox();
        gbc.gridx = 1;
        panel.add(projectileClipped, gbc);

        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(new JLabel("Clip Type"), gbc);
        clipType = new JTextField(20);
        gbc.gridx = 1;
        panel.add(clipType, gbc);

        JPanel buttonPanel = new JPanel();
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });
        cancelButton.addActionListener(e -> dispose());

        this.setLayout(new BorderLayout());
        this.add(panel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);

        pack();
    }

    private void load() {
        objectName.setText(this.defs.getName());
        sizeX.setText(String.valueOf(this.defs.getSizeX()));
        sizeY.setText(String.valueOf(this.defs.getSizeY()));
        clipType.setText(String.valueOf(this.defs.getClipType()));

        if (this.defs.models != null) {
            StringBuilder modelIds = new StringBuilder();
            for (int i = 0; i < this.defs.models.length; i++) {
                modelIds.append(this.defs.models[i]);
                modelIds.append(";");
            }
            this.modelIds.setText(modelIds.toString());
        }

        if (this.defs.originalModelColors != null && this.defs.modifiedModelColors != null) {
            StringBuilder colorsStr = new StringBuilder();
            for (int i = 0; i < defs.originalModelColors.length; i++) {
                colorsStr.append(defs.originalModelColors[i])
                        .append("=")
                        .append(this.defs.modifiedModelColors[i]);

                colorsStr.append(";");
            }
            this.originalColorsField.setText(colorsStr.toString());
        }

        objectOptions.setText(arrayToString(defs.options) + ";");
        projectileClipped.setSelected(defs.isProjectileCliped());
    }

    private void save() {
        try {
            String name = this.objectName.getText().trim();
            this.defs.setName(name);

            if (!this.modelIds.getText().trim().isEmpty()) {
                String[] modelIds = this.modelIds.getText().split(";");
                int[] models = new int[modelIds.length];
                for (int i = 0; i < modelIds.length; i++) {
                    models[i] = Integer.parseInt(modelIds[i].trim());
                }
                this.defs.setModels(models);
            }

            this.defs.sizeX = Integer.parseInt(this.sizeX.getText().trim());
            this.defs.sizeY = Integer.parseInt(this.sizeY.getText().trim());

            this.defs.clipType = Integer.parseInt(this.clipType.getText().trim());

            if (!this.modelIds.getText().trim().isEmpty()) {
                String[] modelIds = this.modelIds.getText().split(";");
                int[] models = new int[modelIds.length];
                for (int i = 0; i < modelIds.length; i++) {
                    models[i] = Integer.parseInt(modelIds[i].trim());
                }
                this.defs.models = models;
            }

            if (!this.originalColorsField.getText().trim().isEmpty()) {
                String[] colorPairs = this.originalColorsField.getText().split(";");
                short[] originalColors = new short[colorPairs.length];
                short[] modifiedColors = new short[colorPairs.length];
                for (int i = 0; i < colorPairs.length; i++) {
                    String[] pair = colorPairs[i].split("=");
                    originalColors[i] = Short.parseShort(pair[0].trim());
                    modifiedColors[i] = Short.parseShort(pair[1].trim());
                }
                this.defs.originalModelColors = originalColors;
                this.defs.modifiedModelColors = modifiedColors;
            }

            // Set options
            if (!this.objectOptions.getText().trim().isEmpty()) {
                String[] options = this.objectOptions.getText().split(";");
                this.defs.options = options;
            }

            // Set projectile clipped
            this.defs.projectileCliped = this.projectileClipped.isSelected();

            // Write to store
            this.defs.write(ObjectSelection.STORE);

            // Update object definitions
            this.os.updateObjectDefs(this.defs);

            JOptionPane.showMessageDialog(this, "Object saved.");
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving object: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String arrayToString(Object[] array) {
        if (array == null || array.length == 0) return "";
        StringBuilder sb = new StringBuilder();
        for (Object obj : array) {
            sb.append(obj == null ? "null" : obj.toString()).append(";");
        }
        return sb.toString().trim();
    }

    private String arrayToString(short[] array) {
        if (array == null || array.length == 0) return "";
        StringBuilder sb = new StringBuilder();
        for (short num : array) {
            sb.append(num).append(";");
        }
        return sb.toString().trim();
    }

    private String arrayToString(int[] array) {
        if (array == null || array.length == 0) return "";
        StringBuilder sb = new StringBuilder();
        for (int num : array) {
            sb.append(num).append(";");
        }
        return sb.toString().trim();
    }

    private String arrayToString(int[][] array) {
        if (array == null) return "";
        StringBuilder sb = new StringBuilder();
        for (int[] subArray : array) {
            for (int num : subArray) {
                sb.append(num).append(";");
            }
            sb.append(";");
        }
        return sb.toString().trim();
    }
}
