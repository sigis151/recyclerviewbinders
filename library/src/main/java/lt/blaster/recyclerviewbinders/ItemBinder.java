package lt.blaster.recyclerviewbinders;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

public interface ItemBinder<T, V extends ItemViewHolder<T>> {

    @NonNull
    V newViewHolder(@NonNull ViewGroup parent);

    void bindViewHolder(@NonNull V holder, int position);

    long getItemId(int position);

    int getItemCount();
}
