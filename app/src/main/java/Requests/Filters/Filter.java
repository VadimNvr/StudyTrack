package Requests.Filters;

import android.util.Log;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yudzh_000 on 01.04.2016.
 */
public class Filter {
    List<FilterComponent> filterComponents;

    public Filter() {
        filterComponents = new ArrayList<>();
    }

    public void addTownsFilter(List<String> townNames) {
        filterComponents.add(new TownFilterComponent(townNames));
    }

    public void addSpecialityFilter(List<String> specialityNames) {
        filterComponents.add(new SpecialityFilterComponent(specialityNames));
    }

    public void addPointsFilter(List<Integer> points) {
        filterComponents.add(new PointsFilterComponent(points));
    }

    public String getSQL() {
        if(filterComponents.isEmpty()) {
            throw new InvalidParameterException("You need to add filters before");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Select University.* from University\n" +
                "left join Town on University.TOWN_ID = Town.ID\n" +
                "left join Speciality on University.ID = Speciality.UNIVERSITY_ID \n" +
                "left join SpecialityType on SpecialityType.ID = Speciality.TYPE_ID\n"+
                "Where " + filterComponents.get(0).getSQL()
                );
        for (int i = 1; i < filterComponents.size(); i++) {
            FilterComponent filterComponent = filterComponents.get(i);
            stringBuilder.append(" and ");
            stringBuilder.append(filterComponent.getSQL());
        }
        Log.i("SQL", stringBuilder.toString());
        return stringBuilder.toString();
    }

    public String getRequest()  {
        if(filterComponents.isEmpty()) {
            throw new InvalidParameterException("You need to add filters before");
        }
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(filterComponents.get(0).getRequest());
            for (int i = 1; i < filterComponents.size(); i++) {
                FilterComponent filterComponent = filterComponents.get(i);
                stringBuilder.append("&");
                stringBuilder.append(filterComponent.getRequest());
            }
            return stringBuilder.toString();
        }
        catch (Exception e) {

        }
        return "";
    }
}
