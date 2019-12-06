package com.o3sa.mobipugapp.sidemenu;

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
import com.o3sa.mobipugapp.customerfragments.CstmrLandingPage;
import com.o3sa.mobipugapp.customerfragments.CustomerProductlist;
import com.o3sa.mobipugapp.customerfragments.CustomerWishlist;
import com.o3sa.mobipugapp.customerfragments.MyOrders;
import com.o3sa.mobipugapp.customerfragments.Mycart;
import com.o3sa.mobipugapp.customerfragments.OrdersMain;
import com.o3sa.mobipugapp.customerfragments.ProductDetails;
import com.o3sa.mobipugapp.customerfragments.ProductDetailsMain;
import com.o3sa.mobipugapp.customerfragments.Profile;
import com.o3sa.mobipugapp.fragments.VRegistnOne;
import com.o3sa.mobipugapp.fragments.VRegistnTwo;
import com.o3sa.mobipugapp.storedobjects.StoredObjects;
import com.o3sa.mobipugapp.fragments.GenerateBill;
import com.o3sa.mobipugapp.fragments.ManageOffersMain;
import com.o3sa.mobipugapp.fragments.ManageProductsMain;
import com.o3sa.mobipugapp.fragments.VendorHomePage;
import com.o3sa.mobipugapp.uicomponents.BasicComponents;
import com.o3sa.mobipugapp.uicomponents.Constants;


/**
 * Created by android-4 on 09/26/2018.
 */

public class Sidemenu extends AppCompatActivity {

    ImageView menu_share_img,menu_cart_img;
    public static ImageView back_img,menu_img;
    public static TextView menu_title;
    BasicComponents components;

    //btm_lay
    public static LinearLayout btm_lay;
    public static LinearLayout btm_home_lay,btm_gnrt_bill_lay,btm_ctgry_lay,btm_sub_ctgry_lay,btm_settings_lay;
    public static ImageView btm_home_img,btm_gnrt_bill_img,btm_ctgry_img,btm_sub_ctgry_img,btm_settings_img;
    public static TextView btm_home_txt,btm_gnrt_bill_txt,btm_ctgry_txt,btm_sub_ctgry_txt,btm_settings_txt;

    public static LinearLayout actionbar_lay;
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.side_menu);

        components = new BasicComponents(Sidemenu.this);
        initialization();
        if (savedInstanceState == null) {
            //on first time display view for first nav item
            if( StoredObjects.UserType.equalsIgnoreCase("Vendor")){
                menu_cart_img.setVisibility(View.GONE);
                fragmentcallinglay(new VendorHomePage());
               /* if(StoredObjects.Profileupdate.equalsIgnoreCase("No")){
                    UpdateProfileAlert("Please update your profile details");
                }*/
            }else{
                menu_cart_img.setVisibility(View.VISIBLE);
                fragmentcallinglay(new CstmrLandingPage());
            }

        }

    }

    private void initialization() {

        //Actionbar
        menu_share_img=(ImageView) findViewById(R.id.menu_share_img);
        menu_img=(ImageView) findViewById(R.id.menu_img);
        menu_cart_img=(ImageView) findViewById(R.id.menu_cart_img);
        back_img=(ImageView) findViewById(R.id.back_img);
        menu_title=(TextView) findViewById(R.id.menu_title);
        back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backbuttonclickevents();
            }
        });
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
        actionbar_lay=(LinearLayout)  findViewById(R.id.actionbar_lay);

        btm_home_txt.setAllCaps(true);
        btm_gnrt_bill_txt.setAllCaps(true);
        btm_ctgry_txt.setAllCaps(true);
        btm_sub_ctgry_txt.setAllCaps(true);
        btm_settings_txt.setAllCaps(true);

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

        buttonchangelaymethod(Sidemenu.this, btm_home_lay,btm_home_img, btm_home_txt, "0");
        buttonchangelaymethod(Sidemenu.this, btm_gnrt_bill_lay,btm_gnrt_bill_img, btm_gnrt_bill_txt, "1");
        buttonchangelaymethod(Sidemenu.this, btm_ctgry_lay,btm_ctgry_img, btm_ctgry_txt, "2");
        buttonchangelaymethod(Sidemenu.this, btm_sub_ctgry_lay,btm_sub_ctgry_img, btm_sub_ctgry_txt, "3");
        buttonchangelaymethod(Sidemenu.this, btm_settings_lay,btm_settings_img, btm_settings_txt, "4");

        AssignData();

        btm_home_img.setColorFilter(getApplicationContext().getResources().getColor(R.color.blue_color));
        components.CustomizeImageview(btm_home_img, new int[]{20,20},R.drawable.home, new int[]{0,8,0,0});
        btm_home_txt.setTextColor(getApplicationContext().getResources().getColor(R.color.blue_color));
        btm_home_txt.setTextSize(Constants.XXXSmall);


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
                        StoredObjects.proviewpos=0;
                        fragmentcalling(new ManageProductsMain(),"");
                    } else if (type.equalsIgnoreCase("2")) {
                        StoredObjects.viewpos=0;
                        fragmentcalling(new ManageOffersMain(),"");
                    } else if (type.equalsIgnoreCase("3")) {
                        fragmentcalling(new GenerateBill(),"");
                    }else if (type.equalsIgnoreCase("4")) {
                        fragmentcalling(new Profile(),"");
                    }
                }else{
                    if (type.equalsIgnoreCase("0")) {
                        fragmentcalling(new CstmrLandingPage(),"");
                    } else if (type.equalsIgnoreCase("1")) {
                        fragmentcalling(new CustomerProductlist(),"");
                    } else if (type.equalsIgnoreCase("2")) {
                        fragmentcalling(new MyOrders(),"");
                    } else if (type.equalsIgnoreCase("3")) {
                        fragmentcalling(new CustomerWishlist(),"");
                    }else if (type.equalsIgnoreCase("4")) {
                        fragmentcalling(new Profile(),"");
                    }
                }

            }

        });

    }


    public void buttonchangemethod(Activity activity, LinearLayout layout1, ImageView image, TextView text1) {

        if(StoredObjects.UserType.equalsIgnoreCase("Vendor")){
            components.CustomizeImageview(btm_home_img, new int[]{18,18},R.drawable.home, new int[]{0,13,0,0});
            components.CustomizeImageview(btm_gnrt_bill_img, new int[]{18,18},R.drawable.product, new int[]{0,13,0,0});
            components.CustomizeImageview(btm_ctgry_img, new int[]{18,18},R.drawable.offers, new int[]{0,13,0,0});
            components.CustomizeImageview(btm_sub_ctgry_img, new int[]{18,18},R.drawable.generate_bills, new int[]{0,13,0,0});
            components.CustomizeImageview(btm_settings_img, new int[]{18,18},R.drawable.settings, new int[]{0,13,0,0});

            components.CustomizeTextview(btm_home_txt, Constants.XXXXSmall,R.color.sport_shoos_txt_clr,getApplicationContext().getResources().getString(R.string.home),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,4,0,10});
            components.CustomizeTextview(btm_gnrt_bill_txt, Constants.XXXXSmall,R.color.sport_shoos_txt_clr,getApplicationContext().getResources().getString(R.string.products),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,4,0,10});
            components.CustomizeTextview(btm_ctgry_txt, Constants.XXXXSmall,R.color.sport_shoos_txt_clr,getApplicationContext().getResources().getString(R.string.offers),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,4,0,10});
            components.CustomizeTextview(btm_sub_ctgry_txt, Constants.XXXXSmall,R.color.sport_shoos_txt_clr,getApplicationContext().getResources().getString(R.string.generate),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,4,0,10});
            components.CustomizeTextview(btm_settings_txt, Constants.XXXXSmall,R.color.sport_shoos_txt_clr,getApplicationContext().getResources().getString(R.string.setttngs),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,4,0,10});
           }else{
            components.CustomizeImageview(btm_home_img, new int[]{18,18},R.drawable.home, new int[]{0,13,0,0});
            components.CustomizeImageview(btm_gnrt_bill_img, new int[]{18,18},R.drawable.product, new int[]{0,13,0,0});
            components.CustomizeImageview(btm_ctgry_img, new int[]{18,18},R.drawable.my_orders, new int[]{0,13,0,0});
            components.CustomizeImageview(btm_sub_ctgry_img, new int[]{18,18},R.drawable.heart, new int[]{0,13,0,0});

            components.CustomizeImageview(btm_settings_img, new int[]{18,18},R.drawable.settings, new int[]{0,13,0,0});

            components.CustomizeTextview(btm_home_txt, Constants.XXXXSmall,R.color.sport_shoos_txt_clr,getApplicationContext().getResources().getString(R.string.home),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,4,0,10});
            components.CustomizeTextview(btm_gnrt_bill_txt, Constants.XXXXSmall,R.color.sport_shoos_txt_clr,getApplicationContext().getResources().getString(R.string.products),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,4,0,10});
            components.CustomizeTextview(btm_ctgry_txt, Constants.XXXXSmall,R.color.sport_shoos_txt_clr,getApplicationContext().getResources().getString(R.string.myorders),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,4,0,10});
            components.CustomizeTextview(btm_sub_ctgry_txt, Constants.XXXXSmall,R.color.sport_shoos_txt_clr,getApplicationContext().getResources().getString(R.string.mywishlist),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,4,0,10});
            components.CustomizeTextview(btm_settings_txt, Constants.XXXXSmall,R.color.sport_shoos_txt_clr,getApplicationContext().getResources().getString(R.string.setttngs),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,4,0,10});

        }

        btm_home_img.setColorFilter(activity.getResources().getColor(R.color.sport_shoos_txt_clr));
        btm_gnrt_bill_img.setColorFilter(activity.getResources().getColor(R.color.sport_shoos_txt_clr));
        btm_ctgry_img.setColorFilter(activity.getResources().getColor(R.color.sport_shoos_txt_clr));
        btm_sub_ctgry_img.setColorFilter(activity.getResources().getColor(R.color.sport_shoos_txt_clr));
        btm_settings_img.setColorFilter(activity.getResources().getColor(R.color.sport_shoos_txt_clr));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            TypedValue outValue = new TypedValue();
            Sidemenu.this.getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
            layout1.setClickable(true);
            layout1.setBackgroundResource(outValue.resourceId);
        }

        //layout1.setBackgroundColor(activity.getResources().getColor(R.color.white));
        image.setColorFilter(activity.getResources().getColor(R.color.blue_color));
        //components.CustomizeImageview(image, new int[]{20,20},R.drawable.heart, new int[]{0,8,0,0});

        components.CustomizeBottomImageview(image, new int[]{20,20}, new int[]{0,8,0,3});

        text1.setTextColor(activity.getResources().getColor(R.color.blue_color));
        text1.setTextSize(Constants.XXXSmall);

    }


    public void AssignData(){

        components.CustomizeImageview(menu_share_img, new int[]{30,32},R.drawable.shareimage, new int[]{0,0,10,0});
        components.CustomizeImageview(menu_img, new int[]{32,28},R.drawable.menu_icon, new int[]{5,0,0,0});
        components.CustomizeImageview(back_img, new int[]{32,28},R.drawable.back_icon, new int[]{5,0,0,0});

        components.CustomizeTextview(menu_title, Constants.XXLarge4,R.color.black,"Home",Constants.WrapLeftBold+Constants.Gibson, new int[]{0,0,0,0});

        if(StoredObjects.UserType.equalsIgnoreCase("Vendor")){
            //btm_lay
            components.CustomizeImageview(btm_home_img, new int[]{18,18},R.drawable.home, new int[]{0,14,0,0});
            components.CustomizeImageview(btm_gnrt_bill_img, new int[]{18,18},R.drawable.product, new int[]{0,14,0,0});
            components.CustomizeImageview(btm_ctgry_img, new int[]{18,18},R.drawable.offers, new int[]{0,14,0,0});
            components.CustomizeImageview(btm_sub_ctgry_img, new int[]{18,18},R.drawable.generate_bills, new int[]{0,14,0,0});
            components.CustomizeImageview(btm_settings_img, new int[]{18,18},R.drawable.settings, new int[]{0,14,0,0});

            components.CustomizeTextview(btm_home_txt, Constants.XXXXSmall,R.color.sport_shoos_txt_clr,getApplicationContext().getResources().getString(R.string.home),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,4,0,10});
            components.CustomizeTextview(btm_gnrt_bill_txt, Constants.XXXXSmall,R.color.sport_shoos_txt_clr,getApplicationContext().getResources().getString(R.string.products),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,4,0,10});
            components.CustomizeTextview(btm_ctgry_txt, Constants.XXXXSmall,R.color.sport_shoos_txt_clr,getApplicationContext().getResources().getString(R.string.offers),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,4,0,10});
            components.CustomizeTextview(btm_sub_ctgry_txt, Constants.XXXXSmall,R.color.sport_shoos_txt_clr,getApplicationContext().getResources().getString(R.string.generate),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,4,0,10});
            components.CustomizeTextview(btm_settings_txt, Constants.XXXXSmall,R.color.sport_shoos_txt_clr,getApplicationContext().getResources().getString(R.string.setttngs),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,4,0,10});

        }else{
            //btm_lay
            components.CustomizeImageview(btm_home_img, new int[]{18,18},R.drawable.home, new int[]{0,14,0,0});
            components.CustomizeImageview(btm_gnrt_bill_img, new int[]{18,18},R.drawable.product, new int[]{0,14,0,0});
            components.CustomizeImageview(btm_ctgry_img, new int[]{18,18},R.drawable.my_orders, new int[]{0,14,0,0});
            components.CustomizeImageview(btm_sub_ctgry_img, new int[]{18,18},R.drawable.heart, new int[]{0,14,0,0});
            components.CustomizeImageview(btm_settings_img, new int[]{18,18},R.drawable.settings, new int[]{0,14,0,0});



            components.CustomizeTextview(btm_home_txt, Constants.XXXXSmall,R.color.sport_shoos_txt_clr,getApplicationContext().getResources().getString(R.string.home),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,4,0,10});
            components.CustomizeTextview(btm_gnrt_bill_txt, Constants.XXXXSmall,R.color.sport_shoos_txt_clr,getApplicationContext().getResources().getString(R.string.products),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,4,0,10});
            components.CustomizeTextview(btm_ctgry_txt, Constants.XXXXSmall,R.color.sport_shoos_txt_clr,getApplicationContext().getResources().getString(R.string.myorders),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,4,0,10});
            components.CustomizeTextview(btm_sub_ctgry_txt, Constants.XXXXSmall,R.color.sport_shoos_txt_clr,getApplicationContext().getResources().getString(R.string.mywishlist),Constants.WrapCenterNormal+Constants.Gibson, new int[]{0,4,0,10});
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

    public void onBackPressed() {
        backbuttonclickevents();

    }

    boolean doubleBackToExitPressedOnce = false;
    public void backbuttonclickevents() {
        if(!StoredObjects.pagetype.equalsIgnoreCase("home")){

            if( StoredObjects.UserType.equalsIgnoreCase("Vendor")){
                if(StoredObjects.pagetype.equalsIgnoreCase("changepassword")||StoredObjects.pagetype.equalsIgnoreCase("viewprofile")||StoredObjects.pagetype.equalsIgnoreCase("sendfeedback")
                        ||StoredObjects.pagetype.equalsIgnoreCase("completeprofile")){
                    fragmentcallinglay(new Profile());

                }else if(StoredObjects.pagetype.equalsIgnoreCase("completeprofile1")){
                    fragmentcallinglay(new VRegistnOne());
                }else if(StoredObjects.pagetype.equalsIgnoreCase("completeprofile2")){
                    fragmentcallinglay(new VRegistnTwo());
                }else if(StoredObjects.pagetype.equalsIgnoreCase("editoffer")){
                    fragmentcallinglay(new ManageOffersMain());
                }else if(StoredObjects.pagetype.equalsIgnoreCase("editproduct")){
                    fragmentcallinglay(new ManageProductsMain());
                }else if(StoredObjects.pagetype.equalsIgnoreCase("scanqr")){
                    fragmentcallinglay(new GenerateBill());
                }else{
                    fragmentcallinglay(new VendorHomePage());
                }
                menu_cart_img.setVisibility(View.GONE);
              /*  if(StoredObjects.Profileupdate.equalsIgnoreCase("No")){
                    UpdateProfileAlert("Please update your profile details");
                }*/
            }else{
                menu_cart_img.setVisibility(View.VISIBLE);
                if(StoredObjects.pagetype.equalsIgnoreCase("sendfeedback")||StoredObjects.pagetype.equalsIgnoreCase("editprofile")||StoredObjects.pagetype.equalsIgnoreCase("changepassword")){
                    fragmentcallinglay(new Profile());

                }else if(StoredObjects.pagetype.equalsIgnoreCase("productdetails")||
                        StoredObjects.pagetype.equalsIgnoreCase("productreviews")){
                    fragmentcallinglay(new CustomerProductlist());
                }else if( StoredObjects.redirecpage.equalsIgnoreCase("homepage")&&  StoredObjects.pagetype.equalsIgnoreCase("mycart")){
                    fragmentcallinglay(new CstmrLandingPage());
                }else if( StoredObjects.redirecpage.equalsIgnoreCase("productspage")&& StoredObjects.pagetype.equalsIgnoreCase("mycart")){
                    fragmentcallinglay(new CustomerProductlist());
                }else if(StoredObjects.pagetype.equalsIgnoreCase("myorders")){
                    fragmentcallinglay(new Mycart());
                }

                else{
                    fragmentcallinglay(new CstmrLandingPage());
                }
            }

        }else{

            if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
               // getSupportFragmentManager().popBackStack();
                backclickevent();
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

        AlertDialog.Builder builder = new AlertDialog.Builder(Sidemenu.this);

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
        AlertDialog.Builder builder = new AlertDialog.Builder(Sidemenu.this);

        builder.setMessage(string)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Sidemenu.this.finish();
                        startActivity(new Intent(Sidemenu.this,LandingPage.class));

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
        AlertDialog.Builder builder = new AlertDialog.Builder(Sidemenu.this);

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

           /* if(StoredObjects.UserType.equalsIgnoreCase("Vendor")){
                actionbar_lay.setVisibility(View.VISIBLE);
            }else{
                actionbar_lay.setVisibility(View.GONE);
            }*/
            back_img.setVisibility(View.GONE);
            menu_img.setVisibility(View.GONE);
        }else{
            back_img.setVisibility(View.VISIBLE);
            menu_img.setVisibility(View.GONE);
            actionbar_lay.setVisibility(View.VISIBLE);
        }

    }

}