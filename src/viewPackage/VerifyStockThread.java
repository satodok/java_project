package viewPackage;

import controllerPackage.ApplicationController;

import java.sql.*;
import exceptionPackage.*;
import modelPackage.*;

import javax.swing.*;


public class VerifyStockThread extends Thread{

    private ApplicationController controller;
    private static final int BRONZE_THRESHOLD = 4;
    private static final int SILVER_THRESHOLD = 3;
    private static final int GOLD_THRESHOLD = 1;
    private MenuWindow menuWindow;


    public VerifyStockThread(MenuWindow menuWindow){
        this.controller = new ApplicationController();
        this.menuWindow = menuWindow;
    }

    @Override
    public void run() {
        while (true) {
                checkCounters();
            try {
                Thread.sleep(10000); // Sleep for 10 seconds
            } catch (InterruptedException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);;
            }
        }
    }

    private void checkCounters() {
        try {
            String alertMessage;
            for (Counter counter : controller.getStock()) {
                System.out.println(counter.getBikesRemaining());
                alertMessage = "Alerte ! La station n°" + counter.getStationNumber() + " n'a plus que " +
                        counter.getBikesRemaining() + " vélos disponibles de type " + counter.getTypeName() + " !";
                if (counter.getBikesRemaining() == 0) {
                    menuWindow.displayStockInformation("Alerte : stock vide pour la station n°"+counter.getBikesRemaining());
                    System.out.println("Alerte : stock vide");
                } else if (counter.getTypeName().equals("BRONZE") && counter.getBikesRemaining() <= BRONZE_THRESHOLD) {
                    System.out.println("Alerte : stock faible");
                    menuWindow.displayStockInformation(alertMessage);
                } else if (counter.getTypeName().equals("SILVER") && counter.getBikesRemaining() <= SILVER_THRESHOLD) {
                    menuWindow.displayStockInformation(alertMessage);
                } else if (counter.getTypeName().equals("GOLD") && counter.getBikesRemaining() <= GOLD_THRESHOLD) {
                    menuWindow.displayStockInformation(alertMessage);
                }
            }
        }

        catch(UnfoundResearchException | ConnectionException exception){
            JOptionPane.showMessageDialog(null, exception.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }

    }

}

