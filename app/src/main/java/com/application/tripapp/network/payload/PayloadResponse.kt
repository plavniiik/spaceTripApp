package com.application.tripapp.network.payload

data class PayloadResponse(
    val id: String,
    val description: String,
    val esID: String,
    val files: List<Any>,
    val hardware: List<Hardware>,
    val identifier: String,
    val identifierLowercase: String,
    val missions: List<Mission>,
    val parents: Parents,
    val payloadName: String,
    val people: List<People>,
    val subjectGroups: List<SubjectGroup>,
    val type: String,
    val versionInfo: VersionInfoX
)

