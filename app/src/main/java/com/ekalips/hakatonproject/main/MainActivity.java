package com.ekalips.hakatonproject.main;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;

import com.ekalips.hakatonproject.R;
import com.ekalips.hakatonproject.adapters.ProjectRecyclerViewAdapter;
import com.ekalips.hakatonproject.data.Member;
import com.ekalips.hakatonproject.data.Project;
import com.ekalips.hakatonproject.data.ProjectListHeader;
import com.ekalips.hakatonproject.data.Task;
import com.ekalips.hakatonproject.databinding.ActivityMainBinding;
import com.ekalips.hakatonproject.events.AssignUserToTaskEvent;
import com.ekalips.hakatonproject.events.CreateProjectEvent;
import com.ekalips.hakatonproject.events.OpenProjectEvent;
import com.ekalips.hakatonproject.networking.ApiInterface;
import com.ekalips.hakatonproject.networking.CallbackOverload;
import com.ekalips.hakatonproject.stuff.Utils;
import com.ekalips.hakatonproject.user.User;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmList;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    public static final String EXTRA_PROJECT_ID = "proj_id";

    ApiInterface apiInterface = ApiInterface.retrofit.create(ApiInterface.class);

    ActivityMainBinding binding;
    Realm realm;
    Project project;
    RealmResults<Project> projectRealmResults;
    RealmResults<Member> memberRealmResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, binding.drawerLay, binding.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLay.addDrawerListener(toggle);
        toggle.syncState();

        binding.setTitle(User.getInstance().getTeamName());

        binding.setUser(User.getInstance());

        binding.recyclerView.setAdapter(new ProjectRecyclerViewAdapter());
        realm = Realm.getDefaultInstance();
        String projId = getIntent().getStringExtra(EXTRA_PROJECT_ID);
        projId = "TEST";

        if (projId == null || projId.isEmpty())
            project = realm.where(Project.class).findFirstAsync();
        else{
            project = realm.where(Project.class).equalTo("projectId",projId).findFirstAsync();
        }
        project.addChangeListener(projectChangeListener);

        projectRealmResults = realm.where(Project.class).findAllAsync();
        projectRealmResults.addChangeListener(projectsChangeListener);
        memberRealmResults = realm.where(Member.class).findAllAsync();
        memberRealmResults.addChangeListener(membersChangeListener);

        if (projectRealmResults.isLoaded())
            binding.setProjects(new ArrayList(projectRealmResults));

        if (memberRealmResults.isLoaded())
            binding.setMembers(new ArrayList(memberRealmResults));

        if (project.isLoaded())
            setData(project);

        if (projId.equals("TEST")) {
            Project test = new Project();
            test.setProjectName("Test project");
            test.setProjDescr("Test project description\nLorem ipsum");
            test.setProjectImage("http://i.imgur.com/xeoORPM.png");
            RealmList<Task> tasks = new RealmList<>();

            RealmList<Member> members = new RealmList<>();
            members.add(new Member("Project member1", "https://cs7058.userapi.com/c639320/v639320829/17f41/97zX7C_l9fs.jpg", "0"));
            members.add(new Member("Project member2", "https://cs7058.userapi.com/c639320/v639320829/17f41/97zX7C_l9fs.jpg", "0"));
            members.add(new Member("Project member3", "https://cs7058.userapi.com/c639320/v639320829/17f41/97zX7C_l9fs.jpg", "0"));
            members.add(new Member("Project member4", "https://cs7058.userapi.com/c639320/v639320829/17f41/97zX7C_l9fs.jpg", "0"));


            tasks.add(new Task(false, "Test task 1", "Test descr 1", "1", new Date().getTime(), members));
            tasks.add(new Task(true, "Test task 2", "Test descr 2", "2", new Date().getTime(), members));
            tasks.add(new Task(true, "Test task 3", "Test descr 3", "3", new Date().getTime(), members));
            tasks.add(new Task(false, "Test task 4", "Test descr 4", "4", new Date().getTime(), members));
            test.setTasks(tasks);

            setData(test);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchMembers();
        fetchProjects();

        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    private void fetchMembers() {
        apiInterface.getTeamMembers(User.getInstance().getBearerToken(), User.getInstance().getRoomToken()).enqueue(new CallbackOverload<List<Member>>() {
            @Override
            public void onSuccess(Call<List<Member>> call, final Response<List<Member>> response) {
                super.onSuccess(call, response);
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.insertOrUpdate(response.body());
                    }
                });
            }

            @Override
            public void onError(Call<List<Member>> call, @Nullable Response<List<Member>> response) {
                super.onError(call, response);
                Utils.showToastMessage(R.string.error_fetching_members);
            }

            @Override
            public void onFailure(Call<List<Member>> call, Throwable t) {
                super.onFailure(call, t);
                Utils.showToastMessage(R.string.no_connection);
            }
        });
    }

    private void fetchProjects() {
        apiInterface.getProjects(User.getInstance().getBearerToken()).enqueue(new CallbackOverload<List<Project>>() {
            @Override
            public void onSuccess(Call<List<Project>> call, final Response<List<Project>> response) {
                super.onSuccess(call, response);
                realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.insertOrUpdate(response.body());
                    }
                });
            }

            @Override
            public void onError(Call<List<Project>> call, @Nullable Response<List<Project>> response) {
                super.onError(call, response);
                Utils.showToastMessage(R.string.error_fetching_projects);
            }

            @Override
            public void onFailure(Call<List<Project>> call, Throwable t) {
                super.onFailure(call, t);
                Utils.showToastMessage(R.string.no_connection);
            }
        });
    }

    RealmChangeListener<Project> projectChangeListener = new RealmChangeListener<Project>() {
        @Override
        public void onChange(Project element) {
            setData(element);
        }
    };

    RealmChangeListener<RealmResults<Project>> projectsChangeListener = new RealmChangeListener<RealmResults<Project>>() {
        @Override
        public void onChange(RealmResults<Project> element) {
            binding.setProjects(new ArrayList(realm.where(Project.class).findAll()));
        }
    };

    RealmChangeListener<RealmResults<Member>> membersChangeListener = new RealmChangeListener<RealmResults<Member>>() {
        @Override
        public void onChange(RealmResults<Member> element) {
            binding.setMembers(new ArrayList(realm.where(Member.class).findAll()));
        }
    };

    private void setData(Project projects) {
        ArrayList objects = new ArrayList<>();
        objects.add(new ProjectListHeader(projects.getProjectImage(), projects.getProjectName(), projects.getProjDescr()));
        objects.addAll(projects.getTasks());
        binding.setTitle(projects.getProjectName());
        binding.setData(objects);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNewProject(CreateProjectEvent event) {
        Intent intent = new Intent(this, NewProjectActivity.class);
        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onOpenProjEvent(OpenProjectEvent event){
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra(EXTRA_PROJECT_ID,event.getId());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAssignUserToTask(AssignUserToTaskEvent event){
        //// TODO: 4/4/17 assign user to task
    }

}
