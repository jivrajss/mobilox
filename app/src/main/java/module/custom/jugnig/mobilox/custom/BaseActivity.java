package module.custom.jugnig.mobilox.custom;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DateFormat;

/**
 * Created by JugniG on 05-05-2016.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Bundle bundle;
    protected Gson mGson;
    protected String TAG;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getIntent().getExtras();
        if (bundle == null)
            bundle = new Bundle();
        mGson = new GsonBuilder()
                .enableComplexMapKeySerialization()
                .serializeNulls()
                .setDateFormat(DateFormat.LONG)
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setPrettyPrinting()
                .setVersion(1.0)
                .create();
        setTAG();
    }

    protected abstract void setRetrofitObject();

    protected abstract void initializeToolBar();

    protected abstract void setTAG();
}
