package me.someonelove.csc150.journalchan4j.promise;

import me.someonelove.csc150.journalchan4j.journal.Journal;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

class PublishPromiseTest {

    @Test
    void test() throws URISyntaxException, MalformedURLException {
        PublishPromise pu = new PublishPromise(new URI("http://localhost:3001").toURL(), new Journal("Booops", "Sex"));
        String id = pu.now();
        if (id == null) {
            throw new RuntimeException();
        }
        System.out.println(pu);
    }

}