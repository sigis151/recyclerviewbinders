package lt.blaster.recyclerviewbinders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

public abstract class BinderAdapter extends RecyclerView.Adapter<ItemViewHolder> {

    public BinderAdapter() {
        super();
    }

    @NonNull
    @Override
    public final ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return getDataBinder(viewType).newViewHolder(parent);
    }

    @Override
    @SuppressWarnings("unchecked")
    public final void onBindViewHolder(@NonNull ItemViewHolder viewHolder, int adapterPosition) {
        int binderPosition = getBinderItemPosition(adapterPosition);
        getDataBinder(viewHolder.getItemViewType()).bindViewHolder(viewHolder, binderPosition);
    }

    @Override
    public abstract int getItemCount();

    @Override
    public abstract int getItemViewType(int adapterPosition);

    public abstract <T extends ItemBinder> T getDataBinder(int viewType);

    public abstract int getAdapterItemPosition(@NonNull ItemBinder binder, int binderPosition);

    public abstract int getBinderItemPosition(int adapterPosition);

    public final void notifyBinderItemChanged(@NonNull ItemBinder binder, int binderPosition) {
        notifyItemChanged(getAdapterItemPosition(binder, binderPosition));
    }

    public abstract void notifyBinderItemRangeChanged(@NonNull ItemBinder binder, int positionStart,
                                                      int itemCount);

    public final void notifyBinderItemInserted(@NonNull ItemBinder binder, int binderPosition) {
        notifyItemInserted(getAdapterItemPosition(binder, binderPosition));
    }

    public final void notifyBinderItemMoved(@NonNull ItemBinder binder, int fromPosition,
                                            int toPosition) {
        notifyItemMoved(getAdapterItemPosition(binder, fromPosition),
                getAdapterItemPosition(binder, toPosition));
    }

    public abstract void notifyBinderItemRangeInserted(@NonNull ItemBinder binder,
                                                       int positionStart, int itemCount);

    public final void notifyBinderItemRemoved(@NonNull ItemBinder binder, int binderPosition) {
        notifyItemRemoved(getAdapterItemPosition(binder, binderPosition));
    }

    public abstract void notifyBinderItemRangeRemoved(@NonNull ItemBinder binder, int positionStart,
                                                      int itemCount);

}
