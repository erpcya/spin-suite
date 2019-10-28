/*************************************************************************************
 * Product: Spin-Suite (Mobile Suite)                                                *
 * Copyright (C) 2012-2018 E.R.P. Consultores y Asociados, C.A.                      *
 * Contributor(s): Yamel Senih ysenih@erpya.com                                      *
 * This program is free software: you can redistribute it and/or modify              *
 * it under the terms of the GNU General Public License as published by              *
 * the Free Software Foundation, either version 3 of the License, or                 *
 * (at your option) any later version.                                               *
 * This program is distributed in the hope that it will be useful,                   *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of                    *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the                     *
 * GNU General Public License for more details.                                      *
 * You should have received a copy of the GNU General Public License                 *
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.            *
 ************************************************************************************/
package org.erpya.component.window;

import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import org.erpya.component.R;
import org.erpya.component.base.CustomPagerAdapter;
import org.erpya.component.base.CustomViewPager;
import org.erpya.component.base.ITab;
import org.erpya.component.window.event.WindowEvent;
import org.erpya.component.window.event.WindowEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Dynamic activity_wizard
 */
public abstract class WindowManager extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private CustomPagerAdapter sectionsPagerAdapter;
    /** Is last action  */
    private boolean isLastAction;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private CustomViewPager viewPagerController;
    /**	Event Listener	*/
    private List<WindowEventListener> listeners = new ArrayList<WindowEventListener>();
    /** Events  */
    private final int VALIDATE = 0;
    private final int START = 1;
    private final int FINISH = 2;
    private final int CHANGE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        sectionsPagerAdapter = new CustomPagerAdapter(getSupportFragmentManager());
        sectionsPagerAdapter.setArguments(getIntent().getExtras());
        //  Add Steps
        setupTabs();
        // Set up the ViewPager with the sections adapter.
        viewPagerController = findViewById(R.id.container);
        viewPagerController.setAdapter(sectionsPagerAdapter);
        viewPagerController.setEnableScroll(false);
        setupAdapter();
        setupActions();
        //  Add listener
        viewPagerController.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeViewText(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //  Default
        changeViewText(0);
        fireDeviceEvent(START);
    }

    /**
     * enable page scroll
     * @param isEnabled
     */
    protected void setEnableScroll(boolean isEnabled) {
        viewPagerController.setEnableScroll(isEnabled);
    }

    /**
     * Get view Pager controller
     * @return
     */
    protected CustomViewPager getViewPagerController() {
        return viewPagerController;
    }

    /**
     * Get adapter
     * @return
     */
    protected CustomPagerAdapter getSectionsPagerAdapter() {
        return sectionsPagerAdapter;
    }

    /**
     * Change view text from position
     * @param position
     */
    private void changeViewText(int position) {
        //  For next action
        if((position + 1) == sectionsPagerAdapter.getCount()) {
            isLastAction = true;
        } else {
            isLastAction = false;
        }
        int currentStep = viewPagerController.getCurrentItem();
        ITab tab = sectionsPagerAdapter.getStepDefinition(currentStep);
        tab.saveIt();
        fireDeviceEvent(CHANGE);
    }

    /**
     * Get current Item position
     * @return
     */
    public int getCurrentItem() {
        return viewPagerController.getCurrentItem();
    }

    /**
     * Get tab count
     * @return
     */
    protected int getCount() {
        return sectionsPagerAdapter.getCount();
    }

    /**
     * Verify last action
     * @return
     */
    protected boolean isLastAction() {
        return isLastAction;
    }

    /**
     * Previous Action
     */
    protected void previousAction() {
        viewPagerController.setCurrentItem(viewPagerController.getCurrentItem() - 1);
        fireDeviceEvent(CHANGE);
    }

    /**
     * Next Action
     */
    protected void nextAction() {
        int currentStep = viewPagerController.getCurrentItem();
        ITab tab = sectionsPagerAdapter.getStepDefinition(currentStep);
        boolean isValid = tab.validateIt();
        fireDeviceEvent(VALIDATE);
        if(tab.isMandatory()) {
            if(isValid) {
                viewPagerController.setCurrentItem(currentStep + 1);
            }
        } else {
            viewPagerController.setCurrentItem(currentStep + 1);
        }
        fireDeviceEvent(CHANGE);
        //  Close if is last
        if((currentStep + 1) == sectionsPagerAdapter.getCount()
                && isLastAction) {
            fireDeviceEvent(FINISH);
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_wizard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Add step for view
     * @param step
     */
    protected void addTab(ITab step) {
        sectionsPagerAdapter.addStep(step);
    }

    /**
     * Initialize here child
     * Add all Tabs
     */
    protected abstract void setupTabs();

    /**
     * Initialize here child
     * Add all Tabs
     */
    protected abstract void setupAdapter();

    /**
     * Setup all action buttons
     */
    protected abstract void setupActions();

    /**
     * Please implement this method for custom view
     * @return
     */
    protected abstract int getContentView();

    /**
     * Add Listener
     * @param listener
     * @return void
     */
    public void addWindowListener(WindowEventListener listener) {
        listeners.add(listener);
    }

    /**
     * Remove Listener
     * @param listener
     * @return void
     */
    public void removeWindowListener(WindowEventListener listener) {
        listeners.remove(listener);
    }

    /**
     * Fire event
     * @param eventType
     * @return void
     */
    private void fireDeviceEvent(int eventType) {
        WindowEvent eventSource = new WindowEvent(this);
        //	Get Iterator
        Iterator<WindowEventListener> iterator = listeners.iterator();
        while(iterator.hasNext()) {
            WindowEventListener listener = ((WindowEventListener) iterator.next());
            //	Iterate
            if(eventType == VALIDATE) {
                listener.onValidate(eventSource);
            } else if(eventType == START) {
                listener.onStart(eventSource);
            } else if(eventType == CHANGE) {
                listener.onChange(eventSource);
            } else if(eventType == FINISH) {
                listener.onFinish(eventSource);
            }
        }
    }
}
