package com.example.androidtask.front

import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FloatingActionButtonManager(
    private val fab: FloatingActionButton
) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy < 0 || (dx == 0 && dy == 0)) {
            fab.show()
        } else {
            fab.hide()
        }
    }
}