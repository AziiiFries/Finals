package petadoptionapp;

public class Dog extends Pet {
	public Dog(String name, int age, String description, String imagePath, String gender) {
		super(name, age, description, imagePath, gender);
	}

	public Dog(String name, int age, int months, String description, String imagePath, String gender) { // Overloaded
		super(name, age, months, description, imagePath, gender);
	}

	@Override
	public void displayDetails() {
		System.out.println("Dog: " + getName() + ", Age: " + getAge() + ", Description: " + getDescription() + ", Gender: " + getGender());
	}
}
