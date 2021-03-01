package com.nick_sib.refactoringdevelop.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nick_sib.refactoringdevelop.view.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [InteractorModule::class])
internal abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    protected abstract fun mainViewModel(mainViewModel: MainViewModel): ViewModel
}