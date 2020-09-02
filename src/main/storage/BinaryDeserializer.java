package main.storage;

import java.io.*;

public class BinaryDeserializer implements IDeserializer
{
    private FileInputStream stream;
    private BufferedInputStream bufferedStream;
    private ObjectInputStream bufferedObjectStream;

    public BinaryDeserializer(String filename)
    {
        try
        {
            stream = new FileInputStream(filename);
            bufferedStream = new BufferedInputStream(stream);
            bufferedObjectStream = new ObjectInputStream(bufferedStream);
        }
        catch (FileNotFoundException e)
        {
            System.out.println("Impossibile trovare file " + filename);
            e.printStackTrace();
        }
        catch (IOException e)
        {
            System.out.println("Errore scrittura file su" + filename);
            e.printStackTrace();
        }
    }

    @Override
    public Object deserialize()
    {
        try
        {
            Object result = bufferedObjectStream.readObject();
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
}
