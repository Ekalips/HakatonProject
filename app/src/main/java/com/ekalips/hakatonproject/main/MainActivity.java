package com.ekalips.hakatonproject.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ekalips.hakatonproject.R;
import com.ekalips.hakatonproject.data.Project;
import com.ekalips.hakatonproject.data.ProjectListHeader;
import com.ekalips.hakatonproject.databinding.ActivityMainBinding;
import com.ekalips.hakatonproject.user.User;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmChangeListener;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    ActivityMainBinding binding;
    Realm realm;
    Project projects;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        binding.setTitle(User.getInstance().getTeamName());

        realm = Realm.getDefaultInstance();

        projects = realm.where(Project.class).findFirstAsync();
        projects.addChangeListener(projectsChangeListener);
    }



    RealmChangeListener<Project> projectsChangeListener = new RealmChangeListener<Project>() {
        @Override
        public void onChange(Project element) {
            ArrayList objects = new ArrayList<>();
            objects.add(new ProjectListHeader(element.getProjectImage(),element.getProjectName(),element.getProjDescr()));
            objects.add(element.getTasks());
            binding.setData(objects);
        }
    };

}
