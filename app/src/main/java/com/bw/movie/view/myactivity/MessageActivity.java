package com.bw.movie.view.myactivity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;

import java.util.List;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.bw.movie.bean.MessageData;
import com.bw.movie.bean.Result;
import com.bw.movie.core.adapter.MessageAdapter;
import com.bw.movie.core.base.BaseActivity;
import com.bw.movie.core.exception.ApiException;
import com.bw.movie.core.interfase.DataCall;
import com.bw.movie.presenter.ChangeSysMsgStatusPresenter;
import com.bw.movie.presenter.FindUnreadMessageCountPresenter;
import com.bw.movie.presenter.MessagePresenter;

public class MessageActivity extends BaseActivity {

    @BindView(R.id.message_recy)
    RecyclerView messageRecy;
    @BindView(R.id.img_finnish)
    ImageView imgFinnish;
    @BindView(R.id.weidu)
    TextView weudu;
    private MessageAdapter messageAdapter;
    private int userId;
    private String sessionId;
    private int position;
    // 未读列表
    private MessagePresenter messagePresenter;
    private ChangeSysMsgStatusPresenter changeSysMsgStatusPresenter;
    private FindUnreadMessageCountPresenter findUnreadMessageCountPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        userId = userInfoBean.getUserId();
        sessionId = userInfoBean.getSessionId();
        messageAdapter = new MessageAdapter(this);
        messageRecy.setAdapter(messageAdapter);
        messageRecy.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        // 查询消息列表
        messagePresenter = new MessagePresenter(new MessageCall());
        messagePresenter.requestNet(userId, sessionId, 1, 10);
        // 改变状态
        changeSysMsgStatusPresenter = new ChangeSysMsgStatusPresenter(new Change());
        // 查询未读条数
        findUnreadMessageCountPresenter = new FindUnreadMessageCountPresenter(new Find());
        messageAdapter.setOnHideText(new MessageAdapter.OnHideText() {
            @Override
            public void onHideText(int id, int i, MessageData messageData) {
                position = i;
                changeSysMsgStatusPresenter.requestNet(userId, sessionId, id, messageData);
            }
        });
        findUnreadMessageCountPresenter.requestNet(userId,sessionId);
    }

    @Override
    protected void onStart() {
        super.onStart();
        findUnreadMessageCountPresenter.requestNet(userId,sessionId);
    }

    @Override
    protected void onResume() {
        super.onResume();
        findUnreadMessageCountPresenter.requestNet(userId,sessionId);
    }

    @OnClick(R.id.img_finnish)
    public void onViewClicked() {
        finish();
        overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
    }

    class MessageCall implements DataCall<Result<List<MessageData>>> {

        @Override
        public void success(Result<List<MessageData>> data) {
            if (data.getStatus().equals("0000")) {
                messageAdapter.addAll(data.getResult());
                messageAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException a) {

        }
    }


    private class Change implements DataCall<Result> {
        @Override
        public void success(Result data) {
            if (data.getStatus().equals("0000")) {
                MessageData o = (MessageData) data.getArgs()[3];
                o.setStatus(1);
                findUnreadMessageCountPresenter.requestNet(userId,sessionId);
                messageAdapter.notifyItemChanged(position);
            }
        }

        @Override
        public void fail(ApiException a) {

        }
    }

    private class Find implements DataCall<Result> {
        @Override
        public void success(Result data) {
            weudu.setText("("+data.getCount()+"条未读)");
        }

        @Override
        public void fail(ApiException a) {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        changeSysMsgStatusPresenter.unBind();
        messagePresenter.unBind();
        findUnreadMessageCountPresenter.unBind();
    }
}
