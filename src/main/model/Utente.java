package main.model;

import java.io.Serializable;

public class Utente extends Persona implements Serializable
{
    private CartaFedelta cartaFedelta;
    private MetodoPagamento metodoPagamento;
}
