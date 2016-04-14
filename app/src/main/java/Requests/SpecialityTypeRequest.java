package Requests;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import Entities.SpecialityType;

/**
 * Created by yudzh_000 on 03.04.2016.
 */
public class SpecialityTypeRequest extends Request {
    int offset;
    int count;

    public SpecialityTypeRequest(AppCompatActivity activity, int offset, int count) {
        super(activity);
        this.offset = 0;
        this.count = Integer.MAX_VALUE;
    }

    public SpecialityTypeRequest(Context context) {
        super(context);
        this.offset = 0;
        this.count = Integer.MAX_VALUE;

    }

    @Override
    protected List<SpecialityType> doInBackground(Object[] params) {
        List<SpecialityType> result = new ArrayList<>();
        result.addAll(this.localDb.loadSpecialityTypes(count, offset));

        if (result.isEmpty() || result.size() < count) {
            List<SpecialityType> extra = this.db.loadSpecialityTypes(offset, count);
            saveToLocal(extra);
            result.addAll(extra);
        }

        return result;
    }
}
