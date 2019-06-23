package GasStation;

import javafx.application.Platform;
import javafx.scene.control.TextField;

public class Pump implements Runnable {
    public PumpManager pumpManager;
    public TextField statusField, queue;

    public Pump(PumpManager pumpManager, TextField statusField, TextField queue){
        this.pumpManager = pumpManager;
        this.statusField = statusField;
        this.queue = queue;
    }

    @Override
    public void run() {
        while(true){
            try {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        statusField.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                        statusField.setText("Czeka");
                    }
                });

                pumpManager.car.acquire();

                pumpManager.mutex.acquire();
                Car car = pumpManager.takeFirstCarFromQueue();
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        queue.setText(pumpManager.listString());
                    }
                });
                pumpManager.mutex.release();

                pumpManager.pump.release();

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        statusField.setStyle("-fx-text-fill: green; -fx-font-weight: normal;");
                        statusField.setText("Tankuje " + car);
                    }
                });

                while(car.fuelLevel < Car.FUEL_CAPACITY * 0.9){
                    car.fuelLevel++;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            car.fuelField.setText(String.valueOf(car.fuelLevel));
                        }
                    });
                    Thread.sleep(100);
                }
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}