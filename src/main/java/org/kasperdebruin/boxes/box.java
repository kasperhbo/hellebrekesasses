package org.kasperdebruin.boxes;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public abstract class box {
    private final int boxCapacity;
    private boolean hasRoom = true;
    private int currentAmount = 0;


    public box(int boxCapacity) {
        this.boxCapacity = boxCapacity;
    }

    public void addIceCream(int amount) {

        if(hasRoom)
            currentAmount+=amount;

        if(currentAmount>=boxCapacity)
            hasRoom = false;
    }

    public boolean hasRoom() {
        return hasRoom;
    }

    public int getCurrentIceAmount(){
        return currentAmount;
    }
}
