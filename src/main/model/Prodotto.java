package main.model;

import javafx.scene.image.Image;

import java.io.Serializable;
import java.util.EnumSet;

public class Prodotto implements Serializable
{
    private Reparto reparto;
    private String nome, marca;
    private int quantitaPerConfezione;
    private Image image;
    private int quantitaDisponibile;

    private EnumSet<Attributo> attributi;

    @Override
    public int hashCode() {
        return nome.hashCode() ^ marca.hashCode() ^ reparto.hashCode();
    }
}
