package com.bw.movie.wxapi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.bw.movie.R;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import java.sql.SQLException;

import demo.com.wdmoviedemo.bean.LoginData;
import demo.com.wdmoviedemo.bean.Result;
import demo.com.wdmoviedemo.bean.UserInfoBean;
import demo.com.wdmoviedemo.core.base.BaseActivity;
import demo.com.wdmoviedemo.core.exception.ApiException;
import demo.com.wdmoviedemo.core.interfase.DataCall;

import demo.com.wdmoviedemo.core.utils.WeiXinUtil;
import demo.com.wdmoviedemo.presenter.WxLoginPresenter;
import demo.com.wdmoviedemo.view.HomeActivity;

public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {
    private WxLoginPresenter wxLoginPresenter;
    private String code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxentry);
        WeiXinUtil.reg(WXEntryActivity.this).handleIntent(getIntent(),this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(final BaseResp baseResp) {
    switch (baseResp.errCode){
        case BaseResp.ErrCode.ERR_OK:
            code = ((SendAuth.Resp) baseResp).code;
            wxLoginPresenter = new WxLoginPresenter(new WxCall());
            wxLoginPresenter.requestNet(code);
            break;
            default:
                break;
    }
    }
    class WxCall implements DataCall<Result<LoginData>> {

        @Override
        public void success(Result<LoginData> data) {
            if (data.getStatus().equals("0000")){
                //登录成功
                Toast.makeText(WXEntryActivity.this, ""+data.getMessage(), Toast.LENGTH_SHORT).show();
                //将登录信息存入数据库
                UserInfoBean userInfoBean = data.getResult().getUserInfo();
                userInfoBean.setUserId(data.getResult().getUserId());
                userInfoBean.setSessionId(data.getResult().getSessionId());
                try {
                    addUser(userInfoBean);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                //跳转登录成功后的页面
                startActivity(new Intent(WXEntryActivity.this, HomeActivity.class));
                finish();
                //下面只是一个跳转页面的动画，可不加
                overridePendingTransition(R.anim.ac_in, R.anim.ac_out);

            }
        }

        @Override
        public void fail(ApiException a) {
            Toast.makeText(WXEntryActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
        }
    }
}
