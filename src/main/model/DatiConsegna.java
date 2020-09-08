package main.model;

import main.model.FasciaOraria;
import main.model.MetodoPagamento;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class DatiConsegna implements Serializable
{
    public LocalDate date;
    public MetodoPagamento metodoPagamento;
    public FasciaOraria fasciaOraria;

    public DatiConsegna(LocalDate date, MetodoPagamento metodoPagamento, FasciaOraria fasciaOraria) {
        this.date = date;
        this.metodoPagamento = metodoPagamento;
        this.fasciaOraria = fasciaOraria;
    }
}
