package lt.blaster.recyclerviewbinders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ListBinderAdapter extends BinderAdapter {
    public static final int POSITION_FIRST = 0;
    private final List<ItemBinder> binderList = new ArrayList<>();
    private final Map<Integer, Integer> typeBinderMap = new ConcurrentHashMap<>();

    @Override
    public final int getItemCount() {
        int itemCount = 0;
        for (ItemBinder binder : binderList) {
            itemCount += binder.getItemCount();
        }
        return itemCount;
    }

    @Override
    public final int getItemViewType(int position) {
        return generateItemViewType(position);
    }

    private Integer generateItemViewType(int position) {
        int itemCount = 0;
        for (int binderPosition = 0; binderPosition < binderList.size(); binderPosition++) {
            ItemBinder binder = binderList.get(binderPosition);
            int binderSize = binder.getItemCount();
            for (int i = 0; i < binderSize; i++) {
                itemCount++;
                int binderViewType = getNormalizedBinderViewType(binderPosition, binder, i);
                if (position == itemCount - 1) {
                    typeBinderMap.put(binderViewType, binderPosition);
                    return binderViewType;
                }
            }
        }
        throw new IllegalArgumentException("No binder assigned to position: " + position);
    }

    @Override
    public final long getItemId(int position) {
        SimpleEntry<ItemBinder, Integer> entry = extractBinderPositionPair(position);
        ItemBinder itemBinder = entry.getKey();
        int binderPosition = entry.getValue();
        return itemBinder == null ? RecyclerView.NO_ID : itemBinder.getItemId(binderPosition);
    }

    @Override
    @SuppressWarnings("unchecked")
    public final <T extends ItemBinder> T getDataBinder(int viewType) {
        return (T) binderList.get(typeBinderMap.get(viewType));
    }

    private int getNormalizedBinderViewType(int binderPosition, ItemBinder binder, int position) {
        int binderViewType = binder.getItemViewType(position);
        if (binderViewType == ItemBinder.DEFAULT_VIEW_TYPE) {
            binderViewType -= binderPosition;
        }
        return binderViewType;
    }

    @SuppressWarnings("unchecked")
    public final <T extends ItemBinder> T getDataBinderAtPosition(int adapterPosition) {
        SimpleEntry<ItemBinder, Integer> entry = extractBinderPositionPair(adapterPosition);
        ItemBinder itemBinder = entry.getKey();
        return itemBinder == null ? null : (T) itemBinder;
    }

    @NonNull
    private SimpleEntry<ItemBinder, Integer> extractBinderPositionPair(int position) {
        int binderItemCount;
        int binderPosition = position;
        ItemBinder itemBinder = null;
        for (int index = 0; index < getBinderList().size(); index++) {
            itemBinder = getBinderList().get(index);
            binderItemCount = itemBinder.getItemCount();
            if (binderPosition - binderItemCount < POSITION_FIRST) {
                break;
            }
            binderPosition -= binderItemCount;
        }
        return new SimpleEntry<>(itemBinder, binderPosition);
    }

    @Override
    public final int getAdapterItemPosition(@NonNull ItemBinder binder, int binderPosition) {
        int viewType = binderList.indexOf(binder);
        if (viewType < POSITION_FIRST) {
            throw new IllegalStateException("Binder does not exist in adapter");
        }

        int position = binderPosition;

        for (int i = 0; i < viewType; i++) {
            position += binderList.get(i).getItemCount();
        }

        if (position < POSITION_FIRST) {
            throw new IllegalStateException("The adapter can not have a " + position + " position");
        }
        return position;
    }

    @Override
    public final int getBinderItemPosition(int adapterPosition) {
        int position = adapterPosition;
        int binderItemCount;
        if (binderList.isEmpty()) {
            throw new IllegalStateException("You're trying to get a binder item position, "
                    + "when there are no binders added");
        } else {
            for (int i = 0; i < binderList.size(); i++) {
                binderItemCount = binderList.get(i).getItemCount();
                if (position - binderItemCount < POSITION_FIRST) {
                    break;
                }
                position -= binderItemCount;
            }
        }
        return position;
    }

    @Override
    public final void notifyBinderItemRangeChanged(@NonNull ItemBinder binder, int positionStart,
                                                   int itemCount) {
        notifyItemRangeChanged(getAdapterItemPosition(binder, positionStart), itemCount);
    }

    @Override
    public final void notifyBinderItemRangeInserted(@NonNull ItemBinder binder, int positionStart,
                                                    int itemCount) {
        notifyItemRangeInserted(getAdapterItemPosition(binder, positionStart), itemCount);
    }

    @Override
    public final void notifyBinderItemRangeRemoved(@NonNull ItemBinder binder, int positionStart,
                                                   int itemCount) {
        notifyItemRangeRemoved(getAdapterItemPosition(binder, positionStart), itemCount);
    }

    @NonNull
    public final List<ItemBinder> getBinderList() {
        return binderList;
    }

    public final void addBinder(@NonNull ItemBinder binder) {
        binderList.add(binder);
    }

    public final void addAllBinders(@NonNull List<ItemBinder> dataSet) {
        binderList.addAll(dataSet);
    }

    public final void addAllBinders(@NonNull ItemBinder... dataSet) {
        binderList.addAll(Arrays.asList(dataSet));
    }
}
