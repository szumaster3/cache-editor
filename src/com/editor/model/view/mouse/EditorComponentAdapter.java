package com.editor.model.view.mouse;

import com.editor.model.view.Main;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public final class EditorComponentAdapter extends ComponentAdapter {
    private Main field5;

    public EditorComponentAdapter(Main var1) {
        this.field5 = var1;
    }

    public final void componentResized(ComponentEvent var1) {
        Main.componentResizedCallback(this.field5, var1);
    }
}
