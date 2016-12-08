package com.training.cst.quanlytienantrua.UserInterface.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.fragment_pay, container, false);
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_pay_rv);
        mDatabaseUser = new DatabaseUser(getContext());
        mListPerson = mDatabaseUser.getPerson();
        tvSizePeople = (TextView) view.findViewById(R.id.fragment_pay_tv_total_person);
        etTotalMoney = (EditText) view.findViewById(R.id.fragment_pay_et_money);
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
                        etTotalMoney.setSelection(formatted.length());
                        etTotalMoney.addTextChangedListener(this);
                    }
                } catch (NumberFormatException n) {
                    s.clear();
                }
            }
        });
        tvSizePeople.setText(String.valueOf(mListPerson.size()));     // Hien thi ra tong so nguoi
        initPosition(); // Khoi tao danh sach cac checkbox be checked
        // Khai bao adapter
        mFragmentPayAdapter = new FragmentPayAdapter(getContext(), mListPerson, new ItemClickListener() {
            @Override
            public void clickItemListtener(View view, int possition) {
                CheckBox chkPay = (CheckBox) view.findViewById(R.id.fragment_pay_item_recycleview_ckb);

                try {
                    if (chkPay.isChecked()) {
                        if (!mListPosition.contains(possition)) {
                            mListPosition.add(possition);
                            Collections.sort(mListPosition);
                        }
                    } else {
                        mListPosition.remove(mListPosition.indexOf(possition));
                        Toast.makeText(getContext(), mListPosition + "", Toast.LENGTH_SHORT).show();
                    }
                } catch (IndexOutOfBoundsException i) {

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
                if (!etTotalMoney.getText().toString().equals("") && mListPerson.size()>0){
                    for (int i = 0; i < mListPosition.size(); i++) {
                        Person person = new Person(mListPerson.get(mListPosition.get(i)).getNamePerson(),
                                mListPerson.get(mListPosition.get(i)).getDepartment(),
                                mListPerson.get(mListPosition.get(i)).getNote(),
                                mListPerson.get(mListPosition.get(i)).getmPathAvatar(),
                                Long.parseLong(Contants.replaceSymbol
                                        (etTotalMoney.getText().toString())) / mListPosition.size(),
                                mListPerson.get(mListPosition.get(i)).getParche(),
                                mListPerson.get(mListPosition.get(i)).getmAmount()-Long.parseLong(Contants.replaceSymbol
                                        (etTotalMoney.getText().toString())) / mListPosition.size(),
                                mListPerson.get(mListPosition.get(i)).getmSumPay()+Long.parseLong(Contants.replaceSymbol
                                        (etTotalMoney.getText().toString())) / mListPosition.size(),
                                getDateTime()
                                );

                        mDatabaseUser.updatePerson(person, mDatabaseUser.COLUMN_NAMEPERSON + " = ?",
                                new String[]{mListPerson.get(mListPosition.get(i)).getNamePerson()});
                        mDatabaseUser.insertHistory(new History(person.getNamePerson(),person.getPay(),
                                person.getmAmount(),getDateTime()));
                        mFragmentPayAdapter.loadNewList(mDatabaseUser.getPerson());
                    }
                    initPosition();
                    dialog.dismiss();
                    Toast.makeText(getContext(), R.string.pay_success, Toast.LENGTH_SHORT).show();
                } else {
                    dialog.dismiss();
                    Toast.makeText(getContext(), R.string.pay_error, Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.show();
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
