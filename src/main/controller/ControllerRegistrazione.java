package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.utils.StageManager;

import java.awt.*;

public class ControllerRegistrazione {

    @FXML
    private TextField nomeTextField;

    @FXML
    private TextField cognomeTextField;

    @FXML
    private TextField indirizzoTextField;

    @FXML
    private TextField telefonoTextField;

    @FXML
    private Label capLabel;

    @FXML
    private TextField mailTextField;

    @FXML
    private PasswordField pswPasswordField;

    @FXML
    private PasswordField confermaPswPasswordField;

    @FXML
    private ImageView covidMarketImageView;

    @FXML
    private RadioButton siRadioButton;

    @FXML
    private RadioButton noRadioButton;

    @FXML
    private Label tesseraFedeltàLabel;

    @FXML
    private Button salvaButton;

    @FXML
    private void initialize(){
        //handler
        salvaButton.setOnAction(this::salvaButtonHandler);
        covidMarketImageView.setOnMouseClicked(this::loginHandler);
        //TODO

    }

    private void loginHandler(MouseEvent mouseEvent) {
        StageManager loginPage = new StageManager();
        loginPage.setStageLogin((Stage) covidMarketImageView.getScene().getWindow());
    }

    private void salvaButtonHandler(ActionEvent actionEvent) {
        //TODO
        System.out.println("premuto salva");
    }

}
