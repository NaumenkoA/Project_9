package com.teamtreehouse.ribbit.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.teamtreehouse.ribbit.R;
import com.teamtreehouse.ribbit.adapters.MessageAdapter;
import com.teamtreehouse.ribbit.models.Message;
import com.teamtreehouse.ribbit.models.MessageFile;
import com.teamtreehouse.ribbit.models.Query;
import com.teamtreehouse.ribbit.models.User;
import com.teamtreehouse.ribbit.models.callbacks.FindCallback;

import java.util.List;

public class InboxFragment extends ListFragment {

    public static final String TEXT_MESSAGE = "text_of_message";
    protected List<Message> mMessages;
    protected SwipeRefreshLayout mSwipeRefreshLayout;
    private ImageButton mFab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_inbox,
                container, false);
        mFab = (ImageButton)rootView.findViewById(R.id.fab);
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        // Deprecated method - what should we call instead?
        mSwipeRefreshLayout.setColorSchemeResources(
                R.color.swipeRefresh1,
                R.color.swipeRefresh2,
                R.color.swipeRefresh3,
                R.color.swipeRefresh4);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        retrieveMessages();
        getActivity().setProgressBarIndeterminateVisibility(true);
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onFabClickedListener listener = (onFabClickedListener) getActivity();
                listener.onFabClicked();
            }
        });
    }

    private void retrieveMessages() {
        Query<Message> query = Message.getQuery();
        query.whereEqualTo(Message.KEY_RECIPIENT_IDS, User.getCurrentUser().getObjectId());
        query.addDescendingOrder(Message.KEY_CREATED_AT);
        query.findInBackground(new FindCallback<Message>() {
            @Override
            public void done(List<Message> messages, Exception e) {
                getActivity().setProgressBarIndeterminateVisibility(false);

                if (mSwipeRefreshLayout.isRefreshing()) {
                    mSwipeRefreshLayout.setRefreshing(false);
                }

                if (e == null) {
                    // We found messages!
                    mMessages = messages;

                    String[] usernames = new String[mMessages.size()];
                    int i = 0;
                    for (Message message : mMessages) {
                        usernames[i] = message.getString(Message.KEY_SENDER_NAME);
                        i++;
                    }
                        if (getListView().getAdapter() == null) {
                            MessageAdapter adapter = new MessageAdapter(
                                    getListView().getContext(),
                                    mMessages);
                            setListAdapter(adapter);
                        } else {
                            // refill the adapter!
                            refillAdapter();
                        }
                    }
                 }
            });
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Message message = mMessages.get(position);
        String messageType = message.getString(Message.KEY_FILE_TYPE);
            if (messageType.equals(Message.TYPE_TEXT)) {
            // view text message
                Intent intent = new Intent(getActivity(), ViewTextMessageActivity.class);
                intent.putExtra(TEXT_MESSAGE, message.getTextMessage());
                startActivity(intent);
             } else {
            MessageFile file = message.getFile(Message.KEY_FILE);
            Uri fileUri = file.getUri();

            if (messageType.equals(Message.TYPE_IMAGE)) {
                // view the image
                Intent intent = new Intent(getActivity(), ViewImageActivity.class);
                intent.setData(fileUri);
                startActivity(intent);
            } else {
                // view the video
                Intent intent = new Intent(Intent.ACTION_VIEW, fileUri);
                intent.setDataAndType(fileUri, "video/*");
                startActivity(intent);
            }
        }

            // Delete it!
        List<String> ids = message.getList(Message.KEY_RECIPIENT_IDS);

        if (ids.size() == 1) {
            // last recipient - delete the whole thing!
            message.deleteInBackground();
        }
        else {
            // remove the recipient
            message.removeRecipient(User.getCurrentUser().getObjectId());
        }
        }

    private void refillAdapter() {
        ((MessageAdapter) getListView().getAdapter()).refill(mMessages);
    }


    protected OnRefreshListener mOnRefreshListener = new OnRefreshListener() {
        @Override
        public void onRefresh() {
            retrieveMessages();
        }
    };

    public interface onFabClickedListener {
        void onFabClicked ();
    }
}








