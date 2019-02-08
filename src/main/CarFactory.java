package main;

import main.Cars.Car;
import main.Enums.CarType;
import main.Enums.Manufacturer;
import main.Manufacturers.AudiCarFactory;
import main.Manufacturers.BMWCarFactory;
import main.Manufacturers.MercedesCarFactory;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class CarFactory {
    private CarFactory(){ }

    public static Car buildCar(Manufacturer manufacturer, CarType type){
        Car car = null;

        switch(manufacturer){
            case BMW:
                car = BMWCarFactory.buildCar(type);
                break;
            case AUDI:
                car = AudiCarFactory.buildCar(type);
                break;
            case MERCEDES:
                car = MercedesCarFactory.buildCar(type);
                break;
            default:
                System.out.println("[!] " + type + " factory does not exist yet!");
                throw new NotImplementedException();
        }
        PreferencesSingleton.getInstance().appendManufacturedCar(car);
        return car;
    }
}
