package ru.anit.anitfresh;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import ru.anit.anitfresh.Interators.synchronizer.InteratorSynhronizerNotifi;
import ru.anit.anitfresh.Interators.synchronizer.helpers.SynchronizerHelper;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();


        SynchronizerHelper.start(false);

        assertEquals("ru.anit.anitfresh", appContext.getPackageName());
    }
}
