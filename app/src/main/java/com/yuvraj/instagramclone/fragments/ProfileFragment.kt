package com.yuvraj.instagramclone.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.yuvraj.instagramclone.R
import com.yuvraj.instagramclone.databinding.FragmentProfileBinding
import com.yuvraj.instagramclone.utils.USER_NODE


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {

    }

    override fun onStart() {
        super.onStart()
            Firebase.firestore.collection(USER_NODE).document()
    }
}