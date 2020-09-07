package main.model;

import main.model.FasciaOraria;
import main.model.MetodoPagamento;

import java.util.Date;

public class DatiConsegna
{
    public Date date;
    public MetodoPagamento metodoPagamento;
    public FasciaOraria fasciaOraria;

    public DatiConsegna(Date date, MetodoPagamento metodoPagamento, FasciaOraria fasciaOraria) {
        this.date = date;
        this.metodoPagamento = metodoPagamento;
        this.fasciaOraria = fasciaOraria;
    }
}
