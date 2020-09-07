package main.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Spesa implements Serializable
{
    private int hashCodeCliente;
    private Carrello carrello;

    private Date dataConsegna;
    private FasciaOraria fasciaOraria;
    private MetodoPagamento metodoPagamento;
    private StatoConsegna statoConsegna;

    @Override
    public int hashCode() {
        return hashCodeCliente;
    }
}
