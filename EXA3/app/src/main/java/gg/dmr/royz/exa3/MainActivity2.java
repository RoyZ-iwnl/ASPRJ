package gg.dmr.royz.exa3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {

    private TextView tvCurrentCity;
    private GridLayout gridFoods;
    private Button btnBack;
    private Button btnConfirmFood;

    private String city;
    private List<Food> foodList = new ArrayList<>();
    private Map<String, Boolean> selectedFoodMap = new HashMap<>();
    private List<CheckBox> checkBoxes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // 初始化视图
        initViews();

        // 获取传递的城市数据
        Intent intent = getIntent();
        city = intent.getStringExtra("city");
        ArrayList<Food> currentSelectedFoods = intent.getParcelableArrayListExtra("currentSelectedFoods");

        // 更新城市标题
        tvCurrentCity.setText("当前城市：" + city);

        // 填充食物网格
        populateFoodGrid();

        // 如果有之前选择的食物，标记为选中状态
        if (currentSelectedFoods != null) {
            for (Food food : currentSelectedFoods) {
                if (food.getCity().equals(city)) {
                    selectedFoodMap.put(food.getName(), true);
                }
            }
            updateCheckboxStatus();
        }

        // 设置按钮事件
        setupButtonListeners();
    }

    // 初始化视图
    private void initViews() {
        tvCurrentCity = findViewById(R.id.tvCurrentCity);
        gridFoods = findViewById(R.id.gridFoods);
        btnBack = findViewById(R.id.btnBack);
        btnConfirmFood = findViewById(R.id.btnConfirmFood);
    }

    // 填充食物网格
    private void populateFoodGrid() {
        // 生成9个城市美食
        for (int i = 1; i <= 9; i++) {
            String foodName = city + "美食" + i;
            foodList.add(new Food(city, foodName));
            selectedFoodMap.put(foodName, false);

            // 创建食物选择框
            CheckBox checkBox = createFoodCheckBox(foodName);
            gridFoods.addView(checkBox);
            checkBoxes.add(checkBox);
        }
    }

    // 更新复选框状态
    private void updateCheckboxStatus() {
        for (CheckBox checkBox : checkBoxes) {
            String foodName = checkBox.getText().toString();
            checkBox.setChecked(selectedFoodMap.getOrDefault(foodName, false));
        }
    }

    // 创建食物复选框
    private CheckBox createFoodCheckBox(String foodName) {
        CheckBox checkBox = new CheckBox(this);
        checkBox.setText(foodName);

        // 设置样式
        checkBox.setGravity(Gravity.CENTER);
        CheckBoxExtension.setDrawableTopResource(checkBox, android.R.drawable.ic_menu_gallery); // 使用系统自带的图标
        checkBox.setPadding(8, 20, 8, 20);
        checkBox.setBackgroundColor(getResources().getColor(android.R.color.darker_gray, null));

        // 设置在GridLayout中的布局参数
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();
        params.width = 0;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1, 1f);
        params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1, 1f);
        params.setMargins(8, 8, 8, 8);
        checkBox.setLayoutParams(params);

        // 设置点击事件
        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            selectedFoodMap.put(foodName, isChecked);
        });

        return checkBox;
    }

    // 设置按钮事件监听
    private void setupButtonListeners() {
        // 确认按钮事件
        btnConfirmFood.setOnClickListener(v -> {
            ArrayList<Food> selectedFoods = getSelectedFoods();
            returnResult(selectedFoods);
        });

        // 返回按钮事件
        btnBack.setOnClickListener(v -> {
            ArrayList<Food> selectedFoods = getSelectedFoods();
            returnResult(selectedFoods);
        });
    }

    // 获取选中的食物列表
    private ArrayList<Food> getSelectedFoods() {
        ArrayList<Food> selectedFoods = new ArrayList<>();
        for (String foodName : selectedFoodMap.keySet()) {
            if (selectedFoodMap.get(foodName)) {
                selectedFoods.add(new Food(city, foodName));
            }
        }
        return selectedFoods;
    }

    // 返回结果到MainActivity
    private void returnResult(ArrayList<Food> selectedFoods) {
        Intent resultIntent = new Intent();
        resultIntent.putParcelableArrayListExtra("selectedFoods", selectedFoods);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}