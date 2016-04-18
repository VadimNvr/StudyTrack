package Requests;

import android.support.v7.app.AppCompatActivity;

import java.util.List;

import Entities.SpecialityType;

/**
 * Created by yudzh_000 on 17.04.2016.
 */
public class GetSpecsWithSpecial extends Request<SpecialityType> {

    String characters;

    public GetSpecsWithSpecial(AppCompatActivity ctx, String characters) {
        super(ctx);
        if(characters.length() > 1) {
            this.characters = characters.substring(0, 1).toUpperCase() + characters.substring(1);
        } else {
            this.characters = characters.toUpperCase();
        }
    }

    @Override
    protected List<SpecialityType> doInBackground(Void... params) {
        List<SpecialityType> specialities = this.localDb.loadSpecialityWithSpecifickName(characters);
        return specialities;
    }
}
