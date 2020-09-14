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
        try(BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/citta.txt")))
        {
            String line = reader.readLine();
            while (line != null)
            {
                if (line.length() > 3)
                {
                    int separator = line.indexOf('\t');
                    int begin = firstValidChar(line);

                    String nome = (String) line.subSequence(begin, separator);
                    String CAP = (String) line.subSequence(separator + 1, line.lastIndexOf('\t'));

                    citta.add(nome);
                    cap.put(nome, CAP);
                }
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

    private int firstValidChar(String value) {
        int result = 0;
        while(!Character.isLetter(value.charAt(result)))
            result += 1;
        return result;
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
