package main.model;

import java.io.Serializable;
import java.util.Date;
import java.util.EnumSet;

public class Responsabile extends Persona implements Serializable
{
    private Date dataDiNascita;
    private String luogoDiNascita;
    private EnumSet<Reparto> repartiGestiti;
    private int matricola;
    private String username;

    public Responsabile(String nome, String cognome, String indirizzo, String citta, String telefono,
                        String email, int CAP, int passwordHash, Date dataDiNascita, String luogoDiNascita,
                        EnumSet<Reparto> repartiGestiti, int matricola, String username)
    {
        super(nome, cognome, indirizzo, citta, telefono, email, CAP, passwordHash);
        this.luogoDiNascita = luogoDiNascita;
        this.repartiGestiti = repartiGestiti;
        this.dataDiNascita = dataDiNascita;
        this.matricola = matricola;
        this.username = username;
    }
}
