package com.example.poster1019;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.imageview.ShapeableImageView;

import java.util.Random;

/**
 * This class handles the logic needed for the Random Stills page to function, such as
 * processing and displaying a random still and a short description of what is happening
 * in the scene.
 */

public class RandomStillsFragment extends Fragment {

    private Context context;
    private int[] stills;
    private ShapeableImageView stillImage;
    private String[] descriptions;
    private TextView stillDescription;

    public RandomStillsFragment() {
        super(R.layout.fragment_random);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Sets listeners for XML elements
        view.findViewById(R.id.randomizeButton).setOnClickListener(v -> randomizeStill());
        // Initializes the Fragment's fields
        context = getContext();
        stillImage = view.findViewById(R.id.stillImage);
        stillDescription = view.findViewById(R.id.stillDescription);
        initStills();
        // Randomizes the initial still and description
        randomizeStill();
    }

    /**
     * This method creates the arrays that contain the integer IDs of the images and
     * their descriptions.
     */
    private void initStills() {
        // Initializes the stills
        stills = new int [] {
                R.drawable.still_backrooms,
                R.drawable.still_doom_arrives,
                R.drawable.still_dune_worm,
                R.drawable.still_gatsby_car,
                R.drawable.still_titan_fight
        };
        // Initializes the descriptions for the stills
        Resources res = context.getResources();
        descriptions = res.getStringArray(R.array.still_descriptions);
    }

    /**
     * This method swaps out the current still and description for random ones.
     */
    public void randomizeStill() {
        Random rand = new Random();
        int i = rand.nextInt(stills.length);
        stillImage.setImageResource(stills[i]);
        stillDescription.setText(descriptions[i]);
    }

}
