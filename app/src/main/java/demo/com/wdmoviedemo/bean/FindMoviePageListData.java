package demo.com.wdmoviedemo.bean;

/**
 * 作者: Wang on 2019/1/26 11:33
 * 寄语：加油！相信自己可以！！！
 */

public class FindMoviePageListData {
    private int id;
    private String imageUrl;
    private String name;
    private long releasetime;
    private String summary;

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
