package com.mobdeve.jardiniano.see.dao

import com.mobdeve.jardiniano.see.Concerts
import java.util.ArrayList

interface UserDAO {
    fun addItems(concert: Concerts?): Long
    fun getUsers(): ArrayList<Concerts?>?
}