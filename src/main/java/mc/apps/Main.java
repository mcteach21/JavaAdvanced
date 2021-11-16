package mc.apps;


import mc.apps.concurrent.Concurrent2Utils;
import mc.apps.concurrent.ConcurrentUtils;
import mc.apps.jaxb.JAXBUtils;
import mc.apps.jdk8.Jdk8Features;
import mc.apps.regex.RegexUtils;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import static mc.apps.introspection.IntrospectionUtils.*;
import static mc.apps.tools.Utils.*;

public class Main {
    static List<String> options = Arrays.asList("Collections", "Concurrent", "Introspection", "JDK8 Features","JAXB", "Regex", "Tests");
    static String title="Java : Advanced Features";

    public static void main(String[] args) {
        Display(title);
        int choice;
        boolean ok;

        do {
           PrintLoopThenReturn(String.format("************** %s **************", title));
           Menu(options);
           PrintLoopThenReturn(String.format("************** %s **************", title));

           choice = ReadInt("your choice : ");
           ok = (choice >0 && choice <= options.size());

           if(ok)
               callMethod(options.get(choice-1));
        }while(choice!=options.size()+1);

        Display("Bye! \uD83D\uDE22 \uD83D\uDD90");
        // http://dplatz.de/blog/2019/emojis-for-java-commandline.html
    }

    private static void callMethod(String title) {
        InvokeMethod(title+"Tests", Main.class.getName(),null,null);
    }

    /**
     * Methodes
     */
    public static void CollectionsTests(){
        String title ="Collections";
        Display(title);

    }
    public static void ConcurrentTests() throws InterruptedException {
        String title ="Concurrent";
        Display(title);

        ConcurrentUtils cu = new ConcurrentUtils(3);
        cu.Start();
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cu.Stop();
        cu.Join();

        Display("Statistiques (Thread:Resource Use)");
        cu.Statistics();

        Display("Future<>, ScheduledFuture<>, CompletableFuture<>");
        try {
            Concurrent2Utils.FutureDemo();
            Concurrent2Utils.CompletableFutureDemo();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }
    public static void IntrospectionTests(){
        String title = "Introspection";
        Display(title);

        Class<?>[] paramTypes = {int.class, int.class};
        Optional<Object> result = InvokeMethod("OtherTests", Main.class.getName(), paramTypes , new Object[]{10, 20});
        System.out.println(result.isPresent()?"Method found - Result : "+result.get():"No Method found!");

        Optional<Class<?>> _class = GetClass("mc.apps.introspection.IntrospectionUtils");
        System.out.println(_class.isPresent()?"Class found : "+_class.get().getName():"No Class found!");

        ClassInfos("mc.apps.modele.User");

        Optional<Object> instance = CreateInstance("mc.apps.modele.User");
        System.out.println(instance.isPresent()?"Created Instance : "+instance.get()+" - "+instance.get().getClass().getSimpleName():"No Instance created!");
    }
    public static void JDK8FeaturesTests(){
        String title ="JDK8 Features";
        Display(title);

        Jdk8Features.LambdaExamples();
        Jdk8Features.StreamExamples();

        Jdk8Features.CollectorsExamples();
        Jdk8Features.forEachExamples();

        Jdk8Features.MethodReferencesExamples();
        Jdk8Features.OptionalClassExamples();

        Jdk8Features.StringJoinerExamples();
        Jdk8Features.AtomicLongExamples();
    }

    public static void JAXBTests(){
        String title = "JAXB";
        Display(title);

        JAXBUtils.Demo();
    }
    public static void RegexTests(){
        String title = "Regex";
        Display(title);

        System.out.println("pattern search..");
        RegexUtils.SearchPattern(); //search pattern in sentence..

        System.out.println();
        System.out.println("email valide?..");
        RegexUtils.ValidateEmails();

        System.out.println();
        System.out.println("date valide?..");
        RegexUtils.ValidateDates();

    }
    public static void TestsTests(){
        String title = "Tests";
        Display(title);

//        Supplier<?> supplier = ()->new Random().nextInt(100);
//        Stream.generate(supplier)
//                .limit(5)
//                .forEach(System.out::println);

        try {
            String dir = Main.class.getResource("/").getFile();
            System.out.println(dir);
        }catch(Exception e){
            System.out.println("error : "+e);
        }


        PrintLoopThenReturn(String.format("************** %s **************", title));
    }

    // Tests (introspection)
    public static String OtherTests(int a, int b) {
        System.out.println("hello from OtherTests!");
        return a+" "+b;
    }
}
