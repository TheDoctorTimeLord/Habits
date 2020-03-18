package com.example.androidtask.front.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.androidtask.R

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentGoodHabits.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentGoodHabits : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_good_habits, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            FragmentGoodHabits().apply {
                arguments = Bundle().apply {

                }
            }
    }
}
