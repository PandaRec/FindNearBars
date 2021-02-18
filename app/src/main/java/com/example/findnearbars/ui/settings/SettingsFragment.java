package com.example.findnearbars.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.findnearbars.LoadingActivity;
import com.example.findnearbars.R;
import com.google.android.material.snackbar.Snackbar;

public class SettingsFragment extends Fragment {
    private SettingsViewModel settingsViewModel;
    private ImageView imageViewGitHub;
    private ImageView imageViewPhone;
    private ImageView imageViewMail;
    private ImageView imageViewRefresh;

    private SharedPreferences sharedPreferences;
    private static final String APP_PREFERENCES = "settings";
    private static final String APP_PREFERENCES_IS_DOWNLOADED = "isDownloaded";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
            View root = inflater.inflate(R.layout.fragment_settings,container,false);
            imageViewGitHub = root.findViewById(R.id.imageViewSettingsGitHub);
            imageViewPhone = root.findViewById(R.id.imageViewSettingsPhone);
            imageViewMail = root.findViewById(R.id.imageViewSettingsMail);
            imageViewRefresh = root.findViewById(R.id.imageViewRefresh);

            sharedPreferences = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);




        imageViewRefresh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // обновление данных
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(APP_PREFERENCES_IS_DOWNLOADED, false);
                    editor.commit();
                    startActivity(new Intent(getActivity(), LoadingActivity.class));
                }
            });

            imageViewGitHub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://github.com/PandaRec/FindNearBars")));
                }
            });

            imageViewPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Intent.ACTION_DIAL,Uri.parse("tel:1234567890")));
                }
            });

            imageViewMail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String subject = "";
                    String bodyText = "";
                    String mailto = "mailto:bob@example.org" +
                            "?cc=" + "alice@example.com" +
                            "&subject=" + Uri.encode(subject) +
                            "&body=" + Uri.encode(bodyText);

                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                    emailIntent.setData(Uri.parse(mailto));

                    try {
                        startActivity(emailIntent);
                    } catch (Exception e) {
                        if(getView()!=null) {
                            Snackbar.make(getView(), "Не нашли ни одного приложения для отправки письма \uD83D\uDE13", Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }else {
                            Toast.makeText(getContext(), "Не нашли ни одного приложения для отправки письма \uD83D\uDE13", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });


        return root;
    }
}
