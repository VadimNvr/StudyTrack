package Requests;

import android.support.v7.app.AppCompatActivity;

import java.util.List;

import Entities.University;

/**
 * Created by yudzh_000 on 19.03.2016.
 */
public class GetFavoriteRequest extends Request<University> {

    public GetFavoriteRequest(AppCompatActivity activity) {
        super(activity);
    }

    @Override
    protected List<University> doInBackground(Void... params) {
        List<University> universities = this.localDb.loadLiked();
        return universities;
    }
}
