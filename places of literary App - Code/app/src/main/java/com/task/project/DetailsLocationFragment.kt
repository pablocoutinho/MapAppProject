package com.task.project

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment

import com.task.project.model.Location
import com.task.project.utils.InternetConnection.linkImages
import java.util.*

/**
 * A simple [Fragment] subclass.
 * Use the [DetailsLocationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailsLocationFragment : DialogFragment() {
    private lateinit var location: Location
    private lateinit var tvId: TextView
    private lateinit var tvName: TextView
    private lateinit var tvGaelicName: TextView
    private lateinit var tvLocation: TextView
    private lateinit var tvType: TextView
    private lateinit var tvLatitude: TextView
    private lateinit var tvLongitude: TextView
    private lateinit var imgShow: ImageView

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = requireArguments().getString(ARG_PARAM1)
            mParam2 = requireArguments().getString(ARG_PARAM2)
        }
    }

    @SuppressLint("ResourceType", "UseCompatLoadingForDrawables", "MissingInflatedId")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_details_location, container, false)
        imgShow = view.findViewById(R.id.img_show)
        tvId = view.findViewById(R.id.tv_id)
        tvLocation = view.findViewById(R.id.tv_location)
        tvName = view.findViewById(R.id.tv_name)
        tvGaelicName = view.findViewById(R.id.tv_name_two)
        tvLatitude = view.findViewById(R.id.tv_lat)
        tvLongitude = view.findViewById(R.id.tv_long)
        tvType = view.findViewById(R.id.tv_type)
        view.findViewById<View>(R.id.back).setOnClickListener { view1: View? -> dismiss() }
        view.findViewById<View>(R.id.btn_close).setOnClickListener { view1: View? -> dismiss() }
        location = MainActivity.currentLocationItem!!
        this.tvId.text="ID:  " + location.id
        this.tvType.text="Place Type ID:  " + location.place_type_id
        this.tvName.setText("Name:  " + location.name)
        this.tvGaelicName.setText("Gaelic Name:   " + location.gaelic_name)
        this.tvLongitude.setText("Longitude: " + location!!.longitude)
        this.tvLatitude.setText("Latitude:  " + location.latitude)
        this.tvLocation.setText("Location:  " + location.location)


            imgShow.setImageResource(linkImages[Random().nextInt(linkImages.size)])


        return view
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window!!.requestFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            dialog.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        }
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EditFlowerFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String?, param2: String?): DetailsLocationFragment {
            return DetailsLocationFragment()
        }
    }
}