package main;

import main.Enums.CarType;
import main.Enums.Manufacturer;

public class Main {
    public static void main(String[] args){
        testAbstractFactory();
        testPrototyping();
    }

    private static void testAbstractFactory() {
        System.out.println("\n- - -  Abstract Factory  - - -");
        Car car1 = CarFactory.buildCar(Manufacturer.BMW, CarType.LUXURY);
        Car car2 = CarFactory.buildCar(Manufacturer.BMW, CarType.SAV);
        Car car3 = CarFactory.buildCar(Manufacturer.MERCEDES, CarType.SEDAN);
        System.out.println(car1);
        System.out.println(car2);
        System.out.println(car3);
    }

    private static void testPrototyping() {
        System.out.println("\n- - -  Prototyping  - - -");
        Car prototype = CarFactory.buildCar(Manufacturer.AUDI, CarType.LUXURY);
        Car copy = prototype.clone();
        copy.setYear(3029);
        System.out.println("Prototype: " + prototype);
        System.out.println("Copy: " + copy);
    }
}
