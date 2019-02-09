# JavaDesignPatterns
Implementation of different design patterns in Java.

> Design patterns, are solutions for most commonly (and frequently) occurred problems while designing a software. These patterns are mostly _“evolved”_ rather than _“discovered”_. A lot of learning, by lots of professional, have been summarized into these design patterns. None of these patterns force you anything in regard to implementation; they are just guidelines to solve a particular problem – in a particular way – in particular contexts. Code implementation is your responsibility.


## Creational Patterns:
Creational patterns often used in place of direct instantiation with constructors. They make the creation process more adaptable and dynamic. In particular, they can provide a great deal of flexibility about which objects are created, how those objects are created, and how they are initialized.

| DESIGN PATTERN | PURPOSE |
| -------------- | :------ |
| Builder        | Builder design pattern is an alternative way to construct complex objects and should be used only when we want to build different types of immutable objects using same object building process. |
| Prototype      | Prototype design pattern is used in scenarios where application needs to create a large number of instances of a class, which have almost same state or differ very little. |
| Factory        | Factory design pattern is most suitable when complex object creation steps are involved. To ensure that these steps are centralized and not exposed to composing classes. |
| Abstract Factory| Abstract factory pattern is used whenever we need another level of abstraction over a group of factories created using factory pattern. |
| Singleton      | Singleton enables an application to have one and only one instance of a class per JVM. |

---

## Singleton Pattern:
Singleton is mainly used when we want to have only one instance of a class inside JVM. It helps keeping the information in one place and not mess with instances.

Singleton is used in _![PreferencesSingleton.java](/src/main/PreferencesSingleton.java)_ The purpose of this class is to gather all created (instantiated) Cars and Engines since the program start.

```java
public class PreferencesSingleton {
    // make instance variable volatile to exclude returning unconstructed object
    private static volatile PreferencesSingleton instance;
    // variables we want to keep track of
    private String owner;
    private List<Car> manufacturedCars;
    private List<Engine> manufacturedEngines;

    // instantiate Lists on first construction of this singleton
    private PreferencesSingleton(){
        manufacturedCars = new ArrayList<>();
        manufacturedEngines = new ArrayList<>();
    }

    // this class will be initialized only once
    public static PreferencesSingleton getInstance(){
        if (instance == null){
            // synchronized prevents creation of many instances when different
            // threads access empty instance variable and try to create new one
            synchronized (PreferencesSingleton.class){
                // Thread-Safe (Double-checked locking)
                if (instance == null){
                    instance = new PreferencesSingleton();
                }
            }
        }
        return instance;
    }

    // returns list of all cars manufactured since this singleton was initialized
    public List<Car> getManufacturedCars(){
        return this.manufacturedCars;
    }

    // append newly manufactured cars using this method
    public void appendManufacturedCar(Car car){
        this.manufacturedCars.add(car);
    }

    ...
}
```

#### Test Builder Pattern

```java
public class Main {
    public static void main(String[] args){
        PreferencesSingleton preferences = PreferencesSingleton.getInstance();

        System.out.println("Cars manufactured:");
        for (Car car: preferences.getManufacturedCars()) {
            System.out.println(car);
        }

        counter = 1;
        System.out.println("\nEngines manufactured:");
        for (Engine engine: preferences.getManufacturedEngines()) {
            System.out.println(engine);
        }
    }
}
```

This will result in following output if you have already instantiated some classes before:

```
Cars manufactured:
BMW L8300(LUXURY) manufactured in 2019
BMW X7700(SAV) manufactured in 2019
MERCEDES S1500(SEDAN) manufactured in 2019
AUDI L1000(LUXURY) manufactured in 2019

Engines manufactured:
Engine: M42P18 (STRAIGHT-4) DIESEL, 177hP | Manufactured in 2017 - Germany
Engine: R10G14 (4-cylinder) GAS, 91hP | Manufactured in 2004
Engine: X33P30 (V_TYPE-8) PETROL, 439hP | Manufactured in Germany
Engine: EL6E20 (6-cylinder) HYBRID, 223hP | Manufactured in 2018
Engine: M51P34 (W_TYPE-8) PETROL, 437hP
```

---

## Builder Pattern:
The builder pattern is mainly useful when you want to build immutable objects (i.e. your class does not have setters). Using builder, we can attach different parts to our objects. Builder pattern can be seen in _![Engine.java](/src/main/Engine.java):_

```java
public class Engine {
    // optional variables are making use of builder pattern
    private final String model; // required
    private final int cylinders; // required
    private final FuelType fuelType; // required
    private final int horsePower; // calculated
    private final EngineType type; // optional
    private final int year; // optional
    private final String country; // optional
    private final Transmission transmission; // optional

    // an "Engine" will be copied from the builder object values that will be created below
    private Engine(EngineBuilder builder){
        this.model = builder.model;
        this.cylinders = builder.cylinders;
        this.fuelType = builder.fuelType;
        this.horsePower = builder.horsePower;
        this.type = builder.type;
        this.year = builder.year;
        this.country = builder.country;
        this.transmission = builder.transmission;
    }

    // Disclude setters to provide immutability
    public String getModel(){
        return this.model;
    }

    public EngineType getType(){
        return this.type;
    }

    Transmission getTransmission(){
        return this.transmission;
    }

    // and other getters that are similar to the ones above
    // ...

    // main builder of our Engine. It will have all of the variables from the main
    // class and will assign null if these (optional) variables are not mentioned
    static class EngineBuilder {
        // we can make required variables final as they will not
        // change once we instantiate this builder class
        private final String model;
        private final int cylinders;
        private final FuelType fuelType;
        private int horsePower;
        private EngineType type;
        private int year;
        private String country;
        private Transmission transmission;

        // builder is first being initialized with required variables
        EngineBuilder(String model, int cylinders, FuelType fuelType){
            if (model == null || model.isEmpty()) {
                throw new InvalidParameterException();
            }

            this.model = model;
            this.cylinders = cylinders;
            this.fuelType = fuelType;
        }

        // if "user" wants to assign values to optional variables, he will be using this method
        EngineBuilder type(EngineType type){
            this.type = type;
            return this;
        }

        EngineBuilder transmission(Transmission transmission){
            this.transmission = transmission;
            return this;
        }

        // other optional variable setters similar to the ones above
        // ...

        // once we collected all of the information, we can send everything to the
        // main Engine constructor to instantiate an immutable object and return it.
        Engine build(){
            this.horsePower = CalculateHorsePower(this.cylinders, this.fuelType, this.type);
            Engine engine = new Engine(this);
            PreferencesSingleton.getInstance().appendManufacturedEngine(engine);
            return engine;
        }
    }
}
```

#### Test Builder Pattern

```java
public class Main {
    public static void main(String[] args){
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
}
```

This will result in following output:

```
Engine: M42P18 (STRAIGHT-4) DIESEL, 177hP | Manufactured in 2017 - Germany
Engine: R10G14 (4-cylinder) GAS, 91hP | Manufactured in 2004
Engine: X33P30 (V_TYPE-8) PETROL, 439hP | Manufactured in Germany
Engine: EL6E20 (6-cylinder) HYBRID, 223hP | Manufactured in 2018
```

#### Benefits of Builder Pattern
Undoubtedly, the number of lines of code increase at least to double in builder pattern, but the effort pays off in terms of design flexibility and much more readable code. The parameters to the constructor are reduced and are provided in highly readable method calls.

Builder pattern also helps minimizing the number of parameters in constructor and thus there is no need to pass in null for optional parameters to the constructor. It’s really attracts me.

#### Costs of Builder Pattern
Though Builder pattern reduce some line of code by eliminating the need of setter methods, still it doubles up total lines by introducing the Builder object. Furthermore, although client code is more readable, the client code is also more verbose.

---

## Factory Pattern
If object creation code is spread in the whole application, and if you need to change the process of object creation then you need to go in each and every place to make necessary changes. Factory, as the name suggests, is a place to create some different products which are somehow similar in features yet divided into categories.

In Java, factory pattern is used to create instances of different classes of the same type.

_![Transmission.java](/src/main/Transmissions/Transmission.java):_
```java
// create an abstract (base) class for all future transmissions
public abstract class Transmission {
    // TransmissionType can be Manual, Automatic, Tiptronic, DSG...
    private TransmissionType type;
    private int gears;
    private float shiftTimeMS;

    // main constructor that will be called in classes that will extend "Transmission"
    Transmission(TransmissionType type, int gears, float shiftTimeMS){
        this.type = type;
        this.gears = gears;
        this.shiftTimeMS = shiftTimeMS;
    }

    // define abstract method for future implementation
    protected abstract void construct();

    // getters/setters
    public TransmissionType getType(){
        return this.type;
    }

    public void setType(TransmissionType type){
        this.type = type;
    }

    // ...
}
```

_![ManualTransmission.java](/src/main/Transmissions/ManualTransmission.java):_
```java
// create another class that will extend "Transmission"
public class ManualTransmission extends Transmission {
    // constructor of this class will automatically assign Manual transmission type (as its
    // name states) and fill in rest of the information that will be received from the factory
    public ManualTransmission(int gears){
        super(TransmissionType.MANUAL, gears, 600);
        construct();
    }

    @Override
    protected void construct(){
        System.out.println("Building manual transmission...");
    }

    // other classes (AutomaticTransmission, DsgTransmission) are very similar to this one
}
```

_![TransmissionFactory.java](/src/main/TransmissionFactory.java):_
```java
public class TransmissionFactory {
    // we get our Transmission object specifically from one of the classes that extend
    // "Transmission". We decide the class by looking at the type user requested
    public static Transmission buildTransmission(TransmissionType type, int gears){
        Transmission transmission;

        switch (type){
            case MANUAL:
                transmission = new ManualTransmission(gears);
                break;
            case AUTOMATIC:
                transmission = new AutomaticTransmission(gears);
                break;
            case TIPTRONIC:
                transmission = new TiptronicTransmission(gears);
                break;
            case DSG:
                transmission = new DsgTransmission(gears);
                break;
            default:
                // throw for now
                throw new NotImplementedException();
        }

        // return the received Transmission object back
        return transmission;
    }
}
```

#### Test Factory Pattern

```java
public class Main {
    public static void main(String[] args){
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
}
```

This will result in following output:

```
Building tiptronic transmission...
TIPTRONIC transmission with 6 gears. shift time: 200.0ms
TIPTRONIC transmission with 7 gears. shift time: 100.0ms
```

**Factory pattern is most suitable where there is some complex object creation steps are involved.** To ensure that these steps are centralized and not exposed to composing classes, factory pattern should be used.

---

## Abstract Factory Pattern

**Abstract factory pattern** is considered as another layer of abstraction over factory pattern.

_![Car.java](/src/main/Cars/Car.java):_
```java
// create an abstract class for future car types
public abstract class Car {
    private Manufacturer manufacturer;
    private String model;
    private int year;
    // CarType can be Sedan, Sav, Luxury...
    private CarType type;

    public Car(){
        manufacturer = null;
        model = null;
        year = 0;
        type = null;
    }

    // main constructor
    public Car(Manufacturer manufacturer, String model, int year, CarType type){
        this.manufacturer = manufacturer;
        this.model = model;
        this.year = year;
        this.type = type;
    }

    // getters/setters
    public Manufacturer getManufacturer(){
        return this.manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer){
        this.manufacturer = manufacturer;
    }
    // ...

    protected abstract void construct();
}
```

_![SedanCar.java](/src/main/Cars/SedanCar.java):_
```java
// extend the "Car" class
public class SedanCar extends Car {
    // automatically assign sedan CarType and continue on with the base class constructor
    public SedanCar(Manufacturer manufacturer, String model, int year){
        super(manufacturer, model, year, CarType.SEDAN);
        construct();
    }

    @Override
    protected void construct() {
        System.out.println("Building Sedan car...");
    }

    @Override
    public Car clone() {
        return new SedanCar(this);
    }

    // other 2 classes (SavCar & LuxuryCar) are implemented in a same way
}
```

_![AudiCarFactory.java](/src/main/Manufacturers/AudiCarFactory.java):_
```java
// a class that uses that "another layer of abstraction"
public class AudiCarFactory {
    // return a Car according to tye CarType received
    public static Car buildCar(CarType type){
        Car car;
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int modelIndex = new Random().nextInt(99) * 100;

        switch (type){
            case SEDAN:
                // automatically assign this manufacturer & call constructor from SedanCar
                car = new SedanCar(Manufacturer.AUDI, "S" + modelIndex, year);
                break;
            case SAV:
                car = new SavCar(Manufacturer.AUDI, "X" + modelIndex, year);
                break;
            case LUXURY:
                car = new LuxuryCar(Manufacturer.AUDI, "L" + modelIndex, year);
                break;
            default:
                // throw for now
                throw new NotImplementedException();
        }
        return car;
    }

    // other Car Manufacturer classes are implemented in a same way
}
```

_![CarFactory.java](/src/main/CarFactory.java):_
```java
public class CarFactory {
    // make constructor private
    private CarFactory(){ }

    // according to the manufacturer and CarType we call the constructor from a specific class
    public static Car buildCar(Manufacturer manufacturer, CarType type){
        Car car = null;

        switch(manufacturer){
            case BMW:
                // call constructor from Car Manufacturer that will take us
                // to the constructor of requested CarType class
                car = BMWCarFactory.buildCar(type);
                break;
            case AUDI:
                car = AudiCarFactory.buildCar(type);
                break;
            case MERCEDES:
                car = MercedesCarFactory.buildCar(type);
                break;
            default:
                // throw for now
                System.out.println("[!] " + type + " factory does not exist yet!");
                throw new NotImplementedException();
        }
        PreferencesSingleton.getInstance().appendManufacturedCar(car);
        return car;
    }
}
```

#### Test Abstract Factory Pattern

```java
public class Main {
    public static void main(String[] args){
        Car car1 = CarFactory.buildCar(Manufacturer.BMW, CarType.LUXURY);
        Car car2 = CarFactory.buildCar(Manufacturer.BMW, CarType.SAV);
        Car car3 = CarFactory.buildCar(Manufacturer.MERCEDES, CarType.SEDAN);
        System.out.println(car1);
        System.out.println(car2);
        System.out.println(car3);
    }
}
```

This will result in following output:

```
Building Luxury car...
Building Sav car...
Building Sedan car...
BMW L8300(LUXURY) manufactured in 2019
BMW X7700(SAV) manufactured in 2019
MERCEDES S1500(SEDAN) manufactured in 2019
```

Whenever you need **another level of abstraction over a group of factories**, you should consider using the abstract factory pattern. It is probably only **difference between factory pattern vs abstract factory pattern.**

---

## Prototype Pattern

A prototype is a template of any object before the actual object is constructed. In java also, it holds the same meaning. Prototype design pattern is used in scenarios where application needs to create a number of instances of a class, which has almost same state or differs very little.
To implement prototype patter we can simply add an abstract method to our _Car.java_ class.

_![Car.java](/src/main/Cars/Car.java):_
```java
public abstract class Car {
    // ...

    // getters/setters
    public Manufacturer getManufacturer(){
        return this.manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer){
        this.manufacturer = manufacturer;
    }
    // ...

    protected abstract void construct();

    // add this abstract method that will be implemented in the classes that extend "Car"
    public abstract Car clone();
}
```

_![SedanCar.java](/src/main/Cars/SedanCar.java):_
```java
public class SedanCar extends Car {
    // ...

    // we create new instance with the values of previous clone
    public SedanCar(SedanCar other){
        super(other.getManufacturer(), other.getModel(), other.getYear(), other.getType());
    }

    @Override
    protected void construct() {
        System.out.println("Building Sedan car...");
    }

    // here we override the clone() method and make use of the 2nd constructor of class
    // this that takes the values from an instance and creates new one with them
    @Override
    public Car clone() {
        return new SedanCar(this);
    }

    // other 2 classes (SavCar & LuxuryCar) are implemented in a same way
}
```

#### Test Prototype Pattern

```java
public class Main {
    public static void main(String[] args){
        Car prototype = CarFactory.buildCar(Manufacturer.AUDI, CarType.LUXURY);
        Car copy = prototype.clone();
        copy.setYear(3029);
        System.out.println("Prototype: " + prototype);
        System.out.println("Copy: " + copy);
    }
}
```

This will result in following output:

```
Building Luxury car...
Prototype: AUDI L1000(LUXURY) manufactured in 2019
Copy: AUDI L1000(LUXURY) manufactured in 3029
```