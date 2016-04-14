package Requests.Filters;

import java.util.List;

/**
 * Created by yudzh_000 on 01.04.2016.
 */
class SpecialityFilterComponent extends FilterComponent {

    public SpecialityFilterComponent(List<String> params) {
        super(params);

        RequestBuilder.append("specialities =");
        SQLBuilder.append("SpecialityType.name IN (" + params.get(0));
        RequestBuilder.append(params.get(0));
        for (int i = 1; i < params.size(); i++) {
            String param = params.get(i);
            SQLBuilder.append(",");
            SQLBuilder.append(param);
            RequestBuilder.append(",");
            RequestBuilder.append(param);
        }
        SQLBuilder.append(")");
    }
}
