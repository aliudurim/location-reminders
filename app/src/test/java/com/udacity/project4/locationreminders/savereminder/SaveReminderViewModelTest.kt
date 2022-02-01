package com.udacity.project4.locationreminders.savereminder

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.udacity.project4.R
import com.udacity.project4.locationreminders.data.FakeDataSource
import com.udacity.project4.locationreminders.data.dto.ReminderDTO
import com.udacity.project4.locationreminders.reminderslist.ReminderDataItem

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class SaveReminderViewModelTest {

    private lateinit var viewModel: SaveReminderViewModel
    private lateinit var dataSource: FakeDataSource

    private val reminder1 = ReminderDataItem("Reminder1", "Description1", "Location1", 10.0, 10.0)
    private val reminderNullTitle = ReminderDataItem(null, "Description2", "location2", 20.0, 20.0)
    private val reminderEmptyTitle = ReminderDataItem("", "Description2", "location2", 20.0, 20.0)
    private val remindersList = mutableListOf<ReminderDTO>()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUpViewModel() {
        dataSource = FakeDataSource(remindersList)
        viewModel = SaveReminderViewModel(ApplicationProvider.getApplicationContext(), dataSource)
        stopKoin()
    }

    @Test
    fun addNewReminder_savesNewReminder() {
        viewModel.validateAndSaveReminder(reminder1)
        MatcherAssert.assertThat(viewModel.showToast.value, `is`("Reminder Saved !"))
    }

    @Test
    fun addNewReminderNullTitle_producesErrorTitle() {
        viewModel.validateAndSaveReminder(reminderNullTitle)
        MatcherAssert.assertThat(viewModel.showSnackBarInt.value, `is`(R.string.err_enter_title))
    }

    @Test
    fun addNewReminderEmptyTitle_producesErrorTitle() {
        viewModel.validateAndSaveReminder(reminderEmptyTitle)
        MatcherAssert.assertThat(viewModel.showSnackBarInt.value, `is`(R.string.err_enter_title))
    }
}