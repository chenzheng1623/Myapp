package com.example.myapplication.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.bean.RssFeed;
import com.example.myapplication.bean.RssItem;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by cz on 2015/9/7 0007.
 */
public class Rssfragment extends Fragment {
    private String url = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getfeed(url);

    }

    private void getfeed(String urlstring) {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
            URL url = new URL(urlstring);
            SAXParser saxParser = saxParserFactory.newSAXParser();
            XMLReader xmlReader = saxParser.getXMLReader();
            Rsshandle rsshandle = new Rsshandle();
            xmlReader.setContentHandler(rsshandle);
            InputSource inputSource = new InputSource(url.openStream());
            xmlReader.parse(inputSource);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class Rsshandle extends DefaultHandler {
        RssFeed rssFeed;
        RssItem rssItem;


        int currentstates=0;

        RssFeed getfeed() {
            return rssFeed;
        }

        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
            rssFeed = new RssFeed();
            rssItem = new RssItem();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, qName, attributes);

        }

        @Override
        public void endDocument() throws SAXException {
            super.endDocument();
        }
    }
}
