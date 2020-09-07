package main.controller;

import java.util.Optional;

public interface IPopup<T>
{
    // Mostra il popup e attende un risultato
    Optional<T> show();
}
