package module.custom.jugnig.mobilox.custom;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import module.custom.jugnig.mobilox.R;

/**
 * Created by JugniG on 05-05-2016.
 */

public class TextViewHolder extends RecyclerView.ViewHolder {
    public TextView textView;

    public TextViewHolder(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.text);
    }
}
