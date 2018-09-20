package seclass.grocerylistmanager;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Item {

    //Private Fields

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "item_ID")
    int item_id;

    @ColumnInfo(name = "item_name")
    String itemName;

    @ColumnInfo(name = "item_type")
    String itemType;

    @ColumnInfo(name = "quantity")
    int quantity;

    @Ignore
    @ColumnInfo(name = "check")
    private boolean checked_off = false;

    public Item(){}

    public Item(String new_item_name, String new_item_type, int original_quantity) {
        setItemName(new_item_name);
        setItemType(new_item_type);
        setQuantity(original_quantity);
    }

    public String getItemName() { return itemName; }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemType() { return itemType; }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) { this.quantity = quantity; }

    public boolean isChecked_off() {
        return checked_off;
    }

    public void setChecked_off(boolean checked_off) {
        this.checked_off = checked_off;
    }

}
