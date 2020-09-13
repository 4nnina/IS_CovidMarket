package main.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.stage.Stage;
import main.model.CartaFedelta;
import main.model.Citta;
import main.model.MetodoPagamento;
import main.model.Utente;
import main.storage.Database;
import main.utils.StageManager;
import main.utils.Validator;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

public class ControllerRegistrazione extends Controller
{

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
    private CheckBox attivaCheckbox;

    @FXML
    private Button salvaButton;

    @FXML private ComboBox<Citta> cittaComboBox;
    @FXML private ChoiceBox<MetodoPagamento> pagamentoChoiceBox;


    private ArrayList<Citta> cittaDisponibili;

    @FXML
    private void initialize()
    {
        salvaButton.setOnAction(this::salvaButtonHandler);
        covidMarketImageView.setOnMouseClicked(this::loginHandler);

        pagamentoChoiceBox.getItems().addAll(MetodoPagamento.values());
        pagamentoChoiceBox.getSelectionModel().select(MetodoPagamento.Nessuno);

        // Carica citta dal file
        cittaDisponibili = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/citta.txt")))
        {
            String line = reader.readLine();
            while (line != null)
            {
                if (line.length() > 3)
                {
                    int separator = line.indexOf('\t');
                    String nome = (String) line.subSequence(0, separator);
                    String CAP = (String) line.subSequence(separator + 1, line.indexOf('\t', separator + 1));

                    cittaDisponibili.add(new Citta(nome, CAP));
                }
                line = reader.readLine();
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        for(Citta citta:cittaDisponibili){
            cittaComboBox.getItems().add(citta);
        }
        cittaComboBox.setOnAction(foo ->{
            capLabel.setText(cittaComboBox.getValue().CAP);
        });


    }

    private void loginHandler(MouseEvent mouseEvent) {
        stageManager.swap(Stages.Login);
    }


    // Controlla se i parametri inseriti vanno bene
    private boolean validateUserData()
    {
        boolean result = true;

        if (!Validator.isAlphanumeric(nomeTextField.getText()))
        {
            nomeTextField.setStyle("-fx-control-inner-background:red");
            result = false;
        }
        else
            nomeTextField.setStyle("-fx-control-inner-background: ecfbfa");

        if (!Validator.isAlphanumeric(cognomeTextField.getText()))
        {
            cognomeTextField.setStyle("-fx-control-inner-background:red");
            result = false;
        }
        else
            nomeTextField.setStyle("-fx-control-inner-background: ecfbfa");

        if (!Validator.isTelephoneNumber(telefonoTextField.getText()))
        {
            telefonoTextField.setStyle("-fx-control-inner-background:red");
            result = false;
        }
        else
            nomeTextField.setStyle("-fx-control-inner-background: ecfbfa");

        if (!Validator.isEmail(mailTextField.getText()))
        {
            mailTextField.setStyle("-fx-control-inner-background:red");
            result = false;
        }else
            nomeTextField.setStyle("-fx-control-inner-background: ecfbfa");

        if(pswPasswordField.getText().isEmpty())
        {
            pswPasswordField.setStyle("-fx-control-inner-background:red");
            result = false;
        }else
            nomeTextField.setStyle("-fx-control-inner-background: ecfbfa");

        if(confermaPswPasswordField.getText().isEmpty() ||
                !confermaPswPasswordField.getText().equals(pswPasswordField.getText()))
        {
            confermaPswPasswordField.setStyle("-fx-control-inner-background:red");
            result = false;
        }else
            nomeTextField.setStyle("-fx-control-inner-background: ecfbfa");

        if (!Validator.isAddressFormat(indirizzoTextField.getText()))
        {
            indirizzoTextField.setStyle("-fx-control-inner-background:red");
            result = false;
        }else
            nomeTextField.setStyle("-fx-control-inner-background: ecfbfa");


        if(capLabel.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING, "Nessuna città selezionata");
            alert.showAndWait();
            result = false;
        }
        /*
        int tmp = 0;
        try{tmp = Integer.valueOf(capLabel.getText()); } catch (NumberFormatException e)
        {
            capLabel.setStyle("-fx-control-inner-background:red");
            result = false;
        }
        */

        return result;
    }

    @FXML
    private void salvaButtonHandler(ActionEvent actionEvent)
    {
        //TODO
        System.out.println("premuto salva");

        if (validateUserData())
        {
            Utente.Builder builder = new Utente.Builder()
                    .setNominativo(nomeTextField.getText().toUpperCase(), cognomeTextField.getText().toUpperCase())
                    .setIndirizzo(indirizzoTextField.getText(), cittaComboBox.getValue().nome, capLabel.getText())
                    .setTelefono(telefonoTextField.getText())
                    .setEmail(mailTextField.getText())
                    .setPassword(pswPasswordField.getText())
                    .setCartaFedelta(null)
                    .setMetodoPagamento(pagamentoChoiceBox.getValue());

            if (attivaCheckbox.isSelected()) {
                builder.setCartaFedelta(new CartaFedelta(LocalDate.now()));
            }

            Utente user = builder.build();
            Database database = Database.getInstance();

            if(existingUser(mailTextField.getText()))
            {
                if(database.getUtenti().add(user)) {
                    // Ha inserito con successo
                    stageManager.setTargetUser(user);
                    stageManager.swap(Stages.HomeUtente);
                }
                else
                    System.out.println("non inserito in database");

            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Utente già esistente");
                alert.showAndWait();
                mailTextField.clear();
                System.out.println("UTENTE ESISTE GIA");
            }
        }
    }

    private boolean existingUser(String mail) {

        Database database = Database.getInstance();
        for(Utente user: database.getUtenti()){
            if(user.getEmail().equals(mail))
                return false;
        }

        return true;
    }
}
