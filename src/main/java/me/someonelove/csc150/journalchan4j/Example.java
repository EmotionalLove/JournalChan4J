package me.someonelove.csc150.journalchan4j;

import me.someonelove.csc150.journalchan4j.journal.Journal;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public class Example {

    public static void main(String[] args) {
        try {
            JournalChan api = new JournalChan("http://localhost:3001");
            // publishing a journal
            System.out.println("Publish");
            Journal journal = new Journal("Beatty", "I love the web degree, you should do it bitch");
            String id = api.publishNewJournal(journal).now(); // perform web operation on current thread
            System.out.println(id);

            Thread.sleep(500L);

            // getting a journal by ID
            api.getJournalById(id).later(j -> { // perform web operation on new thread
                System.out.println("Get by ID");
                System.out.println(j.toString());
            });

            // batch fetch journals
            api.getJournalArray(20).later(arr -> {
                System.out.println("Batch Fetch");
                for (Journal journal1 : arr) {
                    System.out.println(journal1);
                }
            });
            //Thread.sleep(5000L);
        } catch (URISyntaxException | MalformedURLException | InterruptedException e) {
            e.printStackTrace();
            System.err.println("Invalid URL, check formatting");
        }
    }
}
