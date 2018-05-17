package com.example.davide.esercizio_mongo_retrofit.Fragments;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.davide.esercizio_mongo_retrofit.MainActivity;
import com.example.davide.esercizio_mongo_retrofit.R;
import com.example.davide.esercizio_mongo_retrofit.Utils.Costants;
import com.example.davide.esercizio_mongo_retrofit.data_model.Response;
import com.example.davide.esercizio_mongo_retrofit.data_model.User;
import com.example.davide.esercizio_mongo_retrofit.network.NetworkUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import retrofit2.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static com.example.davide.esercizio_mongo_retrofit.Utils.Validation.validateEmail;
import static com.example.davide.esercizio_mongo_retrofit.Utils.Validation.validateFields;

public class DeleteAccountDialog extends DialogFragment{

    public static final  String TAG = DeleteAccountDialog.class.getName();

    private TextView txtDeleteAccount;
    private Button btnDeleteAccountYes;
    private Button getBtnDeleteAccountNo;
    private EditText mEtEmail;
    private TextInputLayout mTiName;
    private ProgressBar mProgressbar;

    private CompositeSubscription compositeSubscription;

    private String email;
    private String name;
    private User user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance)
    {
        View view = inflater.inflate(R.layout.delete_account,container,false);
        compositeSubscription = new CompositeSubscription();
        getData();
        initViews(view);
        return view;
    }

    private void getData(){
        Bundle bundle = getArguments();

        email = bundle.getString(Costants.EMAIL);
        name = bundle.getString(Costants.NAME);
        user = new User();
        user.setEmail(email);
        user.setName(name);

    }

    private void initViews(View v)
    {
        mEtEmail = (EditText) v.findViewById(R.id.et_email);

        mTiName = (TextInputLayout) v.findViewById(R.id.ti_name);
        mProgressbar = (ProgressBar) v.findViewById(R.id.progress);
        txtDeleteAccount = v.findViewById(R.id.txtDelete_Account);
        btnDeleteAccountYes = v.findViewById(R.id.btnDelete_Account_Yes);
        getBtnDeleteAccountNo = v.findViewById(R.id.btnDelete_Account_No);

        btnDeleteAccountYes.setOnClickListener(view -> deleteAccount());
        getBtnDeleteAccountNo.setOnClickListener(view -> dismiss());
    }

    private void deleteAccount(){

        String email = mEtEmail.getText().toString();

        int err = 0;

        if (!validateFields(name)) {

            err++;
            mTiName.setError("Name should not be empty !");
        }


        if (err == 0) {

            User user = new User();
            user.setName(name);
            user.setEmail(email);

            mProgressbar.setVisibility(View.VISIBLE);
            deleteProgress(user);

        } else {

            showSnackBarMessage("Enter Valid Details !");
        }


    }

    private void deleteProgress(User user)
    {
        compositeSubscription.add(NetworkUtil.getRetrofit().deleteAccount(email)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse,this::handleError));
    }

    private void handleResponse(Response response)
    {

        email=null;

        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

    private void handleError(Throwable error)
    {
        if (error instanceof HttpException) {

            Gson gson = new GsonBuilder().create();

            try {

                String errorBody = ((HttpException) error).response().errorBody().string();
                Response response = gson.fromJson(errorBody,Response.class);
                showSnackBarMessage(response.getMessage());

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            showSnackBarMessage("Network Error !");
        }
    }


    private void showSnackBarMessage(String message) {

        if (getView() != null) {

            Snackbar.make(getView(),message,Snackbar.LENGTH_SHORT).show();
        }
    }
}
