package Requests.Filters;

import java.util.List;

class PointsFilterComponent extends FilterComponent {

    public PointsFilterComponent(List<Integer> params, boolean flag) {
        super(params);

        SQLBuilder.append("(university.mean_points >= ");
        SQLBuilder.append(params.get(0).intValue());
        SQLBuilder.append(" and university.mean_points <= ");
        SQLBuilder.append(params.get(1).intValue());
        RequestBuilder.append("min_points=");
        RequestBuilder.append(params.get(0).intValue());
        RequestBuilder.append("&");
        RequestBuilder.append("max_points=");
        RequestBuilder.append(params.get(1).intValue());
        if(!flag) {
            SQLBuilder.append(" and university.mean_points is not NULL");
        }
        SQLBuilder.append(")");
        RequestBuilder.append("&flag=" + flag);
    }
}
