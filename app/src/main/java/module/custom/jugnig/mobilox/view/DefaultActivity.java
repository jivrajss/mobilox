package module.custom.jugnig.mobilox.view;

/**
 * Created by JugniG on 05-05-2016.
 */

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import module.custom.jugnig.mobilox.R;
import module.custom.jugnig.mobilox.custom.BaseActivity;
import module.custom.jugnig.mobilox.data.PreferenceInnerItem;
import module.custom.jugnig.mobilox.data.SearchModel;
import module.custom.jugnig.mobilox.data.SearchRequestBody;
import module.custom.jugnig.mobilox.retrofit.PreferencesInterface;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DefaultActivity extends BaseActivity implements Callback<SearchModel> {

    private MaterialSearchView searchView;
    private Call<SearchModel> modelCall;
    private SearchRequestBody mObject;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        searchView = (MaterialSearchView) findViewById(R.id.search_view);
        searchView.setVoiceSearch(false);
        searchView.setCursorDrawable(R.drawable.custom_cursor);
        searchView.setEllipsize(true);
        searchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
        searchView.setOnItemClickListener(mItemClickListener);
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Snackbar.make(findViewById(R.id.container), "Query: " + query, Snackbar.LENGTH_LONG)
                        .show();
                Log.d(TAG, "Submitted Text---" + query);
                if (query != null && query.length() > 3) {
                    Log.d(TAG, "New Text---" + query);
                    callApi(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                if (newText != null && newText.length() > 3) {
                    Log.d(TAG, "New Text---" + newText);
                    callApi(newText);
                }
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic'
                Log.d(TAG, "search view Shown");
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
                Log.d(TAG, "search view Closed!");
            }
        });
        setRetrofitObject();
    }

    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d(TAG, "position----" + position);
            Toast.makeText(getApplicationContext(), "Item--" + itemList.get(position).getTitle()
                    + "--Type--" + itemList.get(position).getType(), Toast.LENGTH_SHORT).show();
        }
    };

    private void callApi(String newText) {
        mObject.setKeyword(newText);
        Log.d(TAG, "Object---" + mGson.toJson(mObject));
        if (modelCall != null && modelCall.isExecuted()) {
            Log.e(TAG, "already running--");
            modelCall.cancel();
        } else
            Log.d(TAG, "fist Instance of modelcall");
        modelCall = retrofit.create(PreferencesInterface.class).getSearchResults(mGson.toJson(mObject));
        modelCall.enqueue(DefaultActivity.this);
    }

    @Override
    protected void setRetrofitObject() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
// set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient mOkHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request request = original.newBuilder()
                                .header("content-type", "application/x-www-form-urlencoded")
                                .method(original.method(), original.body())
                                .build();

                        return chain.proceed(request);
                    }
                })
                .addInterceptor(logging).
                        build();
        retrofit = new Retrofit.Builder().client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(PreferencesInterface.BASE_URL).build();
        mObject = new SearchRequestBody();
        mObject.setReq("search");
//        mObject.setCategory("22");

//        retrofit.create(PreferencesInterface.class).getPreferencesList(mObject).enqueue(this);
    }

    @Override
    protected void initializeToolBar() {

    }

    @Override
    protected void setTAG() {
        TAG = this.getClass().getSimpleName();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MaterialSearchView.REQUEST_VOICE && resultCode == RESULT_OK) {
            ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            if (matches != null && matches.size() > 0) {
                String searchWrd = matches.get(0);
                if (!TextUtils.isEmpty(searchWrd)) {
                    searchView.setQuery(searchWrd, false);
                }
            }

            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private List<PreferenceInnerItem> itemList;
    private List<String> titleList;

    @Override
    public void onResponse(Call<SearchModel> call, Response<SearchModel> response) {
        Log.d(TAG, "onresponse--" + response.code());
        if (response.code() == 200) {
            Log.d(TAG, "onresponse--" + mGson.toJson(response.body()));
            if (itemList != null) {
                itemList.clear();
            } else
                itemList = new ArrayList<>();
            itemList.addAll(response.body().getData());
            if (titleList != null) {
                titleList.clear();
            } else
                titleList = new ArrayList<>();
            for (PreferenceInnerItem mitem : itemList) {
                titleList.add(mitem.getTitle());
            }
            if (searchView != null) {
                searchView.setSuggestions(titleList.toArray(new String[0]));
                searchView.setOnItemClickListener(mItemClickListener);
            } else
                Log.e(TAG, "searchView is nulll...not possible with activity in stack!");
        }
    }

    @Override
    public void onFailure(Call<SearchModel> call, Throwable t) {
        t.printStackTrace();
    }
}
