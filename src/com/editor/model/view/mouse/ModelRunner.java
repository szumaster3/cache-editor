package com.editor.model.view.mouse;


import com.editor.model.view.render.Canvas;
import com.editor.model.view.Main;

public final class ModelRunner implements Runnable {
    public final void run() {
        Main main;
        (main = new Main()).setTitle("");
        main.loadFiles();
        main.setVisible(true);
        main.render();
        (new Thread(String.valueOf(main))).start();
        Canvas.load();
    }
}
