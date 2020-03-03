package me.someonelove.csc150.journalchan4j.promise;

import me.someonelove.csc150.journalchan4j.journal.Journal;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

class JournalListPromiseTest {

    @Test
    void test() throws URISyntaxException, MalformedURLException {
        Promise<Journal[]> promise = new JournalListPromise(new URI("http://localhost:3001").toURL(), 5);
        System.out.println(Arrays.toString(promise.now()));
    }

}