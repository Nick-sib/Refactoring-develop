package com.nick_sib.refactoringdevelop.view


import android.os.Bundle
import com.nick_sib.refactoringdevelop.R
import com.nick_sib.refactoringdevelop.model.data.AppState
import com.nick_sib.refactoringdevelop.view.base.BaseActivity

class MainActivity : BaseActivity<AppState>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}