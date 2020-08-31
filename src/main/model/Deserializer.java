package main.model;

import java.io.*;

public class Deserializer implements Closeable
{
    private FileInputStream stream;
    private BufferedInputStream bufferedStream;
    private ObjectInputStream bufferedObjectStream;

    public Deserializer(String file) throws IOException
    {
        stream = new FileInputStream(file);
        bufferedStream = new BufferedInputStream(stream);
        bufferedObjectStream = new ObjectInputStream(bufferedStream);
    }

    public Object deserialize() throws IOException, ClassNotFoundException {
        return bufferedObjectStream.readObject();
    }

    @Override
    public void close() throws IOException
    {
        bufferedObjectStream.close();
        bufferedStream.close();
        stream.close();
    }
}
