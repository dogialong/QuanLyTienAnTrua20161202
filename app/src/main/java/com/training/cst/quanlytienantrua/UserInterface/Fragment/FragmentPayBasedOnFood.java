package com.training.cst.quanlytienantrua.UserInterface.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.training.cst.quanlytienantrua.DataManager.Adapter.FragmentPayFoodAdapter;
import com.training.cst.quanlytienantrua.DataManager.Adapter.ItemClickListener;
import com.training.cst.quanlytienantrua.DataManager.Adapter.SpinnerAdapter;
import com.training.cst.quanlytienantrua.DataManager.Object.Food;
import com.training.cst.quanlytienantrua.DataManager.Object.History;
import com.training.cst.quanlytienantrua.DataManager.Object.Person;
import com.training.cst.quanlytienantrua.Database.DatabaseUser;
import com.training.cst.quanlytienantrua.Helper.Contants;
import com.training.cst.quanlytienantrua.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by longdg on 05/12/2016.
 */

public class FragmentPayBasedOnFood extends Fragment {
    private DatabaseUser mDatabaseUser;
    private List<Food> mListFood;
    private List<String> mListString ;
    private List<Person> mListPerson;
    private List<Integer>   mListPosition ;      // luu vi tri chk trong spinner
    private List<Integer> mListPositionCkb ;    // luu vi tri chk person
    private List<Boolean> mListChecked ;    // luu vi tri chk person
    private SpinnerAdapter mSpinnerAdapter;
    private FragmentPayFoodAdapter mFragmentPayFoodAdapter;
    String mString;
    long moneyPay;                             // so tien can phai thanh toan
    String [] arrString;
    Spinner spinner;
    private RadioGroup radioCkbGroup;
    private CheckBox chkAll;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View myView = (View) inflater.inflate(R.layout.fragment_pay_baseonfood, null, false);
        RecyclerView mRecyclerView = (RecyclerView) myView.findViewById(R.id.fragment_food_rv);
        chkAll = (CheckBox) myView.findViewById(R.id.chkPayFood);
        initSpinner();
        mListPosition = new ArrayList<>();
        mListPositionCkb = new ArrayList<>();
        mListPerson = new ArrayList<>();
        mListPerson = mDatabaseUser.getPerson();
//        initPosition();
        initStringList();
        initBoolean(false);
        spinner = (Spinner) myView.findViewById(R.id.myspinner);
        spinner.setAdapter(mSpinnerAdapter);
        spinner.setDropDownVerticalOffset(50);
        spinner.setDropDownHorizontalOffset(40);
        spinner.setSelection(mSpinnerAdapter.getCount());
        if(mListPositionCkb.size() == 0) {
            spinner.setVisibility(View.INVISIBLE);
        }
        radioCkbGroup = (RadioGroup) myView.findViewById(R.id.radioCheck);
        mFragmentPayFoodAdapter = new FragmentPayFoodAdapter(getContext(), mListPerson, mListString,
                mListChecked,
                new ItemClickListener() {

                    @Override
                    public void clickItemListtener(View view, int possition) {
                        CheckBox chkPer = (CheckBox) view.findViewById(R.id.fragment_pay_food_item_recycleview_ckb);
                        try {
                            if (chkPer.isChecked()) {
                                if (!mListPositionCkb.contains(possition)) {
                                    mListPositionCkb.add(possition);
                                    Collections.sort(mListPositionCkb);
                                    mListChecked.set(possition,true);
                                    Log.d("hihi", "clickItemListtener: " + mListChecked);
                                    if(mListPositionCkb.size()>0){
                                        spinner.setVisibility(View.VISIBLE);
                                    }
                                    mListPosition.clear();
                                }
                            } else {
                                mListChecked.set(possition,false);
                                mListPositionCkb.remove(mListPositionCkb.indexOf(possition));
                                if(mListPositionCkb.size()==0) {
                                    spinner.setVisibility(View.INVISIBLE);
                                }
                                mListPosition.clear();
                            }
                        } catch (IndexOutOfBoundsException i) {
                            Toast.makeText(getContext(), "err", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        mFragmentPayFoodAdapter.loadCheckbox(initBoolean(false));
        Button btnPay = (Button) myView.findViewById(R.id.fragment_pay_food_btnpay);
        chkAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chkAll.isChecked()) {
                    initmListPostionCkb();
                    initBoolean(true);
                    if(mListPositionCkb.size()>0){
                        spinner.setVisibility(View.VISIBLE);
                    }
                    Log.d("ahi", "onClick: " + mListPositionCkb);
                    mFragmentPayFoodAdapter.loadCheckbox(mListChecked);

                } else {
                    mListChecked.clear();
                    mListPositionCkb.clear();
                    initBoolean(false);
                    spinner.setVisibility(View.INVISIBLE);
                    mFragmentPayFoodAdapter.loadCheckbox(mListChecked);
                }
            }
        });
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        setUpRecycleView(mRecyclerView);
        return myView;
    }

    // init multiSelection spinner
    private void initSpinner() {
        mDatabaseUser = new DatabaseUser(getContext());
        mListFood = mDatabaseUser.getFood();
        mListFood.add(new Food("Select food to pay", 0));
        mSpinnerAdapter = new SpinnerAdapter(getContext(), mListFood, new ItemClickListener() {
            @Override
            public void clickItemListtener(View view, int possition) {
                CheckBox chkFood = (CheckBox) view.findViewById(R.id.ckbfood);
                    try {
                        if (chkFood.isChecked()) {
                            if (!mListPosition.contains(possition)) {
                                mListPosition.add(possition);
                                Collections.sort(mListPosition);
                                Log.d("hih", "clickItemListtener: " + mListChecked);
                            }
                        } else {
                            mListPosition.remove(mListPosition.indexOf(possition));
                        }
                        StringBuilder stringBuilder = new StringBuilder();
                        if (mListPosition.size() > 0) {
                            for (int i = 0; i < mListPosition.size(); i++) {
                                stringBuilder.append(Contants.handlerTextToLong2(mListFood.get(mListPosition.get(i)).getNameFood()));
                                if(i < mListPosition.size()-1) {
                                    stringBuilder.append(",");
                                }
                            }
                            mString = stringBuilder.toString();
                        }  else {
                            mString = "";
                        }

                    } catch (IndexOutOfBoundsException i) {

                    }
                    for (int i = 0; i < mListPositionCkb.size(); i++) {
                        mListString.set(mListPositionCkb.get(i), mString);
                    }
                    moneyPay = 0;               // reset lai so tien
                Log.d("ahhi", "clickItemListtener: " + mListChecked);
//                mFragmentPayFoodAdapter.loadCheckbox(mListChecked);
                mFragmentPayFoodAdapter.loadCheckbox(mListChecked);

            }
        });
    }
    // init list boolean
    private List<Boolean> initBoolean (Boolean checked) {
        mListChecked = new ArrayList<>();
        for(int i = 0 ; i < mListPerson.size();i++){
            mListChecked.add(checked);
        }
        return mListChecked;
    }
    // init list checkbox
    private void initmListPostionCkb() {
        mListPositionCkb.clear();
        for (int i = 0; i < mListPerson.size(); i++) {
            mListPositionCkb.add(i);
        }
    }
    // show dialog
    private void showDialog() {
        final Dialog dialog = new Dialog(getContext());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);

        dialog.findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.btnOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    try {
                        if (mListPositionCkb.size() > 0) {
                            for (int i = 0; i < mListPositionCkb.size(); i++) {
                                if(!mListString.get(mListPositionCkb.get(i)).toString().equals("")){
                                    // xu li viec pay dung khi o day
                                    moneyPay = 0;
                                    arrString = mListString.get(mListPositionCkb.get(i)).toString().split(",");
                                    for(int j = 0; j < arrString.length; j++ ) {
                                        moneyPay += mDatabaseUser.getPriceFood(arrString[j]);
                                    }
                                    Person person = new Person(mListPerson.get(mListPositionCkb.get(i)).getNamePerson(),
                                            mListPerson.get(mListPositionCkb.get(i)).getDepartment(),
                                            mListPerson.get(mListPositionCkb.get(i)).getNote(),
                                            mListPerson.get(mListPositionCkb.get(i)).getmPathAvatar(),
                                            moneyPay,
                                            mListPerson.get(mListPositionCkb.get(i)).getParche(),
                                            mListPerson.get(mListPositionCkb.get(i)).getmAmount()-moneyPay);

                                    mDatabaseUser.updatePerson(person, mDatabaseUser.COLUMN_NAMEPERSON + " = ?",
                                            new String[]{mListPerson.get(mListPositionCkb.get(i)).getNamePerson()});
                                    mDatabaseUser.insertHistory(new History(person.getNamePerson(),person.getPay(),
                                            person.getmAmount(),getDateTime()));
                                    dialog.dismiss();
                                    Toast.makeText(getContext(), R.string.pay_success, Toast.LENGTH_SHORT).show();
                                } else {
                                    // pay sai. gia tri dang la ""
                                    dialog.dismiss();
                                    Toast.makeText(getContext(), "Person " +mListPerson.get( mListPositionCkb.
                                            get(i)).getNamePerson() + " is missing info", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            dialog.dismiss();
                            Toast.makeText(getContext(), R.string.missing_info, Toast.LENGTH_SHORT).show();
                        }

                    } catch (IndexOutOfBoundsException i) {
                        Log.d("Error", "onClick: " + i.toString());
                    }
                }
        });
        dialog.show();
    }

    // Khoi tao list string
    private void initStringList() {
        mListString = new ArrayList<>();
        for (int i = 0; i < mListPerson.size(); i++) {
            mListString.add("");
        }
    }
    // setup recycleview
    private void setUpRecycleView(RecyclerView rv) {
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
        rv.setAdapter(mFragmentPayFoodAdapter);

    }
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
