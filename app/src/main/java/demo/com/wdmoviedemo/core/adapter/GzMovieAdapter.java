package demo.com.wdmoviedemo.core.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import demo.com.wdmoviedemo.bean.FindMoviePageListData;

/**
 * 作者: Wang on 2019/1/25 14:40
 * 寄语：加油！相信自己可以！！！
 */


public class GzMovieAdapter extends RecyclerView.Adapter<GzMovieAdapter.Vh> {
    private Context context;
    private List<FindMoviePageListData> listData;

    public void setListData(List<FindMoviePageListData> listData) {
        this.listData = listData;
    }

    public GzMovieAdapter(Context context ) {
        this.context = context;
        this.listData = new ArrayList<>();
    }

    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull Vh vh, int i) {

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class Vh extends RecyclerView.ViewHolder {
        public Vh(@NonNull View itemView) {
            super(itemView);
        }
    }
}
