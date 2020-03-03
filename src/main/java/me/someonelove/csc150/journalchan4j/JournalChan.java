package me.someonelove.csc150.journalchan4j;

import me.someonelove.csc150.journalchan4j.journal.Journal;
import me.someonelove.csc150.journalchan4j.promise.JournalListPromise;
import me.someonelove.csc150.journalchan4j.promise.JournalPromise;
import me.someonelove.csc150.journalchan4j.promise.Promise;
import me.someonelove.csc150.journalchan4j.promise.PublishPromise;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class JournalChan {

    /**
     * The root URL for all requests.
     */
    private URL baseUrl;

    public JournalChan(String baseUrl) throws URISyntaxException, MalformedURLException {
        this.baseUrl = new URI(baseUrl).toURL();
    }

    /**
     * Retrieve a Promise for publishing a new Journal
     * @param journal The journal to POST to the server.
     * @return The ID for the journal, or null if it couldn't be published.
     * @throws IllegalArgumentException If the journal is invalid.
     */
    public final Promise<String> publishNewJournal(Journal journal)  throws IllegalArgumentException {
        if (!journal.validate()) {
            throw new IllegalArgumentException("Journal body must be non-null");
        }
        return new PublishPromise(baseUrl, journal);
    }

    /**
     * Retrieve a Promise for getting a Journal by ID from the server.
     * @param journalId The ID of the journal
     * @return the Journal, if the server had it.
     * @throws IllegalArgumentException If the ID is null or empty.
     */
    public final Promise<Journal> getJournalById(String journalId)  throws IllegalArgumentException {
        if (journalId == null || journalId.isEmpty()) {
            throw new IllegalArgumentException("Journal id must be non-null and not empty");
        }
        return new JournalPromise(baseUrl, journalId);
    }

    /**
     * Retrieve a Promise for batch fetching Journals, in order of date published.
     * @param max The maximum amount of journals to retrieve (between 1 and 100)
     * @return An array of the requested journals
     * @throws IllegalArgumentException if the max value was invalid.
     */
    public final Promise<Journal[]> getJournalArray(int max)  throws IllegalArgumentException {
        if (max < 1 || max > 100) {
            throw new IllegalArgumentException("Max must be >= 1");
        }
        return new JournalListPromise(baseUrl, max);
    }

}
