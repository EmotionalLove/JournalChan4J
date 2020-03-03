package me.someonelove.csc150.journalchan4j.promise;

import java.net.URL;
import java.util.function.Consumer;

public abstract class Promise<T> {

    private URL url;

    protected Promise(URL url) {
        this.url = url;
    }

    /**
     * Perform now() on a new thread, and accept the consumer when the operation is complete.
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
