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

package me.theentropyshard.ptlauncher.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.theentropyshard.ptlauncher.PTLauncher;
import me.theentropyshard.ptlauncher.RenderMode;
import me.theentropyshard.ptlauncher.Utils;
import me.theentropyshard.ptlauncher.model.Profile;
import me.theentropyshard.ptlauncher.view.View;

import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

public class Controller {
    private final View view;
    private final List<Profile> profiles;
    private final Gson gson;
    private Process currentProcess;

    public Controller(View view) {
        this.view = view;
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.profiles = new ArrayList<>();
        try {
            Profile[] a = this.gson.fromJson(new FileReader(PTLauncher.getInstance().getProfilesFile()), Profile[].class);
            if(a != null) {
                this.profiles.addAll(Arrays.asList(a));
                this.profiles.forEach(profile -> this.view.getComboBox().addItem(profile.getName()));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Could not load profiles: not found", e);
        }

        if(this.profiles.isEmpty()) {
            this.addNewProfile(new Profile(
                    "Default",
                    "ProTanki-" + UUID.randomUUID().toString().substring(0, 8),
                    PTLauncher.getInstance().getCwd() + File.separator + "client.swf",
                    "146.59.110.195:1337",
                    "http://146.59.110.103"
            ));
        }

        this.view.getAddProfileButtonMenu().addActionListener(e -> this.addNewProfile());
        this.view.getPlayButton().addActionListener(e -> this.play());

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if(this.currentProcess != null) {
                if(this.currentProcess.isAlive()) {
                    this.currentProcess.destroy();
                }
                if(this.currentProcess.isAlive()) {
                    this.currentProcess.destroyForcibly();
                }
            }
        }));
    }

    public void addNewProfile() {
        String profileName = this.view.getProfileNameField().getText();
        String pathToLib = this.view.getPathToLibraryField().getText();
        String serverEndpoint = this.view.getServerEndpointField().getText();
        String resourceServer = this.view.getResourceAddressField().getText();

        Profile profile = new Profile(
                profileName,
                "ProTanki-" + UUID.randomUUID().toString().substring(0, 8),
                pathToLib,
                serverEndpoint,
                resourceServer
        );
        this.addNewProfile(profile);
        this.view.getLayout().show(this.view.getRoot(), "PlayPanel");
    }

    public void addNewProfile(Profile profile) {
        this.profiles.add(profile);
        this.saveProfile(profile);
    }

    private void saveProfile(Profile profile) {
        this.view.getComboBox().addItem(profile.getName());
        try(FileWriter writer = new FileWriter(PTLauncher.getInstance().getProfilesFile())) {
            this.gson.toJson(this.profiles.toArray(new Profile[0]), writer);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void setControlsEnabled(boolean enabled) {
        this.view.getComboBox().setEnabled(enabled);
        this.view.getAddProfileButton().setEnabled(enabled);
        this.view.getPlayButton().setEnabled(enabled);
        this.view.getFrame().setVisible(enabled);
    }

    public void play() {
        this.setControlsEnabled(false);

        JComboBox<String> comboBox = this.view.getComboBox();
        Profile profile = this.profiles.stream()
                .filter(profile1 -> profile1.getName().equals(comboBox.getSelectedItem()))
                .collect(Collectors.toList()).get(0);
        new Thread(() -> {
            String query = "lang=" + "ru" + "&amp;" +
                    "client=" + profile.getPathToLib() + "&amp;" +
                    "server=" + profile.getServerEndpoint() + "&amp;" +
                    "resources=" + profile.getResourceServer();
            String appDescriptor = Utils.getApplicationDescriptor(profile.getId(), query, RenderMode.DIRECT);
            try {
                File tempAppDescriptor = File.createTempFile("ProTanki", ".tmp.xml", PTLauncher.getInstance().getTmpDir());
                tempAppDescriptor.deleteOnExit();
                Files.write(tempAppDescriptor.toPath(), appDescriptor.getBytes(StandardCharsets.UTF_8), StandardOpenOption.WRITE);
                if(this.currentProcess != null) {
                    if(this.currentProcess.isAlive()) {
                        this.currentProcess.destroy();
                    }
                    if(this.currentProcess.isAlive()) {
                        this.currentProcess.destroyForcibly();
                    }
                }
                this.currentProcess = new ProcessBuilder().command(
                        Paths.get("runtime\\adl.exe").toRealPath().toString(),
                        "-runtime", Paths.get("runtime\\win").toRealPath().toString(),
                        "-profile", "desktop",
                        tempAppDescriptor.getAbsolutePath(), "./"
                ).start();
                new Thread(() -> {
                    Scanner inputStreamSc = new Scanner(this.currentProcess.getInputStream());
                    while(inputStreamSc.hasNextLine()) {
                        System.out.println(inputStreamSc.nextLine());
                    }
                }).start();

                int exitCode = this.currentProcess.waitFor();
                if(exitCode != 0) {
                    Scanner scanner = new Scanner(this.currentProcess.getErrorStream());
                    while(scanner.hasNextLine()) {
                        System.err.println(scanner.nextLine());
                    }
                    scanner.close();
                }
                this.setControlsEnabled(true);
                Thread.currentThread().interrupt();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }){{
            this.setName("ProTanki Worker Thread");
        }}.start();
    }
}
