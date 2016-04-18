package Requests.Filters;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by yudzh_000 on 01.04.2016.
 */
class SpecialityFilterComponent extends FilterComponent {

    public SpecialityFilterComponent(List<String> params) {
        super(params);

        RequestBuilder.append("specialities=");
        SQLBuilder.append("SpecialityType.name IN ('" + params.get(0) + "'");
        try {
            RequestBuilder.append( URLEncoder.encode(params.get(0), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        for (int i = 1; i < params.size(); i++) {
            String param = params.get(i);
            SQLBuilder.append(",'");

            SQLBuilder.append( param + "'");

            RequestBuilder.append("@");
            try {
                RequestBuilder.append(URLEncoder.encode(param, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        SQLBuilder.append(")");
    }
}
