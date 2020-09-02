package main.storage;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class BinarySerializer implements ISerializer
{
    private FileOutputStream stream;
    private BufferedOutputStream bufferedStream;
    private ObjectOutputStream bufferedObjectStream;

    public BinarySerializer(String filename) {
        try
        {
            stream = new FileOutputStream(filename);
            bufferedStream = new BufferedOutputStream(stream);
            bufferedObjectStream = new ObjectOutputStream(bufferedStream);
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
            bufferedObjectStream.writeObject(obj);
        }
        catch (IOException e)
        {
            System.out.println("Errore salvataggio oggetto");
            e.printStackTrace();
        }
    }
}
