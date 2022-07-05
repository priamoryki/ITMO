package info.kgeorgiy.ja.lymar.concurrent;

import info.kgeorgiy.java.advanced.mapper.ParallelMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Pavel Lymar
 */
public class ThreadsController<T, U> {
    private final ParallelMapper mapper;
    private final int realThreadsNumber;
    private final List<Thread> listOfThreads;
    private final List<Stream<? extends T>> blocks;

    public ThreadsController(final int threads, final List<? extends T> values, ParallelMapper mapper) {
        if (values.isEmpty()) {
            throw new NoSuchElementException("Can't be applied to empty list");
        }
        realThreadsNumber = Math.min(threads, values.size());
        this.blocks = splitOnBlocks(values);
        this.mapper = mapper;
        listOfThreads = new ArrayList<>();
    }

    private List<Stream<? extends T>> splitOnBlocks(List<? extends T> values) {
        int subListLen = values.size() / realThreadsNumber;
        int rest = values.size() % realThreadsNumber;
        return IntStream.range(0, realThreadsNumber).mapToObj(
                i -> values.subList(
                        subListLen * i + Math.min(rest, i),
                        subListLen * (i + 1) + Math.min(rest, i + 1)
                ).stream()
        ).collect(Collectors.toList());
    }

    public U getResult(
            final Function<Stream<? extends T>, U> sequenceFunction,
            final Function<Stream<U>, U> resultingFunction) throws InterruptedException {
        final List<U> threadsResults;
        if (mapper == null) {
            threadsResults = new ArrayList<>(Collections.nCopies(realThreadsNumber, null));
            IntStream.range(0, realThreadsNumber).forEach(
                    i -> {
                        listOfThreads.add(
                                new Thread(() -> threadsResults.set(i, sequenceFunction.apply(blocks.get(i))))
                        );
                        listOfThreads.get(i).start();
                    }
            );
            listOfThreads.forEach(
                    thread -> {
                        try {
                            thread.join();
                        } catch (InterruptedException e) {
                            System.err.println("Thread has been interrupted: " + e.getMessage());
                        }
                    }
            );
        } else {
            threadsResults = mapper.map(sequenceFunction, blocks);
        }
        return resultingFunction.apply(threadsResults.stream());
    }
}
