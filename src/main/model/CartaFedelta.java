package main.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class CartaFedelta implements Serializable
{
    public String ID = new String();
    public LocalDate dataEmissione;
    public int punti = 0;

    public CartaFedelta(LocalDate dataEmissione)
    {
        Random r = new Random();
        for (int i =0; i<8; ++i)
            this.ID += r.nextInt((9 - 0) + 1);

        this.dataEmissione = dataEmissione;
    }
}
