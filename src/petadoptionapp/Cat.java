package petadoptionapp;

public class Cat extends Pet {
	public Cat(String name, int age, String description, String imagePath, String gender) {
		super(name, age, description, imagePath, gender);
	}

	public Cat(String name, int age, int months, String description, String imagePath, String gender) { // Overloaded
		super(name, age, months, description, imagePath, gender);
	}

	@Override
	public void displayDetails() {
		System.out.println("Cat: " + getName() + ", Age: " + getAge() + ", Description: " + getDescription() + ", Gender: " + getGender());
	}
}
