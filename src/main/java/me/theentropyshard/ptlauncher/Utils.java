/*      PTLauncher. Custom launcher for ProTanki
 *      Copyright (C) 2022-2023 TheEntropyShard
 *
 *      This program is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      (at your option) any later version.
 *
 *      This program is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package me.theentropyshard.ptlauncher;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public final class Utils {
    private Utils() {
        throw new Error("Class Utils should not be instantiated");
    }

    public static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(Objects.requireNonNull(Utils.class.getResourceAsStream(path)));
        } catch (IOException e) {
            throw new RuntimeException("Could not load image", e);
        }
    }

    public static File createFileOrDir(File file, boolean isDir) {
        if(!file.exists()) {
            try {
                if(isDir) {
                    file.mkdirs();
                } else {
                    file.createNewFile();
                }
            } catch (IOException e) {
                throw new RuntimeException("Could not create file or dir: " + file, e);
            }
        }
        return file;
    }

    public static String getApplicationDescriptor(String id, String query, RenderMode renderMode) {
        return String.format(
                "<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n" +
                        "<application xmlns=\"http://ns.adobe.com/air/application/3.2\">\n" +
                        "  <id>%s</id>\n" +
                        "  <versionNumber>1.0</versionNumber>\n" +
                        "  <filename>%s</filename>\n" +
                        "\n" +
                        "  <initialWindow>\n" +
                        "    <title>ProTanki</title>\n" +
                        "    <content>runtime\\protankiloader.swf?%s</content>\n" +
                        "    <visible>true</visible>\n" +
                        "\n" +
                        "    <renderMode>%s</renderMode>\n" +
                        "    <depthAndStencil>true</depthAndStencil>\n" +
                        "\n" +
                        "    <minimizable>true</minimizable>\n" +
                        "    <maximizable>true</maximizable>\n" +
                        "    <resizable>true</resizable>\n" +
                        "  </initialWindow>\n" +
                        "</application>\n",
                id, id, query, renderMode.getRenderMode()
        );
    }
}
