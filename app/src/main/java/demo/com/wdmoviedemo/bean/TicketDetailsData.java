package demo.com.wdmoviedemo.bean;

import java.util.List;

/**
 * date: 2019/1/26.
 * Created 王思敏
 * function:购票详情页
 */
public class TicketDetailsData {

    /**
     * beginTime : 20:00
     * duration : 136分钟
     * endTime : 21:48
     * id : 595
     * price : 0.28
     * screeningHall : 7号厅
     * seatsTotal : 90
     * seatsUseCount : 30
     * status : 1
     */

    private String beginTime;
    private String duration;
    private String endTime;
    private int id;
    private double price;
    private String screeningHall;
    private int seatsTotal;
    private int seatsUseCount;
    private int status;

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getScreeningHall() {
        return screeningHall;
    }

    public void setScreeningHall(String screeningHall) {
        this.screeningHall = screeningHall;
    }

    public int getSeatsTotal() {
        return seatsTotal;
    }

    public void setSeatsTotal(int seatsTotal) {
        this.seatsTotal = seatsTotal;
    }

    public int getSeatsUseCount() {
        return seatsUseCount;
    }

    public void setSeatsUseCount(int seatsUseCount) {
        this.seatsUseCount = seatsUseCount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
