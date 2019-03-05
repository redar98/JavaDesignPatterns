package main.Transmissions;

import main.Enums.TransmissionType;

public class AutomaticTransmission extends Transmission {
    public AutomaticTransmission(int gears){
        super(TransmissionType.AUTOMATIC, gears, 180);
        construct();
    }

    @Override
    protected void construct(){
        System.out.println("Building automatic transmission...");
    }
}
