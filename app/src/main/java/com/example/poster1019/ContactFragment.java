package com.example.poster1019;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.materialswitch.MaterialSwitch;

public class ContactFragment extends Fragment {

    private ImageView aboutUsImage;
    private LinearLayout layoutEmail;
    private MaterialSwitch aboutUsToggle;
    private TextView aboutUsSubtitle;
    private TextView aboutUsTitle;
    private TextView description;
    private TextView getInTouchText;
    private View divider;

    public ContactFragment(){
        super(R.layout.fragment_contact);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.layoutEmail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEmailApp(getString(R.string.contact_email));
            }
        });
        // Binds XML elements to fields by their XML IDs
        aboutUsImage = view.findViewById(R.id.aboutUsImage);
        aboutUsToggle = view.findViewById(R.id.aboutUsToggle);
        aboutUsSubtitle = view.findViewById(R.id.aboutUsSubtitle);
        aboutUsTitle = view.findViewById(R.id.aboutUsTitle);
        description = view.findViewById(R.id.description);
        divider = view.findViewById(R.id.divider);
        getInTouchText = view.findViewById(R.id.getInTouchText);
        layoutEmail = view.findViewById(R.id.layoutEmail);
        // Attaches listeners to necessary UI elements
        aboutUsToggle.setOnClickListener(v -> toggleContent());
    }

    /**
     * This method open's the default email application on the user's device.
     *
     * @param emailAddress the email address being sent to
     */
    private void openEmailApp(String emailAddress){
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:"+emailAddress));
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(requireContext(),R.string.no_email_app,Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method toggles the visibility of the About Us (company) and Creator
     * (Ethan Rocklin, modifier) information in the Contact page.
     */
    private void toggleContent() {
        if (aboutUsToggle.isChecked()) {
            // Makes unneeded elements invisible
            divider.setVisibility(View.INVISIBLE);
            getInTouchText.setVisibility(View.INVISIBLE);
            layoutEmail.setVisibility(View.INVISIBLE);
            // Changes the values of needed elements to match the creator's info
            aboutUsImage.setImageResource(R.drawable.app_creator);
            aboutUsTitle.setText(R.string.creator_name);
            aboutUsSubtitle.setText(R.string.creator_literal);
            description.setText(R.string.creator_intro);
        } else {
            // Makes needed elements visible
            divider.setVisibility(View.VISIBLE);
            getInTouchText.setVisibility(View.VISIBLE);
            layoutEmail.setVisibility(View.VISIBLE);
            // Changes the values of needed elements to match the company's info
            aboutUsImage.setImageResource(R.mipmap.ic_launcher_round);
            aboutUsTitle.setText(R.string.app_name);
            aboutUsSubtitle.setText(R.string.about_us);
            description.setText(R.string.company_intro);
        }
    }
}
