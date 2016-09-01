package lt.blaster.recyclerviewbinders;

import android.support.annotation.NonNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public abstract class MapBinderAdapter extends BinderAdapter {

    public static final int POSITION_FIRST = 0;
    private final ConcurrentMap<Integer, ItemBinder> binderMap = new ConcurrentHashMap<>();

    @Override
    public final int getItemCount() {
        int itemCount = 0;
        for (ItemBinder binder : binderMap.values()) {
            itemCount += binder.getItemCount();
        }
        return itemCount;
    }

    @Override
    public abstract int getItemViewType(int adapterPosition);

    public final int getBinderViewType(@NonNull ItemBinder binder) {
        for (Map.Entry<Integer, ItemBinder> entry : binderMap.entrySet()) {
            if (entry.getValue().equals(binder)) {
                return entry.getKey();
            }
        }
        throw new IllegalArgumentException("Invalid Data Binder");
    }

    @Override
    public final long getItemId(int adapterPosition) {
        int targetViewType = getItemViewType(adapterPosition);
        ItemBinder itemBinder = getDataBinder(targetViewType);
        return itemBinder.getItemId(getBinderItemPosition(adapterPosition));
    }

    public final void putBinder(int type, @NonNull ItemBinder binder) {
        binderMap.put(type, binder);
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public final <T extends ItemBinder> T getDataBinder(int viewType) {
        return (T) binderMap.get(viewType);
    }

    @Override
    public final int getAdapterItemPosition(@NonNull ItemBinder binder, int binderPosition) {
        int position = binderPosition;
        int targetViewType = getBinderViewType(binder);
        int itemCount = getItemCount();
        for (int i = 0; i < itemCount; i++) {
            if (targetViewType == getItemViewType(i)) {
                position--;
                if (position < POSITION_FIRST) {
                    return i;
                }
            }
        }
        return itemCount;
    }

    @Override
    public final int getBinderItemPosition(int adapterPosition) {
        int targetViewType = getItemViewType(adapterPosition);
        int binderPosition = -1;
        for (int i = 0; i <= adapterPosition; i++) {
            if (targetViewType == getItemViewType(i)) {
                binderPosition++;
            }
        }
        if (binderPosition == -1) {
            throw new IllegalArgumentException("Invalid binder item position");
        }
        return binderPosition;
    }

    @Override
    public final void notifyBinderItemRangeChanged(@NonNull ItemBinder binder, int positionStart,
                                                   int itemCount) {
        for (int i = positionStart; i < itemCount; i++) {
            notifyItemChanged(getAdapterItemPosition(binder, i));
        }
    }

    @Override
    public final void notifyBinderItemRangeInserted(@NonNull ItemBinder binder, int positionStart,
                                                    int itemCount) {
        for (int i = positionStart; i < itemCount; i++) {
            notifyItemInserted(getAdapterItemPosition(binder, i));
        }
    }

    @Override
    public final void notifyBinderItemRangeRemoved(@NonNull ItemBinder binder, int positionStart,
                                                   int itemCount) {
        for (int i = positionStart; i < itemCount; i++) {
            notifyItemRemoved(getAdapterItemPosition(binder, i));
        }
    }

    @NonNull
    public final Map<Integer, ItemBinder> getBinderMap() {
        return binderMap;
    }
}
