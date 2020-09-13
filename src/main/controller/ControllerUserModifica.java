package main.controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.model.Citta;
import main.model.MetodoPagamento;
import main.model.Persona;
import main.model.Utente;
import main.storage.Database;
import main.utils.StageManager;
import main.utils.Validator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ControllerUserModifica extends Controller{

    @FXML private ImageView covidMarketImageView;
    @FXML private ImageView carrelloImageView;
    @FXML private Label usernameLabel;
    @FXML private ChoiceBox<String> sezioneChoicebox;
    @FXML private TextField indirizzoTextField;
    @FXML private TextField telefonoTextField;
    @FXML private Label nomeLabel;
    @FXML private Label cognomeLabel;
    @FXML private Label capLabel;
    @FXML private ComboBox<Citta> cittaCombobox;
    @FXML private TextField mailTextField;
    @FXML private PasswordField pswTextField;
    @FXML private PasswordField controllopswTextField;
    @FXML private ComboBox<String> pagamentoComboBox;
    @FXML private Button modificaButton;

    private Utente currentUser;
    private ArrayList<Citta> cittaDisponibili;

    @FXML
    private void initialize(){

        //handler
        covidMarketImageView.setOnMouseClicked(this::homeHandler);
        modificaButton.setOnMouseClicked(this::modificaHandler);

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
            cittaCombobox.getItems().add(citta);
        }

        cittaCombobox.setOnAction(foo ->{
            capLabel.setText(cittaCombobox.getValue().CAP);
        });

        // Cambia schermata in base alla scelta
        sezioneChoicebox.getItems().setAll("Profilo", "Tessera Fedelta", "Storico Spese", "Logout");
        sezioneChoicebox.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, t1) -> {
            switch (t1.intValue()) {
                case 0: stageManager.swap(Stages.Profilo);     break;
                case 1: stageManager.swap(Stages.Tessera);     break;
                case 2: stageManager.swap(Stages.SpesaUtente); break;
                case 3: stageManager.swap(Stages.Login);       break;
            }
        });

        carrelloImageView.setOnMouseClicked(this::carrelloButtonHandler);

    }
    private void carrelloButtonHandler(MouseEvent mouseEvent) {
        stageManager.swap(Stages.Carrello);
    }

    @Override
    public void onSwap(Persona target)
    {
        // Deseleziona menu
        sezioneChoicebox.getSelectionModel().select(null);

        this.currentUser = (Utente)target;
        usernameLabel.setText(currentUser.getNome());

        nomeLabel.setText(currentUser.getNome());
        cognomeLabel.setText(currentUser.getCognome());
        indirizzoTextField.setText(currentUser.getIndirizzo());


        String cittaUser = currentUser.getCitta();
        int cittaIndex = 0;
        for(Citta citta : cittaDisponibili){
            if(citta.nome.equals(cittaUser))
                cittaIndex = cittaDisponibili.indexOf(citta);
        }
        cittaCombobox.getSelectionModel().select(cittaIndex);
        capLabel.setText(String.valueOf(currentUser.getCAP()));
        telefonoTextField.setText(currentUser.getTelefono());
        mailTextField.setText(currentUser.getEmail());

        pagamentoComboBox.getItems();
        for(MetodoPagamento pagamento : MetodoPagamento.values())
            pagamentoComboBox.getItems().add(pagamento.name());
        pagamentoComboBox.getSelectionModel().select(currentUser.getMetodoPagamento().name());
    }

    private void homeHandler(MouseEvent mouseEvent) {
        stageManager.swap(Stages.HomeUtente);
    }

    private void modificaHandler(MouseEvent mouseEvent) {
        if (validateUserData())
        {
            String metodoPagamento = pagamentoComboBox.getSelectionModel().getSelectedItem();
            currentUser.setPagamento(MetodoPagamento.valueOf(metodoPagamento));
            currentUser.setIndirizzo(indirizzoTextField.getText(), cittaCombobox.getValue().nome, capLabel.getText());
            currentUser.setTelefono(telefonoTextField.getText());
            currentUser.setEmail(mailTextField.getText());
            if(!pswTextField.getText().isEmpty())
                currentUser.setPassword(pswTextField.getText());

            stageManager.swap(Stages.Profilo);
        }


    }

    private boolean validateUserData() {
        boolean result = true;

        if (!Validator.isTelephoneNumber(telefonoTextField.getText()))
        {
            telefonoTextField.setStyle("-fx-control-inner-background:red");
            result = false;
        }
        if (!Validator.isEmail(mailTextField.getText()))
        {
            mailTextField.setStyle("-fx-control-inner-background:red");
            result = false;
        }

        if (!Validator.isAddressFormat(indirizzoTextField.getText()))
        {
            indirizzoTextField.setStyle("-fx-control-inner-background:red");
            result = false;
        }
        if(!controllopswTextField.getText().equals(pswTextField.getText()))
        {
            controllopswTextField.setStyle("-fx-control-inner-background:red");
            result = false;
        }

        return result;
    }

}
