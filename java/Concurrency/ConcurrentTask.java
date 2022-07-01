package Concurrency;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

public class ConcurrentTask {
    public static void main(String[] args){

        Runnable run = () -> System.out.println("Bir runnable program");
        Thread t= new Thread(run);
        t.start();

        ExecutorService executor= Executors.newFixedThreadPool(10); //10 Tane paralell'de Gişe açacak.
        List<Runnable> listofrunnable= Arrays.asList(
                () -> System.out.println("Runnable 1"),
                () -> System.out.println("Runnable 2"),
                () -> System.out.println("Runnable 3"),
                () -> System.out.println("Runnable 4"),
                () -> System.out.println("Runnable 5"),
                () -> System.out.println("Runnable 6"),
                () -> System.out.println("Runnable 7")
        );
        List<Callable<Integer>> listofcallable = Arrays.asList(
                () -> {int a=1; System.out.println("Callable 1"); return a;},
                () -> {int a=1; System.out.println("Callable 2"); return a;},
                () -> {int a=1; System.out.println("Callable 3"); return a;},
                () -> {int a=1; System.out.println("Callable 4"); return a;},
                () -> {int a=1; System.out.println("Callable 5"); return a;},
                () -> {int a=1; System.out.println("Callable 6"); return a;}
        );


        try {
            List<Future<Integer>> results = executor.invokeAll(listofcallable);
            results.forEach( r -> {
                try {
                    System.out.println( "Result:"+r.get());
                } catch (InterruptedException | ExecutionException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        IntStream.range(0,7).forEach(i -> executor.submit(listofrunnable.get(i)));

    }
}
