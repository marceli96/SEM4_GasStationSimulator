package GasStation;

import javafx.application.Platform;
import javafx.scene.control.TextField;

public class Car implements Runnable {
    public int number;
    public int fuelLevel;
    public static final int FUEL_CAPACITY = 60;
    public PumpManager pumpManager;

    public TextField fuelField, statusField, queue;

    public Car(int number, int fuelLevel, PumpManager pumpManager, TextField fuelField, TextField statusField, TextField queue){
        this.number = number;
        this.fuelLevel = fuelLevel;
        this.pumpManager = pumpManager;
        this.fuelField = fuelField;
        this.statusField = statusField;
        this.queue = queue;
    }

    @Override
    public String toString() {
        return "Samochód " + number;
    }

    @Override
    public void run() {
        while(true){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    statusField.setStyle("-fx-text-fill: black; -fx-font-weight: normal;");
                    statusField.setText("Jeździ");
                }
            });

            //samochód jeździ sobie i traci paliwo
            while(fuelLevel >= Car.FUEL_CAPACITY * 0.1){
                fuelLevel--;
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        fuelField.setText(String.valueOf(fuelLevel));
                    }
                });
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //tankowanie
            try {
                pumpManager.mutex.acquire();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        statusField.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                        statusField.setText("Czeka");
                        queue.setText(pumpManager.listString());
                    }
                });
                pumpManager.waitInQueue(this);
                pumpManager.mutex.release();
                pumpManager.car.release();
                pumpManager.pump.acquire();

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        queue.setText(queue.getText());
                        statusField.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                        statusField.setText("Tankuje");
                    }
                });

                while(this.fuelLevel < Car.FUEL_CAPACITY * 0.9){
                    Thread.sleep(100);
                }
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
