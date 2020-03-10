package me.someonelove.csc150.journalchan4j.promise;

import me.someonelove.csc150.journalchan4j.journal.Journal;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class JournalListPromise extends Promise<Journal[]> {

    private int amount;

    public JournalListPromise(URL url, int amount) {
        super(url);
        this.amount = amount;
    }

    @Override
    public Journal[] now() {
        try {
            URLConnection connection = new URL(getUrl().toString() + "/retrieve/" + amount).openConnection();
            connection.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String l;
            while ((l = reader.readLine()) != null) {
                builder.append(l);
            }

            JSONObject parent = new JSONObject(builder.toString());
            if (parent.getInt("status") != 200) {
                throw new IllegalStateException("Shit's gone to hell. Status " + parent.getInt("status"));
            }

            int amt = parent.getInt("length");
            JSONArray arr = parent.getJSONArray("elements");
            Journal[] journals = new Journal[amt];
            amount = amt;
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                journals[i] = new Journal(obj.getString("id"), obj.getString("author"), obj.getString("body"), obj.getLong("msTime"));
            }
            return journals;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
