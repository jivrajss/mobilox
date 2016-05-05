package module.custom.jugnig.mobilox.retrofit;


import module.custom.jugnig.mobilox.data.PreferenceModel;
import module.custom.jugnig.mobilox.data.RequestTypeObject;
import module.custom.jugnig.mobilox.data.SearchModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;


public interface PreferencesInterface {

    String BASE_URL = "http://techmagik.in/";

    @FormUrlEncoded
    @POST("/zeemarathi/apis.php")
    Call<PreferenceModel> getPreferencesList(@Field("data") RequestTypeObject object);

    @FormUrlEncoded
    @POST("/zeemarathi/apis.php")
    Call<SearchModel> getSearchResults(@Field("data") String object);

    @FormUrlEncoded
    @POST("/zeemarathi/apis.php")
    Call<ResponseBody> postFeedBack(@Field("data") String object);


}
