package module.custom.jugnig.mobilox.custom;

/**
 * Created by JugniG on 05-05-2016.
 */

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import module.custom.jugnig.mobilox.R;

public class NumberedAdapter extends RecyclerView.Adapter<TextViewHolder> {
    private static final String TAG = NumberedAdapter.class.getSimpleName();
    private List<String> labels;

    public NumberedAdapter(int count) {
        labels = new ArrayList<String>(count);
        for (int i = 0; i < count; ++i) {
            labels.add(String.valueOf(i));
        }
    }

    @Override
    public TextViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new TextViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TextViewHolder holder, final int position) {
        final String label = labels.get(position);
        holder.textView.setText(label);
        Log.d(TAG, "into bind ViewHolder---" + position+holder.textView.getText().toString()+"--visibility--"+(holder.textView.getVisibility()==View.VISIBLE));
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(
                        holder.textView.getContext(), label, Toast.LENGTH_SHORT).show();
            }
        });
//        holder.textView.setText("item--" + position);
    }

    @Override
    public int getItemCount() {
        return labels.size();
    }
}
