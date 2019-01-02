package altayiskender.movieapp


import altayiskender.movieapp.di.DaggerApplicationComponent
import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject


class MovieApp : Application(), HasActivityInjector {
    override fun activityInjector(): AndroidInjector<Activity> {
        return dispatchingActivityInjector
    }

    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        DaggerApplicationComponent.builder().application(this)
                .build()
                .inject(this)
    }

}