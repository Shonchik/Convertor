package com.example.convertor.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlValue

@Serializable
data class ValCurs(
    @SerialName("Date") val date: String,
    @SerialName("name") val name: String,
    @SerialName("Valute ") val valute: List<Valute>,
)

@Serializable
data class Valute(
    @SerialName("ID") val id: String,
    @SerialName("NumCode") val numCode: NumCode,
    @SerialName("CharCode") val charCode: CharCode,
    @SerialName("Nominal") val nominal: Nominal,
    @SerialName("Name") val name: Name,
    @SerialName("Value") val value: Value,
)

@Serializable
data class NumCode(
    @XmlValue(value = true) val name: String,
)

@Serializable
data class CharCode(
    @XmlValue(value = true) val name: String,
)

@Serializable
data class Nominal(
    @XmlValue(value = true) val name: String,
)

@Serializable
data class Name(
    @XmlValue(value = true) val name: String,
)

@Serializable
data class Value(
    @XmlValue(value = true) val name: String,
)