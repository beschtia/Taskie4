package hr.etfos.davorlukic.taskie.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import hr.etfos.davorlukic.taskie.R
import hr.etfos.davorlukic.taskie.common.EXTRA_SCREEN_TYPE
import hr.etfos.davorlukic.taskie.common.EXTRA_TASK_ID
import hr.etfos.davorlukic.taskie.common.gone
import hr.etfos.davorlukic.taskie.common.visible
import hr.etfos.davorlukic.taskie.model.Task
import hr.etfos.davorlukic.taskie.persistence.Repository
import hr.etfos.davorlukic.taskie.ui.activities.ContainerActivity
import hr.etfos.davorlukic.taskie.ui.adapters.TaskAdapter
import hr.etfos.davorlukic.taskie.ui.fragments.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_tasks.*

class TasksFragment : BaseFragment(), AddTaskFragmentDialog.TaskAddedListener {

    private val repository = Repository
    private val adapter by lazy { TaskAdapter {onItemSelected(it)} }

    override fun getLayoutResourceId() = R.layout.fragment_tasks

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initListeners()
        refreshTasks()
    }

    private fun initUi() {
        progress.visible()
        noData.visible()
        tasksRecyclerView.layoutManager = LinearLayoutManager(context)
        tasksRecyclerView.adapter = adapter
    }

    private fun initListeners() {
        addTask.setOnClickListener { addTask() }
    }

    private fun onItemSelected(task: Task){
        val detailsIntent = Intent(context, ContainerActivity::class.java).apply {
            putExtra(EXTRA_SCREEN_TYPE, ContainerActivity.SCREEN_TASK_DETAILS)
            putExtra(EXTRA_TASK_ID, task.id)
        }
        startActivity(detailsIntent)
    }

    private fun refreshTasks() {
        progress.gone()
        val data = repository.getAllTasks()
        if (data.isNotEmpty()) {
            noData.gone()
        } else {
            noData.visible()
        }
        adapter.setData(data)
    }

    private fun addTask() {
        val dialog = AddTaskFragmentDialog.newInstance()
        dialog.setTaskAddedListener(this)
        dialog.show(childFragmentManager, dialog.tag)
    }

    override fun onTaskAdded(task: Task) {
        refreshTasks()
    }

    companion object {
        fun newInstance(): Fragment {
            return TasksFragment()
        }
    }
}