package main.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class CartaFedelta implements Serializable
{
    public String ID;
    public LocalDate dataEmissione;
    public int punti = 0;

    public CartaFedelta(LocalDate dataEmissione)
    {
        this.ID = String.valueOf(UUID.randomUUID().toString().hashCode());
        this.dataEmissione = dataEmissione;
    }
}
