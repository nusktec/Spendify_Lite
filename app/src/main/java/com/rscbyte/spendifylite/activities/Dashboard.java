package com.rscbyte.spendifylite.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.rscbyte.spendifylite.R;
import com.rscbyte.spendifylite.Utils.Tools;
import com.rscbyte.spendifylite.databinding.ActivityDashboardBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Dashboard extends AppCompatActivity {

    //Main layout inflater holder
    ActivityDashboardBinding bdx = null;
    Activity ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //assign context
        this.ctx = this;
        //inflate layout
        bdx = DataBindingUtil.setContentView(ctx, R.layout.activity_dashboard);
        //initialize toolbar
        initToolBar();
        //initialize components
        componentsInit();
        //initialize main methods
        main();
    }

    //set header and toolbar
    public void initToolBar() {
        Tools.setSystemBarColor(ctx, R.color.app_color_1);
    }

    //components initializer
    public void componentsInit() {
        //btn add transaction
        bdx.btnDashAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDialog();
            }
        });
        //btn dash home
        bdx.btnDashHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bdx.mainViewPager.setCurrentItem(0, true);
            }
        });
        //btn dash list
        bdx.btnDashList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bdx.mainViewPager.setCurrentItem(1, true);
            }
        });
        //make control (misc)

    }

    //Add transaction box
    private Dialog dialog = null;

    public void addDialog() {
        dialog = new Dialog(ctx);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(R.layout.dialog_add_data);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        dialog.findViewById(R.id.bt_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //add transaction
                Tools.showToast(ctx, "Transaction Added");
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }


    //main control
    public void main() {
        //prepare view pager
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), 1);
        adapter.addFragment(new FragmentChart());
        adapter.addFragment(new FragmentList());
        //add the adapter to view pager
        bdx.mainViewPager.setAdapter(adapter);
        bdx.mainViewPager.setOffscreenPageLimit(2);
        bdx.mainViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0) {
                    animationSwitcher(bdx.btnDashHome);
                } else {
                    animationSwitcher(bdx.btnDashList);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //animations switcher
    private View tmp_view = null;

    private void animationSwitcher(View view) {
        if (tmp_view == null) {
            tmp_view = view;
        } else {
            tmp_view.clearAnimation();
            tmp_view = view;
        }
        //apply animations
        tmp_view.startAnimation(AnimationUtils.loadAnimation(ctx, R.anim.anim_pulse));
    }

    //view pager adapter class
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> fragments = new ArrayList<>();

        ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        //add the fragment
        public void addFragment(Fragment fragment) {
            fragments.add(fragment);
        }
    }
}
