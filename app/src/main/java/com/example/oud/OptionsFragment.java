package com.example.oud;

import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class OptionsFragment extends Fragment {

    private static final String TAG = OptionsFragment.class.getSimpleName();

    private ArrayList<Integer> mIcons;
    private ArrayList<String> mTitles;
    private ArrayList<View.OnClickListener> mClickListeners;

    private RecyclerView mRecyclerView;


    public OptionsFragment() {
        // Required empty public constructor
    }

    public static OptionsFragment newInstance(ArrayList<Integer> icons, ArrayList<String> titles, ArrayList<View.OnClickListener> clickListeners) {
        OptionsFragment instance = new OptionsFragment();
        instance.mIcons = icons;
        instance.mTitles = titles;
        instance.mClickListeners = clickListeners;

        return instance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_options, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //view.setOnClickListener(v -> Log.i(TAG, "Outer View"));


        OptionsRecyclerViewAdapter adapter = new OptionsRecyclerViewAdapter(
                getContext(),
                mIcons,
                mTitles,
                mClickListeners
        );

        mRecyclerView = view.findViewById(R.id.recycler_view_options);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        //layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);

        //view.findViewById(R.id.view_options_click_listener).setOnClickListener(v -> Log.i(TAG, "Outer View"));

        //mRecyclerView.setOnTouchListener((v, event) -> view.dispatchTouchEvent(event));


    }

    public static boolean doesOptionsFragmentExist(FragmentActivity activity, @IdRes int containerId) {
        return activity.getSupportFragmentManager().findFragmentById(containerId) instanceof OptionsFragment;
    }

    public static void hideOptionsFragment(FragmentActivity activity, @IdRes int fragmentContainerId) {
        FragmentManager manager = activity.getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.remove(manager.findFragmentById(fragmentContainerId))
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    public static Builder builder(FragmentActivity activity) {
        return new Builder(activity);
    }

    public static class Builder {

        private FragmentActivity fragmentActivity;
        private int containerId = R.id.container_options;

        private ArrayList<Integer> icons;
        private ArrayList<String> titles;
        private ArrayList<View.OnClickListener> clickListeners;

        public Builder(FragmentActivity fragmentActivity) {
            this.fragmentActivity = fragmentActivity;
        }

        public Builder addItem(@Nullable @DrawableRes Integer iconId,
                               @Nullable String title,
                               @Nullable View.OnClickListener onClickListener) {
            if (icons == null) icons = new ArrayList<>();
            if (titles == null) titles = new ArrayList<>();
            if (clickListeners == null) clickListeners = new ArrayList<>();

            if (iconId == null) icons.add(R.drawable.ic_oud);
            else icons.add(iconId);

            if(title == null) titles.add("item" + titles.size());
            else titles.add(title);

            if (onClickListener == null) {
                String currentTitle = titles.get(titles.size() - 1);
                clickListeners.add(v -> Log.i(TAG, "Item with title : "
                        + currentTitle + " has an empty click listener."));
            } else clickListeners.add(onClickListener);

            return this;
        }

        public Builder inContainer(@IdRes int containerId) {
            this.containerId = containerId;
            return this;
        }

        public void show() {
            FragmentManager manager = fragmentActivity.getSupportFragmentManager();
            OptionsFragment optionsFragment = newInstance(icons, titles, clickListeners);
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(containerId, optionsFragment, Constants.OPTIONS_FRAGMENT_TAG)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        }


    }
}
