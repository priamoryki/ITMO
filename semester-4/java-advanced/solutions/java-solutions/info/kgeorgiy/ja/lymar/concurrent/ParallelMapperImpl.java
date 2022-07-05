package info.kgeorgiy.ja.lymar.concurrent;

import info.kgeorgiy.java.advanced.mapper.ParallelMapper;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Pavel Lymar
 */
public class ParallelMapperImpl implements ParallelMapper {
    private final List<Thread> listOfThreads;
    private final Queue<TaskWrapper> tasks;

    public ParallelMapperImpl(int threads) {
        tasks = new ArrayDeque<>();
        listOfThreads = Stream.generate(
                () -> new Thread(
                        () -> {
                            try {
                                while (!Thread.interrupted()) {
                                    TaskWrapper task;
                                    synchronized (tasks) {
                                        while (tasks.isEmpty()) {
                                            tasks.wait();
                                        }
                                        task = tasks.poll();
                                        tasks.notify();
                                    }
                                    task.run();
                                    synchronized (task.getCounter()) {
                                        task.getCounter().increase();
                                        if (task.getCounter().isCompleted()) {
                                            task.getCounter().notify();
                                        }
                                    }
                                }
                            } catch (InterruptedException ignored) {
                            } finally {
                                Thread.currentThread().interrupt();
                            }
                        }
                )
        ).limit(threads).collect(Collectors.toList());
        listOfThreads.forEach(Thread::start);
    }

    @Override
    public <T, R> List<R> map(Function<? super T, ? extends R> f, List<? extends T> args) throws InterruptedException {
        final CounterWrapper counter = new CounterWrapper(args.size());
        final List<R> result = new ArrayList<>(Collections.nCopies(args.size(), null));
        IntStream.range(0, args.size()).forEach(
                i -> {
                    synchronized (tasks) {
                        tasks.add(new TaskWrapper(() -> result.set(i, f.apply(args.get(i))), counter));
                        tasks.notify();
                    }
                }
        );
        synchronized (counter) {
            while (!counter.isCompleted()) {
                counter.wait();
            }
        }
        return result;
    }

    @Override
    public void close() {
        // :NOTE: join
        listOfThreads.forEach(Thread::interrupt);
        for (Thread thread : listOfThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.err.println("Thread has been interrupted: " + e.getMessage());
            }
        }
    }
}
