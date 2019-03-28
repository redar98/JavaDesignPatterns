# JavaDesignPatterns
Implementation of different design patterns in Java.

> Design patterns, are solutions for most commonly (and frequently) occurred problems while designing a software. These patterns are mostly _“evolved”_ rather than _“discovered”_. A lot of learning, by lots of professional, have been summarized into these design patterns. None of these patterns force you anything in regard to implementation; they are just guidelines to solve a particular problem – in a particular way – in particular contexts. Code implementation is your responsibility.


## Behavioral Patterns:
Behavioral patterns abstract an action we want to take on the object or class that takes the action. By changing the object or class, we can change the algorithm used, the objects affected, or the behavior, while still retaining the same basic interface for client classes.

| DESIGN PATTERN | PURPOSE |
| -------------- | :------ |
| Chain of responsibility* | Chain of responsibility design pattern gives more than one object an opportunity to handle a request by linking receiving objects together in form of a chain. |
| Command | 	Command design pattern is useful to abstract the business logic into discrete actions which we call commands. These command objects help in loose coupling between two classes where one class (invoker) shall call a method on other class (receiver) to perform a business operation. |
| Interpreter | Interpreter pattern specifies how to evaluate sentences in a language, programatically. It helps in building a grammar for a simple language, so that sentences in the language can be interpreted. |
| Iterator* | Iterator pattern provides a way to access the elements of an aggregate object sequentially without exposing its underlying representation. |
| Mediator | Mediator pattern defines an object that encapsulates how a set of objects interact. Mediator promotes loose coupling by keeping objects from referring to each other explicitly, and it lets us vary their interaction independently. |
| Memento* | Memento pattern is used to restore state of an object to a previous state. It is also known as snapshot pattern. |
| Observer* | Observer pattern defines a one-to-many dependency between objects so that when one object changes state, all its dependents are notified and updated automatically. It is also referred to as the publish-subscribe pattern. |
| State* | In state pattern allows an object to alter its behavior when its internal state changes. The object will appear to change its class. There shall be a separate concrete class per possible state of an object. |
| Strategy | Strategy pattern is used where we choose a specific implementation of algorithm or task in run time – out of multiple other implementations for same task. |
| Template method | Template method pattern defines the sequential steps to execute a multi-step algorithm and optionally can provide a default implementation as well (based on requirements). |
| Visitor | Visitor pattern is used when we want a hierarchy of objects to modify their behavior but without modifying their source code. |

*\* Patterns implemented in this project.*

---

## Chain of Responsibility Pattern:
> "Gives more than one object an opportunity to handle a request by linking receiving objects together."

Chain of Responsibility allows a number of classes to attempt to handle a request, independently of any other object along the chain. Once the request is handled, it completes it’s journey through the chain.

Chain of Responsibility pattern is used in _[Account.java](./src/main/Accounts/Account.java)_. There can be several accounts that hold a defined balance like Paypal, Bitcoin, Bank account and etc... Chain of responsibility allows us to set successors (another accounts) in case balance is not enough in our first account.

Here is the abstract class that will allow us do above written operations:

```java
public abstract class Account extends Observable {
    // next account in case this one does not have enough balance
    private Account successor;
    private double balance;

    Account(double balance){
        this.balance = balance;
    }

    // set the successor using this method
    public void setSuccessor(Account successor){
        this.successor = successor;
    }

    // simple logic to pay with this accounts balance. If its not enough we ask the successor account to pay
    public boolean pay(double amount){
        if (this.canPay(amount)){
            this.balance -= amount;
            setChanged();
            notifyObservers(new AccountCashback(this, amount));
            System.out.println("Paid $" + amount + " using " + getClass().getSimpleName() + " Remaining: " + this.balance);
            return true;
        } else if (this.successor != null){
            System.out.println("Can not pay $" + amount + " using " + getClass().getSimpleName() + ". Proceeding...");
            return successor.pay(amount);
        } else {
            System.out.println("Can not pay $" + amount + " with " + getClass().getSimpleName());
            return false;
        }
    }

    // . . .
}
```

Using this abstract class we can create different classes that will have their own logic in some parts. But eventually what we want to do is to set the successor in case of fail.

```java
public class Paypal extends Account {

    public Paypal(double balance){
        super(balance);
    }
}
```

### Testing Chain of Responsibility Pattern:

Now we will create several accounts and set the successors for them

```java
public static void main(String[] args){
    Account bank = new Bank(3000);
    Account paypal = new Paypal(24800);
    Account bitcoin = new Bitcoin(82000);

    bank.setSuccessor(paypal);
    paypal.setSuccessor(bitcoin);

    Car expensiveCar = CarFactory.buildCar(Manufacturer.MERCEDES, CarType.LUXURY);
    expensiveCar.setPrice(48720);

    expensiveCar.purchase(bank);
}
```

Above code will result in using the Bitcoin account as only it has the enough ammount of money to pay for our *expensive* car.

Output:

```
Building Luxury car...
Can not pay $48720.0 using Bank. Proceeding...
Can not pay $48720.0 using Paypal. Proceeding...
Paid $48720.0 using Bitcoin Remaining: 33280.0
MERCEDES L3600 was purchased by $48720
```

### **Advantage of Chain of Responsibility Pattern**
* Decouples the sender and receivers of the request.
* Simplifies our object because it doesn't have to know the chain’s structure and keep direct references to its members.
* Allows us to add or remove responsibilities dynamically by changing the members or order of the chain.

---

## Iterator Pattern:
Iterator pattern provides a way to access the elements of an aggregate object sequentially without exposing its underlying representation.

As name implies, iterator helps in **traversing the collection of objects in a defined manner** which is useful the client applications. During iteration, client programs can perform various other operations on the elements as per requirements.

Remember the *Garage.FillWithCarsFacade* method we created before? We will use iterator to iterate over cars list in our garage.

To implement this we need to create a method that would return the Iterator object for our class. We can create it right inside the *Garage* class:

```java
public class Garage {
    private List<ICar> cars = new ArrayList<>();

    // . . .

    public Iterator getIterator(){
        return new CarIterator();
    }

    private class CarIterator implements Iterator {
        int index;

        // if increased index is out of bound then return false
        @Override
        public boolean hasNext() {
            return index < cars.size();
        }

        // this method increases the index and gets the element
        @Override
        public Object next() {
            if(this.hasNext()){
                return cars.get(index++);
            }
            return null;
        }
    }
}
```

### Testing Iterator Pattern:

```java
public static void main(String[] args){
    // fill our garage with 3 random cars
    Garage garage = Garage.FillWithCarsFacade(Manufacturer.BMW, 3);

    // iterate over each car using our iterator
    for(Iterator iterator = garage.getIterator(); iterator.hasNext();){
        ICar car = (ICar) iterator.next();
        System.out.println(car);
    }
}
```

This may seem like an usual for loop but there are several advantages in using iterator.

Output:

```
Building Sedan car...
Building Sav car...
Building Sedan car...
BMW S2300(SEDAN) manufactured in 2016
BMW X1700(SAV) manufactured in 2006
BMW S8300(SEDAN) manufactured in 2009
```

### **Advantage of Iterator Pattern**
* Iterations **will not lead** to any sort of runtime exception like **IndexOutofBounds**.
* Access using iterators are more efficient.

---

## Memento Pattern
A memento is is like a restore point during the life cycle on the object, which the client application can use to restore the object state to its state. Conceptually, it is very much like we create restore points for operating systems and use to restore the system if something breaks or system crashes.

> The intent of memento pattern is to capture the internal state of an object without violating encapsulation and thus providing a mean for restoring the object into initial state when needed.

Memento pattern shall be be used in any application in which object’s state is continuously changing and the user of the application may decide to rollback or undo the changes changes at any point.

In our case we will use it to undo cars list for our Garage. For example we will purchase a car and add add it to our Garage and after some time we will not be pleased by the new cars list and restore the old cars for our Garage.

We will need to implement 2 methods in our _[Garage.java](./src/main/Garage.java)_ class to have the ability of saving states: **save** and **restore**
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

    public GarageMemento save(){
        return new GarageMemento(new ArrayList<>(this.cars));
    }

    public void restore(GarageMemento memento){
        this.cars = memento.getCars();
    }
}
```

And to save the cars list we will need another class that will hold the value of current list:

```java
public class GarageMemento {
    private List<ICar> cars;

    public GarageMemento(List<ICar> cars){
        this.cars = cars;
    }

    public List<ICar> getCars(){
        return this.cars;
    }
}
```

Now, we can easily save and restore the state of our Garage using *save* and *restore* methods.

### Testing Memento Pattern:

```java
public static void main(String[] args){
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
```

Here we should be able to restore our cars list before *mercedesCar* was purchased.

Output:

```
Building Sav car...
Building Sedan car...
Building Sedan car...

- Latest Garage State:
AUDI S7300(SEDAN) manufactured in 2013 - €62,850
AUDI S200(SEDAN) manufactured in 2003 - €90,750
MERCEDES X1300(SAV) manufactured in 2019 - €36,750
* This garages NET Price: €190,350

- Restored Garage State:
AUDI S7300(SEDAN) manufactured in 2013 - €62,850
AUDI S200(SEDAN) manufactured in 2003 - €90,750
* This garages NET Price: €153,600
```

### **Advantage of Memento Pattern**
* The biggest advantage is that you can always discard the unwanted changes and restore it to an intended or stable state.
* You do not compromise the encapsulation associated with the key objects that are participating in this model.
* Maintains high cohesion.
* Provides an easy recovery technique.

---

## Observer Pattern

In observer pattern, there are many observers (subscriber objects) that are observing a particular subject (publisher object). Observers register themselves to a subject to get a notification when there is a change made inside that subject.

A observer object can register or unregister from subject at any point of time. It helps is making the objects objects **loosely coupled**.

We will add our *PreferencesSingletone* as observer for our *Account* classes. That way we will be implementing the **cashback** service that will be ruled by our singleton class. What we want is to return an amoun of money once we purchase a car using one of our *Accounts*

In java we already have **Observable** class defined in java.util, so we will just be extending it for our abstract *Account* class. And to notify our observers we will call the *notifyObservers* method additionally passing an object that will contain this account and amount to be spent:
```java
public abstract class Account extends Observable {
    private Account successor;
    private double balance;

    Account(double balance){
        this.balance = balance;
    }

    // . . .

    public boolean pay(double amount){
        if (this.canPay(amount)){
            this.balance -= amount;
            setChanged();
            notifyObservers(new AccountCashback(this, amount));
            System.out.println("Paid $" + amount + " using " + getClass().getSimpleName() + " Remaining: " + this.balance);
            return true;
        } else if (this.successor != null){
            System.out.println("Can not pay $" + amount + " using " + getClass().getSimpleName() + ". Proceeding...");
            return successor.pay(amount);
        } else {
            System.out.println("Can not pay $" + amount + " with " + getClass().getSimpleName());
            return false;
        }
    }
}
```

AccountCashback class will contain the account for which we want to return the cashback and the money that was spent:

```java
public class AccountCashback {
    private Account account;
    private double spentAmount;

    public AccountCashback(Account account, double spentAmount){
        this.account = account;
        this.spentAmount = spentAmount;
    }

    public Account getAccount(){
        return this.account;
    }

    public double getSpentAmount(){
        return this.spentAmount;
    }
}
```

To get update notifications on our observers, we have to implement already defined class *Observer* that is also part of java.util

```java
public class PreferencesSingleton implements Observer {
    private static volatile PreferencesSingleton instance;

    // . . .

    @Override
    public void update(Observable o, Object arg) {
        AccountCashback accountCashback = (AccountCashback) arg;
        Account account = accountCashback.getAccount();

        double spentAmount = accountCashback.getSpentAmount();
        int cashback = (int)(spentAmount * 0.02);
        account.addToBalance(cashback);

        System.out.println("[!] Observer: Account balance changed! Withdrawn amount: $" + spentAmount + " Cashback: $" + cashback);
    }
```

### Testing Observer Pattern:

```java
public static void main(String[] args){
    Account bank = new Bank(38000);
    bank.addObserver(PreferencesSingleton.getInstance());

    Car car = CarFactory.buildCar(Manufacturer.MERCEDES, CarType.SEDAN);
    car.setPrice(27500);

    car.purchase(bank);
}
```

Above code will withdraw $27500 from our account balance, then the Observer *(PreferencesSingleton)* will detect the balance change and return 2% of our spendings.

Output:

```
Building Sedan car...
[!] Observer: Account balance changed! Withdrawn amount: $27500.0 Cashback: $549
Paid $27500.0 using Bank Remaining: 11049.0
MERCEDES S7700 was purchased by $27500
```

### **Advantage of Observer Pattern**
* The subject and observers make a loosely coupled system. They do not need to know each other explicitly.
* We can independently add or remove observers at any time.

---

## State Pattern

A state **allows an object to alter its behavior when its internal state changes**. The object will appear to change its class.

It can be drawn from above definition that there shall be a **separate concrete class per possible state of an object**. Each concrete state object will have logic to accept or reject a state transition request based on it’s present state and context information passed to it as method arguments.

Here, I decided to make different types of cashback services with different rates. We will give one of those services to our *PreferencesSingleton* and it will remember our choice and use it in future cashbacks.

For this we create an interface for our future *Cashback* services:
```java
public interface CashbackProfile {
    int getCashbackAmount(double amountSpent);
    float getCashbackRate();
}
```

And here are 2 simple (for now) classes that differ only by cashback rate. There can be some complex logic and additional verifications but for the sake of brevity I will keep it short:

```java
public class LowCashback implements CashbackProfile {
    private final static float cashbackRate = 0.02f;

    @Override
    public int getCashbackAmount(double amountSpent) {
        return (int)(amountSpent * cashbackRate);
    }

    @Override
    public float getCashbackRate() {
        return cashbackRate * 100;
    }
}
```

```java
public class HighCashback implements CashbackProfile {
    private final static float cashbackRate = 0.12f;

    @Override
    public int getCashbackAmount(double amountSpent) {
        return (int)(amountSpent * cashbackRate);
    }

    @Override
    public float getCashbackRate() {
        return cashbackRate * 100;
    }
}
```

Now we need a method to make *PreferencesSingleton* remember our choice and change the update method for our *Observer* to use the active *CashbackProfile*:

```java
public class PreferencesSingleton implements Observer {
    private static volatile PreferencesSingleton instance;
    private CashbackProfile cashbackProfile = new LowCashback();
    
    // . . .

    public void setCashbackProfile(CashbackProfile newCashbackProfile){
        this.cashbackProfile = newCashbackProfile;
    }

    @Override
    public void update(Observable o, Object arg) {
        AccountCashback accountCashback = (AccountCashback) arg;
        Account account = accountCashback.getAccount();

        double spentAmount = accountCashback.getSpentAmount();
        int cashback = this.cashbackProfile.getCashbackAmount(spentAmount);
        account.addToBalance(cashback);

        System.out.println("[!] Observer: Account balance changed! Withdrawn amount: $" + spentAmount
                + " Cashback(" + this.cashbackProfile.getCashbackRate() + "%): $" + cashback);
    }
```

#### Testing State Pattern:

```java
public static void main(String[] args){
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
```

Output:

```
Building Sedan car...
[!] Observer: Account balance changed! Withdrawn amount: $42800.0 Cashback(2.0%): $855
Paid $42800.0 using Paypal Remaining: 78555.0
BMW S3500 was purchased by $42800
[!] Observer: Account balance changed! Withdrawn amount: $42800.0 Cashback(12.0%): $5135
Paid $42800.0 using Paypal Remaining: 40890.0
BMW S3500 was purchased by $42800
```

We can see that our *CashbackProfile* is successfully remembered by our class and used when needed. One important thing is that **State objects should usually be singletons** and our class fits just well.

#### **Advantage of State Pattern**
* We can easily add new states and new behaviors in the application without impacting other components.
* It also helps in reducing complexity by reducing the use of if-else statements or switch/case conditional logic.

_[— (Design patterns for humans) —](https://github.com/kamranahmedse/design-patterns-for-humans)_