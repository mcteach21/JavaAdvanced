package mc.apps.regex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {

    public static void SearchPattern(){
        String sentence="Depuis que les applications Android ont fait leur apparition sur le Play Store, le langage de programmation Java a été au cœur du développement des applications Android." +
                "Cependant, au cours de la dernière décennie, plusieurs autres langages de programmation ont concurrencé Java, Kotlin étant le principal porte-drapeau. ";

        Pattern pattern = Pattern.compile("Java|Android|Kotlin");
        Matcher matcher = pattern.matcher(sentence);

        while(matcher.find())
            System.out.println(matcher.group()+" ==> ["+ matcher.start()+"-"+matcher.end()+"]");
    }

    public static void ValidateEmails(){
        List<String> emails = new ArrayList();

        emails.add("mc@domain.fr");
        emails.add("mc1@domain.com");
        emails.add("mc.120@domain.com2");

        emails.add("mc@domain");
        emails.add("mc#domain.com");
        emails.add("@domain.com");

        String email_regex = "^(.+)@(.+)[.][a-zA-Z0-9.-]{3,4}$";
        Pattern pattern = Pattern.compile(email_regex);

        emails.forEach(email->{
            Matcher matcher = pattern.matcher(email);
            System.out.println(email +" : "+ matcher.matches());
        });

    }
    public static void ValidateDates(){
        String date_regex = "^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$";
        List<String> dates= Arrays.asList("31/02/2021","xx/00/11111","26/08/1970","250/12/2");

        Pattern pattern = Pattern.compile(date_regex);
        dates.forEach(date->{
            Matcher matcher = pattern.matcher(date);
            System.out.println(date +" : "+ matcher.matches());
        });
    }

    public static void GeneratePassword(){

    }
}
