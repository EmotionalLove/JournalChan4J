package me.someonelove.csc150.journalchan4j.promise;

import me.someonelove.csc150.journalchan4j.journal.Journal;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class JournalPromise extends Promise<Journal> {

    private String id;

    public JournalPromise(URL url, String id) {
        super(url);
        this.id = id;
    }

//    @Override
//    public void later(Consumer<Journal> p) {
//        new Thread(() -> {
//            Journal journal = this.now();
//            if (journal == null) {
//                return;
//            }
//            p.accept(journal);
//        }).start();
//    }

    @Override
    public Journal now() {
        try {
            URLConnection connection = new URL(getUrl().toString() + "/" + id).openConnection();
            connection.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String l;
            while ((l = reader.readLine()) != null) {
                builder.append(l);
            }
            JSONObject parent = new JSONObject(builder.toString());
            JSONObject obj = parent.getJSONObject("entry");
            if (parent.getInt("status") != 200) {
                throw new IllegalStateException("Shit's gone to hell. Status " + parent.getInt("status"));
            }
            return new Journal(obj.getString("id"), obj.getString("author"), obj.getString("body"), obj.getLong("msTime"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
