package com.sitcom.sreejithm.saltnpepper;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.server.converter.StringToIntConverter;
import com.sitcom.sreejithm.app.AppConfig;
import com.sitcom.sreejithm.app.AppController;
import com.sitcom.sreejithm.expandablelistview.ExpandableListAdapter;
import com.sitcom.sreejithm.helper.Dishes;
import com.sitcom.sreejithm.helper.RequestHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

/**
 * Created by SreejithM on 2/14/2016.
 * this class contains all functions and variables related to the menu related activity.
 * the class can be modified with strictly adhering to OOPs concept.
 */
public class RestaurantMenu extends AppCompatActivity {
    private static final String TAG = "Restraunt Menu";
    //below are parent list for veg, nonveg, desert and beverages
    List<String> commonGrp; //veg and non veg fused to common grp
    List<String> beverageGrp;//Hot , cold, and alchoholic
    List<String> desertGrp;//Ice Creams, Salads, Sweets

    //below are child list for all beverages
    List<String> vegStarter; //list to store veg starters
    List<String> vegSoups;
    List<String> vegRollsBreads;
    List<String> vegMainCourse; // List to store veg Main Course

    List<String> nonVegStarters; // list to store non veg starters
    List<String> nonVegSoup;
    List<String> nonVegRollsBreads;
    List<String> nonVegMainCourse; //List to store non veg main course

    //below are the list of beverage items
    List<String> hotBeverages;
    List<String> coldBeverages;
    List<String> alcoholBeverages;

    //below are the list of deserts items
    List<String> iceCreams;
    List<String> salads;
    List<String> sweets;

    List<String> selectedCuisines;
    Map<String, List<String>> vegOptionCollection;
    Map<String, List<String>> nonVegOptionCollection;
    Map<String, List<String>> beveragesOptionCollection;
    Map<String, List<String>> desertOptionCollection;


    private Map<String, Dishes> dishMapcollection;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

//    public RestaurantMenu() {
//
//        // Progress dialog
//
//        dishMapcollection = new LinkedHashMap<String, Dishes>();
//        CreateGroup();
//        //CreateOptions();
//        getJSON();
//        selectedCuisines = new ArrayList<String>();
//    }

    private void CreateGroup() {
        commonGrp = new ArrayList<String>();
        commonGrp.add("Starters");
        commonGrp.add("Main Course");
        commonGrp.add("Rolls & Breads");
        commonGrp.add("Soups");

        beverageGrp = new ArrayList<String>();
        beverageGrp.add("Hot");
        beverageGrp.add("Cold");
        beverageGrp.add("Alcoholic");

        desertGrp = new ArrayList<String>();
        desertGrp.add("Ice Creams");
        desertGrp.add("Salads");
        desertGrp.add("Sweets");

    }

    private void setDishList(String dishName, String dishType, String dishSubType) {
        switch (dishType) {
            case "VEG":
                switch (dishSubType) {
                    case "STARTERS":
                        if (vegStarter == null) {
                            vegStarter = new ArrayList<String>();
                        }
                        if (!vegStarter.contains(dishName)) {
                            vegStarter.add(dishName);
                        }
                        break;
                    case "MAIN COURSE":
                        if (vegMainCourse == null) {
                            vegMainCourse = new ArrayList<String>();
                        }
                        if (!vegMainCourse.contains(dishName)) {
                            vegMainCourse.add(dishName);
                        }
                        break;
                    case "rolls and breads":
                        if (vegRollsBreads == null) {
                            vegRollsBreads = new ArrayList<String>();
                        }
                        if (!vegRollsBreads.contains(dishName)) {
                            vegRollsBreads.add(dishName);
                        }
                        break;
                    case "Soups":
                        if (vegSoups == null) {
                            vegSoups = new ArrayList<String>();
                        }
                        if (!vegSoups.contains(dishName)) {
                            vegSoups.add(dishName);
                        }
                        break;
                }
                break;
            case "NON VEG":
                switch (dishSubType) {
                    case "STARTERS":
                        if (nonVegStarters == null) {
                            nonVegStarters = new ArrayList<String>();
                        }
                        if (!nonVegStarters.contains(dishName)) {
                            nonVegStarters.add(dishName);
                        }
                        break;
                    case "MAIN COURSE":
                        if (nonVegMainCourse == null) {
                            nonVegMainCourse = new ArrayList<String>();
                        }
                        if (!nonVegMainCourse.contains(dishName)) {
                            nonVegMainCourse.add(dishName);
                        }
                        break;
                    case "rolls and breads":
                        if (nonVegRollsBreads == null) {
                            nonVegRollsBreads = new ArrayList<String>();
                        }
                        if (!nonVegRollsBreads.contains(dishName)) {
                            nonVegRollsBreads.add(dishName);
                        }
                        break;
                    case "Soups":
                        if (nonVegSoup == null) {
                            nonVegSoup = new ArrayList<String>();
                        }
                        if (!nonVegSoup.contains(dishName)) {
                            nonVegSoup.add(dishName);
                        }
                        break;
                }
                break;
            case "BEVERAGES":
                switch (dishSubType) {
                    case "Hot":
                        if (hotBeverages == null) {
                            hotBeverages = new ArrayList<String>();
                        }
                        if (!hotBeverages.contains(dishName)) {
                            hotBeverages.add(dishName);
                        }
                        break;
                    case "Cold":
                        if (coldBeverages == null) {
                            coldBeverages = new ArrayList<String>();
                        }
                        if (!coldBeverages.contains(dishName)) {
                            coldBeverages.add(dishName);
                        }
                        break;
                    case "Alchoholic":
                        if (alcoholBeverages == null) {
                            alcoholBeverages = new ArrayList<String>();
                        }
                        if (!alcoholBeverages.contains(dishName)) {
                            alcoholBeverages.add(dishName);
                        }
                        break;
                }
                break;
            case "DESERTS":
                switch (dishSubType) {
                    case "Ice Cream":
                        if (iceCreams == null) {
                            iceCreams = new ArrayList<String>();
                        }
                        if (!iceCreams.contains(dishName)) {
                            iceCreams.add(dishName);
                        }
                        break;
                    case "Salads":
                        if (salads == null) {
                            salads = new ArrayList<String>();
                        }
                        if (!salads.contains(dishName)) {
                            salads.add(dishName);
                        }
                        break;
                    case "Sweets":
                        if (sweets == null) {
                            sweets = new ArrayList<String>();
                        }
                        if (!sweets.contains(dishName)) {
                            sweets.add(dishName);
                        }
                        break;
                }
                break;
        }
    }

    private void CreateOptions() {
        //add all veg starters
        vegStarter = new ArrayList<String>();
        vegStarter.add("Gobi manchurian");
        vegStarter.add("Chilly veg");
        vegStarter.add("Chilly paneer");
        vegStarter.add("Chilly mushroom");
        vegStarter.add("Chilly babycorn ");
        vegStarter.add("Paneer manchurian");
        vegStarter.add("Babycorn manchurian ");
        vegStarter.add("Mushroom manchurian");
        vegStarter.add("Veg-65");
        vegStarter.add("veg momos");
        vegStarter.add("veg crispy");
        vegStarter.add("veg-spring roll");
        vegStarter.add("Veg thai roll");
        vegStarter.add("Golden fried babycorn ");
        //add all main course(veg)
        vegMainCourse = new ArrayList<String>();
        vegMainCourse.add("Hyderabadi biriyani");
        vegMainCourse.add("baked pumpkin");
        vegMainCourse.add("pithivier");
        vegMainCourse.add("pies");
        vegMainCourse.add("polenta");
        vegMainCourse.add("Khichuri-edit");
        vegMainCourse.add("Kashmiri_Dum_Aaloo");
        vegMainCourse.add("Choleindia");
        vegMainCourse.add("Chapatiroll");
        vegMainCourse.add("Chana_masala");
        vegMainCourse.add("Cabbage_kootu");
        vegMainCourse.add("Bisi_Bele_Bath");
        vegMainCourse.add("Palakpaneer");
        vegMainCourse.add("Delhi_Chaat_with_saunth_chutney");

        //add all veg rolls and breads
        vegRollsBreads = new ArrayList<String>();
        vegRollsBreads.add("Naan roti");
        vegRollsBreads.add("Paneer Roll");
        vegRollsBreads.add("Veg Manchurian Roll");
        //add all veg soups
        vegSoups = new ArrayList<String>();
        vegSoups.add("Tomato soup");
        vegSoups.add("Veg hot & sour Soup");
        vegSoups.add("Cream of veg clear Soup");
        vegSoups.add("Mushroom Soup");
        vegSoups.add("Asparagus Soup");
        vegSoups.add("Mon chow Soup");
        vegSoups.add("Sweet Corn Soup");
        //add all starters (non veg)
        nonVegStarters = new ArrayList<String>();
        nonVegStarters.add("Angara kabab");
        nonVegStarters.add("chilly chicken");
        nonVegStarters.add("chicken hunan");
        nonVegStarters.add("chicken manchurian");
        nonVegStarters.add("chicken salt & pepper");
        nonVegStarters.add("Thai chilly chicken");
        nonVegStarters.add("Kung pao chicken");
        nonVegStarters.add("Mongolian Chicken ");
        nonVegStarters.add("chicken momos");
        nonVegStarters.add("chicken spring roll");
        nonVegStarters.add("Chilly fish");
        nonVegStarters.add("Fish-N-chips");
        nonVegStarters.add("Fish manchurian/hunan");
        nonVegStarters.add("Golden fried prawns");
        //add all main course items.
        nonVegMainCourse = new ArrayList<String>();
        nonVegMainCourse.add("Triple Schezwan");
        nonVegMainCourse.add("butter-chicken");
        nonVegMainCourse.add("Tawa chicken");
        nonVegMainCourse.add("kadhai chicken");
        nonVegMainCourse.add("chicken-korma");
        nonVegMainCourse.add("chicken-salna");
        nonVegMainCourse.add("chickenmanchurian");
        nonVegMainCourse.add("mangalorean-chicken-curry");
        nonVegMainCourse.add("desi chicken kari");
        nonVegMainCourse.add("spicy_chicken_masala");
        nonVegMainCourse.add("slemon chicken");
        nonVegMainCourse.add("chicken red cook");
        nonVegMainCourse.add("fish choice of sauce");
        nonVegMainCourse.add("prawns choice of sauce");
        //add all rolls and bread items
        nonVegRollsBreads = new ArrayList<String>();
        nonVegRollsBreads.add("Chicken burger");
        nonVegRollsBreads.add("Chicken Manchurian Roll ");
        nonVegRollsBreads.add("Chicken Mayonnaise Roll");
        nonVegRollsBreads.add("Chicken Indiana Roll");
        nonVegRollsBreads.add("Butter Chicken Roll");
        nonVegRollsBreads.add("Chicken Sausage Roll");
        //add all non veg soup items
        nonVegSoup = new ArrayList<String>();
        nonVegSoup.add("Mutton soup");
        nonVegSoup.add("Chicken Clear soup");
        nonVegSoup.add("Kozhi Rasam");
        nonVegSoup.add("Elumbu(Born) Soup");
        //add all hot beverages
        hotBeverages = new ArrayList<String>();
        hotBeverages.add("Tea");
        hotBeverages.add("Brewed coffee");
        hotBeverages.add("Latte");
        hotBeverages.add("Cappiccino");
        hotBeverages.add("Depth charge");
        hotBeverages.add("Caramel Latte");
        hotBeverages.add("Americano");
        hotBeverages.add("Chai Latte");
        //add all cold beverages
        coldBeverages = new ArrayList<String>();
        coldBeverages.add("Cold coffee");
        coldBeverages.add("Jasmine Milk Tea");
        coldBeverages.add("Taro Milk Tea");
        coldBeverages.add("Chocolate Cereal Tea");
        coldBeverages.add("Coconut Milk Tea");
        coldBeverages.add("Chocolate Milk Tea");
        coldBeverages.add("Oreo Milk Tea");
        coldBeverages.add("Healthy Milk Tea");
        coldBeverages.add("Blueberry Milk Tea");
        //add all remaining alchoholic beverages
        alcoholBeverages = new ArrayList<String>();
        alcoholBeverages.add("Tri Mocktail");
        alcoholBeverages.add("Bud Light");
        alcoholBeverages.add("Ice House");
        alcoholBeverages.add("Miller Light");
        alcoholBeverages.add("Corona");
        alcoholBeverages.add("Asahi");
        alcoholBeverages.add("Sapporo");
        alcoholBeverages.add("Moscato");
        alcoholBeverages.add("House Merlot");
        //add all remaining icecreams
        iceCreams = new ArrayList<String>();
        iceCreams.add("Vanilla crushed");
        //add all remaining salads
        salads = new ArrayList<String>();
        salads.add("Falooda");
        //add all remaining sweet details below
        sweets = new ArrayList<String>();
        sweets.add("Gulab jamun");
        sweets.add("Gajar Halwa");
        sweets.add("Shewai Kheer");
        //sweets.add("");
    }

    public void CreateVegOption() {
        vegOptionCollection = new LinkedHashMap<String, List<String>>();
        for (String option : commonGrp) {
            switch (option) {
                case "Starters":
                    vegOptionCollection.put(option, vegStarter);
                    break;
                case "Main Course":
                    vegOptionCollection.put(option, vegMainCourse);
                    break;
                case "Rolls & Breads":
                    vegOptionCollection.put(option, vegRollsBreads);
                    break;
                case "Soups":
                    vegOptionCollection.put(option, vegSoups);
                    break;
            }
        }
    }

    public void CreateNonVegOption() {
        nonVegOptionCollection = new LinkedHashMap<String, List<String>>();
        for (String option : commonGrp) {
            switch (option) {
                case "Starters":
                    nonVegOptionCollection.put(option, nonVegStarters);
                    break;
                case "Main Course":
                    nonVegOptionCollection.put(option, nonVegMainCourse);
                    break;
                case "Rolls & Breads":
                    nonVegOptionCollection.put(option, nonVegRollsBreads);
                    break;
                case "Soups":
                    nonVegOptionCollection.put(option, nonVegSoup);
                    break;
            }
        }
    }

    public void CreateBeverageOption() {
        beveragesOptionCollection = new LinkedHashMap<String, List<String>>();
        for (String option : beverageGrp) {
            switch (option) {
                case "Hot":
                    beveragesOptionCollection.put(option, hotBeverages);
                    break;
                case "Cold":
                    beveragesOptionCollection.put(option, coldBeverages);
                    break;
                case "Alcoholic":
                    beveragesOptionCollection.put(option, alcoholBeverages);
                    break;
            }
        }
    }

    public void CreateDesertOption() {
        desertOptionCollection = new LinkedHashMap<String, List<String>>();
        for (String option : desertGrp) {
            switch (option) {
                case "Ice Creams":
                    desertOptionCollection.put(option, iceCreams);
                    break;
                case "Salads":
                    desertOptionCollection.put(option, salads);
                    break;
                case "Sweets":
                    desertOptionCollection.put(option, sweets);
                    break;
            }
        }
    }

    public ExpandableListAdapter FetchExpandableListVegAdapter(View view) {
        ExpandableListAdapter expListAdapter = new ExpandableListAdapter(this, commonGrp, vegOptionCollection);
        return expListAdapter;
    }

    public ExpandableListAdapter FetchExpandableListNonVegAdapter(View view) {
        ExpandableListAdapter expListAdapter = new ExpandableListAdapter(this, commonGrp, nonVegOptionCollection);
        return expListAdapter;
    }

    public ExpandableListAdapter FetchExpandableListBeverageAdapter(View view) {
        ExpandableListAdapter expListAdapter = new ExpandableListAdapter(this, beverageGrp, beveragesOptionCollection);
        return expListAdapter;
    }

    public ExpandableListAdapter FetchExpandableListDesertAdapter(View view) {
        ExpandableListAdapter expListAdapter = new ExpandableListAdapter(this, desertGrp, desertOptionCollection);
        return expListAdapter;
    }


    private void fetchDish() {
        // Tag used to cancel the request
        String tag_string_req = "req_dish";

        try {
            JsonObjectRequest jreq = new JsonObjectRequest(Request.Method.GET, AppConfig.URL_FETCH_DISH,null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            int success = 0;
                            try {
                                success = response.getInt("success");
                                if (success == 1) {
                                    JSONArray result = response.getJSONArray("result");
                                    Dishes dish = null;
                                    for (int i = 0; i < result.length(); i++) {
                                        JSONObject jo = result.getJSONObject(i);
                                        dish = new Dishes();
                                        dish.setDishid(Integer.valueOf(jo.getString("dishId")));
                                        dish.setDishname(jo.getString("dishname"));
                                        dish.setDishtype(jo.getString("dishtype"));
                                        dish.setDishSubType(jo.getString("dishsubtype"));
                                        dish.setDishSubType(jo.getString("price"));
                                        setDishList(dish.getDishname(), dish.getDishtype(), dish.getDishSubType());
                                        dishMapcollection.put(dish.getDishname(), dish);
                                    }
                                }
                            } catch (JSONException e) {
                                // JSON error
                                e.printStackTrace();
                                Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "Login Error: " + error.getMessage());
                    Toast.makeText(getApplicationContext(),
                            error.getMessage(), Toast.LENGTH_LONG).show();

                }
            });
            AppController.getInstance().addToRequestQueue(jreq, tag_string_req);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //
//        StringRequest strReq = new StringRequest(Request.Method.POST,
//                AppConfig.URL_FETCH_DISH, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.d(TAG, "Fetching dish: " + response.toString());
//
//                try {
//                    JSONObject jObj = null;
//                    try {
//                        jObj = new JSONObject(response);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    boolean error = jObj.getBoolean("error");
//
//                    // Check for error node in json
//                    if (!error) {
//
//                        JSONArray result = jObj.getJSONArray("result");
//                        Dishes dish = null;
//                        for (int i = 0; i < result.length(); i++) {
//                            JSONObject jo = result.getJSONObject(i);
//                            dish = new Dishes();
//                            dish.setDishid(Integer.valueOf(jo.getString("dishId")));
//                            dish.setDishname(jo.getString("dishname"));
//                            dish.setDishtype(jo.getString("dishtype"));
//                            dish.setDishSubType(jo.getString("dishsubtype"));
//                            dish.setDishSubType(jo.getString("price"));
//                            setDishList(dish.getDishname(), dish.getDishtype(), dish.getDishSubType());
//                            dishMapcollection.put(dish.getDishname(), dish);
//                        }
//
//                    } else {
//                        // Error in login. Get the error message
//                        String errorMsg = jObj.getString("error_msg");
//                        Toast.makeText(getApplicationContext(),
//                                errorMsg, Toast.LENGTH_LONG).show();
//                    }
//                } catch (JSONException e) {
//                    // JSON error
//                    e.printStackTrace();
//                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.e(TAG, "Login Error: " + error.getMessage());
//                Toast.makeText(getApplicationContext(),
//                        error.getMessage(), Toast.LENGTH_LONG).show();
//
//            }
//        });
//        // Adding request to request queue
//        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
//    }

    }

    private String JSON_STRING;

    /**
     *
     */
    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //loading = ProgressDialog.show(ViewAllEmployee.this,"Fetching Data","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSON_STRING = s;
                fetchDishList();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(AppConfig.URL_FETCH_DISH);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    /**
     *
     */
    private void fetchDishList() {
        JSONObject jsonObject = null;
        int success = 0;
        try {
            jsonObject = new JSONObject(JSON_STRING);

            success = jsonObject.getInt("success");
            if (success == 1) {
                JSONArray result = jsonObject.getJSONArray("result");
                Dishes dish = null;
                for (int i = 0; i < result.length(); i++) {
                    JSONObject jo = result.getJSONObject(i);
                    dish = new Dishes();
                    dish.setDishid(jo.getInt("dishId"));
                    dish.setDishname(jo.getString("dishname"));
                    dish.setDishtype(jo.getString("dishtype"));
                    dish.setDishSubType(jo.getString("dishsubtype"));
                    dish.setPrice(String.valueOf(jo.getInt("price")));
                    setDishList(dish.getDishname(), dish.getDishtype(), dish.getDishSubType());
                    dishMapcollection.put(dish.getDishname(), dish);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dishMapcollection = new LinkedHashMap<String, Dishes>();
        CreateGroup();
        //CreateOptions();
        getJSON();
        selectedCuisines = new ArrayList<String>();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

}
