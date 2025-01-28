package com.shhatrat.loggerek.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LogTypeResponse(
    @SerialName("submittable_logtypes")
    val submittableLogTypes: List<LogType>,

    @SerialName("can_recommend")
    val canRecommend: Boolean,

    @SerialName("rcmd_founds_needed")
    val rcmdFoundsNeeded: Int? = null,

    @SerialName("can_rate")
    val canRate: Boolean,

    @SerialName("can_set_needs_maintenance")
    val canSetNeedsMaintenance: Boolean,

    @SerialName("can_reset_needs_maintenance")
    val canResetNeedsMaintenance: Boolean
)