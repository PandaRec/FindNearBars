package com.example.findnearbars.ui.near;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.RecognitionService;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.loader.app.LoaderManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findnearbars.DetailsActivity;
import com.example.findnearbars.R;
import com.example.findnearbars.adapters.SearchAdapter;
import com.example.findnearbars.adapters.SearchDistanceAdapter;
import com.example.findnearbars.pojo.Coords;
import com.example.findnearbars.pojo.Result;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import android.location.Location;
import android.location.LocationListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
// todo : добавить карту с меткой пользователя и скзать, что он тут
import static android.content.Context.LOCATION_SERVICE;
// todo: сделать карту с гео пользователя
// todo : дать возможность изменить текущую гео

public class NearFragment extends Fragment {

    private NearViewModel nearViewModel;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private SearchDistanceAdapter adapter;

    private Coords currentCoords = null;
    private List<Result> myResults;
    private List<Result> resultsByDistance;

    private ImageView imageViewFind;
    private EditText editTextDistance;
    private RecyclerView recyclerViewNear;

    private static final int REQUEST_CODE_ASK_PERMISSIONS = 123;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.i("my_rez", "onRequestPermissionsResult + ok");
            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            if (canGetLocation(getContext()))
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);


        } else {
            Log.i("my_rez", "onRequestPermissionsResult + fail");

        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        myResults = new ArrayList<>();
        resultsByDistance =  new ArrayList<>();
        adapter = new SearchDistanceAdapter();
        nearViewModel = new ViewModelProvider(this).get(NearViewModel.class);
        nearViewModel.createNearViewModel(getContext());
        locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

        View root = inflater.inflate(R.layout.fragment_near, container, false);
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
        recyclerViewNear.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        recyclerViewNear.setAdapter(adapter);

        nearViewModel.getResults().observe(getViewLifecycleOwner(), new Observer<List<Result>>() {
            @Override
            public void onChanged(List<Result> results) {
                myResults.addAll(results);
            }
        });


        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                if (location != null) {
                    currentCoords = new Coords();
                    currentCoords.setLat(location.getLatitude());
                    currentCoords.setLon(location.getLongitude());
                    Log.i("my_rez", (String.valueOf(location.getLatitude())));
                    Log.i("my_rez", (String.valueOf(location.getLongitude())));
                } else {
                    Log.i("my_rez", "Sorry, location");
                    Log.i("my_rez", "unavailable");
                }
            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {
                Log.i("my_rez", "providerEnabled");
            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {
                Log.i("my_rez", "providerDisabled");
                startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.i("my_rez", "status changed");
            }
        };


        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.i("my_rez", "no permission");

            requestPermissions(new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_ASK_PERMISSIONS);


        } else {
            if (canGetLocation(getContext())) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                Log.i("my_rez", "can get location");

            } else {
                Log.i("my_rez", "can't get location");
                startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }


        return root;
    }

    @Override
    public void onResume() {
        editTextDistance.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    Log.i("my_rez", "find pressed");
                    setDataToAdapter();
                    return true;
                }
                return false;
            }
        });
        imageViewFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("my_rez", "image find pressed");
                setDataToAdapter();

            }
        });

        if (canGetLocation(getContext())) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Log.i("mt_rez","work here");
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
        adapter.setOnClickResultListener(new SearchDistanceAdapter.OnClickResultListener() {
            @Override
            public void onClickResult(int position) {
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra("result",new Gson().toJson(adapter.getResults().get(position)));
                startActivity(intent);
            }
        });

        super.onResume();

    }

    @Override
    public void onPause() {
        locationManager.removeUpdates(locationListener);
        super.onPause();
    }


    public static boolean canGetLocation(Context context) {
        return isLocationEnabled(context); // application context
    }

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;
                try {
                    locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

                }catch (Exception e){}

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
    }

    private void setDataToAdapter(){
        double distance = Double.parseDouble(editTextDistance.getText().toString());
        Map<Double,Result> result =new TreeMap<Double, Result>();
        result = nearViewModel.getResultsByDistance(currentCoords,distance,myResults);
        //resultsByDistance.addAll(nearViewModel.getResultsByDistance(currentCoords,distance,myResults));
        if(result.size()==0){
            Snackbar.make(getView(), "Ничего не нашли \uD83D\uDE13", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            return;
        }
        for(Double dist: result.keySet()){
            Result res = result.get(dist);
            res.setDistance(dist);
            resultsByDistance.add(res);
        }
        adapter.setResults(resultsByDistance);
        editTextDistance.setText("");
        //editTextDistance.clearFocus();
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }
}
