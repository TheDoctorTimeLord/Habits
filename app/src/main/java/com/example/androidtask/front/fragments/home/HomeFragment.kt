package com.example.androidtask.front.fragments.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.androidtask.R
import com.example.androidtask.front.fragments.editHabit.EditHabitFragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.bottom_sheet.*
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()

        floatingActionButton.setOnClickListener {
            val bundle = Bundle()
                .apply {
                    putString(EditHabitFragment.ACTION_TYPE, EditHabitFragment.ActionTypes.ADD.name)
                }
            navController.navigate(R.id.action_nav_home_to_editHabitFragment, bundle)
        }

        val bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet)
        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) { }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (BottomSheetBehavior.STATE_DRAGGING == newState) {
                    floatingActionButton.animate().scaleX(0f).scaleY(0f).setDuration(300).start()
                    val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                } else if (BottomSheetBehavior.STATE_COLLAPSED == newState) {
                    floatingActionButton.animate().scaleX(1f).scaleY(1f).setDuration(300).start()
                }
            }
        })

        habitListsViewPager.adapter = HabitListsAdapter(childFragmentManager)
        habitListsTabLayout.setupWithViewPager(habitListsViewPager)
    }
}
