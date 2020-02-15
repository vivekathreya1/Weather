package com.vivek.weather.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.RequestManager;
import com.vivek.weather.R;
import com.vivek.weather.ViewModelProviderFactory;
import com.vivek.weather.api.exceptions.NoConnectivityException;
import com.vivek.weather.databinding.MainFragmentBinding;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

import static com.vivek.weather.utils.Constants.API_KEY_ERROR;
import static com.vivek.weather.utils.Constants.LATLONG_ERROR;

public class MainFragment extends DaggerFragment {

    private static final String TAG = MainFragment.class.getSimpleName();
    private MainViewModel mViewModel;
    private MainFragmentBinding binding;
    private View rootView;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    RequestManager requestManager;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding =DataBindingUtil.inflate(inflater,R.layout.main_fragment, container, false);
        rootView = binding.getRoot();
        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this,providerFactory).get(MainViewModel.class);
        binding.setViewModel(mViewModel);
        binding.setLifecycleOwner(this);
        setObservers();
        setClickListeners();
    }

    private void setClickListeners(){
        binding.retryBut.setOnClickListener((view -> {
            mViewModel.getData();
        }));
    }

    private void setObservers(){
        mViewModel.getErrorThrowable().removeObservers(getViewLifecycleOwner());
       mViewModel.getErrorThrowable().observe(getViewLifecycleOwner(), throwable -> {
           if(throwable instanceof NoConnectivityException){
               binding.errorTv.setText(getString(R.string.no_internet));
               binding.errorTv.setVisibility(View.VISIBLE);
               binding.retryBut.setVisibility(View.VISIBLE);
           }else if(throwable == null){
               binding.errorTv.setVisibility(View.INVISIBLE);
               binding.retryBut.setVisibility(View.INVISIBLE);
           }else if(throwable!=null){
               binding.errorTv.setText(getString(R.string.unknown_error));
               binding.errorTv.setVisibility(View.VISIBLE);
               binding.retryBut.setVisibility(View.VISIBLE);
           }
       });
        mViewModel.getErrorCode().removeObservers(getViewLifecycleOwner());
       mViewModel.getErrorCode().observe(getViewLifecycleOwner(), s -> {
           if(s.equalsIgnoreCase(LATLONG_ERROR)){
               binding.errorTv.setText(getString(R.string.latlongError));
               binding.errorTv.setVisibility(View.VISIBLE);
               binding.retryBut.setVisibility(View.VISIBLE);
           }else if(s.equalsIgnoreCase(API_KEY_ERROR)){
               binding.errorTv.setText(getString(R.string.apikeyError));
               binding.errorTv.setVisibility(View.VISIBLE);
               binding.retryBut.setVisibility(View.VISIBLE);
           }else if(s.isEmpty()){
               binding.errorTv.setVisibility(View.INVISIBLE);
               binding.retryBut.setVisibility(View.INVISIBLE);
           }
       });
    }


}
