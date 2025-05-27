package petadoptionapp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;

public class PetPanel extends JPanel {
	public PetPanel() {
		setBackground(Color.decode("#efefda"));

		JPanel containerPanel = new JPanel(new BorderLayout());
		containerPanel.setBorder(new EmptyBorder(0, 10, 0, 10));
		containerPanel.setBackground(Color.decode("#efefda"));

		JPanel petsPanel = new JPanel(new GridLayout(0, 2, 10, 10));
		petsPanel.setBackground(Color.decode("#efefda"));

		// Example pets for the homepage (a smaller selection)
		// !!! IMPORTANT: Update these imagePaths to actual paths within your resources folder !!!
		Pet[] pets = {
				new Cat("Whiskers", 2, "A fluffy, affectionate cat who loves to nap in sunny spots. Enjoys gentle head scratches and playing with string.", "/resources/cat1.jpg", "Female"),
				new Dog("Buddy", 3, "An energetic golden retriever mix. Loves long walks, playing fetch, and is very good with children.", "/resources/dog1.jpg", "Male"),
				new Cat("Mittens", 1, "A curious and playful kitten. Always looking for new adventures and loves chasing laser pointers.", "/resources/cat2.jpg", "Female"),
				new Dog("Max", 4, "A calm and loyal Labrador. Max is house-trained and loves to cuddle. He's great for a quiet home.", "/resources/dog2.jpg", "Male"),
		};

		for (Pet pet : pets) {
			JButton petButton;
			try {
				ImageIcon originalIcon = new ImageIcon(getClass().getResource(pet.getImagePath()));
				Image scaledImage = originalIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
				petButton = new JButton(new ImageIcon(scaledImage));
			} catch (NullPointerException ex) {
				petButton = new JButton("<html><center>Image Error:<br>" + pet.getName() + "</center></html>"); // Nicer fallback text
				petButton.setFont(new Font("Arial", Font.PLAIN, 12));
				petButton.setBackground(Color.LIGHT_GRAY);
				System.err.println("Could not load image for: " + pet.getName() + " at path: " + pet.getImagePath());
			}

			petButton.setPreferredSize(new Dimension(150, 150));
			petButton.setMargin(new Insets(0, 0, 0, 0));
			petButton.setBorderPainted(false);
			petButton.setContentAreaFilled(false);
			petButton.setOpaque(false);

			petButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// Need to get the parent frame to pass it to the dialog constructor
					Frame ownerFrame = (Frame) SwingUtilities.getWindowAncestor(PetPanel.this);
					PetDetailsDialog petDetailsDialog = new PetDetailsDialog(ownerFrame, pet);
					petDetailsDialog.setVisible(true);
				}
			});
			petsPanel.add(petButton);
		}

		containerPanel.add(petsPanel, BorderLayout.CENTER);
		add(containerPanel);
	}
}
