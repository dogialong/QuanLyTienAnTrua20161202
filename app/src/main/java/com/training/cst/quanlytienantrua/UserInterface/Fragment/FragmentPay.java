package com.training.cst.quanlytienantrua.UserInterface.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.training.cst.quanlytienantrua.DataManager.Adapter.FragmentPayAdapter;
import com.training.cst.quanlytienantrua.DataManager.Adapter.ItemClickListener;
import com.training.cst.quanlytienantrua.DataManager.Object.History;
import com.training.cst.quanlytienantrua.DataManager.Object.Person;
import com.training.cst.quanlytienantrua.Database.DatabaseUser;
import com.training.cst.quanlytienantrua.Helper.Contants;
import com.training.cst.quanlytienantrua.R;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FragmentPay extends Fragment {
    private DatabaseUser mDatabaseUser;                      // Co so du lieu.
    private List<Person> mListPerson = new ArrayList<>();    // Danh sach nhan vien
    private TextView tvSizePeople;                           // Hien thi tong so nguoi trong cong ty
    private EditText etTotalMoney;                           // Dien vao tong so tien cua moi nguoi
    private FragmentPayAdapter mFragmentPayAdapter;          // Khai bao adpater.
    private List<Integer> mListPosition;                    // chua danh sach nhan vien duoc chon
    private String currentText = "";                         // Dung de xu li khi ngdung nhap so tien
    private RadioGroup radioCkbGroup;
    private TextView tvPayDemo;
    private List<String> mListString;
    private List<String> mListString2;
    private List<Boolean> mListBoolean ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.fragment_pay, container, false);
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_pay_rv);
        mDatabaseUser = new DatabaseUser(getContext());
        mListString = new ArrayList<>();
        mListString2 = new ArrayList<>();
        mListPerson = mDatabaseUser.getPerson();
        initStringList();
        initBoolean(true);
        tvSizePeople = (TextView) view.findViewById(R.id.fragment_pay_tv_total_person);
        etTotalMoney = (EditText) view.findViewById(R.id.fragment_pay_et_money);
        tvPayDemo = (TextView) view.findViewById(R.id.tvTryPay);
        radioCkbGroup = (RadioGroup) view.findViewById(R.id.radioCheck);
        etTotalMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    if (!s.toString().equals(currentText)) {
                        etTotalMoney.removeTextChangedListener(this);

                        String replaceable = String.format("[%s,.\\s]",
                                NumberFormat.getCurrencyInstance().getCurrency().getSymbol());
                        String cleanString = s.toString().replaceAll(replaceable, "");

                        double parsed;
                        try {
                            parsed = Double.parseDouble(cleanString);
                        } catch (NumberFormatException e) {
                            parsed = 0.00;
                        }
                        NumberFormat formatter = NumberFormat.getCurrencyInstance();
                        formatter.setMaximumFractionDigits(0);
                        String formatted = formatter.format((parsed));

                        currentText = formatted;
                        etTotalMoney.setText(formatted);
                        etTotalMoney.setSelection(formatted.length()-2);
                        etTotalMoney.addTextChangedListener(this);
                    }
                } catch (NumberFormatException n) {
                    s.clear();
                }
            }
        });
        tvSizePeople.setText(String.valueOf(mListPerson.size()));     // Hien thi ra tong so nguoi
        initPosition();                                 // Khoi tao danh sach cac checkbox be checked
        addListenerOnButton();
        // Khai bao adapter
        mFragmentPayAdapter = new FragmentPayAdapter(getContext(), mListPerson,mListString,mListBoolean,
                new ItemClickListener() {
            @Override
            public void clickItemListtener(View view, int possition) {
                CheckBox chkPay = (CheckBox) view.findViewById(R.id.fragment_pay_item_recycleview_ckb);

                try {
                    if (chkPay.isChecked()) {
                        if (!mListPosition.contains(possition)) {
                            mListPosition.add(possition);
                            mListBoolean.set(possition,true);
                            Collections.sort(mListPosition);
                            Log.d("cc", "mFragmentPayAdapter: " + mListBoolean);
                        }
                    } else {
                        mListBoolean.set(possition,false);
                        mListPosition.remove(mListPosition.indexOf(possition));
                    }
                } catch (IndexOutOfBoundsException i) {

                }
            }
        });
        mFragmentPayAdapter.loadNewBoolean(initBoolean(true));
        tvPayDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    for (int i = 0; i < mListPosition.size(); i++) {
                        mListString2.set(mListPosition.get(i),String.valueOf(Long.parseLong(Contants.replaceSymbol
                                (etTotalMoney.getText().toString())) / mListPosition.size()));
                    }
                } catch (NumberFormatException n) {

                }
                Log.d("ahi", "paydemo: " + mListBoolean);
                mFragmentPayAdapter.loadNewListString(mListString2,mListBoolean);
                switch (radioCkbGroup.getCheckedRadioButtonId()) {
                    case R.id.radioCheckAll:
                        Log.d("ahi", "onClickinradio: " + mListPosition);
                        Log.d("ahi", "onClickinradio: " + mListBoolean);
                        break;
                    case R.id.radioUnCheckAll:
//                        mListPosition.clear();
                        mFragmentPayAdapter.loadNewListString(mListString2,mListBoolean);
                        Log.d("ahi", "onClickinradiouncheck: " + mListPosition);
                        break;
                    default:break;
                }
            }
        });
        setUpRecycleView(mRecyclerView);
        setHasOptionsMenu(true);
        return view;

    }

    // setup recycleview
    private void setUpRecycleView(RecyclerView rv) {
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
        rv.setAdapter(mFragmentPayAdapter);

    }

    // Khoi tao listPosition
    private void initPosition() {
        mListPosition = new ArrayList<>();
        mListPosition.clear();
        for (int i = 0; i < mListPerson.size(); i++) {
            mListPosition.add(i);
        }
    }
    // Khoi tao list string
    private void initStringList() {
        mListString.clear();
        mListString2.clear();
        for (int i = 0; i < mListPerson.size(); i++) {
            mListString.add("0");
            mListString2.add("0");
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
//                mFragmentPayAdapter.checkboxSelected(false);
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.btnOk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!etTotalMoney.getText().toString().equals("") && mListPerson.size()>0 && checkPay(mListString2)){
                    if(mListPosition.size() > 0){
                        try {
                            for (int i = 0; i < mListPosition.size(); i++) {
                                Person person = new Person(mListPerson.get(mListPosition.get(i)).getNamePerson(),
                                        mListPerson.get(mListPosition.get(i)).getDepartment(),
                                        mListPerson.get(mListPosition.get(i)).getNote(),
                                        mListPerson.get(mListPosition.get(i)).getmPathAvatar(),
                                        Long.parseLong(Contants.replaceSymbol(mListString2.get(mListPosition.get(i)))),
                                        mListPerson.get(mListPosition.get(i)).getParche(),
                                        mListPerson.get(mListPosition.get(i)).getmAmount()-
                                                Long.parseLong(Contants.replaceSymbol(mListString2.get(mListPosition.get(i)))),
                                        mListPerson.get(mListPosition.get(i)).getmSumPay()+
                                                Long.parseLong(Contants.replaceSymbol(mListString2.get(mListPosition.get(i)))),
                                        getDateTime()
                                );

                                mDatabaseUser.updatePerson(person, mDatabaseUser.COLUMN_NAMEPERSON + " = ?",
                                        new String[]{mListPerson.get(mListPosition.get(i)).getNamePerson()});
                                mDatabaseUser.insertHistory(new History(person.getNamePerson(),person.getPay(),
                                        person.getmAmount(),getDateTime()));
                                mFragmentPayAdapter.loadNewBoolean(mListBoolean);
//                                Log.d("cc", "Save: " + mListBoolean);
                            }
                        } catch (NumberFormatException n){

                        }
                        dialog.dismiss();
                        Toast.makeText(getContext(), R.string.pay_success, Toast.LENGTH_SHORT).show();
                    } else {
                        dialog.dismiss();
                        Log.d("ahi", "onClick3: " + "ahihihi");
                        Toast.makeText(getContext(), "Please select people you want pay or choose Check all", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    dialog.dismiss();
                    Toast.makeText(getContext(), R.string.pay_error, Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
    }
    private boolean checkPay (List<String> mListring) {
        boolean check = false;
        int countCheck = 0;
        for (int i = 0; i < mListPosition.size() ; i++) {
            if(!mListring.get(mListPosition.get(i)).equals("0")){
                countCheck ++;
                if(countCheck == mListPosition.size()){
                    check = true;
                    break;
                }
            }
        }

        return check;
    }
    // xu li su kien checkbox check hay khong
    public void addListenerOnButton() {
        RadioButton rdbCheckAll = (RadioButton) radioCkbGroup.findViewById(R.id.radioCheckAll);
        rdbCheckAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mFragmentPayAdapter.checkboxSelected(true);
                mFragmentPayAdapter.loadNewBoolean(initBoolean(true));
                initPosition();
                Log.d("ahi", "onClick1: "  + mListPosition);
            }
        });
        RadioButton rdbUnCheckall = (RadioButton) radioCkbGroup.findViewById(R.id.radioUnCheckAll);
        rdbUnCheckall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mFragmentPayAdapter.checkboxSelected(false);
                mFragmentPayAdapter.loadNewBoolean(initBoolean(false));
                mListPosition.clear();
                Log.d("ahi", "onClick2: "  + mListPosition);
            }
        });
    }
    // init list boolean
    private List<Boolean> initBoolean (Boolean checked) {
        mListBoolean = new ArrayList<>();
        for(int i = 0 ; i < mListPerson.size();i++){
            mListBoolean.add(checked);
        }
        return mListBoolean;
    }
    // get datetime
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.menu_main2, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.cancel) {
            showDialog();
        }
        return super.onOptionsItemSelected(item);
    }

}
