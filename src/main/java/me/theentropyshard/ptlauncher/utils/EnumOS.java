/*      Pita. A simple desktop client for NetSchool by irTech
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

public enum EnumOS {
    WINDOWS,
    LINUX,
    MACOS,
    SOLARIS,
    UNKNOWN;

    public static EnumOS getOS() {
        String osName = System.getProperty("os.name").toLowerCase();
        if(osName.contains("win")) return EnumOS.WINDOWS;
        if(osName.contains("mac")) return EnumOS.MACOS;
        if(osName.contains("solaris") || osName.contains("sunos")) return EnumOS.SOLARIS;
        if(osName.contains("linux") || osName.contains("unix")) return EnumOS.LINUX;
        return EnumOS.UNKNOWN;
    }
}
