package com.training.cst.quanlytienantrua.Helper;

import android.os.Build;
import android.text.Editable;

import java.math.BigDecimal;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Created by longdg123 on 11/28/2016.
 */

public class Contants {
    public final static String INPUT = "[ a-zA-Z0-9_ẮắẰằẲẳẴẵẶặĂẤấẦầẨẩẪẫẬậÂâÁáÀàÃãẢảẠạĐđẾếỀềỂểỄễỆ" +
            "ệÊêÉéÈèẺẻẼẽẸẹÍíÌìỈỉĨĩỊịỐốỒồỔổỖỗỘộÔôỚớỜờỞởỠỡỢợƠơÓóÒòÕõỎỏỌọỨứỪừỬửỮữỰựƯưÚúÙùỦủŨũỤụÝýỲỳỶỷỸỹỴỵ]+";
    public final static Pattern PATTERN = Pattern.compile(INPUT);

    public static String replaceSymbol(String sFormat) {
        String replaceable = String.format("[%s,.\\s]",   NumberFormat.getCurrencyInstance().getCurrency().getSymbol());
        String cleanString = sFormat.toString().replaceAll(replaceable, "");
        return cleanString;
    }

    public static String formatCurrency(Editable s, String current, double money) {

        Locale locale = new Locale("vi", "VN");
        Currency currency = Currency.getInstance("VND");
        String cleanString = s.toString().replaceAll("[^\\p{Alpha}\\p{Digit}]+", "");
        money = Long.parseLong(cleanString);
        Math.round(money);
        String formatted = NumberFormat.getCurrencyInstance(locale).format(money);
        current = formatted;
        return current;
    }

    public static String formatCurrencyForTextView(String s, String current, double money) {
        Locale locale = new Locale("vi", "VN");
        Currency currency = Currency.getInstance("VND");
        String cleanString = s.toString().replaceAll("[^\\p{Alpha}\\p{Digit}]+", "");
        DecimalFormatSymbols df = DecimalFormatSymbols.getInstance(locale);
        df.setCurrency(currency);
        money = Long.parseLong(cleanString);
        Math.round(money);
        String formatted = NumberFormat.getCurrencyInstance(locale).format(money);
        current = formatted;
        return current;
    }
    // format infomation person
    public static String formatInfoperson(String str ){
        try {
            str = str.trim();
            str = str.replaceAll("\\s+", " ");
            String temp[] = str.split(" ");
            str = ""; // ? ^-^
            for (int i = 0; i < temp.length; i++) {
                str += String.valueOf(temp[i].charAt(0)).toUpperCase() + temp[i].substring(1);
                if (i < temp.length - 1) // ? ^-^
                    str += " ";
            }
        } catch (StringIndexOutOfBoundsException s) {
            str = "";
        }
        return str;
    }
}