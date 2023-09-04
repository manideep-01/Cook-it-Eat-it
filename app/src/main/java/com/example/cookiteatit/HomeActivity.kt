package com.example.cookiteatit

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.cookiteatit.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var rvAdapterPopular: PopularAdapter
    private lateinit var rvAdapterCategory: CategoryAdapter
    private lateinit var selectedCategory: String
    private lateinit var datalist: ArrayList<Recipe>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)



        // More option
        binding.more.setOnClickListener {
            var dialog = Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.bottom_sheet)
            dialog.show()
            dialog.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.window!!.setGravity(Gravity.BOTTOM)


            // Find the TextView inside the initial bottom sheet with the ID "about_info"
            val aboutInfoTextView = dialog.findViewById<TextView>(R.id.about_info)
            // Set an OnClickListener for the TextView
            aboutInfoTextView.setOnClickListener {
                // Create the second bottom sheet dialog
                var pri_Dialog = Dialog(this)
                pri_Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                pri_Dialog.setContentView(R.layout.privacy_policy_popup)
                pri_Dialog.show()
                pri_Dialog.window!!.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                pri_Dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                pri_Dialog.window!!.setGravity(Gravity.BOTTOM)
            }

            // Find the TextView inside the initial bottom sheet with the ID "about_dev"
            val aboutDevTextView = dialog.findViewById<TextView>(R.id.about_dev)

            // Set an OnClickListener for the "about_dev" TextView
            aboutDevTextView.setOnClickListener {
                // Create the second bottom sheet dialog for "about_dev"
                var about_Dialog = Dialog(this)
                about_Dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
                about_Dialog.setContentView(R.layout.about_dev_popup) // Replace with the layout for "about_dev" if it's different
                about_Dialog.show()
                about_Dialog.window!!.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                about_Dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                about_Dialog.window!!.setGravity(Gravity.BOTTOM)
            }
        }

        // Search Activity
        binding.search.setOnClickListener {
            startActivity(Intent(this@HomeActivity,SearchActivity::class.java))
        }

        setUpRecyclerViewPopular()
        setUpRecyclerViewCategory()

        // Set up RadioGroup listener
        binding.categoryRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.salad -> filterCategory("Salad")
                R.id.dishes -> filterCategory("Dishes")
                R.id.beverage -> filterCategory("Beverages")
                R.id.desert -> filterCategory("Desserts")
                R.id.soup -> filterCategory("Soup")
                R.id.noodles -> filterCategory("Noodles")
            }
        }
        // By default, select the Salad category
        binding.salad.isChecked = true
    }

    private fun filterCategory(category: String) {
        selectedCategory = category
        val filteredList = datalist.filter { it.category.contains(category) }
        rvAdapterCategory.updateData(filteredList)
    }

    private fun setUpRecyclerViewCategory() {
        datalist = ArrayList()
        binding.rvCategory.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        var db = Room.databaseBuilder(this@HomeActivity,AppDatabase::class.java,"db_name")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .createFromAsset("recipe.db")
            .build()
        var daoObject=db.getDao()
        var recipes = daoObject.getAll()
        datalist.addAll((recipes ?: emptyList()) as Collection<Recipe>)
        rvAdapterCategory = CategoryAdapter(datalist, this)
        binding.rvCategory.adapter = rvAdapterCategory

    }

    private fun setUpRecyclerViewPopular() {
        datalist = ArrayList()
        binding.rvPopular.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        var db = Room.databaseBuilder(this@HomeActivity,AppDatabase::class.java,"db_name")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .createFromAsset("recipe.db")
            .build()
        var daoObject=db.getDao()
        var recipes = daoObject.getAll()
        for(i in recipes!!.indices){
            if(recipes[i]!!.category.contains("Popular")){
                datalist.add(recipes[i]!!)
            }
            rvAdapterPopular=PopularAdapter(datalist,this)
            binding.rvPopular.adapter=rvAdapterPopular
        }
    }
}