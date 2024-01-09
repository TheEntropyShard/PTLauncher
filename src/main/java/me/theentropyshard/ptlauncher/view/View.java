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

package me.theentropyshard.ptlauncher.view;

import com.formdev.flatlaf.ui.FlatButtonBorder;
import me.theentropyshard.ptlauncher.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class View {
    private final JFrame frame;
    private final JPanel root;
    private final JPanel playPanel;
    private final JButton addProfileButton;
    private final JComboBox<String> comboBox;
    private final Color transparentColor;
    private final JButton addProfileButtonMenu;
    private final JButton playButton;
    private final JTextField resourceAddressField;
    private final JTextField serverEndpointField;
    private final JTextField pathToLibraryField;
    private final JTextField profileNameField;
    private final CardLayout layout;

    public View(String title) {
        this.frame = new JFrame(title);
        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Image ptLogo = Utils.loadImage("/images/ptLogo.png");
        this.transparentColor = new Color(255, 255, 255, 128);

        this.layout = new CardLayout();
        this.root = new JPanel(this.layout) {{
            this.setPreferredSize(new Dimension(1000, 600));
        }};

        this.playPanel = new JPanel(null) {
            private final Image background = Utils.loadImage("/images/background.png");
            private final Dimension size;

            {
                this.size = new Dimension(1000, 600);
                this.setPreferredSize(this.size);
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(this.background, 0, 0, null);
                g.drawImage(ptLogo, (this.size.width / 2) - (ptLogo.getWidth(null) / 2), -10, null);
            }
        };

        JPanel addProfilePanel = new JPanel(null) {
            private final Image background = Utils.loadImage("/images/background.png");
            private final Dimension size;

            {
                this.size = new Dimension(1000, 600);
                this.setPreferredSize(this.size);
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(this.background, 0, 0, null);
                g.drawImage(ptLogo, (this.size.width / 2) - (ptLogo.getWidth(null) / 2), -10, null);
            }
        };

        this.addProfileButton = new JButton() {
            {
                final Color[] color = new Color[1];
                this.setBorder(new FlatButtonBorder() {{
                    color[0] = this.focusColor;
                }});
                this.setBackground(color[0]);
                this.add(new JPanel() {{
                    this.setOpaque(false);
                    this.setLayout(new GridLayout(1, 1));
                    this.add(new JLabel("Add Profile") {{
                        this.setHorizontalAlignment(JLabel.CENTER);
                        this.setFont(this.getFont().deriveFont(18.0f));
                    }});
                }});
            }

            @Override
            public void paint(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                AlphaComposite newComposite =
                        AlphaComposite.getInstance(
                                AlphaComposite.SRC_OVER, 0.4f);
                g2.setComposite(newComposite);
                super.paint(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setComposite(AlphaComposite.SrcOver);
                super.paintBorder(g);
            }

            @Override
            protected void paintChildren(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setComposite(AlphaComposite.SrcOver);
                super.paintChildren(g);
            }
        };

        int width = 360;
        int height = 140;
        JPanel center = new JPanel() {
            {
                Dimension size = playPanel.getPreferredSize();
                this.setBounds(size.width / 2 - width / 2, size.height / 2 - height / 2, width, height);
                this.setOpaque(false);
                this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(transparentColor);
                g.fillRoundRect(0, 0, width, height, 8, 8);
            }
        };

        center.add(new JPanel() {{
            this.setOpaque(false);
            this.setLayout(new GridLayout(1, 1));
            this.add(new JLabel("Profile:") {{
                this.setHorizontalAlignment(JLabel.CENTER);
                this.setFont(this.getFont().deriveFont(18.0f));
            }});
        }});

        this.comboBox = new JComboBox<>();
        center.add(this.comboBox);

        center.add(new JPanel() {{
            this.setOpaque(false);
            this.setLayout(new GridLayout(1, 1));
            this.add(addProfileButton);
        }});

        this.playButton = new JButton() {
            {
                Color color = new Color(62, 143, 35);
                this.setBackground(color);
                this.setBorder(new FlatButtonBorder() {{
                    this.focusColor = color;
                    this.hoverBorderColor = color;
                    this.focusedBorderColor = color;
                }});
                this.add(new JPanel() {{
                    this.setOpaque(false);
                    this.setLayout(new GridLayout(1, 1));
                    this.add(new JLabel("Play") {{
                        this.setHorizontalAlignment(JLabel.CENTER);
                        this.setFont(this.getFont().deriveFont(18.0f));
                    }});
                }});
            }

            @Override
            public void paint(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                AlphaComposite newComposite =
                        AlphaComposite.getInstance(
                                AlphaComposite.SRC_OVER, 0.4f);
                g2.setComposite(newComposite);
                super.paint(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setComposite(AlphaComposite.SrcOver);
                super.paintBorder(g);
            }

            @Override
            protected void paintChildren(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setComposite(AlphaComposite.SrcOver);
                super.paintChildren(g);
            }
        };
        center.add(new JPanel() {{
            this.setOpaque(false);
            this.setLayout(new GridLayout(1, 1));
            this.add(playButton);
        }});

        int addProfileWidth = 360;
        int addProfileHeight = 200;
        JPanel addProfileCenter = new JPanel() {
            {
                Dimension size = playPanel.getPreferredSize();
                this.setBounds(size.width / 2 - addProfileWidth / 2, size.height / 2 - addProfileHeight / 2, addProfileWidth, addProfileHeight);
                this.setOpaque(false);
                this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(transparentColor);
                g.fillRoundRect(0, 0, addProfileWidth, addProfileHeight, 8, 8);
            }
        };

        this.profileNameField = new JTextField() {
            {
                JLabel comp = new JLabel("Profile name") {{
                    this.setHorizontalAlignment(JLabel.LEFT);
                    this.setFont(this.getFont().deriveFont(16.0f));
                    this.setForeground(new Color(50, 50, 50));
                }};
                JTextField thisField = this;
                this.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        if(thisField.getText().length() == 0) {
                            comp.setText("");
                        } else if(thisField.getText().length() == 1 && e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                            comp.setText("Profile name");
                        }
                    }
                });
                this.setLayout(new GridLayout(1, 1));
                this.setFont(this.getFont().deriveFont(15.0f));
                this.add(new JPanel() {{
                    this.setOpaque(false);
                    this.setLayout(new GridLayout(1, 1));
                    this.add(comp);
                }});
            }

            @Override
            public void paint(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                AlphaComposite newComposite =
                        AlphaComposite.getInstance(
                                AlphaComposite.SRC_OVER, 0.4f);
                g2.setComposite(newComposite);
                super.paint(g);
            }

            @Override
            protected void paintChildren(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setComposite(AlphaComposite.SrcOver);
                super.paintChildren(g);
            }
        };

        addProfileCenter.add(new JPanel() {{
            this.setOpaque(false);
            this.setLayout(new GridLayout(1, 1));
            this.add(profileNameField);
        }});

        this.pathToLibraryField = new JTextField() {
            {
                JLabel comp = new JLabel("Path to library") {{
                    this.setHorizontalAlignment(JLabel.LEFT);
                    this.setFont(this.getFont().deriveFont(16.0f));
                    this.setForeground(new Color(50, 50, 50));
                }};
                JTextField thisField = this;
                this.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        if(thisField.getText().length() == 0) {
                            comp.setText("");
                        } else if(thisField.getText().length() == 1 && e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                            comp.setText("Path to library");
                        }
                    }
                });
                this.setFont(this.getFont().deriveFont(15.0f));
                this.setLayout(new GridLayout(1, 1));
                this.add(new JPanel() {{
                    this.setOpaque(false);
                    this.setLayout(new GridLayout(1, 1));
                    this.add(comp);
                }});
            }

            @Override
            public void paint(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                AlphaComposite newComposite =
                        AlphaComposite.getInstance(
                                AlphaComposite.SRC_OVER, 0.4f);
                g2.setComposite(newComposite);
                super.paint(g);
            }

            @Override
            protected void paintChildren(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setComposite(AlphaComposite.SrcOver);
                super.paintChildren(g);
            }
        };

        addProfileCenter.add(new JPanel() {{
            this.setOpaque(false);
            this.setLayout(new GridLayout(1, 1));
            this.add(pathToLibraryField);
        }});

        this.serverEndpointField = new JTextField() {
            {
                JLabel comp = new JLabel("Server ip and port (ip:port)") {{
                    this.setHorizontalAlignment(JLabel.LEFT);
                    this.setFont(this.getFont().deriveFont(16.0f));
                    this.setForeground(new Color(50, 50, 50));
                }};
                JTextField thisField = this;
                this.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        if(thisField.getText().length() == 0) {
                            comp.setText("");
                        } else if(thisField.getText().length() == 1 && e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                            comp.setText("Server ip and port (ip:port)");
                        }
                    }
                });
                this.setFont(this.getFont().deriveFont(15.0f));
                this.setLayout(new GridLayout(1, 1));
                this.add(new JPanel() {{
                    this.setOpaque(false);
                    this.setLayout(new GridLayout(1, 1));
                    this.add(comp);
                }});
            }

            @Override
            public void paint(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                AlphaComposite newComposite =
                        AlphaComposite.getInstance(
                                AlphaComposite.SRC_OVER, 0.4f);
                g2.setComposite(newComposite);
                super.paint(g);
            }

            @Override
            protected void paintChildren(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setComposite(AlphaComposite.SrcOver);
                super.paintChildren(g);
            }
        };

        addProfileCenter.add(new JPanel() {{
            this.setOpaque(false);
            this.setLayout(new GridLayout(1, 1));
            this.add(serverEndpointField);
        }});

        this.resourceAddressField = new JTextField() {
            {
                JLabel comp = new JLabel("Resource server address") {{
                    this.setHorizontalAlignment(JLabel.LEFT);
                    this.setFont(this.getFont().deriveFont(16.0f));
                    this.setForeground(new Color(50, 50, 50));
                }};
                JTextField thisField = this;
                this.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyPressed(KeyEvent e) {
                        if(thisField.getText().length() == 0) {
                            comp.setText("");
                        } else if(thisField.getText().length() == 1 && e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                            comp.setText("Resource server address");
                        }
                    }
                });
                this.setFont(this.getFont().deriveFont(15.0f));
                this.setLayout(new GridLayout(1, 1));
                this.add(new JPanel() {{
                    this.setOpaque(false);
                    this.setLayout(new GridLayout(1, 1));
                    this.add(comp);
                }});
            }

            @Override
            public void paint(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                AlphaComposite newComposite =
                        AlphaComposite.getInstance(
                                AlphaComposite.SRC_OVER, 0.4f);
                g2.setComposite(newComposite);
                super.paint(g);
            }

            @Override
            protected void paintChildren(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setComposite(AlphaComposite.SrcOver);
                super.paintChildren(g);
            }
        };

        addProfileCenter.add(new JPanel() {{
            this.setOpaque(false);
            this.setLayout(new GridLayout(1, 1));
            this.add(resourceAddressField);
        }});

        this.addProfileButtonMenu = new JButton() {
            {
                final Color[] color = new Color[1];
                this.setBorder(new FlatButtonBorder() {{
                    color[0] = this.focusColor;
                }});
                this.setBackground(color[0]);
                this.add(new JPanel() {{
                    this.setOpaque(false);
                    this.setLayout(new GridLayout(1, 1));
                    this.add(new JLabel("Add Profile") {{
                        this.setHorizontalAlignment(JLabel.CENTER);
                        this.setFont(this.getFont().deriveFont(18.0f));
                    }});
                }});
            }

            @Override
            public void paint(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                AlphaComposite newComposite =
                        AlphaComposite.getInstance(
                                AlphaComposite.SRC_OVER, 0.4f);
                g2.setComposite(newComposite);
                super.paint(g);
            }

            @Override
            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setComposite(AlphaComposite.SrcOver);
                super.paintBorder(g);
            }

            @Override
            protected void paintChildren(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setComposite(AlphaComposite.SrcOver);
                super.paintChildren(g);
            }
        };
        addProfileCenter.add(new JPanel() {{
            this.setOpaque(false);
            this.setLayout(new GridLayout(1, 1));
            this.add(addProfileButtonMenu);
        }});

        addProfileCenter.add(new JPanel() {{
            this.setOpaque(false);
            this.setLayout(new GridLayout(1, 1));
            this.add(new JButton() {
                {
                    Color color = new Color(173, 29, 29);
                    this.setBackground(color);
                    this.setBorder(new FlatButtonBorder() {{
                        this.focusColor = color;
                        this.hoverBorderColor = color;
                        this.focusedBorderColor = color;
                    }});
                    this.setBackground(color);
                    this.addActionListener(e -> layout.show(root, "PlayPanel"));
                    this.add(new JPanel() {{
                        this.setOpaque(false);
                        this.setLayout(new GridLayout(1, 1));
                        this.add(new JLabel("Back") {{
                            this.setHorizontalAlignment(JLabel.CENTER);
                            this.setFont(this.getFont().deriveFont(18.0f));
                        }});
                    }});
                }

                @Override
                public void paint(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g;
                    AlphaComposite newComposite =
                            AlphaComposite.getInstance(
                                    AlphaComposite.SRC_OVER, 0.4f);
                    g2.setComposite(newComposite);
                    super.paint(g);
                }

                @Override
                protected void paintBorder(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setComposite(AlphaComposite.SrcOver);
                    super.paintBorder(g);
                }

                @Override
                protected void paintChildren(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setComposite(AlphaComposite.SrcOver);
                    super.paintChildren(g);
                }
            });
        }});

        addProfilePanel.add(addProfileCenter);
        this.playPanel.add(center);

        this.addProfileButton.addActionListener(e -> this.layout.show(this.root, "AddProfilePanel"));

        this.root.add("PlayPanel", this.playPanel);
        this.root.add("AddProfilePanel", addProfilePanel);
        this.layout.show(this.root, "PlayPanel");

        this.frame.setIconImage(ptLogo);
        this.frame.add(this.root);
        this.frame.setResizable(false);
        this.frame.pack();
        this.frame.setLocationRelativeTo(null);
    }

    public void setVisible(boolean visible) {
        this.frame.setVisible(visible);
    }

    public JFrame getFrame() {
        return this.frame;
    }

    public CardLayout getLayout() {
        return this.layout;
    }

    public JPanel getRoot() {
        return this.root;
    }

    public JButton getAddProfileButton() {
        return this.addProfileButton;
    }

    public JButton getAddProfileButtonMenu() {
        return this.addProfileButtonMenu;
    }

    public JButton getPlayButton() {
        return this.playButton;
    }

    public JTextField getResourceAddressField() {
        return this.resourceAddressField;
    }

    public JTextField getServerEndpointField() {
        return this.serverEndpointField;
    }

    public JTextField getPathToLibraryField() {
        return this.pathToLibraryField;
    }

    public JTextField getProfileNameField() {
        return this.profileNameField;
    }

    public JComboBox<String> getComboBox() {
        return this.comboBox;
    }
}
