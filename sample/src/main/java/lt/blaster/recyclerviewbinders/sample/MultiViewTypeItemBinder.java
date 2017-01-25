package lt.blaster.recyclerviewbinders.sample;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lt.blaster.recyclerviewbinders.BinderAdapter;
import lt.blaster.recyclerviewbinders.ItemViewHolder;
import lt.blaster.recyclerviewbinders.ListItemBinder;

public class MultiViewTypeItemBinder extends ListItemBinder<String, ItemViewHolder<String>> {
    private static final int VIEW_TYPE_STRING = 0;
    private static final int VIEW_TYPE_CUSTOM_STRING = 1;
    private final ItemClickListener<String> listener;

    public MultiViewTypeItemBinder(@NonNull BinderAdapter adapter,
                                   @NonNull ItemClickListener<String> listener) {
        super(adapter);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder<String> newViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if (viewType == VIEW_TYPE_STRING) {
            View view = layoutInflater.inflate(R.layout.item_list, parent, false);
            return new StringItemViewHolder(view);
        } else {
            View view = layoutInflater.inflate(R.layout.item_list_custom, parent, false);
            return new CustomStringItemViewHolder(view, listener);
        }
    }

    @Override
    public void bindViewHolder(@NonNull ItemViewHolder<String> holder, int position) {
        super.bindViewHolder(holder, position);
        if (holder instanceof StringItemViewHolder) {
            ((StringItemViewHolder) holder).setListener(listener);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2 == 0 ? VIEW_TYPE_STRING : VIEW_TYPE_CUSTOM_STRING;
    }
}
