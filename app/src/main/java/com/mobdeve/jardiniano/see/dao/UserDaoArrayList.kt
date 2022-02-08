package com.mobdeve.jardiniano.see.dao

import com.mobdeve.jardiniano.see.Concerts
import com.mobdeve.jardiniano.see.R
import java.util.ArrayList

class UserDaoArrayList : UserDAO{
    var concertlst = ArrayList<Concerts>()

    override fun addItems(concert: Concerts?): Long {
            concertlst.add(
                Concerts(
                    R.drawable.harry_styles_picture, "Love on Tour", "Harry Styles",
                    "November 20, 2021 8PM TO 11PM",
                    "Mall of Asia Arena, Pasig City",
                    "Total prices are as follows: P20,000 P10,000 P5,000"
                )
            )
            concertlst.add(
                Concerts(
                    R.drawable.billie_eilish_picture,
                    "When We All Fall Asleep Tour",
                    "Billie Eilish",
                    "July 06, 2021 8PM TO 11PM",
                    "Mall of Asia Arena, Pasig City",
                    "Total prices are as follows: P30,000 P15,000 P5,000"
                )
            )

            concertlst.add(
                Concerts(
                    R.drawable.taylor_swift_picture, "Fearless Tour", "Taylor Swift",
                    "March 16, 2020 8PM TO 11PM",
                    "Mall of Asia Arena, Pasig City",
                    "Total prices are as follows: P30,000 P15,000 P5,000"
                )
            )
        return 1L
    }

    override fun getUsers(): ArrayList<Concerts?>? {
        TODO("Not yet implemented")
    }

}