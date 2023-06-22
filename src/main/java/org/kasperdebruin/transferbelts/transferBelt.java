package org.kasperdebruin.transferbelts;

import org.kasperdebruin.Machine;
import org.kasperdebruin.boxes.box;
import org.kasperdebruin.boxes.defaultbox;
import org.kasperdebruin.cups.iceCup;
import org.kasperdebruin.pallets.defaultPalet;
import org.kasperdebruin.pallets.palet;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

// Represents een transfer belt
public class transferBelt implements ItransferBelt{
    private final int speed;
    private final boolean canProduceCups;
    private final iceCup producingCupType;

    private boolean isRunning = false;
    private boolean isProducingCups = false;


    private box currentBox = null;
    private Supplier<box> boxSupplier = defaultbox::new;

    private palet currentPalet = null;
    private Supplier<palet> paletSupplier = defaultPalet::new;


    private int totalSpoonsInBox = 0;
    private int totalSpoons = 0;

    private int boxesProduced = 0;
    private int paletsProduced = 0;
    private float litersProduced = 0;


    public transferBelt(int speed, boolean canProduceCups, iceCup producingCupType) {
        this.speed = speed;
        this.canProduceCups = canProduceCups;
        this.producingCupType = producingCupType;
        start();
    }

    ScheduledExecutorService executor = null;
    private void start() {
        isRunning = true;
        if(true){
            executor = Executors.newScheduledThreadPool(1);
            executor.scheduleAtFixedRate(itemScannedRunnable, 0, speed, TimeUnit.SECONDS);
        }else{
            if(executor != null) {
                executor.shutdown();
            }
        }
    }

    //Normaal is dit de scanner
    private Runnable itemScannedRunnable = new Runnable() {
        public void run() {
            addIceCreamInBox();
        }
    };

    protected void addIceCreamInBox(){
        if(currentBox == null){
            addNewBox();
        }
        if(currentPalet == null)
        {
            addNewPalet();
        }

        if(!currentPalet.hasRoom())
        {
            addNewPalet();
            Machine.addPalet();
            paletsProduced++;
        }

        if(!currentBox.hasRoom())
        {
            addNewBox();
            Machine.AddBox();
            currentPalet.addBox(1);
            totalSpoonsInBox = 0;
            boxesProduced++;
        }

        if(isProducingCups)
        {
            totalSpoonsInBox++;
            totalSpoons++;
        }

        litersProduced += producingCupType.getLiterAmount();
        Machine.AddLitersToTotal(producingCupType.getLiterAmount());

        currentBox.addIceCream(1);
    }

    private void addNewBox(){
        currentBox = boxSupplier.get();
    }

    private void addNewPalet(){
        currentPalet = paletSupplier.get();
    }

    public void turnOnOrOff(boolean on) {
        isRunning = on;
    }
    public int getBoxesProduced(){
        return boxesProduced;
    }

    public int getPaletsProduced(){
        return paletsProduced;
    }


    public int getProductionSpeed(){
        return speed;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public box getCurrentBox(){
        return currentBox;
    }

    public palet getCurrentPalet(){
        return currentPalet;
    }

    public void setProducingCups(boolean producingCups) {
        isProducingCups = producingCups;
    }

    public boolean isProducingCups() {
        return isProducingCups;
    }

    public int getTotalSpoonsInBox() {
        return totalSpoonsInBox;
    }

    public int getTotalSpoons() {
        return totalSpoons;
    }

    public boolean getCanProduceCups() {
        return canProduceCups;
    }

    public float getLitersProduced(){
        return litersProduced;
    }

}
