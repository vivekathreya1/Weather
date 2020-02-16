package com.vivek.weather.ui.main;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.vivek.weather.R;
import com.vivek.weather.databinding.ForecastDataBinding;
import com.vivek.weather.ui.main.model.ForecastWeather;

import java.util.ArrayList;
import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {

    private List<ForecastWeather> foreCastList = new ArrayList<>();

    @NonNull
    @Override
    public ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ForecastDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.forecast_data, parent, false);
        return new ForecastViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return foreCastList.size();
    }

    public void setForeCastList(List<ForecastWeather> foreCastList){
        this.foreCastList = foreCastList;
        notifyDataSetChanged();
    }

    public class ForecastViewHolder extends RecyclerView.ViewHolder{

        ForecastDataBinding binding;

        public ForecastViewHolder(ForecastDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(int position){
            binding.setForecastWeather(foreCastList.get(position));
            binding.executePendingBindings();
        }
    }
}
