package com.editor.cache.iface;

import com.alex.defs.interfaces.ComponentDefinition;
import com.alex.filestore.Store;
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
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class InterfaceEditor extends JFrame {
    public static Store STORE;
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
    private JTextField txt_widht = new JTextField();
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

    public InterfaceEditor(String cache) throws IOException {
        STORE = new Store(cache);
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

        JMenu mnNewMenu_1 = new JMenu("Informations");
        mnNewMenu_1.addActionListener(arg0 -> JOptionPane.showMessageDialog(interfaceViewportScrollPane, "Interface editor made by Shnek, Discord : Cara Shnek#6969 "));
        menuBar.add(mnNewMenu_1);

        JMenu mnAbout = new JMenu("Extra");
        menuBar.add(mnAbout);

        JMenuItem mntmDumpSprites = new JMenuItem("Export sprites");
        mntmDumpSprites.addActionListener(arg0 -> {
            progressMonitor = new ProgressMonitor(menuBar, "Dumping sprites", "", 0, STORE.getIndexes()[8].getLastArchiveId());
            progressMonitor.setProgress(1);
            SpriteDumper.dump(cache);
        });
        mnAbout.add(mntmDumpSprites);
        JMenuItem mntmPackInterface = getJMenuItem();
        mnAbout.add(mntmPackInterface);

        pack();
        revalidate();
    }

    @NotNull
    private JMenuItem getJMenuItem() {
        JMenuItem mntmPackInterface = new JMenuItem("Pack interface");
        mntmPackInterface.addActionListener(arg0 -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File("."));
            chooser.setDialogTitle("choosertitle");
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);

            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                packInterface(chooser.getSelectedFile().getPath());
                // System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
            } else {
                System.out.println("No Selection ");
            }


        });
        return mntmPackInterface;
    }

    private void constructWestPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        panel.setMinimumSize(new Dimension(IfaceConstants.LEFT_SCROLLPANE_WIDTH, IfaceConstants.DEFAULT_EDITOR_HEIGHT));
        panel.setMaximumSize(new Dimension(IfaceConstants.LEFT_SCROLLPANE_WIDTH, IfaceConstants.DEFAULT_EDITOR_HEIGHT));
        panel.setPreferredSize(new Dimension(IfaceConstants.LEFT_SCROLLPANE_WIDTH, IfaceConstants.DEFAULT_EDITOR_HEIGHT));

        JPanel searchPanel = new JPanel(new FlowLayout());
        txt_interId = new JTextField();
        txt_interId.setBounds(IfaceConstants.CONTENT_PADDING, IfaceConstants.CONTENT_PADDING, IfaceConstants.SEARCHBOX_WIDTH, IfaceConstants.SEARCHBOX_HEIGHT);
        txt_interId.setColumns(10);
        searchPanel.add(txt_interId);

        JButton btnFind = new JButton("find");
        btnFind.setBounds(IfaceConstants.CONTENT_PADDING * 2 + IfaceConstants.SEARCHBOX_WIDTH, IfaceConstants.CONTENT_PADDING, IfaceConstants.BUTTON_WIDTH, IfaceConstants.BUTTON_HEIGHT);
        btnFind.addActionListener(arg0 -> {
            int id = Integer.parseInt(txt_interId.getText());
            currentInterface = id;
            drawTree(id);
        });
        searchPanel.add(btnFind);
        panel.add(searchPanel, BorderLayout.NORTH);

        ifListScrollPane = new JScrollPane();
        ifListScrollPane.setPreferredSize(new Dimension(IfaceConstants.LEFT_SCROLLPANE_WIDTH, IfaceConstants.VIEWPORT_HEIGHT));
        ifListScrollPane.setBounds(10, 54, 193, 331);
        interface_list = new JList(populateList());
        ifListScrollPane.setViewportView(interface_list);
        interface_list.addListSelectionListener(evt -> {
            if (evt.getValueIsAdjusting()) return;
            int id = Integer.parseInt(interface_list.getSelectedValue().toString().replaceAll("Interface: ", ""));
            System.out.println("Interface " + id + " is selected.");
            currentInterface = id;
            viewportPanel.repaint();
            drawTree(id);
            cleanValues();
        });
        panel.add(ifListScrollPane, BorderLayout.CENTER);

        final JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem export = new JMenuItem("Export");
        popupMenu.add(export);
        interface_list.addMouseListener(new MouseAdapter() {

            public void mouseClicked(MouseEvent me) {
                if (SwingUtilities.isRightMouseButton(me)    // if right mouse button clicked
                        && !interface_list.isSelectionEmpty()            // and list selection is not empty
                        && interface_list.locationToIndex(me.getPoint()) // and clicked point is
                        == interface_list.getSelectedIndex()) {       //   inside selected item bounds
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

        JPanel moreButtonsPanel = new JPanel(new FlowLayout());
        moreButtonsPanel.setPreferredSize(new Dimension(IfaceConstants.LEFT_SCROLLPANE_WIDTH, IfaceConstants.DEFAULT_EDITOR_HEIGHT / 3));

        JButton addInterfaceButton = getJButton();
        moreButtonsPanel.add(addInterfaceButton);

        JButton deleteInterfaceButton = new JButton("Delete Selected");
        deleteInterfaceButton.addActionListener(arg0 -> {
            int option = JOptionPane.showConfirmDialog(this, "Are you sure that you want to remove interface " + currentInterface + " ?", "Inane warning", JOptionPane.YES_NO_OPTION);
            if (option == 0) {
                for (int i = 0; i < ComponentDefinition.getInterfaceDefinitionsComponentsSize(STORE, currentInterface); i++) {
                    if (i == 0) {
                        addIndex0text((currentInterface));
                    } else STORE.getIndexes()[3].removeFile((currentInterface), i);
                }
                ComponentDefinition.icomponentsdefs = new ComponentDefinition[ComponentDefinition.getInterfaceDefinitionsSize(STORE)][];
                drawTree(currentInterface);
            }
        });
        deleteInterfaceButton.setPreferredSize(new Dimension(IfaceConstants.LEFT_SCROLLPANE_WIDTH, IfaceConstants.BUTTON_HEIGHT));
        moreButtonsPanel.add(deleteInterfaceButton);

        JPanel premadeComponentsPanel = new JPanel(new FlowLayout());
        premadeComponentsPanel.setBorder(new TitledBorder(null, "Premade components", TitledBorder.LEADING, TitledBorder.TOP, null, null));
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

        JButton saveButton = new JButton("Save");
        saveButton.setPreferredSize(new Dimension(IfaceConstants.LEFT_SCROLLPANE_WIDTH, IfaceConstants.BUTTON_HEIGHT));
        saveButton.addActionListener(arg0 -> {
            if (currentInterface != -1 && selectedComp != -1) {
                saveInterface(currentInterface, selectedComp);
                drawTree(currentInterface);
                setValues(currentInterface, selectedComp);
            } else {
                JOptionPane.showMessageDialog(interfaceViewportScrollPane, "Please selected a component & interface before saving it.");
            }
        });
        moreButtonsPanel.add(saveButton);

        panel.add(moreButtonsPanel, BorderLayout.SOUTH);

        getContentPane().add(panel, BorderLayout.WEST);
    }

    @NotNull
    private JButton getJButton() {
        JButton addInterfaceButton = new JButton("Add Interface");
        addInterfaceButton.addActionListener(arg0 -> {
            ComponentDefinition defaultButton = ComponentDefinition.getInterfaceComponent(6, 36);
            defaultButton.basePositionX = 0;
            defaultButton.basePositionY = 0;
            defaultButton.parentId = -1;
            STORE.getIndexes()[3].putFile(ComponentDefinition.getInterfaceDefinitionsSize(STORE), 0, defaultButton.encode());
            ComponentDefinition.icomponentsdefs = new ComponentDefinition[ComponentDefinition.getInterfaceDefinitionsSize(STORE)][];
            JList list = new JList(populateList());
            list.addListSelectionListener(evt -> {
                if (evt.getValueIsAdjusting()) return;
                int id = Integer.parseInt(list.getSelectedValue().toString().replaceAll("Interface: ", ""));
                currentInterface = id;
                drawTree(id);
            });
            ifListScrollPane.setViewportView(list);
        });
        addInterfaceButton.setPreferredSize(new Dimension(IfaceConstants.LEFT_SCROLLPANE_WIDTH, IfaceConstants.BUTTON_HEIGHT));
        return addInterfaceButton;
    }

    private void constructEastPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(IfaceConstants.RIGHT_SCROLLPANE_WIDTH, IfaceConstants.DEFAULT_EDITOR_HEIGHT));
        panel.setMaximumSize(new Dimension(IfaceConstants.RIGHT_SCROLLPANE_WIDTH, IfaceConstants.DEFAULT_EDITOR_HEIGHT));
        panel.setMinimumSize(new Dimension(IfaceConstants.RIGHT_SCROLLPANE_WIDTH, IfaceConstants.DEFAULT_EDITOR_HEIGHT));

        /**
         * scrollpane for the jtree
         */
        componentScrollpane = new JScrollPane();
        componentScrollpane.setPreferredSize(new Dimension(IfaceConstants.RIGHT_SCROLLPANE_WIDTH, IfaceConstants.VIEWPORT_HEIGHT));
        //jtree itself
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

        JPanel moreButtonsPanel = new JPanel(new FlowLayout());
        moreButtonsPanel.setPreferredSize(new Dimension(IfaceConstants.RIGHT_SCROLLPANE_WIDTH, IfaceConstants.DEFAULT_EDITOR_HEIGHT / 3));

        JPanel componentButtons = getJPanel();
        moreButtonsPanel.add(componentButtons);

        JPanel settingsPanel = new JPanel(new FlowLayout());
        settingsPanel.setBorder(new TitledBorder(null, "Settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        settingsPanel.setPreferredSize(new Dimension(IfaceConstants.RIGHT_SCROLLPANE_WIDTH, IfaceConstants.DEFAULT_EDITOR_HEIGHT / 4));

        Dimension checkBoxSize = new Dimension(IfaceConstants.RIGHT_SCROLLPANE_WIDTH - 10, IfaceConstants.BUTTON_HEIGHT_SMALL);

        chckbxShowContainers = new JCheckBox("Show Containers");
        chckbxShowContainers.setSelected(true);
        chckbxShowContainers.setPreferredSize(checkBoxSize);
        chckbxShowContainers.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                drawInterfaceComponents(currentInterface, true, chckbxShowHiddenComps.isSelected(), chckbxShowRectangles.isSelected(), chckbxRealFonttesting.isSelected());
            } else {
                drawInterfaceComponents(currentInterface, false, chckbxShowHiddenComps.isSelected(), chckbxShowRectangles.isSelected(), chckbxRealFonttesting.isSelected());
            }
        });
        settingsPanel.add(chckbxShowContainers);

        chckbxShowRectangles = new JCheckBox("Show Models");
        chckbxShowRectangles.setSelected(true);
        chckbxShowRectangles.setPreferredSize(checkBoxSize);
        chckbxShowRectangles.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                drawInterfaceComponents(currentInterface, chckbxShowContainers.isSelected(), chckbxShowHiddenComps.isSelected(), true, chckbxRealFonttesting.isSelected());
            } else {
                drawInterfaceComponents(currentInterface, chckbxShowContainers.isSelected(), chckbxShowHiddenComps.isSelected(), false, chckbxRealFonttesting.isSelected());
            }
        });
        settingsPanel.add(chckbxShowRectangles);

        chckbxShowHiddenComps = new JCheckBox("Show Hidden");
        chckbxShowHiddenComps.setPreferredSize(checkBoxSize);
        settingsPanel.add(chckbxShowHiddenComps);

        chckbxRealFonttesting = new JCheckBox("Draw Real Font");
        chckbxRealFonttesting.setPreferredSize(checkBoxSize);
        chckbxRealFonttesting.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                drawTree(currentInterface);
                drawInterfaceComponents(currentInterface, chckbxShowContainers.isSelected(), chckbxShowHiddenComps.isSelected(), chckbxShowRectangles.isSelected(), chckbxRealFonttesting.isSelected());
            }
        });
        chckbxRealFonttesting.setBounds(6, 107, 193, 25);
        settingsPanel.add(chckbxRealFonttesting);
        moreButtonsPanel.add(settingsPanel);
        panel.add(moreButtonsPanel, BorderLayout.SOUTH);

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

        JButton btnDelete = new JButton("Delete");
        btnDelete.setPreferredSize(buttonSize);
        btnDelete.setToolTipText("Deletes the selected component");
        btnDelete.addActionListener(e -> {
            if (selectedComp <= 0) {
                JOptionPane.showMessageDialog(interfaceViewportScrollPane, "Please select an component first.");
            } else {
                ComponentDefinition c = ComponentDefinition.getInterfaceComponent(currentInterface, selectedComp);
                String message = (c.type == ComponentConstants.CONTAINER ? "Are you sure that you want to remove component " + selectedComp + " from interface " + currentInterface + " ? NOTE: this component is a container, childs will be removed aswell." : "Are you sure that you want to remove  component " + selectedComp + " from interface " + currentInterface + " ?");
                int option = JOptionPane.showConfirmDialog(this, message, "Inane warning", JOptionPane.YES_NO_OPTION);
                if (option == 0) {
                    try {
                        STORE.getIndexes()[3].removeFile(currentInterface, selectedComp);
                        STORE.getIndexes()[3].resetCachedFiles();
                        STORE.getIndexes()[3].rewriteTable();
                    } finally {
                        drawTree(currentInterface);
                    }
                }
            }
        });
        componentButtons.add(btnDelete);
        return componentButtons;
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

        JButton btnAddText = new JButton("Add Text");
        btnAddText.setPreferredSize(buttonSize);
        btnAddText.addActionListener(e -> {
            if (currentInterface <= 0) return;
            ComponentDefinition comp = ComponentDefinition.getInterfaceComponent(4, 5);
            comp.basePositionX = 0;
            comp.basePositionY = 0;
            comp.parentId = -1;
            comp.text = "Hallo world";
            comp.ihash = ComponentDefinition.getInterfaceDefinitionsComponentsSize(STORE, currentInterface) + (currentInterface << 16);
            STORE.getIndexes()[3].putFile(currentInterface, ComponentDefinition.getInterfaceDefinitionsComponentsSize(STORE, currentInterface), comp.encode());
            ComponentDefinition.getInterface(currentInterface);
            drawTree(currentInterface);

        });
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
            STORE.getIndexes()[3].putFile(currentInterface, ComponentDefinition.getInterfaceDefinitionsComponentsSize(STORE, currentInterface), comp.encode());
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
            comp.ihash = ComponentDefinition.getInterfaceDefinitionsComponentsSize(STORE, currentInterface) + (currentInterface << 16);
            STORE.getIndexes()[3].putFile(currentInterface, ComponentDefinition.getInterfaceDefinitionsComponentsSize(STORE, currentInterface), comp.encode());
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
            comp.ihash = ComponentDefinition.getInterfaceDefinitionsComponentsSize(STORE, currentInterface) + (currentInterface << 16);
            STORE.getIndexes()[3].putFile(currentInterface, ComponentDefinition.getInterfaceDefinitionsComponentsSize(STORE, currentInterface), comp.encode());
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
            comp.ihash = ComponentDefinition.getInterfaceDefinitionsComponentsSize(STORE, currentInterface) + (currentInterface << 16);
            STORE.getIndexes()[3].putFile(currentInterface, ComponentDefinition.getInterfaceDefinitionsComponentsSize(STORE, currentInterface), comp.encode());
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
        generalTab.add(createInfoFieldPanel("Width", txt_widht));
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
                InterfaceEditor frame = new InterfaceEditor(STORE.toString());
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private ComponentDefinition selectedComponent;

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
        this.txt_widht.setText(comp.baseWidth + "");
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
     * saves the interface
     *
     * @param inter
     * @param comp
     */
    public void saveInterface(int inter, int comp) {
        ComponentDefinition changedComponent = ComponentDefinition.getInterfaceComponent(inter, comp);
        changedComponent.basePositionX = Integer.parseInt(txt_x.getText());
        changedComponent.basePositionY = Integer.parseInt(txt_y.getText());
        changedComponent.baseHeight = Integer.parseInt(txt_height.getText());
        changedComponent.baseWidth = Integer.parseInt(this.txt_widht.getText());
        changedComponent.parentId = Integer.parseInt(this.txt_parent.getText());
        changedComponent.color = Integer.parseInt(this.txt_color.getText());
        changedComponent.aspectXType = (byte) Integer.parseInt(this.txt_modex.getText());
        changedComponent.aspectYType = (byte) Integer.parseInt(this.txt_positionmodeY.getText());
        if (changedComponent.type == ComponentConstants.SPRITE) {
            changedComponent.spriteId = Integer.parseInt(this.txt_sprite.getText());
        }
		/*
		 * this.txt_scrollX.setText(comp.layerHeight+"");
			this.txt_scrollY.setText(comp.layerWidth+"");
		 */
        changedComponent.layerHeight = Integer.parseInt(this.txt_scrollX.getText());
        changedComponent.layerWidth = Integer.parseInt(this.txt_scrollY.getText());
        changedComponent.aspectXType = Byte.parseByte(this.txt_modex.getText());
        changedComponent.aspectYType = Byte.parseByte(this.txt_positionmodeY.getText());
        changedComponent.text = this.txt_text.getText();
        if (!this.txt_font.getText().isEmpty()) changedComponent.fontId = Integer.parseInt(this.txt_font.getText());
        if (!this.txt_animationId.getText().isEmpty())
            changedComponent.animationId = Integer.parseInt(this.txt_animationId.getText());
        changedComponent.hidden = chckbxHidden.isSelected();
        changedComponent.hFlip = this.chckbxHorizontalFlip.isSelected();
        changedComponent.vFlip = this.chckbxVerticalFlip.isSelected();
        changedComponent.repeat_ = changedComponent.repeat_;//this.chckbxRepeat.isSelected();
        changedComponent.modelId = Integer.parseInt(this.txt_model.getText());
        changedComponent.aspectHeightType = (byte) Integer.parseInt(this.txt_modeHeight.getText());
        changedComponent.aspectWidthType = (byte) Integer.parseInt(this.txt_widthMode.getText());
        changedComponent.transparency = Integer.parseInt(this.txt_trans.getText());
        changedComponent.filled = this.chckbxFilled.isSelected();
        changedComponent.textHorizontalAli = Integer.parseInt(this.txt_xali.getText());
        changedComponent.textVerticalAli = Integer.parseInt(this.txt_yali.getText());
        /**
         * saving texts
         * TODO more options
         */
        if (!this.txt_leftclick.getText().isEmpty()) {
            if (changedComponent.rightclickOptions == null) changedComponent.rightclickOptions = new String[5];
            changedComponent.optionMask = ComponentConstants.CLICK_MASK;
            changedComponent.rightclickOptions[0] = this.txt_leftclick.getText();
        }
        /**
         * saving scripts
         */
        changedComponent.onMouseRepeat = InterfaceUtils.getScriptArray(this.txt_onMouseRepeat.getText());
        changedComponent.onMouseHoverScript = InterfaceUtils.getScriptArray(this.txt_fullonhover.getText());
        changedComponent.onMouseLeaveScript = InterfaceUtils.getScriptArray(this.txt_mouseLeave.getText());
        changedComponent.onOptionClick = InterfaceUtils.getScriptArray(this.txt_onOptionClick.getText());
        changedComponent.onStatTransmit = InterfaceUtils.getScriptArray(this.txt_onStatTransmit.getText());
        changedComponent.onUse = InterfaceUtils.getScriptArray(this.txt_onUse.getText());
        changedComponent.onVarpTransmit = InterfaceUtils.getScriptArray(this.txt_onVarpTransmit.getText());
        changedComponent.onInvTransmit = InterfaceUtils.getScriptArray(this.txt_onInvTransmit.getText());
        changedComponent.onTimer = InterfaceUtils.getScriptArray(this.txt_onTimer.getText());
        changedComponent.onLoadScript = InterfaceUtils.getScriptArray(this.txt_onload.getText());
        changedComponent.onUseWith = InterfaceUtils.getScriptArray(this.txt_onUseWith.getText());
        changedComponent.varpTriggers = InterfaceUtils.getConfigArray(this.txt_varpTriggers.getText());
        //message
        //JOptionPane.showMessageDialog(scrollPane_2, "Component has been succesfully saved.");
        //saves it
        System.out.println("Saving component " + comp + " from interface interface " + inter);
        STORE.getIndexes()[3].putFile(inter, comp, changedComponent.encode());
    }

    /**
     * Fills the list with interface names.
     *
     * @return DefaultListModel containing interface names.
     */
    public DefaultListModel populateList() {
        System.out.println("Populating interface list");

        DefaultListModel listModel = new DefaultListModel();

        if (STORE == null) {
            System.out.println("STORE is null, cannot populate the interface list.");
            return listModel;
        }

        int interfaceCount = ComponentDefinition.getInterfaceDefinitionsSize(STORE);

        for (int i = 0; i < interfaceCount; i++) {
            try {
                ComponentDefinition[] interfaceDefs = ComponentDefinition.getInterface(i, false, STORE);

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
     * TODO rewrite this
     * fills the jtree
     *
     * @param interfaceId
     * @return
     */
    public DefaultTreeModel createInterfaceTree(int interfaceId) {
        DefaultMutableTreeNode inter = new DefaultMutableTreeNode("Interface " + interfaceId + "");
        //new stuff
        for (int i = 0; i < ComponentDefinition.getInterfaceDefinitionsComponentsSize(STORE, interfaceId); i++) {
            ComponentDefinition c = ComponentDefinition.getInterfaceComponent(interfaceId, i);
            if (c == null) {
                System.out.println("is null" + i);
                continue;
            }
            //System.out.println("here");
            //check for the base containers
            if (c.parentId == -1 && ComponentDefinition.hasChilds(interfaceId, c.ihash)) {
                DefaultMutableTreeNode child = new DefaultMutableTreeNode("Component " + c.componentId);
                ArrayList<ComponentDefinition> childs = ComponentDefinition.getChildsByParent(interfaceId, c.ihash);
                //loop throu the childs
                for (ComponentDefinition c2 : childs) {
                    //check if the child is a container , if so loop throu his childs etc..
                    if (ComponentDefinition.hasChilds(interfaceId, c2.ihash)) {
                        ArrayList<ComponentDefinition> childs2 = ComponentDefinition.getChildsByParent(interfaceId, c2.ihash);
                        DefaultMutableTreeNode container2 = new DefaultMutableTreeNode("Component " + c2.componentId);
                        for (ComponentDefinition c3 : childs2) {
                            if (ComponentDefinition.hasChilds(interfaceId, c3.ihash)) {
                                ArrayList<ComponentDefinition> childs3 = ComponentDefinition.getChildsByParent(interfaceId, c3.ihash);
                                DefaultMutableTreeNode container3 = new DefaultMutableTreeNode("Component " + c3.componentId);
                                for (ComponentDefinition c4 : childs3) {
                                    if (ComponentDefinition.hasChilds(interfaceId, c4.ihash)) {
                                        ArrayList<ComponentDefinition> child4 = ComponentDefinition.getChildsByParent(interfaceId, c4.ihash);
                                        DefaultMutableTreeNode container4 = new DefaultMutableTreeNode("Component " + c4.componentId);
                                        for (ComponentDefinition c5 : child4) {
                                            DefaultMutableTreeNode childs4 = new DefaultMutableTreeNode("Component " + c5.componentId);
                                            container4.add(childs4);
                                        }

                                        container3.add(container4);
                                    } else {
                                        DefaultMutableTreeNode child2 = new DefaultMutableTreeNode("Component " + c4.componentId);
                                        container3.add(child2);
                                    }
                                }
                                container2.add(container3);
                            } else {
                                DefaultMutableTreeNode child2 = new DefaultMutableTreeNode("Component " + c3.componentId);
                                container2.add(child2);
                            }
                        }
                        child.add(container2);
                    } else {
                        DefaultMutableTreeNode child2 = new DefaultMutableTreeNode("Component " + c2.componentId);
                        child.add(child2);
                    }
                }

                inter.add(child);
            } else if (c.parentId == -1 && !ComponentDefinition.hasChilds(interfaceId, c.ihash)) {
                DefaultMutableTreeNode child2 = new DefaultMutableTreeNode("Component " + c.componentId);
                inter.add(child2);
            }

        }

        return new DefaultTreeModel(inter);

    }

    public void addIndex0text(int interfaceId) {
        ComponentDefinition defaultButton = ComponentDefinition.getInterfaceComponent(6, 19);
        defaultButton.parentId = -1;
        STORE.getIndexes()[3].putFile(interfaceId, 0, defaultButton.encode());
    }

    /**
     * handles the component pasting
     */
    public void pasteComponent() {
        if (copiedComp == null) {
            JOptionPane.showMessageDialog(interfaceViewportScrollPane, "No component was selected to paste.");
            return;
        }
        if (copiedComp.type == ComponentConstants.CONTAINER) {
            int containerPlace = ComponentDefinition.getInterfaceDefinitionsComponentsSize(STORE, currentInterface);
            copiedComp.parentId = -1;
            STORE.getIndexes()[3].putFile(currentInterface, containerPlace, copiedComp.encode());
            ArrayList<ComponentDefinition> childs = ComponentDefinition.getChildsByParent(copiedComp.interfaceId, copiedComp.ihash);
            for (ComponentDefinition c : childs) {
                if (c.type == ComponentConstants.CONTAINER) { //TODO packing containers in containers

                } else
                    STORE.getIndexes()[3].putFile(currentInterface, ComponentDefinition.getInterfaceDefinitionsComponentsSize(STORE, currentInterface), c.encode());
            }
            ComponentDefinition.getInterface(currentInterface);
            int size = ComponentDefinition.getInterfaceDefinitionsComponentsSize(STORE, currentInterface);
            ComponentDefinition parent = ComponentDefinition.getInterfaceComponent(currentInterface, containerPlace);
            for (int i = size - childs.size() - 1; i < size; i++) {
                ComponentDefinition component = ComponentDefinition.getInterfaceComponent(currentInterface, i);
                if (component.type != 0) {
                    component.parentId = parent.ihash;
                    STORE.getIndexes()[3].putFile(currentInterface, i, component.encode());

                }
            }
            ComponentDefinition.getInterface(currentInterface);
            drawTree(currentInterface);
        } else {
            copiedComp.parentId = -1;
            STORE.getIndexes()[3].putFile(currentInterface, ComponentDefinition.getInterfaceDefinitionsComponentsSize(STORE, currentInterface), copiedComp.encode());
            ComponentDefinition.getInterface(currentInterface);
            drawTree(currentInterface);
        }
    }

    /**
     * export to interface to a basic file
     *
     * @param interfaceId
     * @throws IOException
     * @throws FileNotFoundException
     */
    public void exportInterface(int interfaceId) throws FileNotFoundException, IOException {
        File file = new File("data/export/" + interfaceId + ".dat");
        byte[] data = STORE.getIndexes()[3].getArchive(interfaceId).getData();
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(data);
            fos.close();
            //message
            JOptionPane.showMessageDialog(interfaceViewportScrollPane, interfaceId + " dumped to data/export/" + interfaceId + ".dat");
        }
    }

    /**
     * todo
     *
     * @param path
     */
    public void packInterface(String path) {
        byte[] data;
        try {
            data = Files.readAllBytes(new File(path).toPath());
            int archiveId = ComponentDefinition.getInterfaceDefinitionsSize(STORE);
            ComponentDefinition defaultButton = ComponentDefinition.getInterfaceComponent(6, 19);
            defaultButton.parentId = -1;
            STORE.getIndexes()[3].putFile(archiveId, 0, defaultButton.encode());
            //STORE.getIndexes()[3].getArchive(archiveId).s(data);
            this.drawTree(archiveId);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * returns them in the right order
     *
     * @param interfaceId
     * @return sorted comp list
     */
    public ArrayList<ComponentDefinition> getOrderedComps(int interfaceId) {
        ArrayList<ComponentDefinition> comps = new ArrayList();
        ArrayList<ComponentDefinition> containers = ComponentDefinition.getInterfaceContainers(interfaceId); //gets all the containers of an interface
        ComponentDefinition[] allComps = ComponentDefinition.getInterface(interfaceId);
        if (allComps == null) return null;
       /* for (ComponentDefinition c : allComps) {
            if (c == null)
                continue;
            if (c.parentId == -1)
                comps.add(c);
        }*/
        for (ComponentDefinition comp : containers) {
            if (!comps.contains(comp)) comps.add(comp); //add container itself
            for (ComponentDefinition child : ComponentDefinition.getChildsByParent(interfaceId, comp.ihash))
                comps.add(child); //Add childs
        }
        /**
         * adding all the comps who don't have a parent
         */
        for (int i = 0; i < allComps.length; i++) {
            if (allComps[i] == null) continue;
            ComponentPosition.setValues(allComps[i]);
            boolean found = false;
            for (ComponentDefinition c : comps) {
                if (c.componentId == allComps[i].componentId) found = true;
            }
            if (!found) comps.add(allComps[i]);
        }
        return comps;

    }

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
                STORE.getIndexes()[3].putFile(currentInterface, ComponentDefinition.getInterfaceDefinitionsComponentsSize(STORE, currentInterface), defaultCloseButton.encode());
                ComponentDefinition.getInterface(currentInterface); //since we need to reload the array
                drawTree(currentInterface);
                break;
            case 1: //normal hover button
                ComponentDefinition container = ComponentDefinition.getInterfaceComponent(506, 1);
                ComponentDefinition text = ComponentDefinition.getInterfaceComponent(506, 2);
                STORE.getIndexes()[3].putFile(currentInterface, ComponentDefinition.getInterfaceDefinitionsComponentsSize(STORE, currentInterface), container.encode());
                STORE.getIndexes()[3].putFile(currentInterface, ComponentDefinition.getInterfaceDefinitionsComponentsSize(STORE, currentInterface), text.encode());
                ComponentDefinition.getInterface(currentInterface); //since we need to reload the array
                ComponentDefinition n = ComponentDefinition.getInterfaceComponent(currentInterface, ComponentDefinition.getInterfaceDefinitionsComponentsSize(STORE, currentInterface) - 2);
                ComponentDefinition xd = ComponentDefinition.getInterfaceComponent(currentInterface, ComponentDefinition.getInterfaceDefinitionsComponentsSize(STORE, currentInterface) - 1);
                xd.parentId = n.ihash;
                STORE.getIndexes()[3].putFile(currentInterface, ComponentDefinition.getInterfaceDefinitionsComponentsSize(STORE, currentInterface) - 1, xd.encode());
                ComponentDefinition.getInterface(currentInterface); //since we need to reload the array
                drawTree(currentInterface);
                break;
            case 2: //basic starter interface
                ComponentDefinition basic = ComponentDefinition.getInterfaceComponent(6, 0);
                int place = ComponentDefinition.getInterfaceDefinitionsComponentsSize(STORE, currentInterface);
                STORE.getIndexes()[3].putFile(currentInterface, place, basic.encode());
                for (ComponentDefinition comp : ComponentDefinition.getChildsByParent(6, ComponentDefinition.getInterfaceComponent(6, 0).ihash)) {
                    if (comp.text.toLowerCase().contains("brimhaven")) comp.text = "";
                    comp.parentId++;
                    STORE.getIndexes()[3].putFile(currentInterface, ComponentDefinition.getInterfaceDefinitionsComponentsSize(STORE, currentInterface), comp.encode());
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
                STORE.getIndexes()[3].putFile(currentInterface, ComponentDefinition.getInterfaceDefinitionsComponentsSize(STORE, currentInterface), hover.encode());
                ComponentDefinition.getInterface(currentInterface); //since we need to reload the array
                drawTree(currentInterface);
                break;
            case 4:
                ComponentDefinition popupButton = ComponentDefinition.getInterfaceComponent(762, 33);
                ComponentDefinition cont = ComponentDefinition.getInterfaceComponent(762, 119);
                cont.parentId = -1;
                popupButton.parentId = -1;
                int place2 = ComponentDefinition.getInterfaceDefinitionsComponentsSize(STORE, currentInterface);
                STORE.getIndexes()[3].putFile(currentInterface, place2, cont.encode());
                popupButton.onMouseRepeat[3] = "Click here to change your preset settings.";
                popupButton.onMouseLeaveScript[2] = ComponentDefinition.getInterfaceComponent(currentInterface, place2).ihash;
                popupButton.onMouseRepeat[2] = ComponentDefinition.getInterfaceComponent(currentInterface, place2).ihash;
                STORE.getIndexes()[3].putFile(currentInterface, ComponentDefinition.getInterfaceDefinitionsComponentsSize(STORE, currentInterface), popupButton.encode());
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
        g.setColor(IfaceConstants.BG_FILL_COLOR);
        g.fillRect(0, 0, IfaceConstants.VIEWPORT_WIDTH, IfaceConstants.VIEWPORT_HEIGHT);
        /**
         * make sure you get them in the right order (containers)
         */
        List<ComponentDefinition> orderedComponents = this.getOrderedComps(interfaceId);
        if (orderedComponents == null) {
            System.out.println("is null");
            return;
        }
        for (ComponentDefinition component : orderedComponents) {
            ComponentPosition.setValues(component);
            /**
             * if hidden or no null
             */
            if (component == null || (InterfaceUtils.isHidden(component) && !showHidden)) {
                continue;
            }
            /* vars */
            int width = component.width;
            int height = component.height;
            int x = ComponentDefinition.getX(component, interfaceId);
            int y = ComponentDefinition.getY(component, interfaceId);
            ComponentDefinition parent = InterfaceUtils.getParent(component.parentId);//ComponentDefinition.getParent(component, interfaceId);
            /*if (parent == null)
                continue;*/
            /* setting correct values of the parent ofcourse*/
            if (parent != null) {
//                ComponentPosition.setValues(parent);
//
//                if (width > parent.width)
//                    width = parent.width;
//                if (height > parent.height)
//                    height = parent.height;
//                if (component.positionX < 0)
//                    component.positionX = 0;
//                if ((component.positionX + component.width) > parent.width)
//                    component.positionX = (parent.width - component.width);
//                if (component.positionY < 0)
//                    component.positionY = 0;
//                if ((component.positionY + component.height) > parent.height)
//                    component.positionY = (parent.height - component.height);
            }
            /**
             * checks if it's a sprite
             */
            if (component.type == ComponentConstants.SPRITE && component.spriteId > -1) {
                BufferedImage sprite = null;
                BufferedImage unscaled = SpriteLoader.getSprite(component.spriteId);

                if (unscaled == null) return;

                Image scaled = unscaled.getScaledInstance(component.width, component.height, Image.SCALE_SMOOTH);
                sprite = ImageUtils.imageToBufferedImage(scaled);

                /* horizontal flip*/
                if (component.hFlip) sprite = ImageUtils.horizontalFlip(sprite);
                /* vertical flip*/
                if (component.vFlip) sprite = ImageUtils.verticalFlip(sprite);
                g.drawImage(sprite, x, y, null);

            }

            /**
             * Rectangles
             */
            if (component.type == ComponentConstants.FIGURE) {
                if (component.color == 0) {
                    g.setColor(Color.black);
                } else {
                    /** Setting the color **/
                    Color color = new Color(component.color);
                    int red = color.getRed();
                    int green = color.getGreen();
                    int blue = color.getBlue();
                    g.setColor(new Color(red, green, blue));
                }
                g.drawRect(ComponentDefinition.getX(component, currentInterface), ComponentDefinition.getY(component, currentInterface), component.width, component.height);
                if (component.filled)
                    g.fillRect(ComponentDefinition.getX(component, currentInterface), ComponentDefinition.getY(component, currentInterface), component.width, component.height);
            }
            /**
             * models
             */
            if (component.type == ComponentConstants.MODEL && showModels) {
                g.setColor(Color.BLUE);
                g.drawRect(ComponentDefinition.getX(component, interfaceId), ComponentDefinition.getY(component, interfaceId), component.width, component.height);

            }
            /**
             * Containers
             *
             *
             * ComponentDefinition.getX(comp, interafece)
             */
            if (component.type == ComponentConstants.CONTAINER) {
                BufferedImage sprite = null;
                if (ContainerHelper.isScrollBar(component)) {
                    try {
                        sprite = ImageUtils.resize(ImageIO.read(new File("data/export/scriptsprites/scrollbar.jpg")), width, height);
                    } catch (IOException e) {
                        System.out.println("scrollbar.jpg not found");
                    }
                    g.drawImage(sprite, ComponentDefinition.getX(component, interfaceId), ComponentDefinition.getY(component, interfaceId), null);
                } else if (ContainerHelper.isButton(component)) {
                    try {
                        sprite = ImageUtils.resize(ImageIO.read(new File("data/scriptsprites/button.png")), width, height);
                    } catch (IOException e) {
                        System.out.println("button.png not found");
                    }
                    g.drawImage(sprite, ComponentDefinition.getX(component, interfaceId), ComponentDefinition.getY(component, interfaceId), null);
                } else if (showContainers) {
                    g.setColor(Color.RED);
                    if (component.parentId > 0) {
                        g.drawRect(ComponentDefinition.getX(component, interfaceId), ComponentDefinition.getY(component, interfaceId), component.width, component.height);
                    } else {
                        g.setColor(Color.green);
                        g.drawRect(component.positionX, component.positionY, component.width, component.height);

                    }
                }
            }
            /**
             * checks if it's text
             * TODO make it written by container some text doesn't get shown because it's under the other sprite
             */
            if (component.type == ComponentConstants.TEXT) {
                FontMetrics fm = g.getFontMetrics();
                Rectangle2D rect = fm.getStringBounds(component.text, g);
                /**
                 * color of the text
                 */
                Color color = new Color(component.color);
                int red = color.getRed();
                int green = color.getGreen();
                int blue = color.getBlue();
                g.setColor(new Color(red, green, blue));
                /** setting font **/
                g.setFont(new Font("Helvetica", 0, 11));
                if (component.parentId == -1) {
                    g.drawString(component.text, (int) (component.positionX + component.width / 2 - rect.getWidth() / 2), (int) (component.positionY + component.height / 2 + rect.getHeight() / 2));
                } else {
                    ComponentPosition.setValues(parent);

                    //text in buttons, to center it lol capypasta
                    if (component.baseWidth == 0 && component.baseHeight == 0) {
                        FontMetrics metrics = g.getFontMetrics(new Font("Helvetica", 0, 11));
                        // Determine the X coordinate for the text
                        int x2 = parent.positionX + (parent.width - metrics.stringWidth(component.text)) / 2;
                        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
                        int y2 = parent.positionY + ((parent.height - metrics.getHeight()) / 2) + metrics.getAscent();
                        // Set the font
                        g.setFont(new Font("Helvetica", 0, 11));
                        // Draw the String
                        g.drawString(component.text, x2, y2);
                    } else {
                        /*position*/
                        int positionX = ComponentDefinition.getX(component, interfaceId);
                        int positionY = ComponentDefinition.getY(component, interfaceId);
                        /*  not drAWING OUTSIDE THE CONTAINER*/
                        if (positionX > parent.width + parent.positionX) positionX = parent.width - component.width;
                        // if(positionY > parent.height + parent.positionY)
                        //positionY = parent.height - component.height;
                        g.drawString(component.text, (int) (positionX + component.width / 2 - rect.getWidth() / 2), (int) (positionY + component.height / 2 + rect.getHeight() / 2));

                    }
                }
                /**
                 * special
                 */
                if (component.text.contains("</u>")) {

                }
                /**
                 * testing
                 */
                if (showRealFonts) {
                    int positionX = ComponentDefinition.getX(component, interfaceId);
                    int positionY = ComponentDefinition.getY(component, interfaceId);
                    int startX = (int) (positionX + component.width / 2 - rect.getWidth() / 2);
                    for (BufferedImage im : FontDecoding.getTextArray(component)) {
                        g.drawImage(ImageUtils.colorImage(im, color), startX, (int) (positionY + component.height / 2 + rect.getHeight() / 2), null);
                        startX += im.getWidth() / 2;
                    }
                }
            }
        }


        //interfaceViewportScrollPane.setViewportView(jLabel);
        //this.getContentPane().add(jPanel);
    }
}

