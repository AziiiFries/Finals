package petadoptionapp;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;

public class ContactDialog extends JDialog {

    public ContactDialog(Frame owner, String contactNumber, String contactEmail) {
        super(owner, "Contact Us", true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setUndecorated(true);

        setSize(320, 250);

        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int arc = 20;
                int x = 0;
                int y = 0;
                int width = getWidth();
                int height = getHeight();

                g2.setColor(Color.decode("#efefda"));
                g2.fillRoundRect(x, y, width, height, arc, arc);

                g2.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(1));
                g2.drawRoundRect(x, y, width - 1, height - 1, arc, arc);
            }
        };
        mainPanel.setOpaque(false);
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Get in Touch!");
        titleLabel.setFont(new Font("Arial Narrow", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0, 0, 128));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel detailsPanel = new JPanel();
        detailsPanel.setOpaque(false);
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBorder(new EmptyBorder(15, 0, 15, 0));

        JLabel numberLabel = new JLabel("<html><b>Phone:</b> " + contactNumber + "</html>");
        numberLabel.setFont(new Font("Arial Narrow", Font.PLAIN, 18));
        numberLabel.setForeground(Color.BLACK);
        numberLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Make the email label clickable
        JLabel emailLabel = new JLabel("<html><b>Email: <b>" + contactEmail + "</a></html><a href=''>");
        emailLabel.setFont(new Font("Arial Narrow", Font.PLAIN, 18));
        emailLabel.setForeground(Color.BLUE);
        emailLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        emailLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    Desktop.getDesktop().mail(new URI("mailto:" + contactEmail));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(ContactDialog.this, "Unable to open email client.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        detailsPanel.add(numberLabel);
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        detailsPanel.add(emailLabel);

        mainPanel.add(detailsPanel, BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.setFont(new Font("Arial Narrow", Font.BOLD, 16));
        closeButton.setBackground(new Color(0, 0, 128));
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);
        buttonPanel.add(closeButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        setLocationRelativeTo(owner);
    }
}
