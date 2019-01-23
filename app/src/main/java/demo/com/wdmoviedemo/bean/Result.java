package demo.com.wdmoviedemo.bean;

/**
 * 作者: Wang on 2019/1/22 16:10
 * 寄语：加油！相信自己可以！！！
 */


public class Result<T> {

    /**
     * result : {"sessionId":"15320592619803","userId":3,"userInfo":{"birthday":320256000000,"id":3,"lastLoginTime":1532059192000,"nickName":"你的益达","phone":"18600151568","sex":1,"headPic":"http://172.17.8.100/images/head_pic/bwjy.jpg"}}
     * message : 登陆成功
     * status : 0000
     */

    private T result;
    private String message;
    private String status;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
