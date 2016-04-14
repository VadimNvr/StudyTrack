package Requests;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import Entities.Region;

/**
 * Created by yudzh_000 on 19.03.2016.
 */
public class GetRegionsRequest extends Request<Region> {


    public GetRegionsRequest(AppCompatActivity activity) {
        super(activity);
    }

    public GetRegionsRequest(Context context) {
        super(context);
    }

    @Override
    protected List<Region> doInBackground(Void... params) {
        List<Region> regions = this.localDb.loadRegions();

        if(regions.isEmpty()) {
            regions = this.db.loadRegions();
            saveToLocal(regions);
        }
        return regions;
    }
}
