package seclass.grocerylistmanager;

/**
 * Created by A.Miller on 11/14/2017.
 */

import java.util.ArrayList;
import java.util.HashMap;
import android.content.Context;
import android.widget.Toast;

import java.io.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* Class will likely be completely changed in final version*/

public class DBItems {

    //HashMap to hold Item Database, <Name,Type>
    private HashMap<String,String> item_database;

    //Static Reference to database for Singleton Patter
    private static DBItems database_instance;

    //Database Name
    private static final String database_name = "Grocery List Database";

    private DBItems(){

        File test_file = new File(database_name);

        if(test_file.exists())
            load_database();
        else{
            item_database = new HashMap<String,String>();
            initialize_database();
        }

    }

    public static DBItems get_instance(){
        if(database_instance == null)
            return database_instance = new DBItems();
        else
            return database_instance;
    }

    public void add_item(String item_name,String item_type,Context current_context){
        item_database.put(item_name,item_type);
        save_database();
        Toast.makeText(current_context.getApplicationContext(),"Item successfully added!", Toast.LENGTH_SHORT).show();
    }

//    public ArrayList<String> search_by_type(String type){
//
//        ArrayList<String> items_of_a_type = new ArrayList<String>();
//
//        for(String i: item_database.keySet()){
//            if(type.equals(item_database.get(i))){
//                items_of_a_type.add(i);
//            }
//        }
//
//        return items_of_a_type;
//    }

    public boolean search_by_type(String type){

        for(String i: item_database.values()){
            if(type.equals(i)){
                return true;
            }
        }
        return false;
    }
    public boolean search_by_name(String name){

        String item = "";

        for(String i: item_database.keySet()){
            if(name.equals(i)){
                item = i;
                break;
            }
        }

        if(item.equals("")){
            return false;
        }
        else
            return true;
    }

    public List<String> getAll_names(){

        ArrayList<String> all_names = new ArrayList<>();

        for(String i: item_database.keySet()){
            all_names.add(i);
            }


        return all_names;
    }

    public List<String> getAll_types(){

        ArrayList<String> all_types = new ArrayList<>();

        for(String i: item_database.values()){
            all_types.add(i);
        }

        Set<String> duplicate_remover = new HashSet<>();

        duplicate_remover.addAll(all_types);

        all_types.clear();

        all_types.addAll(duplicate_remover);

        return all_types;
    }

    public void save_database(){

        try {
            FileOutputStream file_out = new FileOutputStream(database_name);
            ObjectOutputStream out = new ObjectOutputStream(file_out);
            out.writeObject(item_database);
            out.close();
            file_out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void load_database(){

        try {
            FileInputStream file_in = new FileInputStream(database_name);
            ObjectInputStream in = new ObjectInputStream(file_in);
            item_database = (HashMap<String, String>) in.readObject();
            in.close();
            file_in.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
            return;
        }
    }

    public void initialize_database(){

        String b = "beverage";
        String ba = "baked Goods";
        String d = "dairy";
        String v = "vegetable";
        String f = "fruit";
        String m = "meat";
        String c = "cannned goods";
        String con = "condiments";
        String s = "snacks";

        item_database.put("coffee",b);
        item_database.put("tea",b);
        item_database.put("water",b);
        item_database.put("grape juice",b);
        item_database.put("soda", b);

        item_database.put("bagels",ba);
        item_database.put("cake", ba);
        item_database.put("garlic bread",ba);
        item_database.put("english muffin", ba);

        item_database.put("butter",d);
        item_database.put("cheese",d);
        item_database.put("milk",d);
        item_database.put("eggs",d);
        item_database.put("yogurt", d);

        item_database.put("asparagus", v);
        item_database.put("broccoli",v);
        item_database.put("avocado",v);
        item_database.put("cabbage",v);
        item_database.put("carrots", v);

        item_database.put("apples", f);
        item_database.put("mango", f);
        item_database.put("orange", f);
        item_database.put("peach",f);

        item_database.put("chicken", m);
        item_database.put("steak" , m);
        item_database.put("pork" , m);
        item_database.put("shrimp", m);
    }


}