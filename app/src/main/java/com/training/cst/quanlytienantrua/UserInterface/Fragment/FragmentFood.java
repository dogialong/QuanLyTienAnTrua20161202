package com.training.cst.quanlytienantrua.UserInterface.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.widget.EditText;
import android.widget.Toast;

import com.training.cst.quanlytienantrua.DataManager.Adapter.FragmentFoodAdapter;
import com.training.cst.quanlytienantrua.DataManager.Adapter.ItemClickListener;
import com.training.cst.quanlytienantrua.DataManager.Object.Food;
import com.training.cst.quanlytienantrua.Database.DatabaseUser;
import com.training.cst.quanlytienantrua.Helper.Contants;
import com.training.cst.quanlytienantrua.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * Created by longdg on 04/12/2016.
 */

public class FragmentFood extends Fragment {
    private List<Food> mListFood = new ArrayList<>();   // list chua danh sach food
    private DatabaseUser mDatabaseUser;
    private FragmentFoodAdapter mFragmentFoodAdapter;
    EditText etNameFood ;                               // editext nhap ten food
    EditText etPriceFood;                               // edittext nhap gia food
    private String currentText = "";                    // xu li chuyen doi tien
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myView = (View) inflater.inflate(R.layout.fragment_food,null,false);
        RecyclerView myRecycleView = (RecyclerView) myView.findViewById(R.id.fragment_food_rv);
        mDatabaseUser = new DatabaseUser(getContext());
        mListFood = mDatabaseUser.getFood();
        mFragmentFoodAdapter = new FragmentFoodAdapter(mDatabaseUser, getContext(), mListFood, new ItemClickListener() {
            @Override
            public void clickItemListtener(View view, int possition) {
               showDialog(possition);
            }
        });
        etNameFood = (EditText) myView.findViewById(R.id.fragment_food_ed_namefood);
        etPriceFood = (EditText) myView.findViewById(R.id.fragment_food_ed_pricefood);
        etPriceFood.addTextChangedListener(new TextWatcher() {
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
                        etPriceFood.removeTextChangedListener(this);

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
                        etPriceFood.setText(formatted);
                        etPriceFood.setSelection(formatted.length());
                        etPriceFood.addTextChangedListener(this);
                    }
                } catch (NumberFormatException n) {
                    s.clear();
                }
            }
        });
        setUpRecycleView(myRecycleView);
        setHasOptionsMenu(true);
        return myView;

    }
    // setup recycleview
    private void setUpRecycleView(RecyclerView rv) {
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
        rv.setAdapter(mFragmentFoodAdapter);
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mFragmentFoodAdapter!=null ) {
            mFragmentFoodAdapter.saveStates(outState);
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (mFragmentFoodAdapter != null ) {
            mFragmentFoodAdapter.restoreStates(savedInstanceState);
        }
    }
    // show dialog
    private void showDialogSave() {
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
                // save food.
                String nameFood = etNameFood.getText().toString();
                nameFood = Contants.formatInfoperson(nameFood);
                Matcher matcher = Contants.PATTERN.matcher(nameFood);
                final int countFood = mDatabaseUser.checkFood(mDatabaseUser.COLUMN_NAMEFOOD + "= ?", new String[]{nameFood});
                if (countFood < 1 && matcher.matches() && !nameFood.equals("")
                        && !etPriceFood.getText().toString().equals("")) {
                    long priceFood = Long.parseLong(Contants.replaceSymbol(etPriceFood.getText().toString()));
                    mDatabaseUser.insertFood(new Food(nameFood, priceFood));
                    mFragmentFoodAdapter.setmListFood(mDatabaseUser.getFood());
                    resetText();
                    Toast.makeText(getContext(), R.string.add_food_success, Toast.LENGTH_SHORT).show();
                } else {
                    resetText();
                    Toast.makeText(getContext(), R.string.missing_info, Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * hien thi dialog cho nguoi dung xac nhan hanh dong
     * type = 0 >> delete, type = 1 >> modify
     * position : vi tri cua item
     */
    private void showDialog (final int position) {
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
                // delete food.
                    mDatabaseUser.deleteFood(mDatabaseUser.COLUMN_NAMEFOOD + "=?"
                            ,new String[]{mListFood.get(position).getNameFood()});
                    mFragmentFoodAdapter.setmListFood(mDatabaseUser.getFood());
                    Toast.makeText(getContext(), R.string.food_delete_success, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
            }
        });
        dialog.show();
    }
    // reset text
    private void resetText(){
        etPriceFood.setText("");
        etNameFood.setText("");
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.menu_food_save, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.fragment_food_add) {
            // code chuyen fragment.
            showDialogSave();
//            FragmentPersonAdd fragment2 = new FragmentPersonAdd();
//            ((MainActivity) getActivity()).replaceFragment(fragment2, "Add person");
        }
        return super.onOptionsItemSelected(item);
    }
}
