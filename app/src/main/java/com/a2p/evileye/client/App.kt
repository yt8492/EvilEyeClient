package com.a2p.evileye.client

import android.app.Application
import com.a2p.evileye.client.data.user.UserRepository
import com.a2p.evileye.client.di.applicationModule
import com.github.salomonbrys.kodein.*
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder

class App : Application(), KodeinAware {
    override val kodein by Kodein.lazy {
        import(applicationModule(this@App))
        bind<ManagedChannel>() with singleton {
            ManagedChannelBuilder.forAddress("10.0.2.2", 50051)
                .usePlaintext()
                .build()
        }
        bind<UserRepository>() with singleton { UserRepository(instance(), instance()) }
    }
}