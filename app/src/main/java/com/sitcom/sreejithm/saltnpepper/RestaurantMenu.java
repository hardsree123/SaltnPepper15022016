package com.sitcom.sreejithm.saltnpepper;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.sitcom.sreejithm.expandablelistview.ExpandableListAdapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SreejithM on 2/14/2016.
 * this class contains all functions and variables related to the menu related activity.
 * the class can be modified with strictly adhering to OOPs concept.
 */
public class RestaurantMenu extends AppCompatActivity {
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
    Map<String,List<String>> vegOptionCollection;
    Map<String,List<String>> nonVegOptionCollection;
    Map<String,List<String>> beveragesOptionCollection;
    Map<String,List<String>> desertOptionCollection;

    public RestaurantMenu()
    {
        CreateGroup();
        CreateOptions();
        selectedCuisines = new ArrayList<String>();
    }

    private void CreateGroup()
    {
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

    private void CreateOptions()
    {
        //add all veg starters
        vegStarter = new ArrayList<String>();
        vegStarter.add("Gobi manchurian");
        //add all main course(veg)
        vegMainCourse = new ArrayList<String>();
        vegMainCourse.add("Hyderabadi biriyani");
        //add all veg rolls and breads
        vegRollsBreads = new ArrayList<String>();
        vegRollsBreads.add("Naan roti");
        //add all veg soups
        vegSoups = new ArrayList<String>();
        vegSoups.add("Tomato soup");
        //add all starters (non veg)
        nonVegStarters = new ArrayList<String>();
        nonVegStarters.add("Angara kabab");
        //add all main course items.
        nonVegMainCourse = new ArrayList<String>();
        nonVegMainCourse.add("Triple Schezwan");
        //add all rolls and bread items
        nonVegRollsBreads = new ArrayList<String>();
        nonVegRollsBreads.add("Chicken burger");
        //add all non veg soup items
        nonVegSoup = new ArrayList<String>();
        nonVegSoup.add("Mutton soup");
        //add all hot beverages
        hotBeverages = new ArrayList<String>();
        hotBeverages.add("Tea");
        //add all cold beverages
        coldBeverages = new ArrayList<String>();
        coldBeverages.add("Cold coffee");
        //add all remaining alchoholic beverages
        alcoholBeverages = new ArrayList<String>();
        alcoholBeverages.add("Tri Mocktail");
        //add all remaining icecreams
        iceCreams = new ArrayList<String>();
        iceCreams.add("Vanilla crushed");
        //add all remaining salads
        salads = new ArrayList<String>();
        salads.add("Falooda");
        //add all remaining sweet details below
        sweets = new ArrayList<String>();
        sweets.add("Gulab jamun");
        //sweets.add("");
    }

    public void CreateVegOption()
    {
        vegOptionCollection = new LinkedHashMap<String,List<String>>();
        for (String option : commonGrp) {
           switch (option) {
               case "Starters" :
                   vegOptionCollection.put(option, vegStarter);
                   break;
               case "Main Course" :
                   vegOptionCollection.put(option,vegMainCourse);
                   break;
               case "Rolls & Breads" :
                   vegOptionCollection.put(option,vegRollsBreads);
                   break;
               case "Soups" :
                   vegOptionCollection.put(option,vegSoups);
                   break;
           }
        }
    }

    public void CreateNonVegOption()
    {
        nonVegOptionCollection = new LinkedHashMap<String,List<String>>();
        for (String option : commonGrp) {
            switch (option) {
                case "Starters" :
                    nonVegOptionCollection.put(option, nonVegStarters);
                    break;
                case "Main Course" :
                    nonVegOptionCollection.put(option,nonVegMainCourse);
                    break;
                case "Rolls & Breads" :
                    nonVegOptionCollection.put(option,nonVegRollsBreads);
                    break;
                case "Soups" :
                    nonVegOptionCollection.put(option,nonVegSoup);
                    break;
            }
        }
    }

    public void CreateBeverageOption()
    {
        beveragesOptionCollection = new LinkedHashMap<String,List<String>>();
        for (String option : beverageGrp) {
            switch (option) {
                case "Hot" :
                    beveragesOptionCollection.put(option, hotBeverages);
                    break;
                case "Cold" :
                    beveragesOptionCollection.put(option,coldBeverages);
                    break;
                case "Alcoholic" :
                    beveragesOptionCollection.put(option,alcoholBeverages);
                    break;
            }
        }
    }

    public void CreateDesertOption()
    {
        desertOptionCollection = new LinkedHashMap<String,List<String>>();
        for (String option : desertGrp) {
            switch (option) {
                case "Ice Creams" :
                    desertOptionCollection.put(option, iceCreams);
                    break;
                case "Salads" :
                    desertOptionCollection.put(option,salads);
                    break;
                case "Sweets" :
                    desertOptionCollection.put(option,sweets);
                    break;
            }
        }
    }

    public ExpandableListAdapter FetchExpandableListVegAdapter(View view) {
        ExpandableListAdapter expListAdapter = new ExpandableListAdapter(this, commonGrp, vegOptionCollection);
        return expListAdapter;
    }

    public ExpandableListAdapter FetchExpandableListNonVegAdapter(View view){
        ExpandableListAdapter expListAdapter = new ExpandableListAdapter(this,commonGrp,nonVegOptionCollection);
        return expListAdapter;
    }

    public ExpandableListAdapter FetchExpandableListBeverageAdapter(View view){
        ExpandableListAdapter expListAdapter = new ExpandableListAdapter(this,beverageGrp,beveragesOptionCollection);
        return expListAdapter;
    }

    public ExpandableListAdapter FetchExpandableListDesertAdapter(View view){
        ExpandableListAdapter expListAdapter = new ExpandableListAdapter(this,desertGrp,desertOptionCollection);
        return expListAdapter;
    }

}
