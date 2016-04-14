package Requests.Filters;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by yudzh_000 on 01.04.2016.
 */
class TownFilterComponent extends FilterComponent {

    public TownFilterComponent(List<String> params) {
        super(params);
        SQLBuilder.append("Town.name IN ('" + params.get(0)+"'");
        try {
            RequestBuilder.append("towns=" + URLEncoder.encode(params.get(0), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        for (int i = 1; i < params.size(); i++) {
            String param = params.get(i);
            SQLBuilder.append(",'");
            try {
                SQLBuilder.append( URLEncoder.encode(param,"UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            SQLBuilder.append("'");
            RequestBuilder.append(",");
            RequestBuilder.append(param);
        }
        SQLBuilder.append(")");
    }
}
