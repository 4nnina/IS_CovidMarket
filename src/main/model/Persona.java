package main.model;

import java.io.Serializable;

public abstract class Persona implements Serializable
{
    protected String nome, cognome, indirizzo, citta, telefono, email;
    protected int CAP, passwordHash;

    protected Persona(String nome, String cognome, String indirizzo, String citta,
                      String telefono, String email, int CAP, int passwordHash)
    {
        this.nome = nome;
        this.cognome = cognome;
        this.indirizzo = indirizzo;
        this.citta = citta;
        this.telefono = telefono;
        this.email = email;
        this.CAP = CAP;
        this.passwordHash = passwordHash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj.getClass() == this.getClass())
        {
            Persona other = (Persona)obj;
            return other.nome == this.nome && other.cognome == this.cognome && other.email == this.email;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return nome.hashCode() ^ cognome.hashCode() ^ email.hashCode();
    }

    // Controlla se le credenziali sono corrette per questa persona
    public abstract LoginResult validLogin(String username, String password);
}
