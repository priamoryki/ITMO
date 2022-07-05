package info.kgeorgiy.ja.lymar.crawler;

import info.kgeorgiy.java.advanced.crawler.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.*;

/**
 * @author Pavel Lymar
 */
public class WebCrawler implements Crawler {
    private final Downloader downloader;
    private final ExecutorService downloaders;
    private final ExecutorService extractors;
    private final int perHost;
    private final Set<String> downloaded;
    private final ConcurrentMap<String, IOException> errors;
    private final ConcurrentMap<String, Semaphore> hostsSemaphores;

    public WebCrawler(Downloader downloader, int downloaders, int extractors, int perHost) {
        this.downloader = downloader;
        this.downloaders = Executors.newFixedThreadPool(downloaders);
        this.extractors = Executors.newFixedThreadPool(extractors);
        this.perHost = perHost;
        // :NOTE: download может вызываться параллельно в нескольких потоках.
        this.downloaded = ConcurrentHashMap.newKeySet();
        this.errors = new ConcurrentHashMap<>();
        this.hostsSemaphores = new ConcurrentHashMap<>();
    }

    @Override
    public Result download(String url, int depth) {
        Set<String> urls = ConcurrentHashMap.newKeySet();
        urls.add(url);
        download(urls, depth, new Phaser(1));
        downloaded.removeAll(errors.keySet());
        return new Result(new ArrayList<>(downloaded), errors);
    }

    private void download(Set<String> urls, int depth, Phaser phaser) {
        if (depth == 0) {
            return;
        }
        Set<String> queue = ConcurrentHashMap.newKeySet();
        for (String url : urls) {
            if (downloaded.add(url)) {
                try {
                    String host = URLUtils.getHost(url);
                    hostsSemaphores.putIfAbsent(host, new Semaphore(perHost));
                    Semaphore hostSemaphore = hostsSemaphores.get(host);
                    hostSemaphore.acquireUninterruptibly();
                    phaser.register();
                    downloaders.submit(() -> {
                        try {
                            Document document = downloader.download(url);
                            phaser.register();
                            extractors.submit(() -> {
                                try {
                                    queue.addAll(document.extractLinks());
                                } catch (IOException e) {
                                    errors.put(url, e);
                                } finally {
                                    phaser.arriveAndDeregister();
                                }
                            });
                        } catch (IOException e) {
                            errors.put(url, e);
                        } finally {
                            phaser.arriveAndDeregister();
                            hostSemaphore.release();
                        }
                    });
                } catch (IOException e) {
                    errors.put(url, e);
                }
            }
        }
        phaser.arriveAndAwaitAdvance();
        download(queue, depth - 1, phaser);
    }

    @Override
    public void close() {
        close(downloaders);
        close(extractors);
    }

    private void close(ExecutorService service) {
        service.shutdown();
        try {
            if (!service.awaitTermination(1, TimeUnit.MINUTES)) {
                service.shutdownNow();
            }
        } catch (InterruptedException ignored) {
        }
    }

    // :NOTE: main
}
