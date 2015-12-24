package com.v4.facebook.tutorial.facebookv4helloworld;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookRequestError;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostFragment extends Fragment implements View.OnClickListener {

    private Button btnShare;
    private CallbackManager mCallbackManager;
    private ShareDialog shareDialog;
    private FacebookCallback<Sharer.Result> mCallback = new FacebookCallback<Sharer.Result>() {
        @Override
        public void onSuccess(Sharer.Result result) {
            Toast.makeText(getActivity().getApplicationContext(), "Successful", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel() {

        }

        @Override
        public void onError(FacebookException error) {

        }
    };

    public PostFragment() {
    }


    // TODO: Rename and change types and number of parameters
    public static PostFragment newInstance() {
        PostFragment fragment = new PostFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCallbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        shareDialog.registerCallback(mCallbackManager, mCallback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnShare = (Button) view.findViewById(R.id.facebook_share);
        btnShare.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
//        if (ShareDialog.canShow(ShareLinkContent.class)) {
//            ShareLinkContent linkContent = new ShareLinkContent.Builder()
//                    .setContentTitle("Hello Facebook")
//                    .setContentDescription("The 'Hello Facebook' sample  showcases simple Facebook integration")
//
//                    .setContentUrl(Uri.parse("http://developers.facebook.com/android"))
//                    .build();
//
//            shareDialog.show(linkContent);
//        }
        postToWall("This is a test message");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void postToWall(String message){
        //so to check whether we have publish permission we do the following statement or check sharedpreferences
        if(AccessToken.getCurrentAccessToken().getDeclinedPermissions().contains(Constants.FB_PUBLISH_PERMISSION)){
            Toast.makeText(getActivity().getApplicationContext(), "You are not allowed to post, cuz you have refused to post to wall", Toast.LENGTH_LONG).show();
            return;
        }

        Bundle params = new Bundle();
        params.putString("message", message);

        new GraphRequest(AccessToken.getCurrentAccessToken(), "me/feed", params, HttpMethod.POST, new GraphRequest.Callback(){
            @Override
            public void onCompleted(GraphResponse response) {
                FacebookRequestError error = response.getError();
                String msg;
                if(error == null) {
                    msg = "Posted to wall";
                } else {
                    msg = error.getErrorMessage();
                }
                Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
        }).executeAsync();
    }
}
