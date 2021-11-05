package mc.apps.concurrent;

import java.util.Random;
import java.util.concurrent.*;

public class Concurrent2Utils {

    public static void Demo() throws InterruptedException, ExecutionException {

        FutureTask<String> future = new FutureTask<>(() -> {

           System.out.println("future task with callable..running..");
           sleepAwhile();

           return getName();
       });

//        Thread thread = new Thread(future);
//        thread.start();

//        ExecutorService executor = Executors.newFixedThreadPool(2);
//        executor.submit(future);

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
        executor.schedule(future,5,TimeUnit.SECONDS);
        System.out.println("future task with callable..scheduled to start in 5sec..");

        while(!future.isDone()) {
            System.out.println("future task with callable..not done yet");
            Thread.sleep(1000);
        }

        System.out.println("future task with callable..get (Thread name): "+future.get());

        executor.shutdown();

        CompletableFuture<String> future_completable = new CompletableFuture<>();

        sleepAwhile();
        future_completable.complete(getName());

        System.out.println();
        System.out.println("completable future task..get (Thread name): "+future.get());
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
