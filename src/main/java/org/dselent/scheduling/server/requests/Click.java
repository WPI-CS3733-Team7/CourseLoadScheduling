package org.dselent.scheduling.server.requests;

import org.dselent.scheduling.server.miscellaneous.RequestParameterConverter;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

public class Click {
    public static final RequestMethod REQUEST_TYPE = RequestMethod.POST;
    public static final String REQUEST_NAME = "click";
    private static final List<Click.HeaderKey> HEADER_KEY_LIST;
    private static final List<Click.ParameterKey> PARAMETER_KEY_LIST;
    private static final List<Click.BodyKey> BODY_KEY_LIST;

    public static enum HeaderKey
    {
        USER_ID;
    }

    public static enum ParameterKey
    {

    }

    public static enum BodyKey
    {

    }



    static
    {
        HEADER_KEY_LIST = new ArrayList<Click.HeaderKey>();
        BODY_KEY_LIST = new ArrayList<Click.BodyKey>();
        PARAMETER_KEY_LIST = new ArrayList<Click.ParameterKey>();

        for(Click.HeaderKey key : Click.HeaderKey.values())
        {
            HEADER_KEY_LIST.add(key);
        }

        for(Click.ParameterKey key : Click.ParameterKey.values())
        {
            PARAMETER_KEY_LIST.add(key);
        }

        for(Click.BodyKey key : Click.BodyKey.values())
        {
            BODY_KEY_LIST.add(key);
        }

    };

    private Click()
    {

    };

    public static String getHeaderName(Click.HeaderKey key)
    {
        return RequestParameterConverter.convertKeyName(key);
    }

    public static List<String> getHeaderNameList()
    {
        List<String> headerNameList = new ArrayList<>();

        for(Click.HeaderKey key : HEADER_KEY_LIST)
        {
            headerNameList.add(getHeaderName(key));
        }

        return headerNameList;
    }

    public static String getParameterName(Click.ParameterKey key)
    {
        return RequestParameterConverter.convertKeyName(key);
    }

    public static List<String> getParameterNameList()
    {
        List<String> parameterNameList = new ArrayList<>();

        for(Click.ParameterKey key : PARAMETER_KEY_LIST)
        {
            parameterNameList.add(getParameterName(key));
        }

        return parameterNameList;
    }

    public static String getBodyName(Click.BodyKey key)
    {
        return RequestParameterConverter.convertKeyName(key);
    }

    public static List<String> getBodyNameList()
    {
        List<String> bodyNameList = new ArrayList<>();

        for(Click.BodyKey key : BODY_KEY_LIST)
        {
            bodyNameList.add(getBodyName(key));
        }

        return bodyNameList;
    }
}
