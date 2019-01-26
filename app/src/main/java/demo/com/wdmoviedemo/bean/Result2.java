package demo.com.wdmoviedemo.bean;

import java.util.List;

/**
 * 作者: Wang on 2019/1/26 19:06
 * 寄语：加油！相信自己可以！！！
 */


public class Result2 {

    private List<Cinemaslist> cinemaslist;
    private String headpic;
    private int integral;
    private List<Movielist> movielist;
    private String nickname;
    private String phone;
    private int usersignstatus;

    public void setCinemaslist(List<Cinemaslist> cinemaslist) {
        this.cinemaslist = cinemaslist;
    }

    public List<Cinemaslist> getCinemaslist() {
        return cinemaslist;
    }

    public void setHeadpic(String headpic) {
        this.headpic = headpic;
    }

    public String getHeadpic() {
        return headpic;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public int getIntegral() {
        return integral;
    }

    public void setMovielist(List<Movielist> movielist) {
        this.movielist = movielist;
    }

    public List<Movielist> getMovielist() {
        return movielist;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setUsersignstatus(int usersignstatus) {
        this.usersignstatus = usersignstatus;
    }

    public int getUsersignstatus() {
        return usersignstatus;
    }

}