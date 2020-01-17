package com.rscbyte.spendifylite.activities;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
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

import com.onesignal.OneSignal;
import com.orm.SugarContext;
import com.orm.SugarRecord;
import com.rscbyte.spendifylite.R;
import com.rscbyte.spendifylite.Utils.Constants;
import com.rscbyte.spendifylite.Utils.Tools;
import com.rscbyte.spendifylite.databinding.ActivityDashboardBinding;
import com.rscbyte.spendifylite.databinding.DialogAddDataBinding;
import com.rscbyte.spendifylite.models.MData;
import com.rscbyte.spendifylite.models.MProfile;
import com.rscbyte.spendifylite.networks.Adscene;
import com.rscbyte.spendifylite.networks.Synchronize;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import hotchemi.android.rate.AppRate;
import hotchemi.android.rate.OnClickButtonListener;

public class Dashboard extends AppCompatActivity {

    //number format
    private NumberFormat nf = new DecimalFormat("#,###.00");
    private List<MData> mData = null;
    private MProfile profile;
    //Main layout inflater holder
    private ActivityDashboardBinding bdx = null;
    private Activity ctx;
    private int _scrolled_nun = 0;
    private boolean isLogged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //re-initialize db
        SugarContext.init(this);
        //assign context
        this.ctx = this;
        //inflate layout
        bdx = DataBindingUtil.setContentView(ctx, R.layout.activity_dashboard);
        //run check profile
        if (MProfile.count(MProfile.class) > 0) {
            this.profile = MProfile.findById(MProfile.class, 1);
            this.isLogged = true;
        }
        //initialize toolbar
        initToolBar();
        //initialize components
        componentsInit();
        //check apply settings
        //initialize main methods
        main();
        //wait and run
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    checkAfter3Open();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }, 3000);
        //check and pop rate
        try {
            AppRate.with(this)
                    .setInstallDays(4) // default 10, 0 means install day.
                    .setLaunchTimes(3) // default 10
                    .setRemindInterval(4) // default 1
                    .setShowLaterButton(true) // default true
                    .setDebug(false) // default false
                    .setTitle(this.isLogged ? "Hey, " + this.profile.getNames().split(" ")[0] + " !" : "Spendify Token")
                    .setOnClickButtonListener(new OnClickButtonListener() { // callback listener.
                        @Override
                        public void onClickButton(int which) {
                            int keys = Math.abs(which);
                            if (keys == 2) {
                                //tell a friend
                                Tools.shareText(ctx, "Invite A Friend", getResources().getString(R.string.share_caption) + Tools.getMarketLink(ctx));
                            }
                        }
                    })
                    .monitor();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Show a dialog if meets conditions
        AppRate.showRateDialogIfMeetsConditions(this);
        AppRate.with(this).clearAgreeShowDialog();
        //one-signal initializer
        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(false)
                .init();
    }

    //set header and toolbar
    public void initToolBar() {
        Tools.setHeaderColor(this);
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

    //global runner
    private Handler handler = new Handler();

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
        dialog.setCancelable(true);

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
                    Tools.showToast(ctx, "Transaction fields are empty...");
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
                main();
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }


    //main control
    public void main() {
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
        checkDefaultThing();
    }

    //check for newer things
    private void checkDefaultThing() {
        int getNewAlert = getSharedPreferences(Constants.SHARED_PREF_NAME, MODE_PRIVATE).getInt(Constants.SHARED_ALERT_KEY, 0);
        if (getNewAlert > 0) {
            Tools.msgDialog(ctx, "New Alert Sync.", getNewAlert + " alert(s) were sync and added to your transaction time ago, check your list...", R.drawable.ic_textsms, R.color.green_600);
        }
        getSharedPreferences(Constants.SHARED_PREF_NAME, MODE_PRIVATE).edit().putInt(Constants.SHARED_ALERT_KEY, 0).apply();
        //fire first launched
        firstLaunched();
    }

    //fire first launched
    private void firstLaunched() {
        int firstLaunched = getSharedPreferences(Constants.SHARED_PREF_NAME, MODE_PRIVATE).getInt(Constants.SHARED_NEW_OPEN_KEY, 0);
        if (firstLaunched > 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Tools.msgDialog(ctx, "Quick Help !", "1. Click on + icon to add new transaction\n\n2. Usual is same as last month expense\n\n3. Monthly Average is same as previous 4 months average", R.drawable.ic_help, R.color.blue_700);
                }
            }, 2000);
        }
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
        void addFragment(Fragment fragment) {
            fragments.add(fragment);
        }
    }

    //check if logged
    private void checkAfter3Open() {

        if (!Tools.isNetworkAvailable(ctx) || !isLogged) {
            return;
        }
        //upload dbToCloud
        Synchronize.doBackupDb(ctx, profile.getEmail());
        //request for adverts
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new Adscene().showAdvert(ctx, profile.getEmail());
                    }
                });
            }
        }, 3000);
        Tools.isTimeCheck();
    }

    //override onBack press
    boolean freeExit = false;

    @Override
    public void onBackPressed() {
        if (bdx.mainViewPager.getCurrentItem() == 0) {
            if (freeExit)
                finish();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    freeExit = false;
                }
            }, 3000);
            freeExit = true;
            Tools.showToast(ctx, "Press again to exit");
        } else {
            bdx.mainViewPager.setCurrentItem(0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                main();
            }
        }, 500);
    }
}
