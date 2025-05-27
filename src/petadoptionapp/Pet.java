package petadoptionapp;

public abstract class Pet {
	private String name;
	private int age; // Age in years
	private int months; // Age in months (additional detail)
	private String description;
	private String imagePath; // Path to the image resource
	private String gender; // Added gender property

	public Pet(String name, int age, String description, String imagePath, String gender) {
		this(name, age, 0, description, imagePath, gender); // Call overloaded constructor with 0 months
	}

	public Pet(String name, int age, int months, String description, String imagePath, String gender) {
		this.name = name;
		this.age = age;
		this.months = months;
		this.description = description;
		this.imagePath = imagePath;
		this.gender = gender; // Initialize gender
	}

	// Getters and setters (Encapsulation)
	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public int getMonths() { // Getter for months
		return months;
	}

	public String getDescription() {
		return description;
	}

	public String getImagePath() {
		return imagePath;
	}

	public String getGender() { // Getter for gender
		return gender;
	}

	public abstract void displayDetails(); // Abstraction
}
