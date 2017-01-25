package lt.blaster.recyclerviewbinders.sample;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lt.blaster.recyclerviewbinders.ListBinderAdapter;
import lt.blaster.recyclerviewbinders.SingleItemBinder;

public class MainActivity extends AppCompatActivity implements ItemClickListener<String>,
        SwipeRefreshLayout.OnRefreshListener {
    private final List<String> firstBinderItems = Arrays.asList(
            "binder 1 : item 1", "binder 1 : item 2");
    private final List<String> secondBinderItems = Arrays.asList(
            "binder 2 : item 1", "binder 2 : item 2");
    private final List<String> thirdBinderItems = Arrays.asList(
            "binder 3 : item 1", "binder 3 : item 2",
            "binder 3 : item 3", "binder 3 : item 4");
    private final List<String> fourthBinderItems = Arrays.asList(
            "binder 4 : item 1", "binder 4 : item 2",
            "binder 4 : item 3", "binder 4 : item 4");
    private ListBinderAdapter adapter;
    private StringItemBinder firstBinder;
    private StringItemBinder secondBinder;
    private MultiViewTypeItemBinder thirdBinder;
    private MultiViewTypeItemBinder fourthBinder;
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        refreshLayout.setOnRefreshListener(this);

        adapter = new ListBinderAdapter();

        addBasicBinders(recyclerView, adapter);
        addMultiViewTypeBinders(recyclerView, adapter);
        addMultiViewTypeBinders(recyclerView, adapter);

        recyclerView.setAdapter(adapter);
    }

    private void addBasicBinders(RecyclerView recyclerView, ListBinderAdapter adapter) {
        firstBinder = new StringItemBinder(adapter);
        adapter.addBinder(firstBinder);
        firstBinder.setItems(firstBinderItems);
        firstBinder.setListener(this);

        adapter.addBinder(new SingleItemBinder(recyclerView, R.layout.divider));

        secondBinder = new StringItemBinder(adapter);
        adapter.addBinder(secondBinder);
        secondBinder.setItems(secondBinderItems);
        secondBinder.setListener(this);
    }

    private void addMultiViewTypeBinders(RecyclerView recyclerView, ListBinderAdapter adapter) {
        adapter.addBinder(new SingleItemBinder(recyclerView, R.layout.divider));

        thirdBinder = new MultiViewTypeItemBinder(adapter, this);
        adapter.addBinder(thirdBinder);
        thirdBinder.setItems(new ArrayList<>(thirdBinderItems));

        fourthBinder = new MultiViewTypeItemBinder(adapter, this);
        adapter.addBinder(fourthBinder);
        fourthBinder.setItems(new ArrayList<>(fourthBinderItems));
    }

    @Override
    public void onItemClick(View view, String value, int position) {
        Toast.makeText(this, "View type: " + adapter.getItemViewType(position)
                + ", item: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        firstBinder.removeItems(0, firstBinder.getItemCount());
        secondBinder.removeItems(0, secondBinder.getItemCount());
        thirdBinder.removeItems(0, thirdBinder.getItemCount());
        fourthBinder.removeItems(0, fourthBinder.getItemCount());
        firstBinder.setItems(firstBinderItems);
        secondBinder.setItems(secondBinderItems);
        thirdBinder.setItems(thirdBinderItems);
        fourthBinder.setItems(fourthBinderItems);
        refreshLayout.setRefreshing(false);
    }
}
