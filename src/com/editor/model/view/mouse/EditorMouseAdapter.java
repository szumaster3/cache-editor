package com.editor.model.view.mouse;

import com.editor.model.view.Main;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public final class EditorMouseAdapter extends MouseAdapter {
    private Main main;

    public EditorMouseAdapter(Main main) {
        this.main = main;
    }

    public final void mousePressed(MouseEvent var1) {
    }

    public final void mouseReleased(MouseEvent var1) {
        Main.mouseReleasedCallback(this.main, var1);
    }
}
