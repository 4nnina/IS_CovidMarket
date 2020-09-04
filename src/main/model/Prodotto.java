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

    private Prodotto(Reparto reparto, String nome, String marca, int prezzo,
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

    public Reparto getReparto() {
        return reparto;
    }
    public String getNome() {
        return nome;
    }
    public String getMarca() {
        return marca;
    }
    public int getQuantitaPerConfezione() {
        return quantitaPerConfezione;
    }
    public Image getImage() {
        return image;
    }
    public int getQuantitaDisponibile() {
        return quantitaDisponibile;
    }
    public EnumSet<Attributo> getAttributi() {
        return attributi;
    }
    public int getPrezzo() {
        return prezzo;
    }

    /**
     * Controlla se gli attributi di questo prodotto sono un sottoinsieme
     */
    public boolean checkAttributi(EnumSet<Attributo> attributi) {
        for (Attributo attrib : attributi) {
            if (!this.attributi.contains(attrib))
                return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return nome.hashCode() ^ marca.hashCode() ^ reparto.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj); //TODO
    }

    /**
     * Esplicita la creazione di un prodotto
     */
    public static class Builder
    {
        // Valori di default
        private Reparto reparto = Reparto.Tutto;
        private String nome = "NULL";
        private String marca = "NULL";
        private int quantitaPerConfezione = 0;
        private Image image = null;
        private int quantitaDisponibile = 0;
        private EnumSet<Attributo> attributi = EnumSet.noneOf(Attributo.class);
        private int prezzo = 0;

        public Builder setReparto(Reparto reparto) {
            this.reparto = reparto;
            return this;
        }

        public Builder setNome(String nome) {
            this.nome = nome;
            return this;
        }

        public Builder setMarca(String marca) {
            this.marca = marca;
            return this;
        }

        public Builder setQuantitaPerConfezione(int quantitaPerConfezione) {
            this.quantitaPerConfezione = quantitaPerConfezione;
            return this;
        }

        public Builder setImage(Image image) {
            this.image = image;
            return this;
        }

        public Builder setQuantitaDisponibile(int quantitaDisponibile) {
            this.quantitaDisponibile = quantitaDisponibile;
            return this;
        }

        public Builder setAttributi(EnumSet<Attributo> attributi) {
            this.attributi = attributi;
            return this;
        }

        public Builder addAttributo(Attributo attrib) {
            this.attributi.add(attrib);
            return this;
        }

        public Builder setPrezzo(int prezzo) {
            this.prezzo = prezzo;
            return this;
        }

        public Prodotto build() {
            return new Prodotto(
                    reparto,
                    nome,
                    marca,
                    prezzo,
                    quantitaPerConfezione,
                    image,
                    quantitaDisponibile,
                    attributi
            );
        }
    }
}
