package Requests;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import Entities.Region;
import Entities.Town;

public class GetTownsRequest extends Request<Town> {
    Region region;


    public GetTownsRequest(AppCompatActivity activity, Region region) {
        super(activity);
        this.region = region;
    }

    public GetTownsRequest(Context context, Region region) {
        super(context);
        this.region = region;
    }

    @Override
    protected List<Town> doInBackground(Void... params) {

        List<Town> towns = this.localDb.loadTowns(region);

        if(towns.isEmpty()) {
            towns = this.db.loadTowns(region);
            saveToLocal(towns);
        }
        return towns;
    }


}