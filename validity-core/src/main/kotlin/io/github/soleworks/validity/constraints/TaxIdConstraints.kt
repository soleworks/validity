package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.constraints.argentina.ARGENTINA_TAX_IDS
import io.github.soleworks.validity.constraints.australia.AUSTRALIA_TAX_IDS
import io.github.soleworks.validity.constraints.belgium.BELGIUM_TAX_IDS
import io.github.soleworks.validity.constraints.brazil.BRAZIL_TAX_IDS
import io.github.soleworks.validity.constraints.bulgaria.BULGARIA_TAX_IDS
import io.github.soleworks.validity.constraints.canada.CANADA_TAX_IDS
import io.github.soleworks.validity.constraints.chile.CHILE_TAX_IDS
import io.github.soleworks.validity.constraints.china.CHINA_TAX_IDS
import io.github.soleworks.validity.constraints.colombia.COLOMBIA_TAX_IDS
import io.github.soleworks.validity.constraints.france.FRANCE_TAX_IDS
import io.github.soleworks.validity.constraints.germany.GERMANY_TAX_IDS
import io.github.soleworks.validity.constraints.india.INDIA_TAX_IDS
import io.github.soleworks.validity.constraints.israel.ISRAEL_TAX_IDS
import io.github.soleworks.validity.constraints.italy.ITALY_TAX_IDS
import io.github.soleworks.validity.constraints.japan.JAPAN_TAX_IDS
import io.github.soleworks.validity.constraints.mexico.MEXICO_TAX_IDS
import io.github.soleworks.validity.constraints.netherlands.NETHERLANDS_TAX_IDS
import io.github.soleworks.validity.constraints.norway.NORWAY_TAX_IDS
import io.github.soleworks.validity.constraints.pakistan.PAKISTAN_TAX_IDS
import io.github.soleworks.validity.constraints.paraguay.PARAGUAY_TAX_IDS
import io.github.soleworks.validity.constraints.peru.PERU_TAX_IDS
import io.github.soleworks.validity.constraints.poland.POLAND_TAX_IDS
import io.github.soleworks.validity.constraints.portugal.PORTUGAL_TAX_IDS
import io.github.soleworks.validity.constraints.russia.RUSSIA_TAX_IDS
import io.github.soleworks.validity.constraints.slovenia.SLOVENIA_TAX_IDS
import io.github.soleworks.validity.constraints.southafrica.SOUTH_AFRICA_TAX_IDS
import io.github.soleworks.validity.constraints.southkorea.SOUTH_KOREA_TAX_IDS
import io.github.soleworks.validity.constraints.spain.SPAIN_TAX_IDS
import io.github.soleworks.validity.constraints.sweden.SWEDEN_TAX_IDS
import io.github.soleworks.validity.constraints.switzerland.SWITZERLAND_TAX_IDS
import io.github.soleworks.validity.constraints.thailand.THAILAND_TAX_IDS
import io.github.soleworks.validity.constraints.ukraine.UKRAINE_TAX_IDS
import io.github.soleworks.validity.constraints.unitedarabemirates.UNITED_ARAB_EMIRATES_TAX_IDS
import io.github.soleworks.validity.constraints.unitedkingdom.UNITED_KINGDOM_TAX_IDS
import io.github.soleworks.validity.constraints.unitedstates.UNITED_STATES_TAX_IDS
import io.github.soleworks.validity.constraints.uruguay.URUGUAY_TAX_IDS
import io.github.soleworks.validity.filled
import io.github.soleworks.validity.messages

private val TAX_IDS: Map<String, Map<String, (String) -> Boolean>> = mapOf(
    "AE" to UNITED_ARAB_EMIRATES_TAX_IDS,
    "AR" to ARGENTINA_TAX_IDS,
    "AU" to AUSTRALIA_TAX_IDS,
    "BE" to BELGIUM_TAX_IDS,
    "BG" to BULGARIA_TAX_IDS,
    "BR" to BRAZIL_TAX_IDS,
    "CA" to CANADA_TAX_IDS,
    "CH" to SWITZERLAND_TAX_IDS,
    "CL" to CHILE_TAX_IDS,
    "CN" to CHINA_TAX_IDS,
    "CO" to COLOMBIA_TAX_IDS,
    "DE" to GERMANY_TAX_IDS,
    "ES" to SPAIN_TAX_IDS,
    "FR" to FRANCE_TAX_IDS,
    "GB" to UNITED_KINGDOM_TAX_IDS,
    "IL" to ISRAEL_TAX_IDS,
    "IN" to INDIA_TAX_IDS,
    "IT" to ITALY_TAX_IDS,
    "JP" to JAPAN_TAX_IDS,
    "KR" to SOUTH_KOREA_TAX_IDS,
    "MX" to MEXICO_TAX_IDS,
    "NL" to NETHERLANDS_TAX_IDS,
    "NO" to NORWAY_TAX_IDS,
    "PE" to PERU_TAX_IDS,
    "PK" to PAKISTAN_TAX_IDS,
    "PL" to POLAND_TAX_IDS,
    "PT" to PORTUGAL_TAX_IDS,
    "PY" to PARAGUAY_TAX_IDS,
    "RU" to RUSSIA_TAX_IDS,
    "SE" to SWEDEN_TAX_IDS,
    "SI" to SLOVENIA_TAX_IDS,
    "TH" to THAILAND_TAX_IDS,
    "UA" to UKRAINE_TAX_IDS,
    "US" to UNITED_STATES_TAX_IDS,
    "UY" to URUGUAY_TAX_IDS,
    "ZA" to SOUTH_AFRICA_TAX_IDS
)

/**
 * Checks that the string is a tax or identity number of [type] in [country], with the same rules as the constraint
 * of that document in the package of the country.
 *
 * [type] is the name of that constraint in upper snake case: `CUIT` checks what `cuit` checks, `PARTITA_IVA` what
 * `partitaIva` checks. Both arguments can be in any case. When [country] or [type] is `null`, or the pair is not known,
 * nothing is checked, so this overload is for a country and a type that come as text, such as from a request. When the
 * document is known in the code, the overload that takes a [TaxIdType] does not compile with a pair that does not
 * exist. Documents are known for 36 countries:
 *
 * | Country | Types |
 * |---|---|
 * | `AE` | `TRADE_LICENSE`, `EMIRATES_ID` |
 * | `AR` | `CUIT`, `CUIL` |
 * | `AU` | `ABN`, `TFN` |
 * | `BE` | `BCE`, `NATIONAL_NUMBER` |
 * | `BG` | `EGN` |
 * | `BR` | `CPF`, `CNPJ` |
 * | `CA` | `SIN`, `BN` |
 * | `CH` | `UID`, `AHV` |
 * | `CL` | `RUT` |
 * | `CN` | `USCC` |
 * | `CO` | `NIT`, `CC` |
 * | `DE` | `UST_ID_NR`, `STEUER_ID` |
 * | `ES` | `DNI`, `NIE`, `CIF` |
 * | `FR` | `SPI`, `SIREN`, `SIRET` |
 * | `GB` | `VAT`, `NINO` |
 * | `IL` | `TEUDAT_ZEHUT`, `COMPANY_NUMBER` |
 * | `IN` | `PAN`, `GSTIN` |
 * | `IT` | `PARTITA_IVA`, `CODICE_FISCALE` |
 * | `JP` | `CORPORATE_NUMBER`, `MY_NUMBER` |
 * | `KR` | `BRN`, `RRN` |
 * | `MX` | `RFC` |
 * | `NL` | `BSN`, `KVK` |
 * | `NO` | `ORGANISASJONSNUMMER`, `FODSELSNUMMER` |
 * | `PE` | `RUC`, `DNI` |
 * | `PK` | `CNIC` |
 * | `PL` | `PESEL`, `NIP` |
 * | `PT` | `NIF`, `NIPC` |
 * | `PY` | `RUC`, `CI` |
 * | `RU` | `INN_INDIVIDUAL`, `INN_LEGAL_ENTITY` |
 * | `SE` | `ORGANISATIONSNUMMER`, `PERSONNUMMER` |
 * | `SI` | `DAVCNA_STEVILKA` |
 * | `TH` | `NATIONAL_ID` |
 * | `UA` | `RNOKPP` |
 * | `US` | `EIN`, `SSN`, `ITIN` |
 * | `UY` | `RUT`, `CI` |
 * | `ZA` | `CIPC`, `ID_NUMBER` |
 *
 * Fails with code `taxId` and, by default, the message `must be a valid {type} for {country}`
 * ([Messages.taxId][io.github.soleworks.validity.Messages.taxId]).
 *
 * ```kotlin
 * ::document required { taxId(country = country, type = documentType) }
 * ```
 *
 * @param country the ISO 3166-1 alpha-2 code of the country, in any case.
 * @param type the type of document in the country, in any case.
 * @param message the message of the violation. `{country}`, `{type}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.taxId(
    country: String?,
    type: String?,
    message: String = messages.taxId
): Unit = taxId(
    country = country,
    type = type,
    message = { _, _ -> message.filled("country" to country, "type" to type) }
)

/**
 * Checks that the string is a tax or identity number of [type] in [country], with a message built from the value.
 *
 * When [country] or [type] is `null`, or the pair is not known, nothing is checked.
 *
 * Fails with code `taxId`.
 *
 * @param country the ISO 3166-1 alpha-2 code of the country, in any case.
 * @param type the type of document in the country, in any case.
 * @param message builds the message from the value (the receiver), [country] and [type].
 */
public fun ValidationNode<String>.taxId(
    country: String?,
    type: String?,
    message: suspend String.(String, String) -> String
) {
    if (country == null || type == null)
        return

    val predicate = TAX_IDS[country.uppercase()]?.get(type.uppercase()) ?: return

    constraint(
        message = { it.message(country, type) },
        code = "taxId",
        predicate = { predicate(it) }
    )
}

/**
 * Checks that the string is a document of [type], with the same rules as the constraint of that document in the
 * package of its country.
 *
 * When [type] is `null`, nothing is checked.
 *
 * Fails with code `taxId` and, by default, the message `must be a valid {type} for {country}`
 * ([Messages.taxId][io.github.soleworks.validity.Messages.taxId]), where `{type}` is [TaxIdType.type] and `{country}`
 * is [TaxIdType.country].
 *
 * ```kotlin
 * ::cuit required { taxId(TaxIdType.AR_CUIT) }
 * ```
 *
 * @param type the document, or `null` to check nothing.
 * @param message the message of the violation. `{country}`, `{type}`, `{path}` and `{value}` are filled in.
 */
public fun ValidationNode<String>.taxId(
    type: TaxIdType?,
    message: String = messages.taxId
): Unit = taxId(
    type = type,
    message = { document -> message.filled("country" to document.country, "type" to document.type) }
)

/**
 * Checks that the string is a document of [type], with a message built from the value.
 *
 * When [type] is `null`, nothing is checked.
 *
 * Fails with code `taxId`.
 *
 * @param type the document, or `null` to check nothing.
 * @param message builds the message from the value (the receiver) and [type].
 */
public fun ValidationNode<String>.taxId(
    type: TaxIdType?,
    message: suspend String.(TaxIdType) -> String
) {
    if (type == null)
        return

    val predicate = TAX_IDS.getValue(type.country).getValue(type.type)

    constraint(
        message = { it.message(type) },
        code = "taxId",
        predicate = { predicate(it) }
    )
}
