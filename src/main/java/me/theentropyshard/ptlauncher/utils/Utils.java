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

package me.theentropyshard.ptlauncher.utils;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Различные утилиты
 */
public final class Utils {
    public static Path getAppDir(String appName) {
        String userHome = System.getProperty("user.home", ".");
        Path appDir = Paths.get(userHome, appName);

        switch (EnumOS.getOS()) {
            case WINDOWS:
                String appData = System.getenv("APPDATA");
                if (appData != null) {
                    appDir = Paths.get(appData, appName);
                }
                break;

            case MACOS:
                appDir = Paths.get(userHome, "Library", "Application Support", appName);
                break;

            case LINUX:
            case SOLARIS:
                appDir = Paths.get(userHome, ".config", appName);
                break;
        }

        return appDir;
    }

    private Utils() {
        throw new UnsupportedOperationException();
    }
}
