package com.example.cookiteatit

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.cookiteatit.databinding.ActivityRecipeBinding
import com.example.cookiteatit.databinding.ActivitySearchBinding

class RecipeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecipeBinding
    var imgCrop = true
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        Glide.with(this).load(intent.getStringExtra("img")).into(binding.itemImg)
        binding.tittleDes.text = intent.getStringExtra("tittle")
        binding.stepsData.text = intent.getStringExtra("des")
        var ing = intent.getStringExtra("ing")?.split("\n".toRegex())?.filter { it.isNotBlank() }
        val des = intent.getStringExtra("des")?.split("\n".toRegex())?.filter { it.isNotBlank() }
        binding.stepsData.text = des?.joinToString("\n") { "🔴 $it" } ?: ""
        if (!ing.isNullOrEmpty()) {
            binding.timeText.text = ing[0]

            val ingDataText = ing.drop(1).joinToString("\n") { "🔴 $it" }
            binding.ingData.text = ingDataText
        } else {
            binding.timeText.text = ""
            binding.ingData.text = ""
        }
        binding.timeText.text=ing?.get(0)

        binding.steps.background = null
        binding.ingredient.setTextColor(getColor(R.color.white))
        binding.steps.setTextColor(getColor(R.color.black))
        binding.ingredient.setOnClickListener {
            binding.ingredient.setBackgroundResource(R.drawable.btn_ing)
            binding.ingredient.setTextColor(getColor(R.color.white))
            binding.steps.setTextColor(getColor(R.color.black))
            binding.steps.background = null
            binding.ingScroll.visibility = View.VISIBLE
            binding.stepsScroll.visibility = View.GONE
        }

        binding.steps.setOnClickListener {
            binding.steps.setBackgroundResource(R.drawable.btn_ing)
            binding.steps.setTextColor(getColor(R.color.white))
            binding.ingredient.setTextColor(getColor(R.color.black))
            binding.ingredient.background = null
            binding.ingScroll.visibility = View.GONE
            binding.stepsScroll.visibility = View.VISIBLE
        }


        binding.fullScreen.setOnClickListener {
            if(imgCrop){
                binding.itemImg.scaleType=ImageView.ScaleType.FIT_CENTER
                binding.fullScreen.setColorFilter(Color.BLACK,PorterDuff.Mode.SRC_ATOP)
                binding.shade.visibility = View.GONE
                imgCrop=!imgCrop
            } else {
                binding.itemImg.scaleType=ImageView.ScaleType.CENTER_CROP
                binding.fullScreen.colorFilter = null
                binding.shade.visibility = View.VISIBLE
                imgCrop=!imgCrop
            }
        }
        binding.recipeBackBtn.setOnClickListener {
            finish()
        }
    }
}