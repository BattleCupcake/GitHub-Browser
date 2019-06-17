package com.githubbrowser.widget

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View
import com.githubbrowser.extensions.hide
import com.githubbrowser.extensions.show

class EventsRecyclerView(context: Context, attrs: AttributeSet) : RecyclerView(context, attrs) {

    var emptyView: View? = null
    var progressView: View? = null

    fun showProgress() {
        progressView?.show()
        emptyView?.hide()
        this.hide()
    }

    fun hideProgress() {
        progressView?.hide()
    }

    fun updateView() {
        if (adapter!!.itemCount > 0) {
            showContent()
        } else {
            showEmptyView()
        }
    }

    private fun showEmptyView() {
        emptyView?.show()
        this.hide()

    }

    private fun showContent() {
        emptyView?.hide()
        this.show()
    }
}