package com.example.expensemanagement.ui.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.expensemanagement.Utils.Util;

import org.joda.time.LocalDate;

public class DatePickerFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LocalDate currentDate = Util.getTodayDate();
        int year = currentDate.getYear();
        int month = currentDate.getMonthOfYear();
        int day = currentDate.getDayOfMonth();
        return new DatePickerDialog(getActivity(),(DatePickerDialog.OnDateSetListener) getActivity(),year, month-1,day);
    }
}
