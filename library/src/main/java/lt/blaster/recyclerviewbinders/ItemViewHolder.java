package lt.blaster.recyclerviewbinders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;


public class ItemViewHolder<T> extends RecyclerView.ViewHolder {

    private T item;

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    protected final void bindBase(@NonNull T item) {
        this.item = item;
        bind(item);
    }

    protected void bind(@NonNull T item) {
        //Empty
    }

    @NonNull
    public final T getItem() {
        return item;
    }

}
