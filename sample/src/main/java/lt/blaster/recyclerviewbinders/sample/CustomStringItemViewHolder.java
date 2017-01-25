package lt.blaster.recyclerviewbinders.sample;

import android.view.View;

import lt.blaster.recyclerviewbinders.ItemViewHolder;

public class CustomStringItemViewHolder extends ItemViewHolder<String>
        implements View.OnClickListener {
    private final ItemClickListener<String> listener;

    public CustomStringItemViewHolder(View itemView, ItemClickListener<String> listener) {
        super(itemView);
        this.listener = listener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onItemClick(view, getItem(), getAdapterPosition());
        }
    }
}
