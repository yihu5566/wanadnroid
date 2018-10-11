package test.juyoufuli.com.myapplication.mvp.ui.searchview.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

import io.reactivex.Observable;
import test.juyoufuli.com.myapplication.R;
import test.juyoufuli.com.myapplication.mvp.entity.Datas;

/**
 * Author : ludf
 * Created Time : 2018-09-27  14:57
 * Description:
 */
public class SearchAdapter extends BaseRecyclerViewAdapter<Datas> {

    public SearchAdapter(Context context) {
        super(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = mLayoutInflater.inflate(R.layout.article_item, null);
        return new SearchItemHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SearchItemHolder mHolder = (SearchItemHolder) holder;
        Datas data = getList().get(position);
        mHolder.mName.setText(data.getAuthor());
        mHolder.mDesc.setText(data.getTitle());
        mHolder.tvTime.setText(data.getNiceDate());

        mHolder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnItemClickListener.onItemClick(R.id.root, position, data);
            }
        });

    }

}
