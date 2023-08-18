package me.theentropyshard.ptlauncher.utils;

import java.awt.*;

public final class SwingUtils {
    public static void centerWindowOnScreen(Window window, int screen) {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] allDevices = env.getScreenDevices();

        if (screen < 0 || screen >= allDevices.length) {
            screen = 0;
        }

        Rectangle bounds = allDevices[screen].getDefaultConfiguration().getBounds();
        window.setLocation(
                ((bounds.width - window.getWidth()) / 2) + bounds.x,
                ((bounds.height - window.getHeight()) / 2) + bounds.y
        );
    }

    private SwingUtils() {
        throw new UnsupportedOperationException();
    }
}
