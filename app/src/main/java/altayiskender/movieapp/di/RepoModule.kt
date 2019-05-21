package altayiskender.movieapp.di

import altayiskender.movieapp.BuildConfig
import altayiskender.movieapp.repository.AppDatabase
import altayiskender.movieapp.repository.MovieBookmarksDao
import altayiskender.movieapp.repository.Repository
import altayiskender.movieapp.repository.TmdbApi
import android.app.Application
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

private const val SERVER_URL = "https://api.themoviedb.org/3/"
private const val API_KEY = "api_key"
private const val LANGUAGE = "language"

private const val key = BuildConfig.TMDB_API_TOKEN

@Module
class RepoModule {


    @Provides
    @Reusable
    fun providesOkHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder().addInterceptor(AuthInterceptor())
        return client.build()
    }

    @Provides
    @Reusable
    fun providesRetrofitClient(okHttpClient: OkHttpClient): TmdbApi {
        return Retrofit.Builder()
                .baseUrl(SERVER_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(TmdbApi::class.java)
    }

    @Provides
    @Reusable
    fun providesAppDatabase(app: Application): AppDatabase {
        return AppDatabase.getInstance(app)
    }

    @Provides
    @Reusable
    fun providesMovieBookmarksDao(database: AppDatabase): MovieBookmarksDao {
        return database.movieBookmarksDao()
    }

    @Provides
    @Reusable
    fun provideRepository(dbApi: TmdbApi, movieBookmarksDao: MovieBookmarksDao): Repository {
        return Repository(dbApi, movieBookmarksDao)
    }

}


class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response {
        var request = chain?.request()
        val url = request?.url()?.newBuilder()?.addQueryParameter(API_KEY, key)?.addQueryParameter(LANGUAGE, Locale.getDefault().language)?.build()
        request = request?.newBuilder()?.url(url!!)?.build()
        return chain!!.proceed(request!!)

    }
}