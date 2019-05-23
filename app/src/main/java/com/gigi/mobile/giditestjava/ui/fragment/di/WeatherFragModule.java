package com.gigi.mobile.giditestjava.ui.fragment.di;

import com.gigi.mobile.giditestjava.repo.Repository;
import com.gigi.mobile.giditestjava.repo.offline.CurrentDao;
import com.gigi.mobile.giditestjava.repo.offline.ForecastDao;
import com.gigi.mobile.giditestjava.repo.remote.ApiService;
import com.gigi.mobile.giditestjava.utils.scheduler.SchedulerProvider;
import com.gigi.mobile.giditestjava.viewmodels.MainActivityViewModel;
import com.gigi.mobile.giditestjava.viewmodels.ViewModelFactory;

import androidx.lifecycle.ViewModelProvider;
import dagger.Module;
import dagger.Provides;

@Module
public class WeatherFragModule {

    @Provides
    MainActivityViewModel provideViewModel(Repository repository, SchedulerProvider provider) {
        return new MainActivityViewModel(repository, provider);
    }

    @Provides
    Repository provideRepository(ApiService apiService, CurrentDao currentDao, ForecastDao dataDao,
                                 SchedulerProvider provider){
        return new Repository(apiService, currentDao, dataDao, provider);
    }

    @Provides
    ViewModelProvider.Factory provideViewModelProvider(MainActivityViewModel viewModel){
        return new ViewModelFactory<>(viewModel);
    }

}
