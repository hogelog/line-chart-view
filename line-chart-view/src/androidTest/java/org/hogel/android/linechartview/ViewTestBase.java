package org.hogel.android.linechartview;

import android.app.Activity;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public abstract class ViewTestBase {
    Activity activity;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.setupActivity(Activity.class);
    }

    @After
    public void tearDown() throws Exception {

    }
}
