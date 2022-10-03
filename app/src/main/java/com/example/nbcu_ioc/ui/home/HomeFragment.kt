package com.example.nbcu_ioc.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.nbcu_ioc.NBCUApplication
import com.example.nbcu_ioc.R
import com.example.nbcu_ioc.ui.model.NBCUNetwork


class HomeFragment : Fragment() {

    private lateinit var network: NBCUNetwork

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(
            R.layout.fragment_home,
            container,
            false
        )
        network = NBCUApplication.injector.getDependency(NBCUNetwork::class)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        network.provideRemoteData().let {
            view.findViewById<TextView>(R.id.data_display).text = it.toString()
        }
    }
}