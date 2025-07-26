package com.example.expensemanagement.Utils;


import android.view.ContextMenu;

import com.example.expensemanagement.enums.SpecialCharacters;

import org.joda.time.LocalDate;


public class Util {
    public static LocalDate getTodayDate(){
        return new LocalDate();
    }

    public static String makeFirstCharacterCapital(String input){
        if(input.isEmpty()){
            return null;
        }
        return (input.toUpperCase().charAt(0)+""+input.toLowerCase().substring(1)).trim();
    }
    public static String[] getMonths() {
        String[] months = new String[13];
        months[1] = "Jan";
        months[2] = "Feb";
        months[3] = "Mar";
        months[4] = "Apr";
        months[5] = "May";
        months[6] = "Jun";
        months[7] = "Jul";
        months[8] = "Aug";
        months[9] = "Sept";
        months[10] = "Oct";
        months[11] = "Nov";
        months[12] = "Dec";

        return months;

    }

    public static void createUpdateDeleteContextMenu(ContextMenu contextMenu, int position){
        contextMenu.setHeaderTitle("Select option");
        contextMenu.add(position, SpecialCharacters.DELETE,0,"Delete");

    }
}
