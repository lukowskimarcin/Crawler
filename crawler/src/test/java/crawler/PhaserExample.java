package crawler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Phaser;
import java.util.concurrent.atomic.AtomicLong;

/**
 * An example of using a Phaser to wait for the completion of recursive tasks.
 * @author Voxelot
 */
public class PhaserExample {
    /** Workstealing threadpool with reduced queue contention. */
    private static ForkJoinPool executors;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        executors = new ForkJoinPool();
        List<Long> sequence = new ArrayList<>();
         
            sequence.add(fib(5));
        
        System.out.println(sequence);
    }

    /**
     * Computes the nth Fibonacci number in the Fibonacci sequence.
     * @param n The index of the Fibonacci number to compute
     * @return The computed Fibonacci number
     */
    private static Long fib(int n) throws InterruptedException {
        AtomicLong result = new AtomicLong();
        //Flexible sychronization barrier
        Phaser phaser = new Phaser();
        //Base task
        Task initialTask = new Task(n, result, phaser);
        //Register fib(n) calling thread
        
        System.out.println( "fib register: " + phaser.register() );
        //Submit base task
        executors.submit(initialTask);
        //Make the calling thread arrive at the synchronization
        //barrier and wait for all future tasks to arrive.
        System.out.println( "fib arriveAndAwaitAdvance: " + phaser.arriveAndAwaitAdvance() );
        
        //Get the result of the parallel computation.
        return result.get();
    }

    private static class Task implements Runnable {
        /** The Fibonacci sequence index of this task. */
        private final int index;
        /** The shared result of the computation. */
        private final AtomicLong result;
        /** The synchronizer. */
        private final Phaser phaser;

        public Task(int n, AtomicLong result, Phaser phaser) {
            index = n;
            this.result = result;
            this.phaser = phaser;
            //Inform synchronizer of additional work to complete.
            System.out.println("register: " + phaser.register());
        }

        @Override
        public void run() {
            if (index == 1) {
                result.incrementAndGet();
            } else if (index > 1) {
                //recurrence relation: Fn = Fn-1 + Fn-2
            	 
            	
                Task task1 = new Task(index - 1, result, phaser);
                Task task2 = new Task(index - 2, result, phaser);
                executors.submit(task1);
                executors.submit(task2);
            }
            //Notify synchronizer of task completion.
            System.out.println("arrive: " + phaser.arrive());
        }
    }
}