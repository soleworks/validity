package io.github.soleworks.validity.constraints

/**
 * A tax or identity document, for [taxId]: each one is a type of document of one country.
 *
 * An entry is named after the country and the type, as `AR_CUIT`, and is checked by the constraint of that document
 * in the package of the country.
 */
public enum class TaxIdType {
    /**
     * The `EMIRATES_ID` of the United Arab Emirates, checked as
     * [emiratesId][io.github.soleworks.validity.constraints.unitedarabemirates.emiratesId] does.
     */
    AE_EMIRATES_ID,

    /**
     * The `TRADE_LICENSE` of the United Arab Emirates, checked as
     * [tradeLicense][io.github.soleworks.validity.constraints.unitedarabemirates.tradeLicense] does.
     */
    AE_TRADE_LICENSE,

    /** The `CUIL` of Argentina, checked as [cuil][io.github.soleworks.validity.constraints.argentina.cuil] does. */
    AR_CUIL,

    /** The `CUIT` of Argentina, checked as [cuit][io.github.soleworks.validity.constraints.argentina.cuit] does. */
    AR_CUIT,

    /** The `ABN` of Australia, checked as [abn][io.github.soleworks.validity.constraints.australia.abn] does. */
    AU_ABN,

    /** The `TFN` of Australia, checked as [tfn][io.github.soleworks.validity.constraints.australia.tfn] does. */
    AU_TFN,

    /** The `BCE` of Belgium, checked as [bce][io.github.soleworks.validity.constraints.belgium.bce] does. */
    BE_BCE,

    /**
     * The `NATIONAL_NUMBER` of Belgium, checked as
     * [nationalNumber][io.github.soleworks.validity.constraints.belgium.nationalNumber] does.
     */
    BE_NATIONAL_NUMBER,

    /** The `EGN` of Bulgaria, checked as [egn][io.github.soleworks.validity.constraints.bulgaria.egn] does. */
    BG_EGN,

    /** The `CNPJ` of Brazil, checked as [cnpj][io.github.soleworks.validity.constraints.brazil.cnpj] does. */
    BR_CNPJ,

    /** The `CPF` of Brazil, checked as [cpf][io.github.soleworks.validity.constraints.brazil.cpf] does. */
    BR_CPF,

    /** The `BN` of Canada, checked as [bn][io.github.soleworks.validity.constraints.canada.bn] does. */
    CA_BN,

    /** The `SIN` of Canada, checked as [sin][io.github.soleworks.validity.constraints.canada.sin] does. */
    CA_SIN,

    /** The `AHV` of Switzerland, checked as [ahv][io.github.soleworks.validity.constraints.switzerland.ahv] does. */
    CH_AHV,

    /** The `UID` of Switzerland, checked as [uid][io.github.soleworks.validity.constraints.switzerland.uid] does. */
    CH_UID,

    /** The `RUT` of Chile, checked as [rut][io.github.soleworks.validity.constraints.chile.rut] does. */
    CL_RUT,

    /** The `USCC` of China, checked as [uscc][io.github.soleworks.validity.constraints.china.uscc] does. */
    CN_USCC,

    /** The `CC` of Colombia, checked as [cc][io.github.soleworks.validity.constraints.colombia.cc] does. */
    CO_CC,

    /** The `NIT` of Colombia, checked as [nit][io.github.soleworks.validity.constraints.colombia.nit] does. */
    CO_NIT,

    /**
     * The `STEUER_ID` of Germany, checked as
     * [steuerId][io.github.soleworks.validity.constraints.germany.steuerId] does.
     */
    DE_STEUER_ID,

    /**
     * The `UST_ID_NR` of Germany, checked as
     * [ustIdNr][io.github.soleworks.validity.constraints.germany.ustIdNr] does.
     */
    DE_UST_ID_NR,

    /** The `CIF` of Spain, checked as [cif][io.github.soleworks.validity.constraints.spain.cif] does. */
    ES_CIF,

    /** The `DNI` of Spain, checked as [dni][io.github.soleworks.validity.constraints.spain.dni] does. */
    ES_DNI,

    /** The `NIE` of Spain, checked as [nie][io.github.soleworks.validity.constraints.spain.nie] does. */
    ES_NIE,

    /** The `SIREN` of France, checked as [siren][io.github.soleworks.validity.constraints.france.siren] does. */
    FR_SIREN,

    /** The `SIRET` of France, checked as [siret][io.github.soleworks.validity.constraints.france.siret] does. */
    FR_SIRET,

    /** The `SPI` of France, checked as [spi][io.github.soleworks.validity.constraints.france.spi] does. */
    FR_SPI,

    /**
     * The `NINO` of the United Kingdom, checked as
     * [nino][io.github.soleworks.validity.constraints.unitedkingdom.nino] does.
     */
    GB_NINO,

    /**
     * The `VAT` of the United Kingdom, checked as
     * [vat][io.github.soleworks.validity.constraints.unitedkingdom.vat] does.
     */
    GB_VAT,

    /**
     * The `COMPANY_NUMBER` of Israel, checked as
     * [companyNumber][io.github.soleworks.validity.constraints.israel.companyNumber] does.
     */
    IL_COMPANY_NUMBER,

    /**
     * The `TEUDAT_ZEHUT` of Israel, checked as
     * [teudatZehut][io.github.soleworks.validity.constraints.israel.teudatZehut] does.
     */
    IL_TEUDAT_ZEHUT,

    /** The `GSTIN` of India, checked as [gstin][io.github.soleworks.validity.constraints.india.gstin] does. */
    IN_GSTIN,

    /** The `PAN` of India, checked as [pan][io.github.soleworks.validity.constraints.india.pan] does. */
    IN_PAN,

    /**
     * The `CODICE_FISCALE` of Italy, checked as
     * [codiceFiscale][io.github.soleworks.validity.constraints.italy.codiceFiscale] does.
     */
    IT_CODICE_FISCALE,

    /**
     * The `PARTITA_IVA` of Italy, checked as
     * [partitaIva][io.github.soleworks.validity.constraints.italy.partitaIva] does.
     */
    IT_PARTITA_IVA,

    /**
     * The `CORPORATE_NUMBER` of Japan, checked as
     * [corporateNumber][io.github.soleworks.validity.constraints.japan.corporateNumber] does.
     */
    JP_CORPORATE_NUMBER,

    /**
     * The `MY_NUMBER` of Japan, checked as
     * [myNumber][io.github.soleworks.validity.constraints.japan.myNumber] does.
     */
    JP_MY_NUMBER,

    /** The `BRN` of South Korea, checked as [brn][io.github.soleworks.validity.constraints.southkorea.brn] does. */
    KR_BRN,

    /** The `RRN` of South Korea, checked as [rrn][io.github.soleworks.validity.constraints.southkorea.rrn] does. */
    KR_RRN,

    /** The `RFC` of Mexico, checked as [rfc][io.github.soleworks.validity.constraints.mexico.rfc] does. */
    MX_RFC,

    /**
     * The `BSN` of the Netherlands, checked as
     * [bsn][io.github.soleworks.validity.constraints.netherlands.bsn] does.
     */
    NL_BSN,

    /**
     * The `KVK` of the Netherlands, checked as
     * [kvk][io.github.soleworks.validity.constraints.netherlands.kvk] does.
     */
    NL_KVK,

    /**
     * The `FODSELSNUMMER` of Norway, checked as
     * [fodselsnummer][io.github.soleworks.validity.constraints.norway.fodselsnummer] does.
     */
    NO_FODSELSNUMMER,

    /**
     * The `ORGANISASJONSNUMMER` of Norway, checked as
     * [organisasjonsnummer][io.github.soleworks.validity.constraints.norway.organisasjonsnummer] does.
     */
    NO_ORGANISASJONSNUMMER,

    /** The `DNI` of Peru, checked as [dni][io.github.soleworks.validity.constraints.peru.dni] does. */
    PE_DNI,

    /** The `RUC` of Peru, checked as [ruc][io.github.soleworks.validity.constraints.peru.ruc] does. */
    PE_RUC,

    /** The `CNIC` of Pakistan, checked as [cnic][io.github.soleworks.validity.constraints.pakistan.cnic] does. */
    PK_CNIC,

    /** The `NIP` of Poland, checked as [nip][io.github.soleworks.validity.constraints.poland.nip] does. */
    PL_NIP,

    /** The `PESEL` of Poland, checked as [pesel][io.github.soleworks.validity.constraints.poland.pesel] does. */
    PL_PESEL,

    /** The `NIF` of Portugal, checked as [nif][io.github.soleworks.validity.constraints.portugal.nif] does. */
    PT_NIF,

    /** The `NIPC` of Portugal, checked as [nipc][io.github.soleworks.validity.constraints.portugal.nipc] does. */
    PT_NIPC,

    /** The `CI` of Paraguay, checked as [ci][io.github.soleworks.validity.constraints.paraguay.ci] does. */
    PY_CI,

    /** The `RUC` of Paraguay, checked as [ruc][io.github.soleworks.validity.constraints.paraguay.ruc] does. */
    PY_RUC,

    /**
     * The `INN_INDIVIDUAL` of Russia, checked as
     * [innIndividual][io.github.soleworks.validity.constraints.russia.innIndividual] does.
     */
    RU_INN_INDIVIDUAL,

    /**
     * The `INN_LEGAL_ENTITY` of Russia, checked as
     * [innLegalEntity][io.github.soleworks.validity.constraints.russia.innLegalEntity] does.
     */
    RU_INN_LEGAL_ENTITY,

    /**
     * The `ORGANISATIONSNUMMER` of Sweden, checked as
     * [organisationsnummer][io.github.soleworks.validity.constraints.sweden.organisationsnummer] does.
     */
    SE_ORGANISATIONSNUMMER,

    /**
     * The `PERSONNUMMER` of Sweden, checked as
     * [personnummer][io.github.soleworks.validity.constraints.sweden.personnummer] does.
     */
    SE_PERSONNUMMER,

    /**
     * The `DAVCNA_STEVILKA` of Slovenia, checked as
     * [davcnaStevilka][io.github.soleworks.validity.constraints.slovenia.davcnaStevilka] does.
     */
    SI_DAVCNA_STEVILKA,

    /**
     * The `NATIONAL_ID` of Thailand, checked as
     * [nationalId][io.github.soleworks.validity.constraints.thailand.nationalId] does.
     */
    TH_NATIONAL_ID,

    /** The `RNOKPP` of Ukraine, checked as [rnokpp][io.github.soleworks.validity.constraints.ukraine.rnokpp] does. */
    UA_RNOKPP,

    /**
     * The `EIN` of the United States, checked as
     * [ein][io.github.soleworks.validity.constraints.unitedstates.ein] does.
     */
    US_EIN,

    /**
     * The `ITIN` of the United States, checked as
     * [itin][io.github.soleworks.validity.constraints.unitedstates.itin] does.
     */
    US_ITIN,

    /**
     * The `SSN` of the United States, checked as
     * [ssn][io.github.soleworks.validity.constraints.unitedstates.ssn] does.
     */
    US_SSN,

    /** The `CI` of Uruguay, checked as [ci][io.github.soleworks.validity.constraints.uruguay.ci] does. */
    UY_CI,

    /** The `RUT` of Uruguay, checked as [rut][io.github.soleworks.validity.constraints.uruguay.rut] does. */
    UY_RUT,

    /**
     * The `CIPC` of South Africa, checked as
     * [cipc][io.github.soleworks.validity.constraints.southafrica.cipc] does.
     */
    ZA_CIPC,

    /**
     * The `ID_NUMBER` of South Africa, checked as
     * [idNumber][io.github.soleworks.validity.constraints.southafrica.idNumber] does.
     */
    ZA_ID_NUMBER;

    /** The ISO 3166-1 alpha-2 code of the country of the document, such as `AR`. */
    public val country: String
        get() = name.substringBefore('_')

    /** The type of the document in its country, such as `CUIT`. */
    public val type: String
        get() = name.substringAfter('_')
}
