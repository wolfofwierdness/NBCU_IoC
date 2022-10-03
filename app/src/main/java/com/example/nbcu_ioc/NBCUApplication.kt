package com.example.nbcu_ioc

import android.app.Application
import com.example.nbcu.controller.NBCUController
import com.example.nbcu_ioc.ui.model.NBCUNetwork

class NBCUApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        injector = NBCUController()

        initDependencies()
    }

    private fun initDependencies() {
        injector.addObjectToGraph(NBCUNetwork::class)
    }

    companion object{
        lateinit var injector: NBCUController
    }
}