package seclass.grocerylistmanager;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "GroceryList")
public class GroceryList {

    //Private Name Field
    @ColumnInfo(name = "list_name")
    private String list_name;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "list_ID")
    private int list_ID;

    @Embedded
    Item item;

//    @Ignore
//    private AppDatabase db;

    //Private ArrayList reference
    @Ignore
    private final List<Item> item_list = new ArrayList<Item>();;
    @Ignore
    private final List<String> names_of_all_items = new ArrayList<String>();
    @Ignore
    private DBItems database_ref;



    //Constructor

    public GroceryList(String list_name){
        setList_name(list_name);
        database_ref = DBItems.get_instance();
    }

    //Core functions of class


    public boolean add_item(String item_name,String item_type,int quantity,Context current_context){

		/*Check against database, add to database if item does not exist*/
		if(!database_ref.search_by_name(item_name)||!database_ref.search_by_type(item_type)){
            Toast.makeText(current_context.getApplicationContext(),"Item does not exists in the database, it will now be added. Please try again", Toast.LENGTH_SHORT).show();
            database_ref.add_item(item_name,item_type,current_context);
		    return false;
		}

        if(item_name == null || item_type == null|| quantity == 0)
            return false;

        if(item_name == "" || item_type == ""|| quantity == 0)
            return false;

        for(final Item i:item_list){
            if(i.getItemName().equals(item_name) && i.getItemType().equals(item_type)){
                i.setQuantity(i.getQuantity()+quantity);
                return true;
            }
        }

        item_list.add(new Item(item_name,item_type,quantity));
        names_of_all_items.add("Item Name: "+ item_name + ", Current Quantity: "+quantity);
        return true;
    }

    public void delete_item(String name_of_item_to_remove){

        if(name_of_item_to_remove == null){
            return;
        }

        for(final Item i:item_list){
            if(i.getItemName().equals(name_of_item_to_remove)){
                item_list.remove(i);
                names_of_all_items.remove(item_list.indexOf(i));
                return;
            }
        }
    }

    public void update_quantity(String name_of_item_quantity_to_change, int new_quantity){

        for(final Item i:item_list){
            if(i.getItemName().equals(name_of_item_quantity_to_change)){
                i.setQuantity(new_quantity);
				names_of_all_items.set(names_of_all_items.indexOf(i),"Item Name: "+ name_of_item_quantity_to_change + ", Current Quantity: "+new_quantity);
                return;
            }
        }
    }

    public void check_off(String checked_item){

        for(final Item i:item_list){
            if(i.getItemName().equals(checked_item)){

                i.setChecked_off(!i.isChecked_off());

                if(i.isChecked_off())
                    names_of_all_items.set(item_list.indexOf(i),"Item Name: "+ checked_item + ", Current Quantity: "+i.getQuantity()+", Marked");
                else
                    names_of_all_items.set(item_list.indexOf(i),"Item Name: "+ checked_item + ", Current Quantity: "+i.getQuantity());

                return;
            }
        }
    }

    public void remove_all_checks(){

        for(final Item i:item_list){
            i.setChecked_off(false);
            names_of_all_items.set(item_list.indexOf(i),"Item Name: "+ i.getItemName() + ", Current Quantity: "+i.getQuantity());
        }
    }

//    public boolean is_in_database(String item_name,String item_type,Context current_context) {
//
//        db = AppDatabase.getAppDatabase(current_context);
//
////        List<Item> all_items = db.itemDao().getAll();
//
////        for(final Item i:all_items){
////            if(i.getItemName().equals(item_name))
////                return true;
////        }
////        for(final Item i:all_items){
////            if(i.getItemType().equals(item_name))
////                Toast.makeText(current_context.getApplicationContext(),"This item does not currently exist, updating database.......Please try again", Toast.LENGTH_SHORT).show();
////        }
////
////        db.itemDao().insertItem(new Item(item_name,item_type,0));
////
//        return false;
//    }

    public String getList_name() {
        return this.list_name;
    }
    public void setList_name(String new_list_name) {this.list_name = new_list_name;}
    public List<String> getNames_of_all_items(){return names_of_all_items;}
    public List<Item> getItem_list(){
        return item_list;
    }
    public int getList_ID(){return this.list_ID;}
    public void setList_ID(int list_ID) {this.list_ID = list_ID;}
}
