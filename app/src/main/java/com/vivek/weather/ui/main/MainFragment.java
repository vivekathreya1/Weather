package com.vivek.weather.ui.main;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.vivek.weather.R;
import com.vivek.weather.ViewModelProviderFactory;
import com.vivek.weather.api.exceptions.NoConnectivityException;
import com.vivek.weather.databinding.MainFragmentBinding;
import com.vivek.weather.ui.BaseFragment;
import com.vivek.weather.ui.main.viewmodel.MainViewModel;

import javax.inject.Inject;

import static com.vivek.weather.utils.Constants.API_KEY_ERROR;
import static com.vivek.weather.utils.Constants.LATLONG_ERROR;

public class MainFragment extends BaseFragment {

    private static final String TAG = MainFragment.class.getSimpleName();
    private MainViewModel mViewModel;
    private MainFragmentBinding binding;
    private View rootView;


    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    ForecastAdapter adapter;

    private Location location;

    public static MainFragment newInstance() {
        return new MainFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false);
        rootView = binding.getRoot();
        return rootView;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this, providerFactory).get(MainViewModel.class);
        binding.setViewModel(mViewModel);
        binding.setLifecycleOwner(this);
        initRecyclerview();
        setObservers();
        setClickListeners();


    }


    private void setClickListeners() {
        binding.retryBut.setOnClickListener((view -> {
            binding.progressCircular.setVisibility(View.VISIBLE);
            mViewModel.getData(location);
        }));

        binding.turnOnBut.setOnClickListener((view -> {
            binding.progressCircular.setVisibility(View.VISIBLE);
            getPermissions();

        }));

        binding.slideUpArrow.setOnClickListener(view -> {

            binding.bottomPanel.bottomPanelayout.setVisibility(View.VISIBLE);
            Animation panelAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_up);
            binding.bottomPanel.bottomPanelayout.startAnimation(panelAnimation);
            binding.slideUpArrow.startAnimation(panelAnimation);
            binding.slideUpDown.startAnimation(panelAnimation);


            panelAnimation.setAnimationListener(new AnimationListener() {
                @Override
                public void onAnimationEnd(Animation animation) {
                    binding.slideUpArrow.setVisibility(View.GONE);
                    binding.slideUpDown.setVisibility(View.VISIBLE);

                }
            });

        });

        binding.slideUpDown.setOnClickListener(view -> {
            Animation panelAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_down);
            binding.slideUpArrow.startAnimation(panelAnimation);
            binding.slideUpDown.startAnimation(panelAnimation);
            binding.bottomPanel.bottomPanelayout.startAnimation(panelAnimation);
            panelAnimation.setAnimationListener(new AnimationListener() {
                @Override
                public void onAnimationEnd(Animation animation) {
                    binding.bottomPanel.bottomPanelayout.setVisibility(View.GONE);
                    binding.slideUpDown.setVisibility(View.GONE);
                    binding.slideUpArrow.setVisibility(View.VISIBLE);

                }
            });
        });
    }

    private void setObservers() {
        mViewModel.getErrorThrowable().removeObservers(getViewLifecycleOwner());
        mViewModel.getErrorThrowable().observe(getViewLifecycleOwner(), throwable -> {
            binding.progressCircular.setVisibility(View.INVISIBLE);
            if (throwable instanceof NoConnectivityException) {
                setErrorVisibility(getString(R.string.no_internet), View.VISIBLE);

            } else if (throwable == null) {
                setErrorVisibility("", View.INVISIBLE);
            } else if (throwable != null) {
                setErrorVisibility(getString(R.string.unknown_error), View.VISIBLE);
            }
        });
        mViewModel.getErrorCode().removeObservers(getViewLifecycleOwner());
        mViewModel.getErrorCode().observe(getViewLifecycleOwner(), s -> {
            binding.progressCircular.setVisibility(View.INVISIBLE);
            if (s.equalsIgnoreCase(LATLONG_ERROR)) {
                setErrorVisibility(getString(R.string.latlongError), View.VISIBLE);
            } else if (s.equalsIgnoreCase(API_KEY_ERROR)) {
                setErrorVisibility(getString(R.string.apikeyError), View.VISIBLE);
            } else if (s.isEmpty()) {
                setErrorVisibility("", View.INVISIBLE);
            }
        });

        mViewModel.forecastWeatherLiveData.removeObservers(getViewLifecycleOwner());
        mViewModel.forecastWeatherLiveData.observe(getViewLifecycleOwner(), currentWeathers -> {
            adapter.setForeCastList(currentWeathers);
        });
    }

    private void setErrorVisibility(String msg, int visibility) {
        binding.errorTv.setText(msg);
        binding.errorTv.setVisibility(visibility);
        binding.retryBut.setVisibility(visibility);
        binding.turnOnBut.setVisibility(View.GONE);
        if (visibility == View.VISIBLE) {
            binding.currentTempMax.setVisibility(View.GONE);
            binding.currentTempMin.setVisibility(View.GONE);
            binding.slideUpDown.setVisibility(View.GONE);
            binding.slideUpArrow.setVisibility(View.GONE);
        } else {
            binding.currentTempMax.setVisibility(View.VISIBLE);
            binding.currentTempMin.setVisibility(View.VISIBLE);
            binding.slideUpArrow.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public void setLocation(Location location) {
        mViewModel.getData(location);
        this.location = location;
    }

    @Override
    public void setLocationError() {
        mViewModel.getErrorThrowable().setValue(new Throwable());
        binding.errorTv.setText(R.string.location_permission_denied);
        binding.progressCircular.setVisibility(View.GONE);
        binding.retryBut.setVisibility(View.GONE);
        binding.turnOnBut.setVisibility(View.VISIBLE);

    }

    private void initRecyclerview() {
        binding.bottomPanel.forecastRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.bottomPanel.forecastRv.setAdapter(adapter);

    }
}
