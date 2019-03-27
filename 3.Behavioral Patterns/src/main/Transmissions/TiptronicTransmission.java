package main.Transmissions;

import main.Enums.TransmissionType;

public class TiptronicTransmission extends Transmission {
    public TiptronicTransmission(int gears){
        super(TransmissionType.TIPTRONIC, gears, 200);
        construct();
    }

    @Override
    protected void construct(){
        System.out.println("Building tiptronic transmission...");
    }
}
