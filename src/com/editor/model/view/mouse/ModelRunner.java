package com.editor.model.view.mouse;


import com.editor.model.view.Main;
import com.editor.model.view.render.Canvas;

public final class ModelRunner implements Runnable {
    public void run() {
        Main main;
        (main = new Main()).setTitle("");
        main.loadFiles();
        main.setVisible(true);
        main.render();
        (new Thread(String.valueOf(main))).start();
        Canvas.load();
    }
}
