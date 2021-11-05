package mc.apps.concurrent;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static mc.apps.tools.Utils.Display;
import static mc.apps.tools.Utils.PrintLoopThenReturn;

public class ConcurrentUtils {

    List<Thread> activeThreads = new ArrayList<>();
    Map<String,Integer> stats  = new HashMap<>();

    class Resource{
        private int id;
        private boolean used;
        public boolean isUsed() {
            return used;
        }
        public void setUsed(boolean used) {
            this.used = used;
        }
    }
    class Log{
        private static int count_0=0;
        private static AtomicInteger count = new AtomicInteger(0);

        public static void increment(){
            count_0++;                  // opération NON atomique!
            count.incrementAndGet();    // opération atomique!
        }

        public static int getCount0() {
            return count_0;
        }
        public static int getCount() {
            return count.get();
        }
    }


    public ConcurrentUtils(int nb){
        Runnable runnable= ()->{
            System.out.println("Thread : " + getName() + ", running..");

            Long dur;
            while (!Thread.currentThread().isInterrupted()) {
                try {

                    Log.increment();    //compter nb exec.
                    askForResource();

                    dur = duration();
                    System.out.println("Thread : " + getName() + ", sleeping for "+dur+"ms..");
                    Thread.sleep(dur);
                    freeResource();

                    Thread.sleep(dur);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // restore interrupted status
                }
            }
            System.out.println("Thread : " + getName() + ", interrupted!");
        };
        Thread thread;
        for (int i = 0; i < nb ; i++) {
            thread =  new Thread(runnable);
            activeThreads.add(thread);
        }
    }

    public void Start(){
        activeThreads.forEach(t->t.start());
    }
    public void Join(){
        activeThreads.forEach(t-> {
            try {
                t.join();       // attendre que les threads se terminent
            } catch (InterruptedException e) {
                System.out.println(e.getLocalizedMessage());
            }
        });
    }
    public void Stop(){
        System.out.println("stop threads..");
        activeThreads.forEach(t->t.interrupt());
    }

    private String getName() {
        return Thread.currentThread().getName();
    }
    private void sleepAwhile() throws InterruptedException {
        Thread.sleep(duration());
    }
    private long duration(){
        Random random = new Random();
        return random.nextInt(500)+500;
    }

    /**
     * accès concurrent => ressources!
     */
    final Resource resource = new Resource();

    private Object lock = new Object();
    private synchronized void askForResource() throws InterruptedException {
        while(resource.isUsed()) { //&& !Thread.currentThread().isInterrupted()
            System.out.println("Thread : " + getName() + ", waiting for resource..");
            wait();
        }
        synchronized(lock) {
            resource.setUsed(true);

            String name=getName();
            System.out.println("==========> Thread : " + name + ", GET Resource!");
            if(!stats.containsKey(name))
                stats.put(getName(), 0);
            stats.replace(name, stats.get(name), stats.get(name)+1);
        }
    }
    private synchronized void freeResource() {
        resource.setUsed(false);
        System.out.println("<========== Thread : " + getName() + ", FREE Resource!");
        notifyAll();
    }

    public void Statistics(){
       Comparator<Integer> comparator_desc = (o1, o2) -> (o2<o1)?-1:(o2>o1)?1:0;

        // sort by key
        // TreeMap<String, Integer> sortedMap = new TreeMap<>(stats);
        // sortedMap.forEach((k,v)->System.out.println(k+":"+v));

        System.out.println("Total executions : "+Log.getCount()+" ["+Log.getCount0()+"]");
        stats.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(comparator_desc))
                .forEach(System.out::println);
    }
}
