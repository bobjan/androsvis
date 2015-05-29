package com.logotet.androsvis.xmlparser;

import com.logotet.androsvis.HttpCatcher;
import com.logotet.androsvis.model.Prilog;
import com.logotet.androsvis.model.Sednica;
import com.logotet.androsvis.model.Tacka;

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
public class DnevniRedXMLHandler extends DefaultHandler {
    private int pcData = 0;
    private CharArrayWriter contents = new CharArrayWriter();
    private InputStream inputStream;
    private Sednica currentSednica;
    private Tacka currentTacka;
    private Prilog currentPrilog;

    private String rednibroj;
    private String podbroj;
    private String godina;
    private String naziv;
    private String izvestilac;


    private String opis;
    private String size;
    private String href;
    private String nazivPriloga;

    private boolean isPrilog;

    public DnevniRedXMLHandler(InputStream inputStream) {
        this.inputStream = inputStream;
        currentSednica = new Sednica();
        isPrilog = false;
    }

    public Sednica getCurrentSednica() {
        return currentSednica;
    }

    /**
     * po standardnom APIju ova metoda hvata pocetak nekog taga, a u njoj je moguce
     * procitati i atribute
     */
    public void startElement(String namespaceURI, String localName,
                             String rawName, Attributes attr) throws SAXException {
        contents.reset();

        if (rawName.equals("sednica")) {
            currentSednica = new Sednica(attr.getValue("id"), attr.getValue("broj"),attr.getValue("datum"));
            pcData = 1;
        }

        if (rawName.equals("tacka")) {
             pcData = 2;
            currentTacka = new Tacka();
            isPrilog = false;
        }
        if (rawName.equals("rednibroj")) {
                pcData = 3;
        }
        if (rawName.equals("podbroj")) {
                pcData = 4;
        }
        if (rawName.equals("godina")) {
                pcData = 5;
        }
        if (rawName.equals("naziv")) {
                pcData = 6;
        }
        if (rawName.equals("izvestilac")) {
            pcData = 7;
        }
        if (rawName.equals("prilozi")) {
                isPrilog = true;
        }
        if (rawName.equals("prilog")) {
                isPrilog = true;
                pcData = 10;
                currentPrilog = new Prilog();
        }
        if (rawName.equals("opis")) {
                pcData = 11;
        }
        if (rawName.equals("size")) {
                pcData = 13;
        }
        if (rawName.equals("href")) {
                pcData = 14;
        }
    }

    /**
     * po standardnom APIju ova metoda hvata kraj nekog taga,
     */
    public void endElement(String namespaceURI, String localName,
                           String rawName) throws SAXException {
        if (rawName.equals("tacka")) {
            currentTacka.setNaziv(naziv);
            currentTacka.setGodina(godina);
            currentTacka.setIzvestilac(izvestilac);
            currentTacka.setPodbroj(Integer.parseInt(podbroj));
            currentTacka.setRednibroj(Integer.parseInt(rednibroj));
            currentSednica.addTacka(currentTacka);
        }
        if (rawName.equals("rednibroj")) {
                pcData = 0;
        }
        if (rawName.equals("podbroj")) {
                pcData = 0;
        }
        if (rawName.equals("godina")) {
                pcData = 0;
        }
        if (rawName.equals("naziv")) {
               pcData = 0;
        }
        if (rawName.equals("izvestilac")) {
            pcData = 0;
        }
        if (rawName.equals("prilog")) {
                pcData = 0;
            currentPrilog.setHref(href);
            currentPrilog.setOpis(opis);
            currentPrilog.setNaziv(nazivPriloga);
            currentPrilog.setVelicina(size);
            currentTacka.addPrilog(currentPrilog);
        }
        if (rawName.equals("opis")) {
                pcData = 0;
        }
        if (rawName.equals("size")) {
                pcData = 0;
        }
        if (rawName.equals("href")) {
                pcData = 0;
        }
        if (rawName.equals("prilozi")) {
                pcData = 0;
            isPrilog = false;
        }
        if (rawName.equals("prilog")) {
                pcData = 0;
            isPrilog = false;
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
                rednibroj = tekst;
                break;
            case 4:
                podbroj = tekst;
                break;
            case 5:
                godina = tekst;
                break;
            case 6:
                if(isPrilog)
                    nazivPriloga = tekst;
                else
                    naziv = tekst;
                break;
            case 7:
                  izvestilac = tekst;
                break;
            case 11:
                opis = tekst;
                break;
            case 12:
                nazivPriloga = tekst;
                break;
            case 13:
                size = tekst;
                break;
            case 14:
                href = tekst;
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

    public static void main(String[] args) {
        HttpCatcher http = new HttpCatcher("http://130.180.228.82/svisxml/dnevnired.php?sid=320");
        DnevniRedXMLHandler hip = new DnevniRedXMLHandler(http.getInputStream());
        hip.doEntireJob();
            Sednica sed = hip.getCurrentSednica();
            System.out.println(sed.getFull());
        }
   
}
