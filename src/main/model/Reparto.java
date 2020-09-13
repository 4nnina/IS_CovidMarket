package main.model;

public enum Reparto
{
    Tutto, Bevande, Carne, Pesce, Frutta, Verdura, Alimentari, Surgelati, Cereali, Biscotti, Dolci, Pasta;

    public String identificativo() {
        switch (this)
        {
            case Tutto:
                return "---";
            default:
                return this.name();
        }
    }
}
