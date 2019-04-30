package com.a2p.evileye.client

import android.app.Application
import com.a2p.evileye.client.data.EvilEyeService
import com.a2p.evileye.client.di.applicationModule
import com.github.salomonbrys.kodein.*
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder

class App : Application(), KodeinAware {
    override val kodein by Kodein.lazy {
        import(applicationModule(this@App))
        bind<EvilEyeService>() with singleton {
            EvilEyeService(
                instance(),
                "13.112.130.130",
                50051
            )
        }
    }
}