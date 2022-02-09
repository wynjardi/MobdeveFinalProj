package com.mobdeve.jardiniano.see

import java.util.ArrayList

class User(val email: String, val dname: String, val password: String) {
    val userId: String? = null
    val threads: ArrayList<Thread> = ArrayList<Thread>()

    fun addThread(thread: java.lang.Thread) {
        threads.add(thread)
    }
}