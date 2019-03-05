package main;

import main.Cars.Car;
import main.Enums.*;
import main.Transmissions.Transmission;

public class Main {
    public static void main(String[] args){
        testAbstractFactory();
        testPrototyping();
        testBuilder();
        testFactory();
        testSingleton();
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

    private static void testBuilder() {
        System.out.println("\n- - -  Builder  - - -");
        Engine engine1 = new Engine.EngineBuilder("M42P18", 4, FuelType.DIESEL)
                .type(EngineType.STRAIGHT)
                .year(2017)
                .country("Germany")
                .build();

        Engine engine2 = new Engine.EngineBuilder("R10G14", 4, FuelType.GAS)
                .year(2004)
                .build();

        Engine engine3 = new Engine.EngineBuilder("X33P30", 8, FuelType.PETROL)
                .type(EngineType.V_TYPE)
                .country("Germany")
                .build();

        Engine engine4 = new Engine.EngineBuilder("EL6E20", 6, FuelType.HYBRID)
                .year(2018)
                .build();

        System.out.println(engine1);
        System.out.println(engine2);
        System.out.println(engine3);
        System.out.println(engine4);
    }

    private static void testFactory(){
        System.out.println("\n- - -  Factory  - - -");
        Transmission transmission1 = TransmissionFactory.buildTransmission(TransmissionType.TIPTRONIC, 6);

        Engine engine = new Engine.EngineBuilder("M51P34", 8, FuelType.PETROL)
                .type(EngineType.W_TYPE)
                .transmission(transmission1)
                .build();

        System.out.println(engine.getTransmission());
        engine.getTransmission().setGears(7);
        engine.getTransmission().setShiftTimeMS(100);
        System.out.println(engine.getTransmission());
    }

    private static void testSingleton(){
        System.out.println("\n- - -  Singleton  - - -");
        PreferencesSingleton preferences = PreferencesSingleton.getInstance();
        preferences.setOwner("Deniz Dincer");
        int counter = 1;

        System.out.println("Cars manufactured:");
        for (Car car: preferences.getManufacturedCars()) {
            System.out.println(counter + ": " + car);
            counter++;
        }

        counter = 1;
        System.out.println("\nEngines manufactured:");
        for (Engine engine: preferences.getManufacturedEngines()) {
            System.out.println(counter + ": " + engine);
            counter++;
        }
    }
}
