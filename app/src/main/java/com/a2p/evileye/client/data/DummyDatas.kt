package com.a2p.evileye.client.data

import com.yt8492.evileye.protobuf.Tarekomi
import com.yt8492.evileye.protobuf.TarekomiSummary

object DummyDatas {
    val tarekomiSummaries = (1..10).map { id ->
        TarekomiSummary.newBuilder()
            .setTarekomi(Tarekomi.newBuilder()
                .setTargetUserId(id.toLong())
                .setUrl("url$id")
                .setDesc("desc$id")
                .build())
            .setUserName("User$id")
            .build()
    }
}