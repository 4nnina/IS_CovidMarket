package main;

import main.model.*;

import java.util.HashSet;

public class Database
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
     * @param filename Percorso al file binario da caricare
     * @return Il database, per questioni ergonomiche
     */
    public Database load(String filename)
    {
        try(Deserializer ser = new Deserializer(filename))
        {
            utenti = (HashSet<Utente>) ser.deserialize();
            responsabili = (HashSet<Responsabile>) ser.deserialize();
            prodotti = (HashSet<Prodotto>) ser.deserialize();
            spese = (HashSet<Spesa>) ser.deserialize();
        }
        catch (Exception e)
        {
            System.out.println("Impossibile caricata database da " + filename);
            e.printStackTrace();
        }

        return this;
    }

    /**
     * Salva su memoria secondaria il database
     * @param filename Percorso al file binario da salvare
     */
    public void save(String filename)
    {
        try(Serializer ser = new Serializer(filename))
        {
            ser.serialize(utenti);
            ser.serialize(responsabili);
            ser.serialize(prodotti);
            ser.serialize(spese);
        }
        catch (Exception e)
        {
            System.out.println("Impossibile salvare database su " + filename);
            e.printStackTrace();
        }
    }

    private static Database INSTANCE;
    public static Database getInstance() {
        if (INSTANCE == null) INSTANCE = new Database();
        return INSTANCE;
    }
}
