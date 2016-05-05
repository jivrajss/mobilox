package module.custom.jugnig.mobilox.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import module.custom.jugnig.mobilox.R;
import module.custom.jugnig.mobilox.custom.GridAutofitLayoutManager;
import module.custom.jugnig.mobilox.custom.NumberedAdapter;

/**
 * Created by JugniG on 05-05-2016.
 */
public class GridLayoutAutoFitActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_fit_recycler_view);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridAutofitLayoutManager(getApplicationContext(),200)); //  this is important line..
        recyclerView.setAdapter(new NumberedAdapter(30));
    }
}
