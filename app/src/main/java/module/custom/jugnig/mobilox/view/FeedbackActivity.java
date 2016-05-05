package module.custom.jugnig.mobilox.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;

import module.custom.jugnig.mobilox.R;
import module.custom.jugnig.mobilox.custom.BaseActivity;
import module.custom.jugnig.mobilox.data.FeedBackModel;
import module.custom.jugnig.mobilox.retrofit.PreferencesInterface;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by JugniG on 05-05-2016.
 */
public class FeedbackActivity extends BaseActivity implements View.OnClickListener, Callback<ResponseBody>, AdapterView.OnItemSelectedListener {


    EditText name, email, phone, comment;
    TextView submit;
    Spinner country, feedback_type;
    private Retrofit retrofit;
    private FeedBackModel mObject;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        setRetrofitObject();
        name = (EditText) findViewById(R.id.name_view);
        email = (EditText) findViewById(R.id.email_view);
        phone = (EditText) findViewById(R.id.phone_view);
        submit = (TextView) findViewById(R.id.submit);
        submit.setOnClickListener(this);
        comment = (EditText) findViewById(R.id.comment_view);
        country = (Spinner) findViewById(R.id.country_spinner);
        feedback_type = (Spinner) findViewById(R.id.feedback_type_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.country_array, R.layout.spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        country.setAdapter(adapter);

        country.setTag("country");
        country.setOnItemSelectedListener(this);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> feedback_adapter = ArrayAdapter.createFromResource(this,
                R.array.feedback_type_array, R.layout.spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        feedback_type.setAdapter(feedback_adapter);
        feedback_type.setTag("feedback");
        feedback_type.setOnItemSelectedListener(this);

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
        mObject = new FeedBackModel();
        mObject.setReq("feedback");
    }

    @Override
    protected void initializeToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBackPressed();
                }
            });
        }
    }

    @Override
    protected void setTAG() {
        TAG = this.getClass().getSimpleName();
    }

    @Override
    public void onClick(View v) {
        mObject.setFull_name(name.getText().toString());
        mObject.setEmail(email.getText().toString());
        mObject.setContact(phone.getText().toString());
        mObject.setContent(comment.getText().toString());
        mObject.setOs("android");
        Log.d(TAG, "setting feedback--" + mGson.toJson(mObject));
        retrofit.create(PreferencesInterface.class).postFeedBack(mGson.toJson(mObject)).enqueue(this);
    }

    @Override
    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
        Log.d(TAG, "response code--" + response.code());
    }

    @Override
    public void onFailure(Call<ResponseBody> call, Throwable t) {
        t.printStackTrace();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "onitemselected---" + position + parent.getTag().toString() + "----" + parent.getItemAtPosition(position).toString());
        if (parent.getTag() != null && parent.getTag().toString().equalsIgnoreCase("country")) {
            if (mObject != null)
                mObject.setCountry(parent.getItemAtPosition(position).toString());
        } else if (parent.getTag() != null && parent.getTag().toString().equalsIgnoreCase("feedback")) {
            if (mObject != null)
                mObject.setFeedbacktype(parent.getItemAtPosition(position).toString());
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Log.d(TAG, "nothing selected---" + parent.getTag());
    }
}
