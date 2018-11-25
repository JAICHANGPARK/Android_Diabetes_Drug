package com.dreamwalkers.elab_yang.mmk.model.drugselect;


import com.dreamwalkers.elab_yang.mmk.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Artem Kholodnyi on 9/6/16.
 */
public class TrackList {
    public final static List<Track> TRACKS = new ArrayList<Track>() {{
        add(new Track("노보믹스30", "혼합형", R.drawable.ic_syringe_mix));
        add(new Track("노보믹스50", "혼합형", R.drawable.ic_syringe_mix));
        add(new Track("노보믹스70", "혼합형", R.drawable.ic_syringe_mix));
        add(new Track("믹스타드30", "혼합형", R.drawable.ic_syringe_mix));
        add(new Track("휴마로그믹스25", "혼합형", R.drawable.ic_syringe_mix));
        add(new Track("휴마로그믹스50", "혼합형", R.drawable.ic_syringe_mix));
        add(new Track("휴물린70/30", "혼합형", R.drawable.ic_syringe_mix));
        
        add(new Track("노보래피드", "초속효성", R.drawable.ic_syringe_rrpid));
        add(new Track("휴마로그", "초속효성", R.drawable.ic_syringe_rrpid));
        add(new Track("애피드라", "초속효성", R.drawable.ic_syringe_rrpid));
        add(new Track("휴물린R", "속효성", R.drawable.ic_syringe_rapid));
        add(new Track("노보린R", "속효성", R.drawable.ic_syringe_rapid));
        add(new Track("노보렛R", "속효성", R.drawable.ic_syringe_rapid));
        add(new Track("인슈라타드", "중간형", R.drawable.ic_syringe_middle));
        add(new Track("휴물린N", "중간형", R.drawable.ic_syringe_middle));
        add(new Track("노보린N", "중간형", R.drawable.ic_syringe_middle));
        add(new Track("노보렛N", "중간형", R.drawable.ic_syringe_middle));
        add(new Track("란투스", "지속형", R.drawable.ic_syringe_long));
        add(new Track("레버미어", "지속형", R.drawable.ic_syringe_long));
        add(new Track("투제오", "지속형", R.drawable.ic_syringe_long));
        add(new Track("트레시바", "지속형", R.drawable.ic_syringe_long));

    }};
}
