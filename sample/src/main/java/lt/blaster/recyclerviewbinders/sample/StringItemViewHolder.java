package lt.blaster.recyclerviewbinders.sample;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import lt.blaster.recyclerviewbinders.ItemViewHolder;

public class StringItemViewHolder extends ItemViewHolder<String> {
    private ItemClickListener<String> listener;

    public StringItemViewHolder(View view) {
        super(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClick(view, getItem(), getAdapterPosition());
                }
            }
        });
    }

    @Override
    protected void bind(@NonNull final String item) {
        ((TextView) itemView).setText(item);
    }

    public void setListener(@Nullable ItemClickListener<String> listener) {
        this.listener = listener;
    }
}
