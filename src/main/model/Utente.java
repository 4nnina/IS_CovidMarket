package main.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Utente extends Persona implements Serializable
{
    private CartaFedelta cartaFedelta;
    private MetodoPagamento metodoPagamento;

    // Items che non sono ancora stati confermati (prodotto, quantita)
    private Carrello carrelloCorrente = new Carrello();

    private Utente(Builder builder)
    {
        super(builder);
        this.cartaFedelta = builder.cartaFedelta;
        this.metodoPagamento = builder.metodoPagamento;
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

    public Carrello getCarrello() {
        return this.carrelloCorrente;
    }

    /**
     * Builder pattern per l'utente
     */
    public static class Builder extends Persona.Builder<Builder>
    {
        private CartaFedelta cartaFedelta;
        private MetodoPagamento metodoPagamento;

        @Override
        public Utente build() {
            return new Utente(this);
        }

        @Override
        protected Builder self() {
            return this;
        }

        public Builder setCartaFedelta(CartaFedelta cartaFedelta) {
            this.cartaFedelta = cartaFedelta;
            return this;
        }

        public Builder setMetodoPagamento(MetodoPagamento metodoPagamento) {
            this.metodoPagamento = metodoPagamento;
            return this;
        }
    }
}
