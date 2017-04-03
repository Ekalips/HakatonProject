package com.ekalips.hakatonproject.auth;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.PermissionChecker;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ekalips.hakatonproject.R;
import com.ekalips.hakatonproject.data.Attachment;
import com.ekalips.hakatonproject.databinding.FragmentSetPhotoBinding;
import com.ekalips.hakatonproject.events.SetButtonTextEvent;
import com.ekalips.hakatonproject.events.TakePictureFromCameraEvent;
import com.ekalips.hakatonproject.stuff.StepFragment;
import com.ekalips.hakatonproject.stuff.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class SetPhotoFragment extends StepFragment {


    public static final int ROLE_TEAM = 0;
    public static final int ROLE_USER = 1;


    @IntDef(value = {ROLE_TEAM, ROLE_USER})
    private @interface PhotoRole {
    }

    public static final String ARG_ROLE = "arg_role";

    public SetPhotoFragment() {
        // Required empty public constructor
    }

    public static SetPhotoFragment newInstance(@PhotoRole int role) {

        Bundle args = new Bundle();
        args.putInt(ARG_ROLE, role);
        SetPhotoFragment fragment = new SetPhotoFragment();
        fragment.setArguments(args);
        return fragment;
    }


    int role;

    FragmentSetPhotoBinding binding;
    private final int REQUEST_TAKE_IMAGE = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_set_photo, container, false);

        binding.setAtt(new Attachment());

        role = getArguments().getInt(ARG_ROLE,ROLE_USER);
        binding.setRole(role);
        binding.trashBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.setAtt(null);
            }
        });

        binding.attachFileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.dispatchTakePictureIntent(SetPhotoFragment.this, REQUEST_TAKE_IMAGE);
            }
        });

        binding.executePendingBindings();
        invalidateButtonName();

        return binding.getRoot();
    }

    @Override
    public boolean isToolbarEnabled() {
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_TAKE_IMAGE) {
            boolean isSuccess = true;
            for (int permRes :
                    grantResults) {
                isSuccess = isSuccess && permRes == PermissionChecker.PERMISSION_GRANTED;
            }
            if (isSuccess)
                Utils.dispatchTakePictureIntent(this, REQUEST_TAKE_IMAGE);
        }
    }

    Attachment attachment;
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTakePictureEvent(TakePictureFromCameraEvent event) {
        attachment = new Attachment(Uri.fromFile(event.getFile()), event.getFile());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_IMAGE) {
            if (resultCode != RESULT_OK) {
                attachment = null;
                binding.setAtt(null);
            }
            else {
                binding.setAtt(attachment);
            }
        }
        binding.executePendingBindings();
        invalidateButtonName();
    }

    @Override
    public void onResume() {
        super.onResume();
        invalidateButtonName();
    }

    @Override
    public String getToolbarText() {
        switch (role) {
            case ROLE_TEAM: {
                return getString(R.string.set_photo_team_title);
            }
            case ROLE_USER: {
                return getString(R.string.set_photo_user_title);
            }
            default:
                return "";
        }
    }

    public void invalidateButtonName(){
        if (binding.getAtt()!=null && binding.getAtt().getFile()!=null){
            EventBus.getDefault().post(new SetButtonTextEvent(getString(R.string.set)));
        }
        else
            EventBus.getDefault().post(new SetButtonTextEvent(getString(R.string.skip)));
    }

    @Override
    public void tryCompleteStep() {
        onStepResult(true);
    }


}
