package main.model;

import javafx.scene.image.Image;

import java.io.Serializable;
import java.util.EnumSet;

public class Prodotto implements Serializable
{
    private Reparto reparto;
    public String nome, marca;
    private int quantitaPerConfezione;
    private Image image;
    private int quantitaDisponibile;
    private EnumSet<Attributo> attributi;
    public int prezzo;

    public Prodotto(Reparto reparto, String nome, String marca, int prezzo,
                    int quantitaPerConfezione, Image image, int quantitaDisponibile, EnumSet<Attributo> attributi)
    {
        this.reparto = reparto;
        this.nome = nome;
        this.marca = marca;
        this.quantitaPerConfezione = quantitaPerConfezione;
        this.image = image;
        this.quantitaDisponibile = quantitaDisponibile;
        this.attributi = attributi;
        this.prezzo = prezzo;
    }

    @Override
    public int hashCode() {
        return nome.hashCode() ^ marca.hashCode() ^ reparto.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj); //TODO
    }
}
