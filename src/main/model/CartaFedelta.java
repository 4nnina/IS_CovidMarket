package main.model;

import java.io.Serializable;
import java.util.Date;

public class CartaFedelta implements Serializable
{
    public String ID;
    public Date dataEmissione;
    public int punti;

    public CartaFedelta(String ID, Date dataEmissione, int punti){
        this.ID = ID;
        this.dataEmissione = dataEmissione;
        this.punti = punti;
    }
}
