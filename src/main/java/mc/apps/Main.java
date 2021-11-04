package mc.apps;

import mc.apps.jdk8.Jdk8Features;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static mc.apps.concurrent.ConcurrentUtils.CreateThreadsAndStart;
import static mc.apps.concurrent.ConcurrentUtils.StopThreads;
import static mc.apps.introspection.IntrospectionUtils.*;
import static mc.apps.tools.Utils.*;

public class Main {
    static List<String> options = Arrays.asList("Collections", "Concurrent", "Introspection", "JDK8 Features", "Regex");

    public static void main(String[] args) {
        Display("Java : Advanced Features");

        Menu(options);
        int choice;
        boolean ok;

        do {
           choice = ReadInt("your choice :");
           ok = (choice >0 && choice <= options.size());

           if(ok)
               callMethod(options.get(choice-1));
        }while(choice!=options.size()+1);

        Display("Bye!");
    }

    private static void callMethod(String title) {
        InvokeMethod(title, Main.class.getName(),null,null);
    }

    /**
     * Methodes
     */
    public static void CollectionsTests(){
        String title ="Collections";
        Display(title);


        PrintLoopThenReturn(String.format("************** %s **************", title));
    }
    public static void ConcurrentTests() throws InterruptedException {
        String title ="Concurrent";
        Display(title);

        CreateThreadsAndStart(3);
        Thread.sleep(5000);

        System.out.println();
        StopThreads();

        PrintLoopThenReturn(String.format("************** %s **************", title));
    }
    public static void IntrospectionTests(){
        String title = "Introspection";
        Display(title);

        Class<?>[] paramTypes = {int.class, int.class};
        Optional<Object> result = InvokeMethod("Other", Main.class.getName(), paramTypes , new Object[]{10, 20});
        System.out.println(result.isPresent()?"Method found - Result : "+result.get():"No Method found!");

        Optional<Class<?>> _class = GetClass("mc.apps.introspection.IntrospectionUtils");
        System.out.println(_class.isPresent()?"Class found : "+_class.get().getName():"No Class found!");

        ClassInfos("mc.apps.jdk8.User");

        Optional<Object> instance = CreateInstance("mc.apps.jdk8.User");
        System.out.println(instance.isPresent()?"Created Instance : "+instance.get()+" - "+instance.get().getClass().getSimpleName():"No Instance created!");

        PrintLoopThenReturn(String.format("************** %s **************", title));
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

        PrintLoopThenReturn(String.format("************** %s **************", title));
    }
    public static void RegexTests(){
        String title = "Regex";
        Display(title);

        PrintLoopThenReturn(String.format("************** %s **************", title));
    }

    // Tests
    public static String OtherTests(int a, int b) {
        System.out.println("hello from OtherTests!");
        return a+" "+b;
    }
}
