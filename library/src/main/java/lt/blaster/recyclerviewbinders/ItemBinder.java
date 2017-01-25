package lt.blaster.recyclerviewbinders;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

public interface ItemBinder<T, V extends ItemViewHolder<T>> {
    int DEFAULT_VIEW_TYPE = -1;

    @NonNull
    V newViewHolder(@NonNull ViewGroup parent, int viewType);

    void bindViewHolder(@NonNull V holder, int position);

    int getItemViewType(int position);

    long getItemId(int position);

    int getItemCount();
}
