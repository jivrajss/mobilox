package module.custom.jugnig.mobilox.data;

import java.util.List;

/**
 * Created by JugniG on 05-05-2016.
 */
public class PreferenceItem {

    private int id;
    private String title;
    private String type;
    private List<PreferenceInnerItem> items;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<PreferenceInnerItem> getItems() {
        return items;
    }

    public void setItems(List<PreferenceInnerItem> items) {
        this.items = items;
    }
}
