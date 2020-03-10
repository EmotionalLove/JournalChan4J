package me.someonelove.csc150.journalchan4j.promise;

import me.someonelove.csc150.journalchan4j.journal.Journal;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Requests a JSON-formatted {@link Journal} to be published via a POST request.
 */
public class PublishPromise extends Promise<String> {

    private Journal toPublish;

    public PublishPromise(URL url, Journal journal) {
        super(url);
        this.toPublish = journal;
    }

    @Override
    public String now() {
        if (!toPublish.validate()) {
            return null;
        }
        String json = toPublish.serialise().toString();
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(getUrl().toString() + "/publish").openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            OutputStreamWriter w = new OutputStreamWriter(connection.getOutputStream());
            w.write(json);
            w.flush();
            connection.connect();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String l;
            while ((l = reader.readLine()) != null) {
                builder.append(l);
            }
            JSONObject parent = new JSONObject(builder.toString());
            w.close();
            if(parent.getInt("status") == 200) {
                return parent.getString("id");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
