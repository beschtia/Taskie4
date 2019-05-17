package hr.etfos.davorlukic.taskie.ui.activities

import com.google.android.material.bottomnavigation.BottomNavigationView
import hr.etfos.davorlukic.taskie.R
import hr.etfos.davorlukic.taskie.ui.activities.base.BaseActivity
import hr.etfos.davorlukic.taskie.ui.fragments.AboutFragment
import hr.etfos.davorlukic.taskie.ui.fragments.TasksFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home ->{
                showFragment(TasksFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_about ->{
                showFragment(AboutFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun getLayoutResourceId() = R.layout.activity_main

    override fun setUpUi() {
        showFragment(TasksFragment.newInstance())
        nav_view.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }
}


