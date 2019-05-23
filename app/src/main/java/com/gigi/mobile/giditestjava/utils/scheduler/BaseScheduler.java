package com.gigi.mobile.giditestjava.utils.scheduler;

import io.reactivex.Scheduler;

public interface BaseScheduler {

    Scheduler computation();

    Scheduler io();

    Scheduler ui();


}
