package com.parrot.sliderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private FrameLayout frameLayout;
    private Button show_btn,hide_btn,slideLeft_btn;
    private static final int FRAGMENT_1=1;
    private static final int FRAGMENT_2=2;
    private static final int FRAGMENT_3=3;
    private static final int FRAGMENT_4=4;
    private static final int FRAGMENT_5=5;
    private static final int FRAGMENT_6=6;
    private static final int FRAGMENT_7=7;
    private static final int FRAGMENT_8=8;
    private static final int FRAGMENT_9=9;
    private static final int FRAGMENT_91=10;
    private static final int FRAGMENT_92=11;
    private static final int FRAGMENT_93=12;
    private static final int FRAGMENT_94=13;
    private static final int FRAGMENT_95=14;


    private int upcomingFragment=2;
    private int currentFragmentNo=1;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frameLayout = findViewById(R.id.main_framelayout);
        show_btn = findViewById(R.id.show_btn);
        hide_btn = findViewById(R.id.hide_btn);
        slideLeft_btn = findViewById(R.id.slide_left_btn);

        show_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFirstFragment(new Fragment1());
            }
        });

        slideLeft_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (upcomingFragment == FRAGMENT_2) {
                    setFragment(new Fragment2(), upcomingFragment);
                } else if (upcomingFragment == FRAGMENT_3) {
                    setFragment(new Fragment3(), upcomingFragment);
                } else if (upcomingFragment == FRAGMENT_4) {
                    setFragment(new Fragment4(), upcomingFragment);
                } else if (upcomingFragment == FRAGMENT_5) {
                    setFragment(new Fragment5(), upcomingFragment);
                } else if (upcomingFragment == FRAGMENT_6) {
                    setFragment(new Fragment6(), upcomingFragment);
                } else if (upcomingFragment == FRAGMENT_7) {
                    setFragment(new Fragment7(), upcomingFragment);
                }else if (upcomingFragment == FRAGMENT_8) {
                    setFragment(new Fragment8(), upcomingFragment);
                }else if (upcomingFragment == FRAGMENT_9) {
                    setFragment(new Fragment9(), upcomingFragment);
                }else if (upcomingFragment == FRAGMENT_91) {
                    setFragment(new Fragment91(), upcomingFragment);
                }else if (upcomingFragment == FRAGMENT_92) {
                    setFragment(new Fragment92(), upcomingFragment);
                }else if (upcomingFragment == FRAGMENT_93) {
                    setFragment(new Fragment93(), upcomingFragment);
                }else if (upcomingFragment == FRAGMENT_94) {
                    setFragment(new Fragment94(), upcomingFragment);
                }else if (upcomingFragment == FRAGMENT_95) {
                    setFragment(new Fragment95(), upcomingFragment);
                }else {
                    Toast.makeText(MainActivity.this, "Fragments Completed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        hide_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideFragment();
            }

        });
    }



    private void setFragment(Fragment fragment,int fragmentNo) {
        currentFragmentNo = fragmentNo;
        upcomingFragment = fragmentNo+1;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right,R.anim.slideout_from_left);
        fragmentTransaction.replace(frameLayout.getId(),fragment);
        fragmentTransaction.commit();

    }

    private void setFirstFragment(Fragment fragment) {
        currentFragmentNo = 1;
        upcomingFragment = 2;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_up,R.anim.slide_out_up);
        fragmentTransaction.replace(frameLayout.getId(),fragment);
        fragmentTransaction.commit();
        hide_btn.setEnabled(true);
    }

    private void hideFragment() {
        final Fragment toRemove = getSupportFragmentManager().findFragmentById(R.id.main_framelayout);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_top,R.anim.slide_out_bottom);
        fragmentTransaction.remove(toRemove);
        fragmentTransaction.commit();
        hide_btn.setEnabled(false);

    }


}
