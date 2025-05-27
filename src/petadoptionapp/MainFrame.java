package petadoptionapp; // Adjust package name if different

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicButtonUI;

import java.awt.geom.RoundRectangle2D; // For rounded corners
import java.net.URL; // For loading images from URL if needed
import java.awt.Desktop; // For opening URLs
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI; // For URI handling

public class MainFrame extends JFrame {
    private JPanel cardPanel;
    private CardLayout cardLayout;

    public MainFrame() {
        setTitle("FurGivers Paws of Hope"); // Changed title
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1024, 768));
        setPreferredSize(new Dimension(1200, 900));
        setLayout(new BorderLayout());

        // Set the frame to be maximized (fullscreen)
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Set a modern background color for the entire content pane
        getContentPane().setBackground(Color.decode("#F2F4F8")); // Consistent light grey

        // Add a subtle border to the content pane for a modern, defined look
        getRootPane().setBorder(BorderFactory.createLineBorder(Color.decode("#E0E0E0"), 1)); // Light grey border

        // --- Top Section: Logo and Navigation ---
        JPanel topWrapper = new JPanel(new BorderLayout()); // Use BorderLayout for flexible placement
        topWrapper.setBackground(Color.WHITE); // White background for the header section
        topWrapper.setBorder(new EmptyBorder(20, 60, 20, 60)); // Generous padding

        // Logo (now a picture matching the provided image)
        JLabel logoLabel;
        try {
            URL imageUrl = getClass().getResource("/resources/1.png"); // Assuming this is the CARA logo
            if (imageUrl != null) {
                ImageIcon originalIcon = new ImageIcon(imageUrl);
                // Scale the logo image to fit the header, maintaining aspect ratio
                // The original image seems to be a full header, so we'll scale it down
                Image scaledLogo = originalIcon.getImage().getScaledInstance(300, 80, Image.SCALE_SMOOTH); // Adjust size as needed
                logoLabel = new JLabel(new ImageIcon(scaledLogo));
            } else {
                System.err.println("Error: Logo image not found at /resources/image_fe8dc8.png. Using text fallback.");
                logoLabel = new JLabel("FurGivers Paws of Hope"); // Changed logo text fallback
                logoLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
                logoLabel.setForeground(Color.decode("#2B4576")); // Changed accent color
            }
        } catch (Exception e) {
            System.err.println("Exception loading logo image: " + e.getMessage() + ". Using text fallback.");
            logoLabel = new JLabel("FurGivers Paws of Hope"); // Changed logo text fallback
            logoLabel.setFont(new Font("SansSerif", Font.BOLD, 36));
            logoLabel.setForeground(Color.decode("#2B4576")); // Changed accent color
        }
        logoLabel.setBorder(new EmptyBorder(0, 0, 0, 0));
        topWrapper.add(logoLabel, BorderLayout.WEST);

        // Navigation buttons container (on the right)
        JPanel navButtonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 25, 0)); // Align right, increased spacing
        navButtonsPanel.setOpaque(false); // Transparent to show topWrapper background
        navButtonsPanel.setBorder(new EmptyBorder(25, 0, 25, 0)); // Vertical padding to center buttons

        MenuButton homeButton = new MenuButton("Home");
        MenuButton aboutButton = new MenuButton("About"); // This will now go to the About Us page
        MenuButton donateButton = new MenuButton("Donate");
        MenuButton findAPetButton = new MenuButton("Find a Pet");
        MenuButton contactButton = new MenuButton("Contact");


        navButtonsPanel.add(homeButton);
        navButtonsPanel.add(aboutButton);
        navButtonsPanel.add(donateButton);
        navButtonsPanel.add(findAPetButton);
        navButtonsPanel.add(contactButton);


        topWrapper.add(navButtonsPanel, BorderLayout.EAST);
        add(topWrapper, BorderLayout.NORTH);

        // --- Main Content Area ---
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(Color.decode("#F2F4F8")); // Match background
        cardPanel.setBorder(new EmptyBorder(30, 80, 30, 80)); // Consistent margins

        // Add different panels/screens
        JPanel homePanel = createHomePanel(); // Method to create the enhanced home panel
        AllPetsPanel allPetsPanel = new AllPetsPanel(this); // Pass MainFrame instance
        JPanel aboutUsPanel = createAboutUsPanel(); // New About Us panel

        cardPanel.add(homePanel, "Home");
        cardPanel.add(allPetsPanel, "Adopt"); // This is the main pet display panel (Find a Pet)
        cardPanel.add(aboutUsPanel, "About"); // Add the new About Us panel
        // Removed placeholder for Donate as the button will now directly open a link
        // JPanel donatePlaceholderPanel = new JPanel();
        // donatePlaceholderPanel.setBackground(Color.decode("#F2F4F8"));
        // JLabel donateComingSoon = new JLabel("<html><center>Thank you for your generosity!<br>Donation information coming soon.</center></html>");
        // donateComingSoon.setFont(new Font("SansSerif", Font.PLAIN, 20));
        // donateComingSoon.setForeground(Color.decode("#333333"));
        // donatePlaceholderPanel.add(donateComingSoon);
        // cardPanel.add(donatePlaceholderPanel, "Donate"); // Keep this panel for now, but button won't navigate to it directly

        add(cardPanel, BorderLayout.CENTER);

        // --- Button Actions ---
        homeButton.addActionListener(e -> cardLayout.show(cardPanel, "Home"));
        findAPetButton.addActionListener(e -> cardLayout.show(cardPanel, "Adopt")); // "Find a Pet" maps to "Adopt" panel
        aboutButton.addActionListener(e -> cardLayout.show(cardPanel, "About")); // Link About button to About Us panel
        contactButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Contact section coming soon!"));

        // Donate button now opens a link
        donateButton.addActionListener(e -> {
            final String DONATE_URL = "https://docs.google.com/forms/d/e/1FAIpQLSda_jQUn0XWPIzr3Eli5bVkoWOW10H_VVsiQDWC-dWoHyiPMQ/viewform?usp=sharing&ouid=108321204493867680753"; // Example URL
            try {
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    Desktop.getDesktop().browse(new URI(DONATE_URL));
                } else {
                    JOptionPane.showMessageDialog(this, "Cannot open browser. Please visit:\n" + DONATE_URL, "Browser Not Supported", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error opening link: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });


        // --- Footer ---
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(Color.decode("#F2F4F8")); // Consistent background
        footerPanel.setBorder(new EmptyBorder(15, 0, 15, 0)); // Increased vertical padding

        JLabel footerLabel = new JLabel("2025 FurGivers Paws of Hope © All Rights Reserved"); // Updated footer text
        footerLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        footerLabel.setForeground(Color.decode("#757575")); // Medium grey for footer text
        footerPanel.add(footerLabel);

        add(footerPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
    }

    private JPanel createHomePanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); // Use BoxLayout for vertical stacking
        panel.setBackground(Color.decode("#F2F4F8")); // Consistent background
        panel.setBorder(new EmptyBorder(30, 0, 30, 0)); // Vertical padding for content within the panel

        // Main Title: "FurGivers Paws of Hope"
        JLabel mainTitle = new JLabel("FurGivers Paws of Hope"); // Changed main title
        mainTitle.setFont(new Font("SansSerif", Font.BOLD, 50)); // Large, bold font
        mainTitle.setForeground(Color.decode("#333333")); // Dark grey for main title
        mainTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Subtitle: "COMPASSION AND RESPONSIBILITY FOR ANIMALS"
        JLabel subTitle = new JLabel("COMPASSION AND RESPONSIBILITY FOR ANIMALS");
        subTitle.setFont(new Font("SansSerif", Font.PLAIN, 22)); // Smaller, plain font
        subTitle.setForeground(Color.decode("#666666")); // Medium grey for subtitle
        subTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createVerticalStrut(50)); // Space from top
        panel.add(mainTitle);
        panel.add(Box.createVerticalStrut(10)); // Small space between title and subtitle
        panel.add(subTitle);
        panel.add(Box.createVerticalStrut(40)); // Space below subtitle

        panel.add(Box.createVerticalStrut(50)); // Space before pet previews

        // Pet Previews Section (simplified, similar to AllPetsPanel cards - re-added)
        JPanel previewGridPanel = new JPanel(new GridLayout(1, 3, 20, 0)); // 1 row, 3 columns
        previewGridPanel.setOpaque(false);
        previewGridPanel.setBorder(new EmptyBorder(0, 50, 0, 50)); // Horizontal padding for the grid

        // Placeholder for pet previews (replace with actual pet data if desired)
        previewGridPanel.add(createHomePetPreview("/resources/dog_arian.jpg", "Hi I'm Arian, and I'm a very good boy!"));
        previewGridPanel.add(createHomePetPreview("/resources/cat1.jpg", "Hi I'm Whiskers, the cuddly cat."));
        previewGridPanel.add(createHomePetPreview("/resources/dog_brisket.jpg", "Hi I'm Brisket, will you Netflix and chill with me?"));

        panel.add(previewGridPanel);

        return panel;
    }

    // New method for About Us page
    private JPanel createAboutUsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.decode("#F2F4F8"));
        panel.setBorder(new EmptyBorder(50, 100, 50, 100)); // Generous padding

        JLabel title = new JLabel("About FurGivers Paws of Hope"); // Changed title
        title.setFont(new Font("SansSerif", Font.BOLD, 40));
        title.setForeground(Color.decode("#2B4576")); // Changed accent color
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(new EmptyBorder(0, 0, 30, 0));

        JTextArea content = new JTextArea(
            "FurGivers Paws of Hope is a non-profit organization dedicated to the welfare of animals. " +
            "Established in 2000, our mission is to promote compassion and responsibility towards animals " +
            "through education, advocacy, and direct animal rescue and rehabilitation.\n\n" +
            "We believe that every animal deserves a life free from cruelty, neglect, and abuse. " +
            "Our programs include: \n" +
            "  •  **Rescue and Rehabilitation:** Providing immediate care, medical attention, and rehabilitation for rescued animals.\n" +
            "  •  **Adoption Program:** Finding loving forever homes for our rescued cats and dogs.\n" +
            "  •  **Spay/Neuter Program:** Promoting population control through responsible pet ownership and accessible spay/neuter services.\n" +
            "  •  **Education and Advocacy:** Raising awareness about animal welfare issues and advocating for stronger animal protection laws.\n\n" +
            "We rely heavily on the support of volunteers and donations to continue our vital work. " +
            "Join us in making a difference in the lives of countless animals!"
        );
        content.setFont(new Font("SansSerif", Font.PLAIN, 18));
        content.setForeground(Color.decode("#333333"));
        content.setBackground(Color.decode("#F2F4F8"));
        content.setLineWrap(true);
        content.setWrapStyleWord(true);
        content.setEditable(false);
        content.setAlignmentX(Component.LEFT_ALIGNMENT); // Align text to left
        content.setBorder(new EmptyBorder(0, 0, 30, 0)); // Padding below text

        // Add a placeholder image for the About Us page
        JLabel aboutImageLabel = new JLabel();
        try {
            URL aboutImageUrl = getClass().getResource("/resources/about_us_placeholder.jpg"); // Placeholder image
            if (aboutImageUrl != null) {
                ImageIcon aboutIcon = new ImageIcon(aboutImageUrl);
                Image scaledAboutImage = aboutIcon.getImage().getScaledInstance(500, 300, Image.SCALE_SMOOTH);
                aboutImageLabel.setIcon(new ImageIcon(scaledAboutImage));
            } else {
                aboutImageLabel.setText("About Us Image Placeholder");
                aboutImageLabel.setFont(new Font("SansSerif", Font.ITALIC, 14));
                aboutImageLabel.setForeground(Color.GRAY);
                System.err.println("Error loading About Us image: /resources/about_us_placeholder.jpg not found.");
            }
        } catch (Exception e) {
            aboutImageLabel.setText("About Us Image Error");
            aboutImageLabel.setFont(new Font("SansSerif", Font.ITALIC, 14));
            aboutImageLabel.setForeground(Color.GRAY);
            System.err.println("Exception loading About Us image: " + e.getMessage());
        }
        aboutImageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        aboutImageLabel.setBorder(new EmptyBorder(20, 0, 20, 0)); // Padding around image

        panel.add(title);
        panel.add(content);
        panel.add(aboutImageLabel);

        return panel;
    }


    // Helper method to create pill-shaped buttons (kept for createHomePanel if needed elsewhere)
    private JButton createPillButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.PLAIN, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.decode("#333333"));
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(8, 15, 8, 15)); // Padding inside pill
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.setUI(new BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                JButton btn = (JButton) c;
                int width = btn.getWidth();
                int height = btn.getHeight();
                int arc = height; // Makes it fully rounded (pill shape)

                g2.setColor(btn.getBackground());
                g2.fillRoundRect(0, 0, width, height, arc, arc);

                // Optional: Add a subtle border to pills
                g2.setColor(Color.decode("#D0D0D0")); // Slightly darker border for pills
                g2.drawRoundRect(0, 0, width - 1, height - 1, arc, arc);


                // Paint the text
                super.paint(g2, c);
                g2.dispose();
            }
        });

        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    // Helper method to create a simplified pet preview card for the homepage
    private JPanel createHomePetPreview(String imagePath, String description) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE); // White background for preview cards
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.decode("#E0E0E0"), 1), // Light grey border
            new EmptyBorder(15, 15, 15, 15) // Inner padding
        ));
        panel.setPreferredSize(new Dimension(280, 400)); // Fixed size for consistency
        panel.setMaximumSize(new Dimension(280, 400));
        panel.setMinimumSize(new Dimension(280, 400));

        // Add a subtle shadow to preview cards
        panel.setOpaque(false); // Make it transparent for custom painting
        panel.addMouseListener(new java.awt.event.MouseAdapter() {
            private float scale = 1.0f;
            private Timer scaleTimer;
            private final int ANIMATION_STEPS = 5;
            private final int ANIMATION_DELAY = 20;

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                startScaleAnimation(1.03f); // Scale up slightly on hover
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                startScaleAnimation(1.0f); // Scale back
            }

            private void startScaleAnimation(float targetScale) {
                if (scaleTimer != null && scaleTimer.isRunning()) {
                    scaleTimer.stop();
                }
                float startScale = scale;
                float deltaScale = (targetScale - startScale) / ANIMATION_STEPS;
                scaleTimer = new Timer(ANIMATION_DELAY, (ActionListener) new ActionListener() {
                    int step = 0;
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        step++;
                        scale = startScale + deltaScale * step;
                        if (step >= ANIMATION_STEPS) {
                            scale = targetScale;
                            ((Timer)e.getSource()).stop();
                        }
                        panel.repaint();
                    }
                });
                scaleTimer.start();
            }

            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int cx = getWidth() / 2;
                int cy = getHeight() / 2;
                g2.translate(cx, cy);
                g2.scale(scale, scale);
                g2.translate(-cx, -cy);

                int arc = 15; // Rounded corners
                int width = getWidth();
                int height = getHeight();

                // Draw subtle shadow
                g2.setColor(new Color(0, 0, 0, 15));
                g2.fill(new RoundRectangle2D.Double(2, 2, width - 2, height - 2, arc, arc));

                // Fill background
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, width, height, arc, arc);

                super.paintComponent(g2); // Paint children
                g2.dispose();
            }

            protected void paintBorder(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int cx = getWidth() / 2;
                int cy = getHeight() / 2;
                g2.translate(cx, cy);
                g2.scale(scale, scale);
                g2.translate(-cx, -cy);

                int arc = 15;
                int width = getWidth();
                int height = getHeight();

                g2.setColor(Color.decode("#E0E0E0")); // Light grey border
                g2.setStroke(new BasicStroke(1));
                g2.drawRoundRect(0, 0, width - 1, height - 1, arc, arc);
                g2.dispose();
            }
        });


        JLabel imageLabel = new JLabel();
        try {
            URL imgUrl = getClass().getResource(imagePath);
            if (imgUrl != null) {
                ImageIcon icon = new ImageIcon(imgUrl);
                Image scaledImg = icon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
                imageLabel.setIcon(new ImageIcon(scaledImg));
            } else {
                imageLabel.setText("Image N/A");
                imageLabel.setFont(new Font("SansSerif", Font.ITALIC, 12));
                imageLabel.setForeground(Color.GRAY);
            }
        } catch (Exception e) {
            imageLabel.setText("Image Error");
            imageLabel.setFont(new Font("SansSerif", Font.ITALIC, 12));
            imageLabel.setForeground(Color.GRAY);
            System.err.println("Error loading preview image " + imagePath + ": " + e.getMessage());
        }
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        imageLabel.setBorder(new EmptyBorder(0, 0, 10, 0));

        JTextArea descArea = new JTextArea(description);
        descArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
        descArea.setForeground(Color.decode("#333333"));
        descArea.setBackground(Color.WHITE);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setEditable(false);
        descArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        descArea.setMaximumSize(new Dimension(250, 60)); // Limit height for description
        descArea.setBorder(new EmptyBorder(5, 5, 5, 5));


        panel.add(imageLabel);
        panel.add(descArea);

        return panel;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}

// Custom JButton for consistent menu styling
class MenuButton extends JButton {
    public MenuButton(String text) {
        super(text);
        setFont(new Font("SansSerif", Font.PLAIN, 16)); // Changed to PLAIN for less bold look
        setForeground(Color.decode("#333333")); // Dark grey text color
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add a subtle hover effect (changing color)
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setForeground(Color.decode("#2B4576")); // Changed accent color
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                setForeground(Color.decode("#333333")); // Back to normal
            }
        });
    }
}
