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

import me.theentropyshard.ptlauncher.controller.Controller;
import me.theentropyshard.ptlauncher.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public final class PTLauncher {
    private final File cwd;
    private final File runtimeDir;
    private final File tmpDir;
    private final File profilesFile;

    public PTLauncher() {
        if(instance != null) {
            throw new IllegalStateException("PTLauncher already created");
        }
        instance = this;

        this.cwd = new File(System.getProperty("user.dir"));
        this.runtimeDir = new File(this.cwd, "runtime");
        this.tmpDir = Utils.createFileOrDir(new File(this.cwd, "tmp"), true);

        if(!this.runtimeDir.exists()) {
            System.out.println("Runtime dir does not exist, unpacking AIR");
            Utils.createFileOrDir(this.runtimeDir, true);
            try {
                this.unzipAIR();
            } catch (IOException e) {
                System.err.println("Could not unzip AIR");
                e.printStackTrace();
            }
            System.out.println("Unpacked AIR");
        } else {
            System.out.println("AIR already unpacked");
        }

        this.profilesFile = Utils.createFileOrDir(new File(this.cwd, "profiles.json"), false);

        View view = new View("PTLauncher");
        new Controller(view);

        view.setVisible(true);
    }

    private void unzipAIR() throws IOException {
        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(Objects.requireNonNull(PTLauncher.class.getResourceAsStream("/air.zip")));
        ZipEntry zipEntry = zis.getNextEntry();
        while(zipEntry != null) {
            File newFile = new File(this.runtimeDir, zipEntry.getName());

            String destDirPath = this.runtimeDir.getCanonicalPath();
            String destFilePath = newFile.getCanonicalPath();

            if (!destFilePath.startsWith(destDirPath + File.separator)) {
                throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
            }
            if(zipEntry.isDirectory()) {
                if(!newFile.isDirectory() && !newFile.mkdirs()) {
                    throw new IOException("Failed to create directory " + newFile);
                }
            } else {
                File parent = newFile.getParentFile();
                if(!parent.isDirectory() && !parent.mkdirs()) {
                    throw new IOException("Failed to create directory " + parent);
                }

                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
            }
            zipEntry = zis.getNextEntry();
        }

        zis.closeEntry();
        zis.close();
    }

    public File getCwd() {
        return this.cwd;
    }

    public File getTmpDir() {
        return this.tmpDir;
    }

    public File getProfilesFile() {
        return this.profilesFile;
    }

    private static PTLauncher instance;

    public static PTLauncher getInstance() {
        return instance;
    }
}
