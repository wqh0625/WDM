package com.bw.movie.bean;

public class Movielist {

    private int id;
    private String imageurl;
    private String name;
    private int releasetime;
    private String summary;
    public void setId(int id) {
         this.id = id;
     }
     public int getId() {
         return id;
     }

    public void setImageurl(String imageurl) {
         this.imageurl = imageurl;
     }
     public String getImageurl() {
         return imageurl;
     }

    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setReleasetime(int releasetime) {
         this.releasetime = releasetime;
     }
     public int getReleasetime() {
         return releasetime;
     }

    public void setSummary(String summary) {
         this.summary = summary;
     }
     public String getSummary() {
         return summary;
     }

}