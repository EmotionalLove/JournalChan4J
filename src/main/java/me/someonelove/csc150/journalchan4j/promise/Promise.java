package me.someonelove.csc150.journalchan4j.promise;

import java.net.URL;
import java.util.function.Consumer;

/**
 * This class handles anything intended to be a network operation that returns the response from said request
 * <p>
 * Overriding classes must implement {@link Promise#now()}, and must handle exceptions thrown in that method.
 *
 * @param <T> The type returned by the request.
 */
public abstract class Promise<T> {

    private URL url;

    protected Promise(URL url) {
        this.url = url;
    }

    /**
     * Performs {@link Promise#now()} on a new thread, and accept the consumer when the operation is complete.
     */
    public final void later(Consumer<T> p) {
        new Thread(() -> {
            T t = this.now();
            p.accept(t);
        }).start();
    }

    /**
     * Perform the operation on the current thread.
     */
    public abstract T now();

    public final URL getUrl() {
        return url;
    }
}
