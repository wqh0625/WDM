package demo.com.wdmoviedemo.bean;

/**
 * date: 2019/1/29.
 * Created 王思敏
 * function:待付款
 */
public class ObligationData {

    /**
     * amount : 3
     * beginTime : 40800000
     * cinemaName : 青春光线电影院
     * createTime : 1533805513000
     * endTime : 47880000
     * id : 52
     * movieName : 西虹市首富
     * orderId : 20180809170513183
     * price : 66.5
     * screeningHall : 2号厅
     * status : 1
     * userId : 5
     */
    private int amount;
    private String beginTime;
    private String cinemaName;
    private long createTime;
    private String endTime;
    private int id;
    private String movieName;
    private String orderId;
    private double price;
    private String screeningHall;
    private int status;
    private int userId;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
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

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
