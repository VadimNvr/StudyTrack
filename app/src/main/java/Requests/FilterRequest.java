package Requests;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import Entities.University;
import Requests.Filters.Filter;

/**
 * Created by yudzh_000 on 30.03.2016.
 */
public class FilterRequest extends Request<University> {
    int offset;
    int count;
    Filter filter;
    AppCompatActivity activity;

    public FilterRequest(AppCompatActivity activity,int offset, int count, Filter filter) {
        super(activity);
        this.activity = activity;
        this.offset = offset;
        this.count = count;
        this.filter = filter;
    }

    @Override
    protected List<University> doInBackground(Void... params) {
        List<University> result =  new ArrayList<>();
        result.addAll(this.localDb.loadFilters(filter.getSQL(),count, offset));
        if(result.size()<count) {
            List<University> resultFromServer = new ArrayList<>();
            resultFromServer.addAll(this.db.loadFilters(filter.getRequest(), offset, count, activity));
            List<University> newUniversities = new ArrayList<>();
            for (University university : resultFromServer) {
                if (result.size()<count) {
                    if(!result.contains(university)) {
                        newUniversities.add(university);
                        result.add(university);
                    } else {
                        break;
                    }
                }
            }
            saveToLocal(newUniversities);
        }
        return result;
    }
}
