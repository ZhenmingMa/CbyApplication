package com.example.uuun.cbyapplication.utils;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Xml;

import com.example.uuun.cbyapplication.bean.Province;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by uuun on 2017/6/30.
 */

public class PullUtil {
    /**
     * 获取省份列表
     * @param ctx
     * @param xmlname
     * @param tablename
     * @return
     */
    public static List<Province> getAttributeList(Context ctx, String xmlname, String tablename) {
        AssetManager asset = ctx.getAssets();
        List<Province> list = null;
        try {
            /**��ȡassets��xml��������*/
            InputStream input = asset.open(xmlname);
            /**��ʼ����*/
            list = PullParseXML(input, tablename);
            MyLog.info(""+list.size());
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<Province> PullParseXML(InputStream inStream, String tablename) {
        MyLog.info("-----------------");
        List<Province> list = null;
        Province province = null;
        // ����XmlPullParserFactory
        try {
            XmlPullParser xmlPullParser = Xml.newPullParser();
            // ���������� xml�ļ�
            xmlPullParser.setInput(inStream, "UTF-8");

            // ��ʼ
            int eventType = xmlPullParser.getEventType();
            String id = null;
            try {
                while (eventType != XmlPullParser.END_DOCUMENT) {

                    String nodeName = xmlPullParser.getName();
//                    MyLog.info("nodeName=" + nodeName);
                    switch (eventType) {
                        // �ĵ���ʼ
                        case XmlPullParser.START_DOCUMENT:
                            list = new ArrayList<>();
                            break;
                        // ��ʼ�ڵ�
                        case XmlPullParser.START_TAG:
                            if ("Province".equals(nodeName)) {
                                province = new Province();
                                province.setId(Integer.parseInt(xmlPullParser.getAttributeValue(0)));
                                province.setProvinceName(xmlPullParser.getAttributeValue(1));

                            }
                            break;
                        // �����ڵ�
                        case XmlPullParser.END_TAG:
                            if ("Province".equals(nodeName)) {
                                list.add(province);
                                MyLog.info(province.getProvinceName());

                            }
                            break;
                        default:
                            break;
                    }
                    eventType = xmlPullParser.next();
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获取城市列表
     * @param ctx
     * @param xmlname
     * @param pid
     * @return
     */
    public static List<Province> getCityList(Context ctx, String xmlname,String pid){
        AssetManager asset = ctx.getAssets();
        List<Province> list = null;
        try {
            InputStream input = asset.open(xmlname);
            list = PullParseCityXML(input,pid);

        } catch (Throwable e) {
            e.printStackTrace();
        }
        return list;
    }

    private static List<Province> PullParseCityXML(InputStream input, String pid) {
        List<Province> list = null;
        Province province = null;
        // ����XmlPullParserFactory
        try {
            XmlPullParser xmlPullParser = Xml.newPullParser();
            // ���������� xml�ļ�
            xmlPullParser.setInput(input, "UTF-8");

            // ��ʼ
            int eventType = xmlPullParser.getEventType();
            String id = null;
            try {
                while (eventType != XmlPullParser.END_DOCUMENT) {

                    String nodeName = xmlPullParser.getName();
//                    MyLog.info("nodeName=" + nodeName);
                    switch (eventType) {
                        // �ĵ���ʼ
                        case XmlPullParser.START_DOCUMENT:
                            list = new ArrayList<>();
                            break;
                        // ��ʼ�ڵ�
                        case XmlPullParser.START_TAG:
                            // �ж������ʵ�ڵ�Ϊdic
                            if ("City".equals(nodeName)) {
                                id = xmlPullParser.getAttributeValue(2);
                                if(id.equals(pid)){
                                    MyLog.info("!!!!!!!!!!!!!!"+id.length());
                                    province = new Province();
                                    province.setId(Integer.parseInt(xmlPullParser.getAttributeValue(0)));
                                    province.setProvinceName(xmlPullParser.getAttributeValue(1));
                                    list.add(province);
                                }
                            }

                            break;
                        // �����ڵ�
                        case XmlPullParser.END_TAG:
                            if ("City".equals(nodeName)) {

                                //  MyLog.info(city.getCityName());

                            }
                            break;
                        default:
                            break;
                    }
                    eventType = xmlPullParser.next();
                }

            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        MyLog.info(""+list.size());
        return list;

    }

    /**
     * 获取地区列表
     */
    public static List<Province> getDistinctList(Context ctx, String xmlname,String cid){
        AssetManager asset = ctx.getAssets();
        List<Province> list = null;
        try {
            InputStream input = asset.open(xmlname);
            list = PullParseDistinctXML(input,cid);

        } catch (Throwable e) {
            e.printStackTrace();
        }
        return list;
    }

    private static List<Province> PullParseDistinctXML(InputStream input, String cid) {
        List<Province> list = null;
        Province province = null;
        // ����XmlPullParserFactory
        try {
            XmlPullParser xmlPullParser = Xml.newPullParser();
            // ���������� xml�ļ�
            xmlPullParser.setInput(input, "UTF-8");

            // ��ʼ
            int eventType = xmlPullParser.getEventType();
            String id = null;
            try {
                while (eventType != XmlPullParser.END_DOCUMENT) {

                    String nodeName = xmlPullParser.getName();
//                    MyLog.info("nodeName=" + nodeName);
                    switch (eventType) {
                        // �ĵ���ʼ
                        case XmlPullParser.START_DOCUMENT:
                            list = new ArrayList<>();
                            break;
                        // ��ʼ�ڵ�
                        case XmlPullParser.START_TAG:
                            // �ж������ʵ�ڵ�Ϊdic
                            if ("District".equals(nodeName)) {
                                id = xmlPullParser.getAttributeValue(2);
                                if(id.equals(cid)){
                                    province = new Province();
                                    province.setId(Integer.parseInt(xmlPullParser.getAttributeValue(0)));
                                    province.setProvinceName(xmlPullParser.getAttributeValue(1));
                                    list.add(province);
                                }
                            }

                            break;
                        // �����ڵ�
                        case XmlPullParser.END_TAG:
                            if ("District".equals(nodeName)) {

                                //  MyLog.info(city.getCityName());

                            }
                            break;
                        default:
                            break;
                    }
                    eventType = xmlPullParser.next();
                }

            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        MyLog.info(""+list.size());
        return list;

    }
}
