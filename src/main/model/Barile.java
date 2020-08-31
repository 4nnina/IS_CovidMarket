package main.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Barile<T extends Serializable>
{
    public ArrayList<T> elementi = new ArrayList<>();

    public void save(String filename)
    {
        try(Serializer ser = new Serializer(filename)) {
            ser.serialize(elementi);
        }
        catch (Exception e)
        {
            System.out.println("Impossibile salvare barile su " + filename);
            e.printStackTrace();
        }
    }

    public void load(String filename)
    {
        try(Deserializer des = new Deserializer(filename)) {
            this.elementi = (ArrayList<T>) des.deserialize();
        }
        catch (Exception e)
        {
            System.out.println("Impossibile caricare barile da " + filename);
            e.printStackTrace();
        }
    }
}
