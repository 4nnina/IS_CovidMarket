package main;

import main.model.*;

public class Database
{
    public final String UTENTI_FILENAME = "utenti.db";
    public Barile<Utente> utenti = new Barile<>();

    public final String RESPONSABILI_FILENAME = "../resources/data/responsabili.db";
    public Barile<Responsabile> responsabili = new Barile<>();

    public Barile<Prodotto> prodotti = new Barile<>();
    public Barile<Spesa> spese = new Barile<>();

    public void load()
    {
        // Carica tutti i dati dalla memoria secondaria
        utenti.load(UTENTI_FILENAME);
        //responsabili.load(RESPONSABILI_FILENAME);
        //prodotti.load("resources/data/prodotti.db");
        //spese.load("resources/data/spese.db");
    }

    public void save()
    {
        utenti.save(UTENTI_FILENAME);
        //responsabili.save(RESPONSABILI_FILENAME);
        //prodotti.save("resources/data/prodotti.db");
        //spese.save("resources/data/spese.db");
    }

    private static Database instance;
    public static Database getInstance()
    {
        if (instance == null)
            instance = new Database();
        return instance;
    }
}
