package com.training.cst.quanlytienantrua.UserInterface.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.training.cst.quanlytienantrua.DataManager.Adapter.FragmentPersonAdapter;
import com.training.cst.quanlytienantrua.DataManager.Adapter.ItemClickListener;
import com.training.cst.quanlytienantrua.DataManager.Object.Person;
import com.training.cst.quanlytienantrua.Database.DatabaseUser;
import com.training.cst.quanlytienantrua.R;
import com.training.cst.quanlytienantrua.UserInterface.Activity.MainActivity;

import java.util.ArrayList;
import java.util.List;


public class FragmentPeople extends Fragment {
    private List<Person> mListPerson = new ArrayList<>();
    private DatabaseUser mDatabaseUser;
    private FragmentPersonAdapter mFragmentPersonAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        RecyclerView myRecycleView = (RecyclerView) inflater.inflate(R.layout.fragment_people, container, false);
        mDatabaseUser = new DatabaseUser(getContext());
        mListPerson = mDatabaseUser.getPerson();
        mFragmentPersonAdapter = new FragmentPersonAdapter(getContext(), mListPerson,
                mDatabaseUser, new ItemClickListener() {
            @Override
            public void clickItemListtener(View view, int possition) {
                showDialog(possition);
            }
        }) ;
        setUpRecycleView(myRecycleView);
        setHasOptionsMenu(true);
        return myRecycleView;

    }

    // setup recycleview
    private void setUpRecycleView(final RecyclerView rv) {
        rv.setLayoutManager(new LinearLayoutManager(rv.getContext()));
        rv.setAdapter(mFragmentPersonAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.menu_people, menu);
    }
    // show dialog
    private void showDialog(final int position) {
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
                mDatabaseUser.deletePerson(mDatabaseUser.COLUMN_NAMEPERSON + " = ?",
                        new String[]{mListPerson.get(position).getNamePerson()});
//                mListPerson = mDatabaseUser.getPerson(null, null);
                mFragmentPersonAdapter.setmListPerson(mDatabaseUser.getPerson());
                Toast.makeText(getContext(), R.string.delete_person_success, Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.fragment_people_menu_add) {
            // code chuyen fragment.

            FragmentPersonAdd fragment2 = new FragmentPersonAdd();
            ((MainActivity) getActivity()).replaceFragment(fragment2, "Add person");
        }
        return super.onOptionsItemSelected(item);
    }

}
