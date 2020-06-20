package machine;

import java.util.Scanner;

//Este codigo se puede mejorar con un metodo makeCoffee() para abstraer makeLatte, Capucchino o expreso.
//Tambien se puede mejorar la legibilidad usando constantes para los ingredientes de cada cafe

class Main {
    public static void main(String[] args) {
        Machine coffeeMachine = new Machine(400, 540, 120, 9, 550);
        coffeeMachine.run();
    }
}

class Machine {

    private int water;
    private int milk;
    private int gramsCoffee;
    private int cups;
    private int money;
    private Scanner scanner;


    Machine(int water, int milk, int gramsCoffee, int cups, int money) {
        this.water = water;
        this.milk = milk;
        this.gramsCoffee = gramsCoffee;
        this.cups = cups;
        this.money = money;
        this.scanner = new Scanner(System.in);
    }

    public void run() {

        while (true) {
            System.out.println("Write action (buy, fill, take, remaining, exit):");
            String input = scanner.nextLine();
            System.out.println();
            switch (input) {
                case "buy":
                    buy();
                    break;
                case "fill":
                    fill();
                    break;
                case "take":
                    take();
                    break;
                case "remaining":
                    printStatus();
                    break;
                case "exit":
                    break;
            }
            if (input.equals("exit")) {
                break;
            }
        }
    }

    private void printStatus() {
        System.out.println(this);
    }

    private void buy() {
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu: ");
        String input = scanner.nextLine();
        switch (input) {
            case "1":
                makeEspresso();
                break;
            case "2":
                makeLatte();
                break;
            case "3":
                makeCappuccino();
                break;
            case "back":
                System.out.println();
                break;
        }
    }

    private void makeEspresso() {
        if (water >= 250 && gramsCoffee >= 16 && cups >= 1) {
            this.water -= 250;
            this.gramsCoffee -= 16;
            this.money += 4;
            this.cups -= 1;
            System.out.println("I have enough resources, making you a coffee!");
        } else if (water < 250) {
            System.out.println("Not enough water!");
        } else if (gramsCoffee < 16) {
            System.out.println("Not enough coffee beans!");
        } else {
            System.out.println("There are not cups!");
        }
        System.out.println();
    }

    private void makeLatte() {
        if (water >= 350 && gramsCoffee >= 20 && milk >= 75 && cups >= 1) {
            this.water -= 350;
            this.milk -= 75;
            this.gramsCoffee -= 20;
            this.money += 7;
            this.cups -= 1;
            System.out.println("I have enough resources, making you a coffee!");
        } else if (water < 350) {
            System.out.println("Not enough water!");
        } else if (gramsCoffee < 20) {
            System.out.println("Not enough coffee beans!");
        } else if (cups < 1) {
            System.out.println("There are not cups!");
        } else {
            System.out.println("Not enough milk!");
        }
        System.out.println();
    }

    private void makeCappuccino() {
        if (water >= 200 && gramsCoffee >= 12 && cups >= 1 && milk >= 100) {
            this.water -= 200;
            this.milk -= 100;
            this.gramsCoffee -= 12;
            this.money += 6;
            this.cups -= 1;
            System.out.println("I have enough resources, making you a coffee!");
        } else if (water < 200) {
            System.out.println("Not enough water!");
        } else if (gramsCoffee < 12) {
            System.out.println("Not enough coffee beans!");
        } else if (cups < 1) {
            System.out.println("There are not cups!");
        } else {
            System.out.println("Not enough milk!");
        }
        System.out.println();
    }

    private void fill() {
        System.out.println("Write how many ml of water do you want to add:");
        int water = Integer.parseInt(scanner.nextLine());
        System.out.println("Write how many ml of milk do you want to add:");
        int milk = Integer.parseInt(scanner.nextLine());
        System.out.println("Write how many grams of coffee beans do you want to add: ");
        int coffeeBeans = Integer.parseInt(scanner.nextLine());
        System.out.println("Write how many disposable cups of coffee do you want to add: ");
        int cups = Integer.parseInt(scanner.nextLine());
        this.water += water;
        this.milk += milk;
        this.gramsCoffee += coffeeBeans;
        this.cups += cups;
        System.out.println();
    }

    private void take() {
        System.out.println();
        System.out.println("I give you $" + money);
        this.money -= money;
        System.out.println();
    }

    @Override
    public String toString() {
        return "The coffee machine has: \n" +
                water + " of water\n" +
                milk + " of milk\n" +
                gramsCoffee + " of coffee beans\n" +
                cups + " of disposable cups\n" +
                money + " of money\n";
    }
}

