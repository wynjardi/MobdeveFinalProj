package com.mobdeve.jardiniano.see

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.google.android.datatransport.runtime.firebase.transport.LogEventDropped
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mobdeve.jardiniano.see.databinding.FragmentConcertUserBinding
import java.lang.Exception

class ConcertUserFragment : Fragment {

    private lateinit var binding: FragmentConcertUserBinding

    public companion object{
        private const val TAG = "CONCERT_USER_TAG"

        //receive data from activity to load concert details like category
        public fun newInstance(categoryId: String, category: String, uid: String): ConcertUserFragment{
            val fragment = ConcertUserFragment()
            //put data to bundle intent
            val args = Bundle()
            args.putString("categoryId", categoryId)
            args.putString("category", category)
            args.putString("uid", uid)
            fragment.arguments = args
            return fragment
        }
    }

    private var categoryId = ""
    private var category = ""
    private var uid = ""

    private lateinit var concertArrayList: ArrayList<ModelConcert>
    private lateinit var adapterConcertUser: AdapterConcertUser

    constructor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //get arguments that were passed to instance
        val args = arguments
        if (args != null){
            categoryId = args.getString("categoryId")!!
            category = args.getString("category")!!
            uid = args.getString("uid")!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentConcertUserBinding.inflate(LayoutInflater.from(context), container, false)

        //load concert according to category
        Log.d(TAG, "onCreateView: Category: $category")
        if (category == "All"){
            //load all concerts
            loadAllConcerts()
        }

        else{
            //load selected category concerts
            loadCategorizedConcerts()
        }

        //search
//        binding.searchEt.addTextChangedListener { object : TextWatcher{
//
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//
//            }
//
//            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
//               try {
//                    adapterConcertUser.filter.filter(s)
//               }
//
//               catch (e: Exception){
//                   Log.d(TAG, "onTextChanged: Search Exception: ${e.message}")
//
//               }               }
//
//            override fun afterTextChanged(p0: Editable?) {
//
//            }
//        } }

        return binding.root
    }

    private fun loadAllConcerts() {
        //init list
        concertArrayList = ArrayList()
        val ref = FirebaseDatabase.getInstance().getReference("Concerts")
        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //clear list before start adding data
                concertArrayList.clear()
                for (ds in snapshot.children){
                    //get data, to change model concert
                    val model = ds.getValue(ModelConcert::class.java)
                    //add to list
                    concertArrayList.add(model!!)
                }
                //setup adapter
//                adapterConcertUser = AdapterConcertUser(context!!, concertArrayList)
                //set adapter to recycler view
                binding.concertsRv.adapter = adapterConcertUser
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    private fun loadCategorizedConcerts(){
        //init list
        concertArrayList = ArrayList()
        val ref = FirebaseDatabase.getInstance().getReference("Concerts")
        ref.orderByChild("categoryId").equalTo(categoryId)
            .addValueEventListener(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    //clear list before adding data
                    concertArrayList.clear()
                    for (ds in snapshot.children){
                        //get data
                        val model = ds.getValue(ModelConcert::class.java)
                        //add to list
                        concertArrayList.add(model!!)
                    }

                    //setup adapter
//                    adapterConcertUser = AdapterConcertUser(context!!, concertArrayList)
                    //set adapter to recycler view
                    binding.concertsRv.adapter = adapterConcertUser
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })

    }
}