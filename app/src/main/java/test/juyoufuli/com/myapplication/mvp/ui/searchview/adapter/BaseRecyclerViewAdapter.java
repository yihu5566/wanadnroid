package test.juyoufuli.com.myapplication.mvp.ui.searchview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter {
    protected List<T> mList = new ArrayList<>();
    protected Context mContext;
    protected LayoutInflater mLayoutInflater;
    protected OnItemClickListener<T> mOnItemClickListener;

    public BaseRecyclerViewAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setList(List<T> list) {
        mList = list;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener<T> mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void clearList() {
        mList = new ArrayList<T>();
        notifyDataSetChanged();
    }

    public void addList(List<T> list) {
        if (list == null) return;
        if (mList == null) mList = new ArrayList<T>();
        for (T t : list) {
            mList.add(t);
        }
        notifyDataSetChanged();
    }

    public void appendData(List list) {
        if (list != null && !list.isEmpty()) {
            this.mList.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void removeItem(T object) {
        if (mList == null || object == null)
            return;
        mList.remove(object);
        notifyDataSetChanged();
    }

    public void removeItem(int pos) {
        if (mList == null || pos > mList.size())
            return;
        mList.remove(pos);
        notifyDataSetChanged();
    }

    public void addFirst(T item) {
        if (mList == null) mList = new ArrayList<>();
        mList.add(0, item);
        notifyDataSetChanged();
    }

    public void addAt(int index, T item) {
        if (mList == null) {
            mList = new ArrayList<>();
        }
        mList.add(index, item);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public T getItem(int position) {
        if (mList == null) {
            return null;
        }
        if (position < 0 || position >= mList.size()) {
            return null;
        }
        return mList.get(position);
//        return mList == null ? null : mList.get(position);
    }

    public interface OnItemClickListener<T> {
        void onItemClick(int clickId, int position, T item);
    }

    public List<T> getList() {
        return mList;
    }
}
