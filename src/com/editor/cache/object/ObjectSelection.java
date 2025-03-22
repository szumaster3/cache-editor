package com.editor.cache.object;

import com.alex.defs.objects.ObjectDefinitions;
import com.alex.filestore.Store;
import com.alex.util.Utils;
import console.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

/**
 * The type Object selection.
 */
public class ObjectSelection extends JFrame {
    private static final long serialVersionUID = 3916958457638397356L;
    public static Store STORE;
    private JButton addButton;
    private JButton duplicateButton;
    private JButton editButton;
    private DefaultListModel<ObjectDefinitions> objectsListModel;
    private JList<ObjectDefinitions> objectsList;
    private JMenu jMenu1;
    private JMenuBar jMenuBar1;
    private JMenuItem exitButton;
    private JButton deleteButton;

    /**
     * Instantiates a new Object selection.
     *
     * @param cache the cache
     * @throws IOException the io exception
     */
    public ObjectSelection(String cache) throws IOException {
        STORE = new Store(cache);
        setTitle("Object Selection");
        setResizable(false);
        setDefaultCloseOperation(1);
        setLocationRelativeTo(null);
        initComponents();
    }

    /**
     * Instantiates a new Object selection.
     */
    public ObjectSelection() {
        initComponents();
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     * @throws IOException the io exception
     */
    public static void main(String[] args) throws IOException {
        STORE = new Store("cache/", false);
        EventQueue.invokeLater(() -> new ObjectSelection().setVisible(true));
    }

    private void initComponents() {
        editButton = new JButton("Edit");
        addButton = new JButton("Add New");
        duplicateButton = new JButton("Duplicate");
        deleteButton = new JButton("Delete");
        jMenuBar1 = new JMenuBar();
        jMenu1 = new JMenu("File");
        exitButton = new JMenuItem("Close");

        this.objectsListModel = new DefaultListModel<>();
        this.objectsList = new JList<>(this.objectsListModel);
        objectsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        objectsList.setCellRenderer(new ObjectListCellRenderer());  // Ustawienie renderer'a
        JScrollPane jScrollPane1 = new JScrollPane(objectsList);

        editButton.addActionListener(e -> editObject());
        addButton.addActionListener(e -> addNewObject());
        duplicateButton.addActionListener(e -> duplicateObject());
        deleteButton.addActionListener(e -> deleteObject());

        exitButton.addActionListener(this::exitButtonActionPerformed);
        jMenu1.add(exitButton);
        jMenuBar1.add(jMenu1);
        setJMenuBar(jMenuBar1);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(editButton)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(addButton))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(duplicateButton)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(deleteButton)))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        )
        );

        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 279, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(editButton)
                                        .addComponent(addButton))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(duplicateButton)
                                        .addComponent(deleteButton))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        )
        );

        pack();
        addAllObjects();
    }

    private void exitButtonActionPerformed(ActionEvent evt) {
        dispose();
    }

    /**
     * Add all objects to the list
     */
    public void addAllObjects() {
        int id;
        int totalObjects = Utils.getObjectDefinitionsSize(STORE);
        for (id = 0; id < totalObjects; ++id) {
            this.addObjectDefs(ObjectDefinitions.getObjectDefinition(STORE, id));
        }

        Main.log("ObjectSelection", "All Objects Loaded");
    }

    /**
     * Add object definitions to the list model
     *
     * @param defs the object definition
     */
    public void addObjectDefs(final ObjectDefinitions defs) {
        EventQueue.invokeLater(() -> ObjectSelection.this.objectsListModel.addElement(defs));
    }

    /**
     * Update object definitions in the list model
     *
     * @param obj the object definition
     */
    public void updateObjectDefs(final ObjectDefinitions obj) {
        EventQueue.invokeLater(() -> {
            int index = objectsListModel.indexOf(obj);
            if (index == -1) {
                objectsListModel.addElement(obj);
            } else {
                objectsListModel.setElementAt(obj, index);
            }
        });
    }

    /**
     * Remove object definitions from the list model
     *
     * @param obj the object definition
     */
    public void removeObjectDefs(final ObjectDefinitions obj) {
        EventQueue.invokeLater(() -> objectsListModel.removeElement(obj));
    }

    private void editObject() {
        int selectedIndex = objectsList.getSelectedIndex();
        if (selectedIndex != -1) {
            ObjectDefinitions obj = objectsList.getSelectedValue();
            if (obj != null) {
                new ObjectEditor(this, obj);
            }
        }
    }

    private void addNewObject() {
        ObjectDefinitions obj = new ObjectDefinitions(ObjectSelection.STORE, getNewObjectID(), false);
        if (obj != null && obj.id != -1) {
            System.out.println("Adding new object with ID: " + obj.id);
            new ObjectEditor(this, obj).setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Failed to create a new object!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void duplicateObject() {
        ObjectDefinitions obj = objectsList.getSelectedValue();
        if (obj != null) {
            ObjectDefinitions clonedObj = (ObjectDefinitions) obj.clone();
            if (clonedObj != null) {
                clonedObj.id = getNewObjectID();
                if (clonedObj.id != -1) {
                    new ObjectEditor(this, clonedObj).setVisible(true);
                }
            }
        }
    }

    private void deleteObject() {
        ObjectDefinitions obj = objectsList.getSelectedValue();
        if (obj != null) {
            int result = JOptionPane.showConfirmDialog(this, "Do you really want to delete object " + obj.id + "?");
            if (result == JOptionPane.YES_OPTION) {
                System.out.println("Deleting object: " + obj.id);
                STORE.getIndexes()[16].removeFile(obj.getArchiveId(), obj.getFileId());
                removeObjectDefs(obj);
                Main.log("ObjectSelection", "Object " + obj.id + " removed.");
            }
        }
    }

    private int getNewObjectID() {
        return Utils.getObjectDefinitionsSize(STORE);
    }

    private class ObjectListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof ObjectDefinitions) {
                ObjectDefinitions defs = (ObjectDefinitions) value;
                setText(defs.id + " - " + defs.getName());
            }
            return c;
        }
    }
}
