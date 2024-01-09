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

package me.theentropyshard.ptlauncher.model;

public class Profile {
    private String name;
    private String id;
    private String pathToLib;
    private String serverEndpoint;
    private String resourceServer;

    public Profile() {

    }

    public Profile(String name, String id, String pathToLib, String serverEndpoint, String resourceServer) {
        this.name = name;
        this.id = id;
        this.pathToLib = pathToLib;
        this.serverEndpoint = serverEndpoint;
        this.resourceServer = resourceServer;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPathToLib() {
        return this.pathToLib;
    }

    public void setPathToLib(String pathToLib) {
        this.pathToLib = pathToLib;
    }

    public String getServerEndpoint() {
        return this.serverEndpoint;
    }

    public void setServerEndpoint(String serverEndpoint) {
        this.serverEndpoint = serverEndpoint;
    }

    public String getResourceServer() {
        return this.resourceServer;
    }

    public void setResourceServer(String resourceServer) {
        this.resourceServer = resourceServer;
    }

    @Override
    public String toString() {
        return "Profile{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", pathToLib='" + pathToLib + '\'' +
                ", serverEndpoint='" + serverEndpoint + '\'' +
                ", resourceServer='" + resourceServer + '\'' +
                '}';
    }
}
