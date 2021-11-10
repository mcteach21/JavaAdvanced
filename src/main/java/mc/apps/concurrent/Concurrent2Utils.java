package mc.apps.concurrent;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class Concurrent2Utils {

    public static void FutureDemo() throws InterruptedException, ExecutionException {

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

        while (!future.isDone()) {
            System.out.println("future task with callable..not done yet");
            Thread.sleep(1000);
        }

        System.out.println("future task with callable..get (Thread name): " + future.get());

        executor.shutdown();

        /**
         * ScheduledFuture + ScheduledExecutorService
         */

        Callable<String> task_callable = () -> getName();

        ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
        ScheduledFuture<String> scheduled_future = ses.schedule(task_callable, 5, TimeUnit.SECONDS);

        System.out.println();
        System.out.println("Scheduled Future Task with callable..scheduled to start in 5sec..");
        System.out.println("Scheduled Future Task task with callable..get (Thread name): " + scheduled_future.get());


        /**
         * ..Periodically
         */
        AtomicInteger count = new AtomicInteger();
        scheduled_future_period = ses.scheduleAtFixedRate(
                () -> {
                    System.out.println(getName());
                    if (count.incrementAndGet() > 5) {
                        cancel();
                    }
                }, 5, 1, TimeUnit.SECONDS);

        System.out.println();
        System.out.println("Scheduled Future Task Periodically with callable..scheduled to start in 5sec..");

        Thread.sleep(10000);
        System.out.println("ScheduledExecutorService shutdown!");
        ses.shutdown();
    }
    public static void CompletableFutureDemo() throws InterruptedException, ExecutionException {
        /**
         * CompletableFuture
         * fork-join : default pool of thread used to execute tasks by Completable Futures
         */
        CompletableFuture<String> future_completable = new CompletableFuture<>();
        sleepAwhile();
        future_completable.complete(getName());
        System.out.println("completable future task..get (Thread name): "+future_completable.get());
       
        
        Runnable runnable = ()-> System.out.println("future_completable_runnable - Thread : "+Thread.currentThread().getName());
        Supplier<String> supplier = ()->Thread.currentThread().getName(); // similar to Callable but they do not throw checked exceptions

        CompletableFuture<Void> future_completable_runnable = CompletableFuture.runAsync(runnable);
        CompletableFuture<String> future_completable_supplier = CompletableFuture.supplyAsync(supplier);

        future_completable_runnable.get();
        System.out.println("future_completable_supplier - Thread : "+future_completable_supplier.get());


        Executor executor = Executors.newFixedThreadPool(5);
        CompletableFuture<String> future_completable_with_executor = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new IllegalStateException(e);
            }
            return getName();
        }, executor);

        System.out.println("future_completable_with_executor - Thread : "+future_completable_with_executor.get());


        /**
         * get() bloquante!
         * utilisation de : thenApply(), thenAccept(), thenRun()
         */

        CompletableFuture
                .supplyAsync(() -> getName())
                .thenApply(result -> "(Thead Name) ="+result.toUpperCase())  // traiter et transformer le résultat d'un CompletableFuture
                .thenAccept(result -> { // exécuter traitement sur résultat (ne renvoie rien)
                    System.out.println("CompletableFuture - thenAccept() => Result from future : " + result);
                }).thenRun(             // exécuter traitement de fin (ne renvoie rien) (pas accès au résultat)
                     ()-> System.out.println("CompletableFuture - thenRun() => done!")
                );

        System.out.println();
        /**
         * composing , combining futures
         *
         */

        //composing..
        System.out.println("Composing futures..");
        CompletableFuture<String> combosed_futures = getWebPageUrl(0).thenCompose(url->getWebPageContent(url));
        combosed_futures.thenAccept(System.out::println);


        // combining..
        CompletableFuture<String> future_1 = CompletableFuture
                                                .supplyAsync(() ->  new Object(){}.getClass().getEnclosingMethod().getName())
                                                .thenApply(result->"(Method Name) = "+result);

        CompletableFuture<String> future_2 = CompletableFuture
                                                .supplyAsync(() -> getName())
                                                .thenApply(result->"(Thead Name) = "+result);

        System.out.println("Combining futures..");

        CompletableFuture<String> combined_futures = future_1.thenCombine(future_2, (result1, result2) -> result1 + " => " + result2);
        combined_futures.thenAccept(System.out::println);

        CompletableFuture.runAsync(
                ()-> {
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) { }
                    System.out.println();
                    System.out.println("CompletableFuture Demo Done!");
                }
        ).get();
    }

    private static CompletableFuture<String> getWebPageUrl(int i) {
        return CompletableFuture.supplyAsync(()->"web-page-url-sample"+i);
    }
    private static CompletableFuture<String> getWebPageContent(String url) {
        return CompletableFuture.supplyAsync(()->"page url : ["+url+"] ==> page content :  web-page-content..");
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
