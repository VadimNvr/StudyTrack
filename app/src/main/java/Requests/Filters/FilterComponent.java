package Requests.Filters;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yudzh_000 on 30.03.2016.
 */
abstract class FilterComponent {
    List<?> params = new ArrayList<>();
    protected StringBuilder SQLBuilder = new StringBuilder();
    protected StringBuilder RequestBuilder = new StringBuilder();

    public FilterComponent(List<?> params) {
        if(params.isEmpty()) {
            throw new InvalidParameterException("Size must be more than zero");
        }
        this.params = params;
    }

    public String getSQL(){
        return SQLBuilder.toString();
    }

    public String getRequest() {
        return RequestBuilder.toString();
    }

}
