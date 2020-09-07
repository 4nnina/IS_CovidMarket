package main.model;

import java.io.Serializable;
import java.util.Date;

public class CartaFedelta implements Serializable
{
    public String ID;
    public Date dataEmissione;
    public int punti = 0;

    public CartaFedelta(String ID, Date dataEmissione) {
        this.ID = ID;
        this.dataEmissione = dataEmissione;
    }
}
