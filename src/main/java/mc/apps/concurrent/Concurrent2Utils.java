package mc.apps.concurrent;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Concurrent2Utils {

    public static void Demo() throws InterruptedException, ExecutionException {

        /**
         * Future
         */
        FutureTask<String> future = new FutureTask<>(() -> {

           System.out.println("future task with callable..running..");
           sleepAwhile();

           return getName();
       });

        //        Thread thread = new Thread(future);
        //        thread.start();

        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(future);

        while(!future.isDone()) {
            System.out.println("future task with callable..not done yet");
            Thread.sleep(1000);
        }

        System.out.println("future task with callable..get (Thread name): "+future.get());

        executor.shutdown();

        /**
         * ScheduledFuture + ScheduledExecutorService
         */

        Callable<String> task_callable = () -> getName();

        ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
        ScheduledFuture<String> scheduled_future = ses.schedule(task_callable, 5, TimeUnit.SECONDS);

        System.out.println();
        System.out.println("Scheduled Future Task with callable..scheduled to start in 5sec..");
        System.out.println("Scheduled Future Task task with callable..get (Thread name): "+scheduled_future.get());


        /**
         * ..Periodically
         */
        AtomicInteger count = new AtomicInteger();
        scheduled_future_period = ses.scheduleAtFixedRate(
                ()-> {
                    System.out.println(getName());
                    if(count.incrementAndGet() >5){
                       cancel();
                    }
                }, 5,1, TimeUnit.SECONDS);

        System.out.println();
        System.out.println("Scheduled Future Task Periodically with callable..scheduled to start in 5sec..");

        Thread.sleep(10000);
        System.out.println("ScheduledExecutorService shutdown!");
        ses.shutdown();

        /**
         * CompletableFuture
         */
        CompletableFuture<String> future_completable = new CompletableFuture<>();

        sleepAwhile();
        future_completable.complete(getName());

        System.out.println();
        System.out.println("completable future task..get (Thread name): "+future.get());
        System.out.println("demo done!");
    }

    static ScheduledFuture<?> scheduled_future_period;
    private static void cancel() {
        System.out.println("Scheduled Future Task Periodically..cancel");

        try {
            scheduled_future_period.cancel(true);
        }catch (Exception e){
            System.out.println("exception : "+e.getMessage());
        }

        System.out.println("Scheduled Future Task Periodically..canceled : "+scheduled_future_period.isCancelled());
    }

    private static String getName() {
        return Thread.currentThread().getName();
    }
    private static void sleepAwhile() throws InterruptedException {
        Thread.sleep(duration());
    }
    private static long duration(){
        Random random = new Random();
        return random.nextInt(500)+500;
    }
}
