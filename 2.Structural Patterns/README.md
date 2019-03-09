# JavaDesignPatterns
Implementation of different design patterns in Java.

> Design patterns, are solutions for most commonly (and frequently) occurred problems while designing a software. These patterns are mostly _“evolved”_ rather than _“discovered”_. A lot of learning, by lots of professional, have been summarized into these design patterns. None of these patterns force you anything in regard to implementation; they are just guidelines to solve a particular problem – in a particular way – in particular contexts. Code implementation is your responsibility.


## Structural Patterns:
Structural design patterns are concerned with how classes and objects can be composed, to form larger structures. The structural design patterns **simplifies the structure by identifying the relationships**. These patterns focus on, how the classes inherit from each other and how they are composed from other classes.

| DESIGN PATTERN | PURPOSE |
| -------------- | :------ |
| Adapter*        | Adapting an interface into another according to client expectation. |
| Bridge*      | Separating abstraction (interface) from implementation. |
| Composite*        | Allowing clients to operate on hierarchy of objects. |
| Decorator | Adding functionality to an object dynamically. |
| Facade*      | Providing an interface to a set of interfaces. |
| Flyweight | Reusing an object by sharing it. |
| Proxy* | Representing another object. |

*\* Patterns implemented in this project.*

---

## Adapter Pattern:
An Adapter Pattern says that just **"converts the interface of a class into another interface that a client wants"**.

In other words, to provide the interface according to client requirement while using the services of a class with a different interface. The Adapter Pattern is also known as **Wrapper**.

Adapter pattern is used in _[PreferencesSingleton.java](./src/main/PreferencesSingleton.java)_. There is a method for setting your favorite ***Car***. I have only one class that implement the *ICar* interface. But there is also a ***ToyCar*** class that can not be considered as real car. What if I want to set this toy car as my favorite car? To solve this, I created **an adapter for *ToyCar*** class.

Here is the method to set my favorite ***Car***:

```java
public class PreferencesSingleton {
    private ICar favoriteCar;

    // . . .

    public void setFavoriteCar(ICar car){
        this.favoriteCar = car;
    }
}
```

And the ***ToyCar*** class that is incompatible with the *setFavoriteCar* method:

```java
public class ToyCar {
    private Manufacturer brand;
    private String toyName;
    private int releaseYear;
    private CarType carType;
    private ICarColorScheme colorScheme;
    private int price;

    // . . .
}
```

To create an adapter I need to implement *ICar* interface and wrap the *ToyCar* class. This will make sure that adapter will be considered just as Car class:

```java
public class ToyCarAdapter implements ICar {
    private ToyCar toyCar;

    public ToyCarAdapter(ToyCar toyCar){
        this.toyCar = toyCar;
    }

    public ToyCar getToyCar(){
        return this.toyCar;
    }

    @Override
    public Manufacturer getManufacturer() {
        return this.toyCar.getBrand();
    }

    @Override
    public void setManufacturer(Manufacturer manufacturer) {
        this.toyCar.setBrand(manufacturer);
    }

    // . . .
}
```

### Testing Adapter Pattern:

```java
public static void main(String[] args){
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
```

Output:

```
Real car:
Current favorite car is "S8800" manufactured by AUDI. This car is 7 years old.
Toy car:
Current favorite car is "'15 Defender Double Cab" manufactured by LAND_ROVER. This car is 2 years old.
```

### **Advantage of Adapter Pattern**
* It allows two or more previously incompatible objects to interact.
* It allows reusability of existing functionality.

---

## Bridge Pattern:
A Bridge Pattern says that just **"decouple the functional abstraction from the implementation so that the two can vary independently"**. The Bridge Pattern is also known as Handle or Body.

In our case we have *ICar* interface implemented by both ***Car*** and ***ToyCarAdapter*** classes. Consider we want to have 2 different color schemes: LightCarColor and DarkCarColor for both our classes. Would you extend those classes to have: *LightColorCar*, *DarkColorCar*, *LightToyCar* & *DarkToyCar*? The solution is to **decouple the ICar and CarColorScheme** interfaces.

Both Car and ToyCarAdapter classes contain an ICarColorScheme object:

```java
public interface ICar {
    Manufacturer getManufacturer();
    void setManufacturer(Manufacturer manufacturer);
    String getModel();
    void setModel(String model);
    // . . .
    void setColorScheme(ICarColorScheme newColor);
    ICarColorScheme getColorScheme();
}
```

```java
public interface ICarColorScheme {
    Color getBodyColor();
    InteriorColor getInteriorColor();
}
```

We will assign *ICarColorScheme* Objects to our Cars. This will make it possible to change Car classes without modifying the CarColorSchemes:

```java
public abstract class Car implements ICar {
    // . . .
    private int price;
    private ICarColorScheme colorScheme;

    
    @Override
    public void setColorScheme(ICarColorScheme newColorScheme){
        this.colorScheme = newColorScheme;
    }

    @Override
    public ICarColorScheme getColorScheme(){
        return this.colorScheme;
    }

    // . . .
}
```

```java
public class ToyCarAdapter implements ICar {
    private ToyCar toyCar;

    @Override
    public void setColorScheme(ICarColorScheme newColorScheme) {
        toyCar.setColorScheme(newColorScheme);
    }

    @Override
    public ICarColorScheme getColorScheme() {
        return toyCar.getColorScheme();
    }

    // . . .
}
```

Now, we create 2 classes that implement the *ICarColorScheme* to use them:

```java
public class LightCarColorScheme implements ICarColorScheme {
    private Color bodyColor;
    private InteriorColor interiorColor;


    public LightCarColorScheme(Color bodyColor){
        this.bodyColor = bodyColor;
        this.interiorColor = InteriorColor.LIGHT;
    }

    @Override
    public Color getBodyColor() {
        return this.bodyColor;
    }

    @Override
    public InteriorColor getInteriorColor() {
        return this.interiorColor;
    }

    @Override
    public String toString(){
        return "Body color: " + this.bodyColor + " | " + this.interiorColor + " interior!";
    }
}
```

Dark color scheme is like above, except the interior color is dark.

### Testing Bridge Pattern:

```java
public static void main(String[] args){
    ICarColorScheme lightColorScheme = new LightCarColorScheme(Color.RED);
    ICarColorScheme darkColorScheme = new DarkCarColorScheme(Color.GREEN);

    Car car = CarFactory.buildCar(Manufacturer.MERCEDES, CarType.LUXURY);
    car.setColorScheme(lightColorScheme);

    ToyCar toyCar = new ToyCar(Manufacturer.FERRARI, "Speeder Wheels", CarType.SEDAN);
    toyCar.setColorScheme(darkColorScheme);

    System.out.println(car + "\n" + car.getColorScheme());
    System.out.println(toyCar + "\n" + toyCar.getColorScheme());
}
```

Output:

```
MERCEDES L6500(LUXURY) manufactured in 2019
Body color: RED | LIGHT interior!
Toy car 'Speeder Wheels' FERRARI. Released in 2019
Body color: GREEN | DARK interior!
```

### **Advantage of Bridge Pattern**
* It enables the separation of implementation from the interface.
* It improves the extensibility.
* It allows the hiding of implementation details from the client.

---

## Composite Pattern
A Composite Pattern says that just **"allow clients to operate in generic manner on objects that may or may not represent a hierarchy of objects"**. This pattern is most suitable in cases where you need to work **with objects which form a tree like hierarchy**. In that tree, each node/object (except root node) is either composite or leaf node.

We are asked to prepare a design which can be useful to generate the customer’s consolidated garage view which is able to show customer’s total garage price as well as information about all of the cars after merging all the *Car* & *ToyCar*s prices.

For this, a new class *"Garage"* is created that would be able to store many cars (both real and toys). Using composite pattern we are able to show net garage price with all the cars and toys in it by merging their prices.

_[Garage.java](./src/main/Garage.java)_ class that contains a list of *ICar*s. New cars can be added by calling the *addCars* method:
```java
public class Garage {
    private List<ICar> cars = new ArrayList<>();

    public void addCars(ICar... cars){
        if (cars.length > 0) {
            this.cars.addAll(Arrays.asList(cars));
        }
    }

    public int getNetGaragePrice(){
        int totalPrice = 0;
        for (ICar car: this.cars)
            totalPrice += car.getPrice();

        return totalPrice;
    }

    public void showAllCars(){
        for (ICar car: this.cars){
            System.out.println(car + " - €" + NumberFormat.getIntegerInstance().format(car.getPrice()));
        }
        System.out.println("* This garages NET Price: €" + NumberFormat.getIntegerInstance().format(getNetGaragePrice()));
    }

    // . . .
}
```

### Testing Composite Pattern:

```java
public static void main(String[] args){
    ICar car1 = CarFactory.buildCar(Manufacturer.AUDI, CarType.SEDAN);
    car1.setPrice(42000);
    ICar car2 = CarFactory.buildCar(Manufacturer.BMW, CarType.SAV);
    car2.setPrice(31500);

    ToyCar toyCar = new ToyCar(Manufacturer.LAMBORGHINI, "The Dark Knight", CarType.SEDAN);
    toyCar.setPrice(32);
    ICar car3 = new ToyCarAdapter(toyCar);

    Garage worldwideGarage = new Garage();
    worldwideGarage.addCars(car1, car2, car3);

    String netPrice = NumberFormat.getIntegerInstance()
            .format(worldwideGarage.getNetGaragePrice());
    System.out.println("* NET Garage Price: €" + netPrice);
}
```

Output:

```
* NET Garage Price: €73,532
```

### **Advantage of Bridge Pattern**
* It defines class hierarchies that contain primitive and complex objects.
* It makes easier to you to add new kinds of components.
* It provides flexibility of structure with manageable class or interface.

---

## Facade Pattern

A Facade Pattern says that just **"just provide a unified and simplified interface to a set of interfaces in a subsystem, therefore it hides the complexities of the subsystem from the client"**.

In other words, Facade Pattern describes a higher-level interface that makes the sub-system easier to use.

Practically, **every Abstract Factory** is a type of **Facade**.

We will create Facade on top of *Garage* class. To make it simple we will call a method that would fill our garage with random cars (with "complex" logic) by indicating only the number of cars and their Manufacturer. Additionally there will be a method to apply discount of any percent to all of the cars in given garage:

_[Garage.java](./src/main/Garage.java):_
```java
public class Garage {
    private List<ICar> cars = new ArrayList<>();

    // . . .

    public static Garage FillWithCarsFacade(Manufacturer manufacturer, int carsNum){
        Garage garage = new Garage();

        for (int i = 0; i < carsNum; i++){
            Random rnd = new Random();
            Car car = CarFactory.buildCar(manufacturer, CarType.values()[rnd.nextInt(3)]);

            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            int rndYear = rnd.nextInt(currentYear - 2001) + 2001;
            car.setYear(rndYear);

            int basePrice = 21000;
            int rndPrice = rnd.nextInt(27) * 1550;
            int carAgePriceDrop = rndPrice / ((currentYear - rndYear) * 5400);
            car.setPrice(basePrice + 3 * rndPrice - carAgePriceDrop);

            Color rndColor = Color.values()[rnd.nextInt(8)];
            ICarColorScheme colorScheme = rnd.nextInt(2) == 0? new LightCarColorScheme(rndColor): new DarkCarColorScheme(rndColor);
            car.setColorScheme(colorScheme);

            garage.addCars(car);
        }

        return garage;
    }

    public void applyDiscount(float percentage) {
        for(ICar car: this.cars){
            car.setPrice((int)(car.getPrice() * (100 - percentage) / 100));
        }
        System.out.println("\nApplied a discount of " + percentage + "% for all cars in this garage!");
    }
}
```

### Testing Facade Pattern:

```java
public static void main(String[] args){
    Garage bmwGarage = Garage.FillWithCarsFacade(Manufacturer.BMW, 4);
    bmwGarage.showAllCars();
    bmwGarage.applyDiscount(15);
    bmwGarage.showAllCars();
}
```

Output:

```
BMW L1200(LUXURY) manufactured in 2017 - €81,449
BMW L1100(LUXURY) manufactured in 2014 - €132,599
BMW S3600(SEDAN) manufactured in 2018 - €86,096
BMW L8000(LUXURY) manufactured in 2007 - €137,250
* This garages NET Price: €437,394

Applied a discount of 15.0% for all cars in this garage!
BMW L1200(LUXURY) manufactured in 2017 - €69,231
BMW L1100(LUXURY) manufactured in 2014 - €112,709
BMW S3600(SEDAN) manufactured in 2018 - €73,181
BMW L8000(LUXURY) manufactured in 2007 - €116,662
* This garages NET Price: €371,783
```

### **Advantage of Facade Pattern**
* It shields the clients from the complexities of the sub-system components.
* It promotes loose coupling between subsystems and its clients.

---

## Proxy Pattern

Simply, proxy means an object representing another object. According to GoF, a Proxy Pattern **"provides the control for accessing the original object"**.

So, we can perform many operations like hiding the information of original object, on demand loading etc. Proxy pattern is also known as **Surrogate or Placeholder**.

This pattern is used to *"wrap"* *Garage* class with security (**Protective Proxy**). 

_[GarageProxy.java](./src/main/GarageProxy.java):_
```java
public class GarageProxy extends Garage {
    private boolean doorsOpen = false;


    @Override
    public void applyDiscount(float percentage){
        if (doorsOpen){
            System.out.println("Applying discounts to your garage!");
            super.applyDiscount(percentage);
        } else {
            System.out.println("Doors to this garage are closed! You are not allowed to make any changes!");
        }
    }

    public void openDoors(String password){
        if (isValidPassword(password) && !doorsOpen){
            doorsOpen = true;
            System.out.println("Doors to your garage are now open! You can apply discounts...");
        } else if (!isValidPassword(password) && !doorsOpen) {
            System.out.println("No! You can not open doors to that garage!");
        } else {
            System.out.println("Doors to your garage are already open.");
        }
    }

    private boolean isValidPassword(String password){
        return password.equals("$ecr@t");
    }

    public void closeDoors(){
        if (doorsOpen) {
            doorsOpen = false;
            System.out.println("Doors are now closed!");
        }
    }
}
```

#### Testing Proxy Pattern:

```java
public static void main(String[] args){
    ICar car1 = CarFactory.buildCar(Manufacturer.AUDI, CarType.SEDAN);
    car1.setPrice(24000);
    ICar car2 = CarFactory.buildCar(Manufacturer.MERCEDES, CarType.SAV);
    car2.setPrice(72800);

    GarageProxy secureGarage = new GarageProxy();
    secureGarage.addCars(car1, car2);

    String netPrice = NumberFormat.getIntegerInstance()
            .format(secureGarage.getNetGaragePrice());
    System.out.println("NET Garage Price: €" + netPrice);
    secureGarage.applyDiscount(15);
    secureGarage.openDoors("invalid");
    secureGarage.openDoors("$ecr@t");
    secureGarage.applyDiscount(15);

    netPrice = NumberFormat.getIntegerInstance().format(secureGarage.getNetGaragePrice());
    System.out.println("NET Garage Price: €" + netPrice);

    secureGarage.closeDoors();
}
```

Output:

```
NET Garage Price: €96,800
Doors to this garage are closed! You are not allowed to make any changes!
No! You can not open doors to that garage!
Doors to your garage are now open! You can apply discounts...
Applying discounts to your garage!

Applied a discount of 15.0% for all cars in this garage!
NET Garage Price: €82,280
Doors are now closed!
```

#### **Advantage of Proxy Pattern**
* It provides the protection to the original object from the outside world.