package com.o3sa.mobipugapp.sidemenu;

        import android.annotation.SuppressLint;
        import android.annotation.TargetApi;
        import android.app.Activity;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.res.Configuration;
        import android.content.res.TypedArray;
        import android.os.Build;
        import android.os.Bundle;
        import android.os.Handler;
        import android.support.v4.app.ActionBarDrawerToggle;
        import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentManager;
        import android.support.v4.widget.DrawerLayout;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.transition.TransitionManager;
        import android.util.Log;
        import android.util.TypedValue;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.ListView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.o3sa.mobipugapp.R;
        import com.o3sa.mobipugapp.activities.LandingPage;
        import com.o3sa.mobipugapp.customerfragments.CustomerProductlist;
        import com.o3sa.mobipugapp.customerfragments.MyOrders;
        import com.o3sa.mobipugapp.customerfragments.Mycart;
        import com.o3sa.mobipugapp.customerfragments.OrdersMain;
        import com.o3sa.mobipugapp.customerfragments.Profile;
        import com.o3sa.mobipugapp.fragments.VRegistnOne;
        import com.o3sa.mobipugapp.storedobjects.StoredObjects;
        import com.o3sa.mobipugapp.fragments.AboutUs;
        import com.o3sa.mobipugapp.fragments.GenerateBill;
        import com.o3sa.mobipugapp.customerfragments.HomePage;
        import com.o3sa.mobipugapp.fragments.ManageCategoriesMain;
        import com.o3sa.mobipugapp.fragments.ManageOffersMain;
        import com.o3sa.mobipugapp.fragments.ManageProductsMain;
        import com.o3sa.mobipugapp.fragments.ManageSubCategoriesMain;
        import com.o3sa.mobipugapp.fragments.VendorHomePage;
        import com.o3sa.mobipugapp.uicomponents.BasicComponents;
        import com.o3sa.mobipugapp.uicomponents.Constants;

        import java.util.ArrayList;

        import static com.o3sa.mobipugapp.uicomponents.Constants.bottom;
        import static com.o3sa.mobipugapp.uicomponents.Constants.left;
        import static com.o3sa.mobipugapp.uicomponents.Constants.right;
        import static com.o3sa.mobipugapp.uicomponents.Constants.top;
        import static java.security.AccessController.getContext;

/**
 * Created by android-4 on 09/26/2018.
 */

public class SidemenuOld extends AppCompatActivity {

    public static DrawerLayout mDrawerLayout;
    public static ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;

    private String[] navMenuTitles;
    private TypedArray navMenuIconsleft;
    private TypedArray navMenuIconsright;
    private ArrayList<NavDrawerItems> navDrawerItems;
    private NavDrawerListAdapter adapter;
    public static int drawablecount = 0;

    //actionbar

    //hedr part
    ViewGroup header;
    public static LinearLayout view_profile_sidemenu;
    public static LinearLayout headr_cancl_lay,hedr_prfl_img_lay;
    public static ImageView headr_cancl_img,headr_profile_img;
    public static TextView hedr_prfl_ratng_txt,hedr_prfl_name_txt,hedr_prfl_mail_txt;

    ImageView menu_share_img,menu_cart_img;
    public static ImageView back_img,menu_img;
    public static TextView menu_title;
    BasicComponents components;

    //btm_lay
    public static LinearLayout btm_lay;
    //public static LinearLayout btm_home_layy,btm_gnrt_bill_layy,btm_ctgry_layy,btm_sub_ctgry_layy,btm_settings_layy;
    public static LinearLayout btm_home_lay,btm_gnrt_bill_lay,btm_ctgry_lay,btm_sub_ctgry_lay,btm_settings_lay;
    public static ImageView btm_home_img,btm_gnrt_bill_img,btm_ctgry_img,btm_sub_ctgry_img,btm_settings_img;
    public static TextView btm_home_txt,btm_gnrt_bill_txt,btm_ctgry_txt,btm_sub_ctgry_txt,btm_settings_txt;

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sidemenuold);

        components = new BasicComponents(SidemenuOld.this);
        initialization();
        if (savedInstanceState == null) {
            //on first time display view for first nav item
            if( StoredObjects.UserType.equalsIgnoreCase("Vendor")){
                menu_cart_img.setVisibility(View.GONE);
                fragmentcallinglay(new VendorHomePage());
              /*  if(StoredObjects.Profileupdate.equalsIgnoreCase("No")){
                    UpdateProfileAlert("Please update your profile details");
                }*/
            }else{
                menu_cart_img.setVisibility(View.VISIBLE);
                fragmentcallinglay(new HomePage());
            }

            displayView(0);
        }

    }

    @SuppressLint("WrongViewCast")
    private void initialization() {

        mDrawerList = (ListView) findViewById(R.id.mDrawerList);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        //Actionbar
        menu_share_img=(ImageView) findViewById(R.id.menu_share_img);
        menu_img=(ImageView) findViewById(R.id.menu_img);
        menu_cart_img=(ImageView) findViewById(R.id.menu_cart_img);
        back_img=(ImageView) findViewById(R.id.back_img);
        menu_title=(TextView) findViewById(R.id.menu_title);
        navDrawerItems = new ArrayList<NavDrawerItems>();
        if( StoredObjects.UserType.equalsIgnoreCase("Vendor")){
            navMenuTitles = getResources().getStringArray(R.array.vendormenu_list);
            navMenuIconsleft = getResources().obtainTypedArray(R.array.vendormenu_icons);
        }else{
            navMenuTitles = getResources().getStringArray(R.array.customermenu_list);
            navMenuIconsleft = getResources().obtainTypedArray(R.array.customermenu_icons);
        }


        for (int i = 0; i < navMenuTitles.length; i++) {
            navDrawerItems.add(new NavDrawerItems(navMenuTitles[i], navMenuIconsleft.getResourceId(i, -1)));
        }
        navMenuIconsleft.recycle();
        mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

        menu_img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                drawablecount++;
                if (drawablecount == 1) {
                    mDrawerLayout.openDrawer(mDrawerList);
                    //title.setText(mTitle);
                } else {
                    drawablecount = 0;
                    mDrawerLayout.closeDrawer(mDrawerList);

                }

            }

        });

        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backbuttonclickevents();
            }
        });
        adapter = new NavDrawerListAdapter(SidemenuOld.this, navDrawerItems);
        header =(ViewGroup) LayoutInflater.from(this).inflate(R.layout.menu_header, null);

        view_profile_sidemenu = (LinearLayout) header.findViewById(R.id.view_profile_sidemenu);

        headr_cancl_img=(ImageView) header.findViewById(R.id.headr_cancl_img);
        headr_profile_img=(ImageView) header.findViewById(R.id.headr_profile_img);
        headr_cancl_lay =(LinearLayout)  header.findViewById(R.id.headr_cancl_lay);
        hedr_prfl_img_lay =(LinearLayout) header.findViewById(R.id.hedr_prfl_img_lay);
        hedr_prfl_ratng_txt =(TextView) header.findViewById(R.id.hedr_prfl_ratng_txt);
        hedr_prfl_name_txt =(TextView)  header.findViewById(R.id.hedr_prfl_name_txt);
        hedr_prfl_mail_txt = (TextView) header.findViewById(R.id.hedr_prfl_mail_txt);


        //btm_lay

        btm_lay = (LinearLayout)findViewById(R.id.btm_lay);

        btm_home_lay = (LinearLayout) findViewById(R.id.btm_home_lay);
        btm_gnrt_bill_lay = (LinearLayout)findViewById(R.id.btm_gnrt_bill_lay);
        btm_ctgry_lay = (LinearLayout)findViewById(R.id.btm_ctgry_lay);
        btm_sub_ctgry_lay = (LinearLayout)findViewById(R.id.btm_sub_ctgry_lay);
        btm_settings_lay = (LinearLayout)findViewById(R.id.btm_settings_lay);

        btm_home_img = (ImageView)findViewById(R.id.btm_home_img);
        btm_gnrt_bill_img = (ImageView)findViewById(R.id.btm_gnrt_bill_img);
        btm_ctgry_img = (ImageView)findViewById(R.id.btm_ctgry_img);
        btm_sub_ctgry_img = (ImageView)findViewById(R.id.btm_sub_ctgry_img);
        btm_settings_img = (ImageView)findViewById(R.id.btm_settings_img);

        btm_home_txt = (TextView)btm_home_lay.findViewById(R.id.btm_home_txt);
        btm_gnrt_bill_txt = (TextView)btm_gnrt_bill_lay.findViewById(R.id.btm_gnrt_bill_txt);
        btm_ctgry_txt = (TextView)btm_ctgry_lay.findViewById(R.id.btm_ctgry_txt);
        btm_sub_ctgry_txt = (TextView)btm_sub_ctgry_lay.findViewById(R.id.btm_sub_ctgry_txt);
        btm_settings_txt = (TextView)btm_settings_lay.findViewById(R.id.btm_settings_txt);

        btm_home_txt.setAllCaps(true);
        btm_gnrt_bill_txt.setAllCaps(true);
        btm_ctgry_txt.setAllCaps(true);
        btm_sub_ctgry_txt.setAllCaps(true);
        btm_settings_txt.setAllCaps(true);
        view_profile_sidemenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        mDrawerList.addHeaderView(header, null, false);

        mDrawerList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.ic_launcher, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ) {
            public void onDrawerClosed(View view) {
                //getActionBar().setTitle(mTitle);
                //title.setText(mTitle);
                invalidateOptionsMenu();
                drawablecount = 0;

                Log.i("test", "drawablelayout closed");

            }

            public void onDrawerOpened(View drawerView) {
                //getActionBar().setTitle(mDrawerTitle);
                drawablecount++;
                Log.i("test", "drawablelayout opened");
                invalidateOptionsMenu();
            }

        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        menu_share_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = "Mobipug";
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Mobipug");
                sharingIntent.putExtra(Intent.EXTRA_TEXT, name +" Please refer to the given link "+"https://play.google.com/store/");
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });

        menu_cart_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentcallinglay(new OrdersMain());
            }
        });

        fragmentcalling(new VendorHomePage(),"");

        btm_home_img.setColorFilter(getApplicationContext().getResources().getColor(R.color.blue_color));
        components.CustomizeImageview(btm_home_img, new int[]{20,20},R.drawable.heart, new int[]{0,8,0,0});
        btm_home_txt.setTextColor(getApplicationContext().getResources().getColor(R.color.blue_color));
        btm_home_txt.setTextSize(Constants.XXXSmall);
        buttonchangelaymethod(SidemenuOld.this, btm_home_lay,btm_home_img, btm_home_txt, "0");
        buttonchangelaymethod(SidemenuOld.this, btm_gnrt_bill_lay,btm_gnrt_bill_img, btm_gnrt_bill_txt, "1");
        buttonchangelaymethod(SidemenuOld.this, btm_ctgry_lay,btm_ctgry_img, btm_ctgry_txt, "2");
        buttonchangelaymethod(SidemenuOld.this, btm_sub_ctgry_lay,btm_sub_ctgry_img, btm_sub_ctgry_txt, "3");
        buttonchangelaymethod(SidemenuOld.this, btm_settings_lay,btm_settings_img, btm_settings_txt, "4");

        AssignData();

    }

    private class SlideMenuClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {

            displayView(position);

        }
    }


    public void buttonchangelaymethod(final Activity activity, final LinearLayout layout1, final ImageView imageView, final TextView text1, final String type) {

        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                buttonchangemethod(activity,layout1,imageView, text1);

                if(StoredObjects.UserType.equalsIgnoreCase("Vendor")){
                    if (type.equalsIgnoreCase("0")) {
                        fragmentcalling(new VendorHomePage(),"");
                    } else if (type.equalsIgnoreCase("1")) {
                        fragmentcalling(new ManageProductsMain(),"");
                    } else if (type.equalsIgnoreCase("2")) {
                        fragmentcalling(new ManageOffersMain(),"");
                    } else if (type.equalsIgnoreCase("3")) {
                        fragmentcalling(new GenerateBill(),"");
                    }else if (type.equalsIgnoreCase("4")) {
                        fragmentcalling(new Profile(),"");
                    }
                }else{
                    if (type.equalsIgnoreCase("0")) {
                        fragmentcalling(new HomePage(),"");
                    } else if (type.equalsIgnoreCase("1")) {
                        fragmentcalling(new CustomerProductlist(),"");
                    } else if (type.equalsIgnoreCase("2")) {
                        fragmentcalling(new MyOrders(),"");
                    } else if (type.equalsIgnoreCase("3")) {
                        fragmentcalling(new Mycart(),"");
                    }else if (type.equalsIgnoreCase("4")) {
                        fragmentcalling(new Profile(),"");
                    }
                }


            }

        });

    }


    public void buttonchangemethod(Activity activity, LinearLayout layout1, ImageView image, TextView text1) {

        if(StoredObjects.UserType.equalsIgnoreCase("Vendor")){
            components.CustomizeImageview(btm_home_img, new int[]{18,18},R.drawable.heart, new int[]{0,13,0,0});
            components.CustomizeImageview(btm_gnrt_bill_img, new int[]{18,18},R.drawable.heart, new int[]{0,13,0,0});
            components.CustomizeImageview(btm_ctgry_img, new int[]{18,18},R.drawable.heart, new int[]{0,13,0,0});
            components.CustomizeImageview(btm_sub_ctgry_img, new int[]{18,18},R.drawable.heart, new int[]{0,13,0,0});
            components.CustomizeImageview(btm_settings_img, new int[]{18,18},R.drawable.heart, new int[]{0,13,0,0});

            components.CustomizeTextview(btm_home_txt, Constants.XXXXSmall,R.color.sport_shoos_txt_clr,getApplicationContext().getResources().getString(R.string.home),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,4,0,10});
            components.CustomizeTextview(btm_gnrt_bill_txt, Constants.XXXXSmall,R.color.sport_shoos_txt_clr,getApplicationContext().getResources().getString(R.string.products),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,4,0,10});
            components.CustomizeTextview(btm_ctgry_txt, Constants.XXXXSmall,R.color.sport_shoos_txt_clr,getApplicationContext().getResources().getString(R.string.offers),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,4,0,10});
            components.CustomizeTextview(btm_sub_ctgry_txt, Constants.XXXXSmall,R.color.sport_shoos_txt_clr,getApplicationContext().getResources().getString(R.string.generate),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,4,0,10});
            components.CustomizeTextview(btm_settings_txt, Constants.XXXXSmall,R.color.sport_shoos_txt_clr,getApplicationContext().getResources().getString(R.string.setttngs),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,4,0,10});

        }else{
            components.CustomizeImageview(btm_home_img, new int[]{18,18},R.drawable.heart, new int[]{0,13,0,0});
            components.CustomizeImageview(btm_gnrt_bill_img, new int[]{18,18},R.drawable.heart, new int[]{0,13,0,0});
            components.CustomizeImageview(btm_ctgry_img, new int[]{18,18},R.drawable.heart, new int[]{0,13,0,0});
            components.CustomizeImageview(btm_sub_ctgry_img, new int[]{18,18},R.drawable.heart, new int[]{0,13,0,0});
            components.CustomizeImageview(btm_settings_img, new int[]{18,18},R.drawable.heart, new int[]{0,13,0,0});

            components.CustomizeTextview(btm_home_txt, Constants.XXXXSmall,R.color.sport_shoos_txt_clr,getApplicationContext().getResources().getString(R.string.home),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,4,0,10});
            components.CustomizeTextview(btm_gnrt_bill_txt, Constants.XXXXSmall,R.color.sport_shoos_txt_clr,getApplicationContext().getResources().getString(R.string.products),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,4,0,10});
            components.CustomizeTextview(btm_ctgry_txt, Constants.XXXXSmall,R.color.sport_shoos_txt_clr,getApplicationContext().getResources().getString(R.string.myorders),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,4,0,10});
            components.CustomizeTextview(btm_sub_ctgry_txt, Constants.XXXXSmall,R.color.sport_shoos_txt_clr,getApplicationContext().getResources().getString(R.string.mycart),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,4,0,10});
            components.CustomizeTextview(btm_settings_txt, Constants.XXXXSmall,R.color.sport_shoos_txt_clr,getApplicationContext().getResources().getString(R.string.setttngs),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,4,0,10});

        }

        btm_home_img.setColorFilter(activity.getResources().getColor(R.color.ash_clr));
        btm_gnrt_bill_img.setColorFilter(activity.getResources().getColor(R.color.ash_clr));
        btm_ctgry_img.setColorFilter(activity.getResources().getColor(R.color.ash_clr));
        btm_sub_ctgry_img.setColorFilter(activity.getResources().getColor(R.color.ash_clr));
        btm_settings_img.setColorFilter(activity.getResources().getColor(R.color.ash_clr));


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            TypedValue outValue = new TypedValue();
            getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
            layout1.setClickable(true);
            layout1.setBackgroundResource(outValue.resourceId);
        }

        //layout1.setBackgroundColor(activity.getResources().getColor(R.color.white));
        image.setColorFilter(activity.getResources().getColor(R.color.blue_color));
        components.CustomizeImageview(image, new int[]{20,20},R.drawable.heart, new int[]{0,8,0,0});
        text1.setTextColor(activity.getResources().getColor(R.color.blue_color));
        text1.setTextSize(Constants.XXXSmall);

    }

    public void displayView(int position) {
        Fragment fragment = null;

        position -= mDrawerList.getHeaderViewsCount();
        StoredObjects.count= position;

        if( StoredObjects.UserType.equalsIgnoreCase("Vendor")){
            switch (position) {

                case 0:
                    fragment = new VendorHomePage();
                    break;

                case 1:
                    fragment = new ManageProductsMain();
                    break;

                case 2:
                    fragment = new ManageOffersMain();
                    break;

                case 3:
                    fragment = new ManageCategoriesMain();
                    break;

                case 4:
                    fragment = new ManageSubCategoriesMain();
                    break;

                case 5:
                    fragment = new GenerateBill();
                    break;

                case 6:
                    //fragment = new AboutUs();
                    break;

                case 7:
                    fragment = new AboutUs();
                    break;

                case 8:
                    fragment = new AboutUs();
                    break;

                case 9:
                    //fragment = new Testing();
                    break;

                case 10:
                    DisplayAlertDialogg("Do you want to Logout?");
                    break;

            }

        }else{
            switch (position) {

                case 0:
                    fragment = new HomePage();
                    break;

                case 1:
                    fragment = new CustomerProductlist();
                    break;

                case 2:
                    fragment = new MyOrders();
                    break;

                case 3:
                    fragment = new Profile();
                    break;

                case 4:
                    //fragment = new Testing();
                    break;

                case 5:
                    fragment = new AboutUs();
                    break;

                case 6:
                    fragment = new AboutUs();
                    break;

                case 7:
                    //fragment = new AboutUs();
                    break;

                case 8:
                    DisplayAlertDialogg("Do you want to Logout?");
                    break;

            }

        }


        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();

            //update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(navMenuTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
            components.CustomizeTextview(menu_title, Constants.XXLarge4,R.color.black,navMenuTitles[position],Constants.WrapLeftBold+Constants.Gibson, new int[]{0,0,0,0});

        } else {
            //error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }

    }

    public void AssignData(){

        components.CustomizeImageview(headr_profile_img, new int[]{55,65},R.drawable.ic_launcher, new int[]{0,0,0,0});
        components.CustomizeImageview(headr_cancl_img, new int[]{20,20}, R.drawable.ic_launcher, new int[]{0,12,0,5});
        components.CustomizeTextview(hedr_prfl_ratng_txt, Constants.Small,R.color.white,getApplicationContext().getResources().getString(R.string.four),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,0,0,0});
        components.CustomizeTextview(hedr_prfl_name_txt, Constants.XLarge,R.color.white,getApplicationContext().getResources().getString(R.string.arel),Constants.WrapLeftNormal+Constants.Gibson, new int[]{0,12,0,0});
        components.CustomizeTextview(hedr_prfl_mail_txt, Constants.Normal,R.color.side_memu_nrml_txt_clr,getApplicationContext().getResources().getString(R.string.arel_mail),Constants.WrapLeftNormal+Constants.Gibson, new int[]{0,5,0,15});
        components.CustomizeImageview(menu_share_img, new int[]{30,32},R.drawable.shareimage, new int[]{0,0,10,0});
        components.CustomizeImageview(menu_img, new int[]{32,28},R.drawable.menu_icon, new int[]{5,0,0,0});
        components.CustomizeImageview(back_img, new int[]{32,28},R.drawable.back_icon, new int[]{5,0,0,0});

        components.CustomizeTextview(menu_title, Constants.XXLarge4,R.color.black,navMenuTitles[0],Constants.WrapLeftBold+Constants.Gibson, new int[]{0,0,0,0});

        if(StoredObjects.UserType.equalsIgnoreCase("Vendor")){
            //btm_lay
            components.CustomizeImageview(btm_home_img, new int[]{18,18},R.drawable.heart, new int[]{0,14,0,0});
            components.CustomizeImageview(btm_gnrt_bill_img, new int[]{18,18},R.drawable.heart, new int[]{0,14,0,0});
            components.CustomizeImageview(btm_ctgry_img, new int[]{18,18},R.drawable.heart, new int[]{0,14,0,0});
            components.CustomizeImageview(btm_sub_ctgry_img, new int[]{18,18},R.drawable.heart, new int[]{0,14,0,0});
            components.CustomizeImageview(btm_settings_img, new int[]{18,18},R.drawable.heart, new int[]{0,14,0,0});

            components.CustomizeTextview(btm_home_txt, Constants.XXXXSmall,R.color.sport_shoos_txt_clr,getApplicationContext().getResources().getString(R.string.home),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,4,0,10});
            components.CustomizeTextview(btm_gnrt_bill_txt, Constants.XXXXSmall,R.color.sport_shoos_txt_clr,getApplicationContext().getResources().getString(R.string.gnrt_bill),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,4,0,10});
            components.CustomizeTextview(btm_ctgry_txt, Constants.XXXXSmall,R.color.sport_shoos_txt_clr,getApplicationContext().getResources().getString(R.string.ctgryy),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,4,0,10});
            components.CustomizeTextview(btm_sub_ctgry_txt, Constants.XXXXSmall,R.color.sport_shoos_txt_clr,getApplicationContext().getResources().getString(R.string.sub_ctgryy),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,4,0,10});
            components.CustomizeTextview(btm_settings_txt, Constants.XXXXSmall,R.color.sport_shoos_txt_clr,getApplicationContext().getResources().getString(R.string.setttngs),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,4,0,10});

        }else{
            //btm_lay
            components.CustomizeImageview(btm_home_img, new int[]{18,18},R.drawable.heart, new int[]{0,14,0,0});
            components.CustomizeImageview(btm_gnrt_bill_img, new int[]{18,18},R.drawable.heart, new int[]{0,14,0,0});
            components.CustomizeImageview(btm_ctgry_img, new int[]{18,18},R.drawable.heart, new int[]{0,14,0,0});
            components.CustomizeImageview(btm_sub_ctgry_img, new int[]{18,18},R.drawable.heart, new int[]{0,14,0,0});
            components.CustomizeImageview(btm_settings_img, new int[]{18,18},R.drawable.heart, new int[]{0,14,0,0});

            components.CustomizeTextview(btm_home_txt, Constants.XXXXSmall,R.color.sport_shoos_txt_clr,getApplicationContext().getResources().getString(R.string.home),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,4,0,10});
            components.CustomizeTextview(btm_gnrt_bill_txt, Constants.XXXXSmall,R.color.sport_shoos_txt_clr,getApplicationContext().getResources().getString(R.string.products),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,4,0,10});
            components.CustomizeTextview(btm_ctgry_txt, Constants.XXXXSmall,R.color.sport_shoos_txt_clr,getApplicationContext().getResources().getString(R.string.myorders),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,4,0,10});
            components.CustomizeTextview(btm_sub_ctgry_txt, Constants.XXXXSmall,R.color.sport_shoos_txt_clr,getApplicationContext().getResources().getString(R.string.mycart),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,4,0,10});
            components.CustomizeTextview(btm_settings_txt, Constants.XXXXSmall,R.color.sport_shoos_txt_clr,getApplicationContext().getResources().getString(R.string.setttngs),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,4,0,10});

        }

    }

    public void fragmentcallinglay(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();

    }

    public  void fragmentcalling(Fragment fragment, String type){

        FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragment);

        if(type.equalsIgnoreCase("0")){

        }else{
            fragmentTransaction.addToBackStack("my_fragment");
        }

        fragmentTransaction.commit();

    }


    public void setTitle(CharSequence title) {
        //mTitle = title;
        //getActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    public void onBackPressed() {
        backbuttonclickevents();

    }

    boolean doubleBackToExitPressedOnce = false;
    public void backbuttonclickevents() {
        if(!StoredObjects.pagetype.equalsIgnoreCase("home")){
            if(StoredObjects.pagetype.equalsIgnoreCase("sendfeedback")||StoredObjects.pagetype.equalsIgnoreCase("editprofile")||StoredObjects.pagetype.equalsIgnoreCase("changepassword")){
                fragmentcallinglay(new Profile());
            }else{
                if( StoredObjects.UserType.equalsIgnoreCase("Vendor")){
                    if(StoredObjects.pagetype.equalsIgnoreCase("editoffer")){
                        fragmentcallinglay(new ManageOffersMain());
                    }else{
                        fragmentcallinglay(new VendorHomePage());
                    }
                    menu_cart_img.setVisibility(View.GONE);
              /*  if(StoredObjects.Profileupdate.equalsIgnoreCase("No")){
                    UpdateProfileAlert("Please update your profile details");
                }*/
                }else{
                    menu_cart_img.setVisibility(View.VISIBLE);
                    fragmentcallinglay(new HomePage());

                }
            }

        }else{

            if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
                getSupportFragmentManager().popBackStack();
            }
            else if(getSupportFragmentManager().getBackStackEntryCount() == 0||getSupportFragmentManager().getBackStackEntryCount() == 1) {

                backclickevent();
            }
            else {
                super.onBackPressed();
            }



        }


    }

    public void backclickevent() {
        if (doubleBackToExitPressedOnce) {
            // super.onBackPressed();
            DisplayAlertDialog("Do You Want To Exit?");
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 3000);
    }

    public void DisplayAlertDialog(String string) {

        AlertDialog.Builder builder = new AlertDialog.Builder(SidemenuOld.this);

        builder.setMessage(string)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        minimizeApp();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void minimizeApp() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    public void DisplayAlertDialogg(String string) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SidemenuOld.this);

        builder.setMessage(string)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SidemenuOld.this.finish();
                        startActivity(new Intent(SidemenuOld.this,LandingPage.class));

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }
    public void UpdateProfileAlert(String string) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SidemenuOld.this);

        builder.setMessage(string)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        fragmentcallinglay(new VRegistnOne());

                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void updatemenu(String pagetype){
        if(pagetype.equalsIgnoreCase("home")){

            back_img.setVisibility(View.GONE);
            menu_img.setVisibility(View.VISIBLE);
        }else{
            back_img.setVisibility(View.VISIBLE);
            menu_img.setVisibility(View.GONE);
        }

    }


}

