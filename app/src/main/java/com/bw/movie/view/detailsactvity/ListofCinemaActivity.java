package com.bw.movie.view.detailsactvity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.bw.movie.bean.ListofCinemaData;
import com.bw.movie.bean.Result;
import com.bw.movie.core.adapter.ListofCinemaAdapter;
import com.bw.movie.core.base.BaseActivity;
import com.bw.movie.core.exception.ApiException;
import com.bw.movie.core.interfase.DataCall;
import com.bw.movie.presenter.ListofCinemaPresenter;

public class ListofCinemaActivity extends BaseActivity {

    @BindView(R.id.list_txt_name)
    TextView listTxtName;
    @BindView(R.id.list_recy_item)
    RecyclerView listRecyItem;
    @BindView(R.id.top)
    LinearLayout top;
    @BindView(R.id.list_image_back)
    ImageView listImageBack;
    private int position;
    private ListofCinemaAdapter listofCinemaAdapter;
    private ListofCinemaPresenter listofCinemaPresenter;
    private String names;
    private String movieTypes;
    private String director;
    private String duration;
    private String placeOrigin;
    private String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the__film__details);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        position = intent.getExtras().getInt("position");
        names = intent.getExtras().getString("name");
        movieTypes = intent.getExtras().getString("movieTypes");
        imageUrl = intent.getExtras().getString("imageUrl");
        director = intent.getExtras().getString("director");
        duration = intent.getExtras().getString("duration");
        placeOrigin = intent.getExtras().getString("placeOrigin");
        initData();
    }

    private void initData() {
        listTxtName.setText(names);
        listofCinemaAdapter = new ListofCinemaAdapter(this);
        listRecyItem.setAdapter(listofCinemaAdapter);
        listRecyItem.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        listofCinemaPresenter = new ListofCinemaPresenter(new ListofCinemaCall());
        listofCinemaPresenter.requestNet(position);
        //接口回调传值
        listofCinemaAdapter.setOnListofCinemaListener(new ListofCinemaAdapter.OnListofCinemaListener() {
            @Override
            public void onList(int id, String name,String address) {
                Intent intent = new Intent(ListofCinemaActivity.this,TicketDetailsActivity.class);
                intent.putExtra("position",position);
                intent.putExtra("id",id);
                intent.putExtra("name",name);
                intent.putExtra("names",names);
                intent.putExtra("address",address);
                intent.putExtra("imageUrl",imageUrl);
                intent.putExtra("movieTypes",movieTypes);
                intent.putExtra("director",director);
                intent.putExtra("duration",duration);
                intent.putExtra("placeOrigin",placeOrigin);
                startActivity(intent);
            }
        });
    }
    class ListofCinemaCall implements DataCall<Result<List<ListofCinemaData>>>{

        @Override
        public void success(Result<List<ListofCinemaData>> data) {
            if (data.getStatus().equals("0000")){
                listofCinemaAdapter.addAll(data.getResult());
                listofCinemaAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException a) {
            Toast.makeText(ListofCinemaActivity.this, "请求失败", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.list_image_back)
    public void onViewClicked() {
        finish();
    }
}
