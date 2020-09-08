package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import main.controller.PopupLogin;
import main.controller.SectionUser;
import main.controller.Stages;
import main.model.*;
import main.storage.BinaryDeserializer;
import main.storage.BinarySerializer;
import main.storage.Database;
import main.storage.ISerializer;
import main.utils.Dashboard;
import main.utils.StageManager;

import java.util.Calendar;
import java.util.EnumSet;
import java.util.Optional;

public class Program extends Application
{
    private static final String PATH_DATABASE = "database.bin";
    private static final String PATH_FXML = "../resources/fxml/";

    @Override
    public void start(Stage primaryStage) throws Exception
    {

        Database database = Database.getInstance();

        /*
        try(BinaryDeserializer ser = new BinaryDeserializer(PATH_DATABASE)) {
            database.load(ser);
        }
        */

        Utente sis = new Utente.Builder()
                .setNominativo("sis", "sis")
                .setIndirizzo("sis sis 29", "sis", "36071")
                .setEmail("sis@gmail.com")
                .setTelefono("3779927407")
                .setCartaFedelta(new CartaFedelta("", Calendar.getInstance().getTime()))
                .setMetodoPagamento(MetodoPagamento.PayPal)
                .setPassword("sis")
                .build();

        database.getUtenti().add(sis);

        Responsabile resp = new Responsabile.Builder()
                .setDataDiNascita(Calendar.getInstance().getTime())
                .setLuogoDiNascita("To mare")
                .setMatricola(5236437)
                .setRepartiGestiti(EnumSet.allOf(Reparto.class))
                .setEmail("resp@gmail.com")
                .setIndirizzo("", "", "0")
                .setUsername("resp")
                .setNominativo("resp", "resp")
                .setPassword("resp")
                .setTelefono("3779927407")
                .build();

        database.getResponsabili().add(resp);

        database.getProdotti().add(new Prodotto.Builder()
                .setNome("Banane")
                .setMarca("Chiquila")
                .addAttributo(Attributo.SenzaLatte)
                .addAttributo(Attributo.Vegano)
                .addAttributo(Attributo.Biologico)
                .addAttributo(Attributo.Vegetariano)
                .setImagePath("main/resources/images/banane.png")
                .setPrezzo(5)
                .setReparto(Reparto.Frutta)
                .setQuantitaDisponibile(4)
                .setQuantitaPerConfezione(4)
                .build());

        database.getProdotti().add(new Prodotto.Builder()
                .setNome("Gocciole")
                .setMarca("Pavesi")
                .addAttributo(Attributo.Vegetariano)
                .addAttributo(Attributo.Vegano)
                .setImagePath("main/resources/images/gocciole.png")
                .setPrezzo(2)
                .setReparto(Reparto.Biscotti)
                .setQuantitaDisponibile(999)
                .setQuantitaPerConfezione(30)
                .build());

        PopupLogin popupLogin = new PopupLogin();
        Optional<Utente> optionUser = popupLogin.show();
        if (optionUser.isPresent())
        {
            // Dashboard specifica per l'utente
            Dashboard<SectionUser> userDashboard = new Dashboard.Builder<SectionUser>(primaryStage)
                    .controllerDashboard(PATH_FXML + "utente_dashboard.fxml")
                    .controllerSection(SectionUser.Home, PATH_FXML + "utente_home.fxml")
                    .controllerSection(SectionUser.Carrello, PATH_FXML + "utente_carrello.fxml")
                    .launch(sis);
        }
    }

    @Override
    public void stop() throws Exception
    {
        super.stop();

        Database database = Database.getInstance();
        try(BinarySerializer ser = new BinarySerializer(PATH_DATABASE)) {
            database.save(ser);
        }
    }

    public static void main(String[] args)
    {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        launch(args);
    }
}
