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

import com.training.cst.quanlytienantrua.DataManager.Adapter.FragmentRechargeAdapter;
import com.training.cst.quanlytienantrua.DataManager.Adapter.ItemClickListener;
import com.training.cst.quanlytienantrua.DataManager.Object.Person;
import com.training.cst.quanlytienantrua.Database.DatabaseUser;
import com.training.cst.quanlytienantrua.Helper.Contants;
import com.training.cst.quanlytienantrua.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.fragment_recharge, container, false);
        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.fragment_recharge_rv);
        mDatabaseUser = new DatabaseUser(getContext());    //Khoi tao DatabaseUser
        mListPerson = mDatabaseUser.getPerson();
        initPosition(); // Khoi tao danh sach cac checkbox be checked

        // Khoi tao Adapter
        mFragmentRechareAdapter = new FragmentRechargeAdapter(getContext(), mListPerson, new ItemClickListener() {
            @Override
            public void clickItemListtener(View view, final int possition) {
                CheckBox chkRecharge = (CheckBox) view.findViewById(R.id.fragment_pay_item_recycleview_ckb);
                try {
                    if (chkRecharge.isChecked()) {
                        if (!mListPosition.contains(possition)) {
                            mListPosition.add(possition);
                            Collections.sort(mListPosition);
                        }
                    } else {
                        mListPosition.remove(mListPosition.indexOf(possition));
                    }
                } catch (IndexOutOfBoundsException i) {

                }
            }

        });

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
                        etAmount.setSelection(formatted.length());
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
                if (!etAmount.getText().toString().equals("")) {
                    for (int i = 0; i < mListPosition.size(); i++) {

                        Person person = new Person(mListPerson.get(mListPosition.get(i)).getNamePerson(),
                                mListPerson.get(mListPosition.get(i)).getDepartment(),
                                mListPerson.get(mListPosition.get(i)).getNote(),
                                mListPerson.get(mListPosition.get(i)).getmPathAvatar(),
                                mListPerson.get(mListPosition.get(i)).getPay(),
                                Long.parseLong(Contants.replaceSymbol(etAmount.getText().toString())),
                                mListPerson.get(mListPosition.get(i)).getmAmount()+ Long.parseLong(
                                                Contants.replaceSymbol(etAmount.getText().toString()))

                        );
                        mDatabaseUser.updatePerson(person, mDatabaseUser.COLUMN_NAMEPERSON + " = ?",
                                new String[]{mListPerson.get(mListPosition.get(i)).getNamePerson()});
                        mFragmentRechareAdapter.loadNewList(mDatabaseUser.getPerson());
                        tvTotalMoney.setText(Contants.formatCurrencyForTextView(String.valueOf
                                (Long.parseLong(Contants.replaceSymbol
                                        (etAmount.getText().toString()))*mListPosition.size()),"",moneyCurrent));
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
}
