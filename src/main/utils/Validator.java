package main.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator
{
    private static final Pattern patternEmail = Pattern.compile("^([\\w-]+(?:\\.[\\w-]+)*)@((?:[\\w-]+\\.)*\\w[\\w-]{0,66})\\.([a-z]{2,6}(?:\\.[a-z]{2})?)$");
    private static final Pattern patternAlphabetic = Pattern.compile("^[A-Za-z ]{3,30}$");
    private static final Pattern patternTelephone = Pattern.compile("\\+?\\d{1,4}?[-.\\s]?\\(?\\d{1,3}?\\)?[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,4}[-.\\s]?\\d{1,9}");
    private static final Pattern patternAddressFormat = Pattern.compile("([a-z ]{2,}\\s?)(\\d{0,3})(\\s?\\S{2,})?");

    /**
     * Controlla che sia una email
     */
    public static boolean isEmail(String email)
    {
        Matcher matcher = patternEmail.matcher(email);
        return matcher.matches();
    }

    /**
     * Controlla se è alfanumerico
     */
    public static boolean isAlphanumeric(String text)
    {
        Matcher matcher = patternAlphabetic.matcher(text);
        return matcher.matches();
    }

    /**
     * Controlla che sia un numero di telefono
     */
    public static boolean isTelephoneNumber(String number)
    {
        Matcher matcher = patternTelephone.matcher(number);
        return matcher.matches();
    }

    /**
     * Controlla che sia un indirizzo valido
     */
    public static boolean isAddressFormat(String address)
    {
        Matcher matcher = patternAddressFormat.matcher(address);
        return matcher.matches();
    }


}
