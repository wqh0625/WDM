package demo.com.wdmoviedemo.view.myactivity.kffragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bw.movie.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import demo.com.wdmoviedemo.core.base.BaseFragment;

/**
 * 作者: Wang on 2019/1/25 14:15
 * 寄语：加油！相信自己可以！！！
 */


public class DfkTicketFragment extends Fragment {

    private Unbinder bind;

    @BindView(R.id.dfk_rec)
    RecyclerView rec;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dfkticket, container, false);
        bind = ButterKnife.bind(this, view);

        rec.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }
}
