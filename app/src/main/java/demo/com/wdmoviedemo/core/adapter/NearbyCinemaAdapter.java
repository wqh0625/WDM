package demo.com.wdmoviedemo.core.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

import demo.com.wdmoviedemo.bean.NearbyData;
import demo.com.wdmoviedemo.view.cinemaactivity.CinemaDetailActivity;

/**
 * 作者: Wang on 2019/1/25 19:59
 * 寄语：加油！相信自己可以！！！
 */


public class NearbyCinemaAdapter extends RecyclerView.Adapter<NearbyCinemaAdapter.Vh> {
    private List<NearbyData> list;
    private FragmentActivity context;

    public void setList(List<NearbyData> list) {
        this.list = list;
    }

    public NearbyCinemaAdapter(FragmentActivity context) {
        this.list = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.adapter_nearby_item, null);
        return new Vh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Vh vh, int i) {
        final NearbyData nearbyData = list.get(i);
        vh.icon.setImageURI(Uri.parse(nearbyData.getLogo()));
        vh.title.setText(nearbyData.getName());
        vh.location.setText(nearbyData.getAddress());
        vh.size.setText(nearbyData.getDistance() + "km");
        if (nearbyData.getFollowcinema() == 1) {
            // 已关注
            vh.like.setImageResource(R.drawable.icon_collection_selected);
        } else {
            // 未关注
            vh.like.setImageResource(R.drawable.aix);
        }
        vh.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CinemaDetailActivity.class);
                intent.putExtra("CcinemaID", nearbyData.getId());
                intent.putExtra("Caddress", nearbyData.getAddress());
                intent.putExtra("CimageUrl", nearbyData.getLogo());
                intent.putExtra("CcinameName", nearbyData.getName());
                Toast.makeText(context, "cinemaId"+nearbyData.getId(), Toast.LENGTH_SHORT).show();
                context.startActivity(intent);
                context.overridePendingTransition(R.anim.ac_in, R.anim.ac_out);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Vh extends RecyclerView.ViewHolder {

        private final SimpleDraweeView icon;
        private final ImageView like;
        private final TextView location, title, size;

        public Vh(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.a_nearby_image_icon);
            like = itemView.findViewById(R.id.a_nearby_like);
            location = itemView.findViewById(R.id.a_nearby_location);
            title = itemView.findViewById(R.id.a_nearby_title);
            size = itemView.findViewById(R.id.a_nearby_size);
        }
    }
}
