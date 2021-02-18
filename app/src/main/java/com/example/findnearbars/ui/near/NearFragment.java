package com.example.findnearbars.ui.near;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
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
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.findnearbars.CustomMapView;
import com.example.findnearbars.DetailsActivity;
import com.example.findnearbars.R;
import com.example.findnearbars.adapters.SearchDistanceAdapter;
import com.example.findnearbars.pojo.Coords;
import com.example.findnearbars.pojo.Result;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.InputListener;
import com.yandex.mapkit.map.Map;
import com.yandex.mapkit.map.MapObjectCollection;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.runtime.image.ImageProvider;

import android.location.Location;
import android.location.LocationListener;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import static android.content.Context.LOCATION_SERVICE;
public class NearFragment extends Fragment {

    private TextView textViewNearSearch;
    private ImageView imageViewNearSearch;
    private TextView textViewNearLable;
    private CardView cardViewNear;

    private NearViewModel nearViewModel;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private SearchDistanceAdapter adapter;

    private Coords currentCoords = null;
    private List<Result> myResults;
    private List<Result> resultsByDistance;
    private Point currentGeo = null;

    private ImageView imageViewFind;
    private EditText editTextDistance;
    private RecyclerView recyclerViewNear;

    private CustomMapView mapview;

    private MapObjectCollection mapObjectCollection;


    private static final int REQUEST_CODE_ASK_PERMISSIONS = 123;

    private final  InputListener inputListener = new InputListener() {
        @Override
        public void onMapTap(@NonNull Map map, @NonNull Point point) {

        }

        @Override
        public void onMapLongTap(@NonNull Map map, @NonNull Point point) {
            if(point!=currentGeo && currentGeo==null){
                mapObjectCollection.clear();
                PlacemarkMapObject placemark = mapObjectCollection.addPlacemark(point,ImageProvider.fromResource(getContext(),R.drawable.placeholder));
                currentGeo = point;
                Log.i("my_rez","ok");
            }else if(point!=currentGeo && currentGeo!=null){
                mapObjectCollection.clear();
                PlacemarkMapObject placemark = mapObjectCollection.addPlacemark(point,ImageProvider.fromResource(getContext(),R.drawable.placeholder));
            }
            Log.i("my_rez","ne ok");

        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        MapKitFactory.setApiKey("fefdbc18-9be6-468b-8dbc-6fb788c4f4a1");
        MapKitFactory.initialize(getContext());
    }

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


        mapview = root.findViewById(R.id.mapviewNear);
        mapview.getMap().move(
                new CameraPosition(new Point(55.751574, 37.573856), 11.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);



        mapview.getMap().addInputListener(inputListener);
        mapObjectCollection = mapview.getMap().getMapObjects().addCollection();
        //mapObjectCollection.addPlacemark(new Point(55.751574,37.573856));
        //PlacemarkMapObject placemark = mapObjectCollection.addPlacemark(new Point(55.751574,37.573856));





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
                    if (currentCoords==null){
                        currentCoords = new Coords();
                    currentCoords.setLat(location.getLatitude());
                    currentCoords.setLon(location.getLongitude());
                        mapObjectCollection.clear();

                        PlacemarkMapObject placemark = mapObjectCollection.addPlacemark(new Point(location.getLatitude(),location.getLongitude()),ImageProvider.fromResource(getContext(),R.drawable.placeholder));

                        mapview.getMap().move(
                                new CameraPosition(new Point(location.getLatitude(),location.getLongitude()), 18.0f, 0.0f, 0.0f),
                                new Animation(Animation.Type.SMOOTH, 0),
                                null);

                        Log.i("my_rez", (String.valueOf(location.getLatitude())));
                    Log.i("my_rez", (String.valueOf(location.getLongitude())));
                }else if(currentCoords.getLon() != location.getLongitude() && currentCoords.getLat() != location.getLatitude()) {
                        currentCoords = new Coords();
                        currentCoords.setLat(location.getLatitude());
                        currentCoords.setLon(location.getLongitude());
                        Log.i("my_rez", (String.valueOf(location.getLatitude())));
                        Log.i("my_rez", (String.valueOf(location.getLongitude())));
                        mapObjectCollection.clear();

                        PlacemarkMapObject placemark = mapObjectCollection.addPlacemark(new Point(location.getLatitude(),location.getLongitude()),ImageProvider.fromResource(getContext(),R.drawable.placeholder));
                        mapview.getMap().move(
                                new CameraPosition(new Point(location.getLatitude(),location.getLongitude()), 18.0f, 0.0f, 0.0f),
                                new Animation(Animation.Type.SMOOTH, 0),
                                null);

                    }
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
        textViewNearSearch = root.findViewById(R.id.textViewNearSearch);
        imageViewNearSearch = root.findViewById(R.id.imageViewNear);
        textViewNearLable = root.findViewById(R.id.textViewNearIAmHere);
        cardViewNear = root.findViewById(R.id.cardViewNear);


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
        textViewNearSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentCoords!=null || currentGeo!=null) {


                    textViewNearSearch.setVisibility(View.GONE);
                    mapview.setVisibility(View.GONE);
                    imageViewNearSearch.setVisibility(View.GONE);
                    textViewNearLable.setVisibility(View.GONE);
                    cardViewNear.setVisibility(View.GONE);

                    editTextDistance.setVisibility(View.VISIBLE);
                    recyclerViewNear.setVisibility(View.VISIBLE);
                    imageViewFind.setVisibility(View.VISIBLE);
                }else {
                    Toast.makeText(getContext(), "Укажите ваше местоположение", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imageViewNearSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentCoords!=null || currentGeo!=null) {

                    textViewNearSearch.setVisibility(View.GONE);
                    mapview.setVisibility(View.GONE);
                    imageViewNearSearch.setVisibility(View.GONE);
                    textViewNearLable.setVisibility(View.GONE);
                    cardViewNear.setVisibility(View.GONE);

                    editTextDistance.setVisibility(View.VISIBLE);
                    recyclerViewNear.setVisibility(View.VISIBLE);
                    imageViewFind.setVisibility(View.VISIBLE);
                }else {
                    Toast.makeText(getContext(), "Укажите ваше местоположение", Toast.LENGTH_SHORT).show();

                }

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
        editTextDistance.setVisibility(View.GONE);
        imageViewFind.setVisibility(View.GONE);

        double distance = Double.parseDouble(editTextDistance.getText().toString());
        java.util.Map<Double,Result> result =new TreeMap<Double, Result>();
        if(currentGeo==null) {

            result = nearViewModel.getResultsByDistance(currentCoords, distance, myResults);

        }
        else {
            Coords tempCoords = new Coords();
            tempCoords.setLat(currentGeo.getLatitude());
            tempCoords.setLon(currentGeo.getLongitude());
            result = nearViewModel.getResultsByDistance(tempCoords, distance, myResults);
        }
        //resultsByDistance.addAll(nearViewModel.getResultsByDistance(currentCoords,distance,myResults));
        if(result.size()==0){
            Snackbar.make(getView(), "Ничего не нашли \uD83D\uDE13", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            editTextDistance.setVisibility(View.VISIBLE);
            imageViewFind.setVisibility(View.VISIBLE);
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

    @Override
    public void onStop() {
        super.onStop();
        mapview.onStop();
        MapKitFactory.getInstance().onStop();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapview.onStart();
        MapKitFactory.getInstance().onStart();

    }
}
