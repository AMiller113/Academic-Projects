package seclass.grocerylistmanager;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Lists extends AppCompatActivity {

    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);

//        db = AppDatabase.getAppDatabase(this);
////        new DatabaseAsync().execute();

        final User the_user;
        the_user = User._get_user_interface();

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.grocerylist_layout, the_user.get_grocery_lists_names());

        ListView myGroceryListView = findViewById(R.id.myGroceryListView);

        myGroceryListView.setAdapter(adapter);

        myGroceryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(getApplicationContext(),"Clicked "+(i+1), Toast.LENGTH_SHORT).show();

                Intent list_display = new Intent(getApplicationContext(), ListDisplay.class);

                list_display.putExtra("Name of List", the_user.choose_list(i));

                startActivity(list_display);
            }
        });

        Button addList = findViewById(R.id.addListButton);
        addList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText listInput = findViewById(R.id.editTextList);

                String newItem = listInput.getText().toString();

                the_user.new_list(newItem,getApplicationContext());

                adapter.notifyDataSetChanged();
            }
        });
    }

//    private class DatabaseAsync extends AsyncTask<Void, Void, List<Item>> {
//
//        protected List<Item> doInBackground(Void... voids) {
//
////            Item item1 = new Item();
////            item1.setItemName("Coffee");
////            item1.setItemType("Beverage");
////            AppDatabase.getAppDatabase(getApplicationContext()).itemDao().insertItem(item1);
////            Item item2 = new Item();
////            item2.setItemName("Tea");
////            item2.setItemType("Beverage");
////            AppDatabase.getAppDatabase(getApplicationContext()).itemDao().insertItem(item2);
////            Item item3 = new Item();
////            item3.setItemName("Juice");
////            item3.setItemType("Beverage");
////            AppDatabase.getAppDatabase(getApplicationContext()).itemDao().insertItem(item3);
////            Item item4 = new Item();
////            item4.setItemName("Soda");
////            item4.setItemType("Beverage");
////            AppDatabase.getAppDatabase(getApplicationContext()).itemDao().insertItem(item4);
////            Item item5 = new Item();
////            item5.setItemName("Bagel");
////            item5.setItemType("Bread and Baked Goods");
////            AppDatabase.getAppDatabase(getApplicationContext()).itemDao().insertItem(item5);
////            Item item6 = new Item();
////            item6.setItemName("Cake");
////            item6.setItemType("Bread and Baked Goods");
////            AppDatabase.getAppDatabase(getApplicationContext()).itemDao().insertItem(item6);
////            Item item7 = new Item();
////            item7.setItemName("English Muffins");
////            item7.setItemType("Bread and Baked Goods");
////            AppDatabase.getAppDatabase(getApplicationContext()).itemDao().insertItem(item7);
////            Item item8 = new Item();
////            item8.setItemName("Garlic Bread");
////            item8.setItemType("Bread and Baked Goods");
////            AppDatabase.getAppDatabase(getApplicationContext()).itemDao().insertItem(item8);
////            Item item9 = new Item();
////            item9.setItemName("Butter");
////            item9.setItemType("Dairy");
////            AppDatabase.getAppDatabase(getApplicationContext()).itemDao().insertItem(item9);
////            Item item10 = new Item();
////            item10.setItemName("Cheese");
////            item10.setItemType("Dairy");
////            AppDatabase.getAppDatabase(getApplicationContext()).itemDao().insertItem(item10);
////            Item item11 = new Item();
////            item11.setItemName("Eggs");
////            item11.setItemType("Dairy");
////            AppDatabase.getAppDatabase(getApplicationContext()).itemDao().insertItem(item11);
////            Item item12 = new Item();
////            item12.setItemName("Yogurt");
////            item12.setItemType("Dairy");
////            AppDatabase.getAppDatabase(getApplicationContext()).itemDao().insertItem(item12);
////            Item item13 = new Item();
////            item13.setItemName("Asparagus");
////            item13.setItemType("Vegetable");
////            AppDatabase.getAppDatabase(getApplicationContext()).itemDao().insertItem(item13);
////            Item item14 = new Item();
////            item14.setItemName("Avocado");
////            item14.setItemType("Vegetable");
////            AppDatabase.getAppDatabase(getApplicationContext()).itemDao().insertItem(item14);
////            Item item15 = new Item();
////            item15.setItemName("Broccoli");
////            item15.setItemType("Vegetable");
////            AppDatabase.getAppDatabase(getApplicationContext()).itemDao().insertItem(item15);
////            Item item16 = new Item();
////            item16.setItemName("Cabbage");
////            item16.setItemType("Vegetable");
////            AppDatabase.getAppDatabase(getApplicationContext()).itemDao().insertItem(item16);
////            Item item17 = new Item();
////            item17.setItemName("Carrots");
////            item17.setItemType("Vegetable");
////            AppDatabase.getAppDatabase(getApplicationContext()).itemDao().insertItem(item17);
////            Item item18 = new Item();
////            item18.setItemName("Celery");
////            item18.setItemType("Vegetable");
////            AppDatabase.getAppDatabase(getApplicationContext()).itemDao().insertItem(item18);
////            Item item19 = new Item();
////            item19.setItemName("Cucumber");
////            item19.setItemType("Vegetable");
////            AppDatabase.getAppDatabase(getApplicationContext()).itemDao().insertItem(item19);
////            Item item20 = new Item();
////            item20.setItemName("Corn");
////            item20.setItemType("Vegetable");
////            AppDatabase.getAppDatabase(getApplicationContext()).itemDao().insertItem(item20);
////            Item item21 = new Item();
////            item21.setItemName("Lettuce");
////            item21.setItemType("Vegetable");
////            AppDatabase.getAppDatabase(getApplicationContext()).itemDao().insertItem(item21);
////            Item item22 = new Item();
////            item22.setItemName("Onion");
////            item22.setItemType("Vegetable");
////            AppDatabase.getAppDatabase(getApplicationContext()).itemDao().insertItem(item22);
////            Item item23 = new Item();
////            item23.setItemName("Potato");
////            item23.setItemType("Vegetable");
////            AppDatabase.getAppDatabase(getApplicationContext()).itemDao().insertItem(item23);
////            Item item24 = new Item();
////            item24.setItemName("Spinach");
////            item24.setItemType("Vegetable");
////            AppDatabase.getAppDatabase(getApplicationContext()).itemDao().insertItem(item24);
////            Item item25 = new Item();
////            item25.setItemName("Tomato");
////            item25.setItemType("Vegetable");
////            AppDatabase.getAppDatabase(getApplicationContext()).itemDao().insertItem(item25);
////            Item item26 = new Item();
////            item26.setItemName("Apple");
////            item26.setItemType("Fruit");
////            AppDatabase.getAppDatabase(getApplicationContext()).itemDao().insertItem(item26);
////            Item item27 = new Item();
////            item27.setItemName("Banana");
////            item27.setItemType("Fruit");
////            AppDatabase.getAppDatabase(getApplicationContext()).itemDao().insertItem(item27);
////            Item item28 = new Item();
////            item28.setItemName("Grape");
////            item28.setItemType("Fruit");
////            AppDatabase.getAppDatabase(getApplicationContext()).itemDao().insertItem(item28);
////            Item item29 = new Item();
////            item29.setItemName("Kiwi");
////            item29.setItemType("Fruit");
////            AppDatabase.getAppDatabase(getApplicationContext()).itemDao().insertItem(item29);
////            Item item30 = new Item();
////            item30.setItemName("Mango");
////            item30.setItemType("Fruit");
////            AppDatabase.getAppDatabase(getApplicationContext()).itemDao().insertItem(item30);
////            Item item31 = new Item();
////            item31.setItemName("Orange");
////            item31.setItemType("Fruit");
////            AppDatabase.getAppDatabase(getApplicationContext()).itemDao().insertItem(item31);
////            Item item32 = new Item();
////            item32.setItemName("Peach");
////            item32.setItemType("Fruit");
////            AppDatabase.getAppDatabase(getApplicationContext()).itemDao().insertItem(item32);
////            Item item33 = new Item();
////            item33.setItemName("Pear");
////            item33.setItemType("Fruit");
////            AppDatabase.getAppDatabase(getApplicationContext()).itemDao().insertItem(item33);
////            Item item34 = new Item();
////            item34.setItemName("Pineapple");
////            item34.setItemType("Fruit");
////            AppDatabase.getAppDatabase(getApplicationContext()).itemDao().insertItem(item34);
////            Item item35 = new Item();
////            item35.setItemName("Strawberry");
////            item35.setItemType("Fruit");
////            AppDatabase.getAppDatabase(getApplicationContext()).itemDao().insertItem(item35);
////            Item item36 = new Item();
////            item36.setItemName("Bacon");
////            item36.setItemType("Meat");
////            AppDatabase.getAppDatabase(getApplicationContext()).itemDao().insertItem(item36);
////            Item item37 = new Item();
////            item37.setItemName("Chicken");
////            item37.setItemType("Meat");
////            AppDatabase.getAppDatabase(getApplicationContext()).itemDao().insertItem(item37);
////            Item item38 = new Item();
////            item38.setItemName("Ground Beef");
////            item38.setItemType("Meat");
////            AppDatabase.getAppDatabase(getApplicationContext()).itemDao().insertItem(item38);
////            Item item39 = new Item();
////            item39.setItemName("Ham");
////            item39.setItemType("Meat");
////            AppDatabase.getAppDatabase(getApplicationContext()).itemDao().insertItem(item39);
////            Item item40 = new Item();
////            item40.setItemName("Pork Chop");
////            item40.setItemType("Meat");
////            AppDatabase.getAppDatabase(getApplicationContext()).itemDao().insertItem(item40);
////            Item item41 = new Item();
////            item41.setItemName("Sausage");
////            item41.setItemType("Meat");
////            AppDatabase.getAppDatabase(getApplicationContext()).itemDao().insertItem(item41);
////            Item item42 = new Item();
////            item42.setItemName("Sausage");
////            item42.setItemType("Meat");
////            AppDatabase.getAppDatabase(getApplicationContext()).itemDao().insertItem(item42);
////            Item item43 = new Item();
////            item43.setItemName("Beans");
////            item43.setItemType("Canned Goods");
////            AppDatabase.getAppDatabase(getApplicationContext()).itemDao().insertItem(item43);
////            Item item44 = new Item();
////            item44.setItemName("Soup");
////            item44.setItemType("Canned Goods");
////            AppDatabase.getAppDatabase(getApplicationContext()).itemDao().insertItem(item44);
////            Item item45 = new Item();
////            item45.setItemName("Spam");
////            item45.setItemType("Canned Goods");
////            AppDatabase.getAppDatabase(getApplicationContext()).itemDao().insertItem(item45);
////            Item item46 = new Item();
////            item46.setItemName("Ketchup");
////            item46.setItemType("Condiments");
////            AppDatabase.getAppDatabase(getApplicationContext()).itemDao().insertItem(item46);
////            Item item47 = new Item();
////            item47.setItemName("Peanut Butter");
////            item47.setItemType("Condiments");
////            AppDatabase.getAppDatabase(getApplicationContext()).itemDao().insertItem(item47);
////            Item item48 = new Item();
////            item48.setItemName("Candy");
////            item48.setItemType("Snacks");
////            AppDatabase.getAppDatabase(getApplicationContext()).itemDao().insertItem(item48);
////            Item item49 = new Item();
////            item49.setItemName("Chips");
////            item49.setItemType("Snacks");
////            AppDatabase.getAppDatabase(getApplicationContext()).itemDao().insertItem(item49);
////            Item item50 = new Item();
////            item50.setItemName("Peanuts");
////            item50.setItemType("Snacks");
////            AppDatabase.getAppDatabase(getApplicationContext()).itemDao().insertItem(item50);
//            List<Item> itemList;
////            itemList = AppDatabase.getAppDatabase(getApplicationContext()).itemDao().getAll();
//
//            return null;
//        }
//    }
}
