package main;

import main.CarColorSchemes.DarkCarColorScheme;
import main.CarColorSchemes.ICarColorScheme;
import main.CarColorSchemes.LightCarColorScheme;
import main.Cars.Car;
import main.Cars.ICar;
import main.Enums.CarType;
import main.Enums.Color;
import main.Enums.Manufacturer;
import main.Factories.CarFactory;

import java.text.NumberFormat;
import java.util.*;

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

    public Iterator getIterator(){
        return new CarIterator();
    }

    public GarageMemento save(){
        return new GarageMemento(new ArrayList<>(this.cars));
    }

    public void restore(GarageMemento memento){
        this.cars = memento.getCars();
    }

    private class CarIterator implements Iterator {
        int index;

        @Override
        public boolean hasNext() {
            return index < cars.size();
        }

        @Override
        public Object next() {
            if(this.hasNext()){
                return cars.get(index++);
            }
            return null;
        }
    }
}
