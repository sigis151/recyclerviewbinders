package lt.blaster.recyclerviewbinders;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SingleItemBinder<T> implements ItemBinder<T, ItemViewHolder<T>> {

    private final View view;

    public SingleItemBinder(@NonNull ViewGroup parent, @LayoutRes int layoutId) {
        super();
        view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
    }

    public final View getView() {
        return view;
    }

    @NonNull
    @Override
    public final ItemViewHolder<T> newViewHolder(@NonNull ViewGroup parent) {
        return new ItemViewHolder<>(view);
    }

    @Override
    public final void bindViewHolder(@NonNull ItemViewHolder<T> holder, int position) {
        bindView(view);
    }

    protected void bindView(View view) {
        //Empty
    }

    @Override
    public final int getItemCount() {
        return 1;
    }

    @Override
    public final long getItemId(int position) {
        return getItemId();
    }

    protected long getItemId() {
        return RecyclerView.NO_ID;
    }
}
