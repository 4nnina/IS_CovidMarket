package main.model;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

// Permette di collezionare prodotti
public class Carrello
{
    // Prodotti presenti al momento
    private HashMap<Prodotto, Pair<Prodotto, Integer>> prodotti = new HashMap<>();

    // Aggiunge un nuovo prodotto
    public void addProdotto(Prodotto prodotto, int quantita)
    {
        // Se già esistente aumenta la quantita
        if (prodotti.containsKey(prodotto)) {
            Integer oldQuantity = prodotti.get(prodotto).getValue();
            prodotti.replace(prodotto, new Pair<>(prodotto, oldQuantity + quantita));
        }
        else
            // Altrimenti lo aggiunge
            prodotti.put(prodotto, new Pair<>(prodotto, quantita));
    }

    // Rimuove prodotto
    public void removeProdotto(Prodotto prodotto)
    {
        if (prodotti.containsKey(prodotto))
        {
            Integer quantity = prodotti.get(prodotto).getValue();
            quantity -= 1;

            if (quantity == 0)
                prodotti.remove(prodotto);
        }
    }

    // Ottiene lista di elementi presenti
    public Collection<Pair<Prodotto, Integer>> getProdotti() {
        return prodotti.values();
    }
}
