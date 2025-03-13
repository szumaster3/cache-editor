package com.editor.object;

import com.alex.loaders.objects.ObjectDefinitions;

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
    private JTextField modelIds;
    private JTextField originalColorsField;
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

        // Model Colors
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Model Colors"), gbc);
        originalColorsField = new JTextField(20);
        gbc.gridx = 1;
        panel.add(originalColorsField, gbc);

        // Options
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(new JLabel("Options"), gbc);
        objectOptions = new JTextField(20);
        gbc.gridx = 1;
        panel.add(objectOptions, gbc);

        // Projectile Clipped Checkbox
        gbc.gridx = 0;
        gbc.gridy = 7;
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
            StringBuilder modelIdsStr = new StringBuilder();
            for (int[] modelGroup : defs.getModelIds()) {
                for (int i = 0; i < modelGroup.length; i++) {
                    modelIdsStr.append(modelGroup[i]);
                    if (i < modelGroup.length - 1) {
                        modelIdsStr.append(";");
                    }
                }
                modelIdsStr.append(";");
            }
            modelIds.setText(modelIdsStr.toString());
        }

        if (defs.getOriginalColors() != null && defs.getModifiedColors() != null) {
            StringBuilder colorsStr = new StringBuilder();
            for (int i = 0; i < defs.getOriginalColors().length; i++) {
                colorsStr.append(defs.getOriginalColors()[i])
                        .append("=")
                        .append(defs.getModifiedColors()[i]);

                if (i < defs.getOriginalColors().length - 1) {
                    colorsStr.append(";");
                }
            }
            originalColorsField.setText(colorsStr.toString());
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
                String[] modelGroups = modelIds.getText().split(";");
                int[][] modelIdArray = new int[modelGroups.length][];

                for (int i = 0; i < modelGroups.length; i++) {
                    String[] modelIdStrs = modelGroups[i].split(";");
                    modelIdArray[i] = new int[modelIdStrs.length];

                    for (int j = 0; j < modelIdStrs.length; j++) {
                        modelIdArray[i][j] = Integer.parseInt(modelIdStrs[j].trim());
                    }
                }

                this.defs.setModelIds(modelIdArray);
            }

            // Save Colors
            if (!originalColorsField.getText().trim().isEmpty()) {
                String[] colorPairs = originalColorsField.getText().split(";");
                short[] originalColors = new short[colorPairs.length];
                short[] modifiedColors = new short[colorPairs.length];

                for (int i = 0; i < colorPairs.length; i++) {
                    String[] pair = colorPairs[i].split("=");
                    originalColors[i] = Short.parseShort(pair[0].trim());
                    modifiedColors[i] = Short.parseShort(pair[1].trim());
                }

                this.defs.setOriginalColors(originalColors);
                this.defs.setModifiedColors(modifiedColors);
            }

            if (!objectOptions.getText().trim().isEmpty()) {
                this.defs.invOptions(objectOptions.getText().split(";"));
            }

            this.defs.projectileCliped = projectileClipped.isSelected();
            this.defs.write(ObjectSelection.STORE);
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
