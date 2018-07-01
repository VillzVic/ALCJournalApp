package com.vic.villz.journalapp.UnitTest;


import android.support.test.filters.MediumTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.vic.villz.journalapp.Activities.HomePageActivity;
import com.vic.villz.journalapp.R;
import com.vic.villz.journalapp.model.JournalEntry;

import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

@MediumTest
@RunWith(AndroidJUnit4.class)
public class HomePageActivityUnitTest {

    @Rule
    public ActivityTestRule<HomePageActivity> mActivityRule =
            new ActivityTestRule<>(HomePageActivity.class);

    @Test
    public void ensureRecyclerViewIsPresent() throws Exception{
        HomePageActivity activity = mActivityRule.getActivity();
        View view = activity.findViewById(R.id.recyclerView);
        assertThat(view, notNullValue());
        assertThat(view, instanceOf(RecyclerView.class));

        RecyclerView recyclerView = (RecyclerView) view;
        FirebaseRecyclerAdapter adapter = (FirebaseRecyclerAdapter) recyclerView.getAdapter();
        assertThat(adapter, CoreMatchers.<FirebaseRecyclerAdapter>instanceOf(FirebaseRecyclerAdapter.class));
    }

    @Test
    public void testMoveToEntryMethod(){
        JournalEntry entry = new JournalEntry("","","");
        assert mActivityRule.getActivity().moveToDetail(entry);
    }

    @Test
    public void testeditJournalEntryMethod(){
        JournalEntry entry = new JournalEntry("","","");
        String id = "LGBDJGHJNSJ";
        assert mActivityRule.getActivity().editJournalEntry(id, entry);
    }

    @Test
    public void testdeleteJournalEntryMethod(){
        String id = "LGBDJGHJNSJ";
        assert mActivityRule.getActivity().deleteJournalEntry(id);
    }



}
