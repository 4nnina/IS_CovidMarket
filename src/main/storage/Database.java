package main.storage;

import main.model.*;

import java.util.HashSet;

public class Database implements IDatabase
{
    private HashSet<Utente> utenti = new HashSet<>();;
    private HashSet<Responsabile> responsabili = new HashSet<>();;
    private HashSet<Prodotto> prodotti = new HashSet<>();;
    private HashSet<Spesa> spese = new HashSet<>();

    public HashSet<Utente> getUtenti() {
        return utenti;
    }
    public HashSet<Responsabile> getResponsabili() {
        return responsabili;
    }
    public HashSet<Prodotto> getProdotti() {
        return prodotti;
    }
    public HashSet<Spesa> getSpese() {
        return spese;
    }

    /**
     * Carica da memoria secondaria il database
     * @param ser Serializer da usare per il caricamento
     * @return Il database, per questioni ergonomiche
     */
    @Override
    public Database load(IDeserializer ser)
    {
        utenti = (HashSet<Utente>) ser.deserialize();
        responsabili = (HashSet<Responsabile>) ser.deserialize();
        prodotti = (HashSet<Prodotto>) ser.deserialize();
        spese = (HashSet<Spesa>) ser.deserialize();

        return this;
    }

    /**
     * Salva su memoria secondaria il database
     * @param ser Serializer da usare per il salvataggio
     */
    @Override
    public void save(ISerializer ser)
    {
        ser.serialize(utenti);
        ser.serialize(responsabili);
        ser.serialize(prodotti);
        ser.serialize(spese);
    }

    private static Database INSTANCE;
    public static Database getInstance() {
        if (INSTANCE == null) INSTANCE = new Database();
        return INSTANCE;
    }
}
