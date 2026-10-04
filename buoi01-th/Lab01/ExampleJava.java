import java.util.Scanner;
class Lion{
    String name;
    float weight;
    float eat;
}
class Snake{
    String name;
    float weight;
    float length;
}
class Monkey{
    String name;
    float weight;
    String food;

}
public class ExampleJava {
    public static void main(String[] args) {
        //System.out.println("Hello, World!");
        Scanner scanner = new Scanner(System.in);
        Lion lion = new Lion();
        Snake snake = new Snake();
        Monkey monkey = new Monkey();
        System.out.print("Name Lion>");
        lion.name = scanner.nextLine();
        System.out.print("Weight Lion>");
        lion.weight = scanner.nextFloat();
        scanner.nextLine();
        System.out.print("Daily eat>");
        lion.eat = scanner.nextFloat();
        scanner.nextLine();
        //
        System.out.print("Name Snake>");
        snake.name = scanner.nextLine();
        System.out.print("Weight Snake>");
        snake.weight = scanner.nextFloat();
        scanner.nextLine();
        System.out.print("Length Snake>");
        snake.length = scanner.nextFloat();
        scanner.nextLine();
        //
        System.out.print("Name Monkey>");
        monkey.name = scanner.nextLine();
        System.out.print("Weight Monkey>");
        monkey.weight = scanner.nextFloat();
        scanner.nextLine();
        System.out.print("Favourite food>");
        monkey.food = scanner.nextLine();
        //
        System.out.println("Name Lion: "+lion.name+" Weight: "+lion.weight+" Dayli eat: "+lion.eat);
        System.out.println("Name Snake: "+snake.name+" Weight: "+snake.weight+" Lenght: "+snake.length);
        System.out.println("Name Monkey: "+monkey.name+" Weight: "+monkey.weight+" Favourite food: "+monkey.food);
      
      
      
    }
}