package main.model;

import java.io.Serializable;
import java.util.Date;
import java.util.EnumSet;

public class Responsabile extends Persona implements Serializable
{
    private Date dataDiNascita;
    private String luogoDiNascita;
    private EnumSet<Reparto> repartiGestiti;
    private int matricola;
    private String username;
}
