package main;

import javafx.application.Application;
import javafx.stage.Stage;
import main.controller.Stages;
import main.model.*;
import main.storage.BinaryDeserializer;
import main.storage.BinarySerializer;
import main.storage.Database;
import main.utils.StageManager;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.EnumSet;

public class Program extends Application
{
    private static final String PATH_DATABASE = "database.bin";

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Database database = Database.getInstance();

        try(BinaryDeserializer ser = new BinaryDeserializer(PATH_DATABASE)) {
            database.load(ser);
        }
/*
        Utente sis = new Utente.Builder()
                .setNominativo("sis", "sis")
                .setIndirizzo("sis sis 29", "sis", "36071")
                .setEmail("sis@gmail.com")
                .setTelefono("3779927407")
                .setCartaFedelta(new CartaFedelta(LocalDate.now()))
                .setMetodoPagamento(MetodoPagamento.PayPal)
                .setPassword("sis")
                .build();

        database.getUtenti().add(sis);

        Responsabile resp = new Responsabile.Builder()
                .setDataDiNascita(Calendar.getInstance().getTime())
                .setLuogoDiNascita("Arzignano")
                .setMatricola("5236437")
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
                .setPrezzo(1.69)
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
                .setPrezzo(2.50)
                .setReparto(Reparto.Biscotti)
                .setQuantitaDisponibile(999)
                .setQuantitaPerConfezione(1)
                .build());

        database.getProdotti().add(new Prodotto.Builder()
                .setNome("Insalata")
                .setMarca("Bonduelle")
                .addAttributo(Attributo.Vegetariano)
                .addAttributo(Attributo.Vegano)
                .setImagePath("main/resources/images/insalata.png")
                .setPrezzo(1.09)
                .setReparto(Reparto.Verdura)
                .setQuantitaDisponibile(30)
                .setQuantitaPerConfezione(1)
                .build());

*/

        StageManager stageManager = new StageManager(primaryStage);
        stageManager.swap(Stages.Login);
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
