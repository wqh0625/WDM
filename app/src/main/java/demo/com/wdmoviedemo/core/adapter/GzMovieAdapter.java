package demo.com.wdmoviedemo.core.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * 作者: Wang on 2019/1/25 14:40
 * 寄语：加油！相信自己可以！！！
 */


public class GzMovieAdapter extends RecyclerView.Adapter<GzMovieAdapter.Vh> {
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
        return 0;
    }

    public class Vh extends RecyclerView.ViewHolder {
        public Vh(@NonNull View itemView) {
            super(itemView);
        }
    }
}
