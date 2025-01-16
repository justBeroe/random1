package random;
class Animal {
    public void sound() {
        System.out.println("Animal makes a sound");
    }
}

class Dog extends Animal {
    @Override
    public void sound() {
        System.out.println("Dog barks");
    }
}
public class MethodOverriding {

    public static void main(String[] args) {
        Animal animal = new Animal();
        animal.sound();  // Calls the parent class method

        Animal dog = new Dog();
        dog.sound();     // Calls the overridden method in Dog class
    }
}
