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
