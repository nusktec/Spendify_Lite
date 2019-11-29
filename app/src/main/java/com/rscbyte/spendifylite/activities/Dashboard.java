package com.rscbyte.spendifylite.activities;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.orm.SugarRecord;
import com.rscbyte.spendifylite.R;
import com.rscbyte.spendifylite.Utils.Tools;
import com.rscbyte.spendifylite.databinding.ActivityDashboardBinding;
import com.rscbyte.spendifylite.databinding.DialogAddDataBinding;
import com.rscbyte.spendifylite.models.MData;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity {

    //number format
    private NumberFormat nf = new DecimalFormat("#,###.00");
    private List<MData> mData = null;

    //Main layout inflater holder
    ActivityDashboardBinding bdx = null;
    Activity ctx;
    private int _scrolled_nun = 0;

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
        main(_scrolled_nun);
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
    //selection of transaction type
    int _transaction = 2;

    public void addDialog() {
        _transaction = 2;
        dialog = new Dialog(ctx);
        final DialogAddDataBinding dbuild = DataBindingUtil.inflate(LayoutInflater.from(ctx), R.layout.dialog_add_data, null, false);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(dbuild.getRoot());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        dbuild.btClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dbuild.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == dbuild.radioCredit.getId()) {
                    _transaction = 1;
                } else {
                    _transaction = 2;
                }
            }
        });

        dialog.findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //add transaction
                MData mData = new MData();
                //check transaction type
                if (dbuild.txtDesc.getText().toString().isEmpty() || dbuild.txtValue.getText().toString().isEmpty()) {
                    Tools.showToast(ctx, "Transactions fields are empty...");
                    return;
                }
                mData.setTrxDay(String.valueOf(Tools.getDay()));
                mData.setTrxMonth(String.valueOf(Tools.getMonth()));
                mData.setTrxYear(String.valueOf(Tools.getYear()));
                mData.setTrxDate(Tools.getFormattedDateSimple());

                mData.setTrxType(_transaction);
                mData.setTrxDesc(dbuild.txtDesc.getText().toString());
                mData.setTrxValue(dbuild.txtValue.getText().toString());
                mData.setTrxSrc(1);
                mData.setTrxSTP(Long.parseLong(Tools.getDateTimeStamp()));

                SugarRecord.save(mData);
                dbuild.txtValue.getText().clear();
                dbuild.txtDesc.getText().clear();
                Tools.showToast(ctx, "Transaction Added " + Tools.getFormattedDateSimple());
                main(_scrolled_nun);
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }


    //main control
    public void main(int num) {
        //prepare view pager
        final ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), 1);
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
                _scrolled_nun = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        bdx.mainViewPager.setCurrentItem(_scrolled_nun);
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
