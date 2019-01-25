package demo.com.wdmoviedemo.bean;

/**
 * date: 2019/1/25.
 * Created 王思敏
 * function:
 */
public class SearchData {
    /**
     * director : 克里斯托弗·麦奎里
     * duration : 148分钟
     * fare : 0.2
     * id : 16
     * imageUrl : http://172.17.8.100/images/movie/stills/dzd6qmwj/dzd6qmwj1.jpg
     * name : 碟中谍6：全面瓦解
     * placeOrigin : 美国
     * starring : 汤姆·克鲁斯,亨利·卡维尔,丽贝卡·弗格森,西蒙·佩吉
     * summary : 有时好意会造成恶果，人反而被自己所造成的结果所困扰。伊桑·亨特（汤姆·克鲁斯 饰）和他的IMF团队（亚历克·鲍德温、西蒙·佩吉、文·瑞姆斯）将在最新的电影《碟中谍6：全面瓦解》中再度回归，他们会与观众们熟悉的盟友（丽贝卡·弗格森、米歇尔·莫娜汉）一起与时间赛跑，应对一次任务中出现的意外。亨利·卡维尔、安吉拉·贝塞特和凡妮莎·柯比也将加入本片的演员阵容，电影制片人克里斯托夫·迈考利将会再度担任导演。
     */

    private String director;
    private String duration;
    private double fare;
    private int id;
    private String imageUrl;
    private String name;
    private String placeOrigin;
    private String starring;
    private String summary;

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlaceOrigin() {
        return placeOrigin;
    }

    public void setPlaceOrigin(String placeOrigin) {
        this.placeOrigin = placeOrigin;
    }

    public String getStarring() {
        return starring;
    }

    public void setStarring(String starring) {
        this.starring = starring;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
