package Services.DataBaseManager;

import android.support.v7.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Entities.Region;
import Entities.Speciality;
import Entities.SpecialityType;
import Entities.Town;
import Entities.University;

public class DataBaseDriver {

    public List<University> loadUniversities(Town town, int count, int offset, AppCompatActivity activity) {
        ArrayList<University> universities = new ArrayList<>();
        String url = "http://finduniv.appspot.com/getUniversities";
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("town", town.getName());
        parameters.put("count", Integer.toString(count));
        parameters.put("offset", Integer.toString(offset));
        try {
            JSONArray objects = (JSONArray) JSONParser.readJsonFromUrl(url,parameters);
            for (int i = 0; i < objects.length() ; i++) {
                universities.add(University.initFromJSON((JSONObject) objects.get(i), town, activity));
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return universities;
    }

    public List<Speciality> loadSpecialies(University university, int count, int offset) {
        ArrayList<Speciality> specialities = new ArrayList<>();
        String url = "http://finduniv.appspot.com/getUniversitySpecialities";
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("count", Integer.toString(count));
        parameters.put("offset", Integer.toString(offset));
        parameters.put("university", university.getName());
        try {
            JSONArray objects = (JSONArray) JSONParser.readJsonFromUrl(url,parameters);
            for (int i = 0; i < objects.length() ; i++) {
                specialities.add(Speciality.initFromJson((JSONObject) objects.get(i), university));
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return specialities;
    }

    public List<SpecialityType> loadSpecialityTypes(int offset,int count) {
        ArrayList<SpecialityType> specialityTypes = new ArrayList<>();
        String url = "http://finduniv.appspot.com/getSpecialities";
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("count", Integer.toString(count));
        parameters.put("offset", Integer.toString(offset));
        try {
            JSONArray objects = (JSONArray) JSONParser.readJsonFromUrl(url,parameters);
            for (int i = 0; i < objects.length() ; i++) {
                specialityTypes.add(SpecialityType.initFromJSON((JSONObject) objects.get(i)));
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return specialityTypes;
    }

    public List<Town> loadTowns(Region region) {
        String url = "http://finduniv.appspot.com/getTowns";
        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("region", region.getName());
        List<Town> result = new ArrayList<>();
        try {
            JSONArray objects = (JSONArray) JSONParser.readJsonFromUrl(url,parameters);
            for (int i = 0; i < objects.length() ; i++) {
                result.add(Town.initFromJSON((JSONObject) objects.get(i), region));
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Region> loadRegions() {
        String url = "http://finduniv.appspot.com/getRegions";
        HashMap<String, String> parameters = new HashMap<>();
        List<Region> result = new ArrayList<>();
        try {
            JSONArray objects = (JSONArray) JSONParser.readJsonFromUrl(url, parameters);
            for (int i = 0; i < objects.length() ; i++) {
                result.add(Region.initFromJSON((JSONObject) objects.get(i)));
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<University> loadFilters(String filter,int offset,int count, AppCompatActivity activity) {
        String url = "http://finduniv.appspot.com/filter?" + filter + "&count=" + count + "&offset=" + offset;
        HashMap<String, String> parameters = new HashMap<>();

        List<University> result = new ArrayList<>();
        try {
            JSONArray objects = (JSONArray) JSONParser.readJsonFromUrl(url, parameters);
            for (int i = 0; i < objects.length() ; i++) {
                JSONObject json = (JSONObject) objects.get(i);
                result.add(University.initFromJSON(json, new Town(json.getString("town")), activity));
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}
