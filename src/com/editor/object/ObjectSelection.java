package com.editor.object;

import com.alex.loaders.objects.ObjectDefinitions;
import com.alex.store.Store;
import com.alex.Utils;
import console.Main;
import com.editor.item.ItemSelection;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ObjectSelection extends JFrame {
    private static final long serialVersionUID = 4997610945859480240L;
    public static Store STORE;
    private JButton addButton;
    private JButton duplicateButton;
    private JButton editButton;
    private DefaultListModel objectListModel;
    private JList<ObjectDefinitions> objectList;
    private JMenu jMenu1;
    private JMenuBar jMenuBar1;
    private JMenuItem exitButton;
    private JButton deleteButton;

    public ObjectSelection(String cache) throws IOException {
        STORE = new Store(cache);
        this.setTitle("NPC Selection");
        this.setResizable(false);
        this.setDefaultCloseOperation(1);
        this.setLocationRelativeTo(null);
        this.initComponents();
    }

    public ObjectSelection() {
        this.initComponents();
    }

    public static void main(String[] args) throws IOException {
        STORE = new Store("cache/", false);
        EventQueue.invokeLater(() -> (new ObjectSelection()).setVisible(true));
    }

    private void initComponents() {
        this.editButton = new JButton();
        this.addButton = new JButton();
        this.duplicateButton = new JButton();
        this.deleteButton = new JButton();
        this.jMenuBar1 = new JMenuBar();
        this.jMenu1 = new JMenu();
        this.exitButton = new JMenuItem();
        this.setDefaultCloseOperation(1);
        this.objectListModel = new DefaultListModel<>();
        this.objectList = new JList<>(this.objectListModel);
        this.objectList.setSelectionMode(1); // single selection
        this.objectList.setLayoutOrientation(0);
        this.objectList.setVisibleRowCount(-1);
        JScrollPane jScrollPane1 = new JScrollPane(this.objectList);

        this.editButton.setText("Edit");
        this.editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ObjectDefinitions defs = (ObjectDefinitions) ObjectSelection.this.objectList.getSelectedValue();
                if (defs != null) {
                    new ObjectEditor(ObjectSelection.this, defs);
                }

            }
        });

        this.addButton.setText("Add New");
        this.addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ObjectDefinitions npc = new ObjectDefinitions(ItemSelection.STORE, ObjectSelection.this.getNewSceneryID(), false);
                if (npc != null && npc.id != -1) {
                    new ObjectEditor(ObjectSelection.this, npc).setVisible(true);
                }
            }
        });

        this.duplicateButton.setText("Duplicate");
        this.duplicateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ObjectDefinitions npc = objectList.getSelectedValue();
                if (npc != null) {
                    ObjectDefinitions clonedNpc = (ObjectDefinitions) npc.clone();
                    if (clonedNpc != null) {
                        clonedNpc.id = ObjectSelection.this.getNewSceneryID();
                        if (clonedNpc.id != -1) {
                            new ObjectEditor(ObjectSelection.this, clonedNpc).setVisible(true);
                        }
                    }
                }
            }
        });


        this.deleteButton.setText("Delete");
        this.deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ObjectDefinitions defs = (ObjectDefinitions) ObjectSelection.this.objectList.getSelectedValue();
                JFrame frame = new JFrame();
                int result = JOptionPane.showConfirmDialog(frame, "Do you really want to delete item " + defs.id);
                if (result == 0) {
                    if (defs == null) {
                        return;
                    }

                    ObjectSelection.STORE.getIndexes()[16].removeFile(defs.getArchiveId(), defs.getFileId());
                    ObjectSelection.this.removeNPCDefs(defs);
                    Main.log("ItemSelection", "Item " + defs.id + " removed.");
                }

            }
        });

        this.jMenu1.setText("File");
        this.exitButton.setText("Close");
        this.exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                ObjectSelection.this.exitButtonActionPerformed(evt);
            }
        });
        this.jMenu1.add(this.exitButton);
        this.jMenuBar1.add(this.jMenu1);
        this.setJMenuBar(this.jMenuBar1);

        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(Alignment.LEADING, false).addComponent(jScrollPane1, -2, 200, -2).addGroup(layout.createSequentialGroup().addComponent(this.editButton).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent(this.addButton))).addGap(0, 0, 32767)).addGroup(layout.createSequentialGroup().addComponent(this.duplicateButton).addPreferredGap(ComponentPlacement.RELATED, -1, 32767).addComponent(this.deleteButton))).addContainerGap(-1, 32767)));
        layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jScrollPane1, -2, 279, -2).addPreferredGap(ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(this.editButton).addComponent(this.addButton)).addPreferredGap(ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(Alignment.BASELINE).addComponent(this.duplicateButton).addComponent(this.deleteButton)).addContainerGap(-1, 32767)));

        this.pack();
        this.addAllObjects();
    }

    private void exitButtonActionPerformed(ActionEvent evt) {
        this.dispose();
    }

    public int getNewSceneryID() {
        return Utils.getObjectDefinitionsSize(STORE);
    }

    public void addAllObjects() {
        int id;
        int npcCount = Utils.getObjectDefinitionsSize(STORE);
        System.out.println("NPC Count: " + npcCount);
        for (id = 0; id < npcCount; ++id) {
            ObjectDefinitions npc = ObjectDefinitions.getObjectDefinition(STORE, id);
            if (npc != null) {
                this.addObjectDef(npc);
            } else {
                System.out.println("Error: NPC not loaded for ID " + id);
            }
        }
        Main.log("NPCSelection", "All NPCs Loaded");
    }

    public void addObjectDef(final ObjectDefinitions npc) {
        EventQueue.invokeLater(() -> objectListModel.addElement(npc));
    }

    public void updateObjectDef(final ObjectDefinitions npc) {
        EventQueue.invokeLater(() -> {
            int index = objectListModel.indexOf(npc);
            if (index == -1) {
                objectListModel.addElement(npc);
            } else {
                objectListModel.setElementAt(npc, index);
            }
        });
    }

    public void removeNPCDefs(final ObjectDefinitions npc) {
        EventQueue.invokeLater(() -> objectListModel.removeElement(npc));
    }
}
