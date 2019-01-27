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

import demo.com.wdmoviedemo.bean.ListofCinemaData;

/**
 * date: 2019/1/26.
 * Created 王思敏
 * function:电影相关影院
 */
public class ListofCinemaAdapter extends RecyclerView.Adapter<ListofCinemaAdapter.ViewHolder> {
    private Context context;
    private List<ListofCinemaData> list;

    public ListofCinemaAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }
    //接口回调
    public interface OnListofCinemaListener{
        void onList(int id,String name,String address);
    }

    public OnListofCinemaListener mOnListofCinemaListener;

    public void setOnListofCinemaListener(OnListofCinemaListener onListofCinemaListener){
        mOnListofCinemaListener = onListofCinemaListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.list_recy, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.listRecySdvImage.setImageURI(Uri.parse(list.get(i).getLogo()));
        viewHolder.listRecyTxtName.setText(list.get(i).getName());
        viewHolder.listRecyTxtAddress.setText(list.get(i).getAddress());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = list.get(i).getId();
                String name = list.get(i).getName();
                String address = list.get(i).getAddress();
                if (mOnListofCinemaListener !=null){
                    mOnListofCinemaListener.onList(id,name,address);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addAll(List<ListofCinemaData> result) {
        if (result !=null){
            list.addAll(result);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final SimpleDraweeView listRecySdvImage;
        private final TextView listRecyTxtName;
        private final TextView listRecyTxtAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            listRecySdvImage = itemView.findViewById(R.id.list_recy_sdv_image);
            listRecyTxtName = itemView.findViewById(R.id.list_recy_txt_name);
            listRecyTxtAddress = itemView.findViewById(R.id.list_recy_txt_address);
        }
    }
}
