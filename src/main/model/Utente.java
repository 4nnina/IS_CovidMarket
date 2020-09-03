package main.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Utente extends Persona implements Serializable
{
    private CartaFedelta cartaFedelta;
    private MetodoPagamento metodoPagamento;

    // Items che non sono ancora stati confermati (prodotto, quantita)
    public HashMap<Prodotto, Integer> carrelloCorrente = new HashMap<>();

    public Utente(String nome, String cognome, String indirizzo, String citta, String telefono, String email,
                  int CAP, int passwordHash, CartaFedelta cartaFedelta, MetodoPagamento metodoPagamento)
    {
        super(nome, cognome, indirizzo, citta, telefono, email, CAP, passwordHash);
        this.cartaFedelta = cartaFedelta;
        this.metodoPagamento = metodoPagamento;
    }

    @Override
    public LoginResult validLogin(String email, String password)
    {
        if (this.email.equals(email)) {
            if (this.passwordHash == password.hashCode()) {
                return LoginResult.Success;
            }
            else {
                return LoginResult.WrongPassword;
            }
        }
        return LoginResult.Failure;
    }
}
