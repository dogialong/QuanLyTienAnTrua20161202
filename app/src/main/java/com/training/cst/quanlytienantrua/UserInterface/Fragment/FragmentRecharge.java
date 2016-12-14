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

import com.training.cst.quanlytienantrua.DataManager.Adapter.FragmentRechargeAdapter;
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

public class FragmentRecharge extends Fragment {
    private FragmentRechargeAdapter mFragmentRechareAdapter;    // Adapter cho listview
    private DatabaseUser mDatabaseUser;                       // Co so du lieu.
    private List<Person> mListPerson = new ArrayList<>();    // Danh sach nhan vien
    private TextView tvSizePeople;                          // Hien thi tong so nguoi trong cong ty
    private TextView tvTotalMoney;                        // HIen thi ra tong so tien cua moi nguoi
    private EditText etAmount;
    private List<Integer> mListPosition;                 // chua danh sach nhan vien duoc chon
    private String currentText = "";                    // Dung de xu li khi ngdung nhap so tien
    private long moneyCurrent = 0;                  // Dung de xu li khi ngdung nhap so tien
    private RadioGroup radioCkbGroup;
    private TextView tvRechargeDemo;
    private List<String> mListString;
    private List<String> mListString2;
    private List<Boolean> mListBoolean ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.fragment_recharge, container, false);
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_recharge_rv);
        mDatabaseUser = new DatabaseUser(getContext());    //Khoi tao DatabaseUser
        mListPerson = mDatabaseUser.getPerson();
        mListString = new ArrayList<>();
        mListString2 = new ArrayList<>();
        initPosition(); // Khoi tao danh sach cac checkbox be checked
        initStringList();
        initBoolean(true);
        tvRechargeDemo = (TextView) view.findViewById(R.id.tvTryRecharge);
        radioCkbGroup = (RadioGroup) view.findViewById(R.id.radioCheck);
        // Khoi tao Adapter
        mFragmentRechareAdapter = new FragmentRechargeAdapter(getContext(), mListPerson,mListString,
                mListBoolean, new ItemClickListener() {
            @Override
            public void clickItemListtener(View view, final int possition) {
                CheckBox chkRecharge = (CheckBox) view.findViewById(R.id.fragment_pay_item_recycleview_ckb);
                try {
                    if (chkRecharge.isChecked()) {
                        if (!mListPosition.contains(possition)) {
                            mListPosition.add(possition);
                            mListBoolean.set(possition,true);
                            Collections.sort(mListPosition);
                            tvTotalMoney.setText(Contants.formatCurrencyForTextView(String.valueOf
                                    (getAmount()),"",moneyCurrent));
                        }
                    } else {
                        mListBoolean.set(possition,false);
                        mListPosition.remove(mListPosition.indexOf(possition));
                        tvTotalMoney.setText(Contants.formatCurrencyForTextView(String.valueOf
                                (getAmount()),"",moneyCurrent));
                    }
                } catch (IndexOutOfBoundsException i) {

                }
            }

        });
        mFragmentRechareAdapter.loadNewBoolean(initBoolean(true));
        tvSizePeople = (TextView) view.findViewById(R.id.fragment_recharge_tv_total_person);
        tvTotalMoney = (TextView) view.findViewById(R.id.fragment_recharge_tv_total_money);
        etAmount = (EditText) view.findViewById(R.id.fragment_recharge_et_money);
        // chuyen doi so tien nhap vao thanh định dạng vnđ
        etAmount.addTextChangedListener(new TextWatcher() {
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
                        etAmount.removeTextChangedListener(this);

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
                        etAmount.setText(formatted);
                        etAmount.setSelection(formatted.length()-2);
                        etAmount.addTextChangedListener(this);
                    }
                } catch (NumberFormatException n) {
                    s.clear();

                } catch (IndexOutOfBoundsException e ){

                }
            }
        });
        tvSizePeople.setText(String.valueOf(mListPerson.size()));     // Hien thi ra tong so nguoi
        try {
            tvTotalMoney.setText(Contants.formatCurrencyForTextView(String.valueOf
                    (Long.parseLong(Contants.replaceSymbol
                            (etAmount.getText().toString()))*mListPosition.size()),"",moneyCurrent));
        } catch (NumberFormatException n){
            tvTotalMoney.setText("0.0");
        }
        addListenerOnButton();
        tvRechargeDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    for (int i = 0; i < mListPosition.size(); i++) {
                        mListString2.set(mListPosition.get(i),String.valueOf(Long.parseLong(Contants.replaceSymbol
                                (etAmount.getText().toString()))));
                    }
                } catch (NumberFormatException n) {

                }
                Log.d("ahi", "paydemo: " + mListBoolean);
                mFragmentRechareAdapter.loadNewListString(mListString2,mListBoolean);
               try{
                   tvTotalMoney.setText(Contants.formatCurrencyForTextView(String.valueOf
                           (Long.parseLong(Contants.replaceSymbol
                                   (etAmount.getText().toString()))*mListPosition.size()),"",moneyCurrent));
               } catch (NumberFormatException n) {}
                switch (radioCkbGroup.getCheckedRadioButtonId()) {
                    case R.id.radioCheckAll:
                        Log.d("ahi", "onClickinradio: " + mListPosition);
                        Log.d("ahi", "onClickinradio: " + mListBoolean);
                        break;
                    case R.id.radioUnCheckAll:
//                        mListPosition.clear();
                        mFragmentRechareAdapter.loadNewListString(mListString2,mListBoolean);
                        Log.d("ahi", "onClickinradiouncheck: " + mListPosition);
                        break;
                    default:break;
                }
                etAmount.setText("");
            }
        });
        setUpRecycleView(mRecyclerView);
        setHasOptionsMenu(true);
        return view;
    }
    // setup recycleview
    private void setUpRecycleView(RecyclerView rv) {
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
        rv.setAdapter(mFragmentRechareAdapter);
    }

    // Khoi tao listPosition
    private void initPosition() {
        mListPosition = new ArrayList<>();
        mListPosition.clear();
        for (int i = 0; i < mListPerson.size(); i++) {
            mListPosition.add(i);
        }
    }
    private long getAmount() {
        long total = 0;
        try {
            for (int i = 0; i < mListPosition.size(); i++) {
                total += Long.parseLong(Contants.replaceSymbol(mListString2.get(mListPosition.get(i))));
            }
        } catch (NumberFormatException n) {

        }
        return total;
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
                if (!etAmount.getText().toString().equals("") && checkRecharge(mListString2)) {
                    for (int i = 0; i < mListPosition.size(); i++) {

                        Person person = new Person(mListPerson.get(mListPosition.get(i)).getNamePerson(),
                                mListPerson.get(mListPosition.get(i)).getDepartment(),
                                mListPerson.get(mListPosition.get(i)).getNote(),
                                mListPerson.get(mListPosition.get(i)).getmPathAvatar(),
                                0,
                                Long.parseLong(Contants.replaceSymbol(mListString2.get(mListPosition.get(i)))),
                                mListPerson.get(mListPosition.get(i)).getmAmount()+
                                        Long.parseLong(Contants.replaceSymbol(mListString2.get(mListPosition.get(i))))

                        );
                        mDatabaseUser.updatePerson(person, mDatabaseUser.COLUMN_NAMEPERSON + " = ?",
                                new String[]{mListPerson.get(mListPosition.get(i)).getNamePerson()});
                        mDatabaseUser.insertHistory(new History(person.getNamePerson(),person.getPay(),
                                person.getmAmount(),getDateTime()));
                        mFragmentRechareAdapter.loadNewBoolean(mListBoolean);
                        tvTotalMoney.setText(Contants.formatCurrencyForTextView(String.valueOf
                                (Long.parseLong(Contants.replaceSymbol
                                        (etAmount.getText().toString()))*mListPosition.size()),"",moneyCurrent));
                        etAmount.setText("");
                    }
                    Toast.makeText(getContext(), R.string.recharge_success, Toast.LENGTH_SHORT).show();
                    initPosition();
                } else {
                    Toast.makeText(getContext(), R.string.recharge_falied, Toast.LENGTH_SHORT).show();
                }

                dialog.dismiss();
            }
        });
        dialog.show();
    }
    private boolean checkRecharge (List<String> mListring) {
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
    private void initStringList() {
        mListString.clear();
        mListString2.clear();
        for (int i = 0; i < mListPerson.size(); i++) {
            mListString.add("0");
            mListString2.add("0");
        }
    }
    // xu li su kien checkbox check hay khong
    public void addListenerOnButton() {
        RadioButton rdbCheckAll = (RadioButton) radioCkbGroup.findViewById(R.id.radioCheckAll);
        rdbCheckAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mFragmentPayAdapter.checkboxSelected(true);
                mFragmentRechareAdapter.loadNewBoolean(initBoolean(true));
                initPosition();
                Log.d("ahi", "onClick1: "  + mListPosition);
            }
        });
        RadioButton rdbUnCheckall = (RadioButton) radioCkbGroup.findViewById(R.id.radioUnCheckAll);
        rdbUnCheckall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mFragmentPayAdapter.checkboxSelected(false);
                mFragmentRechareAdapter.loadNewBoolean(initBoolean(false));
                mListPosition.clear();
                tvTotalMoney.setText(Contants.formatCurrencyForTextView(String.valueOf
                        (0+""),"",moneyCurrent));
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
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.menu_recharge, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.fragment_recharge_save) {
            showDialog();
        }
        return super.onOptionsItemSelected(item);
    }
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }
}
