package gg.dmr.royz.exa3;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SelectedFoodAdapter extends RecyclerView.Adapter<SelectedFoodAdapter.ViewHolder> {

    private List<Food> selectedFoods;

    public SelectedFoodAdapter(List<Food> selectedFoods) {
        this.selectedFoods = selectedFoods;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_selected_food, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Food food = selectedFoods.get(position);
        holder.tvFoodCity.setText(food.getCity() + "：");
        holder.tvFoodName.setText(food.getName());
    }

    @Override
    public int getItemCount() {
        return selectedFoods.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFoodCity;
        TextView tvFoodName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFoodCity = itemView.findViewById(R.id.tvFoodCity);
            tvFoodName = itemView.findViewById(R.id.tvFoodName);
        }
    }
}