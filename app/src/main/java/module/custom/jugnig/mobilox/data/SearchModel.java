package module.custom.jugnig.mobilox.data;

import java.util.List;

/**
 * Created by JugniG on 05-05-2016.
 */
public class SearchModel {
    private int result;
    private List<PreferenceInnerItem> data;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public List<PreferenceInnerItem> getData() {
        return data;
    }

    public void setData(List<PreferenceInnerItem> data) {
        this.data = data;
    }
}
