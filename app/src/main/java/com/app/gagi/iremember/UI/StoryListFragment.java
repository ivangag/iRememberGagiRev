package com.app.gagi.iremember.UI;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.app.gagi.iremember.Common.StoryCreateItem;
import com.app.gagi.iremember.R;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StoryListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StoryListFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class StoryListFragment extends ListFragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG = "Tag";

    private OnFragmentInteractionListener mListener;
    private String mTag;
    private int mFakeStoryCount;

    private StoryListOverviewAdapter mAdapter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param Tag Tag.
     * @return A new instance of fragment StoryListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StoryListFragment newInstance(String Tag) {
        StoryListFragment fragment = new StoryListFragment();
        Bundle args = new Bundle();
        args.putString(TAG, Tag);
        fragment.setArguments(args);
        return fragment;
    }
    public StoryListFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTag = getArguments().getString(TAG);
        }

        mAdapter = new StoryListOverviewAdapter();

        setListAdapter(mAdapter);
        setRetainInstance(true);

    }
    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.story_fragment_menu,menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_story:
                //mAdapter.addElement(new StoryLisOverviewItem("Story #" + mFakeStoryCount++));
                StartStoryActivityCreation();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setHasOptionsMenu(true);
        registerForContextMenu(this.getListView());

        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAdapter.notifyDataSetChanged();

        return super.onCreateView(inflater,container,savedInstanceState);

    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

       Object object = getListView().getItemAtPosition(position);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if((StoryListActivity.RESULT_OK == resultCode)
                && (StoryListActivity.REQUEST_CODE_CREATE_STORY == requestCode)
                )
        {
            //Bundle bundle = data.getBundleExtra("EXTRA_TEST");
            StoryCreateItem storyResult =  data.getExtras().getParcelable("EXTRA_OBJ");
            //Toast.makeText(getActivity().getApplicationContext(),"New Story Created: " + data.getStringExtra("EXTRA_TEST"),Toast.LENGTH_SHORT).show();
            Toast.makeText(getActivity().getApplicationContext(),"New Story Created: " +
                    storyResult.getItemName(),Toast.LENGTH_SHORT).show();

            mAdapter.addElement(storyResult);
        }
    }

    void StartStoryActivityCreation()
    {
        Intent intent = new Intent(getActivity().getApplicationContext(),StoryCreateActivity.class);

        startActivityForResult(intent,StoryListActivity.REQUEST_CODE_CREATE_STORY);
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }


}
