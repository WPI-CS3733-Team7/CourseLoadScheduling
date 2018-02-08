package org.dselent.scheduling.server.requests;

import org.dselent.scheduling.server.miscellaneous.RequestParameterConverter;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

public class Edit {
    public static final RequestMethod REQUEST_TYPE = RequestMethod.POST;
    public static final String REQUEST_NAME = "Edit";
    private static final List<Edit.HeaderKey> HEADER_KEY_LIST;
    private static final List<Edit.ParameterKey> PARAMETER_KEY_LIST;
    private static final List<Edit.BodyKey> BODY_KEY_LIST;

    public static enum HeaderKey
    {

    }

    public static enum ParameterKey
    {

    }

    public static enum BodyKey
    {
        USER_NAME,
        PASSWORD;
    }



    static
    {
        HEADER_KEY_LIST = new ArrayList<Edit.HeaderKey>();
        BODY_KEY_LIST = new ArrayList<Edit.BodyKey>();
        PARAMETER_KEY_LIST = new ArrayList<Edit.ParameterKey>();

        for(Edit.HeaderKey key : Edit.HeaderKey.values())
        {
            HEADER_KEY_LIST.add(key);
        }

        for(Edit.ParameterKey key : Edit.ParameterKey.values())
        {
            PARAMETER_KEY_LIST.add(key);
        }

        for(Edit.BodyKey key : Edit.BodyKey.values())
        {
            BODY_KEY_LIST.add(key);
        }

    };

    private Edit()
    {

    };

    public static String getHeaderName(Edit.HeaderKey key)
    {
        return RequestParameterConverter.convertKeyName(key);
    }

    public static List<String> getHeaderNameList()
    {
        List<String> headerNameList = new ArrayList<>();

        for(Edit.HeaderKey key : HEADER_KEY_LIST)
        {
            headerNameList.add(getHeaderName(key));
        }

        return headerNameList;
    }

    public static String getParameterName(Edit.ParameterKey key)
    {
        return RequestParameterConverter.convertKeyName(key);
    }

    public static List<String> getParameterNameList()
    {
        List<String> parameterNameList = new ArrayList<>();

        for(Edit.ParameterKey key : PARAMETER_KEY_LIST)
        {
            parameterNameList.add(getParameterName(key));
        }

        return parameterNameList;
    }

    public static String getBodyName(Edit.BodyKey key)
    {
        return RequestParameterConverter.convertKeyName(key);
    }

    public static List<String> getBodyNameList()
    {
        List<String> bodyNameList = new ArrayList<>();

        for(Edit.BodyKey key : BODY_KEY_LIST)
        {
            bodyNameList.add(getBodyName(key));
        }

        return bodyNameList;
    }
}
