package lt.blaster.recyclerviewbinders.sample;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lt.blaster.recyclerviewbinders.BinderAdapter;
import lt.blaster.recyclerviewbinders.ListItemBinder;

public class StringItemBinder extends ListItemBinder<String, StringItemViewHolder> {
    private ItemClickListener<String> listener;

    public StringItemBinder(@NonNull BinderAdapter adapter) {
        super(adapter);
    }

    @NonNull
    @Override
    public StringItemViewHolder newViewHolder(@NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list, parent, false);
        return new StringItemViewHolder(view);
    }

    @Override
    public void bindViewHolder(@NonNull StringItemViewHolder holder, int position) {
        super.bindViewHolder(holder, position);
        holder.setListener(listener);
    }

    public void setListener(@Nullable ItemClickListener<String> listener) {
        this.listener = listener;
    }
}
