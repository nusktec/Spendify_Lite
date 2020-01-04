package com.rscbyte.spendifylite.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.orm.SugarContext;
import com.orm.SugarDb;
import com.orm.SugarRecord;
import com.orm.query.Condition;
import com.orm.query.Select;
import com.orm.util.SugarConfig;
import com.rscbyte.spendifylite.Interfaces.CallBacks;
import com.rscbyte.spendifylite.R;
import com.rscbyte.spendifylite.Utils.Constants;
import com.rscbyte.spendifylite.Utils.Tools;
import com.rscbyte.spendifylite.adapters.ASimpleList;
import com.rscbyte.spendifylite.databinding.ActivityFragmentListBinding;
import com.rscbyte.spendifylite.databinding.DialogAddDataBinding;
import com.rscbyte.spendifylite.databinding.DialogPreviewDataBinding;
import com.rscbyte.spendifylite.models.MData;
import com.rscbyte.spendifylite.objects.OData;
import com.rscbyte.spendifylite.objects.OTrxInfo;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class FragmentList extends Fragment {
    //make a public variables
    private ActivityFragmentListBinding bdx = null;
    private Activity ctx;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //assign context
        this.ctx = getActivity();
        //ass layout binding to it parents
        bdx = DataBindingUtil.inflate(inflater, R.layout.activity_fragment_list, container, false);
        //establish main data
        main();
        //reload btn
        bdx.btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main();
            }
        });
        return bdx.getRoot();
    }

    //number format
    private NumberFormat nf = new DecimalFormat("#,###.00");
    //set recycler view
    private ASimpleList aSimpleList = null;

    //simple main
    private void main() {
        //make list item
        List<OData> oData = new ArrayList<>();
        //read from db, stored data
        List<MData> mData = MData.listAll(MData.class);
        main(mData);
    }

    //main methods
    public void main(List<MData> mData) {
        try {
            Collections.reverse(mData);
            List<OData> oData = new ArrayList<>();
            bdx.txtNodata.setVisibility(mData.size() > 0 ? View.INVISIBLE : View.VISIBLE); //hide no data text
            for (MData md : mData) {
                OData oData1 = new OData();
                oData1.setTitle(md.getTrxType() == 1 ? "Credit" : "Debit");
                oData1.setDay(md.getTrxDay());
                oData1.setDesc(md.getTrxDesc());
                oData1.setValue(Constants.getCurrency() + nf.format(Double.valueOf(md.getTrxValue())));
                oData1.setYear(md.getTrxYear());
                oData1.setMonth(md.getTrxMonth());
                oData1.setDate(md.getTrxDate());
                oData1.setmData(md);
                oData.add(oData1);
            }
            //set recycler view
            aSimpleList = new ASimpleList(oData, dataClicked);
            bdx.listItemView.setAdapter(aSimpleList);
            //top text search
            bdx.toolbarRightBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //start searching
                    Tools.showPickerLight(ctx, new Tools.getDateItemsClick() {
                        @Override
                        public void getDate(int y, int m, int d) {
                            searchDataBase(String.valueOf(y), String.valueOf(m));
                        }

                        @Override
                        public void getString(String dateString) {

                        }

                        @Override
                        public void onCancel() {

                        }
                    });
                }
            });
//        bdx.txtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                    //start searching
//                    if (mData.size() > 0) {
//                        if (bdx.txtSearch.getText().toString().length() > 5) {
//                            searchDataBase(bdx.txtSearch.getText().toString());
//                        } else {
//                            Tools.showToast(ctx, "Keywords too short");
//                        }
//                    }
//                    return true;
//                }
//                return false;
//            }
//        });
//        bdx.txtSearch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Tools.showToast(ctx, "Clicked !");
//            }
//        });
        } catch (Exception ex) {
            ex.printStackTrace();
            Tools.showToast(ctx, "Error with data integrity");
        }
    }

    //start searching in database
    private void searchDataBase(String... args) {
        List<MData> md = Select.from(MData.class).where(Condition.prop("TRX_YEAR").eq(args[0])).and(Condition.prop("TRX_MONTH").eq(args[1])).list();
        //make list item
        main(md);
        Tools.showToast(ctx, Tools.getMonthAscAlpha(Integer.parseInt(args[1])) + " data queried...");
    }

    //surface clicked
    private ASimpleList.dataClicked dataClicked = new ASimpleList.dataClicked() {
        @Override
        public void onDataClicked(OData oData) {
            previewData(oData);
        }
    };

    //popup dialog
    private Dialog dialog = null;

    void previewData(final OData data) {
        dialog = new Dialog(ctx);
        final DialogPreviewDataBinding dbuild = DataBindingUtil.inflate(LayoutInflater.from(ctx), R.layout.dialog_preview_data, null, false);
        dbuild.setD(new OTrxInfo());
        dbuild.getD().setDesc(data.getDesc());
        dbuild.getD().setDate(data.getDate());
        dbuild.getD().setSource(data.getmData().getTrxSrc() == 1 ? "Manual" : (data.getmData().getTrxBankName() == null ? "SMS Alert" : data.getmData().getTrxBankName()));
        dbuild.getD().setType(data.getmData().getTrxType() == 1 ? "Credit" : "Debit");
        dbuild.getD().setValue(Constants.getCurrency() + nf.format(Double.valueOf(data.getmData().getTrxValue())));

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog.setContentView(dbuild.getRoot());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //start misc.
        dbuild.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //delete data
                msgBox(new CallBacks() {
                    @Override
                    public void onOkay() {
                        MData mData = MData.findById(MData.class, data.getmData().getId());
                        mData.delete();
                        dialog.dismiss();
                        Tools.showToast(ctx, "Deleted !");
                        main();
                        //Objects.requireNonNull(getActivity()).recreate();
                    }
                }, "Delete", "Sure to delete this transaction ?");
            }
        });
        dbuild.btnDuplicate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                msgBox(new CallBacks() {
                    @Override
                    public void onOkay() {
                        MData m = data.getmData();
                        MData m2 = new MData();
                        if (m.getTrxSrc() == 1) {
                            m2.setTrxDate(Tools.getFormattedDateSimple());
                            m2.setTrxYear(String.valueOf(Tools.getYear()));
                            m2.setTrxMonth(String.valueOf(Tools.getMonth()));
                            m2.setTrxDay(String.valueOf(Tools.getDay()));
                            m2.setTrxSTP(Long.parseLong(Tools.getDateTimeStamp()));
                            m2.setTrxDesc(m.getTrxDesc());
                            m2.setTrxSrc(1);
                            m2.setTrxType(m.getTrxType());
                            m2.setTrxValue(m2.getTrxValue());
                            SugarRecord.save(m2);
                            Tools.showToast(ctx, "Transaction duplicated");
                            dialog.dismiss();
                            main();
                            //Objects.requireNonNull(getActivity()).recreate();
                        } else {
                            Tools.showToast(ctx, "Unable to duplicate bank alert");
                        }
                    }
                }, "Duplicate", "Want to duplicate this transaction ?. Today's date will be used in place");
            }
        });
        dbuild.btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dbuild.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    //Public msgBox
    private void msgBox(final CallBacks callBacks, String... args) {
        AlertDialog.Builder d = new AlertDialog.Builder(ctx);
        d.setTitle(args[0]);
        d.setMessage(args[1]);
        d.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        d.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                callBacks.onOkay();
            }
        });
        d.show();
    }
}
