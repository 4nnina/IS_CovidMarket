package main.model;

import java.io.*;

// Permette di caricare da file degli oggetti
public class Serializer implements Closeable
{
    private FileOutputStream stream;
    private BufferedOutputStream bufferedStream;
    private ObjectOutputStream bufferedObjectStream;

    public Serializer(String file) throws IOException
    {
        stream = new FileOutputStream(file);
        bufferedStream = new BufferedOutputStream(stream);
        bufferedObjectStream = new ObjectOutputStream(bufferedStream);
    }

    public void serialize(Object object) throws IOException {
        bufferedObjectStream.writeObject(object);
    }

    @Override
    public void close() throws IOException
    {
        bufferedObjectStream.close();
        bufferedStream.close();
        stream.close();
    }
}
