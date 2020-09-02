package main.storage;

public interface IDatabase
{
    void save(ISerializer ser);
    void load(IDeserializer ser);
}
