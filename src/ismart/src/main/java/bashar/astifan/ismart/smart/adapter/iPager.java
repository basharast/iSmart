package bashar.astifan.ismart.smart.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import bashar.astifan.ismart.R;
import bashar.astifan.ismart.viewpager.NavigationTabBar;

/**
 *
 *
 * @author Bashar Astifan <br>
 *         <a href=
 *         "astifan.online"
 *         >Read More</a> <br>
 * @version 2.0
 *
 */
public class iPager {
    public iPager(int pagesNumber){
        count=pagesNumber;
    }
    public iPager(){
    }
    int count=0;
    int activeItem=0;
    TabItem[] items;
    Pages[] pages;
    public void setActivePage(int activePage){
        activeItem=activePage;
    }
    public void setPages(Pages[] pages){
        this.pages=pages;
    }
    public void setTabItems(TabItem[] items){
        this.items=items;
    }

    public void initPager(final Context ctx, int viewPaggerId, int TabBar, final int TabBack) {
        final ViewPager viewPager = (ViewPager) ((Activity)ctx).findViewById(viewPaggerId);
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return count;
            }

            @Override
            public boolean isViewFromObject(final View view, final Object object) {
                return view.equals(object);
            }

            @Override
            public void destroyItem(final View container, final int position, final Object object) {
                ((ViewPager) container).removeView((View) object);
            }

            @Override
            public Object instantiateItem(final ViewGroup container, final int position) {
                final View view = LayoutInflater.from(((Activity)ctx).getBaseContext()).inflate(pages[position].layoutId, null, false);
               if(pages[position].layoutClass!=null) {
                   try {
                     Object clas=pages[position].layoutClass.newInstance();
                     Class<?>[] paramTypes = {View.class,int.class};
                     clas.getClass().getMethod(pages[position].methodName,paramTypes).invoke(clas,view,position);
                   } catch (IllegalArgumentException e) { // exception handling omitted for brevity
                   } catch (IllegalAccessException e) { // exception handling omitted for brevity
                   } catch (InvocationTargetException e) { // exception handling omitted for brevity
                   } catch (NoSuchMethodException e) {
                       e.printStackTrace();
                   } catch (InstantiationException e) {
                       e.printStackTrace();
                   }
               }
                //final TextView txtPage = (TextView) view.findViewById(R.id.txt_vp_item_page);
                // txtPage.setText(String.format("Page #%d", position));
                container.addView(view);
                return view;
            }
        });

        final String[] colors = ((Activity)ctx).getResources().getStringArray(R.array.default_preview);

        final NavigationTabBar navigationTabBar = (NavigationTabBar) ((Activity)ctx).findViewById(TabBar);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();

        for(int i=0;i<count;i++) {
            models.add(new NavigationTabBar.Model(((Activity) ctx).getResources().getDrawable(items[i].icon), Color.parseColor(items[i].color),items[i].title));
        }


        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager, activeItem);

        navigationTabBar.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                navigationTabBar.getModels().get(position).hideBadge();
            }

            @Override
            public void onPageScrollStateChanged(final int state) {

            }
        });

        navigationTabBar.post(new Runnable() {
            @Override
            public void run() {
                final View bgNavigationTabBar = ((Activity)ctx).findViewById(TabBack);
                bgNavigationTabBar.getLayoutParams().height = (int) navigationTabBar.getBarHeight();
                bgNavigationTabBar.requestLayout();
            }
        });

        boolean ntb = navigationTabBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < navigationTabBar.getModels().size(); i++) {
                    final NavigationTabBar.Model model = navigationTabBar.getModels().get(i);

                      model.setBadgeTitle(items[i].badge);

                    navigationTabBar.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            model.showBadge();
                        }
                    }, i * 100);
                }
            }
        }, 500);
    }

     public class TabItem extends iPager{
         public TabItem(int icon, String title){

             this.icon=icon;
             this.title=title;

         }
         public TabItem(int icon){
             this.icon=icon;
         }
         public void setColor(String color){
             this.color=color;
         }

         public void setBadgeTitle(String badge){
             this.badge=badge;
         }
         String badge="";
         int icon=0;
         String color="#df5a55";
         String title="item";
    }
    public class Pages extends  iPager{
        public Pages(int layoutId, Class<?> layoutClass,String methodName){
            this.layoutId=layoutId;
            this.layoutClass=layoutClass;
            this.methodName=methodName;
        }

        public Pages(int layoutId){
            this.layoutId=layoutId;
        }
        String methodName="iSmart";
        int layoutId=0;
        Class<?> layoutClass=null;
    }
}
