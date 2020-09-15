package main.utils;

import main.model.Citta;

import java.io.*;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CityHelper
{
    private ArrayList<String> citta = new ArrayList<>();
    private HashMap<String, String> cap = new HashMap<>();

    public CityHelper()
    {
        try(BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/citta_output.txt")))
        {
            String line = reader.readLine();
            while (line != null)
            {
                int separator = line.indexOf(':');
                String nome = (String) line.subSequence(0, separator);
                String CAP = (String) line.subSequence(separator + 1, line.length());

                citta.add(nome);
                cap.put(nome, CAP);

                line = reader.readLine();
            }
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public ArrayList<String> getCities() {
        return citta;
    }

    public String getCap(String city) {
        return cap.get(city);
    }

    private static CityHelper INSTANCE = null;
    public static CityHelper getInstance() {
        if (INSTANCE == null)
            INSTANCE = new CityHelper();
        return INSTANCE;
    }
}
