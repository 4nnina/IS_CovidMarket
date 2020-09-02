package main.storage;

import java.io.*;

public class BinaryDeserializer implements IDeserializer, AutoCloseable
{
    private FileInputStream stream;
    private ObjectInputStream objectStream;

    public BinaryDeserializer(String filename)
    {
        try
        {
            stream = new FileInputStream(filename);
            objectStream = new ObjectInputStream(stream);
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Impossibile trovare file " + filename);
            e.printStackTrace();
        }
        catch (IOException e)
        {
            System.out.println("Errore lettura file su" + filename);
            e.printStackTrace();
        }
    }

    @Override
    public Object deserialize()
    {
        try
        {
            Object result = objectStream.readObject();
            return result;
        }
        catch (IOException e)
        {
            System.out.println("Errore lettura oggetto");
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Oggetto corrotto");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void close() throws Exception {
        objectStream.close();
    }
}
