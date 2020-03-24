package com.example.androidtask.front

import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FloatingActionButtonManager(
    private val fab: FloatingActionButton
) : RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if (dy < 0 || (dx == 0 && dy == 0)) {
            fab.animate().scaleX(1f).scaleY(1f).setDuration(300).start()
        } else {
            fab.animate().scaleX(0f).scaleY(0f).setDuration(300).start()
        }
    }
}