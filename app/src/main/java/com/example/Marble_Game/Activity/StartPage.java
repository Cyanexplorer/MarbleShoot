package com.example.Marble_Game.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.Marble_Game.R;
import com.example.Marble_Game.StartPageFragment;

import static com.example.Marble_Game.util.Constants.STRFRG;

public class StartPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_page);
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = StartPageFragment.newInstance();
        Fragment rmFragment = manager.findFragmentByTag(STRFRG);
        if(rmFragment!=null){
            manager.beginTransaction().remove(rmFragment).commit();
        }
        getSupportFragmentManager().beginTransaction().add(R.id.activity_start_page,fragment,STRFRG).commit();


    }
}
