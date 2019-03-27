package main;

import main.Accounts.Account;
import main.Accounts.Bank;
import main.Accounts.Bitcoin;
import main.Accounts.Paypal;
import main.CarColorSchemes.DarkCarColorScheme;
import main.CarColorSchemes.ICarColorScheme;
import main.CarColorSchemes.LightCarColorScheme;
import main.Cars.Car;
import main.Cars.ICar;
import main.CashbackService.HighCashback;
import main.CashbackService.LowCashback;
import main.Enums.*;
import main.Factories.CarFactory;
import main.Factories.TransmissionFactory;
import main.Transmissions.Transmission;

import java.text.NumberFormat;
import java.util.Iterator;

public class Main {
    public static void main(String[] args){
        //testCreationalPatterns();
        //testStructuralPatterns();
        testBehavioralPatterns();
    }

    private static void testBehavioralPatterns() {
        testChainofResponsibility();
        testIterator();
        testMemento();
        testObserver();
        testState();
    }

    private static void testState() {
        System.out.println("\n- - -  State  - - -");
        PreferencesSingleton prefs = PreferencesSingleton.getInstance();

        Account account = new Paypal(120500);
        account.addObserver(prefs);
        Car car = CarFactory.buildCar(Manufacturer.BMW, CarType.SEDAN);
        car.setPrice(42800);

        prefs.setCashbackProfile(new LowCashback());
        car.purchase(account);

        prefs.setCashbackProfile(new HighCashback());
        car.purchase(account);
    }

    private static void testObserver() {
        System.out.println("\n- - -  Observer  - - -");
        Account bank = new Bank(38000);
        bank.addObserver(PreferencesSingleton.getInstance());

        Car car = CarFactory.buildCar(Manufacturer.MERCEDES, CarType.SEDAN);
        car.setPrice(27500);

        car.purchase(bank);
    }

    private static void testMemento() {
        System.out.println("\n- - -  Memento  - - -");
        ICar mercedesCar = CarFactory.buildCar(Manufacturer.MERCEDES, CarType.SAV);
        mercedesCar.setPrice(36750);

        Garage normalPriceGarage = Garage.FillWithCarsFacade(Manufacturer.AUDI, 2);
        GarageMemento garageMemento = normalPriceGarage.save();

        System.out.println("\n- Latest Garage State:");
        normalPriceGarage.addCars(mercedesCar);
        normalPriceGarage.showAllCars();

        System.out.println("\n- Restored Garage State:");
        normalPriceGarage.restore(garageMemento);
        normalPriceGarage.showAllCars();
    }

    private static void testIterator() {
        System.out.println("\n- - -  Iterator  - - -");
        Garage garage = Garage.FillWithCarsFacade(Manufacturer.BMW, 3);

        for(Iterator iterator = garage.getIterator(); iterator.hasNext();){
            ICar car = (ICar) iterator.next();
            System.out.println(car);
        }
    }

    private static void testChainofResponsibility() {
        System.out.println("\n- - -  Chain of Responsibility  - - -");
        Account bank = new Bank(3000);
        Account paypal = new Paypal(24800);
        Account bitcoin = new Bitcoin(82000);

        bank.setSuccessor(paypal);
        paypal.setSuccessor(bitcoin);

        Car expensiveCar = CarFactory.buildCar(Manufacturer.MERCEDES, CarType.LUXURY);
        expensiveCar.setPrice(48720);

        expensiveCar.purchase(bank);
    }

    private static void testStructuralPatterns(){
        testAdapter();
        testBridge();
        testComposite();
        testFacade();
        testProxy();
    }

    private static void testAdapter(){
        System.out.println("\n- - -  Adapter  - - -");
        PreferencesSingleton preferences = PreferencesSingleton.getInstance();

        Car car = CarFactory.buildCar(Manufacturer.AUDI, CarType.SEDAN);
        car.setYear(2012);
        ToyCar toyCar = new ToyCar(Manufacturer.LAND_ROVER, "'15 Defender Double Cab", CarType.SAV);
        toyCar.setReleaseYear(2017);
        ToyCarAdapter toyCarAdapter = new ToyCarAdapter(toyCar);

        System.out.println("Real car:");
        preferences.setFavoriteCar(car);
        preferences.showFavoriteCarInfo();

        System.out.println("Toy car:");
        preferences.setFavoriteCar(toyCarAdapter);
        preferences.showFavoriteCarInfo();
    }

    private static void testBridge(){
        System.out.println("\n- - -  Bridge  - - -");
        ICarColorScheme lightColorScheme = new LightCarColorScheme(Color.RED);
        ICarColorScheme darkColorScheme = new DarkCarColorScheme(Color.GREEN);

        Car car = CarFactory.buildCar(Manufacturer.MERCEDES, CarType.LUXURY);
        car.setColorScheme(lightColorScheme);

        ToyCar toyCar = new ToyCar(Manufacturer.FERRARI, "Speeder Wheels", CarType.SEDAN);
        toyCar.setColorScheme(darkColorScheme);

        System.out.println(car + "\n" + car.getColorScheme());
        System.out.println(toyCar + "\n" + toyCar.getColorScheme());
    }

    private static void testComposite(){
        System.out.println("\n- - -  Composite  - - -");
        ICar car1 = CarFactory.buildCar(Manufacturer.AUDI, CarType.SEDAN);
        car1.setPrice(42000);
        ICar car2 = CarFactory.buildCar(Manufacturer.BMW, CarType.SAV);
        car2.setPrice(31500);

        ToyCar toyCar = new ToyCar(Manufacturer.LAMBORGHINI, "The Dark Knight", CarType.SEDAN);
        toyCar.setPrice(32);
        ICar car3 = new ToyCarAdapter(toyCar);

        Garage worldwideGarage = new Garage();
        worldwideGarage.addCars(car1, car2, car3);

        String netPrice = NumberFormat.getIntegerInstance().format(worldwideGarage.getNetGaragePrice());
        System.out.println("* NET Garage Price: €" + netPrice);
    }

    private static void testFacade(){
        System.out.println("\n- - -  Facade  - - -");
        Garage bmwGarage = Garage.FillWithCarsFacade(Manufacturer.BMW, 4);
        bmwGarage.showAllCars();
        bmwGarage.applyDiscount(15);
        bmwGarage.showAllCars();
    }

    private static void testProxy(){
        System.out.println("\n- - -  Proxy  - - -");
        ICar car1 = CarFactory.buildCar(Manufacturer.AUDI, CarType.SEDAN);
        car1.setPrice(24000);
        ICar car2 = CarFactory.buildCar(Manufacturer.MERCEDES, CarType.SAV);
        car2.setPrice(72800);

        GarageProxy secureGarage = new GarageProxy();
        secureGarage.addCars(car1, car2);

        String netPrice = NumberFormat.getIntegerInstance().format(secureGarage.getNetGaragePrice());
        System.out.println("NET Garage Price: €" + netPrice);
        secureGarage.applyDiscount(15);
        secureGarage.openDoors("invalid");
        secureGarage.openDoors("$ecr@t");
        secureGarage.applyDiscount(15);

        netPrice = NumberFormat.getIntegerInstance().format(secureGarage.getNetGaragePrice());
        System.out.println("NET Garage Price: €" + netPrice);

        secureGarage.closeDoors();
    }


    private static void testCreationalPatterns(){
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
