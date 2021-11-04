package mc.apps.concurrent;

import java.util.ArrayList;
import java.util.List;

public class ConcurrentUtils {

    static List<Thread> activeThreads = new ArrayList<>();
    private static boolean stop=false;

    public static void CreateThreadsAndStart(int nb){
        Thread thread;
        for (int i = 0; i <nb ; i++) {
            thread = new Thread(() -> {
                while(!stop) {
                    System.out.println("I'm a thread, my name is : " + Thread.currentThread().getName() + ", i'm running..");
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
//                        Thread.currentThread().interrupt(); // restore interrupted status
                    }
                }
                System.out.println("I'm a thread, my name is : " + Thread.currentThread().getName() + ", interrupted!");
            });
            activeThreads.add(thread);
            thread.start();
        }
    }
    public static void StopThreads(){
//        activeThreads.forEach(t->t.interrupt());
        stop=true;
    }
}
