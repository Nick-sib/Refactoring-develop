package com.nick_sib.refactoringdevelop.view.activitys

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nick_sib.refactoringdevelop.databinding.ActivityDescriptionBinding
import com.nick_sib.refactoringdevelop.model.data.DataModel


//TODO: Refactor to Fragment
class DescriptionActivity: AppCompatActivity() {

    private lateinit var binding: ActivityDescriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val data = intent.extras?.getParcelable(DATA_EXTRA) ?: DataModel(null, null)
        binding.property = data
        binding.executePendingBindings()
    }

    companion object {
        private const val DATA_EXTRA = "f76a288a-5dcc-43f1-ba89-7fe1d53f63b0"
        fun getIntent(
            context: Context,
            data: DataModel,
        ): Intent = Intent(context, DescriptionActivity::class.java)
            .apply { putExtra(DATA_EXTRA, data) }
    }
}