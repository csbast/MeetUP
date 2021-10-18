package com.example.meetup.presentation

import android.app.Activity.RESULT_OK
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.example.meetup.R
import com.example.meetup.databinding.FragmentUserDetailBinding
import com.example.meetup.model.UserComplete
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class UserDetailFragment : Fragment(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    private lateinit var binding: FragmentUserDetailBinding
    private lateinit var navController: NavController
    private lateinit var mAuth: FirebaseAuth
    private val args: UserDetailFragmentArgs by navArgs()
    private val user by lazy {
        arguments?.getParcelable("mKey") as? UserComplete
    }

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 1
        private const val TAG = "UserDetailFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_user_detail, container, false
        )
        navController = findNavController()
        mAuth = Firebase.auth
        setupUi()
        setupListener()
        setupToolbar()
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_logout -> {
                mAuth.signOut()
                navController.graph.startDestination = R.id.loginFragment
                navController.navigate(R.id.loginFragment)
            }
            else -> Log.d("###", "ELSE")
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            binding.expandedImage.setImageBitmap(imageBitmap)
            user?.let { userObject ->
                userObject.imageBitmap = imageBitmap
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("###", "$toolbar")
    }


    private fun setupUi() {
        val addressObject = args.userObject.address
        val userObject = args.userObject

        binding.apply {
            userDetailZipCodeTextView.text = addressObject.zipcode
            userDetailSuiteTextView.text = addressObject.suite
            userDetailCityTextView.text = addressObject.city
            userDetailStreetTextView.text = addressObject.street

            userDetailUserNameTextView.text = userObject.username
            userDetailEmailTextView.text = userObject.email
            userDetailIdTextView.text = userObject.id
            userDetailPhoneNumberTextView.text = userObject.phone
            userDetailWebsiteTextView.text = userObject.website
        }
        val userUrl = userObject.imageUrl
        Glide.with(requireContext())
            .load(userUrl)
            .into(binding.expandedImage)
    }

    private fun setupListener() {
        setupMap()
        setupDateTimePickerBtn()
        setupShareIntent()
        setupCameraBtn()
    }

    private fun setupCameraBtn() {
        binding.cameraBtn.setOnClickListener {
            openCameraApp()
        }
    }

    private fun openCameraApp() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
            Log.d(TAG, e.message ?: "Couldn't be able to open camera")
        }
    }

    private fun setupShareIntent() {
        val userString = args.userObject.toString()
        binding.shareBtn.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, userString)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }

    private fun setupDateTimePickerBtn() {
        binding.datePickerBtn.setOnClickListener {
            val cal: Calendar = Calendar.getInstance()
            val day = cal.get(Calendar.DAY_OF_MONTH)
            val month = cal.get(Calendar.MONTH)
            val year = cal.get(Calendar.YEAR)
            DatePickerDialog(requireContext(), this, year, month, day).show()
        }
    }

    private fun setupMap() {
        val userObject = args.userObject
        val action =
            UserDetailFragmentDirections.actionUserDetailFragmentToMapsFragment(userObject)
        binding.mapBtn.setOnClickListener {
            navController.navigate(action)
        }
    }

    private fun setupToolbar() {
        binding.collapsingToolbarLayout.apply {
            title = args.userObject.name
            setExpandedTitleColor(Color.WHITE)
            setCollapsedTitleTextColor(Color.WHITE)
        }
        binding.userDetailToolbar.apply {
            setupWithNavController(navController)
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val cal: Calendar = Calendar.getInstance()
        val hour = cal.get(Calendar.HOUR)
        val minute = cal.get(Calendar.MINUTE)
        val savedDay = dayOfMonth.toString()
        val savedMonth = month.toString()
        val savedYear = year.toString()
        TimePickerDialog(requireContext(), this, hour, minute, true).show()
        binding.datePickerText.text =
            getString(R.string.dateMessage, savedDay, savedMonth, savedYear)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        val savedHour = hourOfDay.toString()
        val savedMinute = minute.toString()
        binding.timePickerText.text =
            getString(R.string.timeMessage, savedHour, savedMinute)
    }
}