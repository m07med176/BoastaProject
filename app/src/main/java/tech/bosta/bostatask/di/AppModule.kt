package tech.bosta.bostatask.di

import android.content.Context
import tech.bosta.bostatask.data.RepositoryImpl
import tech.bosta.bostatask.data.source.remote.IRemoteDataSource
import tech.bosta.bostatask.data.source.remote.RemoteDataSource
import tech.bosta.bostatask.data.source.remote.retrofit.RetrofitInstance
import tech.bosta.bostatask.domain.repository.IRepository
import tech.bosta.bostatask.domain.usecases.GetUsersCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRemoteSource(): IRemoteDataSource {
        return RemoteDataSource(RetrofitInstance().api)
    }

//    @Provides
//    @Singleton
//    fun provideLocalDB(@ApplicationContext context: Context): ILocalDataSource {
//        val db = RoomDB.invoke(context)
//        return LocalDataSource(db.cashDao())
//    }

    @Provides
    @Singleton
    fun provideRepository(
        remote: IRemoteDataSource
    ): IRepository {
        return RepositoryImpl(remote)
    }



}
