package main.model;

public class Persona
{
    public String nome, cognome, indirizzo, citta, telefono, email;
    protected int CAP, passwordHash;

    @Override
    public int hashCode() {
        return nome.hashCode() ^ cognome.hashCode() ^ email.hashCode();
    }
}
