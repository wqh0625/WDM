package com.bw.movie.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "user")
public class UserInfoBean {
    @DatabaseField
    private long birthday;
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField
    private long lastLoginTime;
    @DatabaseField
    private String nickName;
    @DatabaseField
    private String phone;
    @DatabaseField
    private int sex;
    @DatabaseField
    private String headPic;

    @DatabaseField
    private String sessionId;

    @DatabaseField
    private int stats;
    @DatabaseField
    private int userId;

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }




    public void setStats(int stats) {
        this.stats = stats;
    }

    public int getStats() {
        return stats;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(long lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    @Override
    public String toString() {
        return "UserInfoBean{" +
                "birthday=" + birthday +
                ", id=" + id +
                ", lastLoginTime=" + lastLoginTime +
                ", nickName='" + nickName + '\'' +
                ", phone='" + phone + '\'' +
                ", sex=" + sex +
                ", headPic='" + headPic + '\'' +
                ", sessionId='" + sessionId + '\'' +

                ", stats=" + stats +
                ", userId=" + userId +
                '}';
    }
}