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

import com.formdev.flatlaf.FlatIntelliJLaf;
import me.theentropyshard.ptlauncher.utils.PathUtils;
import me.theentropyshard.ptlauncher.utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class PTLauncher {
    private final Path cwd;
    private Path defaultRuntimePath;

    public PTLauncher() {
        this.cwd = Utils.getAppDir("PTLauncher");

        try {
            this.setupDirectories();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.initGUI();

        try {
            Path airRuntimePath = this.cwd.resolve("air_runtime");
            if (!Files.exists(airRuntimePath) || Files.size(airRuntimePath) == 0L) {
                Files.createDirectories(airRuntimePath);
                this.unzipAIR(airRuntimePath.toFile());
                this.defaultRuntimePath = airRuntimePath;

                System.out.println("Unzipped AIR");
            } else {
                System.out.println("AIR already unzipped");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupDirectories() throws IOException {
        PathUtils.createDirectory(this.cwd);
    }

    private void initGUI() {
        if (GraphicsEnvironment.isHeadless()) {
            System.err.println("Your graphics environment is headless");
            System.exit(1);
        }

        JDialog.setDefaultLookAndFeelDecorated(true);
        JFrame.setDefaultLookAndFeelDecorated(true);
        FlatIntelliJLaf.setup();
    }

    private void unzipAIR(File runtimeDir) throws IOException {
        InputStream is = Objects.requireNonNull(PTLauncher.class.getResourceAsStream("/runtimes/air.zip"));
        ZipInputStream zis = new ZipInputStream(is);
        ZipEntry zipEntry = zis.getNextEntry();
        while (zipEntry != null) {
            File newFile = new File(runtimeDir, zipEntry.getName());

            String destDirPath = runtimeDir.getCanonicalPath();
            String destFilePath = newFile.getCanonicalPath();

            if (!destFilePath.startsWith(destDirPath + File.separator)) {
                throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
            }
            if (zipEntry.isDirectory()) {
                if (!newFile.isDirectory() && !newFile.mkdirs()) {
                    throw new IOException("Failed to create directory " + newFile);
                }
            } else {
                File parent = newFile.getParentFile();
                if (!parent.isDirectory() && !parent.mkdirs()) {
                    throw new IOException("Failed to create directory " + parent);
                }

                FileOutputStream fos = new FileOutputStream(newFile);
                byte[] buffer = new byte[4096];
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
            }
            zipEntry = zis.getNextEntry();
        }

        zis.closeEntry();
        zis.close();
    }

    public Path getDefaultRuntimePath() {
        return this.defaultRuntimePath;
    }
}
