package main.controller;

import main.model.DatiConsegna;
import main.model.FasciaOraria;
import main.model.MetodoPagamento;

import java.util.Calendar;

public class PopupCarrello extends Popup<DatiConsegna>
{
    public PopupCarrello()
    {
        //loadFXML(this, "../style/login.fxml");
        returnResult(new DatiConsegna(Calendar.getInstance().getTime(), MetodoPagamento.PayPal, FasciaOraria.Mattina));
    }
}
