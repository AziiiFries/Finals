package petadoptionapp;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.awt.geom.RoundRectangle2D; // For rounded corners
import javax.swing.plaf.basic.BasicButtonUI; // For custom button UI

public class AllPetsPanel extends JPanel {
    private JFrame ownerFrame;
    private JPanel petsGridPanel;
    private ArrayList<Pet> fullPetList;

    private String currentPetTypeFilter = "All"; // "All", "Cat", "Dog"
    private String currentGenderFilter = "All"; // "All", "Male", "Female"

    public AllPetsPanel(JFrame ownerFrame) {
        this.ownerFrame = ownerFrame;
        setBackground(Color.decode("#F2F4F8")); // Consistent light grey background
        setLayout(new BorderLayout()); // Main panel uses BorderLayout

        // --- Top Section: Title and Filters ---
        JPanel topContainerPanel = new JPanel();
        topContainerPanel.setLayout(new BoxLayout(topContainerPanel, BoxLayout.Y_AXIS)); // Stack vertically
        topContainerPanel.setBackground(Color.decode("#F2F4F8")); // Consistent light grey background

        // Title for the All Pets page (Logo)
        JLabel titleLabel = new JLabel("Our Adoptable Fur Babies"); // More appealing title
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 36)); // Increased font size for wider look
        titleLabel.setForeground(Color.decode("#2B4576")); // Changed accent color
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center horizontally within BoxLayout
        titleLabel.setBorder(new EmptyBorder(20, 50, 20, 50)); // Added horizontal padding for wider appearance

        // Filter Panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 25, 10)); // Increased horizontal gap and vertical padding
        filterPanel.setBackground(Color.decode("#F2F4F8")); // Consistent light grey background
        filterPanel.setBorder(new EmptyBorder(0, 10, 15, 10)); // More bottom padding
        filterPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center horizontally within BoxLayout

        // Pet Type Filter (Dropdown)
        JLabel typeLabel = new JLabel("Type:");
        typeLabel.setFont(new Font("SansSerif", Font.BOLD, 16)); // Slightly larger font for labels
        typeLabel.setForeground(Color.decode("#333333")); // Dark grey text
        filterPanel.add(typeLabel);

        String[] petTypes = {"All", "Cat", "Dog"};
        JComboBox<String> typeDropdown = new JComboBox<>(petTypes);
        styleDropdown(typeDropdown); // Apply consistent styling
        typeDropdown.addActionListener(e -> {
            currentPetTypeFilter = (String) typeDropdown.getSelectedItem();
            updatePetDisplay();
        });
        filterPanel.add(typeDropdown);

        // Spacer between dropdowns
        filterPanel.add(Box.createRigidArea(new Dimension(40, 0))); // Larger gap

        // Gender Filter (Dropdown)
        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setFont(new Font("SansSerif", Font.BOLD, 16)); // Slightly larger font for labels
        genderLabel.setForeground(Color.decode("#333333")); // Dark grey text
        filterPanel.add(genderLabel);

        String[] genders = {"All", "Male", "Female"};
        JComboBox<String> genderDropdown = new JComboBox<>(genders);
        styleDropdown(genderDropdown); // Apply consistent styling
        genderDropdown.addActionListener(e -> {
            currentGenderFilter = (String) genderDropdown.getSelectedItem();
            updatePetDisplay();
        });
        filterPanel.add(genderDropdown);

        topContainerPanel.add(titleLabel);
        topContainerPanel.add(filterPanel);

        add(topContainerPanel, BorderLayout.NORTH); // Add the combined top section to NORTH

        // --- Pet Grid Panel (will be updated dynamically) ---
        petsGridPanel = new JPanel(new GridLayout(0, 3, 25, 25)); // Increased gaps, 3 columns
        petsGridPanel.setBackground(Color.decode("#F2F4F8")); // Consistent light grey background
        // Widen horizontal padding for margins
        petsGridPanel.setBorder(new EmptyBorder(30, 150, 30, 150)); // Top: 30, Left: 150, Bottom: 30, Right: 150

        // Use a JScrollPane for the pet grid to handle many pets
        JScrollPane scrollPane = new JScrollPane(petsGridPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder()); // Remove default scroll pane border
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Smooth scrolling
        scrollPane.setBackground(Color.decode("#F2F4F8")); // Consistent light grey background
        scrollPane.getViewport().setBackground(Color.decode("#F2F4F8")); // Ensure viewport matches background

        add(scrollPane, BorderLayout.CENTER);

        // Initialize the full list of pets (keeping content as is)
        fullPetList = new ArrayList<>(Arrays.asList(
            // Existing Cats
            new Cat("Whiskers", 2, 0, "A fluffy, affectionate cat who loves to nap in sunny spots. Enjoys gentle head scratches and playing with string.", "/resources/cat1.jpg", "Female"),
            new Cat("Mittens", 1, 0, "A curious and playful kitten. Always looking for new adventures and loves chasing laser pointers.", "/resources/cat2.jpg", "Female"),
            new Cat("Luna", 2, 0, "A graceful Siamese with piercing blue eyes. Independent but enjoys occasional lap time.", "/resources/cat3.jpg", "Female"),
            new Cat("Bella", 1, 0, "A sweet domestic shorthair, initially shy but blossoms into a very affectionate companion once comfortable.", "/resources/cat4.jpg", "Female"),
            new Cat("Shadow", 5, 0, "A sleek black cat, mysterious yet friendly. Enjoys quiet evenings and bird watching from windows.", "/resources/cat5.jpg", "Male"),
            new Cat("Cleo", 3, 0, "An elegant tabby, very independent but loves a good chin scratch. Prefers calm environments.", "/resources/cat6.jpg", "Female"),

            // --- Male Dog Rescues ---
            new Dog("Alexis", 2, 0,
                    "Color: Brown, Breed: Aspin\n" +
                    "Health Status: One eye surgically removed due to a past injury. Currently under medication maintenance but stable and responding well.\n" +
                    "Spayed/Neutered: Neutered\n" +
                    "Vaccinations & Deworm: Anti-rabies, 8-in-1 & Oral Deworm\n" +
                    "Description: Alexis was found in a vacant lot in Marikina City, alone and injured. Despite her early hardships, he remains incredibly gentle and affectionate.",
                    "/resources/dog_alexis.jpg", "Male"),
            new Dog("Arian", 1, 2, // 1 year & 2 months
                    "Color: Brown and White, Breed: Basenji\n" +
                    "Health Status: Allergic to Royal Canin dry dog food\n" +
                    "Spayed/Neutered: Neutered\n" +
                    "Vaccinations & Deworm: Anti-rabies, 8-in-1 & Oral Deworm\n" +
                    "Description: He was rescued as a malnourished stray pup searching for food and shelter. With love and care, he’s now healthy, happy, and ready for his forever home.",
                    "/resources/dog_arian.jpg", "Male"),
            new Dog("Billie", 1, 0,
                    "Color: Black and White, Breed: Border Collie\n" +
                    "Health Status: Left eye has cataract\n" +
                    "Spayed/Neutered: Not yet\n" +
                    "Vaccinations & Deworm: Anti-rabies, 8-in-1 & Oral Deworm\n" +
                    "Description: Billie was rescued from neglect but has blossomed into a lively, intelligent dog. Despite having a cataract in one eye, he’s full of energy and love.",
                    "/resources/dog_billie.jpg", "Male"),
            new Dog("Brisket", 0, 10, // 10 months
                    "Color: Off white and Gray, Breed: Maltese\n" +
                    "Health Status: No health issues\n" +
                    "Spayed/Neutered: Not yet\n" +
                    "Vaccinations & Deworm: Anti-rabies, 8-in-1 & Oral Deworm\n" +
                    "Description: Brisket was rescued after being abandoned in a box near a busy road, scared and hungry. Now safe and healthy, he’s a cheerful pup ready for a loving home.",
                    "/resources/dog_brisket.jpg", "Male"),
            new Dog("Brix", 0, 11, // 11 months
                    "Color: White, Breed: Shih Tzu\n" +
                    "Health Status: Underbite and has injury in left foot (still recovering)\n" +
                    "Spayed/Neutered: Not yet\n" +
                    "Vaccinations & Deworm: Anti-rabies, 8-in-1 & Oral Deworm\n" +
                    "Description: Brix was found limping near a roadside, likely abandoned, and was gently rescued by a kind passerby. He is now safe and recovering well from his foot injury.",
                    "/resources/dog_brix.jpg", "Male"),
            new Dog("Bruno", 3, 0,
                    "Color: Brown and Black, Breed: Belgian Malinois\n" +
                    "Health Status: No health issues\n" +
                    "Spayed/Neutered: Neutered\n" +
                    "Vaccinations & Deworm: Anti-rabies, 8-in-1 & Oral Deworm\n" +
                    "Description: Bruno was rescued after being spotted wandering alone near a construction site, hungry and scared. He was safely brought in and has since regained his strength and confidence.",
                    "/resources/dog_bruno.jpg", "Male"),
            new Dog("Brutos", 1, 0,
                    "Color: Brown and White, Breed: Aspin\n" +
                    "Health Status: No health issues\n" +
                    "Spayed/Neutered: Neutered\n" +
                    "Vaccinations & Deworm: Not specified\n" +
                    "Description: Brutos was rescued from the streets after being seen scavenging for food near a marketplace. He quickly adapted to care and is now thriving in a safe environment.",
                    "/resources/dog_brutos.jpg", "Male"),
            new Dog("Frankie", 0, 7, // 7 months
                    "Color: Multi-colored, Breed: Chihuahua\n" +
                    "Health Status: Has epilepsy\n" +
                    "Spayed/Neutered: Not yet\n" +
                    "Vaccinations & Deworm: Anti-rabies, 8-in-1 & Oral Deworm\n" +
                    "Description: Frankie was rescued after being abandoned outside a veterinary clinic, trembling and alone. Despite his epilepsy, he is now receiving the care he needs and continues to show a loving spirit.",
                    "/resources/dog_frankie.jpg", "Male"),
            new Dog("Leo", 2, 0,
                    "Color: Light Brown, Breed: Aspin\n" +
                    "Health Status: Not specified\n" +
                    "Spayed/Neutered: Not specified\n" +
                    "Vaccinations & Deworm: Anti-rabies, 8-in-1 & Oral Deworm\n" +
                    "Description: Leo was rescued from a drainage canal where he had been trapped and unable to escape. He was carefully brought to safety and is now doing well under care.",
                    "/resources/dog_leo.jpg", "Male"),
            new Dog("Lexus", 0, 11, // 11 months
                    "Color: Brown and White, Breed: Terrier\n" +
                    "Health Status: No health issues\n" +
                    "Spayed/Neutered: Not yet\n" +
                    "Vaccinations & Deworm: Anti-rabies, 8-in-1 & Oral Deworm\n" +
                    "Description: Lexus was rescued after being found tied to a post and left alone in the heat. He was taken in with care and is now safe and playful in a nurturing environment.",
                    "/resources/dog_lexus.jpg", "Male"),
            new Dog("Nico", 2, 0,
                    "Color: Brown and white, Breed: Corgi\n" +
                    "Health Status: Two lower feet is paralyzed due to car accident\n" +
                    "Spayed/Neutered: Neutered\n" +
                    "Vaccinations & Deworm: Anti-rabies, 8-in-1 & Oral Deworm\n" +
                    "Description: Nico was rescued after a car accident left his two lower legs paralyzed, and he was found dragging himself along a sidewalk. He was immediately brought to safety and is now adjusting well with proper care and support.",
                    "/resources/dog_nico.jpg", "Male"),
            new Dog("Raffy", 5, 0,
                    "Color: Brown and White, Breed: Aspin\n" +
                    "Health Status: Has back pain due to old age\n" +
                    "Spayed/Neutered: Neutered\n" +
                    "Vaccinations & Deworm: Anti-rabies, 8-in-1 & Oral Deworm\n" +
                    "Description: Raffy was rescued while wandering slowly along a rural road, showing signs of fatigue and age-related pain. He was gently taken in and now enjoys a calm and caring environment.",
                    "/resources/dog_raffy.jpg", "Male"),
            new Dog("Reno", 1, 0,
                    "Color: Black and White, Breed: Border Collie mix\n" +
                    "Health Status: Has scar on the back due to abusement\n" +
                    "Spayed/Neutered: Neutered\n" +
                    "Vaccinations & Deworm: Anti-rabies, 8-in-1 & Oral Deworm\n" +
                    "Description: Reno was rescued after being spotted tied up and neglected in a backyard, with visible scars on his back from abuse. He was freed and is now healing in a safe, loving environment.",
                    "/resources/dog_reno.jpg", "Male"),
            new Dog("Rupert", 1, 0,
                    "Color: Brown Black, Breed: Chihuahua\n" +
                    "Health Status: No health issues\n" +
                    "Spayed/Neutered: Not yet\n" +
                    "Vaccinations & Deworm: Anti-rabies, 8-in-1 & Oral Deworm\n" +
                    "Description: Rupert was rescued after being found hiding under a parked car during a heavy rain. He was gently coaxed out and brought to safety, now warm and well cared for.",
                    "/resources/dog_rupert.jpg", "Male"),
            new Dog("Toby", 1, 0,
                    "Color: Black, Breed: Chihuahua\n" +
                    "Health Status: 2 lower feet is paralyzed due to blood dieases\n" +
                    "Spayed/Neutered: Neutered\n" +
                    "Vaccinations & Deworm: Anti-rabies, 8-in-1 & Oral Deworm\n" +
                    "Description: Toby was rescued after being abandoned near a vacant lot, unable to walk due to paralysis in his lower legs caused by a blood disease. He is now receiving the care he needs in a safe and loving environment.",
                    "/resources/dog_toby.jpg", "Male"),

            // --- Female Dog Rescues ---
            new Dog("Alusha", 2, 0,
                    "Color: Light Brown, Breed: Golden Retriever\n" +
                    "Health Status: No health issues\n" +
                    "Spayed/Neutered: Spayed\n" +
                    "Vaccinations & Deworm: Anti-rabies, 8-in-1 & Oral Deworm\n" +
                    "Description: Alusha was rescued from a backyard breeder who could no longer care for her and her littermates. She was the smallest of the group but full of energy and love.",
                    "/resources/dog_alusha.jpg", "Female"),
            new Dog("Andy", 0, 5, // 5 months old
                    "Color: Black, Breed: Dachshund\n" +
                    "Health Status: No health issues\n" +
                    "Spayed/Neutered: Not yet\n" +
                    "Vaccinations & Deworm: 8-in-1 & Oral Deworm\n" +
                    "Description: Andy was found wandering alone near a marketplace, likely abandoned due to his breed's health maintenance needs. A kind passerby alerted rescuers just in time.",
                    "/resources/dog_andy.jpg", "Female"),
            new Dog("Biscoff", 0, 2, // 2 months old
                    "Color: Brown and White, Breed: Corgi\n" +
                    "Health Status: No health issues\n" +
                    "Spayed/Neutered: Not yet\n" +
                    "Vaccinations & Deworm: (1) 8-in-1 & Oral Deworm\n" +
                    "Description: Biscoff was discovered inside a cardboard box left outside a veterinary clinic. Despite his young age, he showed remarkable resilience and playfulness.",
                    "/resources/dog_biscoff.jpg", "Female"),
            new Dog("Bleu", 0, 3, // 3 months old
                    "Color: Black Brown, Breed: Rottweiler\n" +
                    "Health Status: Has allergy to Nutri chunks dry dog food\n" +
                    "Spayed/Neutered: Not yet\n" +
                    "Vaccinations & Deworm: 8-in-1 & Oral Deworm\n" +
                    "Description: Bleu was surrendered by a family who could not manage his dietary needs. He was malnourished and itchy but is now recovering in foster care.",
                    "/resources/dog_bleu.jpg", "Female"),
            new Dog("Cassie", 0, 5, // 5 months
                    "Color: Black, Breed: Labrador\n" +
                    "Health Status: Allergic to Aozi dry and wet dog food\n" +
                    "Spayed/Neutered: Not yet\n" +
                    "Vaccinations & Deworm: 8-in-1 & Oral Deworm\n" +
                    "Description: Cassie was rescued from a cramped cage at an overrun shelter. She had been overlooked due to her allergies and black fur, but she’s now thriving.",
                    "/resources/dog_cassie.jpg", "Female"),
            new Dog("Chichay", 1, 0,
                    "Color: Chocolate Brown, Breed: Labrador\n" +
                    "Health Status: Deaf due to abusement\n" +
                    "Spayed/Neutered: Spayed\n" +
                    "Vaccinations & Deworm: Anti-rabies, 8-in-1 & Oral Deworm\n" +
                    "Description: Chichay was found chained and abused in a backyard; her deafness is a lasting result of the trauma she endured. She has since blossomed into a gentle and loyal companion.",
                    "/resources/dog_chichay.jpg", "Female"),
            new Dog("Chiwa", 1, 0,
                    "Color: Brown, Breed: Chihuahua\n" +
                    "Health Status: Lactose Intolerance to any milk replacer\n" +
                    "Spayed/Neutered: Spayed\n" +
                    "Vaccinations & Deworm: Anti-rabies, 8-in-1 & Oral Deworm\n" +
                    "Description: Chiwa was rescued from a hoarding situation where she was living with over 20 dogs. Malnourished and shy, she’s slowly gaining confidence and trust.",
                    "/resources/dog_chiwa.jpg", "Female"),
            new Dog("Fiona", 1, 0,
                    "Color: White, Breed: Maltese\n" +
                    "Health Status: No health issues\n" +
                    "Spayed/Neutered: Not yet\n" +
                    "Vaccinations & Deworm: Anti-rabies, 8-in-1 & Oral Deworm\n" +
                    "Description: Fiona was left behind when her owners moved away. Neighbors reported her crying outside the empty home, and rescuers quickly took her in.",
                    "/resources/dog_fiona.jpg", "Female"),
            new Dog("Lani", 0, 1, // 1 month old
                    "Color: White, Breed: Aspin\n" +
                    "Health Status: In born deaf\n" +
                    "Spayed/Neutered: Not yet\n" +
                    "Vaccinations & Deworm: None\n" +
                    "Description: Lani was found in a garbage pile with her littermates, all suffering from neglect. Being inborn deaf, she stood out for her calm and cuddly nature.",
                    "/resources/dog_lani.jpg", "Female"),
            new Dog("Lucy", 0, 8, // 8 months old
                    "Color: Black and white, Breed: Shih Tzu\n" +
                    "Health Status: Deaf\n" +
                    "Spayed/Neutered: Not yet\n" +
                    "Vaccinations & Deworm: Anti-rabies, 8-in-1 & Oral Deworm\n" +
                    "Description: Lucy was surrendered to the shelter after her breeder deemed her \"unsellable\" due to her deafness. She has proven to be a sweet and loving pup.",
                    "/resources/dog_lucy_female.jpg", "Female"),
            new Dog("Lyka", 1, 0,
                    "Color: Brown and white, Breed: Aspin\n" +
                    "Health Status: No health issues\n" +
                    "Spayed/Neutered: Not yet\n" +
                    "Vaccinations & Deworm: Anti-rabies, 8-in-1 & Oral Deworm\n" +
                    "Description: Lyka was rescued from a roadside after being hit by a motorcycle. Fortunately, she had no lasting injuries and recovered quickly with care.",
                    "/resources/dog_lyka.jpg", "Female"),
            new Dog("Nami", 3, 0,
                    "Color: Brown and white, Breed: Half beagle\n" +
                    "Health Status: Left ear cannot hear\n" +
                    "Spayed/Neutered: Spayed\n" +
                    "Vaccinations & Deworm: Anti-rabies, 8-in-1 & Oral Deworm\n" +
                    "Description: Nami was rescued from a dog meat trade operation and bears the physical and emotional scars of her past. Her partial hearing loss has never dampened her gentle spirit.",
                    "/resources/dog_nami.jpg", "Female"),
            new Dog("Pearl", 4, 0,
                    "Color: Brown, Breed: Golden Retriever\n" +
                    "Health Status: Recovering from lipomas surgery\n" +
                    "Spayed/Neutered: Not yet\n" +
                    "Vaccinations & Deworm: Anti-rabies, 8-in-1 & Oral Deworm\n" +
                    "Description: Pearl was found tied outside during a storm, left behind by her previous owners. She had multiple untreated lipomas but is now healing well after surgery.",
                    "/resources/dog_pearl.jpg", "Female"),
            new Dog("Rocky", 3, 0, // Note: There's also a male Rocky, this is a female one
                    "Color: Brown, Breed: Golden Retriever\n" +
                    "Health Status: No health issues\n" +
                    "Spayed/Neutered: Spayed\n" +
                    "Vaccinations & Deworm: Anti-rabies, 8-in-1 & Oral Deworm\n" +
                    "Description: Rocky was rescued from a remote village where he was used for breeding. After years of neglect, he now enjoys the love and freedom he always deserved.",
                    "/resources/dog_rocky_female.jpg", "Female"),
            new Dog("Sasi", 0, 7, // 7 months old
                    "Color: Brown, Breed: Golden Retriever\n" +
                    "Health Status: No health status\n" +
                    "Spayed/Neutered: Not yet\n" +
                    "Vaccinations & Deworm: Anti-rabies, 8-in-1 & Oral Deworm\n" +
                    "Description: Sasi was found near a construction site, shivering and hungry. Workers said she had been there for days before she was finally rescued.",
                    "/resources/dog_sasi.jpg", "Female")
        ));

        updatePetDisplay(); // Initial display of all pets
    }

    // Helper method to style JComboBoxes
    private void styleDropdown(JComboBox<String> dropdown) {
        dropdown.setFont(new Font("SansSerif", Font.PLAIN, 15)); // Slightly larger font for dropdown items
        dropdown.setBackground(Color.WHITE); // Modern: White background
        dropdown.setForeground(Color.decode("#333333")); // Modern: Dark grey text
        dropdown.setCursor(new Cursor(Cursor.HAND_CURSOR));
        dropdown.setBorder(BorderFactory.createLineBorder(Color.decode("#E0E0E0"), 1)); // Modern: Light grey border
        dropdown.setRenderer(new DefaultListCellRenderer() { // For better dropdown item appearance
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setBorder(new EmptyBorder(7, 7, 7, 7)); // Increased padding for dropdown items
                if (isSelected) {
                    label.setBackground(Color.decode("#4A699A")); // Changed accent color (lighter blue for selection)
                    label.setForeground(Color.WHITE);
                } else {
                    label.setBackground(Color.WHITE);
                    label.setForeground(Color.decode("#333333"));
                }
                return label;
            }
        });
    }


    // Method to update the displayed pets based on filters
    private void updatePetDisplay() {
        petsGridPanel.removeAll(); // Clear existing pets

        for (Pet pet : fullPetList) {
            boolean matchesType = (currentPetTypeFilter.equals("All") ||
                                   (currentPetTypeFilter.equals("Cat") && pet instanceof Cat) ||
                                   (currentPetTypeFilter.equals("Dog") && pet instanceof Dog));

            boolean matchesGender = (currentGenderFilter.equals("All") ||
                                     pet.getGender().equalsIgnoreCase(currentGenderFilter));

            if (matchesType && matchesGender) {
                // Create a panel for each pet to hold image, name, and button
                JPanel petCard = new JPanel() {
                    private float scale = 1.0f;
                    private Timer scaleTimer;
                    private final int ANIMATION_STEPS = 5; // Number of steps for smooth animation
                    private final int ANIMATION_DELAY = 20; // Milliseconds per step

                    { // Instance initializer block for setup
                        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                        setOpaque(false); // Crucial for custom painting
                        setBorder(new EmptyBorder(15, 15, 15, 15)); // Inner padding

                        setPreferredSize(new Dimension(250, 350));
                        setMaximumSize(new Dimension(250, 350));
                        setMinimumSize(new Dimension(250, 350));

                        addMouseListener(new java.awt.event.MouseAdapter() {
                            @Override
                            public void mouseEntered(java.awt.event.MouseEvent evt) {
                                startScaleAnimation(1.05f); // Scale up by 5%
                            }

                            @Override
                            public void mouseExited(java.awt.event.MouseEvent evt) {
                                startScaleAnimation(1.0f); // Scale back to original
                            }
                        });
                    }

                    private void startScaleAnimation(float targetScale) {
                        if (scaleTimer != null && scaleTimer.isRunning()) {
                            scaleTimer.stop();
                        }

                        float startScale = scale;
                        float deltaScale = (targetScale - startScale) / ANIMATION_STEPS;

                        scaleTimer = new Timer(ANIMATION_DELAY, new ActionListener() {
                            int step = 0;
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                step++;
                                scale = startScale + deltaScale * step;
                                if (step >= ANIMATION_STEPS) {
                                    scale = targetScale; // Ensure final scale is exact
                                    ((Timer)e.getSource()).stop();
                                }
                                repaint();
                            }
                        });
                        scaleTimer.start();
                    }

                    @Override
                    protected void paintComponent(Graphics g) {
                        Graphics2D g2 = (Graphics2D) g.create();
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                        // Apply scaling transformation from the center
                        int cx = getWidth() / 2;
                        int cy = getHeight() / 2;
                        g2.translate(cx, cy);
                        g2.scale(scale, scale);
                        g2.translate(-cx, -cy);

                        int arc = 15; // Rounded corner radius for the card
                        int width = getWidth();
                        int height = getHeight();

                        // Draw subtle shadow before filling the background
                        g2.setColor(new Color(0, 0, 0, 15)); // Very subtle shadow color
                        g2.fill(new RoundRectangle2D.Double(2, 2, width - 2, height - 2, arc, arc)); // Offset shadow

                        // Fill background with white and rounded corners
                        g2.setColor(Color.WHITE); // Modern: White card background
                        g2.fillRoundRect(0, 0, width, height, arc, arc);

                        // Call super.paintComponent to paint children (image, name, button)
                        // This must happen after background but before border for correct layering
                        super.paintComponent(g2); // Pass the transformed graphics context

                        g2.dispose(); // Dispose the copy
                    }

                    @Override
                    protected void paintBorder(Graphics g) {
                        Graphics2D g2 = (Graphics2D) g.create();
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                        // Apply the same scaling transformation for the border
                        int cx = getWidth() / 2;
                        int cy = getHeight() / 2;
                        g2.translate(cx, cy);
                        g2.scale(scale, scale);
                        g2.translate(-cx, -cy);

                        int arc = 15; // Rounded corner radius for the border
                        int width = getWidth();
                        int height = getHeight();

                        // Draw grey rounded border (slightly narrower)
                        g2.setColor(Color.decode("#E0E0E0")); // Modern: Light grey border
                        g2.setStroke(new BasicStroke(1)); // 1 pixel width
                        g2.drawRoundRect(0, 0, width - 1, height - 1, arc, arc);
                        g2.dispose();
                    }
                };

                // Pet Image Label
                JLabel petImageLabel = new JLabel();
                try {
                    ImageIcon originalIcon = new ImageIcon(getClass().getResource(pet.getImagePath()));
                    // Scale image to 250x250 to fit within the fixed card size
                    Image scaledImage = originalIcon.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
                    petImageLabel.setIcon(new ImageIcon(scaledImage));
                } catch (NullPointerException ex) {
                    petImageLabel.setText("<html><center>Image Error:<br>" + pet.getName() + "</center></html>");
                    petImageLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
                    petImageLabel.setBackground(Color.LIGHT_GRAY);
                    System.err.println("Could not load image for: " + pet.getName() + " at path: " + pet.getImagePath());
                }
                petImageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                petImageLabel.setBorder(new EmptyBorder(0, 0, 10, 0)); // Padding below image


                // Add pet's name below the image within the card
                JLabel petNameLabel = new JLabel(pet.getName());
                petNameLabel.setFont(new Font("SansSerif", Font.BOLD, 18)); // Slightly larger font for name
                petNameLabel.setForeground(Color.decode("#333333")); // Modern: Dark grey text
                petNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                petNameLabel.setBorder(new EmptyBorder(5, 0, 10, 0)); // Padding above and below name

                // "ABOUT ME" button
                JButton aboutMeButton = new JButton("ABOUT ME");
                aboutMeButton.setFont(new Font("SansSerif", Font.BOLD, 14));
                aboutMeButton.setForeground(Color.decode("#2B4576")); // Changed accent color
                aboutMeButton.setBackground(Color.WHITE); // Modern: White background
                aboutMeButton.setFocusPainted(false);
                aboutMeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
                aboutMeButton.setBorder(BorderFactory.createLineBorder(Color.decode("#E0E0E0"), 1)); // Modern: Light grey border
                aboutMeButton.setPreferredSize(new Dimension(120, 40)); // Fixed size for consistency
                aboutMeButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the button

                // Custom UI for rounded corners for the ABOUT ME button
                aboutMeButton.setUI(new BasicButtonUI() {
                    @Override
                    public void paint(Graphics g, JComponent c) {
                        Graphics2D g2 = (Graphics2D) g.create();
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                        JButton btn = (JButton) c;
                        int width = btn.getWidth();
                        int height = btn.getHeight();
                        int arc = 10; // Smaller rounded corner radius for button

                        g2.setColor(btn.getBackground());
                        g2.fillRoundRect(0, 0, width, height, arc, arc);

                        // Draw border
                        g2.setColor(Color.decode("#E0E0E0")); // Modern: Light grey border
                        g2.drawRoundRect(0, 0, width - 1, height - 1, arc, arc);

                        // Paint the text
                        super.paint(g2, c);
                        g2.dispose();
                    }
                });

                // Add hover effect for the ABOUT ME button
                aboutMeButton.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseEntered(java.awt.event.MouseEvent evt) {
                        aboutMeButton.setBackground(Color.decode("#F0F0F0")); // Modern: Very subtle hover
                    }

                    public void mouseExited(java.awt.event.MouseEvent evt) {
                        aboutMeButton.setBackground(Color.WHITE); // Modern: Back to white
                    }
                });


                aboutMeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        PetDetailsDialog petDetailsDialog = new PetDetailsDialog(ownerFrame, pet);
                        petDetailsDialog.setVisible(true);
                    }
                });

                // Add components to the petCard
                petCard.add(petImageLabel);
                petCard.add(petNameLabel);
                petCard.add(aboutMeButton);


                petsGridPanel.add(petCard);
            }
        }

        petsGridPanel.revalidate(); // Re-layout the panel
        petsGridPanel.repaint(); // Repaint the panel
    }
}
