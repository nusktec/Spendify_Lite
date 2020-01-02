package com.rscbyte.spendifylite.Interfaces;

import com.rscbyte.spendifylite.objects.OSms;

import java.util.List;

public interface SMSCallBack {
    void isSMS(boolean isReady, List<OSms> isDataLst);
}
