package main.model;

import javafx.util.Pair;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

// Permette di collezionare prodotti
public class Carrello implements Serializable
{
    public static class Coppia implements Serializable
    {
        public int quantita;
        public Prodotto prodotto;

        public Coppia(int quantita, Prodotto prodotto) {
            this.quantita = quantita;
            this.prodotto = prodotto;
        }
    };

    // Prodotti presenti al momento
    private List<Coppia> prodotti = new LinkedList<>();

    // Aggiunge un nuovo prodotto
    public void addProdotto(Prodotto prodotto, int quantita)
    {
        // Se già esistente aumenta la quantita
        Optional<Integer> index = containsKey(prodotto);
        if (index.isPresent())
        {
            Coppia bundle = prodotti.get(index.get());
            bundle.quantita += quantita;
        }
        else
            // Altrimenti lo aggiunge
            prodotti.add(new Coppia(quantita, prodotto));
    }

    // Rimuove prodotto
    public void removeProdotto(Prodotto prodotto)
    {
        Optional<Integer> index = containsKey(prodotto);
        if (index.isPresent()) {
            int i = index.get();
            prodotti.remove(i);
        }
    }

    public Optional<Integer> containsKey(Prodotto prodotto) {
        for(int i = 0; i < prodotti.size(); ++i)
            if (prodotti.get(i).prodotto.equals(prodotto))
                return Optional.of(i);
         return Optional.empty();
    }

    public int getPunti()
    {
        int totale = 0;
        for(Coppia coppia : prodotti)
            totale += coppia.prodotto.getPrezzo() * coppia.quantita;
        return totale;
    }

    // Ottiene lista di elementi presenti
    public List<Coppia> getProdotti() {
        return prodotti;
    }
}
