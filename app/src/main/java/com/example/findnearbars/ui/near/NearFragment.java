package com.example.findnearbars.ui.near;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findnearbars.R;
import com.example.findnearbars.ui.search.SearchViewModel;


public class NearFragment extends Fragment {

    private NearViewModel nearViewModel;

    private ImageView imageViewFind;
    private EditText editTextDistance;
    private RecyclerView recyclerViewNear;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        nearViewModel = new ViewModelProvider(this).get(NearViewModel.class);
        nearViewModel.createNearViewModel(getContext());

        View root = inflater.inflate(R.layout.fragment_near,container,false);
        //TextView textViewNear = root.findViewById(R.id.textViewNear);
//        nearViewModel.getmText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(String s) {
//                textViewNear.setText(s);
//            }
//        });
        imageViewFind = root.findViewById(R.id.imageViewFind);
        editTextDistance = root.findViewById(R.id.editTextDistance);
        recyclerViewNear = root.findViewById(R.id.recyclerViewNear);

        nearViewModel.getResultsByDistance(null,null,0,null);




        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        editTextDistance.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Log.i("my_rez","find pressed");
                    return true;
                }
                return false;
            }
        });
        imageViewFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("my_rez","image find pressed");

            }
        });
    }
}
