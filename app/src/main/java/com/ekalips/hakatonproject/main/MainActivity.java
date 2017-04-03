package com.ekalips.hakatonproject.main;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.ekalips.hakatonproject.R;
import com.ekalips.hakatonproject.adapters.ProjectRecyclerViewAdapter;
import com.ekalips.hakatonproject.data.Member;
import com.ekalips.hakatonproject.data.Project;
import com.ekalips.hakatonproject.data.ProjectListHeader;
import com.ekalips.hakatonproject.data.Task;
import com.ekalips.hakatonproject.databinding.ActivityMainBinding;
import com.ekalips.hakatonproject.user.User;

import java.util.ArrayList;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;

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

        binding.recyclerView.setAdapter(new ProjectRecyclerViewAdapter());
        realm = Realm.getDefaultInstance();

        projects = realm.where(Project.class).findFirstAsync();
        projects.addChangeListener(projectsChangeListener);


        Project test = new Project();
        test.setProjectName("Test project");
        test.setProjDescr("Test project description\nLorem ipsum");
        test.setProjectImage("http://i.imgur.com/xeoORPM.png");
        RealmList<Task> tasks = new RealmList<>();

        RealmList<Member> members = new RealmList<>();
        members.add(new Member("Project member1","https://cs7058.userapi.com/c639320/v639320829/17f41/97zX7C_l9fs.jpg","0"));
        members.add(new Member("Project member2","https://cs7058.userapi.com/c639320/v639320829/17f41/97zX7C_l9fs.jpg","0"));
        members.add(new Member("Project member3","https://cs7058.userapi.com/c639320/v639320829/17f41/97zX7C_l9fs.jpg","0"));
        members.add(new Member("Project member4","https://cs7058.userapi.com/c639320/v639320829/17f41/97zX7C_l9fs.jpg","0"));


        tasks.add(new Task(false,"Test task 1","Test descr 1", "1", new Date().getTime(), members));
        tasks.add(new Task(true,"Test task 2","Test descr 2", "2", new Date().getTime(), members));
        tasks.add(new Task(true,"Test task 3","Test descr 3", "3", new Date().getTime(), members));
        tasks.add(new Task(false,"Test task 4","Test descr 4", "4", new Date().getTime(), members));
        test.setTasks(tasks);

        setData(test);

    }



    RealmChangeListener<Project> projectsChangeListener = new RealmChangeListener<Project>() {
        @Override
        public void onChange(Project element) {
            Log.d(TAG, "onChange: ");

            setData(element);
        }
    };

    private void setData(Project projects){
        ArrayList objects = new ArrayList<>();
        objects.add(new ProjectListHeader(projects.getProjectImage(),projects.getProjectName(),projects.getProjDescr()));
        objects.addAll(projects.getTasks());
        binding.setData(objects);
    }

}
