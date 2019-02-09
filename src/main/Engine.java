package main;

import main.Enums.EngineType;
import main.Enums.FuelType;
import main.Transmissions.Transmission;

import java.security.InvalidParameterException;
import java.util.Random;

public class Engine {
    private final String model; // required
    private final int cylinders; // required
    private final FuelType fuelType; // required
    private final int horsePower; // calculated
    private final EngineType type; // optional
    private final int year; // optional
    private final String country; // optional
    private final Transmission transmission; // optional

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

    public int getCylinders(){
        return this.cylinders;
    }

    public FuelType getFuelType(){
        return this.fuelType;
    }

    public int getHorsePower(){
        return this.horsePower;
    }

    public EngineType getType(){
        return this.type;
    }

    public int getYear(){
        return this.year;
    }

    public String getCountry(){
        return this.country;
    }

    Transmission getTransmission(){
        return this.transmission;
    }

    @Override
    public String toString(){
        String _type = this.type != null? " (" + this.type + (this.cylinders > 0? "-" + this.cylinders: "") + ")": " (" + this.cylinders + "-cylinder)";
        String _country = this.year > 0? " | Manufactured in " + this.year + (this.country != null && !this.country.isEmpty()? " - " + this.country: ""):(this.country != null && !this.country.isEmpty()? " | Manufactured in " + this.country: "");
        return "Engine: " + this.model + _type + " " + this.fuelType + ", " + this.horsePower + "hP" + _country;
    }

    static class EngineBuilder {
        private final String model;
        private final int cylinders;
        private final FuelType fuelType;
        private int horsePower;
        private EngineType type;
        private int year;
        private String country;
        private Transmission transmission;

        EngineBuilder(String model, int cylinders, FuelType fuelType){
            if (model == null || model.isEmpty()) {
                throw new InvalidParameterException();
            }

            this.model = model;
            this.cylinders = cylinders;
            this.fuelType = fuelType;
        }

        EngineBuilder type(EngineType type){
            this.type = type;
            return this;
        }

        EngineBuilder year(int year){
            this.year = year;
            return this;
        }

        EngineBuilder country(String country){
            this.country = country;
            return this;
        }

        EngineBuilder transmission(Transmission transmission){
            this.transmission = transmission;
            return this;
        }

        Engine build(){
            this.horsePower = CalculateHorsePower(this.cylinders, this.fuelType, this.type);
            Engine engine = new Engine(this);
            if (PreferencesSingleton.getInstance() != null) {
                PreferencesSingleton.getInstance().appendManufacturedEngine(engine);
            }
            return engine;
        }

        private static int CalculateHorsePower(int cylinders, FuelType type, EngineType engineType) {
            int basePower = 17;
            float engineTypeMultiplier;

            if (engineType == null){
                engineTypeMultiplier = 1.7f;
            } else {
                switch (engineType) {
                    case STRAIGHT:
                        engineTypeMultiplier = 1.7f;
                        break;
                    case V_TYPE:
                        engineTypeMultiplier = 2;
                        break;
                    case FLAT:
                        engineTypeMultiplier = 1.9f;
                        break;
                    case W_TYPE:
                        engineTypeMultiplier = 2.2f;
                        break;
                    default:
                        engineTypeMultiplier = 1.7f;
                }
            }

            float typeMultiplier;
            switch (type){
                case PETROL:
                    typeMultiplier = 1.4f;
                    break;
                case DIESEL:
                    typeMultiplier = 1.5f;
                    break;
                case GAS:
                    typeMultiplier = 0.75f;
                    break;
                case HYBRID:
                    typeMultiplier = 1.12f;
                    break;
                default:
                    typeMultiplier = 1.4f;
            }

            float randomExtra = new Random().nextFloat() / 4 + 1;

            return (int)(basePower * cylinders * engineTypeMultiplier * typeMultiplier * randomExtra);
        }
    }
}
