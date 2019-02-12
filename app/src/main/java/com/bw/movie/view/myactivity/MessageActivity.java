package com.bw.movie.view.myactivity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bw.movie.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bw.movie.bean.MessageData;
import com.bw.movie.bean.Result;
import com.bw.movie.core.adapter.MessageAdapter;
import com.bw.movie.core.base.BaseActivity;
import com.bw.movie.core.exception.ApiException;
import com.bw.movie.core.interfase.DataCall;
import com.bw.movie.presenter.MessagePresenter;

public class MessageActivity extends BaseActivity {

    @BindView(R.id.message_recy)
    RecyclerView messageRecy;
    @BindView(R.id.img_finnish)
    ImageView imgFinnish;
    private MessageAdapter messageAdapter;
    private int userId;
    private String sessionId;

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
        MessagePresenter messagePresenter = new MessagePresenter(new MessageCall());
        messagePresenter.requestNet(userId, sessionId, 1, 10);
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
                Toast.makeText(MessageActivity.this, "" + data.getMessage(), Toast.LENGTH_SHORT).show();
                messageAdapter.addAll(data.getResult());
                messageAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException a) {
            Toast.makeText(MessageActivity.this, "失败", Toast.LENGTH_SHORT).show();
        }
    }
}