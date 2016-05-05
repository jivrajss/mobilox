package module.custom.jugnig.mobilox.data;

import java.util.List;

/**
 * Created by JugniG on 05-05-2016.
 */
public class PreferenceModel {
    private int result;
    private List<PreferenceItem> data;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public List<PreferenceItem> getData() {
        return data;
    }

    public void setData(List<PreferenceItem> data) {
        this.data = data;
    }
}
