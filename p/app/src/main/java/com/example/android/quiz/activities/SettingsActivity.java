package com.example.android.quiz.activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.android.quiz.R;
import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;
import com.hitomi.cmlibrary.OnMenuStatusChangeListener;

public class SettingsActivity extends AppCompatActivity {
    CircleMenu circleMenu;
    static String SELECTED_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        circleMenu = (CircleMenu) findViewById(R.id.circle_menu);

        circleMenu.setMainMenu(Color.parseColor("#CDCDCD"), R.drawable.main_menu, R.drawable.icon_cancel)
                .addSubMenu(Color.parseColor("#258CFF"), R.drawable.java)
                .addSubMenu(Color.parseColor("#30A400"), R.drawable.cpp)
                .addSubMenu(Color.parseColor("#FF4B32"), R.drawable.android)
                .addSubMenu(Color.parseColor("#8A39FF"), R.drawable.icon_instructions)
                .setOnMenuSelectedListener(new OnMenuSelectedListener() {
                    @Override
                    public void onMenuSelected(int index) {
        Intent i = new Intent(SettingsActivity.this, QuestionsActivity.class);
        Toast t = new Toast(SettingsActivity.this);

                        switch (index) {

                            //java selected
                            case 0:
                                t.makeText(SettingsActivity.this,"Java Quiz",Toast.LENGTH_SHORT).show();
                                i.putExtra(SELECTED_KEY, index);
                                startActivity(i);
                                break;
                            case 1:
                                t.makeText(SettingsActivity.this,"C++ Quiz",Toast.LENGTH_SHORT).show();
                                i.putExtra(SELECTED_KEY, index);
                                startActivity(i);
                                break;
                            case 2:
                                t.makeText(SettingsActivity.this,"Android Quiz",Toast.LENGTH_SHORT).show();
                                i.putExtra(SELECTED_KEY, index);
                                startActivity(i);
                                break;
                            case 3:
                                t.makeText(SettingsActivity.this,"Quiz instructions",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SettingsActivity.this, InstructionsActivity.class));
                                break;
                            default:
                                Toast.makeText(SettingsActivity.this, "Nothing to select", Toast.LENGTH_SHORT).show();

                        }
                    }
                }).setOnMenuStatusChangeListener(new OnMenuStatusChangeListener() {

            @Override
            public void onMenuOpened() {
            }

            @Override
            public void onMenuClosed() {
            }

        });
    }
}
