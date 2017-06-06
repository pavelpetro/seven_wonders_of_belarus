package by.tut.pavelpetro.sevenwondersbelarus;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.Locale;

/**
 * A main activity class, a wrapper for fragments. In this class the fragments are replaced and
 * the languages are changed.
 */
public class MainActivity extends AppCompatActivity implements OnFragmentInteractionListener{

    public static final String APP_PREFERENCES = "mySecretSettings";
    public static final String APP_PREFERENCES_LANGUAGE = "language";
    public static final String APP_PREFERENCES_FIRST = "first";
    SharedPreferences mSettings;
    String langTo = "en";
    private boolean firstLaunch = true;
    FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        //get firstLaunch flag
        if (mSettings.contains(APP_PREFERENCES_FIRST)) {
            firstLaunch = mSettings.getBoolean(APP_PREFERENCES_FIRST, true);
        }

        //get user's language
        if (mSettings.contains(APP_PREFERENCES_LANGUAGE)) {
            langTo = mSettings.getString(APP_PREFERENCES_LANGUAGE, "en");
        }
        //set language from settings
        setLanguage(langTo);
        //check for first launch
        if (firstLaunch) {//show starting fragment
            fm.beginTransaction()
                    .add(R.id.fragmentContainer, new StartingFragment())
                    .commit();
        } else {//no need to show starting fragment, show ListWonderFragment
            fm.beginTransaction()
                    .add(R.id.fragmentContainer, new ListWonderFragment())
                    .commit();
        }

    }

    @SuppressWarnings("deprecation")
    public void setLanguage(String langTo) {
        //set chosen user's language
        Locale locale = new Locale(langTo);
        Locale.setDefault(locale);

        Configuration config = new Configuration();

        config.setLocale(locale);//config.locale = locale;

        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N) {
            createConfigurationContext(config);
        } else {
            getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        }

        //createConfigurationContext(config);
        getResources().updateConfiguration(config, getApplicationContext().getResources().getDisplayMetrics());//cannot resolve yet
        //write into settings
        SharedPreferences.Editor editor = mSettings.edit();
        editor.putString(APP_PREFERENCES_LANGUAGE, langTo);
        editor.apply();
        //get appTitle
        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle(R.string.app_name);
        }

    }

    //change fragment to ListWonderFragment
    @Override
    public void changeFragment() {
        fm.beginTransaction()
                .replace(R.id.fragmentContainer, new ListWonderFragment())
                .commit();
    }
}
