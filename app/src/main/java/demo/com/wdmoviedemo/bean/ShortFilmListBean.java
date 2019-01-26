package demo.com.wdmoviedemo.bean;

/**
 * date: 2019/1/25.
 * Created 王思敏
 * function:
 */
public class ShortFilmListBean {
    /**
     * imageUrl : http://172.17.8.100/images/movie/stills/fyz/fyz2.jpg
     * videoUrl : http://172.17.8.100/video/movie/fyz/fyz1.ts
     */

    private String imageUrl;
    private String videoUrl;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
