package mc.apps.introspection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

import static mc.apps.tools.Utils.Display;

public class IntrospectionUtils {

//    public static void InvokeMethod(String methodName, String className){
//        try {
//            Class<?> methodClass = Class.forName(className);
//            Method m = methodClass.getDeclaredMethod(methodName.replace(" ",""),  null);
//            m.invoke(null);
//        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
//            // Todo : throw custom exception..
//            System.out.println(e);
//        }
//    }
    public static Optional<Object> InvokeMethod(String methodName, String className, Class<?>[] parameterTypes, Object[] arguments){
        try {
            Class<?> methodClass = Class.forName(className);
            Method m = methodClass.getDeclaredMethod(methodName.replace(" ",""), parameterTypes);

            return Optional.ofNullable(m.invoke(null, arguments));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | ClassNotFoundException e) {
            // Todo : throw custom exception..
            System.out.println(e);
        }
        return Optional.empty();
    }
    public static Optional<Class<?>> GetClass(String className){
        try {
            Class<?> _class = Class.forName(className);
            return Optional.ofNullable(_class);
        } catch (ClassNotFoundException e) {
            // Todo : throw custom exception..
            System.out.println(e);
        }
        return Optional.empty();
    }

    public static Optional<Object> CreateInstance(String className){
        try {
            Class<?> _class = Class.forName(className);
            Object _instance = _class.getDeclaredConstructor().newInstance();

            return Optional.ofNullable(_instance);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            // Todo : throw custom exception..
            System.out.println(e);
        }
        return Optional.empty();
    }

    public static void ClassInfos(String className){
        try {
            Class<?> _class = Class.forName(className);

            Display(_class.getCanonicalName());

            Field[] fields = _class.getFields();
            Field[] declaredFields = _class.getDeclaredFields();
            Method[] declaredMethods = _class.getDeclaredMethods();

            Constructor<?>[] declaredConstructors = _class.getDeclaredConstructors();
            Annotation[] declaredAnnotations = _class.getDeclaredAnnotations();

            System.out.println("Fields :");
            Arrays.stream(fields)
                    .map(f->f+" | "+f.getName()+" "+f.getType().getSimpleName())
                    .forEach(System.out::println);
            System.out.println();

            System.out.println("Declared Fields :");
            Arrays.stream(declaredFields)
                    .map(f->f+" | "+f.getName()+" "+f.getType().getSimpleName())
                    .forEach(System.out::println);
            System.out.println();

            System.out.println("Declared Constructor(s) :");
            Arrays.stream(declaredConstructors)
                    .forEach(System.out::println);
            System.out.println();

            System.out.println("Declared Annotations :");
            Arrays.stream(declaredAnnotations)
                    .forEach(System.out::println);
            System.out.println();

            System.out.println("Declared Methods :");
            Arrays.stream(declaredMethods)
                    .map(m->m+" | "+m.getName())
                    .forEach(System.out::println);
            System.out.println();

        } catch (ClassNotFoundException e) {
            // Todo : throw custom exception..
            System.out.println(e);
        }
    }

}
