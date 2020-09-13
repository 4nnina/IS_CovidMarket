package main.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import main.model.Attributo;
import main.model.Prodotto;
import main.model.Reparto;
import main.model.Responsabile;
import main.utils.Validator;

import java.net.URL;
import java.util.EnumSet;
import java.util.ResourceBundle;

public class PopupProdotto extends Popup<Prodotto> implements Initializable
{
    @FXML
    private TextField nomeTextField;

    @FXML private Spinner<Double> prezzoSpinner;
    @FXML private Spinner<Integer> qtSpinner;
    @FXML private Spinner<Integer> disponibileSpinner;

    @FXML private VBox caratteristicheVBox;
    @FXML private ComboBox<Reparto> repartoComboBox;

    @FXML private TextField marcaTextField;
    @FXML private TextField immagineTextField;

    @FXML private Button button;


    private Responsabile currentResponsabile;
    private Prodotto currentProdotto;

    public PopupProdotto(Responsabile responsabile, Prodotto prodotto) {
        super("../resources/fxml/inserisciProdotto.fxml");
        this.currentResponsabile = responsabile;
        this.currentProdotto = prodotto;
    }

    @Override
    public Object self() {
        return this;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        qtSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100));
        prezzoSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.01, 1000, 0.01, 0.01));
        disponibileSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000));

        // Popola caratteristiche possibili
        caratteristicheVBox.setPrefHeight(Attributo.values().length * 20);
        for(Attributo attrib : Attributo.values())
            caratteristicheVBox.getChildren().add(new CheckBox(attrib.toString()));

        // Setta campi pre esistenti
        if (currentProdotto != null)
        {
            button.setText("Modifica");
        }
        else
        {
            button.setText("Aggiungi");
        }
    }

    @Override
    protected void onShow()
    {
        // Carica tutti i reparti
        for (Reparto rep: Reparto.values())
            if (rep != Reparto.Tutto && currentResponsabile.getRepartiGestiti().contains(rep))
                repartoComboBox.getItems().add(rep);
        repartoComboBox.getSelectionModel().select(0);
    }

    @FXML
    void buttonHandler(ActionEvent event)
    {

        if(productIsValid()) {
            // Modifica
            if (currentProdotto != null) {

            }
            // Aggiungi
            else {
                // Accumula caratteristiche
                EnumSet<Attributo> attributi = EnumSet.noneOf(Attributo.class);
                for (int i = 0; i < Attributo.values().length; ++i) {
                    CheckBox checkBox = (CheckBox) caratteristicheVBox.getChildren().get(i);
                    if (checkBox.isSelected())
                        attributi.add(Attributo.values()[i]);
                }

                String pathImage = "main/resources/images/";
                Prodotto prodotto = new Prodotto.Builder()
                        .setNome(nomeTextField.getText())
                        .setPrezzo(prezzoSpinner.getValue())
                        .setMarca(marcaTextField.getText())
                        .setImagePath(pathImage + immagineTextField.getText())
                        .setQuantitaPerConfezione(qtSpinner.getValue())
                        .setReparto(repartoComboBox.getValue())
                        .setAttributi(attributi)
                        .setQuantitaDisponibile(disponibileSpinner.getValue())
                        .build();

                System.out.println("ho confermato");
                close(prodotto);
            }
        }
    }

    private boolean productIsValid() {
        boolean result = true;

        if(nomeTextField.getText().isEmpty()){
            nomeTextField.setStyle("-fx-control-inner-background:#ff0000");
            result = false;
        }
        else{
            nomeTextField.setStyle("-fx-control-inner-background: ecfbfa");
        }
        if(marcaTextField.getText().isEmpty()){
            marcaTextField.setStyle("-fx-control-inner-background:red");
            result = false;
        }
        else{
            marcaTextField.setStyle("-fx-control-inner-background:ecfbfa");
        }
        if(immagineTextField.getText().isEmpty()){
            immagineTextField.setStyle("-fx-control-inner-background:red");
            result = false;
        }
        else{
            immagineTextField.setStyle("-fx-control-inner-background:ecfbfa");
        }
        if(!Validator.isDecimalNumber(String.valueOf(prezzoSpinner.getValue()))){
            System.out.println(String.valueOf(prezzoSpinner));
            prezzoSpinner.setStyle("-fx-control-inner-background:red");
            prezzoSpinner.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.01, 1000, 0.01, 0.01));
            result = false;
        }
        else
            prezzoSpinner.setStyle("-fx-control-inner-background:white");

        return result;
    }

}
