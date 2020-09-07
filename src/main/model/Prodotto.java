package main.model;

import javafx.scene.image.Image;

import java.io.Serializable;
import java.util.EnumSet;

public class Prodotto implements Serializable
{
    private Reparto reparto;
    public String nome, marca;
    private int quantitaPerConfezione;
    private int quantitaDisponibile;
    private EnumSet<Attributo> attributi;
    public int prezzo;

    private transient Image image = null;
    private String imagePath;

    public Prodotto(Builder builder)
    {
        this.reparto = builder.reparto;
        this.nome = builder.nome;
        this.marca = builder.marca;
        this.quantitaDisponibile = builder.quantitaDisponibile;
        this.quantitaPerConfezione = builder.quantitaPerConfezione;
        this.attributi = builder.attributi.clone();
        this.prezzo = builder.prezzo;
        this.imagePath = builder.imagePath;
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
    public int getQuantitaDisponibile() {
        return quantitaDisponibile;
    }
    public EnumSet<Attributo> getAttributi() {
        return attributi;
    }
    public int getPrezzo() {
        return prezzo;
    }
    public String getImagePath() {
        return imagePath;
    }

    // Carica immagine se necessario
    public Image getImage()
    {
        if (image == null)
        {
            try
            {
                image = new Image(imagePath, true);
            }
            catch (Exception e)
            {
                System.out.println("Errore caricamento immagine da " + imagePath);
                e.printStackTrace();
            }
        }
        return image;
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
        private String imagePath = "";
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

        public Builder setImagePath(String imagePath) {
            this.imagePath = imagePath;
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
            return new Prodotto(this);
        }
    }
}
