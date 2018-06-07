package com.masaki.menusample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuListActivity extends AppCompatActivity {

    private ListView _lvMenu;

    private List<Map<String, Object>> _menuList;

    private static final String[] FROM = {"name", "price"};


    private static final int[] TO = {R.id.tvMenuName, R.id.tvMenuPrice};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);

        _lvMenu = findViewById(R.id.lvMenu);

        _menuList = createTeishokuList();
        SimpleAdapter adapter = new SimpleAdapter(MenuListActivity.this, _menuList, R.layout.row, FROM, TO);
        _lvMenu.setAdapter(adapter);

        _lvMenu.setOnItemClickListener(new ListItemClickListener());

        registerForContextMenu(_lvMenu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_option_menu_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch(itemId) {
            case R.id.menuListOptionTeishoku:
                _menuList = createTeishokuList();
                break;
            case R.id.menuListOptionCurry:
                _menuList = createCurryList();
                break;
        }
        SimpleAdapter adapter = new SimpleAdapter(MenuListActivity.this, _menuList, R.layout.row, FROM, TO);
        _lvMenu.setAdapter(adapter);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, view, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_context_menu_list, menu);
        menu.setHeaderTitle(R.string.menu_list_context_header);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int listPosition = info.position;
        Map<String, Object> menu = _menuList.get(listPosition);

        int itemId = item.getItemId();
        switch(itemId) {
            case R.id.menuListContextDesc:
                String desc = (String) menu.get("desc");
                Toast.makeText(MenuListActivity.this, desc, Toast.LENGTH_LONG).show();
                break;

            case R.id.menuListContextOrder:

                order(menu);
                break;
        }

        return super.onContextItemSelected(item);
    }


    private List<Map<String, Object>> createTeishokuList() {

        List<Map<String, Object>> menuList = new ArrayList<>();

        Map<String, Object> menu = new HashMap<>();
        menu.put("name","から揚げ定食");
        menu.put("price",800);
        menu.put("desc","若鳥のから揚げにサラダ、ご飯とお味噌汁が付きます。");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name","ハンバーグ定食");
        menu.put("price",850);
        menu.put("desc","ハンバーグにサラダ、ご飯とお味噌汁が付きます。");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name","焼肉定食");
        menu.put("price",850);
        menu.put("desc","焼肉にサラダ、ご飯とお味噌汁が付きます。");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name","つけもの定食");
        menu.put("price",850);
        menu.put("desc","つけものにサラダ、ご飯とお味噌汁が付きます。");
        menuList.add(menu);

        return menuList;
    }

    private List<Map<String, Object>> createCurryList() {

        List<Map<String, Object>> menuList = new ArrayList<>();


        Map<String, Object> menu = new HashMap<>();
        menu.put("name","ビーフカレー");
        menu.put("price",800);
        menu.put("desc","ビーフカレーにサラダ、ご飯とお味噌汁が付きます。");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name","ポークカレー定食");
        menu.put("price",850);
        menu.put("desc","ポークカレーにサラダ、ご飯とお味噌汁が付きます。");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name","カレー定食");
        menu.put("price",850);
        menu.put("desc","カレーにサラダ、ご飯とお味噌汁が付きます。");
        menuList.add(menu);

        menu = new HashMap<>();
        menu.put("name","カレー２定食");
        menu.put("price",850);
        menu.put("desc","カレーと若鳥のから揚げにサラダ、ご飯とお味噌汁が付きます。");
        menuList.add(menu);

        return menuList;
    }


    private void order(Map<String, Object> menu) {

        String menuName = (String) menu.get("name");
        Integer menuPrice = (Integer) menu.get("price");

        Intent intent = new Intent(MenuListActivity.this, MenuThanksActivity.class);
        intent.putExtra("menuName", menuName);
        intent.putExtra("menuPrice", menuPrice + "円");
        startActivity(intent);
    }


    private class ListItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Map<String, Object> item = (Map<String, Object>) parent.getItemAtPosition(position);
            order(item);
        }
    }
}
