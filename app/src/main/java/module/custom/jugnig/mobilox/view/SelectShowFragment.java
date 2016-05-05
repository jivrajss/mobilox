package module.custom.jugnig.mobilox.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import module.custom.jugnig.mobilox.R;

/**
 * Created by JugniG on 05-05-2016.
 */
public class SelectShowFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_select_show, container, false);
        return view;
    }
}
