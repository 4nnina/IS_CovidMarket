package main.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Spesa implements Serializable
{
    private Utente utente;
    private Carrello carrello;
    private DatiConsegna datiConsegna;
    private StatoConsegna statoConsegna;

    public Spesa(Utente utente, Carrello carrello, DatiConsegna datiConsegna) {
        this.utente = utente;
        this.carrello = carrello;
        this.datiConsegna = datiConsegna;
        this.statoConsegna = StatoConsegna.Preparazione;
    }

    @Override
    public int hashCode() {
        return utente.hashCode() ^ carrello.getProdotti().hashCode() ^ datiConsegna.date.hashCode();
    }
}
