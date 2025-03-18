package com.editor.model.mouse;

import com.editor.model.Main;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public final class EditorMouseWheelListener implements MouseWheelListener {
    private Main field30;

    public EditorMouseWheelListener(Main var1) {
        this.field30 = var1;
    }

    public final void mouseWheelMoved(MouseWheelEvent var1) {
        Main.mouseWheelMoved(this.field30, var1);
    }
}
