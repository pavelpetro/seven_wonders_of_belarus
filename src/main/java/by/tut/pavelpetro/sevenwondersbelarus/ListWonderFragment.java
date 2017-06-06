package by.tut.pavelpetro.sevenwondersbelarus;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

/**
 * A class <code>ListWonderFragment</code> is the main fragment for showing 7 wonders. The class consists cards,
 * each one has a title, the ImageView for liking, the main text and image, icons for sharing and getting location.
 */
public class ListWonderFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    ArrayList<Wonder> listItems = new ArrayList<>();
    private RecyclerView mWonderRecyclerView;
    WonderAdapter mAdapter;
    SharedPreferences mSettings;
    static final String APP_PREFERENCES = "mySecretSettings";
    static final String APP_PREFERENCES_LANGUAGE = "language";
    //uries for getting more informationin Wiki
    private Uri[] enUriList = {//English uries
            Uri.parse("https://en.wikipedia.org/wiki/Bia%C5%82owie%C5%BCa_Forest"),
            Uri.parse("https://en.wikipedia.org/wiki/Brest_Fortress"),
            Uri.parse("https://en.wikipedia.org/wiki/Lake_Narach"),
            Uri.parse("https://en.wikipedia.org/wiki/Mir_Castle_Complex"),
            Uri.parse("https://en.wikipedia.org/wiki/Saint_Sophia_Cathedral_in_Polotsk"),
            Uri.parse("https://en.wikipedia.org/wiki/Babruysk_fortress"),
            Uri.parse("http://www.catholic.by/2/en/belarus/sanctuarium/100262-budslau.html")
    };
    private Uri[] beUriList = {//Belarussian uries
            Uri.parse("https://be.wikipedia.org/wiki/Белавежская_пушча"),
            Uri.parse("https://be.wikipedia.org/wiki/Брэсцкая_крэпасць"),
            Uri.parse("https://be.wikipedia.org/wiki/Возера_Нарач"),
            Uri.parse("https://be.wikipedia.org/wiki/Мірскі_замак"),
            Uri.parse("https://be.wikipedia.org/wiki/Сафійскі_сабор,_Полацк"),
            Uri.parse("https://be.wikipedia.org/wiki/Бабруйская_крэпасць"),
            Uri.parse("https://be.wikipedia.org/wiki/Касцёл_Унебаўзяцця_Найсвяцейшай_Дзевы_Марыі,_Будслаў")

    };
    private Uri[] ruUriList = {//Russian uries
            Uri.parse("https://ru.wikipedia.org/wiki/Беловежская_пуща"),
            Uri.parse("https://ru.wikipedia.org/wiki/Брестская_крепость"),
            Uri.parse("https://ru.wikipedia.org/wiki/Нарочь_(озеро)"),
            Uri.parse("https://ru.wikipedia.org/wiki/Мирский_замок"),
            Uri.parse("https://ru.wikipedia.org/wiki/Софийский_собор_(Полоцк)"),
            Uri.parse("https://ru.wikipedia.org/wiki/Бобруйская_крепость"),
            Uri.parse("https://ru.wikipedia.org/wiki/Костёл_Вознесения_Пресвятой_Девы_Марии_(Будслав)")

    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSettings = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.fragment_list_wonder, container, false);
            mWonderRecyclerView = (RecyclerView) view.findViewById(R.id.cardView);
            mWonderRecyclerView.setHasFixedSize(true);
            LinearLayoutManager MyLayoutManager = new LinearLayoutManager(getActivity());
            MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            if (listItems.size() > 0 & mWonderRecyclerView != null) {
                mWonderRecyclerView.setAdapter(new WonderAdapter(listItems));
            }
            mWonderRecyclerView.setLayoutManager(MyLayoutManager);

            updateUI();//create Wonders and populate listItems. Create adapter
            setHasOptionsMenu(true);//create menu for fragment

            return view;
        }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
    }

    public class WonderAdapter extends RecyclerView.Adapter<WonderHolder> {
            private ArrayList<Wonder> list;

            WonderAdapter(ArrayList<Wonder> Data) {
                list = Data;
            }

            @Override
            public WonderHolder onCreateViewHolder(ViewGroup parent,int viewType) {
                // create a new view
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.category_list_item_1, parent, false);
                return new WonderHolder(view);
            }

            @Override
            public void onBindViewHolder(final WonderHolder holder, int position) {

                Wonder wonder = listItems.get(position);
                holder.bindData(wonder);

            }

            @Override
            public int getItemCount() {
                return list.size();
            }
    }

    private class WonderHolder extends RecyclerView.ViewHolder{

        private Wonder mWonder;
        ImageView mImageView;
        TextView mNameTextView;
        TextView mDescriptionTextView;
        private TextView mReadMoreView;
        private ImageView mLikeView;
        private ImageView mShareView;
        private ImageView mLocationView;

        WonderHolder(View itemView){
            super(itemView);

            mImageView = (ImageView) itemView.findViewById(R.id.imageView);
            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //change size clicking on imageView: big to small (and vice versa) with animation
                    if(mWonder.isBigImage()){//make initial size
                        view.animate().scaleX(1.0f).scaleY(1.0f).translationX(0).translationY(0);
                        mWonder.setBigImage(false);
                    } else {//make double size
                        view.animate().translationX(-180).translationY(60).scaleX(2).scaleY(2);
                        mWonder.setBigImage(true);
                    }
                }
            });

            mNameTextView = (TextView) itemView.findViewById(R.id.textview_name);
            mDescriptionTextView = (TextView) itemView.findViewById(R.id.textview_description);
            mReadMoreView = (TextView) itemView.findViewById(R.id.readMoreView);
            mReadMoreView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String langTo = "en";
                    //get user's language
                    if (mSettings.contains(APP_PREFERENCES_LANGUAGE)) {
                        langTo = mSettings.getString(APP_PREFERENCES_LANGUAGE, "en");
                    }
                    //create intent. English is default
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, mWonder.getUriEn());
                    //change intent according to user's language
                    switch(langTo){
                        case("be"):
                            browserIntent = new Intent(Intent.ACTION_VIEW, mWonder.getUriBe());
                            break;
                        case("ru"):
                            browserIntent = new Intent(Intent.ACTION_VIEW, mWonder.getUriRu());
                            break;
                    }

                    startActivity(browserIntent);

                }
            });
            mShareView = (ImageView) itemView.findViewById(R.id.shareView);
            mShareView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //check for internet connectivity
                    if(isNetworkAvailable()) {
                        //create share intent
                        Uri uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + mWonder.getImageId());
                        Intent shareIntent = new Intent();
                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"yourMail@gmail.com"});
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, mWonder.getName());
                        shareIntent.putExtra(Intent.EXTRA_TEXT, mWonder.getDescription());
                        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                        shareIntent.setType("image/jpeg");
                        startActivity(Intent.createChooser(shareIntent, "yourMail@gmail.com"));
                    } else {
                        Toast.makeText(getActivity(),
                                getText(R.string.checkConnection), Toast.LENGTH_SHORT)
                                .show();
                    }
                }
            });
            mLikeView = (ImageView) itemView.findViewById(R.id.likeView);
            mLikeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(mWonder.isLiked()) {//like, change to to dislike
                        mWonder.setLiked(false);
                        mLikeView.setImageResource(R.drawable.btn_star_big_off);
                        Toast.makeText(getActivity(),
                                getActivity().getText(R.string.notLike) + " " +
                                        mWonder.getName() + " " +
                                        getActivity().getText(R.string.noMore), Toast.LENGTH_SHORT)
                                .show();
                    } else {//dislike, change to like
                        mWonder.setLiked(true);
                        mLikeView.setImageResource(R.drawable.btn_star_big_on);
                        Toast.makeText(getActivity(),
                                getActivity().getText(R.string.like) +
                                        " " + mWonder.getName() + "!", Toast.LENGTH_SHORT)
                                .show();
                    }
                }
            });
            mLocationView = (ImageView) itemView.findViewById(R.id.locationView);
            mLocationView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //create location intent
                    Uri geo = Uri.parse(mWonder.getGeoString());
                    Intent geoIntent = new Intent(Intent.ACTION_VIEW, geo);
                    startActivity(geoIntent);
                }
            });
        }

        void bindData(Wonder wonder){
            mWonder = wonder;

            mImageView.setImageResource(wonder.getImageId());
            mNameTextView.setText(wonder.getName());
            mDescriptionTextView.setText(wonder.getDescription());
        }
    }
    //creates Wonders, populates listItems and makes WonderAdapter
    private void updateUI(){
        String[] wonderNames = {getActivity().getString(R.string.wonderName1),
                getActivity().getString(R.string.wonderName2),
                getActivity().getString(R.string.wonderName3),
                getActivity().getString(R.string.wonderName4),
                getActivity().getString(R.string.wonderName5),
                getActivity().getString(R.string.wonderName6),
                getActivity().getString(R.string.wonderName7)};
        String[] descriptionText = {getString(R.string.wonder1),
                getActivity().getString(R.string.wonder2),
                getActivity().getString(R.string.wonder3),
                getActivity().getString(R.string.wonder4),
                getActivity().getString(R.string.wonder5),
                getActivity().getString(R.string.wonder6),
                getActivity().getString(R.string.wonder7)};
        String[] geoString = {
                "geo:0,0?q=Беловежская+пуща+Каменюки+Каменецкий+район+Брестская+область&z=8",
                "geo:0,0?q=Brest+Fortress&z=8",
                "geo:0,0?q=lake+narach&z=8",
                "geo:0,0?q=Mir+Castle+Complex&z=8",
                "geo:0,0?q=Polotsk+Saint+Sophia+Cathedral&z=8",
                "geo:0,0?q=Belarus+Babruysk+Fortress&z=8",
                "geo:0,0?q=National+Sanctuary+of+the+Mother+of+God+of+Budslau&z=8"
        };
        int[] image = {R.drawable.drawable1,
                R.drawable.drawable2,
                R.drawable.drawable3,
                R.drawable.drawable4,
                R.drawable.drawable5,
                R.drawable.drawable6,
                R.drawable.drawable7
        };
        listItems.clear();
        for(int i =0;i<wonderNames.length;i++){
            Wonder wonder = new Wonder();
            wonder.setName(wonderNames[i]);
            wonder.setDescription(descriptionText[i]);
            wonder.setImageId(image[i]);
            wonder.setUriEn(enUriList[i]);
            wonder.setUriBe(beUriList[i]);
            wonder.setUriRu(ruUriList[i]);
            wonder.setGeoString(geoString[i]);
            listItems.add(wonder);
        }
        mAdapter = new WonderAdapter(listItems);
        mWonderRecyclerView.setAdapter(mAdapter);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //set language items
        switch(id){
            case(R.id.action_be):
                mListener.setLanguage("be");
                break;
            case(R.id.action_en):
                mListener.setLanguage("en");
                break;
            case(R.id.action_ru):
                mListener.setLanguage("ru");
                break;
        }
        updateUI();
        return super.onOptionsItemSelected(item);
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



