package org.dselent.scheduling.server.requests;

import org.dselent.scheduling.server.miscellaneous.RequestParameterConverter;
import org.dselent.scheduling.server.model.Request;
import org.dselent.scheduling.server.model.RequestType;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

public class Submit {
    public static final RequestMethod REQUEST_TYPE = RequestMethod.POST;
    public static final String REQUEST_NAME = "submit";
    private static final List<Submit.HeaderKey> HEADER_KEY_LIST;
    private static final List<Submit.ParameterKey> PARAMETER_KEY_LIST;
    private static final List<Submit.BodyKey> BODY_KEY_LIST;

    public static enum HeaderKey
    {
        USER_ID;
    }

    public static enum ParameterKey
    {

    }

    public static enum BodyKey
    {
        Request_Type,
        Request_Details;
    }

    static
    {
        HEADER_KEY_LIST = new ArrayList<Submit.HeaderKey>();
        BODY_KEY_LIST = new ArrayList<Submit.BodyKey>();
        PARAMETER_KEY_LIST = new ArrayList<Submit.ParameterKey>();

        for(Submit.HeaderKey key : Submit.HeaderKey.values())
        {
            HEADER_KEY_LIST.add(key);
        }

        for(Submit.ParameterKey key : Submit.ParameterKey.values())
        {
            PARAMETER_KEY_LIST.add(key);
        }

        for(Submit.BodyKey key : Submit.BodyKey.values())
        {
            BODY_KEY_LIST.add(key);
        }

    };

    private Submit()
    {

    };

    public static String getHeaderName(Submit.HeaderKey key)
    {
        return RequestParameterConverter.convertKeyName(key);
    }

    public static List<String> getHeaderNameList()
    {
        List<String> headerNameList = new ArrayList<>();

        for(Submit.HeaderKey key : HEADER_KEY_LIST)
        {
            headerNameList.add(getHeaderName(key));
        }

        return headerNameList;
    }

    public static String getParameterName(Submit.ParameterKey key)
    {
        return RequestParameterConverter.convertKeyName(key);
    }

    public static List<String> getParameterNameList()
    {
        List<String> parameterNameList = new ArrayList<>();

        for(Submit.ParameterKey key : PARAMETER_KEY_LIST)
        {
            parameterNameList.add(getParameterName(key));
        }

        return parameterNameList;
    }

    public static String getBodyName(Submit.BodyKey key)
    {
        return RequestParameterConverter.convertKeyName(key);
    }

    public static List<String> getBodyNameList()
    {
        List<String> bodyNameList = new ArrayList<>();

        for(Submit.BodyKey key : BODY_KEY_LIST)
        {
            bodyNameList.add(getBodyName(key));
        }

        return bodyNameList;
    }
}
