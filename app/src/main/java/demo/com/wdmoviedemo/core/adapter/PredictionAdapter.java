package demo.com.wdmoviedemo.core.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bw.movie.R;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import demo.com.wdmoviedemo.bean.ShortFilmListBean;

/**
 * date: 2019/1/26.
 * Created 王思敏
 * function:预告片
 */
public class PredictionAdapter extends RecyclerView.Adapter<PredictionAdapter.ViewHolder> {
    private Context context;
    private List<ShortFilmListBean> list;

    public PredictionAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.prediction_recy, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder myHolder, int i) {
        myHolder.predictionJzplayer.setUp(list.get(i).getVideoUrl(),JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,"预告片");
        String imageUrl = list.get(i).getImageUrl();
        try {
            URL url = new URL(imageUrl);
            Glide.with(context).load(url).into(myHolder.predictionJzplayer.thumbImageView);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(List<ShortFilmListBean> shortFilmList) {
        if (shortFilmList !=null){
            list.addAll(shortFilmList);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final JZVideoPlayerStandard predictionJzplayer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            predictionJzplayer = itemView.findViewById(R.id.prediction_jzplayer);
        }
    }
}
