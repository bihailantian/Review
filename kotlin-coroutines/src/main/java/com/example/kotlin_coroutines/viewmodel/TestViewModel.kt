package com.example.kotlin_coroutines.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TestViewModel : ViewModel() {

    /**
     * LifecycleScope 和 ViewModelScope
     * 在 LifecycleOwner 的子类（AppCompatActivity 和 Fragment 都是它的子类）中使用，
     * 这样写出来的协程会在 Lifecycle 派发 destroy 事件的时候 cancel 掉
     */
    fun test() {

        viewModelScope.launch() {

        }
    }
}