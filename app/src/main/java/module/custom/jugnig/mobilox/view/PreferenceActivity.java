package module.custom.jugnig.mobilox.view;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import module.custom.jugnig.mobilox.R;
import module.custom.jugnig.mobilox.adapter.MyPagerAdapter;
import module.custom.jugnig.mobilox.custom.BaseActivity;
import module.custom.jugnig.mobilox.custom.CustomViewPager;
import module.custom.jugnig.mobilox.custom.LoggingInterceptor;
import module.custom.jugnig.mobilox.data.PreferenceModel;
import module.custom.jugnig.mobilox.data.RequestTypeObject;
import module.custom.jugnig.mobilox.retrofit.PreferencesInterface;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PreferenceActivity extends BaseActivity implements View.OnClickListener, Callback<PreferenceModel> {
    private CustomViewPager mPager;
    private TextView mButtonOne, mButtonTwo;
    private MyPagerAdapter mAdapter;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);
        mPager = (CustomViewPager) findViewById(R.id.view_pager);
        mAdapter = new MyPagerAdapter(getSupportFragmentManager(), 2, bundle);
        initializeToolBar();
        mPager.setAdapter(mAdapter);
        mButtonOne = (TextView) findViewById(R.id.set_preference);
        mButtonTwo = (TextView) findViewById(R.id.more_preference);
        mButtonOne.setOnClickListener(this);
        mButtonTwo.setOnClickListener(this);
        mPager.addOnPageChangeListener(mChangeListener);
        mPager.setCurrentItem(0);
        setToolBar("Select Shows", null);
        setRetrofitObject();
    }

    @Override
    protected void setRetrofitObject() {
//
        OkHttpClient mOkHttpClient = new OkHttpClient.Builder().addInterceptor(new LoggingInterceptor()).
                build();
        retrofit = new Retrofit.Builder().client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(PreferencesInterface.BASE_URL).build();
        RequestTypeObject mObject = new RequestTypeObject();
        mObject.setReq("get_preference_list");
        mObject.setUser_id("1");
        Log.d(TAG, mGson.toJson(mObject));
        retrofit.create(PreferencesInterface.class).getPreferencesList(mObject).enqueue(this);
    }


    private void setToolBar(String title, String subtitle) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setSubtitle(subtitle);
            getSupportActionBar().setTitle(title);
        }

    }


    protected void initializeToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mPager != null) {
                        if (mPager.getCurrentItem() == 0)
                            onBackPressed();
                        else
                            mPager.setCurrentItem(0);
                    } else {
                        onBackPressed();
                    }
                }
            });
//            if (getSupportActionBar() != null) {
//                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//                getSupportActionBar().setDisplayShowHomeEnabled(true);
//            }
        }
    }


    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.d(TAG, "position----" + position);
            Toast.makeText(getApplicationContext(), "Item--" + position
                    , Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    protected void setTAG() {
        TAG = this.getClass().getSimpleName();
    }

    private ViewPager.OnPageChangeListener mChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            Log.d(TAG, "Into OnPageSelected--" + position);
            if (position == 0) {
                setToolBar("Select Shows", null);
                mButtonOne.setText(getResources().getString(R.string.text_set_preference));
                mButtonTwo.setText(getResources().getString(R.string.text_more_preference));
            } else {
                setToolBar(getResources().getString(R.string.text_set_preference), null);
                mButtonOne.setText(getResources().getString(R.string.text_reset));
                mButtonTwo.setText(getResources().getString(R.string.text_set));
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.set_preference:
                break;
            case R.id.more_preference:
                if (mPager.getCurrentItem() == 0) {
                    Log.d(TAG, "onclick If");
                    mPager.setCurrentItem(1);
                } else {
                    Log.d(TAG, "onclick Else--" + mPager.getCurrentItem());
                    /// do something here to call SetPreference Api
                }
                break;
        }
    }

    @Override
    public void onResponse(Call<PreferenceModel> call, Response<PreferenceModel> response) {
        Log.d(TAG, "Code---" + response.code());
        if (response.code() == 200) {
            Log.d(TAG, "body---" + response.body().getResult());
            Log.d(TAG, "response--" + mGson.toJson(response.body()));
        }
    }

    @Override
    public void onFailure(Call<PreferenceModel> call, Throwable t) {
        t.printStackTrace();
    }

}
