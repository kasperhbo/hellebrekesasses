package org.kasperdebruin;

import org.ice1000.jimgui.JImGui;
import org.ice1000.jimgui.util.JniLoader;
import org.kasperdebruin.cups.bigCup;
import org.kasperdebruin.cups.iceCup;
import org.kasperdebruin.cups.smallCup;
import org.kasperdebruin.transferbelts.transferBelt;
import org.kasperdebruin.utils.math;

import java.util.ArrayList;
import java.util.List;

//Deze class represents de main machine die all stats bij houd
public final class Machine {
    private static List<transferBelt> transferbelts = new ArrayList<>();
    private static int totalBoxCount = 0;
    private static int totalPaletCount = 0;
    private static float totalLitersProduced = 0;

    public static void start(){
        //original belt
        bigCup bigCup = new bigCup();
        smallCup smallCup = new smallCup();

        createTransferbelt(2, false, bigCup);

        //faster newer belts
        createTransferbelt(1, true,  smallCup);
        createTransferbelt(1, false, bigCup);
        createTransferbelt(1, true,  bigCup);

        showUI();
    }

    private static void createTransferbelt(int beltSpeed, boolean canProduceCups, iceCup cupType){
        transferBelt belt = new transferBelt(beltSpeed, canProduceCups, cupType);

        transferbelts.add(belt);

        belt.turnOnOrOff(true);

        // Hacky workaround om te voorkomen dat alle "transport belts" tegelijkertijd starten
        // als je dit wel doet werkt de counter niet goed
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void showUI(){
        JniLoader.loadDX11();
        try (JImGui imGui = new JImGui()) {
            while (!imGui.windowShouldClose()) {
                imGui.initNewFrame();

                imGui.begin("main window");
                imGui.text("total boxes produced: " + totalBoxCount);
                imGui.text("total palets produced: " + totalPaletCount);
                imGui.text("total amount of liters produced: " + math.round(totalLitersProduced, 2));

                //Draw for every transfer belt an seperate window to keep track of the production
                for (int i = 0; i < transferbelts.size(); i++) {
                    transferBelt belt = transferbelts.get(i);
                    imGui.begin("transfer belt: " + i);

                    if(belt.isRunning()){
                        if(imGui.button("Stop belt")){
                            belt.turnOnOrOff(false);
                        }
                    }else{
                        if(imGui.button("Start belt")){
                            belt.turnOnOrOff(true);
                        }
                    }

                    imGui.text("is turned on"          + belt.isRunning());
                    imGui.text("production speed:"     + belt.getProductionSpeed());

                    imGui.text("liters produced: "     + math.round(belt.getLitersProduced(),2));


                    imGui.text("ices in current box: " + (belt.getCurrentBox().getCurrentIceAmount()));

                    imGui.text("boxes produced: "      + (belt.getBoxesProduced()));

                    imGui.text("boxes on palet: "    + belt.getCurrentPalet().getCurrentBoxAmount());

                    imGui.text("palets produced: "   + (belt.getPaletsProduced()));

                    if(belt.getCanProduceCups()) {
                        imGui.text("is producing cups: " + (belt.isProducingCups()));
                        imGui.text("total spoons "       + (belt.getTotalSpoons()));
                        imGui.text("total spoons in box" + (belt.getTotalSpoonsInBox()));

                        if (imGui.button("Turn producing cups on or of")) {
                            belt.setProducingCups(!belt.isProducingCups());
                        }
                    }
                }

                imGui.render();
            }
        }
    }

    public static void addPalet() {
        totalPaletCount++;
        System.out.println("total palets produced: " + totalPaletCount);
    }


    public static void AddBox(){
        totalBoxCount++;
        System.out.println("total box count" + totalBoxCount);
    }

    public static void AddLitersToTotal(float amount){
        totalLitersProduced += amount;
        System.out.println("total liters produced" + totalLitersProduced);
   }
}
