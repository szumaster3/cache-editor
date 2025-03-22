package com.editor.model.view.mouse;

import com.editor.model.view.Main;

import javax.swing.*;

public final class EditorAbstractListModel extends AbstractListModel {
    private String[] field24;
    private String[] field26;

    public EditorAbstractListModel(Main var1, int var2, String[] var3) {
        this.field26 = var3;
        this.field24 = this.field26;
    }

    public final int getSize() {
        return this.field24.length;
    }

    public final Object getElementAt(int var1) {
        return this.field24[var1];
    }
}
