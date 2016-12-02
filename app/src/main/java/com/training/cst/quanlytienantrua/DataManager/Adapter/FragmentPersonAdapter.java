package com.training.cst.quanlytienantrua.DataManager.Adapter;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.training.cst.quanlytienantrua.DataManager.Object.Person;
import com.training.cst.quanlytienantrua.Database.DatabaseUser;
import com.training.cst.quanlytienantrua.R;

import java.io.File;
import java.util.List;

/**
 * Created by longdg123 on 11/23/2016.
 */

public class FragmentPersonAdapter extends RecyclerView.Adapter<FragmentPersonAdapter.ViewHolder> {
    private List<Person> mListPerson;
    private Context mContext;
    private DatabaseUser mDatabaseUser;
    ItemClickListener itemClickListener;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_people_item_rv, parent, false);
        return new ViewHolder(view);
    }

    public void setmListPerson(List<Person> ListPerson) {
        mListPerson.clear();
        mListPerson.addAll(ListPerson);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final int positionFake = position;
        holder.tvNamePerson.setText(mListPerson.get(position).getNamePerson());
        holder.ivAvatar.setImageResource(R.drawable.ic_user);
        try {
            if ((new File(mListPerson.get(position).getmPathAvatar())).exists()
                    && !mListPerson.get(position).getmPathAvatar().equals("")) {
                holder.ivAvatar.setImageBitmap(BitmapFactory.decodeFile(mListPerson.get(position).getmPathAvatar()));
            }
        } catch (NullPointerException n) {

        }
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mDatabaseUser.deletePerson(mDatabaseUser.COLUMN_NAMEPERSON + " = ?", new String []{holder.tvNamePerson.getText().toString()});
//                notifyDataSetChanged();
                itemClickListener.clickItemListtener(v, positionFake);
            }
        });
    }

    public FragmentPersonAdapter(Context context, List<Person> listPerson, DatabaseUser mDatabaseUser, ItemClickListener itemClickListener) {
        this.mContext = context;
        this.mListPerson = listPerson;
        this.mDatabaseUser = mDatabaseUser;
        this.itemClickListener = itemClickListener;
    }

    @Override
    public int getItemCount() {
        return mListPerson.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView ivAvatar;
        public final TextView tvNamePerson;
        public final Button btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            ivAvatar = (ImageView) itemView.findViewById(R.id.fragment_people_item_rv_iv);
            tvNamePerson = (TextView) itemView.findViewById(R.id.fragment_people_item_rv_tv_name);
            btnDelete = (Button) itemView.findViewById(R.id.fragment_people_item_rv_btn_delete);
        }
    }
}
