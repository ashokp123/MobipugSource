package com.o3sa.mobipugapp.uicomponents;

import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.widget.LinearLayout;

/**
 * Created by Kiran on 21-09-2018.
 */
//EEE, MMM d, ''yy --- Ex: Wed, Jul 4, '01
//h:mm a  --- Ex: 12:08 PM
//K:mm a  --- Ex: 0:08 PM
//yyyy.MMMMM.dd hh:mm aaa --- Ex: 2001.July.04 12:08 PM
//EEE, d MMM yyyy HH:mm:ss --- Ex: Wed, 4 Jul 2001 12:08:56
//12 hrs to 24 hrs --- hh:mm a ---> kk:mm:ss

public class Constants {
    public static String DATEFORMAT1="dd/MM/yy";
    public static String DATEFORMAT2="dd/MM/yyyy";
    public static String DATEFORMAT3="MM/dd/yyyy";
    public static String DATEFORMAT4="dd-MM-yyyy";
    public static String DATEFORMAT5="dd-MM-yy";
    public static String DATEFORMAT6="yyyy.MMMMM.dd hh:mm aaa";
    public static String DATEFORMAT7="yyyy.MMMMM.dd";
    public static String DATEFORMAT8="dd MMM yyyy HH:mm:ss";
    public static String DATEFORMAT9="EEE, d MMM yyyy HH:mm:ss";
    public static String DATEFORMAT10="K:mm a";
    public static String DATEFORMAT11="h:mm a";
    public static int Num_Column;
    public static int Num_lines=0;
    public static String Gridview="Grid";
    public static String Listview="List";
    public static int ver_orientation= LinearLayoutManager.VERTICAL;
    public static int horizontal_orientation= LinearLayoutManager.HORIZONTAL;
    public static int matchParent= LinearLayout.LayoutParams.MATCH_PARENT;
    public static int wrapContent= LinearLayout.LayoutParams.WRAP_CONTENT;
    public static long ANIM_VIEWPAGER_DELAY = 3000;
    public static String POSTMETHOD="post";
    public static String GETMETHOD="get";
    public static String IMAGEUPLOAD="image";

    //View gravity top
    public static int top=  Gravity.TOP;
    //View gravity right
    public static int right= Gravity.RIGHT;
    //View gravity left
    public static int left=Gravity.LEFT;
    //View gravity bottom
    public static int bottom= Gravity.BOTTOM;
    //View gravity center
    public static int center= Gravity.CENTER;
    //View gravity center


    //View width match parent @ View gravity center @ font type bold
    public static String MatchCenterBold="Match@Center@Bold";
    //View width wrap parent @ View gravity center  @ font type bold
    public static String WrapCenterBold="Wrap@Center@Bold";
    //View width match parent @ View gravity left @ font type bold
    public static String MatchLeftBold="Match@Left@Bold";
    //View width wrap parent @ View gravity left @ font type bold
    public static String WrapLeftBold="Wrap@Left@Bold";
    //View width match parent @ View gravity right @ font type bold
    public static String MatchRightBold="Match@Right@Bold";
    //View width wrap parent @ View gravity right @ font type bold
    public static String WrapRightBold="Wrap@Right@Bold";
    //View width match parent @ View gravity bottom @ font type bold
    public static String MatchBottomBold="Match@Bottom@Bold";
    //View width wrap parent @ View gravity bottom @ font type bold
    public static String WrapBottomBold="Wrap@Bottom@Bold";
    //View width match parent @ View gravity top @ font type bold
    public static String MatchTopBold="Match@Top@Bold";
    //View width wrap parent @ View gravity top @ font type bold
    public static String WrapTopBold="Wrap@Top@Bold";


    //View width match parent @ View gravity center @ font type normal
    public static String MatchCenterNormal="Match@Center@Normal";
    //View width wrap parent @ View gravity center @ font type normal
    public static String WrapCenterNormal="Wrap@Center@Normal";
    //View width match parent @ View gravity left @ font type normal
    public static String MatchLeftNormal="Match@Left@Normal";
    //View width wrap parent @ View gravity left @ font type normal
    public static String WrapLeftNormal="Wrap@Left@Normal";
    //View width match parent @ View gravity right @ font type normal
    public static String MatchRightNormal="Match@Right@Normal";
    //View width wrap parent @ View gravity right @ font type normal
    public static String WrapRightNormal="Wrap@Right@Normal";
    //View width match parent @ View gravity top @ font type normal
    public static String MatchTopNormal="Match@Top@Normal";
    //View width wrap parent @ View gravity top @ font type normal
    public static String WrapTopNormal="Wrap@Top@Normal";
    //View width match parent @ View gravity bottom @ font type normal
    public static String MatchBottomNormal="Match@Bottom@Normal";
    //View width wrap parent @ View gravity bottom @ font type normal
    public static String WrapBottomNormal="Wrap@Bottom@Normal";


    //View width match parent @ View gravity center @ font type SemiBold
    public static String MatchCenterSemiBold="Match@Center@SemiBold";
    //View width wrap parent @ View gravity center @ font type SemiBold
    public static String WrapCenterSemiBold="Wrap@Center@SemiBold";
    //View width match parent @ View gravity left @ font type SemiBold
    public static String MatchLeftSemiBold="Match@Left@SemiBold";
    //View width wrap parent @ View gravity left @ font type SemiBold
    public static String WrapLeftSemiBold="Wrap@Left@SemiBold";
    //View width match parent @ View gravity right @ font type SemiBold
    public static String MatchRightSemiBold="Match@Right@SemiBold";
    //View width wrap parent @ View gravity right @ font type SemiBold
    public static String WrapRightSemiBold="Wrap@Right@SemiBold";
    //View width match parent @ View gravity top @ font type SemiBold
    public static String MatchTopSemiBold="Match@Top@SemiBold";
    //View width wrap parent @ View gravity top @ font type SemiBold
    public static String WrapTopSemiBold="Wrap@Top@SemiBold";
    //View width match parent @ View gravity bottom @ font type SemiBold
    public static String MatchBottomSemiBold="Match@Bottom@SemiBold";
    //View width wrap parent @ View gravity bottom @ font type SemiBold
    public static String WrapBottomSemiBold="Wrap@Bottom@SemiBold";


    //View width match parent @ View gravity center @ font type Italic
    public static String MatchCenterItalic="Match@Center@Italic";
    //View width wrap parent @ View gravity center @ font type Italic
    public static String WrapCenterItalic="Wrap@Center@Italic";
    //View width match parent @ View gravity left @ font type Italic
    public static String MatchLeftItalic="Match@Left@Italic";
    //View width wrap parent @ View gravity left @ font type Italic
    public static String WrapLeftItalic="Wrap@Left@Italic";
    //View width match parent @ View gravity right @ font type Italic
    public static String MatchRightItalic="Match@Right@Italic";
    //View width wrap parent @ View gravity right @ font type Italic
    public static String WrapRightItalic="Wrap@Right@Italic";
    //View width match parent @ View gravity top @ font type Italic
    public static String MatchTopItalic="Match@Top@Italic";
    //View width wrap parent @ View gravity top @ font type Italic
    public static String WrapTopItalic="Wrap@Top@Italic";
    //View width match parent @ View gravity bottom @ font type Italic
    public static String MatchBottomItalic="Match@Bottom@Italic";
    //View width wrap parent @ View gravity bottom @ font type Italic
    public static String WrapBottomItalic="Wrap@Bottom@Italic";


    //Sizes
    public static float XXXXSmall=9f;
    public static float XXXSmall=10f;
    public static float XXSmall=11f;
    public static float XSmall=12f;
    public static float Small=13f;
    public static float Medium=14f;
    public static float Normal=15f;
    public static float XNormal=16f;
    public static float XXNormal=17f;
    public static float XXXNormal=18f;
    public static float Large=19f;
    public static float XLarge=20f;
    public static float XXLarge=21f;
    public static float XXLarge1=22f;
    public static float XXLarge2=23f;
    public static float XXLarge3=24f;
    public static float XXLarge4=35f;
    public static float XXLarge5=30f;
    //font
    public static String Roboto="@1";
    public static String Gibson="@2";
    public static String SFUIText="@3";

}
