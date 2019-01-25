package demo.com.wdmoviedemo.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bw.movie.R;

import java.util.List;

import demo.com.wdmoviedemo.bean.CarouselData;
import demo.com.wdmoviedemo.bean.Result;
import demo.com.wdmoviedemo.core.adapter.CinemaxAdapters;
import demo.com.wdmoviedemo.core.exception.ApiException;
import demo.com.wdmoviedemo.core.interfase.DataCall;
import demo.com.wdmoviedemo.presenter.IsHitPresenter;
import demo.com.wdmoviedemo.view.Film_Details_Activity;

public class IsHitFragment extends Fragment {
    private RecyclerView ishitRecy;
    private CinemaxAdapters cinemaxAdapter;
    private IsHitPresenter isHitPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_is_hit, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        cinemaxAdapter = new CinemaxAdapters(getActivity(),CinemaxAdapters.ISHIT_TYPE);
        ishitRecy.setAdapter(cinemaxAdapter);
        isHitPresenter = new IsHitPresenter(new IsHitCall());
        ishitRecy.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        isHitPresenter.requestNet(1,10);
        cinemaxAdapter.setOnMovieItemClickListener(new CinemaxAdapters.OnCinemaxItemClickListener() {
            @Override
            public void onMovieClick(int position) {
                Intent intent = new Intent(getActivity(),Film_Details_Activity.class);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });
    }
    class IsHitCall implements DataCall<Result<List<CarouselData>>> {

        @Override
        public void success(Result<List<CarouselData>> data) {
            if (data.getStatus().equals("0000")){
                cinemaxAdapter.addAll(data.getResult());
                cinemaxAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void fail(ApiException a) {

        }
    }

    private void initView(View view) {
        ishitRecy = (RecyclerView) view.findViewById(R.id.ishit_recy);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isHitPresenter.unBind();
    }
}
