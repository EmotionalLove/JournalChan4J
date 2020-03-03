package me.someonelove.csc150.journalchan4j.promise;

import me.someonelove.csc150.journalchan4j.journal.Journal;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class JournalPromiseTest {

    @Test
    void test() throws IOException, URISyntaxException {
        Promise<Journal> promise = new JournalPromise(new URI("http://localhost:3001").toURL(), "9899aec69601-48f6-a16e-24ff6ea52afc");
        Lock lock = new ReentrantLock();
        lock.lock();
        Journal journal = promise.now();
        if (journal == null) {
            throw new RuntimeException();
        }
        System.out.println();
        System.out.println();
        System.out.println(journal.toString());
        System.out.println();
        System.out.println();
    }

}