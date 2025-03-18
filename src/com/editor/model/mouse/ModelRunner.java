package com.editor.model.mouse;


import com.editor.model.render.Canvas;
import com.editor.model.Main;

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
