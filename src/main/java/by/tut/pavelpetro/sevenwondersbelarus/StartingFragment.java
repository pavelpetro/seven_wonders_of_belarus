package by.tut.pavelpetro.sevenwondersbelarus;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass. The fragment StartingFragment is showed only at the first launch.
 * The user see greetings and can choose language. After clicking <i></i>mNextView</i> the user get into
 * <code>ListWonderFragment</code>.
 */
public class StartingFragment extends Fragment {

    TextView mLanguageBeView;
    TextView mLanguageEnView;
    TextView mLanguageRuView;
    TextView mWelcomeView;
    ImageView mNextView;
    private OnFragmentInteractionListener mListener;


    SharedPreferences mSettings;
    public static final String APP_PREFERENCES = "mySecretSettings";
    public static final String APP_PREFERENCES_FIRST = "first";

    public StartingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_starting, container, false);

        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);


        mLanguageBeView = (TextView) view.findViewById(R.id.languageBeView);
        mLanguageEnView = (TextView) view.findViewById(R.id.languageEnView);
        mLanguageRuView = (TextView) view.findViewById(R.id.languageRuView);
        mNextView = (ImageView) view.findViewById(R.id.nextView);
        mWelcomeView = (TextView) view.findViewById(R.id.welcomeView);
        //choosing custom language
        mLanguageBeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLang("be");
            }
        });
        mLanguageEnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLang("en");
            }
        });
        mLanguageRuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLang("ru");
            }
        });

        mNextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.changeFragment();
                SharedPreferences.Editor editor = mSettings.edit();
                editor.putBoolean(APP_PREFERENCES_FIRST, false);
                editor.apply();
            }
        });

        return view;
    }
    //set language and refresh mWelcomeView
    private void setLang(String language) {
        mListener.setLanguage(language);
        mWelcomeView.setText(R.string.welcome);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;//mListener to interact with activity
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}

