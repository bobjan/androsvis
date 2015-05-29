package com.logotet.androsvis.xmlparser;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.CharArrayWriter;
import java.io.InputStream;

/**
 * Klasa koja parsira config.xml
 */
public class HostIPHandler extends DefaultHandler {
    private int pcData = 0;
    private CharArrayWriter contents = new CharArrayWriter();
    private InputStream inputStream;
    private boolean isOk;
    String hostIp = null;

    public HostIPHandler(InputStream inputStream) {
        this.inputStream = inputStream;
        isOk = false;
    }

    public boolean isOk() {
        return isOk;
    }

    public String getHostIp() {
        return hostIp;
    }

    /**
     * po standardnom APIju ova metoda hvata pocetak nekog taga, a u njoj je moguce
     * procitati i atribute
     */
    public void startElement(String namespaceURI, String localName,
                             String rawName, Attributes attr) throws SAXException {
        contents.reset();

        if (rawName.equals("androsvis")) {
            isOk = true;
        }
        if (rawName.equals("host")) {
            pcData = 123;
        }
    }

    /**
     * po standardnom APIju ova metoda hvata kraj nekog taga,
     */
    public void endElement(String namespaceURI, String localName,
                           String rawName) throws SAXException {
        if (rawName.equals("androsvis")) {
            pcData = 0;
        }
        if (rawName.equals("host")) {
            pcData = 1;
        }
    }

    /**
     * Za sve PCDATA iz XML fajla
     */
    public void characters(char[] ch, int start, int length) throws SAXException {
        contents.write(ch, start, length);//ne znam cemu sluzi ali neka ostane
        String tekst = new String(ch, start, length);
        if(pcData == 123)
            hostIp = tekst;
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

//    public static void main(String[] args) {
//        HttpCatcher http = new HttpCatcher("http://savskivenac.rs/svishostip.php");
//        HostIPHandler hip = new HostIPHandler(http.getInputStream());
//        hip.doEntireJob();
//        if(hip.isOk())
//            System.out.println(hip.getHostIp());
//    }
}
