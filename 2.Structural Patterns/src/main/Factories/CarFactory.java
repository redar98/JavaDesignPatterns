package main.Factories;

import main.Cars.Car;
import main.Enums.CarType;
import main.Enums.Manufacturer;
import main.Factories.Manufacturers.AudiCarFactory;
import main.Factories.Manufacturers.BMWCarFactory;
import main.Factories.Manufacturers.MercedesCarFactory;
import main.PreferencesSingleton;
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
        if (PreferencesSingleton.getInstance() != null) {
            PreferencesSingleton.getInstance().appendManufacturedCar(car);
        }
        return car;
    }
}
