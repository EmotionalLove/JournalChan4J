package me.someonelove.csc150.journalchan4j.journal;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class Journal implements Serializable {

    private String id;
    private long timePosted;
    private String body;
    private String author;

    private transient boolean needsRefresh;

    public Journal(String id, String author, String body, long timePosted) {
        this.id = id;
        this.timePosted = timePosted;
        this.body = body;
        this.author = author;
    }

    public Journal(String author, String body) {
        this.id = UUID.randomUUID().toString();
        this.body = body;
        this.author = author;
    }

    public String getId() {
        return id;
    }

    public long getDateTimePosted() {
        return timePosted;
    }

    public Date getDatePosted() {
        return new Date(timePosted);
    }

    public String getBody() {
        return body;
    }

    public String getAuthor() {
        return author;
    }

    public boolean doesNeedRefresh() {
        return needsRefresh;
    }

    public final boolean validate() {
        return this.body != null && !this.body.isEmpty();
    }

    public JSONObject serialise() {
        JSONObject obj = new JSONObject();
        if (author != null) obj.put("Author", this.author);
        obj.put("Body", this.body);
        this.needsRefresh = true;
        return obj;
    }

    @Override
    public String toString() {
        return "Journal{" +
                "id='" + id + '\'' +
                ", timePosted=" + timePosted +
                ", body='" + body + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
