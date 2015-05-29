package com.logotet.androsvis;



import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * HttpCatcher
 * <p/>
 * klasa koja salje HttpRequest
 */
public class HttpCatcher {
    private String urlAdresa;
    BufferedReader inFile;
    InputStream instream;

    public HttpCatcher(String urlAdresa) {
        this.urlAdresa = urlAdresa;
    }

    public InputStream getInputStream() {
        try {
            HttpGet httpget = new HttpGet(urlAdresa);
            HttpClient client = new DefaultHttpClient();
//            client.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);

            HttpResponse response = client.execute(httpget);
            HttpEntity httpEntity = response.getEntity();
            if (httpEntity != null) {
                instream = httpEntity.getContent();
            }else
                return null;
        } catch (ClientProtocolException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
        return instream;
    }

    public StringBuffer getHtmlPage() {
        getInputStream();
        inFile = new BufferedReader(new InputStreamReader(instream));
        StringBuffer sb = readAndParse();
        return sb;
    }

    private StringBuffer readAndParse() {
        StringBuffer sb = new StringBuffer();
        boolean petlja = true;
        while (petlja) {
            String linija = null;
            try {
                linija = inFile.readLine();
                if (linija != null) {
                    sb.append("\n" + linija);
                } else
                    petlja = false;
            } catch (NullPointerException e) {
                petlja = false;
            } catch (IOException e) {
                petlja = false;
            }
        }
        try {
            inFile.close();
        } catch (IOException e) {
        }
        return sb;
    }

}