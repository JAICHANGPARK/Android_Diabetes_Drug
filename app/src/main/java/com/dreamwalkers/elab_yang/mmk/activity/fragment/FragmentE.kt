package com.dreamwalkers.elab_yang.mmk.activity.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dreamwalkers.elab_yang.mmk.R
import com.dreamwalkers.elab_yang.mmk.adapter.UserDurgInfoAdapter
import com.dreamwalkers.elab_yang.mmk.model.needle.Drug
import io.paperdb.Paper
import kotlinx.android.synthetic.main.fragment_fragment_e.*

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


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_fragment_e, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Paper.init(activity)
        val drugArrayList = Paper.book().read<ArrayList<Drug>>("user_drug")
        val adapters = UserDurgInfoAdapter(activity, drugArrayList)
        with(recycler_view){
            recycler_view.setHasFixedSize(true)
            adapter = adapters
            layoutManager = LinearLayoutManager(activity)
        }

    }


}
