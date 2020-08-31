package main.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Spesa implements Serializable
{
    private static class Boundle
    {
        private int hashCodeProdotto;
        private int prezzo;
        private int quantita;
    };

    private int hashCodeCliente;
    private ArrayList<Boundle> boundles;

    private Date dataConsegna;
    private FasciaOraria fasciaOraria;
    private MetodoPagamento metodoPagamento;
    private StatoConsegna statoConsegna;

    public int costoTotale() {
        int costo = 0;
        for (Boundle boundle : boundles) {
            costo += boundle.prezzo * boundle.quantita;
        }
        return costo;
    }

    public int saldoPunti() {
        return costoTotale();
    }

    @Override
    public int hashCode() {
        return hashCodeCliente;
    }
}
