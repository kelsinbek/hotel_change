package com.example.hotel_8;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class AdminHotelController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button Logout;

    @FXML
    private Button addroom;

    @FXML
    private Spinner<Integer> finances;

    @FXML
    private TextField hoteladdress;

    @FXML
    private Button hotelinfo;

    @FXML
    private TextField hotelname;

    @FXML
    private Button profile;

    @FXML
    private Text roomtext;

    @FXML
    private Button save;

    @FXML
    private Spinner<Integer> stars;

    @FXML
    private Text titlename;

    @FXML
    private Button workerinfo;

    @FXML
    private Button workers;

    @FXML
    private Text workerscount;

    @FXML
    private Button clientsinfo;

    @FXML
    void initialize() {
        workerscount.setText(workerscount.getText() +" " + Data_work.getWorkers_count());
        roomtext.setText(roomtext.getText() + " " + Data_work.getCountRooms());

        // Value factory.
        SpinnerValueFactory<Integer> valueFactory = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 5);

        SpinnerValueFactory<Integer> valueFactory2 = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 999999999);

        try{
            ResultSet rs = Data_work.getHotelinfo();
            rs.next();
            hotelname.setText(rs.getString("Name"));
            hoteladdress.setText(rs.getString("Address"));
            stars.setValueFactory(valueFactory);
            stars.setEditable(true);
            finances.setValueFactory(valueFactory2);
            finances.setEditable(true);
            stars.getValueFactory().setValue(rs.getInt("Stars"));
            finances.getValueFactory().setValue(rs.getInt("Finances"));

        } catch (SQLException e) {
            e.printStackTrace();
        }

        save.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
//                                    SignInBut.getScene().getWindow().hide();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("Look, a Confirmation Dialog");
                alert.setContentText("Are you ok with this?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    // ... user chose OK
                    try {
                        Data_work.changeHotel(hotelname.getText(), hoteladdress.getText(), stars.getValue(), finances.getValue());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    // ... user chose CANCEL or closed the dialog
                }


            }
        });

        addroom.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
//                                    SignInBut.getScene().getWindow().hide();
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addNewRoom.fxml"));
                    Parent root1 = (Parent) fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setTitle("ABC");
                    stage.setScene(new Scene(root1));
                    stage.show();

                    stage.setOnHiding(new EventHandler<WindowEvent>() {

                        @Override
                        public void handle(WindowEvent event) {
                            Platform.runLater(new Runnable() {

                                @Override
                                public void run() {
                                    roomtext.setText("Rooms: " + Data_work.getCountRooms());
                                }
                            });
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });

    }

}
