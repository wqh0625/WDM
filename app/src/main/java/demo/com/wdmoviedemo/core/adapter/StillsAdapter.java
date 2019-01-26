package demo.com.wdmoviedemo.core.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import demo.com.wdmoviedemo.bean.FilmDetailsData;

/**
 * date: 2019/1/26.
 * Created 王思敏
 * function:剧照
 */
public class StillsAdapter extends RecyclerView.Adapter<StillsAdapter.ViewHolder> {
    private Context context;
    private List<String> list;

    public StillsAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.stills_recy, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        Glide.with(context).load(list.get(i)).into(viewHolder.sdvImage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(List<String> posterList) {
        if (posterList !=null){
            list.addAll(posterList);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView sdvImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sdvImage = itemView.findViewById(R.id.stills_sdv_image);

        }
    }
}
