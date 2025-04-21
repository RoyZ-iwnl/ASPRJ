package gg.dmr.royz.exa3;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Spinner spinnerCity;
    private Button btnConfirmCity;
    private Button btnSelectFood;
    private RecyclerView rvSelectedFoods;

    private String selectedCity = "";
    private List<String> cityList = Arrays.asList("城市1", "城市2", "城市3");
    private List<Food> selectedFoods = new ArrayList<>();
    private SelectedFoodAdapter adapter;

    // 使用ActivityResultLauncher接收返回的结果
    private ActivityResultLauncher<Intent> foodSelectionLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    // 接收从MainActivity2返回的数据
                    ArrayList<Food> newSelectedFoods = result.getData().getParcelableArrayListExtra("selectedFoods");
                    if (newSelectedFoods != null) {
                        // 更新当前选择的食物列表
                        updateSelectedFoods(newSelectedFoods);
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化视图
        initViews();
        // 设置事件监听
        setupListeners();
        // 初始化RecyclerView
        setupRecyclerView();
    }

    // 初始化视图
    private void initViews() {
        spinnerCity = findViewById(R.id.spinnerCity);
        btnConfirmCity = findViewById(R.id.btnConfirmCity);
        btnSelectFood = findViewById(R.id.btnSelectFood);
        rvSelectedFoods = findViewById(R.id.rvSelectedFoods);

        // 设置城市下拉列表适配器
        ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, cityList);
        cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCity.setAdapter(cityAdapter);
    }

    // 设置事件监听
    private void setupListeners() {
        // 确认城市按钮点击事件
        btnConfirmCity.setOnClickListener(v -> {
            selectedCity = spinnerCity.getSelectedItem().toString();
            btnSelectFood.setEnabled(true);  // 启用选择美食按钮
            Toast.makeText(this, "已选择城市: " + selectedCity, Toast.LENGTH_SHORT).show();
        });

        // 选择美食按钮点击事件
        btnSelectFood.setOnClickListener(v -> {
            if (selectedCity.isEmpty()) {
                Toast.makeText(this, "请先选择城市", Toast.LENGTH_SHORT).show();
                return;
            }

            // 跳转到MainActivity2
            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            intent.putExtra("city", selectedCity);
            intent.putParcelableArrayListExtra("currentSelectedFoods", new ArrayList<>(selectedFoods));
            foodSelectionLauncher.launch(intent);
        });
    }

    // 初始化RecyclerView
    private void setupRecyclerView() {
        adapter = new SelectedFoodAdapter(selectedFoods);
        rvSelectedFoods.setLayoutManager(new LinearLayoutManager(this));
        rvSelectedFoods.setAdapter(adapter);
    }

    // 更新选中的美食列表
    private void updateSelectedFoods(List<Food> newFoods) {
        // 清除之前选择的同城市的食物
        List<Food> toRemove = new ArrayList<>();
        for (Food food : selectedFoods) {
            if (food.getCity().equals(selectedCity)) {
                toRemove.add(food);
            }
        }
        selectedFoods.removeAll(toRemove);

        // 添加新选择的食物
        selectedFoods.addAll(newFoods);

        // 通知适配器数据已更改
        adapter.notifyDataSetChanged();
    }
}