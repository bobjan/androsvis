package com.logotet.androsvis.xmlparser;

import com.logotet.androsvis.model.Sednica;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.CharArrayWriter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Klasa koja parsira config.xml
 */
public class SednicaXMLHandler extends DefaultHandler {
    private int pcData = 0;
    private CharArrayWriter contents = new CharArrayWriter();
    private InputStream inputStream;
    private List sednice;

    private String broj;
    private String datum;
    private String id;

    private boolean isOK;

    public SednicaXMLHandler(InputStream inputStream) {
        this.inputStream = inputStream;
        sednice = new ArrayList();
        isOK = false;
    }

    public List getSednice() {
        return sednice;
    }

    /**
     * po standardnom APIju ova metoda hvata pocetak nekog taga, a u njoj je moguce
     * procitati i atribute
     */
    public void startElement(String namespaceURI, String localName,
                             String rawName, Attributes attr) throws SAXException {
        contents.reset();

        if (rawName.equals("Vece")) {
            if(pcData == 0)
                isOK = true;
                pcData = 1;
        }
        if (rawName.equals("Sednica")) {
            if(isOK && (pcData == 1))
                pcData = 2;
            else
                isOK = false;
        }

        if (rawName.equals("Broj")) {
                pcData = 3;
        }
        if (rawName.equals("Id")) {
                pcData = 4;
        }
        if (rawName.equals("Datum")) {
                pcData = 5;
        }

    }

    /**
     * po standardnom APIju ova metoda hvata kraj nekog taga,
     */
    public void endElement(String namespaceURI, String localName,
                           String rawName) throws SAXException {
        if (rawName.equals("Sednica")) {
            sednice.add(new Sednica(id,broj,datum));
            pcData = 1;
        }
        if (rawName.equals("Broj")) {
                pcData = 33;
        }
        if (rawName.equals("Id")) {
                pcData = 34;
        }
        if (rawName.equals("Datum")) {
                pcData = 35;
        }

    }

    /**
     * Za sve PCDATA iz XML fajla
     */
    public void characters(char[] ch, int start, int length) throws SAXException {
        contents.write(ch, start, length);//ne znam cemu sluzi ali neka ostane
        String tekst = new String(ch, start, length);
        switch(pcData){
            case 3:
                broj = tekst;
                break;
            case 4:
                id = tekst;
                break;
            case 5:
                datum = tekst;
                break;
            default:
                break;
        }
    }

    /**
     * odradjuje sve, tj. cita ulazni file i parsira ga
     */
    public void doEntireJob() {
        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            spf.setValidating(false);
            SAXParser saxParser = spf.newSAXParser();
            XMLReader xr = saxParser.getXMLReader();
            xr.setContentHandler(this);

            // Parse the file...
            xr.parse(new InputSource(inputStream));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
