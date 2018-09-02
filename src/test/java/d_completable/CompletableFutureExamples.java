package d_completable;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.Test;

public class CompletableFutureExamples {

    @Test
    public void creatingCompletableFuture() {

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> hello());
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> world(), Executors.newSingleThreadExecutor());
        CompletableFuture<String> future3 = CompletableFuture.completedFuture("!");

        CompletableFuture<Void> future4 = CompletableFuture.runAsync(() -> {
        });

        CompletableFuture<Void> future5 = CompletableFuture.runAsync(() -> {
            System.out.println("Hello Kitty");
        }, Executors.newFixedThreadPool(2));

    }

    @Test
    public void getCompletableFuture() {

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> hello());
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> world(), Executors.newSingleThreadExecutor());
        CompletableFuture<String> future3 = CompletableFuture.completedFuture("!");

        try {
            System.out.println(future1.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        try {
            System.out.println(future2.get(1, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }

        System.out.println(future3.getNow("."));

        System.out.println(future3.join());

        try {
            System.out.println(future3.join());
        } catch (CompletionException e) {
            e.printStackTrace();
        }

        assertEquals(RuntimeException.class, CompletionException.class.getSuperclass());

    }

    @Test
    public void thenApply() throws ExecutionException, InterruptedException {

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> hello())
                .thenApply(s -> s + " ")
                .thenApply(s -> s + world())
                .thenApply(s -> s + "!");

        System.out.println(future.get());

    }

    @Test
    public void thenApplyAsync() throws ExecutionException, InterruptedException {

        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> hello())
                .thenApplyAsync(s -> s + " ")
                .thenApplyAsync(s -> s + world())
                .thenApplyAsync(s -> s + "!");

        System.out.println(future.get());

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> hello())
                .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + " "))
                .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + world()))
                .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + "!"));

        System.out.println(future1.get());
    }

    @Test
    public void thenApplyAcceptRun() throws ExecutionException, InterruptedException {

        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> hello())
                .thenApply(s -> s + " World")
                .thenAccept(s -> System.out.print(s))
                .thenRun(() -> System.out.println("!"));

        System.out.println(future);
        System.out.println(future.get());

    }

    @Test
    public void allOfAnyOf() throws ExecutionException, InterruptedException {

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> hello());
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> world(), Executors.newSingleThreadExecutor());
        CompletableFuture<String> future3 = CompletableFuture.completedFuture("!");

        CompletableFuture<Object> futureAny123 = CompletableFuture.anyOf(future1, future2, future3);
        CompletableFuture<Void> futureAll123 = CompletableFuture.allOf(future1, future2, future3);

        futureAll123.thenRun(() -> System.out.println("ALL"));
        futureAny123.thenRun(() -> System.out.println("ANY"));

        System.out.println(futureAll123.get());
        System.out.println(futureAny123.get());
    }

    @Test
    public void either() throws ExecutionException, InterruptedException {

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(this::hello);
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(this::world);

        CompletableFuture<Void> eitherFuture = future1.acceptEither(future2, val -> System.out.println(val));
        System.out.println(eitherFuture.get());
    }

    @Test
    public void thenCombine() throws ExecutionException, InterruptedException {

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(this::hello);
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(this::world);

        CompletableFuture<String> futureCombined = future1.thenCombine(future2, (s1, s2) -> s1 + " " + s2 + "!");
        System.out.println(futureCombined.get());
    }

    @Test
    public void acceptBoth() throws ExecutionException, InterruptedException {

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(this::hello);
        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(this::world);

        CompletableFuture<Void> futureBoth = future1.thenAcceptBoth(future2, (s1, s2) -> System.out.println(s1 + " " + s2 + "!"));
        System.out.println(futureBoth.get());
    }

    @Test
    public void exceptionally() throws ExecutionException, InterruptedException {

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> unsafeHello())
                .exceptionally(throwable -> throwable.getMessage());

        System.out.println(future1.get());
    }

    @Test
    public void handle() throws ExecutionException, InterruptedException {

        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> unsafeHello())
                .handle((value, throwable) -> value == null ? "Exception: " + throwable.getMessage() : value);

        System.out.println(future1.get());
    }

    @Test
    public void stream() throws ExecutionException, InterruptedException {

        int sum = new Random().ints(1, 1000).limit(10)
                .mapToObj(i -> CompletableFuture.supplyAsync(() -> {
                    sleep(i);
                    return i;
                }))
                .mapToInt(CompletableFuture::join)
                .sum();

        System.out.println(sum);
    }

    @Test
    public void anotherStream() throws ExecutionException, InterruptedException {

        int sum = new Random().ints(1, 1000).limit(10).parallel()
                .mapToObj(i -> CompletableFuture.supplyAsync(() -> {
                    sleep(i);
                    return i;
                }))
                .mapToInt(CompletableFuture::join)
                .sum();

        System.out.println(sum);
    }

    @Test
    public void oneMoreStream() throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(10);
        int sum = new Random().ints(1, 1000).limit(10).parallel()
                .mapToObj(i -> CompletableFuture.supplyAsync(() -> {
                    sleep(i);
                    return i;
                }, executorService))
                .mapToInt(CompletableFuture::join)
                .sum();

        System.out.println(sum);
    }



    private String world() {
        sleep(1000);
        return "World";
    }

    private String hello() {
        sleep(500);
        return "Hello";
    }

    private String unsafeHello()
    {
        sleep(500);
        if (ThreadLocalRandom.current().nextBoolean()) throw new RuntimeException();
        return "Hello";
    }

    private void sleep(int timeout) {
        try {
            MILLISECONDS.sleep(timeout);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
