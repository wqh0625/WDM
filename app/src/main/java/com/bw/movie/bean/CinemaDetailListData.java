package com.bw.movie.bean;

/**
 * 作者: Wang on 2019/1/27 09:55
 * 寄语：加油！相信自己可以！！！
 */


public class CinemaDetailListData {

//    {"duration":"116分钟","fare":0.28,"id":15,"imageUrl":"http://mobile.bwstudent.com/images/movie/stills/aqgy/aqgy1.jpg","name":"爱情公寓","releaseTime":1565366400000,"summary":"《爱情公寓》电影版归来，原班人马十年催泪重聚。曾小贤、胡一菲、唐悠悠、吕子乔、张伟、陈美嘉悉数回归，还是熟悉的场景和熟悉的人，嘻嘻哈哈、打打闹闹，笑声从没停过。老朋友的故事将继续展开，印证了电视剧的那句主题语——“最好的朋友在身边，最爱的人就在对面”。不过这一次，他们打算搞个大事情……"}
    private double fare;
    private int id;
    private String imageurl;
    private String name;
    private long releasetime;
    private String summary;

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getReleasetime() {
        return releasetime;
    }

    public void setReleasetime(long releasetime) {
        this.releasetime = releasetime;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
