package petadoptionapp;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ContactDialog extends JDialog {

    public ContactDialog(Frame owner, String contactNumber, String contactEmail) {
        super(owner, "Contact Us", true); // true makes it modal (blocks owner until closed)
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setUndecorated(true); // Remove default window decorations for custom look

        // Set a fixed size for the dialog to match the app's phone-like aesthetic
        setSize(320, 250); // Adjusted size for contact dialog

        // Main panel for the dialog content with rounded corners
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int arc = 20; // Radius for rounded corners
                int x = 0;
                int y = 0;
                int width = getWidth();
                int height = getHeight();

                // Fill background
                g2.setColor(Color.decode("#efefda")); // Your app's background color
                g2.fillRoundRect(x, y, width, height, arc, arc);

                // Draw border
                g2.setColor(Color.BLACK); // Border color
                g2.setStroke(new BasicStroke(1)); // Border thickness
                g2.drawRoundRect(x, y, width - 1, height - 1, arc, arc);
            }
        };
        mainPanel.setOpaque(false); // Make sure it's transparent for custom painting
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Padding inside the dialog

        // Title Label
        JLabel titleLabel = new JLabel("Get in Touch!");
        titleLabel.setFont(new Font("Arial Narrow", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 0, 128)); // Navy Blue
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Contact details panel
        JPanel detailsPanel = new JPanel();
        detailsPanel.setOpaque(false); // Make it transparent to show mainPanel's background
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBorder(new EmptyBorder(15, 0, 15, 0)); // Vertical padding

        JLabel numberLabel = new JLabel("<html><b>Phone:</b> " + contactNumber + "</html>");
        numberLabel.setFont(new Font("Arial Narrow", Font.PLAIN, 18));
        numberLabel.setForeground(Color.BLACK);
        numberLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel emailLabel = new JLabel("<html><b>Email:</b> " + contactEmail + "</html>");
        emailLabel.setFont(new Font("Arial Narrow", Font.PLAIN, 18));
        emailLabel.setForeground(Color.BLACK);
        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        detailsPanel.add(numberLabel);
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Space between labels
        detailsPanel.add(emailLabel);

        mainPanel.add(detailsPanel, BorderLayout.CENTER);

        // Close button
        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Arial Narrow", Font.BOLD, 16));
        closeButton.setBackground(new Color(0, 0, 128)); // Navy Blue background
        closeButton.setForeground(Color.WHITE); // White text
        closeButton.setFocusPainted(false); // Remove focus border
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.addActionListener(e -> dispose()); // Close the dialog when clicked

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.add(closeButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel); // Set the custom panel as the dialog's content
        setLocationRelativeTo(owner); // Center relative to the owner frame
    }
}
