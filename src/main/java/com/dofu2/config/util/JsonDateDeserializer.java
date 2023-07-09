package com.dofu2.config.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class JsonDateDeserializer extends JsonDeserializer<java.sql.Date> {

    @Override
    public java.sql.Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String dateAsString = jsonParser.getText();
        SimpleDateFormat sdf = new SimpleDateFormat(Constants.DefaultDateFormat);
        try {
            if (dateAsString.isEmpty()) {
                return null;
            }
            else {
                return new Date(sdf.parse(dateAsString).getTime());
            }
        }
        catch (ParseException pe) {
            throw new RuntimeException(pe);
        }
    }
}
