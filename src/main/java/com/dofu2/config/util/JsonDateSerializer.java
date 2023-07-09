package com.dofu2.config.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.dofu2.config.util.Constants;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Bradley Moore on 05/18/2017.
 */
public class JsonDateSerializer extends JsonSerializer<Date> {

    @Override
    public void serialize(Date value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        DateFormat formatter = new SimpleDateFormat(Constants.DefaultDateFormat);
        jgen.writeString(formatter.format(value));
    }
}
