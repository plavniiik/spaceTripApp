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

data class Parents(
    val GLDS_Study: List<Any>,
    val experiment: List<Experiment>
)

data class People(
    val institution: String,
    val person: Person,
    val roles: List<String>
)

data class Mission(
    val endDate: String,
    val identifier: String,
    val link: String,
    val startDate: String
)

data class Hardware(
    val link: String,
    val versionDescription: String,
    val versionName: String
)

data class Person(
    val emailAddress: String,
    val firstName: String,
    val id: String,
    val lastName: String,
    val middleName: String,
    val phone: String,
    val versionInfo: VersionInfoX
)

data class ScientificName(
    val annotationValue: String,
    val annotationValueLower: String,
    val branch: List<String>,
    val definition: String,
    val freeOntology: Boolean,
    val id: String,
    val mapping: List<Any>,
    val termAccession: String,
    val termSource: String
)

data class SubjectGroup(
    val commonName: CommonName,
    val description: String,
    val identifier: String,
    val link: String,
    val scientificName: ScientificName
)

data class VersionInfoX(
    val deleted: Boolean,
    val documentKey: String,
    val version: Int
)

data class CommonName(
    val annotationValue: String,
    val annotationValueLower: String,
    val branch: List<String>,
    val definition: String,
    val freeOntology: Boolean,
    val id: String,
    val mapping: List<Any>,
    val termAccession: String,
    val termSource: String
)

data class Experiment(
    val experiment: String
)

