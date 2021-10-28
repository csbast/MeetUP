package com.example.meetup.presentation

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meetup.R
import com.example.meetup.adapter.DatabaseAdapter
import com.example.meetup.databinding.FragmentUserslistBinding
import com.example.meetup.model.FirestoreUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class UsersListFragment : Fragment() {

    private lateinit var binding: FragmentUserslistBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_userslist, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFirebaseFirestore()
    }

    private fun setupFirebaseFirestore() {
        showLoading()
        val db = Firebase.firestore
        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                result.let {
                    val users = ArrayList<FirestoreUser>()
                    for (document in result) {
                        val user = document.toObject<FirestoreUser>()
                        user.let {
                            user.apply {
                                id = document.id
                            }
                            users.add(user)
                        }
                    }
                    showDatabaseData(users)
                    hideLoading()
                }
            }
            .addOnFailureListener { exception ->
                connectionFailureDialog()
                Log.w("###", "Error getting documents.", exception)
            }
    }

    private fun showLoading() {
        binding.rvProgressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.rvProgressBar.visibility = View.GONE
    }

//    private fun randomUrl(): String {
//        val listOfUrl = listOf(
//            "https://picsum.photos/130?random=$1.jpg",
//            "https://picsum.photos/200?random=2.jpg",
//            "https://picsum.photos/200?random=3.jpg",
//            "https://picsum.photos/200?random=4.jpg",
//            "https://picsum.photos/200?random=5.jpg",
//            "https://picsum.photos/200?random=6.jpg",
//            "https://picsum.photos/200?random=7.jpg",
//            "https://picsum.photos/200?random=8.jpg"
//        )
//        return listOfUrl.random()
//    }

    private fun connectionFailureDialog() {
        val connectionFailureDialog = AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.connectionErrorDialogTitle))
            .setMessage(getString(R.string.connectionErrorDialogMsg))
            .setIcon(R.drawable.ic_error)
            .create()
        connectionFailureDialog.show()
    }

    private fun showDatabaseData(users: ArrayList<FirestoreUser>) {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = DatabaseAdapter(users) {
                navigateToUserDetailFragment(it)
            }
        }
    }

    private fun navigateToUserDetailFragment(user: FirestoreUser) {
        val action =
            UsersListFragmentDirections.actionRecyclerViewFragmentToUserDetailFragment(user)
        findNavController().navigate(action)
    }
}

