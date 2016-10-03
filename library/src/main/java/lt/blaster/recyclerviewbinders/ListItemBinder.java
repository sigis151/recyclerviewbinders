package lt.blaster.recyclerviewbinders;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class ListItemBinder<T, V extends ItemViewHolder<T>>
        implements ItemBinder<T, V> {
    protected final List<T> items = new ArrayList<>();
    private final BinderAdapter adapter;

    public ListItemBinder(@NonNull BinderAdapter adapter) {
        super();
        this.adapter = adapter;
    }

    public void bindViewHolder(@NonNull V holder, int position) {
        holder.bindBase(getItem(position));
    }

    @Override
    public long getItemId(int position) {
        return RecyclerView.NO_ID;
    }

    public final int getItemCount() {
        return items.size();
    }

    public final T getItem(int position) {
        return items.get(position);
    }

    @NonNull
    public final List<T> getItemList() {
        return new ArrayList<>(items);
    }

    public final void setItems(@NonNull List<T> list) {
        items.clear();
        items.addAll(list);
        adapter.notifyDataSetChanged();
    }

    public final void addItem(int position, T item) {
        items.add(position, item);
        adapter.notifyBinderItemInserted(this, position);
    }

    public final void addItems(int positionStart, @NonNull List<T> items) {
        this.items.addAll(positionStart, items);
        adapter.notifyBinderItemRangeInserted(this, positionStart, items.size());
    }

    public final void moveItem(int fromPosition, int toPosition) {
        Collections.swap(items, fromPosition, toPosition);
        adapter.notifyBinderItemMoved(this, fromPosition, toPosition);
    }

    public final void updateItem(int position, @Nullable T item) {
        items.remove(position);
        items.add(position, item);
        adapter.notifyBinderItemChanged(this, position);
    }

    public final void removeItem(int position) {
        items.remove(position);
        adapter.notifyBinderItemRemoved(this, position);
    }

    public final void removeItems(int positionStart, int itemCount) {
        for (int i = positionStart; i < positionStart + itemCount; i++) {
            items.remove(positionStart);
        }
        adapter.notifyBinderItemRangeRemoved(this, positionStart, itemCount);
    }
}
