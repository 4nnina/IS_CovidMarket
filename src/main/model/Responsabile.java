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

    protected Responsabile(Builder builder)
    {
        super(builder);
        this.dataDiNascita = builder.dataDiNascita;
        this.luogoDiNascita = builder.luogoDiNascita;
        this.repartiGestiti = builder.repartiGestiti.clone();
        this.matricola = builder.matricola;
        this.username = builder.username;
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

    public EnumSet<Reparto> getRepartiGestiti() {
        return repartiGestiti;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && this.matricola != ((Responsabile)obj).matricola;
    }

    /**
     * Builder pattern per il responsabile
     */
    public static class Builder extends Persona.Builder<Responsabile.Builder>
    {
        private Date dataDiNascita;
        private String luogoDiNascita;
        private EnumSet<Reparto> repartiGestiti;
        private int matricola;
        private String username;

        @Override
        public Responsabile build() {
            return new Responsabile(this);
        }

        @Override
        protected Responsabile.Builder self() {
            return this;
        }

        public Builder setDataDiNascita(Date dataDiNascita) {
            this.dataDiNascita = dataDiNascita;
            return this;
        }

        public Builder setLuogoDiNascita(String luogoDiNascita) {
            this.luogoDiNascita = luogoDiNascita;
            return this;
        }

        public Builder setRepartiGestiti(EnumSet<Reparto> repartiGestiti) {
            this.repartiGestiti = repartiGestiti;
            return this;
        }

        public Builder setMatricola(int matricola) {
            this.matricola = matricola;
            return this;
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }
    }
}
