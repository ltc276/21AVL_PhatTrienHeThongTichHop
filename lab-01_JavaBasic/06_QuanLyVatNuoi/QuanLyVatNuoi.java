// Bai 6: Quan ly ba vat nuoi: su tu co luong thit an moi ngay, ran co chieu dai,
// va khi co thuc an yeu thich. Cac lop dung chung ten va can nang.
public class QuanLyVatNuoi {
    public static void main(String[] args) {
        Lion lion = new Lion("Leo", 300, 5);
        Snake snake = new Snake("Boa", 50, 5);
        Monkey monkey = new Monkey("George", 150, "chuoi");

        System.out.println("Su tu " + lion.name + " nang " + lion.weight
                + " can va an " + lion.eat + " can thit moi ngay.");
        System.out.println("Ran " + snake.name + " nang " + snake.weight
                + " can va dai " + snake.length + " met.");
        System.out.println("Khi " + monkey.name + " nang " + monkey.weight
                + " can va thich an " + monkey.favoriteFood + ".");
    }
}

class Animal {
    String name;
    int weight;

    Animal(String name, int weight) {
        this.name = name;
        this.weight = weight;
    }
}

class Lion extends Animal {
    int eat;

    Lion(String name, int weight, int eat) {
        super(name, weight);
        this.eat = eat;
    }
}

class Snake extends Animal {
    int length;

    Snake(String name, int weight, int length) {
        super(name, weight);
        this.length = length;
    }
}

class Monkey extends Animal {
    String favoriteFood;

    Monkey(String name, int weight, String favoriteFood) {
        super(name, weight);
        this.favoriteFood = favoriteFood;
    }
}
