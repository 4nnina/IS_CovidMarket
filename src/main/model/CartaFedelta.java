package main.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class CartaFedelta implements Serializable
{
    public String ID;
    public LocalDate dataEmissione;
    public int punti = 0;

    public CartaFedelta(String ID, LocalDate dataEmissione) {
        this.ID = ID;
        this.dataEmissione = dataEmissione;
    }
}
