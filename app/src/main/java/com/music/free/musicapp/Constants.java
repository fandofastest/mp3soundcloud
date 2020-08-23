package com.music.free.musicapp;

import com.music.free.modalclass.SongModalClass;

import java.util.ArrayList;
import java.util.List;

public class Constants {
    private Constants() {
    }


     static  String ads;
     static String key;
     static String banner;
     static String appid;
    static  String   inter;
    static  String interfan;
    static  String bannerfan;
    static  String   statusapp;
    static  String    appupdate;
    static   String    q;
     static int totalduration;
     static int currentduration;
      static String intentfilter="fando";
      static String status="status";
     static String serverurl="https://fando.id/soundcloud/get.php?id=";//dont change
     static String downloaddir="/Downloads";
     static String urlstatus="http://fando.id/simplemusic/getstatus.php";
     static String querysearch;

    public static String getAppid() {
        return appid;
    }

    public static void setAppid(String appid) {
        Constants.appid = appid;
    }

    public static String getInterfan() {
        return interfan;
    }

    public static void setInterfan(String interfan) {
        Constants.interfan = interfan;
    }

    public static String getBannerfan() {
        return bannerfan;
    }

    public static void setBannerfan(String bannerfan) {
        Constants.bannerfan = bannerfan;
    }

    public static String getAds() {
        return ads;
    }

    public static void setAds(String ads) {
        Constants.ads = ads;
    }

    public static List<SongModalClass> getListsongModalClasses() {
        return listsongModalClasses;
    }



    public static void setListsongModalClassesClear() {
        Constants.listsongModalClasses.clear();
    }

    public static void addListsongModalClasses(SongModalClass songModalClass) {
        Constants.listsongModalClasses.add(songModalClass);
    }


    public static void setListsongModalClasses(List<SongModalClass> listsongModalClasses) {
        Constants.listsongModalClasses = listsongModalClasses;
    }

    static List<SongModalClass> listsongModalClasses = new ArrayList<>();


    public static String getQuerysearch() {
        return querysearch;
    }

    public static void setQuerysearch(String querysearch) {
        Constants.querysearch = querysearch;
    }

    public static String getKey() {
        return key;
    }

    public static void setKey(String key) {
        Constants.key = key;
    }

    public static String getBanner() {
        return banner;
    }

    public static void setBanner(String banner) {
        Constants.banner = banner;
    }

    public static String getInter() {
        return inter;
    }

    public static void setInter(String inter) {
        Constants.inter = inter;
    }

    public static String getStatusapp() {
        return statusapp;
    }

    public static void setStatusapp(String statusapp) {
        Constants.statusapp = statusapp;
    }

    public static String getAppupdate() {
        return appupdate;
    }

    public static void setAppupdate(String appupdate) {
        Constants.appupdate = appupdate;
    }

    public static String getQ() {
        return q;
    }

    public static void setQ(String q) {
        Constants.q = q;
    }

    public static int getTotalduration() {
        return totalduration;
    }

    public static void setTotalduration(int totalduration) {
        Constants.totalduration = totalduration;
    }

    public static int getCurrentduration() {
        return currentduration;
    }

    public static void setCurrentduration(int currentduration) {
        Constants.currentduration = currentduration;
    }

    public static String getIntentfilter() {
        return intentfilter;
    }

    public static void setIntentfilter(String intentfilter) {
        Constants.intentfilter = intentfilter;
    }

    public static String getStatus() {
        return status;
    }

    public static void setStatus(String status) {
        Constants.status = status;
    }

    public static String getServerurl() {
        return serverurl;
    }

    public static void setServerurl(String serverurl) {
        Constants.serverurl = serverurl;
    }

    public static String getDownloaddir() {
        return downloaddir;
    }

    public static void setDownloaddir(String downloaddir) {
        Constants.downloaddir = downloaddir;
    }

    public static String getUrlstatus() {
        return urlstatus;
    }

    public static void setUrlstatus(String urlstatus) {
        Constants.urlstatus = urlstatus;
    }
}
