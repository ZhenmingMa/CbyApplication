package com.example.uuun.cbyapplication.utils;

import com.example.uuun.cbyapplication.bean.Province;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by uuun on 2017/6/26.
 */
public class MyHandler extends DefaultHandler {
    private List<Province> list;
    private String tag;
    private Province province;

    public List<Province> getList() {
        return list;
    }

    @Override
    public void startDocument() throws SAXException {
        list = new ArrayList<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
       tag = qName;
        if("Province".equals(qName)){
        province = new Province();
       }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String content = new String(ch,start,length);
        if("ID".equals(tag)){
            province.setId(Integer.parseInt(content));
        }else if("ProvinceName".equals(tag)){
            province.setProvinceName(content);
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        tag = "";
        if("Province".equals(qName)){
            list.add(province);
        }
    }
}
