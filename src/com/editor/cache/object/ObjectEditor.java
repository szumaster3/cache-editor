package com.editor.cache.object;

import com.alex.defs.objects.ObjectDefinitions;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ObjectEditor extends JFrame {
    private ObjectDefinitions definitions;
    private ObjectSelection objectSelection;
    private JTextField name, options, models, childrenIds, modelColors, textureColors;
    private JCheckBox projectileClipped, notClipped;
    private JCheckBox isWalkable, isSolid, isInteractive, castsShadow, isProjectile;
    private JTextField configFileId;
    private JTextField configId;
    private JTextField clipType;
    private JSpinner sizeX, sizeY, scaleX, scaleY, scaleZ;
    private JSpinner rotationX, rotationY, rotationZ;
    private JSpinner animationId;
    private JSpinner heightOffsetX, heightOffsetY;

    public ObjectEditor(ObjectSelection objectSelection, ObjectDefinitions definitions) {
        this.definitions = definitions;
        this.objectSelection = objectSelection;

        setLocationRelativeTo(null);
        setTitle("Object Editor");
        setDefaultCloseOperation(2);

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        setContentPane(new JScrollPane(mainPanel));

        // Basic information.
        JPanel basicInfoPanel = new JPanel();
        basicInfoPanel.setLayout(new BoxLayout(basicInfoPanel, BoxLayout.Y_AXIS));
        basicInfoPanel.setBorder(BorderFactory.createTitledBorder("Basic information"));
        basicInfoPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        JLabel idLabel = new JLabel("Object ID:");
        idLabel.setPreferredSize(new Dimension(120, idLabel.getPreferredSize().height));
        JLabel idValueLabel = new JLabel(String.valueOf(definitions.id));
        idValueLabel.setFont(idValueLabel.getFont().deriveFont(Font.BOLD));
        idPanel.add(idLabel);
        idPanel.add(idValueLabel);
        idPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        basicInfoPanel.add(idPanel);

        basicInfoPanel.add(Box.createVerticalStrut(8));
        basicInfoPanel.add(createLabel("Name:", name = new JTextField(definitions.getName())));
        basicInfoPanel.add(Box.createVerticalStrut(8));

        options = new JTextField(optionsArrayToString(definitions.options));
        basicInfoPanel.add(createLabel("Options:", options));
        basicInfoPanel.add(Box.createVerticalStrut(8));

        basicInfoPanel.add(createLabel("Config file ID:", configFileId = new JTextField(String.valueOf(definitions.getConfigFileId()))));
        basicInfoPanel.add(createLabel("Config ID:", configId = new JTextField(String.valueOf(definitions.getConfigId()))));
        basicInfoPanel.add(createLabel("Clip type:", clipType = new JTextField(String.valueOf(definitions.getClipType()))));

        mainPanel.add(basicInfoPanel);
        mainPanel.add(Box.createVerticalStrut(15));

        // Basic properties.
        JPanel basicPropsPanel = new JPanel();
        basicPropsPanel.setLayout(new BoxLayout(basicPropsPanel, BoxLayout.Y_AXIS));
        basicPropsPanel.setBorder(BorderFactory.createTitledBorder("Properties"));
        basicPropsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel clippingPanel = new JPanel();
        clippingPanel.setLayout(new BoxLayout(clippingPanel, BoxLayout.X_AXIS));
        projectileClipped = new JCheckBox("Projectile Clipped", definitions.isProjectileClipped());
        notClipped = new JCheckBox("Not Clipped", definitions.getClipped());
        clippingPanel.add(projectileClipped);
        clippingPanel.add(Box.createHorizontalStrut(15));
        clippingPanel.add(notClipped);
        clippingPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        basicPropsPanel.add(wrap("Clipping:", clippingPanel));
        basicPropsPanel.add(Box.createVerticalStrut(8));

        JPanel sizePanel = new JPanel(new GridBagLayout());
        sizePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 5, 0, 5);
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;

        addLabel(sizePanel, gbc, 0, "Size X:", definitions.getSizeX());
        addLabel(sizePanel, gbc, 2, "Size Y:", definitions.getSizeY());

        basicPropsPanel.add(wrap("Size:", sizePanel));

        mainPanel.add(basicPropsPanel);
        mainPanel.add(Box.createVerticalStrut(15));

        // Models and Colors.
        JPanel modelsColorsPanel = new JPanel();
        modelsColorsPanel.setLayout(new BoxLayout(modelsColorsPanel, BoxLayout.Y_AXIS));
        modelsColorsPanel.setBorder(BorderFactory.createTitledBorder("Models and Colors"));
        modelsColorsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        models = new JTextField(intArrayToSemicolonString(definitions.models));
        modelsColorsPanel.add(createLabel("Models:", models));

        childrenIds = new JTextField(intArrayToSemicolonString(definitions.childrenIds));
        modelsColorsPanel.add(createLabel("Child IDs:", childrenIds));

        modelColors = new JTextField(pairColorsToString(definitions.originalModelColors, definitions.modifiedModelColors));
        modelsColorsPanel.add(createLabel("Model Colors:", modelColors));

        textureColors = new JTextField(pairColorsToString(definitions.originalTextureColors, definitions.modifiedTextureColors));
        modelsColorsPanel.add(createLabel("Texture Colors:", textureColors));

        mainPanel.add(modelsColorsPanel);
        mainPanel.add(Box.createVerticalStrut(15));

        // Transformations.
        JPanel transformPanel = new JPanel();
        transformPanel.setLayout(new BoxLayout(transformPanel, BoxLayout.Y_AXIS));
        transformPanel.setBorder(BorderFactory.createTitledBorder("Transformations"));
        transformPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Scale panel.
        JPanel scalePanel = new JPanel(new GridBagLayout());
        scalePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 5, 0, 5);
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;

        addLabel(scalePanel, gbc, 0, "Scale X:", definitions.anInt3841);
        addLabel(scalePanel, gbc, 2, "Scale Y:", definitions.anInt3917);
        addLabel(scalePanel, gbc, 4, "Scale Z:", definitions.anInt3902);
        transformPanel.add(wrap("Scale:", scalePanel));

        // Rotation panel.
        JPanel rotationPanel = new JPanel(new GridBagLayout());
        rotationPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 3, 0, 3);
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;

        addLabel(rotationPanel, gbc, 0, "Rotation X:", definitions.anInt3840);
        addLabel(rotationPanel, gbc, 2, "Rotation Y:", definitions.anInt3878);
        addLabel(rotationPanel, gbc, 4, "Rotation Z:", definitions.anInt3876);
        transformPanel.add(wrap("Rotation:", rotationPanel));

        mainPanel.add(transformPanel);
        mainPanel.add(Box.createVerticalStrut(15));

        // Animations.
        animationId = new JSpinner(new SpinnerNumberModel(definitions.anInt3855, -1, 5000, 1));
        limitSize(animationId);
        mainPanel.add(createLabel("Animation ID:", animationId));
        mainPanel.add(Box.createVerticalStrut(15));

        // Flags.
        JPanel flagsPanel = new JPanel();
        flagsPanel.setLayout(new BoxLayout(flagsPanel, BoxLayout.X_AXIS));
        flagsPanel.setBorder(BorderFactory.createTitledBorder("Flags"));
        flagsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        isWalkable = new JCheckBox("Walkable", definitions.isWalkable());
        isSolid = new JCheckBox("Solid", definitions.isSolid());
        isInteractive = new JCheckBox("Interactive", definitions.isInteractive());
        castsShadow = new JCheckBox("Casts Shadow", definitions.castsShadow());
        isProjectile = new JCheckBox("???", definitions.blocksProjectile());

        flagsPanel.add(isWalkable);
        flagsPanel.add(Box.createHorizontalStrut(10));
        flagsPanel.add(isSolid);
        flagsPanel.add(Box.createHorizontalStrut(10));
        flagsPanel.add(isInteractive);
        flagsPanel.add(Box.createHorizontalStrut(10));
        flagsPanel.add(castsShadow);
        flagsPanel.add(Box.createHorizontalStrut(10));
        flagsPanel.add(isProjectile);

        mainPanel.add(flagsPanel);
        mainPanel.add(Box.createVerticalStrut(15));

        // Height offsets.
        JPanel heightOffsetPanel = new JPanel(new GridBagLayout());
        heightOffsetPanel.setBorder(BorderFactory.createTitledBorder("Height offsets"));
        heightOffsetPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 5, 0, 5);
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;

        addLabel(heightOffsetPanel, gbc, 0, "Height offset X:", definitions.anInt3883);
        addLabel(heightOffsetPanel, gbc, 2, "Height offset Y:", definitions.anInt3915);

        mainPanel.add(heightOffsetPanel);
        mainPanel.add(Box.createVerticalStrut(15));

        // ClientScripts clientScriptModel = new ClientScripts(definitions.clientScriptData);
        // JTable clientScriptTable = new JTable(clientScriptModel);
        // clientScriptTable.setFillsViewportHeight(true);

        // JPanel clientScriptPanel = new JPanel(new BorderLayout());
        // clientScriptPanel.setBorder(BorderFactory.createTitledBorder("Client Script Data"));
        // clientScriptPanel.add(new JScrollPane(clientScriptTable), BorderLayout.CENTER);

        // JPanel clientScriptButtons = new JPanel();
        // JButton add = new JButton("Add");
        // add.addActionListener(e -> {
        //     clientScriptModel.getEntries().add(new AbstractMap.SimpleEntry<>(0, ""));
        //     clientScriptModel.fireTableDataChanged();
        // });
        // JButton remove = new JButton("Remove");
        // remove.addActionListener(e -> {
        //     int sel = clientScriptTable.getSelectedRow();
        //     if (sel >= 0) {
        //         clientScriptModel.getEntries().remove(sel);
        //         clientScriptModel.fireTableDataChanged();
        //     }
        // });
        // clientScriptButtons.add(add);
        // clientScriptButtons.add(remove);
        // clientScriptPanel.add(clientScriptButtons, BorderLayout.SOUTH);

        // mainPanel.add(clientScriptPanel);
        // mainPanel.add(Box.createVerticalStrut(15));

        // Buttons.
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton applyButton = new JButton("Add");
        applyButton.addActionListener(
                actionEvent -> save()
        );

        JButton saveButton = new JButton("Dump to TXT");
        saveButton.addActionListener(
                actionEvent -> export()
        );

        buttonsPanel.add(applyButton);
        buttonsPanel.add(saveButton);
        buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(buttonsPanel);
        setPreferredSize(new Dimension(801, 721));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void limitSize(JSpinner spinner) {
        Dimension pref = spinner.getPreferredSize();
        spinner.setPreferredSize(new Dimension(60, pref.height));
        spinner.setMaximumSize(new Dimension(60, pref.height));
    }

    private void addLabel(JPanel panel, GridBagConstraints gbc, int x, String label, int value) {
        JLabel jlabel = new JLabel(label);
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(value, -10000, 10000, 1));
        limitSize(spinner);
        gbc.gridx = x;
        panel.add(jlabel, gbc);
        gbc.gridx = x + 1;
        panel.add(spinner, gbc);

        if ("Size X:".equals(label)) {
            sizeX = spinner;
        } else if ("Size Y:".equals(label)) {
            sizeY = spinner;
        } else if ("Scale X:".equals(label)) {
            scaleX = spinner;
        } else if ("Scale Y:".equals(label)) {
            scaleY = spinner;
        } else if ("Scale Z:".equals(label)) {
            scaleZ = spinner;
        } else if ("Rotation X:".equals(label)) {
            rotationX = spinner;
        } else if ("Rotation Y:".equals(label)) {
            rotationY = spinner;
        } else if ("Rotation Z:".equals(label)) {
            rotationZ = spinner;
        } else if ("Height offset X:".equals(label)) {
            heightOffsetX = spinner;
        } else if ("Height offset Y:".equals(label)) {
            heightOffsetY = spinner;
        }
    }

    private JPanel createLabel(String label, JComponent field) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        JLabel jlabel = new JLabel(label);
        jlabel.setPreferredSize(new Dimension(160, 20));
        panel.add(jlabel, BorderLayout.WEST);
        panel.add(field, BorderLayout.CENTER);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        return panel;
    }

    private int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    private JPanel wrap(String labelText, JPanel panel) {
        JPanel wrapper = new JPanel(new BorderLayout());
        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(150, 20));
        wrapper.add(label, BorderLayout.WEST);
        wrapper.add(panel, BorderLayout.CENTER);
        wrapper.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        wrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
        return wrapper;
    }

    private void export() {
        try {
            String filePath = "data/export/objects/" + definitions.id + ".txt";
            Path path = Paths.get(filePath);

            Files.createDirectories(path.getParent());

            StringBuilder content = new StringBuilder();
            content.append("Object ID: ").append(definitions.id).append("\n");
            content.append("Name: ").append(name.getText()).append("\n");
            content.append("Size X: ").append(sizeX.getValue()).append("\n");
            content.append("Size Y: ").append(sizeY.getValue()).append("\n");
            content.append("Model IDs: ").append(models.getText()).append("\n");

            content.append("Options: ").append(options.getText()).append("\n");

            content.append("Model Colors: ").append(modelColors.getText()).append("\n");
            content.append("Texture Colors: ").append(textureColors.getText()).append("\n");

            content.append("Scale X: ").append(scaleX.getValue()).append("\n");
            content.append("Scale Y: ").append(scaleY.getValue()).append("\n");
            content.append("Scale Z: ").append(scaleZ.getValue()).append("\n");

            content.append("Rotation X: ").append(rotationX.getValue()).append("\n");
            content.append("Rotation Y: ").append(rotationY.getValue()).append("\n");
            content.append("Rotation Z: ").append(rotationZ.getValue()).append("\n");

            content.append("Animation ID: ").append(animationId.getValue()).append("\n");

            content.append("Walkable: ").append(isWalkable.isSelected()).append("\n");
            content.append("Solid: ").append(isSolid.isSelected()).append("\n");
            content.append("Interactive: ").append(isInteractive.isSelected()).append("\n");
            content.append("Casts Shadow: ").append(castsShadow.isSelected()).append("\n");
            content.append("Block Projectile: ").append(isProjectile.isSelected()).append("\n");

            content.append("Height Offset X: ").append(heightOffsetX.getValue()).append("\n");
            content.append("Height Offset Y: ").append(heightOffsetY.getValue()).append("\n");

            content.append("Config File ID: ").append(configFileId.getText()).append("\n");
            content.append("Config ID: ").append(configId.getText()).append("\n");
            content.append("Clip Type: ").append(clipType.getText()).append("\n");

            Files.write(path, content.toString().getBytes());

            JOptionPane.showMessageDialog(this, "Object data saved to file.");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving to file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void save() {
        try {
            definitions.setName(name.getText().trim());
            definitions.setOptions(parseOptionsString(options.getText()));
            definitions.models = parseIntArray(models.getText().trim(), ";");
            definitions.childrenIds = parseIntArray(childrenIds.getText().trim(), ";");
            definitions.originalModelColors = parseOriginalColors(modelColors.getText().trim());
            definitions.modifiedModelColors = parseModifiedColors(modelColors.getText().trim());
            definitions.originalTextureColors = parseOriginalColors(textureColors.getText().trim());
            definitions.modifiedTextureColors = parseModifiedColors(textureColors.getText().trim());
            if (sizeX != null) {
                definitions.setSizeX((Integer) sizeX.getValue());
            }
            if (sizeY != null) {
                definitions.setSizeY((Integer) sizeY.getValue());
            }
            definitions.setBlockProjectile(projectileClipped.isSelected());
            definitions.setClipped(notClipped.isSelected());
            if (scaleX != null) {
                definitions.setScaleX((Integer) scaleX.getValue());
            }
            if (scaleY != null) {
                definitions.setScaleY((Integer) scaleY.getValue());
            }
            if (scaleZ != null) {
                definitions.setScaleZ((Integer) scaleZ.getValue());
            }
            if (rotationX != null) {
                definitions.setRotationX((Integer) rotationX.getValue());
            }
            if (rotationY != null) {
                definitions.setRotationY((Integer) rotationY.getValue());
            }
            if (rotationZ != null) {
                definitions.setRotationZ((Integer) rotationZ.getValue());
            }
            if (animationId != null) {
                definitions.setAnimationId((Integer) animationId.getValue());
            }
            definitions.setWalkable(isWalkable.isSelected());
            definitions.setSolid(isSolid.isSelected());
            definitions.setInteractive(isInteractive.isSelected());
            definitions.setCastsShadow(castsShadow.isSelected());
            definitions.setBlockProjectile(isProjectile.isSelected());
            if (heightOffsetX != null) {
                definitions.setHeightOffsetX((Integer) heightOffsetX.getValue());
            }
            if (heightOffsetY != null) {
                definitions.setHeightOffsetY((Integer) heightOffsetY.getValue());
            }
            try {
                definitions.setConfigFileId(Integer.parseInt(configFileId.getText().trim()));
            } catch (NumberFormatException e) {
                definitions.setConfigFileId(-1);
            }
            try {
                definitions.setConfigId(Integer.parseInt(configId.getText().trim()));
            } catch (NumberFormatException e) {
                definitions.setConfigId(-1);
            }
            try {
                definitions.setClipType(Integer.parseInt(clipType.getText().trim()));
            } catch (NumberFormatException e) {
                definitions.setClipType(-1);
            }


            // Map<Integer, Object> clientScriptData = definitions.clientScriptData != null ? definitions.clientScriptData : new HashMap<>();
            // ClientScripts clientScriptModel = new ClientScripts(clientScriptData);

            // Map<Integer, Object> newClientScriptData = new HashMap<>();
            // for (Map.Entry<Integer, Object> entry : clientScriptModel.getEntries()) {
            //     if (entry.getKey() != null && entry.getValue() != null) {
            //         newClientScriptData.put(entry.getKey(), entry.getValue());
            //     }
            // }
            // definitions.clientScriptData = new HashMap<>(newClientScriptData);

            definitions.write(ObjectSelection.Cache);
            objectSelection.updateObjectDefs(definitions);

            JOptionPane.showMessageDialog(this, "Object saved.");
            dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving object: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private String[] parseOptionsString(String text) {
        if (text == null || text.trim().isEmpty()) {
            return new String[0];
        }
        String[] parts = text.split(";");
        String[] options = new String[parts.length];
        for (int i = 0; i < parts.length; i++) {
            String part = parts[i].trim();
            if (part.equalsIgnoreCase("null") || part.isEmpty()) {
                options[i] = null;
            } else {
                options[i] = part;
            }
        }
        return options;
    }

    private String optionsArrayToString(ObjectDefinitions def) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < def.options.length; i++) {
            String opt = def.options[i];
            if (opt == null || opt.isEmpty()) {
                sb.append("null");
            } else {
                sb.append(opt);
            }
            sb.append(";");
        }
        return sb.toString();
    }

    private String intArrayToSemicolonString(int[] arr) {
        if (arr == null || arr.length == 0) return "";
        StringBuilder sb = new StringBuilder();
        for (int i: arr) {
            sb.append(i).append(";");
        }
        return sb.toString();
    }

    private String shortArrayToSemicolonString(short[] arr) {
        if (arr == null || arr.length == 0) return "";
        StringBuilder sb = new StringBuilder();
        for (short i: arr) {
            sb.append(i).append(";");
        }
        return sb.toString();
    }

    private int[] parseIntArray(String text, String separator) {
        if (text == null || text.trim().isEmpty()) return null;
        String[] parts = text.split(separator);
        int[] result = new int[parts.length];
        int count = 0;
        for (String part: parts) {
            String p = part.trim();
            if (p.isEmpty()) continue;
            try {
                result[count++] = Integer.parseInt(p);
            } catch (NumberFormatException ignored) {}
        }
        if (count == 0) return null;
        if (count < result.length) {
            int[] trimmed = new int[count];
            System.arraycopy(result, 0, trimmed, 0, count);
            return trimmed;
        }
        return result;
    }

    private short[] parseShortArray(String text, String separator) {
        if (text == null || text.trim().isEmpty()) return null;
        String[] parts = text.split(separator);
        short[] result = new short[parts.length];
        int count = 0;
        for (String part: parts) {
            String p = part.trim();
            if (p.isEmpty()) continue;
            try {
                result[count++] = Short.parseShort(p);
            } catch (NumberFormatException ignored) {}
        }
        if (count == 0) return null;
        if (count < result.length) {
            short[] trimmed = new short[count];
            System.arraycopy(result, 0, trimmed, 0, count);
            return trimmed;
        }
        return result;
    }

    private String pairColorsToString(short[] originals, short[] modifieds) {
        if (originals == null || modifieds == null) return "";
        StringBuilder sb = new StringBuilder();
        int len = Math.min(originals.length, modifieds.length);
        for (int i = 0; i < len; i++) {
            sb.append(originals[i]).append("=").append(modifieds[i]).append(";");
        }
        return sb.toString();
    }

    private short[] parseOriginalColors(String text) {
        if (text == null || text.isEmpty()) return new short[0];
        String[] pairs = text.split(";");
        short[] arr = new short[pairs.length];
        for (int i = 0; i < pairs.length; i++) {
            String pair = pairs[i].trim();
            if (pair.isEmpty()) continue;
            String[] parts = pair.split("=");
            arr[i] = Short.parseShort(parts[0]);
        }
        return arr;
    }

    private short[] parseModifiedColors(String text) {
        if (text == null || text.isEmpty()) return new short[0];
        String[] pairs = text.split(";");
        short[] arr = new short[pairs.length];
        for (int i = 0; i < pairs.length; i++) {
            String pair = pairs[i].trim();
            if (pair.isEmpty()) continue;
            String[] parts = pair.split("=");
            arr[i] = Short.parseShort(parts[1]);
        }
        return arr;
    }

    private String optionsArrayToString(String[] options) {
        if (options == null || options.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (String opt : options) {
            if (opt == null || opt.isEmpty()) {
                sb.append("null");
            } else {
                sb.append(opt);
            }
            sb.append(";");
        }
        return sb.toString();
    }
}