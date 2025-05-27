package petadoptionapp;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.awt.Desktop;
import java.awt.geom.RoundRectangle2D; // For rounded corners
import javax.swing.plaf.basic.BasicButtonUI; // For custom button UI

public class PetDetailsDialog extends JDialog {

    public PetDetailsDialog(Frame owner, Pet pet) {
        super(owner, "Details for " + pet.getName(), true); // true makes it modal
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setUndecorated(true); // Remove default window decorations

        // Increased size for the popup, making it taller and wider
        setSize(900, 850); // Width: 900, Height: 850
        setLocationRelativeTo(owner); // Center relative to the main frame

        // Main panel for the dialog content with rounded corners and shadow
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int arc = 25; // Radius for rounded corners
                int x = 0;
                int y = 0;
                int width = getWidth();
                int height = getHeight();

                // Draw subtle shadow before filling the background
                g2.setColor(new Color(0, 0, 0, 20)); // Subtle shadow color
                g2.fill(new RoundRectangle2D.Double(5, 5, width - 5, height - 5, arc, arc)); // Offset shadow

                // Fill background
                g2.setColor(Color.decode("#F8F8F8")); // Modern: Very light grey background
                g2.fillRoundRect(x, y, width, height, arc, arc);

                // Draw subtle border
                g2.setColor(Color.decode("#E0E0E0")); // Modern: Light grey border
                g2.setStroke(new BasicStroke(1));
                g2.drawRoundRect(x, y, width - 1, height - 1, arc, arc);
            }
        };
        mainPanel.setOpaque(false); // Make sure it's transparent for custom painting
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30)); // Increased padding

        // Pet Image (top)
        JLabel petImageLabel = new JLabel();
        try {
            ImageIcon originalIcon = new ImageIcon(getClass().getResource(pet.getImagePath()));
            Image scaledImage = originalIcon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH); // Increased image size
            petImageLabel.setIcon(new ImageIcon(scaledImage));
        } catch (NullPointerException ex) {
            petImageLabel.setText("No Image Available"); // More user-friendly message
            petImageLabel.setFont(new Font("SansSerif", Font.ITALIC, 14));
            petImageLabel.setForeground(Color.decode("#666666")); // Medium grey for error text
            System.err.println("Could not load image for: " + pet.getName() + " at path: " + pet.getImagePath());
        }
        petImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        petImageLabel.setBorder(new EmptyBorder(0, 0, 25, 0)); // Increased padding below image
        mainPanel.add(petImageLabel, BorderLayout.NORTH);

        // Pet Details (center)
        JPanel detailsPanel = new JPanel();
        detailsPanel.setOpaque(false);
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBorder(new EmptyBorder(0, 0, 20, 0)); // Padding above buttons

        JLabel nameLabel = new JLabel("<html><b style='color:#333333;'>Name:</b> <span style='color:#2B4576;'>" + pet.getName() + "</span></html>"); // Changed accent color
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 28)); // Modern font, size
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        String ageText;
        if (pet.getAge() == 0 && pet.getMonths() > 0) {
            ageText = pet.getMonths() + " months";
        } else if (pet.getAge() > 0 && pet.getMonths() > 0) {
            ageText = pet.getAge() + " years & " + pet.getMonths() + " months";
        } else {
            ageText = pet.getAge() + " years";
        }
        JLabel ageLabel = new JLabel("<html><b style='color:#333333;'>Age:</b> " + ageText + "</html>");
        ageLabel.setFont(new Font("SansSerif", Font.PLAIN, 19)); // Modern font, size
        ageLabel.setForeground(Color.decode("#333333")); // Dark grey text
        ageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Format the description with bolded labels and modern styling using HTML
        // Adjusted 'width' for the larger dialog size
        String rawDescription = pet.getDescription();
        String formattedDescription = "<html><body style='font-family:SansSerif; font-size:17px; color:#333333; margin:0; padding:0; width: 800px;'>" + // Dark grey for description text
                                      rawDescription.replace("Color:", "<b>Color:</b>")
                                                    .replace("Breed:", "<b>Breed:</b>")
                                                    .replace("Health Status:", "<b>Health Status:</b>")
                                                    .replace("Spayed/Neutered:", "<b>Spayed/Neutered:</b>")
                                                    .replace("Vaccinations & Deworm:", "<b>Vaccinations & Deworm:</b>")
                                                    .replace("Description:", "<b>Description:</b>")
                                                    .replace("\n", "<br>")
                                      + "</body></html>";

        JLabel descriptionLabel = new JLabel(formattedDescription);
        descriptionLabel.setFont(new Font("SansSerif", Font.PLAIN, 17)); // Ensures fallback even if HTML font isn't applied
        descriptionLabel.setForeground(Color.decode("#333333")); // Dark grey text
        descriptionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        descriptionLabel.setVerticalAlignment(SwingConstants.TOP);
        descriptionLabel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Added padding inside label for text to not touch edges

        JScrollPane descriptionScrollPane = new JScrollPane(descriptionLabel);
        descriptionScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); // Only show if needed
        descriptionScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        descriptionScrollPane.setBorder(BorderFactory.createLineBorder(Color.decode("#E0E0E0"), 1)); // Subtle border for scroll pane
        descriptionScrollPane.setOpaque(false);
        descriptionScrollPane.getViewport().setOpaque(false);
        // Removed fixed preferred/maximum sizes to allow BoxLayout to manage vertical space more freely

        detailsPanel.add(nameLabel);
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Adjusted gap
        detailsPanel.add(ageLabel);
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Adjusted gap before description
        detailsPanel.add(descriptionScrollPane);

        mainPanel.add(detailsPanel, BorderLayout.CENTER);

        // --- Button Panel Structure ---
        JPanel bottomButtonsContainer = new JPanel(new BorderLayout());
        bottomButtonsContainer.setOpaque(false);
        bottomButtonsContainer.setBorder(new EmptyBorder(20, 0, 0, 0)); // Top padding for separation

        JPanel topRowButtons = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0)); // Spacing between buttons
        topRowButtons.setOpaque(false);

        // Back button
        JButton backButton = createStyledButton("Back", Color.decode("#78909C")); // Secondary accent blue-grey
        backButton.addActionListener(e -> dispose());
        topRowButtons.add(backButton);

        // Donate button
        JButton donateButton = createStyledButton("Sponsor Me", Color.decode("#78909C")); // Secondary accent blue-grey
        donateButton.addActionListener(e -> {
            final String DONATE_URL = "https://docs.google.com/forms/d/e/1FAIpQLSda_jQUn0XWPIzr3Eli5bVkoWOW10H_VVsiQDWC-dWoHyiPMQ/viewform?usp=sharing&ouid=108321204493867680753";
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
        topRowButtons.add(donateButton);

        bottomButtonsContainer.add(topRowButtons, BorderLayout.NORTH);

        // Adopt This Dog/Cat button (bigger and at the bottom)
        String adoptButtonText = (pet instanceof Dog) ? "ADOPT THIS DOG!" : "ADOPT THIS CAT!";
        JButton adoptButton = createStyledButton(adoptButtonText, Color.decode("#2B4576")); // Changed accent color
        adoptButton.setPreferredSize(new Dimension(250, 55)); // Made significantly bigger
        adoptButton.setFont(new Font("SansSerif", Font.BOLD, 18)); // Larger font for prominence
        adoptButton.addActionListener(e -> {
            final String ADOPT_URL = "https://docs.google.com/forms/d/e/1FAIpQLSfid0_HZ6eX5P7FRYnQ2NTmOALPW6lwjK5EKyP-n515s8tbMQ/viewform?usp=sharing&ouid=108321204493867680753";
            try {
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                    Desktop.getDesktop().browse(new URI(ADOPT_URL));
                } else {
                    JOptionPane.showMessageDialog(this, "Cannot open browser. Please visit:\n" + ADOPT_URL, "Browser Not Supported", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error opening link: " + ex.getMessage(), "Error", JOptionPane.ABORT);
            }
        });

        JPanel adoptButtonWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Wrapper to center the button
        adoptButtonWrapper.setOpaque(false);
        adoptButtonWrapper.add(adoptButton);
        bottomButtonsContainer.add(adoptButtonWrapper, BorderLayout.SOUTH);

        mainPanel.add(bottomButtonsContainer, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    // Helper method to create consistently styled buttons
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 16)); // Modern font, bold
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE); // White text
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25)); // Padding
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add a subtle hover effect (darken background)
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            Color originalBg = bgColor;
            Color darkerBg;

            // Define darkerBg based on the original color for a consistent hover
            {
                if (originalBg.equals(Color.decode("#2B4576"))) { // Primary accent blue
                    darkerBg = Color.decode("#4A699A"); // Lighter blue for hover
                } else if (originalBg.equals(Color.decode("#78909C"))) { // Secondary accent blue-grey
                    darkerBg = Color.decode("#546E7A"); // Darker blue-grey for hover
                } else {
                    // Fallback for other colors, just darken
                    darkerBg = new Color(Math.max(0, originalBg.getRed() - 20),
                                         Math.max(0, originalBg.getGreen() - 20),
                                         Math.max(0, originalBg.getBlue() - 20));
                }
            }

            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(darkerBg);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(originalBg);
            }
        });

        // Custom UI for rounded corners
        button.setUI(new BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                JButton btn = (JButton) c;
                int width = btn.getWidth();
                int height = btn.getHeight();
                int arc = 25; // Increased rounded corner radius

                if (btn.getModel().isArmed()) {
                    g2.setColor(bgColor.darker()); // Darker when pressed
                } else {
                    g2.setColor(btn.getBackground());
                }
                g2.fillRoundRect(0, 0, width, height, arc, arc);

                // Paint the text
                super.paint(g2, c);
                g2.dispose();
            }
        });

        return button;
    }
}
