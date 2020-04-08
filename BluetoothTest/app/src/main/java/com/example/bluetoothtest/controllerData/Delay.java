package com.example.bluetoothtest.controllerData;

import android.util.Pair;

public class Delay extends Command {
    private int delay;

    public Delay(int delay, boolean start, boolean end, String id) {
        super(start, end, id);
        this.delay = delay;
    }

    @Override
    public Pair<Integer, String> run(int pos) {
        return null;
    }

    @Override
    public String getArg(){
        return Integer.toString(delay);
    }

    @Override
    public Pair<Boolean,String> setArg(String arg){
        if (arg.length()>9) return new Pair<>(true,"Delay cannot be longer than 1 billion ms");

        delay=Integer.parseInt(arg);

        return new Pair<>(false,null);
    }

    @Override
    public String getPreview() {
        return delay + " ms";
    }

    @Override
    public String getOutput() {
        return "Delay\0" + delay + "\0" + super.getOutput();
    }
}