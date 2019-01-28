package demo.com.wdmoviedemo.core.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import demo.com.wdmoviedemo.bean.FindCinemaPageListData;

/**
 * 作者: Wang on 2019/1/26 11:42
 * 寄语：加油！相信自己可以！！！
 */


public class GzCinemaAdapter extends RecyclerView.Adapter<GzCinemaAdapter.Vh> {
    private Context context;
    private List<FindCinemaPageListData> listData;

    public void setListData(List<FindCinemaPageListData> listData) {
        if (listData != null) {
            this.listData.addAll(listData);
        }
    }

    public GzCinemaAdapter(Context context ) {
        this.context = context;
        this.listData = new ArrayList<>();
    }

    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context,R.layout.adapter_cinema_item,null);
        return new Vh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vh vh, int i) {
        FindCinemaPageListData findCinemaPageListData = listData.get(i);
        vh.sim.setImageURI(Uri.parse(findCinemaPageListData.getLogo()));
        vh.dizhi.setText(findCinemaPageListData.getAddress());
        vh.title.setText(findCinemaPageListData.getName());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class Vh extends RecyclerView.ViewHolder{

        private final TextView dizhi,title;
        private final SimpleDraweeView sim;

        public Vh(@NonNull View itemView) {
            super(itemView);
            dizhi = itemView.findViewById(R.id.a_cinema_dizhi);
            title = itemView.findViewById(R.id.a_cinema_title);
            sim = itemView.findViewById(R.id.a_cinema_image_icon);
        }
    }
}
