package main.storage;

public interface IDatabase
{
    void save(ISerializer ser);
    IDatabase load(IDeserializer ser);
}
