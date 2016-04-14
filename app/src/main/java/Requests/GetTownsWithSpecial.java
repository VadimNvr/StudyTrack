package Requests;

import android.support.v7.app.AppCompatActivity;

import java.util.List;

import Entities.Town;

/**
 * Created by yudzh_000 on 06.04.2016.
 */
public class GetTownsWithSpecial extends Request<Town> {

    String characters;

    public GetTownsWithSpecial(AppCompatActivity ctx, String characters) {
        super(ctx);
        if(characters.length() > 1) {
            this.characters = characters.substring(0, 1).toUpperCase() + characters.substring(1);
        } else {
            this.characters = characters.toUpperCase();
        }
    }

    @Override
    protected List<Town> doInBackground(Void... params) {
        List<Town> towns = this.localDb.loadTownsWithSpecifickName(characters);
        return towns;
    }
}
