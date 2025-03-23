package com.editor.cache.iface;

import com.alex.defs.interfaces.ComponentDefinition;
import com.alex.filestore.Cache;
import com.editor.cache.iface.sprites.ImageUtils;
import com.editor.cache.iface.sprites.SpriteDumper;
import com.editor.cache.iface.sprites.SpriteLoader;
import com.editor.cache.iface.text.FontDecoding;
import com.editor.cache.iface.util.*;
import console.Main;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InterfaceEditor extends JFrame {
    public static Cache Cache;
    public static JProgressBar progressBar;
    public ProgressMonitor progressMonitor;
    protected JList interface_list;
    protected JPanel viewportPanel;
    protected JLabel drawnInterfaceLabel;
    protected JScrollPane interfaceViewportScrollPane;
    protected JScrollPane componentScrollpane;
    protected JComboBox comboBox;
    protected JCheckBox chckbxShowContainers = new JCheckBox();
    protected JCheckBox chckbxRepeat = new JCheckBox();
    protected JCheckBox chckbxHorizontalFlip = new JCheckBox();
    protected JCheckBox chckbxVerticalFlip = new JCheckBox();
    protected JCheckBox chckbxRefreshTreeOn = new JCheckBox();
    protected JCheckBox chckbxFilled = new JCheckBox();
    private JTextField txt_type = new JTextField();
    private JTextField txt_hash = new JTextField();
    private JTabbedPane tabbedPane;
    private JTextField txt_x = new JTextField();
    private JTextField txt_y = new JTextField();
    private JTextField txt_height = new JTextField();
    private JTextField txt_width = new JTextField();
    private JTextField txt_text = new JTextField();
    private JTextField txt_leftclick = new JTextField();
    private JTextField txt_parent = new JTextField();
    private JTextField txt_option1 = new JTextField();
    private JTextField txt_option2 = new JTextField();
    private JTextField txt_option3 = new JTextField();
    private JTextField txt_option4 = new JTextField();
    private JTextField txt_option5 = new JTextField();
    private JTextField txt_modex = new JTextField();
    private JTextField txt_positionmodeY = new JTextField();
    private JCheckBox chckbxShowHiddenComps = new JCheckBox();
    private JCheckBox chckbxShowRectangles = new JCheckBox();
    private JTextField txt_border = new JTextField();
    private JMenuItem mntmPackSprite;
    private JTextField txt_sprite = new JTextField();
    private JTextField txt_modeHeight = new JTextField();
    private JTextField txt_widthMode = new JTextField();
    private JTextField txt_model = new JTextField();
    private JCheckBox chckbxHidden = new JCheckBox();
    private JTextField txt_onMouseRepeat = new JTextField();
    private JTextField txt_fullonhover = new JTextField();
    private JTextField txt_mouseLeave = new JTextField();
    private JTextField txt_onload = new JTextField();
    private JTextField txt_onUseWith = new JTextField();
    private JTextField txt_onUse = new JTextField();
    private JTextField txt_onVarpTransmit = new JTextField();
    private JTextField txt_onInvTransmit = new JTextField();
    private JTextField txt_onStatTransmit = new JTextField();
    private JTextField txt_onTimer = new JTextField();
    private JTextField txt_varpTriggers = new JTextField();
    private JTextField txt_onOptionClick = new JTextField();
    private JTextField txt_font = new JTextField();
    private JTextField txt_animationId = new JTextField();
    private JTextField txt_color = new JTextField();
    private JTextField txt_trans = new JTextField();
    private JTextField txt_multi = new JTextField();
    private JTextField txt_qcopy_inter = new JTextField();
    private JTextField txt_qcopy_comp = new JTextField();
    private JTextField txt_scrollX = new JTextField();
    private JTextField txt_scrollY = new JTextField();
    private JTextField txt_inventoryTriggers = new JTextField();
    private JTextField txt_statTriggers = new JTextField();
    private JTextField txt_varcTriggers = new JTextField();
    private JTextField txt_varcStrTriggers = new JTextField();
    private JTextField txt_onClickRepeat = new JTextField();
    private JTextField txt_onDrag = new JTextField();
    private JTextField txt_onRelease = new JTextField();
    private JTextField txt_onHold = new JTextField();
    private JTextField txt_onDragStart = new JTextField();
    private JTextField txt_onDragRelease = new JTextField();
    private JTextField txt_onScroll = new JTextField();
    private JTextField txt_onVarcTransmit = new JTextField();
    private JTextField txt_onVarcStrTransmit = new JTextField();
    private JTextField txt_xali = new JTextField();
    private JTextField txt_yali = new JTextField();
    private JTextField txt_interId = new JTextField();
    private JCheckBox chckbxRealFonttesting = new JCheckBox();
    private JTextArea textArea = new JTextArea(15, 30);
    private JScrollPane ifListScrollPane;
    private BufferedImage result;

    private int currentInterface = -1;
    private int selectedComp = -1;
    private ComponentDefinition copiedComp = null;
    private ComponentDefinition selectedComponent;

    public InterfaceEditor(String cache) throws IOException {
        Cache = new Cache(cache);
        setTitle("Interface Editor Shnek");
        getContentPane().setLayout(new BorderLayout());
        setDefaultCloseOperation(1);
        setBounds(100, 100, IfaceConstants.DEFAULT_EDITOR_WIDTH, IfaceConstants.DEFAULT_EDITOR_HEIGHT);
        constructWestPanel();
        constructCenterPanel();
        constructEastPanel();
        chckbxShowHiddenComps.addItemListener(e -> viewportPanel.repaint());

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu mnNewMenu = new JMenu("");
        menuBar.add(mnNewMenu);

        JMenu mnNewMenu_1 = new JMenu("Information");
        mnNewMenu_1.addActionListener(arg0 -> JOptionPane.showMessageDialog(interfaceViewportScrollPane, "Interface editor made by Shnek, Discord : Cara Shnek#6969 "));
        menuBar.add(mnNewMenu_1);

        JMenu mnAbout = new JMenu("Extra");
        menuBar.add(mnAbout);

        JMenuItem mntmDumpSprites = getJMenuItem(cache, menuBar);
        mnAbout.add(mntmDumpSprites);

        JMenuItem mntmPackInterface = getJMenuItem();
        mnAbout.add(mntmPackInterface);

        pack();
        revalidate();
    }

    @NotNull
    private JMenuItem getJMenuItem(String cache, JMenuBar menuBar) {
        JMenuItem mntmDumpSprites = new JMenuItem("Export sprites");
        mntmDumpSprites.addActionListener(arg0 -> {
            progressMonitor = new ProgressMonitor(menuBar, "Dumping sprites...", "", 0, Cache.getIndexes()[8].getLastArchiveId());
            progressMonitor.setProgress(1);

            new Thread(() -> {
                try {
                    SpriteDumper.dump(cache, progressMonitor);
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(interfaceViewportScrollPane, "An error occurred during sprite dumping.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }).start();
        });
        return mntmDumpSprites;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    JFrame.setDefaultLookAndFeelDecorated(true);
                    JDialog.setDefaultLookAndFeelDecorated(true);

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "" + e);
                    Main.log("Iface tool", e.getMessage());
                }
                if (args.length < 1) {
                    try {
                        PropertyValues.loadValues();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "Properties can not be found, make sure you've a config.properties file.");
                        System.out.println("Properties can not be found, make sure you've a config.properties file.\"");
                    }
                } else {
                    String path = args[0];
                    Main.log("Iface tool", "Using cache path: " + path);
                    PropertyValues.setCachePath(path);
                }
                Main.log("Iface tool", "Application started...");
                InterfaceEditor frame = new InterfaceEditor(Cache.toString());
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @NotNull
    private JMenuItem getJMenuItem() {
        JMenuItem mntmPackInterface = new JMenuItem("Pack interface");
        mntmPackInterface.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File("."));
            chooser.setDialogTitle("Choose a file to pack interface");
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);

            int result = chooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = chooser.getSelectedFile();
                if (selectedFile != null && selectedFile.exists()) {
                    packInterface(selectedFile.getPath());
                    JOptionPane.showMessageDialog(null, "Interface packed successfully: " + selectedFile.getName());
                } else {
                    JOptionPane.showMessageDialog(null, "The selected file does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                System.out.println("No file selected.");
            }
        });
        return mntmPackInterface;
    }


    private void constructWestPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Set the size constraints for the main panel
        panel.setMinimumSize(new Dimension(IfaceConstants.LEFT_SCROLLPANE_WIDTH, IfaceConstants.DEFAULT_EDITOR_HEIGHT));
        panel.setMaximumSize(new Dimension(IfaceConstants.LEFT_SCROLLPANE_WIDTH, IfaceConstants.DEFAULT_EDITOR_HEIGHT));
        panel.setPreferredSize(new Dimension(IfaceConstants.LEFT_SCROLLPANE_WIDTH, IfaceConstants.DEFAULT_EDITOR_HEIGHT));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        txt_interId = new JTextField(10);
        searchPanel.add(txt_interId);

        JButton btnFind = new JButton("Find");
        btnFind.addActionListener(arg0 -> {
            try {
                int id = Integer.parseInt(txt_interId.getText());
                currentInterface = id;
                drawTree(id);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(panel, "Invalid interface ID.");
            }
        });
        searchPanel.add(btnFind);
        panel.add(searchPanel, BorderLayout.NORTH);

        // Construct the interface list panel
        ifListScrollPane = new JScrollPane();
        ifListScrollPane.setPreferredSize(new Dimension(IfaceConstants.LEFT_SCROLLPANE_WIDTH, IfaceConstants.VIEWPORT_HEIGHT));
        interface_list = new JList(populateList());
        ifListScrollPane.setViewportView(interface_list);

        interface_list.addListSelectionListener(evt -> {
            if (!evt.getValueIsAdjusting()) {
                String selectedValue = interface_list.getSelectedValue().toString();
                try {
                    int id = Integer.parseInt(selectedValue.replaceAll("Interface: ", ""));
                    currentInterface = id;
                    viewportPanel.repaint();
                    drawTree(id);
                    cleanValues();
                } catch (NumberFormatException e) {
                    System.out.println("Invalid interface selected.");
                }
            }
        });

        panel.add(ifListScrollPane, BorderLayout.CENTER);

        // Construct right-click popup menu for export
        final JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem export = new JMenuItem("Export");
        popupMenu.add(export);
        interface_list.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                if (SwingUtilities.isRightMouseButton(me) && !interface_list.isSelectionEmpty() &&
                        interface_list.locationToIndex(me.getPoint()) == interface_list.getSelectedIndex()) {
                    popupMenu.show(interface_list, me.getX(), me.getY());
                }
            }
        });

        export.addActionListener(arg0 -> {
            try {
                exportInterface(currentInterface);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(interfaceViewportScrollPane, "File could not be dumped, error: " + e.getMessage());
            }
        });

        // Construct additional buttons panel
        JPanel moreButtonsPanel = new JPanel(new FlowLayout());
        moreButtonsPanel.setPreferredSize(new Dimension(IfaceConstants.LEFT_SCROLLPANE_WIDTH, IfaceConstants.DEFAULT_EDITOR_HEIGHT / 3));

        // Add interface button
        JButton addInterfaceButton = getJButton();
        moreButtonsPanel.add(addInterfaceButton);

        // Delete interface button
        JButton deleteInterfaceButton = getButton();
        moreButtonsPanel.add(deleteInterfaceButton);

        // Construct premade components panel
        JPanel premadeComponentsPanel = new JPanel(new FlowLayout());
        premadeComponentsPanel.setBorder(new TitledBorder("Premade Components"));
        premadeComponentsPanel.setPreferredSize(new Dimension(IfaceConstants.PREMADE_COMP_WIDTH, IfaceConstants.PREMADE_COMP_HEIGHT));

        comboBox = new JComboBox();
        comboBox.setModel(new DefaultComboBoxModel(new String[]{"Close button", "Normal button", "Start interface", "Basic custom hover", "Basic button with pop-up"}));
        comboBox.setPreferredSize(new Dimension(IfaceConstants.PREMADE_COMP_WIDTH - 10, IfaceConstants.BUTTON_HEIGHT));
        premadeComponentsPanel.add(comboBox);

        JButton btnAddPremadeComponent = new JButton("Add Component");
        btnAddPremadeComponent.addActionListener(arg0 -> addDefaultComponent(currentInterface));
        btnAddPremadeComponent.setPreferredSize(new Dimension(IfaceConstants.PREMADE_COMP_WIDTH - 10, IfaceConstants.BUTTON_HEIGHT));
        premadeComponentsPanel.add(btnAddPremadeComponent);
        moreButtonsPanel.add(premadeComponentsPanel);

        // Save button
        JButton saveButton = new JButton("Save");
        saveButton.setPreferredSize(new Dimension(IfaceConstants.LEFT_SCROLLPANE_WIDTH, IfaceConstants.BUTTON_HEIGHT));
        saveButton.addActionListener(arg0 -> {
            if (currentInterface != -1 && selectedComp != -1) {
                saveInterface(currentInterface, selectedComp);
                drawTree(currentInterface);
                setValues(currentInterface, selectedComp);
            } else {
                JOptionPane.showMessageDialog(interfaceViewportScrollPane, "Please select a component & interface before saving it.");
            }
        });
        moreButtonsPanel.add(saveButton);

        panel.add(moreButtonsPanel, BorderLayout.SOUTH);

        getContentPane().add(panel, BorderLayout.WEST);
    }

    @NotNull
    private JButton getButton() {
        JButton deleteInterfaceButton = new JButton("Delete Selected");
        deleteInterfaceButton.addActionListener(arg0 -> {
            int option = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure that you want to remove interface " + currentInterface + "?",
                    "Inane warning",
                    JOptionPane.YES_NO_OPTION
            );

            if (option == JOptionPane.YES_OPTION) {
                Cache.getIndexes()[3].removeArchive(currentInterface);
                ComponentDefinition.icomponentsdefs = new ComponentDefinition[ComponentDefinition.getInterfaceDefinitionsSize(Cache)][];
                drawTree(currentInterface);

                refreshInterface();
            }
        });

        // Set the button's preferred size
        deleteInterfaceButton.setPreferredSize(new Dimension(IfaceConstants.LEFT_SCROLLPANE_WIDTH, IfaceConstants.BUTTON_HEIGHT));

        return deleteInterfaceButton;
    }

    /**
     * This method forces a full refresh of the interface.
     * It clears and re-renders the UI elements as needed.
     */
    private void refreshInterface() {
        getContentPane().removeAll();

        constructWestPanel();
        constructCenterPanel();
        constructEastPanel();

        revalidate();
        repaint();
    }


    @NotNull
    private JButton getJButton() {
        JButton addInterfaceButton = new JButton("Add Interface");
        addInterfaceButton.addActionListener(arg0 -> {
            // Initialize default button and set properties
            ComponentDefinition defaultButton = ComponentDefinition.getInterfaceComponent(6, 36);
            defaultButton.basePositionX = 0;
            defaultButton.basePositionY = 0;
            defaultButton.parentId = -1;

            // Save the default button to cache
            Cache.getIndexes()[3].putFile(ComponentDefinition.getInterfaceDefinitionsSize(Cache), 0, defaultButton.encode());

            // Initialize component definitions array
            ComponentDefinition.icomponentsdefs = new ComponentDefinition[ComponentDefinition.getInterfaceDefinitionsSize(Cache)][];

            // Create list and set up its selection listener
            JList<String> list = new JList<>(populateList());
            list.addListSelectionListener(evt -> {
                if (evt.getValueIsAdjusting()) return; // Avoid unnecessary processing
                int id = Integer.parseInt(list.getSelectedValue().toString().replaceAll("Interface: ", ""));
                currentInterface = id;
                drawTree(id);
            });

            // Set the list to the scroll pane
            ifListScrollPane.setViewportView(list);
        });

        // Set the button's preferred size
        addInterfaceButton.setPreferredSize(new Dimension(IfaceConstants.LEFT_SCROLLPANE_WIDTH, IfaceConstants.BUTTON_HEIGHT));

        return addInterfaceButton;
    }

    private void constructEastPanel() {
        // Create main panel
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(IfaceConstants.RIGHT_SCROLLPANE_WIDTH, IfaceConstants.DEFAULT_EDITOR_HEIGHT));
        panel.setMaximumSize(new Dimension(IfaceConstants.RIGHT_SCROLLPANE_WIDTH, IfaceConstants.DEFAULT_EDITOR_HEIGHT));
        panel.setMinimumSize(new Dimension(IfaceConstants.RIGHT_SCROLLPANE_WIDTH, IfaceConstants.DEFAULT_EDITOR_HEIGHT));

        // Scrollpane for the JTree
        componentScrollpane = new JScrollPane();
        componentScrollpane.setPreferredSize(new Dimension(IfaceConstants.RIGHT_SCROLLPANE_WIDTH, IfaceConstants.VIEWPORT_HEIGHT));

        // JTree setup
        JTree componentTree = new JTree(createInterfaceTree(1));
        componentScrollpane.setViewportView(componentTree);
        componentTree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) componentTree.getLastSelectedPathComponent();
            int id = Integer.parseInt(selectedNode.getUserObject().toString().replaceAll("Component ", ""));
            selectedComp = id;
            setValues(currentInterface, id);
            repaint();
        });
        panel.add(componentScrollpane, BorderLayout.CENTER);

        // Panel for more buttons
        JPanel moreButtonsPanel = new JPanel(new FlowLayout());
        moreButtonsPanel.setPreferredSize(new Dimension(IfaceConstants.RIGHT_SCROLLPANE_WIDTH, IfaceConstants.DEFAULT_EDITOR_HEIGHT / 3));

        // Component buttons panel
        JPanel componentButtons = getJPanel();
        moreButtonsPanel.add(componentButtons);

        // Settings panel with a titled border
        JPanel settingsPanel = new JPanel(new FlowLayout());
        settingsPanel.setBorder(new TitledBorder(null, "Settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        settingsPanel.setPreferredSize(new Dimension(IfaceConstants.RIGHT_SCROLLPANE_WIDTH, IfaceConstants.DEFAULT_EDITOR_HEIGHT / 4));

        // Checkbox size
        Dimension checkBoxSize = new Dimension(IfaceConstants.RIGHT_SCROLLPANE_WIDTH - 10, IfaceConstants.BUTTON_HEIGHT_SMALL);

        // "Show Containers" checkbox
        chckbxShowContainers = new JCheckBox("Show Containers");
        chckbxShowContainers.setSelected(true);
        chckbxShowContainers.setPreferredSize(checkBoxSize);
        chckbxShowContainers.addItemListener(e -> {
            boolean selected = e.getStateChange() == ItemEvent.SELECTED;
            drawInterfaceComponents(currentInterface, selected, chckbxShowHiddenComps.isSelected(), chckbxShowRectangles.isSelected(), chckbxRealFonttesting.isSelected());
        });
        settingsPanel.add(chckbxShowContainers);

        // "Show Models" checkbox
        chckbxShowRectangles = new JCheckBox("Show Models");
        chckbxShowRectangles.setSelected(true);
        chckbxShowRectangles.setPreferredSize(checkBoxSize);
        chckbxShowRectangles.addItemListener(e -> {
            boolean selected = e.getStateChange() == ItemEvent.SELECTED;
            drawInterfaceComponents(currentInterface, chckbxShowContainers.isSelected(), chckbxShowHiddenComps.isSelected(), selected, chckbxRealFonttesting.isSelected());
        });
        settingsPanel.add(chckbxShowRectangles);

        // "Show Hidden" checkbox
        chckbxShowHiddenComps = new JCheckBox("Show Hidden");
        chckbxShowHiddenComps.setPreferredSize(checkBoxSize);
        settingsPanel.add(chckbxShowHiddenComps);

        // "Draw Real Font" checkbox with mouse listener
        chckbxRealFonttesting = new JCheckBox("Draw Real Font");
        chckbxRealFonttesting.setPreferredSize(checkBoxSize);
        chckbxRealFonttesting.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                drawTree(currentInterface);
                drawInterfaceComponents(currentInterface, chckbxShowContainers.isSelected(), chckbxShowHiddenComps.isSelected(), chckbxShowRectangles.isSelected(), chckbxRealFonttesting.isSelected());
            }
        });
        settingsPanel.add(chckbxRealFonttesting);

        // Add settings panel to more buttons panel
        moreButtonsPanel.add(settingsPanel);

        // Add the more buttons panel to the main panel
        panel.add(moreButtonsPanel, BorderLayout.SOUTH);

        // Add the main panel to the content pane
        getContentPane().add(panel, BorderLayout.EAST);
    }

    @NotNull
    private JPanel getJPanel() {
        JPanel componentButtons = new JPanel(new FlowLayout());
        componentButtons.setPreferredSize(new Dimension(IfaceConstants.RIGHT_SCROLLPANE_WIDTH, IfaceConstants.BUTTON_HEIGHT));

        Dimension buttonSize = new Dimension(IfaceConstants.BUTTON_WIDTH_SMALL, IfaceConstants.BUTTON_HEIGHT_SMALL);
        JButton btnCopy = new JButton("Copy");
        btnCopy.setToolTipText("Copy selected interface");
        btnCopy.setPreferredSize(buttonSize);
        btnCopy.addActionListener(arg0 -> {
            copiedComp = ComponentDefinition.getInterfaceComponent(currentInterface, selectedComp);
            System.out.println("Component " + copiedComp.componentId + " copied from interface " + copiedComp.interfaceId);
        });
        componentButtons.add(btnCopy);

        JButton btnPaste = new JButton("Paste");
        btnPaste.setPreferredSize(buttonSize);
        btnPaste.setToolTipText("Past your copied component into the selected interface");
        btnPaste.addActionListener(e -> {
            if (currentInterface == -1) {
                return;
            }
            pasteComponent();
        });
        componentButtons.add(btnPaste);

        JButton btnDelete = getButton(buttonSize);
        componentButtons.add(btnDelete);
        return componentButtons;
    }

    @NotNull
    private JButton getButton(Dimension buttonSize) {
        JButton btnDelete = new JButton("Delete");
        btnDelete.setPreferredSize(buttonSize);
        btnDelete.setToolTipText("Deletes the selected component");
        btnDelete.addActionListener(e -> {
            if (selectedComp <= 0) {
                JOptionPane.showMessageDialog(interfaceViewportScrollPane, "Please select a component first.");
            } else {
                ComponentDefinition c = ComponentDefinition.getInterfaceComponent(currentInterface, selectedComp);
                String message = (c.type == ComponentConstants.CONTAINER ?
                        "Are you sure that you want to remove component " + selectedComp + " from interface " + currentInterface +
                                " ? NOTE: this component is a container, children will be removed as well." :
                        "Are you sure that you want to remove component " + selectedComp + " from interface " + currentInterface + " ?");
                int option = JOptionPane.showConfirmDialog(this, message, "Inane warning", JOptionPane.YES_NO_OPTION);
                if (option == 0) {
                    try {
                        // If the component is a container, delete all nested components as well
                        if (c.type == ComponentConstants.CONTAINER) {
                            for (int compId : getNestedComponents(currentInterface, selectedComp)) {
                                Cache.getIndexes()[3].removeFile(currentInterface, compId);
                            }
                        }
                        // Delete the selected component itself
                        Cache.getIndexes()[3].removeFile(currentInterface, selectedComp);
                        Cache.getIndexes()[3].resetCachedFiles();
                        Cache.getIndexes()[3].rewriteTable();
                    } finally {
                        drawTree(currentInterface);
                    }
                }
            }
        });
        return btnDelete;
    }

    /**
     * This method retrieves nested components for a container component.
     * It assumes the children are stored sequentially in the interface.
     *
     * @param interfaceId The ID of the interface.
     * @param componentId The ID of the container component.
     * @return A list of nested component IDs.
     */
    private List<Integer> getNestedComponents(int interfaceId, int componentId) {
        List<Integer> nestedComponents = new ArrayList<>();

        // Retrieve the components for the interface
        ComponentDefinition[] interfaceComponents = ComponentDefinition.getInterface(interfaceId);
        if (interfaceComponents == null || componentId >= interfaceComponents.length) {
            return nestedComponents;
        }

        // Get the container component
        ComponentDefinition containerComponent = interfaceComponents[componentId];
        if (containerComponent == null || containerComponent.type != ComponentConstants.CONTAINER) {
            return nestedComponents;
        }

        for (int i = componentId + 1; i < interfaceComponents.length; i++) {
            ComponentDefinition childComponent = interfaceComponents[i];
            if (childComponent != null && childComponent.parentId == componentId) {
                nestedComponents.add(i);
            }
        }

        return nestedComponents;
    }

    private void constructCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(IfaceConstants.VIEWPORT_WIDTH, IfaceConstants.DEFAULT_EDITOR_HEIGHT));
        panel.setMaximumSize(new Dimension(IfaceConstants.VIEWPORT_WIDTH, IfaceConstants.DEFAULT_EDITOR_HEIGHT));
        panel.setMinimumSize(new Dimension(IfaceConstants.VIEWPORT_WIDTH, IfaceConstants.DEFAULT_EDITOR_HEIGHT));

        Dimension buttonSize = new Dimension(IfaceConstants.BUTTON_WIDTH, IfaceConstants.BUTTON_HEIGHT_SMALL);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton btnAddSprite = getJButton(buttonSize);
        buttonPanel.add(btnAddSprite);

        JButton btnAddText = getBtnAddText(buttonSize);
        buttonPanel.add(btnAddText);

        JButton btnAddContainer = new JButton("Add Container");
        btnAddContainer.setPreferredSize(buttonSize);
        btnAddContainer.addActionListener(e -> {
            if (currentInterface <= 0) return;
            ComponentDefinition comp = ComponentDefinition.getInterfaceComponent(6, 0);
            comp.basePositionX = 0;
            comp.basePositionY = 0;
            comp.baseHeight = 50;
            comp.baseWidth = 50;
            comp.parentId = -1;
            Cache.getIndexes()[3].putFile(currentInterface, ComponentDefinition.getInterfaceDefinitionsComponentsSize(Cache, currentInterface), comp.encode());
            ComponentDefinition.getInterface(currentInterface);
            drawTree(currentInterface);

        });
        buttonPanel.add(btnAddContainer);

        JButton btnAddModel = new JButton("Add Model");
        btnAddModel.setPreferredSize(buttonSize);
        btnAddModel.addActionListener(e -> {
            if (currentInterface <= 0) return;
            ComponentDefinition comp = ComponentDefinition.getInterfaceComponent(732, 3);
            comp.basePositionX = 0;
            comp.basePositionY = 0;
            comp.parentId = -1;
            comp.ihash = ComponentDefinition.getInterfaceDefinitionsComponentsSize(Cache, currentInterface) + (currentInterface << 16);
            Cache.getIndexes()[3].putFile(currentInterface, ComponentDefinition.getInterfaceDefinitionsComponentsSize(Cache, currentInterface), comp.encode());
            ComponentDefinition.getInterface(currentInterface);
            drawTree(currentInterface);
        });
        buttonPanel.add(btnAddModel);

        JButton btnAddRectangle = new JButton("Add Rectangle");
        btnAddRectangle.setPreferredSize(buttonSize);
        btnAddRectangle.addActionListener(e -> {
            if (currentInterface <= 0) return;
            ComponentDefinition comp = ComponentDefinition.getInterfaceComponent(640, 0);
            comp.basePositionX = 0;
            comp.basePositionY = 0;
            comp.parentId = -1;
            comp.ihash = ComponentDefinition.getInterfaceDefinitionsComponentsSize(Cache, currentInterface) + (currentInterface << 16);
            Cache.getIndexes()[3].putFile(currentInterface, ComponentDefinition.getInterfaceDefinitionsComponentsSize(Cache, currentInterface), comp.encode());
            ComponentDefinition.getInterface(currentInterface);
            drawTree(currentInterface);

        });
        buttonPanel.add(btnAddRectangle);
        panel.add(buttonPanel, BorderLayout.NORTH);

        viewportPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                if (currentInterface == -1) return;
                drawInterfaceToGraphics(g, currentInterface, chckbxShowContainers.isSelected(), chckbxShowHiddenComps.isSelected(), chckbxShowRectangles.isSelected(), chckbxRealFonttesting.isSelected());
                if (selectedComponent != null && (selectedComponent.ihash >> 16) == currentInterface) {
                    g.setColor(Color.WHITE);
                    ComponentPosition.setValues(selectedComponent);
                    g.drawRect(ComponentDefinition.getX(selectedComponent, currentInterface), ComponentDefinition.getY(selectedComponent, currentInterface), selectedComponent.width, selectedComponent.height);
                }
            }
        };
        viewportPanel.setPreferredSize(new Dimension(IfaceConstants.VIEWPORT_WIDTH, IfaceConstants.VIEWPORT_HEIGHT));
        viewportPanel.setMinimumSize(new Dimension(IfaceConstants.VIEWPORT_WIDTH, IfaceConstants.VIEWPORT_HEIGHT));
        viewportPanel.setMaximumSize(new Dimension(IfaceConstants.VIEWPORT_WIDTH, IfaceConstants.VIEWPORT_HEIGHT));
        panel.add(viewportPanel, BorderLayout.CENTER);

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setPreferredSize(new Dimension(IfaceConstants.DEFAULT_EDITOR_WIDTH - IfaceConstants.LEFT_SCROLLPANE_WIDTH - IfaceConstants.RIGHT_SCROLLPANE_WIDTH, IfaceConstants.DEFAULT_EDITOR_HEIGHT - IfaceConstants.VIEWPORT_HEIGHT - IfaceConstants.BUTTON_HEIGHT - 4));
        tabbedPane.addTab("General", null, constructGeneralTab());
        tabbedPane.addTab("Text", null, constructTextTab());
        tabbedPane.addTab("Model", null, constructModelTab());
        tabbedPane.addTab("Sprite", null, constructSpriteTab());
        tabbedPane.addTab("Scripts", null, constructScriptsTab());
        tabbedPane.addTab("Triggers", null, constructTriggersTab());
        panel.add(tabbedPane, BorderLayout.SOUTH);

        getContentPane().add(panel, BorderLayout.CENTER);
    }

    @NotNull
    private JButton getBtnAddText(Dimension buttonSize) {
        JButton btnAddText = new JButton("Add Text");
        btnAddText.setPreferredSize(buttonSize);
        btnAddText.addActionListener(e -> {
            if (currentInterface <= 0) return;
            ComponentDefinition comp = ComponentDefinition.getInterfaceComponent(4, 5);
            comp.basePositionX = 0;
            comp.basePositionY = 0;
            comp.parentId = -1;
            comp.text = "Hallo world";
            comp.ihash = ComponentDefinition.getInterfaceDefinitionsComponentsSize(Cache, currentInterface) + (currentInterface << 16);
            Cache.getIndexes()[3].putFile(currentInterface, ComponentDefinition.getInterfaceDefinitionsComponentsSize(Cache, currentInterface), comp.encode());
            ComponentDefinition.getInterface(currentInterface);
            drawTree(currentInterface);

        });
        return btnAddText;
    }

    @NotNull
    private JButton getJButton(Dimension buttonSize) {
        JButton btnAddSprite = new JButton("Add Sprite");
        btnAddSprite.setPreferredSize(buttonSize);
        btnAddSprite.addActionListener(arg0 -> {
            if (currentInterface <= 0) return;
            ComponentDefinition comp = ComponentDefinition.getInterfaceComponent(6, 38);
            comp.basePositionX = 0;
            comp.basePositionY = 0;
            comp.parentId = -1;
            comp.spriteId = 0;
            comp.ihash = ComponentDefinition.getInterfaceDefinitionsComponentsSize(Cache, currentInterface) + (currentInterface << 16);
            Cache.getIndexes()[3].putFile(currentInterface, ComponentDefinition.getInterfaceDefinitionsComponentsSize(Cache, currentInterface), comp.encode());
            ComponentDefinition.getInterface(currentInterface);
            drawTree(currentInterface);

        });
        return btnAddSprite;
    }

    private JPanel createInfoFieldPanel(String label, JTextField backingField) {
        JPanel fieldPanel = new JPanel(new BorderLayout());
        fieldPanel.setPreferredSize(new Dimension(IfaceConstants.INFO_FIELD_WIDTH, IfaceConstants.INFO_FIELD_HEIGHT));

        JTextField labelField = new JTextField();
        labelField.setPreferredSize(new Dimension(IfaceConstants.INFO_FIELD_WIDTH / 3, IfaceConstants.INFO_FIELD_HEIGHT));
        labelField.setText(label);
        labelField.setEditable(true);
        fieldPanel.add(labelField, BorderLayout.WEST);

        backingField.setText("");
        backingField.setEditable(true);
        fieldPanel.add(backingField, BorderLayout.CENTER);

        return fieldPanel;
    }

    private JPanel createInfoFieldPanel(String label, JCheckBox backingField) {
        JPanel fieldPanel = new JPanel(new BorderLayout());
        fieldPanel.setPreferredSize(new Dimension(IfaceConstants.INFO_FIELD_WIDTH, IfaceConstants.INFO_FIELD_HEIGHT));

        JTextField labelField = new JTextField();
        labelField.setPreferredSize(new Dimension(IfaceConstants.INFO_FIELD_WIDTH / 3, IfaceConstants.INFO_FIELD_HEIGHT));
        labelField.setText(label);
        labelField.setEditable(true);
        fieldPanel.add(labelField, BorderLayout.WEST);

        backingField.setText("");
        backingField.setEnabled(true);
        fieldPanel.add(backingField, BorderLayout.CENTER);

        return fieldPanel;
    }

    private JPanel constructGeneralTab() {
        JPanel generalTab = new JPanel(new GridLayout(10, 2));
        generalTab.setPreferredSize(new Dimension(IfaceConstants.DEFAULT_EDITOR_WIDTH - IfaceConstants.LEFT_SCROLLPANE_WIDTH - IfaceConstants.RIGHT_SCROLLPANE_WIDTH - 10, IfaceConstants.DEFAULT_EDITOR_HEIGHT / 3 - 15));
        generalTab.add(createInfoFieldPanel("Parent", txt_parent));
        generalTab.add(createInfoFieldPanel("Hidden", chckbxHidden));
        generalTab.add(createInfoFieldPanel("Type", txt_type));
        generalTab.add(createInfoFieldPanel("Hash", txt_hash));
        generalTab.add(createInfoFieldPanel("X Pos", txt_x));
        generalTab.add(createInfoFieldPanel("X Mode", txt_modex));
        generalTab.add(createInfoFieldPanel("Y Pos", txt_y));
        generalTab.add(createInfoFieldPanel("Y Mode", txt_positionmodeY));
        generalTab.add(createInfoFieldPanel("Width", txt_width));
        generalTab.add(createInfoFieldPanel("Width Mode", txt_widthMode));
        generalTab.add(createInfoFieldPanel("Height", txt_height));
        generalTab.add(createInfoFieldPanel("Height Mode", txt_modeHeight));
        generalTab.add(createInfoFieldPanel("Color", txt_color));
        generalTab.add(createInfoFieldPanel("Transparency", txt_trans));
        generalTab.add(createInfoFieldPanel("Scroll X", txt_scrollX));
        generalTab.add(createInfoFieldPanel("Scroll Y", txt_scrollY));
        return generalTab;
    }

    private JPanel constructTextTab() {
        JPanel textTab = new JPanel(new GridLayout(10, 2));
        textTab.add(createInfoFieldPanel("Left Click", txt_leftclick));
        textTab.add(createInfoFieldPanel("Text", txt_text));
        textTab.add(createInfoFieldPanel("Option 1", txt_option1));
        textTab.add(createInfoFieldPanel("Font ID", txt_font));
        textTab.add(createInfoFieldPanel("Option 2", txt_option2));
        textTab.add(createInfoFieldPanel("Multi", txt_multi));
        textTab.add(createInfoFieldPanel("Option 3", txt_option3));
        textTab.add(createInfoFieldPanel("X Align", txt_xali));
        textTab.add(createInfoFieldPanel("Option 4", txt_option4));
        textTab.add(createInfoFieldPanel("Y Align", txt_yali));
        textTab.add(createInfoFieldPanel("Option 5", txt_option5));
        return textTab;
    }

    private JPanel constructModelTab() {
        JPanel modelTab = new JPanel(new GridLayout(10, 2));
        modelTab.add(createInfoFieldPanel("Model ID", txt_model));
        modelTab.add(createInfoFieldPanel("Animation", txt_animationId));
        return modelTab;
    }

    private JPanel constructSpriteTab() {
        JPanel spriteTab = new JPanel(new GridLayout(10, 2));
        spriteTab.add(createInfoFieldPanel("Sprite ID", txt_sprite));
        spriteTab.add(createInfoFieldPanel("Border Thickness", txt_border));
        spriteTab.add(createInfoFieldPanel("Repeat", chckbxRepeat));
        spriteTab.add(createInfoFieldPanel("H Flip", chckbxHorizontalFlip));
        spriteTab.add(createInfoFieldPanel("V Flip", chckbxVerticalFlip));
        return spriteTab;
    }

    private JPanel constructScriptsTab() {
        JPanel scriptsTab = new JPanel(new GridLayout(10, 2));
        scriptsTab.add(createInfoFieldPanel("onLoad", txt_onload));
        scriptsTab.add(createInfoFieldPanel("onMouseOver", txt_fullonhover));
        scriptsTab.add(createInfoFieldPanel("onMouseLeave", txt_mouseLeave));
        scriptsTab.add(createInfoFieldPanel("onUseWith", txt_onUseWith));
        scriptsTab.add(createInfoFieldPanel("onUse", txt_onUse));
        scriptsTab.add(createInfoFieldPanel("onVarpTransmit", txt_onVarpTransmit));
        scriptsTab.add(createInfoFieldPanel("onInvTransmit", txt_onInvTransmit));
        scriptsTab.add(createInfoFieldPanel("onStatTransmit", txt_onStatTransmit));
        scriptsTab.add(createInfoFieldPanel("onTimer", txt_onTimer));
        scriptsTab.add(createInfoFieldPanel("onOptionClick", txt_onOptionClick));
        scriptsTab.add(createInfoFieldPanel("onMouseRepeat", txt_onMouseRepeat));
        scriptsTab.add(createInfoFieldPanel("onClickRepeat", txt_onClickRepeat));
        scriptsTab.add(createInfoFieldPanel("onDrag", txt_onDrag));
        scriptsTab.add(createInfoFieldPanel("onRelease", txt_onRelease));
        scriptsTab.add(createInfoFieldPanel("onHold", txt_onHold));
        scriptsTab.add(createInfoFieldPanel("onDragStart", txt_onDragStart));
        scriptsTab.add(createInfoFieldPanel("onDragRelease", txt_onDragRelease));
        scriptsTab.add(createInfoFieldPanel("onScroll", txt_onScroll));
        scriptsTab.add(createInfoFieldPanel("onVarcTransmit", txt_onVarcTransmit));
        scriptsTab.add(createInfoFieldPanel("onVarcstrTransmit", txt_onVarcStrTransmit));
        return scriptsTab;
    }

    private JPanel constructTriggersTab() {
        JPanel triggersTab = new JPanel(new GridLayout(10, 2));
        triggersTab.add(createInfoFieldPanel("varpTriggers", txt_varpTriggers));
        triggersTab.add(createInfoFieldPanel("inventoryTriggers", txt_inventoryTriggers));
        triggersTab.add(createInfoFieldPanel("statTriggers", txt_statTriggers));
        triggersTab.add(createInfoFieldPanel("varcTriggers", txt_varcTriggers));
        triggersTab.add(createInfoFieldPanel("varcStrTriggers", txt_varcStrTriggers));
        return triggersTab;
    }

    public void setValues(int inter, int componentId) {
        /* cleaning previous values*/
        System.out.println("Setting values for component: " + componentId + " from interface " + inter);
        cleanValues();
        /*  selected component*/
        final ComponentDefinition comp = ComponentDefinition.getInterfaceComponent(inter, componentId);
        /* setting all the values*/
        this.txt_hash.setText(comp.ihash + "");
        this.txt_height.setText(comp.baseHeight + "");
        if (comp.type == ComponentConstants.CONTAINER) txt_type.setText("Container");
        else if (comp.type == ComponentConstants.TEXT) txt_type.setText("Text");
        else if (comp.type == ComponentConstants.SPRITE) txt_type.setText("Sprite");
        else if (comp.type == ComponentConstants.FIGURE) txt_type.setText("Figure");
        else if (comp.type == ComponentConstants.MODEL) txt_type.setText("Model");
        else txt_type.setText(comp.type + "");
        this.txt_scrollX.setText(comp.layerHeight + "");
        this.txt_scrollY.setText(comp.layerWidth + "");
        this.txt_width.setText(comp.baseWidth + "");
        this.txt_x.setText(comp.basePositionX + "");
        this.txt_y.setText(comp.basePositionY + "");
        this.txt_border.setText(comp.width2 + "");
        this.txt_animationId.setText(comp.animationId + "");
        if (comp.hidden) chckbxHidden.setSelected(true);
        else chckbxHidden.setSelected(false);
        if (comp.hFlip) this.chckbxHorizontalFlip.setSelected(true);
        else this.chckbxHorizontalFlip.setSelected(false);
        if (comp.vFlip) this.chckbxVerticalFlip.setSelected(true);
        else this.chckbxVerticalFlip.setSelected(false);
        if (comp.filled) this.chckbxFilled.setSelected(true);
        else this.chckbxFilled.setSelected(false);

        this.txt_trans.setText(comp.transparency + "");
        this.txt_modeHeight.setText(comp.aspectHeightType + "");
        this.txt_widthMode.setText(comp.aspectWidthType + "");
        this.txt_model.setText(comp.modelId + "");
        this.txt_parent.setText(comp.parentId + "");
        this.txt_sprite.setText(comp.spriteId + "");
        this.txt_color.setText(comp.color + "");
        this.txt_text.setText(comp.text);
        this.txt_multi.setText(comp.multiline + "");
        this.txt_font.setText(comp.fontId + "");
        this.txt_modex.setText(comp.aspectXType + "");
        this.txt_positionmodeY.setText(comp.aspectYType + "");
        this.txt_xali.setText(comp.textHorizontalAli + "");
        this.txt_yali.setText(comp.textVerticalAli + "");
        if (comp.repeat_) this.chckbxRepeat.setSelected(true);
        else this.chckbxRepeat.setSelected(false);
        this.txt_border.setText(comp.borderThickness + "");

        if (comp.rightclickOptions != null) {
            this.txt_leftclick.setText(comp.rightclickOptions[0]);
            if (comp.rightclickOptions.length > 1)
                if (comp.rightclickOptions[1] != null) this.txt_option1.setText(comp.rightclickOptions[1]);
            if (comp.rightclickOptions.length > 2)
                if (comp.rightclickOptions[2] != null) this.txt_option2.setText(comp.rightclickOptions[2]);
            if (comp.rightclickOptions.length > 3)
                if (comp.rightclickOptions[3] != null) this.txt_option3.setText(comp.rightclickOptions[3]);
            if (comp.rightclickOptions.length > 4)
                if (comp.rightclickOptions[4] != null) this.txt_option4.setText(comp.rightclickOptions[4]);
            if (comp.rightclickOptions.length > 5)
                if (comp.rightclickOptions[5] != null) this.txt_option5.setText(comp.rightclickOptions[5]);
        }
        String values = "";
        if (comp.onMouseHoverScript != null) {
            for (Object o : comp.onMouseHoverScript) {
                values += o + ";";
            }
        }
        txt_fullonhover.setText(values);
        values = "";
        if (comp.onMouseLeaveScript != null) {
            for (Object o : comp.onMouseLeaveScript) {
                values += o + ";";
            }
        }
        txt_mouseLeave.setText(values);
        values = "";
        if (comp.onLoadScript != null) {
            for (Object o : comp.onLoadScript) {
                values += o + ";";
            }
        }
        this.txt_onload.setText(values);
        //anObjectArray4770
        values = "";
        if (comp.onOptionClick != null) {
            for (Object o : comp.onOptionClick) {
                values += o + ";";
            }
        }
        this.txt_onOptionClick.setText(values);
        values = "";
        if (comp.onUseWith != null) {
            for (Object o : comp.onUseWith) {
                values += o + ";";
            }
        }
        this.txt_onUseWith.setText(values);
        values = "";
        if (comp.onUse != null) {
            for (Object o : comp.onUse) {
                values += o + ";";
            }
        }
        this.txt_onUse.setText(values);
        values = "";
        if (comp.onVarpTransmit != null) {
            for (Object o : comp.onVarpTransmit) {
                values += o + ";";
            }
        }
        this.txt_onVarpTransmit.setText(values);
        values = "";
        if (comp.onMouseRepeat != null) {
            for (Object o : comp.onMouseRepeat) {
                values += o + ";";
            }

        }
        this.txt_onMouseRepeat.setText(values);
        values = "";
        if (comp.onInvTransmit != null) {
            for (Object o : comp.onInvTransmit) {
                values += o + ";";
            }
        }
        this.txt_onInvTransmit.setText(values);

        values = "";
        if (comp.onStatTransmit != null) {
            for (Object o : comp.onStatTransmit) {
                values += o + ";";
            }
        }
        this.txt_onStatTransmit.setText(values);

        values = "";
        if (comp.onTimer != null) {
            for (Object o : comp.onTimer) {
                values += o + ";";
            }
        }
        this.txt_onTimer.setText(values);

        values = "";
        if (comp.onClickRepeat != null) {
            for (Object o : comp.onClickRepeat) {
                values += o + ";";
            }
        }
        this.txt_onClickRepeat.setText(values);

        values = "";
        if (comp.onDrag != null) {
            for (Object o : comp.onDrag) {
                values += o + ";";
            }
        }
        this.txt_onDrag.setText(values);
        values = "";
        if (comp.onRelease != null) {
            for (Object o : comp.onRelease) {
                values += o + ";";
            }
        }
        this.txt_onRelease.setText(values);
        values = "";
        if (comp.onHold != null) {
            for (Object o : comp.onHold) {
                values += o + ";";
            }
        }
        this.txt_onHold.setText(values);
        values = "";
        if (comp.onDragStart != null) {
            for (Object o : comp.onDragStart) {
                values += o + ";";
            }
        }
        this.txt_onDragStart.setText(values);
        values = "";
        if (comp.onDragRelease != null) {
            for (Object o : comp.onDragRelease) {
                values += o + ";";
            }
        }
        this.txt_onDragRelease.setText(values);

        values = "";
        if (comp.onScroll != null) {
            for (Object o : comp.onScroll) {
                values += o + ";";
            }
        }
        this.txt_onScroll.setText(values);
        values = "";
        if (comp.onVarcTransmit != null) {
            for (Object o : comp.onVarcTransmit) {
                values += o + ";";
            }
        }
        this.txt_onVarcTransmit.setText(values);
        values = "";
        if (comp.onVarcStrTransmit != null) {
            for (Object o : comp.onVarcStrTransmit) {
                values += o + ";";
            }
        }
        this.txt_onVarcStrTransmit.setText(values);
        /**
         * configs
         */
        values = "";
        if (comp.varpTriggers != null) {
            for (int i : comp.varpTriggers) {
                values += i;
            }
        }
        this.txt_varpTriggers.setText(values);

        values = "";
        if (comp.inventoryTriggers != null) {
            for (int i : comp.inventoryTriggers) {
                values += i;
            }
        }
        this.txt_inventoryTriggers.setText(values);
        values = "";
        if (comp.statTriggers != null) {
            for (int i : comp.statTriggers) {
                values += i;
            }
        }
        this.txt_statTriggers.setText(values);
        values = "";
        if (comp.varcTriggers != null) {
            for (int i : comp.varcTriggers) {
                values += i;
            }
        }
        this.txt_varcTriggers.setText(values);
        values = "";
        if (comp.varcstrTriggers != null) {
            for (int i : comp.varcstrTriggers) {
                values += i;
            }
        }
        this.txt_varcStrTriggers.setText(values);
        /*
         * drawing rec
         */
        this.viewportPanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                g.setColor(Color.WHITE);
                g.drawImage(result, 0, 0, null);
                ComponentPosition.setValues(comp);
                g.drawRect(ComponentDefinition.getX(comp, inter), ComponentDefinition.getY(comp, inter), comp.width, comp.height);

            }
        };
        selectedComponent = comp;
    }


    /**
     * cleans all the input of a giving component container
     *
     * @param container
     */
    private void cleanTab(Container container) {
        for (Component c : container.getComponents())
            if (c instanceof JTextField && c.isEnabled()) {
                JTextField ctrl = (JTextField) c;
                if (ctrl.isEditable()) ctrl.setText(null);
            }
    }

    /**
     * cleans all the values
     */
    public void cleanValues() {
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            cleanTab((Container) tabbedPane.getComponentAt(i));
        }
    }

    /**
     * Saves the interface component data.
     *
     * @param inter The interface ID.
     * @param comp  The component ID.
     */
    public void saveInterface(int inter, int comp) {
        // Retrieve the component to be saved
        ComponentDefinition changedComponent = ComponentDefinition.getInterfaceComponent(inter, comp);

        // Set basic properties
        changedComponent.basePositionX = parseInt(txt_x.getText());
        changedComponent.basePositionY = parseInt(txt_y.getText());
        changedComponent.baseHeight = parseInt(txt_height.getText());
        changedComponent.baseWidth = parseInt(txt_width.getText()); // Fixed typo from 'txt_width' to 'txt_width'
        changedComponent.parentId = parseInt(txt_parent.getText());
        changedComponent.color = parseInt(txt_color.getText());
        changedComponent.aspectXType = (byte) parseInt(txt_modex.getText());
        changedComponent.aspectYType = (byte) parseInt(txt_positionmodeY.getText());

        // Handle sprite-specific properties
        if (changedComponent.type == ComponentConstants.SPRITE) {
            changedComponent.spriteId = parseInt(txt_sprite.getText());
        }

        // Set layer dimensions
        changedComponent.layerHeight = parseInt(txt_scrollX.getText());
        changedComponent.layerWidth = parseInt(txt_scrollY.getText());

        // Set text and font properties
        changedComponent.text = txt_text.getText();
        if (!txt_font.getText().isEmpty()) {
            changedComponent.fontId = parseInt(txt_font.getText());
        }

        // Handle animation ID
        if (!txt_animationId.getText().isEmpty()) {
            changedComponent.animationId = parseInt(txt_animationId.getText());
        }

        // Handle visibility and flipping
        changedComponent.hidden = chckbxHidden.isSelected();
        changedComponent.hFlip = chckbxHorizontalFlip.isSelected();
        changedComponent.vFlip = chckbxVerticalFlip.isSelected();

        // Set model and dimension properties
        changedComponent.modelId = parseInt(txt_model.getText());
        changedComponent.aspectHeightType = (byte) parseInt(txt_modeHeight.getText());
        changedComponent.aspectWidthType = (byte) parseInt(txt_widthMode.getText());
        changedComponent.transparency = parseInt(txt_trans.getText());
        changedComponent.filled = chckbxFilled.isSelected();
        changedComponent.textHorizontalAli = parseInt(txt_xali.getText());
        changedComponent.textVerticalAli = parseInt(txt_yali.getText());

        // Save right-click options
        if (!txt_leftclick.getText().isEmpty()) {
            if (changedComponent.rightclickOptions == null) {
                changedComponent.rightclickOptions = new String[5];
            }
            changedComponent.optionMask = ComponentConstants.CLICK_MASK;
            changedComponent.rightclickOptions[0] = txt_leftclick.getText();
        }

        // Save scripts
        saveScripts(changedComponent);

        // Save the component to the cache
        System.out.println("Saving component " + comp + " from interface " + inter);
        Cache.getIndexes()[3].putFile(inter, comp, changedComponent.encode());
    }

    /**
     * Parses the provided text to an integer, returning 0 if parsing fails.
     *
     * @param text The text to be parsed.
     * @return The parsed integer, or 0 if parsing fails.
     */
    private int parseInt(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return 0; // Return a default value if parsing fails
        }
    }

    /**
     * Saves the script arrays for the component.
     *
     * @param component The component to which scripts will be added.
     */
    private void saveScripts(ComponentDefinition component) {
        component.onMouseRepeat = InterfaceUtils.getScriptArray(txt_onMouseRepeat.getText());
        component.onMouseHoverScript = InterfaceUtils.getScriptArray(txt_fullonhover.getText());
        component.onMouseLeaveScript = InterfaceUtils.getScriptArray(txt_mouseLeave.getText());
        component.onOptionClick = InterfaceUtils.getScriptArray(txt_onOptionClick.getText());
        component.onStatTransmit = InterfaceUtils.getScriptArray(txt_onStatTransmit.getText());
        component.onUse = InterfaceUtils.getScriptArray(txt_onUse.getText());
        component.onVarpTransmit = InterfaceUtils.getScriptArray(txt_onVarpTransmit.getText());
        component.onInvTransmit = InterfaceUtils.getScriptArray(txt_onInvTransmit.getText());
        component.onTimer = InterfaceUtils.getScriptArray(txt_onTimer.getText());
        component.onLoadScript = InterfaceUtils.getScriptArray(txt_onload.getText());
        component.onUseWith = InterfaceUtils.getScriptArray(txt_onUseWith.getText());
        component.varpTriggers = InterfaceUtils.getConfigArray(txt_varpTriggers.getText());
    }

    /**
     * Fills the list with interface names.
     *
     * @return DefaultListModel containing interface names.
     */
    public DefaultListModel populateList() {
        System.out.println("Populating interface list");

        DefaultListModel listModel = new DefaultListModel();

        if (Cache == null) {
            System.out.println("Cache is null, cannot populate the interface list.");
            return listModel;
        }

        int interfaceCount = ComponentDefinition.getInterfaceDefinitionsSize(Cache);

        for (int i = 0; i < interfaceCount; i++) {
            try {
                ComponentDefinition[] interfaceDefs = ComponentDefinition.getInterface(i, false, Cache);

                if (interfaceDefs != null) {
                    listModel.addElement("Interface: " + i);
                } else {
                    System.out.println("Interface " + i + " is null or failed to load.");
                }

            } catch (Exception ex) {
                System.out.println("Error loading interface " + i + ": " + ex.getMessage());
                ex.printStackTrace();
            }
        }

        Main.log("Iface tool", "Done populating list.");
        return listModel;
    }

    /**
     * Fills the JTree with components for a given interface ID.
     *
     * @param interfaceId The ID of the interface.
     * @return The tree model for the interface.
     */
    public DefaultTreeModel createInterfaceTree(int interfaceId) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Interface " + interfaceId);

        // Loop through the components of the given interface ID.
        for (int i = 0; i < ComponentDefinition.getInterfaceDefinitionsComponentsSize(Cache, interfaceId); i++) {
            ComponentDefinition component = ComponentDefinition.getInterfaceComponent(interfaceId, i);
            if (component == null) {
                System.out.println("Component is null at index: " + i);
                continue;
            }

            // Check if the component has child components.
            if (component.parentId == -1 && ComponentDefinition.hasChildren(interfaceId, component.ihash)) {
                DefaultMutableTreeNode parentNode = new DefaultMutableTreeNode("Component " + component.componentId);
                ArrayList<ComponentDefinition> childComponents = ComponentDefinition.getChildrenByParent(interfaceId, component.ihash);

                // Loop through the child components.
                for (ComponentDefinition child : childComponents) {
                    addChildNodes(interfaceId, child, parentNode);
                }

                root.add(parentNode);
            } else if (component.parentId == -1) {
                // If no children, add the component as a leaf node.
                DefaultMutableTreeNode leafNode = new DefaultMutableTreeNode("Component " + component.componentId);
                root.add(leafNode);
            }
        }

        return new DefaultTreeModel(root);
    }

    /**
     * Recursively adds child nodes for a component and its children.
     *
     * @param interfaceId The ID of the interface.
     * @param component   The component whose children are being added.
     * @param parentNode  The parent node to which the child nodes will be added.
     */
    private void addChildNodes(int interfaceId, ComponentDefinition component, DefaultMutableTreeNode parentNode) {
        if (ComponentDefinition.hasChildren(interfaceId, component.ihash)) {
            ArrayList<ComponentDefinition> childComponents = ComponentDefinition.getChildrenByParent(interfaceId, component.ihash);
            DefaultMutableTreeNode containerNode = new DefaultMutableTreeNode("Component " + component.componentId);

            // Recursively add child nodes for this component.
            for (ComponentDefinition child : childComponents) {
                addChildNodes(interfaceId, child, containerNode);
            }

            parentNode.add(containerNode);
        } else {
            DefaultMutableTreeNode childNode = new DefaultMutableTreeNode("Component " + component.componentId);
            parentNode.add(childNode);
        }
    }

    /**
     * Handles the pasting of a copied component into the current interface.
     */
    public void pasteComponent() {
        if (copiedComp == null) {
            JOptionPane.showMessageDialog(interfaceViewportScrollPane, "No component was selected to paste.");
            return;
        }

        int componentSize = ComponentDefinition.getInterfaceDefinitionsComponentsSize(Cache, currentInterface);
        copiedComp.parentId = -1; // Reset parent ID to detach from previous structure

        // If copying a container, ensure child components are also copied
        if (copiedComp.type == ComponentConstants.CONTAINER) {
            pasteContainer(componentSize);
        } else {
            pasteSingleComponent(componentSize);
        }

        ComponentDefinition.getInterface(currentInterface);
        drawTree(currentInterface);
    }

    /**
     * Pastes a container component along with its child components.
     *
     * @param containerIndex The index where the new container will be placed.
     */
    private void pasteContainer(int containerIndex) {
        Cache.getIndexes()[3].putFile(currentInterface, containerIndex, copiedComp.encode());

        // Retrieve and clone child components
        ArrayList<ComponentDefinition> childComponents = ComponentDefinition.getChildrenByParent(copiedComp.interfaceId, copiedComp.ihash);
        if (childComponents == null || childComponents.isEmpty()) return;

        int startIndex = ComponentDefinition.getInterfaceDefinitionsComponentsSize(Cache, currentInterface);
        for (ComponentDefinition child : childComponents) {
            child.parentId = copiedComp.ihash; // Assign new parent ID
            Cache.getIndexes()[3].putFile(currentInterface, startIndex++, child.encode());
        }

        // Refresh parent-child relationships
        updateParentReferences(containerIndex, childComponents);
    }

    /**
     * Pastes a single component into the interface.
     *
     * @param componentIndex The index where the new component will be placed.
     */
    private void pasteSingleComponent(int componentIndex) {
        Cache.getIndexes()[3].putFile(currentInterface, componentIndex, copiedComp.encode());
    }

    /**
     * Updates the parent-child relationships for copied components.
     *
     * @param containerIndex  The index of the new container component.
     * @param childComponents The list of copied child components.
     */
    private void updateParentReferences(int containerIndex, ArrayList<ComponentDefinition> childComponents) {
        int totalSize = ComponentDefinition.getInterfaceDefinitionsComponentsSize(Cache, currentInterface);
        ComponentDefinition newParent = ComponentDefinition.getInterfaceComponent(currentInterface, containerIndex);

        for (int i = totalSize - childComponents.size(); i < totalSize; i++) {
            ComponentDefinition child = ComponentDefinition.getInterfaceComponent(currentInterface, i);
            if (child != null && child.type != ComponentConstants.CONTAINER) {
                child.parentId = newParent.ihash;
                Cache.getIndexes()[3].putFile(currentInterface, i, child.encode());
            }
        }
    }

    public void exportInterface(int interfaceId) throws FileNotFoundException, IOException {
        File file = new File("data/export/" + interfaceId + ".dat");
        byte[] data = Cache.getIndexes()[3].getArchive(interfaceId).getData();
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(data);
            fos.close();
            //message
            JOptionPane.showMessageDialog(interfaceViewportScrollPane, interfaceId + " dumped to data/export/" + interfaceId + ".dat");
        }
    }

    /**
     * Packs an interface from the given file path.
     *
     * @param path the file path of the data to pack.
     */
    public void packInterface(String path) {
        int archiveId = ComponentDefinition.getInterfaceDefinitionsSize(Cache);
        ComponentDefinition defaultButton = ComponentDefinition.getInterfaceComponent(6, 19);
        defaultButton.parentId = -1;

        Cache.getIndexes()[3].putFile(archiveId, 0, defaultButton.encode());
        Cache.getIndexes()[3].getArchive(archiveId).getData();

        drawTree(archiveId);
    }

    /**
     * Returns components in the correct order, including containers and children.
     *
     * @param interfaceId the interface ID.
     * @return sorted list of components.
     */
    public ArrayList<ComponentDefinition> getOrderedComps(int interfaceId) {
        ArrayList<ComponentDefinition> comps = new ArrayList<>();
        ArrayList<ComponentDefinition> containers = ComponentDefinition.getInterfaceContainers(interfaceId);
        ComponentDefinition[] allComps = ComponentDefinition.getInterface(interfaceId);

        if (allComps == null) return comps;

        // Add containers and their children to the list
        for (ComponentDefinition comp : containers) {
            if (!comps.contains(comp)) comps.add(comp);
            comps.addAll(ComponentDefinition.getChildrenByParent(interfaceId, comp.ihash));
        }

        // Add all components that don't have a parent
        for (ComponentDefinition comp : allComps) {
            if (comp != null && !comps.contains(comp)) {
                ComponentPosition.setValues(comp);
                comps.add(comp);
            }
        }
        return comps;
    }

    /**
     * Draws the component tree for a given interface ID.
     *
     * @param id the interface ID to draw.
     */
    public void drawTree(int id) {
        System.out.println("Drawing component tree ");
        JTree tree = new JTree(createInterfaceTree(id));
        tree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
            try {
                if (selectedNode.getUserObject() != null) {
                    int id1 = Integer.parseInt(selectedNode.getUserObject().toString().replaceAll("Component ", ""));
                    selectedComp = id1;
                    setValues(currentInterface, id1);
                    repaint();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                Main.log("Iface Tool", "Error selecting component, error->" + ex);
                /* some roots aren't a root , better catch them instead of spamming console*/

            }

        });
        componentScrollpane.setViewportView(tree);
    }

    /**
     * default components
     *
     * @param id
     */
    public void addDefaultComponent(int id) {
        switch (comboBox.getSelectedIndex()) {
            case 0://default close button (with hover)
                ComponentDefinition defaultCloseButton = ComponentDefinition.getInterfaceComponent(6, 36);
                defaultCloseButton.parentId = -1;
                Cache.getIndexes()[3].putFile(currentInterface, ComponentDefinition.getInterfaceDefinitionsComponentsSize(Cache, currentInterface), defaultCloseButton.encode());
                ComponentDefinition.getInterface(currentInterface); //since we need to reload the array
                drawTree(currentInterface);
                break;
            case 1: //normal hover button
                ComponentDefinition container = ComponentDefinition.getInterfaceComponent(506, 1);
                ComponentDefinition text = ComponentDefinition.getInterfaceComponent(506, 2);
                Cache.getIndexes()[3].putFile(currentInterface, ComponentDefinition.getInterfaceDefinitionsComponentsSize(Cache, currentInterface), container.encode());
                Cache.getIndexes()[3].putFile(currentInterface, ComponentDefinition.getInterfaceDefinitionsComponentsSize(Cache, currentInterface), text.encode());
                ComponentDefinition.getInterface(currentInterface); //since we need to reload the array
                ComponentDefinition n = ComponentDefinition.getInterfaceComponent(currentInterface, ComponentDefinition.getInterfaceDefinitionsComponentsSize(Cache, currentInterface) - 2);
                ComponentDefinition xd = ComponentDefinition.getInterfaceComponent(currentInterface, ComponentDefinition.getInterfaceDefinitionsComponentsSize(Cache, currentInterface) - 1);
                xd.parentId = n.ihash;
                Cache.getIndexes()[3].putFile(currentInterface, ComponentDefinition.getInterfaceDefinitionsComponentsSize(Cache, currentInterface) - 1, xd.encode());
                ComponentDefinition.getInterface(currentInterface); //since we need to reload the array
                drawTree(currentInterface);
                break;
            case 2: //basic starter interface
                ComponentDefinition basic = ComponentDefinition.getInterfaceComponent(6, 0);
                int place = ComponentDefinition.getInterfaceDefinitionsComponentsSize(Cache, currentInterface);
                Cache.getIndexes()[3].putFile(currentInterface, place, basic.encode());
                for (ComponentDefinition comp : ComponentDefinition.getChildrenByParent(6, ComponentDefinition.getInterfaceComponent(6, 0).ihash)) {
                    if (comp.text.toLowerCase().contains("brimhaven")) comp.text = "";
                    comp.parentId++;
                    Cache.getIndexes()[3].putFile(currentInterface, ComponentDefinition.getInterfaceDefinitionsComponentsSize(Cache, currentInterface), comp.encode());
                }
                ComponentDefinition.getInterface(currentInterface); //since we need to reload the array
                drawTree(currentInterface);
                break;
            case 3:
                ComponentDefinition hover = ComponentDefinition.getInterfaceComponent(6, 36);
                hover.parentId = -1;
                hover.onMouseRepeat = null;
                hover.onOptionClick = null;
                hover.spriteId = 0;
                hover.onMouseHoverScript[2] = 1;
                hover.onMouseLeaveScript[2] = 0;
                Cache.getIndexes()[3].putFile(currentInterface, ComponentDefinition.getInterfaceDefinitionsComponentsSize(Cache, currentInterface), hover.encode());
                ComponentDefinition.getInterface(currentInterface); //since we need to reload the array
                drawTree(currentInterface);
                break;
            case 4:
                ComponentDefinition popupButton = ComponentDefinition.getInterfaceComponent(762, 33);
                ComponentDefinition cont = ComponentDefinition.getInterfaceComponent(762, 119);
                cont.parentId = -1;
                popupButton.parentId = -1;
                int place2 = ComponentDefinition.getInterfaceDefinitionsComponentsSize(Cache, currentInterface);
                Cache.getIndexes()[3].putFile(currentInterface, place2, cont.encode());
                popupButton.onMouseRepeat[3] = "Click here to change your preset settings.";
                popupButton.onMouseLeaveScript[2] = ComponentDefinition.getInterfaceComponent(currentInterface, place2).ihash;
                popupButton.onMouseRepeat[2] = ComponentDefinition.getInterfaceComponent(currentInterface, place2).ihash;
                Cache.getIndexes()[3].putFile(currentInterface, ComponentDefinition.getInterfaceDefinitionsComponentsSize(Cache, currentInterface), popupButton.encode());
                ComponentDefinition.getInterface(currentInterface); //since we need to reload the array
                drawTree(currentInterface);
                break;
        }
    }

    public void drawInterfaceComponents(int interfaceId, boolean showContainers, boolean showHidden, boolean showModels, boolean showRealFonts) {
        /* graphic part*/
        System.out.println("Drawing preview interface");
        result = new BufferedImage(viewportPanel.getWidth(), viewportPanel.getHeight(), BufferedImage.TYPE_INT_RGB);
        /**
         * drawing
         **/
        Graphics g = result.getGraphics();

        drawInterfaceToGraphics(g, interfaceId, showContainers, showHidden, showModels, showRealFonts);
    }


    private void drawInterfaceToGraphics(Graphics g, int interfaceId, boolean showContainers, boolean showHidden, boolean showModels, boolean showRealFonts) {
        // Fill the background with the designated color
        g.setColor(IfaceConstants.BG_FILL_COLOR);
        g.fillRect(0, 0, IfaceConstants.VIEWPORT_WIDTH, IfaceConstants.VIEWPORT_HEIGHT);

        // Get ordered components for the given interface ID
        List<ComponentDefinition> orderedComponents = this.getOrderedComps(interfaceId);
        if (orderedComponents == null) {
            System.out.println("Ordered components list is null.");
            return;
        }

        // Iterate through all components
        for (ComponentDefinition component : orderedComponents) {
            // Set the component position values
            ComponentPosition.setValues(component);

            // Skip hidden components if not required to show them
            if (component == null || (InterfaceUtils.isHidden(component) && !showHidden)) {
                continue;
            }

            // Retrieve component dimensions and position
            int width = component.width;
            int height = component.height;
            int x = ComponentDefinition.getX(component, interfaceId);
            int y = ComponentDefinition.getY(component, interfaceId);

            // Handle sprite components
            if (component.type == ComponentConstants.SPRITE && component.spriteId > -1) {
                BufferedImage sprite = getScaledSprite(component);
                if (sprite != null) {
                    // Draw sprite with appropriate flips
                    if (component.hFlip) sprite = ImageUtils.horizontalFlip(sprite);
                    if (component.vFlip) sprite = ImageUtils.verticalFlip(sprite);
                    g.drawImage(sprite, x, y, null);
                }
            }

            // Draw rectangles for figure components
            if (component.type == ComponentConstants.FIGURE) {
                drawRectangle(g, component, x, y);
            }

            // Handle model drawing
            if (component.type == ComponentConstants.MODEL && showModels) {
                drawModel(g, component, x, y);
            }

            // Handle containers
            if (component.type == ComponentConstants.CONTAINER) {
                drawContainer(g, component, x, y, showContainers);
            }

            // Handle text components
            if (component.type == ComponentConstants.TEXT) {
                drawText(g, component, x, y, showRealFonts);
            }
        }
        repaint();
    }

    private BufferedImage getScaledSprite(ComponentDefinition component) {
        // Fetch the sprite from the cache using the spriteId
        BufferedImage sprite = SpriteLoader.getSprite(component.spriteId);

        if (sprite == null) {
            // If the sprite is not found, trigger a load (or refresh) from the cache.
            reloadSpritesFromCache();
            sprite = SpriteLoader.getSprite(component.spriteId);
            if (sprite == null) {
                return null;  // Return null if the sprite still isn't found
            }
        }

        // Scale the sprite to fit the component's size
        return ImageUtils.imageToBufferedImage(sprite.getScaledInstance(component.width, component.height, Image.SCALE_SMOOTH));
    }

    /**
     * Reload or refresh sprites from the cache.
     * This could be useful when sprites are dumped and need to be refreshed from the cache after the dump.
     */
    private void reloadSpritesFromCache() {
        SpriteLoader.INSTANCE.getSpriteCache();
    }

    private void drawRectangle(Graphics g, ComponentDefinition component, int x, int y) {
        // Set the rectangle's color
        g.setColor(component.color == 0 ? Color.black : new Color(component.color));
        // Draw the rectangle
        g.drawRect(x, y, component.width, component.height);
        if (component.filled) {
            g.fillRect(x, y, component.width, component.height);
        }
    }

    private void drawModel(Graphics g, ComponentDefinition component, int x, int y) {
        // Draw a blue rectangle for the model
        g.setColor(Color.BLUE);
        g.drawRect(x, y, component.width, component.height);
    }

    private void drawContainer(Graphics g, ComponentDefinition component, int x, int y, boolean showContainers) {
        // If the container is a scrollbar, use a specific sprite
        if (ContainerHelper.isScrollBar(component)) {
            BufferedImage sprite = loadImage("data/export/scriptsprites/scrollbar.jpg", component.width, component.height);
            if (sprite != null) {
                g.drawImage(sprite, x, y, null);
            }
        } else if (ContainerHelper.isButton(component)) {
            // Load and draw button sprite
            BufferedImage sprite = loadImage("data/scriptsprites/button.png", component.width, component.height);
            if (sprite != null) {
                g.drawImage(sprite, x, y, null);
            }
        } else if (showContainers) {
            // Draw container borders with different colors
            g.setColor(component.parentId > 0 ? Color.RED : Color.GREEN);
            g.drawRect(x, y, component.width, component.height);
        }
    }

    private BufferedImage loadImage(String path, int width, int height) {
        try {
            BufferedImage image = ImageIO.read(new File(path));
            return ImageUtils.resize(image, width, height);
        } catch (IOException e) {
            System.out.println(path + " not found");
            return null;
        }
    }

    private void drawText(Graphics g, ComponentDefinition component, int x, int y, boolean showRealFonts) {
        FontMetrics fm = g.getFontMetrics();
        Rectangle2D rect = fm.getStringBounds(component.text, g);

        // Set the color for the text
        g.setColor(new Color(component.color));

        // Set the font and draw text
        g.setFont(new Font("Helvetica", Font.PLAIN, 11));
        if (component.parentId == -1) {
            g.drawString(component.text, x + (component.width / 2 - (int) rect.getWidth() / 2), y + (component.height / 2 + (int) rect.getHeight() / 2));
        } else {
            ComponentDefinition parent = InterfaceUtils.getParent(component.parentId);
            if (parent != null) {
                // Center text for button-like components
                if (component.baseWidth == 0 && component.baseHeight == 0) {
                    drawCenteredText(g, component.text, parent);
                } else {
                    // Position text within its container
                    g.drawString(component.text, x + (component.width / 2 - (int) rect.getWidth() / 2), y + (component.height / 2 + (int) rect.getHeight() / 2));
                }
            }
        }

        // If showing real fonts, use the special font decoding
        if (showRealFonts) {
            drawRealFontText(g, component, x, y, rect);
        }
    }

    private void drawCenteredText(Graphics g, String text, ComponentDefinition parent) {
        FontMetrics metrics = g.getFontMetrics(new Font("Helvetica", Font.PLAIN, 11));
        int x2 = parent.positionX + (parent.width - metrics.stringWidth(text)) / 2;
        int y2 = parent.positionY + ((parent.height - metrics.getHeight()) / 2) + metrics.getAscent();
        g.drawString(text, x2, y2);
    }

    private void drawRealFontText(Graphics g, ComponentDefinition component, int x, int y, Rectangle2D rect) {
        int startX = x + (component.width / 2 - (int) rect.getWidth() / 2);
        for (BufferedImage im : FontDecoding.getTextArray(component)) {
            g.drawImage(ImageUtils.colorImage(im, new Color(component.color)), startX, y + (component.height / 2 + (int) rect.getHeight() / 2), null);
            startX += im.getWidth() / 2;
        }
    }

}

