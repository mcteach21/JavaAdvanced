package mc.apps;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Tests {
//    List<Thread> activeThreads = new ArrayList<>();
//
//    public Tests() {
//        Runnable runnable= ()->{
//            System.out.println("Thread : " + getName() + ", running..");
//
//            Long dur;
//            while (!Thread.currentThread().isInterrupted()) {
//                try {
//                    askForResource();
//
//                    dur = duration();
//                    System.out.println("Thread : " + getName() + ", sleeping for "+dur+"ms..");
//                    Thread.sleep(dur);
//                    freeResource();
//
////                    System.out.println("Thread : " + getName() + ", sleeping (again) for "+dur+"ms..");
//                    Thread.sleep(dur);
//
//                } catch (InterruptedException e) {
//                    Thread.currentThread().interrupt(); // restore interrupted status
//                }
//            }
//            System.out.println("Thread : " + getName() + ", interrupted!");
//        };
//        Thread thread;
//        for (int i = 0; i <5 ; i++) {
//            thread =  new Thread(runnable);
//            activeThreads.add(thread);
//        }
//    }
//    public void Start(){
//        activeThreads.forEach(t->t.start());
//    }
//    public void WaitForThreads(){
////        System.out.println("wait for threads..");
//        activeThreads.forEach(t-> {
//            try {
//                t.join();       // attendre que les threads se terminent
//            } catch (InterruptedException e) {
//                System.out.println(e.getLocalizedMessage());
//            }
//        });
//    }
//    public void Stop(){
//        System.out.println("stop threads..");
//        activeThreads.forEach(t->t.interrupt());
//    }
//
//    private Object lock = new Object();
//    private boolean resource=true;
//
//    private synchronized void askForResource() throws InterruptedException {
//
//        while(!resource) {
//            System.out.println("Thread : " + getName() + ", waiting for resource..");
//            wait();
//        }
//
//        synchronized(lock) {
//            resource = false;
//            System.out.println("==========> Thread : " + getName() + ", GET Resource!");
//        }
//    }
//    private synchronized void freeResource() {
//        resource=true;
//        System.out.println("<========== Thread : " + getName() + ", FREE Resource!");
//        notifyAll();
//    }
//
//    private String getName() {
//        return Thread.currentThread().getName();
//    }
//    private long duration(){
//        Random random = new Random();
//        return random.nextInt(500)+500;
//    }
//    private void sleepAwhile() throws InterruptedException {
//        Thread.sleep(duration());
//    }

}
