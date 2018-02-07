package org.dselent.scheduling.server.requests;

import java.util.ArrayList;
import java.util.List;

import org.dselent.scheduling.server.miscellaneous.RequestParameterConverter;
import org.springframework.web.bind.annotation.RequestMethod;

public class Select {
    public static final RequestMethod REQUEST_TYPE = RequestMethod.POST;
    public static final String REQUEST_NAME = "select";
    private static final List<Select.HeaderKey> HEADER_KEY_LIST;
    private static final List<Select.ParameterKey> PARAMETER_KEY_LIST;
    private static final List<Select.BodyKey> BODY_KEY_LIST;

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
        HEADER_KEY_LIST = new ArrayList<Select.HeaderKey>();
        BODY_KEY_LIST = new ArrayList<Select.BodyKey>();
        PARAMETER_KEY_LIST = new ArrayList<Select.ParameterKey>();

        for(Select.HeaderKey key : Select.HeaderKey.values())
        {
            HEADER_KEY_LIST.add(key);
        }

        for(Select.ParameterKey key : Select.ParameterKey.values())
        {
            PARAMETER_KEY_LIST.add(key);
        }

        for(Select.BodyKey key : Select.BodyKey.values())
        {
            BODY_KEY_LIST.add(key);
        }

    };

    private Select()
    {

    };

    public static String getHeaderName(Select.HeaderKey key)
    {
        return RequestParameterConverter.convertKeyName(key);
    }

    public static List<String> getHeaderNameList()
    {
        List<String> headerNameList = new ArrayList<>();

        for(Select.HeaderKey key : HEADER_KEY_LIST)
        {
            headerNameList.add(getHeaderName(key));
        }

        return headerNameList;
    }

    public static String getParameterName(Select.ParameterKey key)
    {
        return RequestParameterConverter.convertKeyName(key);
    }

    public static List<String> getParameterNameList()
    {
        List<String> parameterNameList = new ArrayList<>();

        for(Select.ParameterKey key : PARAMETER_KEY_LIST)
        {
            parameterNameList.add(getParameterName(key));
        }

        return parameterNameList;
    }

    public static String getBodyName(Select.BodyKey key)
    {
        return RequestParameterConverter.convertKeyName(key);
    }

    public static List<String> getBodyNameList()
    {
        List<String> bodyNameList = new ArrayList<>();

        for(Select.BodyKey key : BODY_KEY_LIST)
        {
            bodyNameList.add(getBodyName(key));
        }

        return bodyNameList;
    }
}
