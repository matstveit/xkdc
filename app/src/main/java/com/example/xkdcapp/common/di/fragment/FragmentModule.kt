package com.example.xkdcapp.common.di.fragment

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object FragmentModule {

    @Provides
    fun context(fragment: Fragment) = fragment.requireContext()

    @Provides
    fun linearLayoutManager(context: Context) = LinearLayoutManager(context)
}