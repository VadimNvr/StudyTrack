package Requests.Filters;

import java.util.List;

class PointsFilterComponent extends FilterComponent {

    public PointsFilterComponent(List<Integer> params) {
        super(params);

        SQLBuilder.append("university.mean_points >= ");
        SQLBuilder.append(params.get(0).intValue());
        SQLBuilder.append(" and university.mean_points <= ");
        SQLBuilder.append(params.get(1).intValue());
        RequestBuilder.append("min_points=");
        RequestBuilder.append(params.get(0).intValue());
        RequestBuilder.append("&");
        RequestBuilder.append("max_points=");
        RequestBuilder.append(params.get(1).intValue());
        RequestBuilder.append("&flag=False");
    }
}
