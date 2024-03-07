package br.com.hgisystem.certificatemanager.infra.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public abstract class ConvertJsonToMapHelper {
    public static Map getMap(String json) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();

        Map jsonMap = om.readValue(json, new TypeReference<Map<String, Object>>(){});

        return jsonMap;
    }
}
