package com.training.cst.quanlytienantrua.UserInterface.Fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.training.cst.quanlytienantrua.DataManager.Object.Person;
import com.training.cst.quanlytienantrua.Database.DatabaseUser;
import com.training.cst.quanlytienantrua.Helper.Contants;
import com.training.cst.quanlytienantrua.R;
import com.training.cst.quanlytienantrua.UserInterface.Activity.MainActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;

import static android.app.Activity.RESULT_OK;

public class FragmentPersonAdd extends Fragment {

    private static final String TAG = "FragmentPersonAdd";
    private DatabaseUser mDatabaseUser;
    private EditText etNamePerson, etDepartment, etNote;
    private ImageView ivAvatarPerson;
    private static int RESULT_LOAD_IMAGE = 1;  // lay ra image tu thu vien
    private String filePath = null; // Duong dan luu anh
    protected static final int CAMERA_REQUEST = 0;
    protected static final int GALLERY_PICTURE = 1;
    private Intent pictureActionIntent = null;
    Bitmap bitmap;

    private String selectedImagePath;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout for this fragment
        final View myView = inflater.inflate(R.layout.fragment_people_add_person, container, false);
        mDatabaseUser = new DatabaseUser(getContext());
        etNamePerson = (EditText) myView.findViewById(R.id.fragment_people_add_ed_personname);
        etDepartment = (EditText) myView.findViewById(R.id.fragment_people_add_ed_department);
        etNote = (EditText) myView.findViewById(R.id.fragment_people_add_ed_note);
        ivAvatarPerson = (ImageView) myView.findViewById(R.id.fragment_people_add_iv_avatar);
        ivAvatarPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder myAlertDialog = new AlertDialog.Builder(
                        getActivity());
                myAlertDialog.setTitle("Upload Pictures Option");
                myAlertDialog.setMessage("How do you want to set your picture?");

                myAlertDialog.setPositiveButton("Gallery",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {
                                Intent pictureActionIntent = null;

                                pictureActionIntent = new Intent(
                                        Intent.ACTION_PICK,
                                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(
                                        pictureActionIntent,
                                        GALLERY_PICTURE);

                            }
                        });

                myAlertDialog.setNegativeButton("Camera",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface arg0, int arg1) {

                                Intent intent = new Intent(
                                        MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(intent,
                                        CAMERA_REQUEST);

                            }
                        });
                myAlertDialog.show();

            }
        });
        setHasOptionsMenu(true);
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        return myView;

    }


    /**
     * Lay ra file anh de hien thi
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        bitmap = null;
        selectedImagePath = null;

        if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST) {
            bitmap = (Bitmap) data.getExtras().get("data");

            bitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, true);

            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), null, true);
            ivAvatarPerson.setImageBitmap(bitmap);
            saveImage(bitmap);

        } else if (resultCode == RESULT_OK && requestCode == GALLERY_PICTURE) {
            if (data != null) {
                Uri selectedImage = data.getData();
                String[] filePath1 = {MediaStore.Images.Media.DATA};
                Cursor c = getActivity().getContentResolver().query(selectedImage, filePath1,
                        null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath1[0]);
                selectedImagePath = c.getString(columnIndex);
                c.close();

                bitmap = BitmapFactory.decodeFile(selectedImagePath); // load
                // preview image
                bitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, false);
                filePath = selectedImagePath;
                ivAvatarPerson.setImageBitmap(bitmap);

            } else {
                Toast.makeText(getActivity(), "Cancelled",
                        Toast.LENGTH_SHORT).show();
            }
        }

    }

    //Save image to sdcard
    public void saveImage(Bitmap bitmap) {
        boolean success = false;
        String nameImageInSdcard = DateFormat.format("MM-dd-yy hh-mm-ss", new Date().getTime()).toString() + ".jpg";
        // save to sdcard
        File sdCardDirectory = Environment.getExternalStorageDirectory();
        File image = new File(sdCardDirectory.toString(), nameImageInSdcard);

        // Encode the file as a PNG image.
        FileOutputStream outStream;
        try {

            outStream = new FileOutputStream(image);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
        /* 100 to keep full quality of the image */

            outStream.flush();
            outStream.close();
            success = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (success) {
            filePath = image.getAbsolutePath();
        } else {
            Toast.makeText(getContext(),
                    "Error during image saving", Toast.LENGTH_LONG).show();
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
                // save person.
                String nameperson = etNamePerson.getText().toString();
                String department = etDepartment.getText().toString();
                String note = etNote.getText().toString();
                nameperson = Contants.formatInfoperson(nameperson);
                Matcher matcher = Contants.PATTERN.matcher(nameperson);
                Matcher matcher1 = Contants.PATTERN.matcher(department);
                Matcher matcher2 = Contants.PATTERN.matcher(note);
                final int countPerson = mDatabaseUser.checkPerson(mDatabaseUser.COLUMN_NAMEPERSON + "= ?", new String[]{nameperson});
                if (countPerson < 1 && matcher.matches() && matcher1.matches() && matcher2.matches()) {
                    mDatabaseUser.insertPerson(new Person(nameperson, department, note, filePath, 0,0,0));
                    List<Person> listPer = new ArrayList<>();
                    Toast.makeText(getContext(), R.string.add_person_success, Toast.LENGTH_SHORT).show();
                    listPer = mDatabaseUser.getPerson();
                    ((MainActivity) getActivity()).displayView(4);
                }   else if (nameperson.equals("")&& department.equals("") && note.equals("")) {
                    Toast.makeText(getContext(), R.string.missing_info, Toast.LENGTH_SHORT).show();
                } else if (countPerson < 1 && matcher.matches() && department.equals("")
                        || countPerson < 1 && matcher.matches()&& note.equals("")){
                    mDatabaseUser.insertPerson(new Person(nameperson, department, note, filePath,
                            0,0,0));
                    List<Person> listPer = new ArrayList<>();
                    Toast.makeText(getContext(), R.string.add_person_success, Toast.LENGTH_SHORT).show();
                    listPer = mDatabaseUser.getPerson();
                    ((MainActivity) getActivity()).displayView(1);
                }
                else {
                    Toast.makeText(getContext(), R.string.error_create_person, Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.menu_people_add, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.fragment_people_menu_save) {
            showDialogSave();
        }
        return super.onOptionsItemSelected(item);
    }


}
