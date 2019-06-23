package GasStation;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class MenuController {
    @FXML
    public Pane pane;

    @FXML
    public Button buttonRun;

    @FXML
    public ChoiceBox<Integer> choiceCar;

    @FXML
    public ChoiceBox<Integer> choicePump;

    @FXML
    private void handleButtonRun(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("simulationWindow.fxml"));
        Parent simulationWindow = loader.load();

        Scene simulationScene = new Scene(simulationWindow);

        SimulationController controller = loader.getController();
        controller.setCarAmount(choiceCar.getValue());
        controller.setPumpAmount(choicePump.getValue());

        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.setScene(simulationScene);

        appStage.setX(200);
        appStage.setY(200);

        appStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Platform.exit();
                System.exit(0);
            }
        });

        appStage.show();

        controller.start();
    }

    @FXML
    public void initialize(){
        for(int i = 1; i <= 20; i++)
            choiceCar.getItems().add(i);
        choiceCar.setValue(2);

        for(int i = 1; i <= 10; i++)
            choicePump.getItems().add(i);
        choicePump.setValue(2);
    }
}