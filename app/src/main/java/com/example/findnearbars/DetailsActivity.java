package com.example.findnearbars;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.findnearbars.adapters.DetailImagesAdapter;
import com.example.findnearbars.pojo.FavouriteResult;
import com.example.findnearbars.pojo.Result;
import com.example.findnearbars.ui.favourite.FavouriteFragment;
import com.example.findnearbars.ui.favourite.FavouriteViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.runtime.image.ImageProvider;

import java.util.List;


public class DetailsActivity extends AppCompatActivity {
    private Result currentResult;
    private RecyclerView recyclerViewImages;
    private DetailImagesAdapter adapter;
    private FavouriteViewModel favouriteViewModel;
    private FavouriteResult favouriteResult;

    private TextView textViewTitle;
    private TextView textViewAddress;
    private TextView textViewTimetable;
    private TextView textViewPhone;
    private TextView textViewSite;
    private CustomMapView mapview;
    private ScrollView mainScrollView;
    private ImageView imageViewFavourite;

    private ImageView imageViewMorePhone;
    private ImageView imageViewMoreSite;
    private ImageView imageViewMoreAddress;

    private TextView textViewDialogPhone1;
    private TextView textViewDialogPhone2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MapKitFactory.setApiKey("fefdbc18-9be6-468b-8dbc-6fb788c4f4a1");
        MapKitFactory.initialize(this);


        // Укажите имя activity вместо map.
        setContentView(R.layout.activity_details_2);
        //mapview = new CustomMapView(this);
        mapview = findViewById(R.id.mapview);
        mapview.getMap().move(
                new CameraPosition(new Point(55.751574, 37.573856), 11.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);


        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("result")) {
            currentResult = new Gson().fromJson(intent.getStringExtra("result"), Result.class);
        } else {
            currentResult = null;
        }

        Point point = new Point(currentResult.getCoords().getLat(),currentResult.getCoords().getLon());
        mapview.getMap().getMapObjects().addPlacemark(point, ImageProvider.fromResource(this,R.drawable.placeholder));

        mapview.getMap().move(
                new CameraPosition(point, 15.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);


        textViewTitle = findViewById(R.id.textViewTitle);
        textViewAddress = findViewById(R.id.textViewAddress);
        textViewTimetable = findViewById(R.id.textViewTimetable);
        textViewPhone = findViewById(R.id.textViewPhone);
        textViewSite = findViewById(R.id.textViewSite);
        mainScrollView =  findViewById(R.id.scrollView);
        imageViewFavourite = findViewById(R.id.imageViewfavourite);
        imageViewMoreAddress = findViewById(R.id.imageViewMoreAddress);
        imageViewMorePhone = findViewById(R.id.imageViewMorePhone);
        imageViewMoreSite = findViewById(R.id.imageViewMoreSite);


//        textViewDialogPhone1 = findViewById(R.id.textViewDialogPhone1);
//        textViewDialogPhone2 = findViewById(R.id.textViewDialogPhone2);



        favouriteViewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(FavouriteViewModel.class);
        favouriteViewModel.createFavouriteViewModel(this);
        setFavourite();

        imageViewFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(favouriteResult==null){
                    favouriteViewModel.insertFavouriteResult(new FavouriteResult(currentResult));
                    Snackbar snackbar = Snackbar.make(v,"Добавлено в избранное",Snackbar.LENGTH_LONG);

                    snackbar.setBackgroundTint(getResources().getColor(R.color.gray,null));
                    snackbar.setActionTextColor(getResources().getColor(R.color.black,null));
                    snackbar.setTextColor(getResources().getColor(R.color.black,null));
                    snackbar.show();

                }else {
                    favouriteViewModel.deleteFavouriteResult(new FavouriteResult(currentResult));
                    Snackbar snackbar = Snackbar.make(v,"Удалено из избранного",Snackbar.LENGTH_LONG);

                    snackbar.setBackgroundTint(getResources().getColor(R.color.gray,null));
                    snackbar.setActionTextColor(getResources().getColor(R.color.black,null));
                    snackbar.setTextColor(getResources().getColor(R.color.black,null));
                    snackbar.show();

                }
                setFavourite();
            }
        });



        recyclerViewImages = findViewById(R.id.recyclerViewImages);
        adapter = new DetailImagesAdapter();
        recyclerViewImages.setAdapter(adapter);
        recyclerViewImages.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        adapter.setResults(currentResult.getImages());


        textViewTitle.setText(currentResult.getTitle());
        textViewAddress.setText(currentResult.getAddress());
        textViewTimetable.setText(currentResult.getTimetable());
        textViewPhone.setText(currentResult.getPhone());
        //textViewSite.setText(currentResult.getSiteUrl());


        recyclerViewImages.setBackgroundColor(getResources().getColor(R.color.gray));



        recyclerViewImages.setAdapter(adapter);

// add pager behavior
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(recyclerViewImages);

        recyclerViewImages.addItemDecoration(new LinePagerIndicatorDecoration());
        favouriteViewModel.getFavouriteResults().observe(this, new Observer<List<FavouriteResult>>() {
            @Override
            public void onChanged(List<FavouriteResult> favouriteResults) {
                for(FavouriteResult result : favouriteResults){
                    Log.i("my_rez_favorite",result.getTitle());
                }
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        mapview.onStop();
        MapKitFactory.getInstance().onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapview.onStart();
        MapKitFactory.getInstance().onStart();

    }

    @Override
    protected void onResume() {
        textViewPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPhone(v);

            }
        });
        textViewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMap();

            }
        });
        textViewSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToBrowser();

            }
        });
        imageViewMoreSite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToBrowser();

            }
        });
        imageViewMorePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToPhone(v);

            }
        });
        imageViewMoreAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMap();

            }
        });

        super.onResume();
    }

    private void goToPhone(View view){
        String[] phones = currentResult.getPhone().split(",");
        /*
        if(phones.length==2){
            // 2 phones numbers
            Snackbar snackbar = Snackbar.make(view, "По какому будем звонить?", Snackbar.LENGTH_LONG);
            snackbar.setAction("1", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String phone = "tel:"+phones[0];
                    startActivity(new Intent(Intent.ACTION_DIAL,Uri.parse(phone)));

                }
            });
            snackbar.setAction("2", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String phone = "tel:"+phones[1];
                    startActivity(new Intent(Intent.ACTION_DIAL,Uri.parse(phone)));
                }
            });
            snackbar.show();

        }
        else {
            //one phone numbers
            String phone = "tel:"+currentResult.getPhone();
            startActivity(new Intent(Intent.ACTION_DIAL,Uri.parse(phone)));

        }
*/
        if(phones.length==2) {

            LayoutInflater layoutInflater = getLayoutInflater();
            View alertLayout = layoutInflater.inflate(R.layout.custom_dialog, null);
            TextView textViewPhone1 = alertLayout.findViewById(R.id.textViewDialogPhone1);
            TextView textViewPhone2 = alertLayout.findViewById(R.id.textViewDialogPhone2);
            textViewPhone1.setText(phones[0]);
            textViewPhone2.setText(phones[1]);


            //AlertDialog.Builder alertDialog = new AlertDialog.Builder(DetailsActivity.this,R.style.MyThemeOverlay_MaterialComponents_MaterialAlertDialog);
            MaterialAlertDialogBuilder alertDialog = new MaterialAlertDialogBuilder(DetailsActivity.this,R.style.MyThemeOverlay_MaterialComponents_MaterialAlertDialog);


            alertDialog.setCancelable(true);
            alertDialog.setView(alertLayout);
            //AlertDialog dialog = alertDialog.create();
            AlertDialog dialog = alertDialog.create();




            textViewPhone1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    String phone = "tel:" + phones[0];
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(phone)));
                }
            });
            textViewPhone2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    String phone = "tel:" + phones[1];
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(phone)));

                }
            });
//        button.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
            dialog.show();
        }else {
            String phone = "tel:"+currentResult.getPhone();
            startActivity(new Intent(Intent.ACTION_DIAL,Uri.parse(phone)));
        }

    }
    private void goToMap(){
        Toast.makeText(this, "Тут должен быть переход в карты", Toast.LENGTH_SHORT).show();

    }
    private void goToBrowser(){
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(currentResult.getSiteUrl())));

    }

    private void setFavourite(){
        favouriteResult = favouriteViewModel.getFavouriteResultById(currentResult.getId());
        if(favouriteResult==null){
            imageViewFavourite.setImageResource(R.drawable.ic_add_to_favourite);

        }else {
            imageViewFavourite.setImageResource(R.drawable.ic_remove_from_favourite);

        }

    }

}
