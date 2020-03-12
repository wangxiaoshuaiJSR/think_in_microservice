package org.architect.wxs.http.message;

import org.architect.wxs.domain.Person;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Properties;

public class PropertiesPersonHttpMessageConverter extends AbstractHttpMessageConverter<Person> {

    public PropertiesPersonHttpMessageConverter() {
        super(MediaType.valueOf("application/properties+person"));
        setDefaultCharset(Charset.forName("UTF-8"));
    }

    @Override
    protected boolean supports(Class<?> aClass) {
        return aClass.isAssignableFrom(Person.class);
    }

    @Override
    protected Person readInternal(Class<? extends Person> aClass, HttpInputMessage httpInputMessage) throws IOException, HttpMessageNotReadableException {
        InputStream inputMessageBody = httpInputMessage.getBody();

        Properties properties=new Properties();

        properties.load(new InputStreamReader(inputMessageBody,getDefaultCharset()));

        Person person=new Person();

        person.setId(Long.valueOf(properties.getProperty("person.id")));

        person.setName(properties.getProperty("person.name"));

        return person;
    }

    @Override
    protected void writeInternal(Person person, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {
        Properties properties=new Properties();
        OutputStream outputMessageBody = httpOutputMessage.getBody();

        properties.setProperty("person.id",String.valueOf(person.getId()));

        properties.setProperty("person.name",person.getName());

        properties.store(new OutputStreamWriter(outputMessageBody,getDefaultCharset()),"written by applicationserver");
    }
}
