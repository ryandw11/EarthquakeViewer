package me.ryandw11.earthquake;

import org.json.JSONArray;
import org.json.JSONObject;
import org.restlet.data.MediaType;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.resource.ClientResource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RESTHandler {

//    private final String EARTHQUAKE_API = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/1.0_hour.geojson";
    private final String EARTHQUAKE_API = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/4.5_day.geojson";

    private final List<Earthquake> currentQuakes = new ArrayList<>();

    public List<Earthquake> updateList() {
        List<Earthquake> quakesToAdd = new ArrayList<>();
        try {
            ClientResource resource = new ClientResource(EARTHQUAKE_API);
            JsonRepresentation response = new JsonRepresentation(resource.get(MediaType.APPLICATION_JSON));
            JSONArray quakes = response.getJsonObject().getJSONArray("features");

            for (int i = 0; i < quakes.length(); i++) {
                JSONObject quakeObject = quakes.getJSONObject(i);
                Earthquake earthquake = new Earthquake(quakeObject.getJSONObject("properties"),
                        quakeObject.getJSONObject("geometry"));
                if(currentQuakes.contains(earthquake)) continue;

                currentQuakes.add(earthquake);
                quakesToAdd.add(earthquake);
            }
        } catch (IOException ex) {

        }

        return quakesToAdd;
    }

    public List<Earthquake> getCurrentQuakes(){
        return currentQuakes;
    }

    public String getEarthquakeApi(){
        return EARTHQUAKE_API;
    }

}
