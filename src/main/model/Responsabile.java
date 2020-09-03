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

    @Override
    public LoginResult validLogin(String username, String password)
    {
        if (this.username.equals(username)) {
            if (this.passwordHash == password.hashCode()) {
                return LoginResult.Success;
            }
            else {
                return LoginResult.WrongPassword;
            }
        }
        return LoginResult.Failure;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && this.matricola != ((Responsabile)obj).matricola;
    }
}
