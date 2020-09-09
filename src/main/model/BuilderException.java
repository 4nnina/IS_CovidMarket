package main.model;

// Errore generato dai builder in caso di campi errati
public class BuilderException extends RuntimeException
{
    public BuilderException(String message) {
        super(message);
    }
}
