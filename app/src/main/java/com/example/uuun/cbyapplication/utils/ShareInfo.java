package com.example.uuun.cbyapplication.utils;

/**
 * 分享内容数据
 *
 * Created by hyw on 2016/11/1.
 */
public class ShareInfo {

    String title;
    String text;
    String iconPath;
    String netPath;

    public String getNetPath() {
        return netPath;
    }

    public void setNetPath(String netPath) {
        this.netPath = netPath;
    }

    public ShareInfo() {
    }

    public ShareInfo(String title, String text, String iconPath) {
        this.title = title;
        this.text = text;
        this.iconPath = iconPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIconPath() {
        return iconPath;
    }

    public void setIconPath(String iconPath) {
        this.iconPath = iconPath;
    }
}
