package com.d.candy.f.awesometimetable;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.d.candy.f.awesometimetable.structure.EntityType;
import com.d.candy.f.awesometimetable.ui.EditAssignmentFragment;
import com.d.candy.f.awesometimetable.utils.LogHelper;

public class EntityEditorActivity extends AppCompatActivity
        implements EditAssignmentFragment.OnInteractionListener {

    public static final String EXTRA_EDIT_ENTITY_TYPE = "extra";
    private EntityType mEditEntityType = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entity_editor);
        init();
        launchFragment();
    }

    private void init() {
        mEditEntityType = (EntityType) getIntent().getSerializableExtra(EXTRA_EDIT_ENTITY_TYPE);
    }

    private void launchFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (mEditEntityType) {
            case ASSIGNMENT: {
                transaction.replace(R.id.fragment_container, new EditAssignmentFragment()).commit();
                break;
            }

            default:
                throw new IllegalArgumentException("An illegal entity type");
        }
    }

    /**
     * The implementation of EditAssignmentFragment.OnInteractionListener interface
     */
}
