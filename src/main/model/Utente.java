package main.model;

import java.io.Serializable;

public class Utente extends Persona implements Serializable
{
    private CartaFedelta cartaFedelta;
    private MetodoPagamento metodoPagamento;

    public Utente(String nome, String cognome, String indirizzo, String citta, String telefono, String email,
                  int CAP, int passwordHash, CartaFedelta cartaFedelta, MetodoPagamento metodoPagamento)
    {
        super(nome, cognome, indirizzo, citta, telefono, email, CAP, passwordHash);
        this.cartaFedelta = cartaFedelta;
        this.metodoPagamento = metodoPagamento;
    }
}
