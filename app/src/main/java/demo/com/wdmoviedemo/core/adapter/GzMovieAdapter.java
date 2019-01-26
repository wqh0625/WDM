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
import java.util.PriorityQueue;

import demo.com.wdmoviedemo.bean.FindMoviePageListData;
import demo.com.wdmoviedemo.core.utils.ToDate;

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
        View view = View.inflate(context, R.layout.adapter_movie_item,null);
        return new Vh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vh vh, int i) {
        FindMoviePageListData findMoviePageListData = listData.get(i);
        vh.image.setImageURI(Uri.parse(findMoviePageListData.getImageurl()));
        String timedate = ToDate.timedate(findMoviePageListData.getReleasetime());
        vh.date.setText(timedate);
        vh.title.setText(findMoviePageListData.getName());
        vh.xq.setText(findMoviePageListData.getSummary());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class Vh extends RecyclerView.ViewHolder {

        private final SimpleDraweeView image;
        private final TextView title,xq,date;


        public Vh(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.a_movie_sim);
            title = itemView.findViewById(R.id.a_movie_title);
            xq = itemView.findViewById(R.id.a_movie_xiangq);
            date = itemView.findViewById(R.id.a_movie_date);
        }
    }
}
