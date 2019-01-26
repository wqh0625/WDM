package demo.com.wdmoviedemo.core.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bw.movie.R;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;
import demo.com.wdmoviedemo.bean.ShortFilmListBean;

/**
 * date: 2019/1/26.
 * Created 王思敏
 * function:
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
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
    viewHolder.predictionJzplayer.setUp(list.get(i).getVideoUrl(),JZVideoPlayer.SCREEN_WINDOW_NORMAL);
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
