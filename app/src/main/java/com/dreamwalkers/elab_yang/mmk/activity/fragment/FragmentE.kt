package com.dreamwalkers.elab_yang.mmk.activity.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dreamwalkers.elab_yang.mmk.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [FragmentE.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [FragmentE.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class FragmentE : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment_e, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event

}
