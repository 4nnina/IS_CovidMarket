package main.storage;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class BinarySerializer implements ISerializer, AutoCloseable
{
    private FileOutputStream stream;
    private ObjectOutputStream objectStream;

    public BinarySerializer(String filename)
    {
        try
        {
            stream = new FileOutputStream(filename);
            objectStream = new ObjectOutputStream(stream);
        }
        catch (IOException e)
        {
            System.out.println("Errore lettura di " + filename);
            e.printStackTrace();
        }
    }

    @Override
    public void serialize(Object obj)
    {
        try
        {
            objectStream.writeObject(obj);
        }
        catch (IOException e)
        {
            System.out.println("Errore salvataggio oggetto");
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws Exception {
        objectStream.close();
    }
}
