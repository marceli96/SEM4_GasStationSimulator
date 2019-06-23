package GasStation;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.TextField;

import java.util.Random;

public class SimulationController {
    private int carAmount, pumpAmount;
    private Car cars[];
    private Pump pumps[];
    private Random rand;
    private PumpManager pumpManager;

    @FXML
    public TextField status1, status2, status3, status4, status5, status6, status7, status8, status9, status10, status11,
            status12, status13, status14, status15, status16, status17, status18, status19, status20;

    @FXML
    public TextField fuel1, fuel2, fuel3, fuel4, fuel5, fuel6, fuel7, fuel8, fuel9, fuel10, fuel11, fuel12, fuel13, fuel14,
            fuel15, fuel16, fuel17, fuel18, fuel19, fuel20;

    @FXML
    public TextField pumpStatus1, pumpStatus2, pumpStatus3, pumpStatus4, pumpStatus5, pumpStatus6, pumpStatus7, pumpStatus8,
            pumpStatus9, pumpStatus10;

    @FXML
    public TextField queue;

    @FXML
    public Group group1, group2, group3, group4, group5, group6, group7, group8, group9, group10, group11, group12, group13,
            group14, group15, group16, group17, group18, group19, group20;

    @FXML
    public Group groupPump1, groupPump2, groupPump3, groupPump4, groupPump5, groupPump6, groupPump7, groupPump8, groupPump9,
            groupPump10;

    public void setCarAmount(int carAmount) {
        this.carAmount = carAmount;
    }

    public void setPumpAmount(int pumpAmount) {
        this.pumpAmount = pumpAmount;
    }

    @FXML
    public void start(){
        pumpManager = new PumpManager();
        rand = new Random();
        cars = new Car[carAmount];
        pumps = new Pump[pumpAmount];

        TextField carStatus[] = {status1, status2, status3, status4, status5, status6, status7, status8, status9,
                status10, status11, status12, status13, status14, status15, status16, status17, status18, status19, status20};
        TextField fuelStatus[] = {fuel1, fuel2, fuel3, fuel4, fuel5, fuel6, fuel7, fuel8, fuel9, fuel10, fuel11,
                fuel12, fuel13, fuel14, fuel15, fuel16, fuel17, fuel18, fuel19, fuel20};
        TextField pumpStatus[] = {pumpStatus1, pumpStatus2, pumpStatus3, pumpStatus4, pumpStatus5, pumpStatus6,
                pumpStatus7, pumpStatus8, pumpStatus9, pumpStatus10};

        Group carGroup[] = {group1, group2, group3, group4, group5, group6, group7, group8, group9, group10, group11,
                group12, group13, group14, group15, group16, group17, group18, group19, group20};
        Group pumpGroup[] = {groupPump1, groupPump2, groupPump3, groupPump4, groupPump5, groupPump6, groupPump7,
                groupPump8, groupPump9, groupPump10};

        for(int i = carAmount; i < 20; i++)
            carGroup[i].setDisable(true);

        for(int i = pumpAmount; i < 10; i++)
            pumpGroup[i].setDisable(true);

        for(int i = 0; i < carAmount; i++)
            cars[i] = new Car((i+1), rand.nextInt(40) + 20, pumpManager, fuelStatus[i], carStatus[i], queue);

        for(int i = 0; i < pumpAmount; i++)
            pumps[i] = new Pump(pumpManager, pumpStatus[i], queue);

        for(int i = 0; i < carAmount; i++)
            new Thread(cars[i]).start();

        for(int i = 0; i < pumpAmount; i++)
            new Thread(pumps[i]).start();

    }
}