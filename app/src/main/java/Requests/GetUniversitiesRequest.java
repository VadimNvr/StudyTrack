package Requests;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import Entities.Town;
import Entities.University;

/**
 * Created by yudzh_000 on 19.03.2016.
 */
public class GetUniversitiesRequest extends Request<University>{
    Town town;
    int offset;
    int count;
    AppCompatActivity activity;
    public GetUniversitiesRequest(AppCompatActivity activity, Town town, int offset, int count) {
        super(activity);
        this.activity = activity;
        this.town = town;
        this.offset = offset;
        this.count = count;
    }


    @Override
    protected List<University> doInBackground(Void... params) {
        List<University> result =  new ArrayList<>();
        if( town.getCount() - offset - count > 0) {
            result.addAll(this.localDb.loadUniversities(town, count, offset));
            if(result.isEmpty() || result.size() < count) {
                List<University> extra = this.db.loadUniversities(town, count - result.size(), offset + result.size(), activity);
                saveToLocal(extra);
                result.addAll(extra);
            }
        }
        return result;
    }

}
