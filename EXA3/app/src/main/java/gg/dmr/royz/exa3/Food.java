package gg.dmr.royz.exa3;

import android.os.Parcel;
import android.os.Parcelable;

// 实现Parcelable接口以支持在Intent间传递
public class Food implements Parcelable {
    private String city; // 城市
    private String name; // 食物名称

    public Food(String city, String name) {
        this.city = city;
        this.name = name;
    }

    protected Food(Parcel in) {
        city = in.readString();
        name = in.readString();
    }

    public static final Creator<Food> CREATOR = new Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };

    public String getCity() {
        return city;
    }

    public String getName() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(city);
        dest.writeString(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Food food = (Food) obj;

        if (!city.equals(food.city)) return false;
        return name.equals(food.name);
    }

    @Override
    public int hashCode() {
        int result = city.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }
}