package lt.blaster.recyclerviewbinders.sample;

import android.view.View;

public interface ItemClickListener<T> {
    void onItemClick(View view, T value, int position);
}