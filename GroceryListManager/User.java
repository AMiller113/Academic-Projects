package seclass.grocerylistmanager;

/**
 * Created by A.Miller on 11/14/2017.
 */
import android.content.Context;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class User {

    //Private Static Variable for Singleton Pattern

    private static User user_interface;

    //Private Lists for singleton pattern

    private ArrayList<GroceryList> current_lists;
    private final ArrayList<String> grocery_lists_names = new ArrayList<>();
    private List<Item> items_in_database;
    private List<String> all_item_names;
    private List<String> all_item_types;

    //Name of Interface File
    private final String name_of_file = "User_Interface_Data";

    //Singleton Pattern Private Constructor and Instance creator

    private User(){

        File test_file = new File(name_of_file);

        if (test_file.exists())
            load_all_lists();
        else
            current_lists = new ArrayList<>();

        initialize_string_list();
    }

    public static synchronized User _get_user_interface(){

        if(user_interface == null){
            return (user_interface = new User());
        }
        else
            return user_interface;
    }


    // Core Functions of the GroceryList Class


    public void new_list(String name_of_list, Context current_context){

        if (name_of_list.equals("") || name_of_list.equals(null)){
            return;
        }

        if (current_lists.size() > 0 ) {

            for(final GroceryList g: current_lists){
                if(g.getList_name().equals(name_of_list)){
                    Toast.makeText(current_context.getApplicationContext(),"List already exists", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }
        current_lists.add(new GroceryList(name_of_list));
        grocery_lists_names.add(name_of_list);
        save_all_lists();
    }

    public void rename_list(String old_list_name,String new_list_name){

        grocery_lists_names.set(grocery_lists_names.indexOf(old_list_name),new_list_name);

        for(final GroceryList g: current_lists){
            if(g.getList_name().equals(old_list_name)){
                g.setList_name(new_list_name);
                save_all_lists();
                return;
            }
        }
    }

    public GroceryList choose_list(String list_name){

        for(final GroceryList g: current_lists){
            if(g.getList_name().equals(list_name))
                return current_lists.get(current_lists.indexOf(g));
        }
        return null;
    }

    public String choose_list(int index){
        return current_lists.get(index).getList_name();
    }


    public void delete_list(String list_to_be_deleted){

        for(final GroceryList g: current_lists){
            if(g.getList_name().equals(list_to_be_deleted)){
                current_lists.remove(current_lists.indexOf(g));
                save_all_lists();
                return;
            }
        }
    }

    public void initialize_string_list(){
        for (GroceryList g: current_lists){
            grocery_lists_names.add(g.getList_name());
        }
    }

    public void save_all_lists(){

        try {
            FileOutputStream file_out = new FileOutputStream(name_of_file);
            ObjectOutputStream out = new ObjectOutputStream(file_out);
            out.writeObject(current_lists);
            out.close();
            file_out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void load_all_lists(){

        try {
            FileInputStream file_in = new FileInputStream(name_of_file);
            ObjectInputStream in = new ObjectInputStream(file_in);
            current_lists = (ArrayList<GroceryList>) in.readObject();
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

    public ArrayList<String> get_grocery_lists_names(){
        return grocery_lists_names;
    }

    public ArrayList<GroceryList> getCurrent_lists(){
        return current_lists;
    }

//    public List<Item> getItems_in_database(){
//        return items_in_database;
//    }
//
//    public void setItems_in_database(List<Item> items_in_database){
//        this.items_in_database = items_in_database;
//    }
//
//    public List<String> getAll_item_names() {
//        return all_item_names;
//    }
//
//    public void setAll_item_names(List<String> all_item_names) {
//        this.all_item_names = all_item_names;
//    }
//
//    public List<String> getAll_item_types() {
//        return all_item_types;
//    }
//
//    public void setAll_item_types(List<String> all_item_types) {
//        this.all_item_types = all_item_types;
//    }
}
