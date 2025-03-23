package com.editor.cache.object;

import com.alex.defs.objects.ObjectDefinitions;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.*;

public class ObjectEditor extends JFrame {
    private ObjectDefinitions defs;
    private ObjectSelection os;

    private JTextField objectName;
    private JTextField sizeX, sizeY;
    private JTextField objectAnimation;
    private JTextField modelIds;
    private JTextField originalColorsField;
    private JTextField objectOptions;
    private JTextField textureColorsField;
    private JCheckBox projectileClipped;
    private JTextField clipType;
    private JTextField configFileIdField;
    private JTextField configIdField;
    private JButton saveButton, cancelButton, saveToFileButton;

    private JCheckBox clippedCheckBox;

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
        gbc.gridwidth = 2;
        JLabel objectIdLabel = new JLabel("Object ID: " + defs.id, SwingConstants.CENTER);
        panel.add(objectIdLabel, gbc);
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Name"), gbc);
        objectName = new JTextField(20);
        gbc.gridx = 1;
        panel.add(objectName, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Size X"), gbc);
        sizeX = new JTextField(20);
        gbc.gridx = 1;
        panel.add(sizeX, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Size Y"), gbc);
        sizeY = new JTextField(20);
        gbc.gridx = 1;
        panel.add(sizeY, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Model IDs"), gbc);
        modelIds = new JTextField(20);
        gbc.gridx = 1;
        panel.add(modelIds, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Options"), gbc);
        objectOptions = new JTextField(20);
        gbc.gridx = 1;
        panel.add(objectOptions, gbc);

        // Tab 2
        JPanel texturePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcTex = new GridBagConstraints();
        gbcTex.insets = new Insets(5, 5, 5, 5);
        gbcTex.anchor = GridBagConstraints.WEST;

        gbcTex.gridx = 0;
        gbcTex.gridy = 0;
        texturePanel.add(new JLabel("Model Colors"), gbcTex);
        originalColorsField = new JTextField(20);
        gbcTex.gridx = 1;
        texturePanel.add(originalColorsField, gbcTex);

        gbcTex.gridx = 0;
        gbcTex.gridy = 1;
        texturePanel.add(new JLabel("Texture Colors"), gbcTex);
        textureColorsField = new JTextField(20);
        gbcTex.gridx = 1;
        texturePanel.add(textureColorsField, gbcTex);

        gbcTex.gridx = 0;
        gbcTex.gridy = 2;
        texturePanel.add(new JLabel("Config File ID"), gbcTex);
        configFileIdField = new JTextField(20);
        gbcTex.gridx = 1;
        texturePanel.add(configFileIdField, gbcTex);

        gbcTex.gridx = 0;
        gbcTex.gridy = 3;
        texturePanel.add(new JLabel("Config ID"), gbcTex);
        configIdField = new JTextField(20);
        gbcTex.gridx = 1;
        texturePanel.add(configIdField, gbcTex);

        gbcTex.gridx = 0;
        gbcTex.gridy = 4;
        texturePanel.add(new JLabel("Clip Type"), gbcTex);
        clipType = new JTextField(20);
        gbcTex.gridx = 1;
        texturePanel.add(clipType, gbcTex);

        gbcTex.gridx = 0;
        gbcTex.gridy = 5;
        texturePanel.add(new JLabel("Projectile Clipped"), gbcTex);
        projectileClipped = new JCheckBox();
        gbcTex.gridx = 1;
        texturePanel.add(projectileClipped, gbcTex);

        gbcTex.gridx = 0;
        gbcTex.gridy = 6;
        texturePanel.add(new JLabel("Clipped"), gbcTex);
        clippedCheckBox = new JCheckBox();
        gbcTex.gridx = 1;
        texturePanel.add(clippedCheckBox, gbcTex);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Tab 1", panel);
        tabbedPane.addTab("Tab 2", texturePanel);

        this.add(tabbedPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");
        saveToFileButton = new JButton("Save to txt");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(saveToFileButton);

        saveButton.addActionListener(e -> save());
        cancelButton.addActionListener(e -> dispose());
        saveToFileButton.addActionListener(e -> saveToFile());

        this.setLayout(new BorderLayout());
        this.add(tabbedPane, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);

        pack();
    }

    private void load() {
        objectName.setText(this.defs.getName());
        sizeX.setText(String.valueOf(this.defs.getSizeX()));
        sizeY.setText(String.valueOf(this.defs.getSizeY()));

        if (this.defs.models != null) {
            StringBuilder modelIds = new StringBuilder();
            for (int i = 0; i < this.defs.models.length; i++) {
                modelIds.append(this.defs.models[i]);
                modelIds.append(";");
            }
            this.modelIds.setText(modelIds.toString());
        }
        this.originalColorsField.setText(getChangedModelColors());
        this.textureColorsField.setText(getChangedTextureColors());

        configFileIdField.setText(String.valueOf(this.defs.getConfigFileId()));
        configIdField.setText(String.valueOf(this.defs.getConfigId()));

        objectOptions.setText(arrayToString(defs.options));

        clipType.setText(String.valueOf(this.defs.getClipType()));
        projectileClipped.setSelected(this.defs.isProjectileCliped());
        clippedCheckBox.setSelected(this.defs.getClipped());
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

            if (!this.textureColorsField.getText().trim().isEmpty()) {
                String[] texturePairs = this.textureColorsField.getText().split(";");
                short[] originalTextures = new short[texturePairs.length];
                short[] modifiedTextures = new short[texturePairs.length];

                for (int i = 0; i < texturePairs.length; i++) {
                    String[] pair = texturePairs[i].split("=");
                    originalTextures[i] = Short.parseShort(pair[0].trim());
                    modifiedTextures[i] = Short.parseShort(pair[1].trim());
                }
                this.defs.originalTextureColors = originalTextures;
                this.defs.modifiedTextureColors = modifiedTextures;
            }

            this.defs.setConfigFileId(Integer.parseInt(configFileIdField.getText().trim()));
            this.defs.setConfigId(Integer.parseInt(configIdField.getText().trim()));

            String text = this.objectOptions.getText().trim();
            String[] var19 = text.isEmpty() ? new String[0] : text.split(";");

            for (int i = 0; i < this.defs.options.length; ++i) {
                if (i < var19.length) {
                    this.defs.options[i] = var19[i].equals("null") ? null : var19[i];
                } else {
                    this.defs.options[i] = null;
                }
            }

            this.defs.projectileCliped = this.projectileClipped.isSelected();
            this.defs.setClipped(this.clippedCheckBox.isSelected());

            this.defs.write(ObjectSelection.Cache);
            this.os.updateObjectDefs(this.defs);

            JOptionPane.showMessageDialog(this, "Object saved.");
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving object: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveToFile() {
        try {
            String filePath = "data/export/objects/" + defs.id + ".txt";
            Path path = Paths.get(filePath);

            Files.createDirectories(path.getParent());

            StringBuilder content = new StringBuilder();
            content.append("Object ID: ").append(defs.id).append("\n");
            content.append("Name: ").append(objectName.getText()).append("\n");
            content.append("Size X: ").append(sizeX.getText()).append("\n");
            content.append("Size Y: ").append(sizeY.getText()).append("\n");
            content.append("Model IDs: ").append(modelIds.getText()).append("\n");
            content.append("Options: ").append(objectOptions.getText()).append("\n");
            content.append("Model Colors: ").append(originalColorsField.getText()).append("\n");
            content.append("Texture Colors: ").append(textureColorsField.getText()).append("\n");
            content.append("Config File ID: ").append(configFileIdField.getText()).append("\n");
            content.append("Config ID: ").append(configIdField.getText()).append("\n");
            content.append("Clip Type: ").append(clipType.getText()).append("\n");
            content.append("Projectile Clipped: ").append(projectileClipped.isSelected()).append("\n");
            content.append("Clipped: ").append(clippedCheckBox.isSelected()).append("\n");

            Files.write(path, content.toString().getBytes());
            JOptionPane.showMessageDialog(this, "Object data saved to file.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving to file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
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


    public String getChangedModelColors() {
        String text = "";
        if (this.defs.originalModelColors != null) {
            for (int i = 0; i < this.defs.originalModelColors.length; ++i) {
                text = text + this.defs.originalModelColors[i] + "=" + this.defs.modifiedModelColors[i] + ";";
            }
        }

        return text;
    }

    public String getChangedTextureColors() {
        String text = "";
        if (this.defs.originalTextureColors != null) {
            for (int i = 0; i < this.defs.originalTextureColors.length; ++i) {
                text = text + this.defs.originalTextureColors[i] + "=" + this.defs.modifiedTextureColors[i] + ";";
            }
        }

        return text;
    }
}
