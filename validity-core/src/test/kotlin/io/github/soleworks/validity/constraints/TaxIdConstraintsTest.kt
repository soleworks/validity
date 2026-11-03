package io.github.soleworks.validity.constraints

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.EnumSource

class TaxIdConstraintsTest {
    @Nested
    @DisplayName("When taxId is called")
    inner class TaxId {
        @ParameterizedTest
        @CsvSource(
            value = [
                "AE,EMIRATES_ID,'784196865703050'",
                "AE,TRADE_LICENSE,'CN-1234567'",
                "AR,CUIL,'20308246281'",
                "AR,CUIT,'33301661318'",
                "AU,ABN,'53710731177'",
                "AU,TFN,'468344386'",
                "BE,BCE,'0661488530'",
                "BE,NATIONAL_NUMBER,'74022569427'",
                "BG,EGN,'7501010010'",
                "BR,CNPJ,'11.222.333/0001-81'",
                "BR,CPF,'52998224725'",
                "CA,BN,'235396504'",
                "CA,SIN,'521719666'",
                "CH,AHV,'7564699338286'",
                "CH,UID,'CHE785278205'",
                "CL,RUT,'84268465-K'",
                "CN,USCC,'91927469EKP84HN3YJ'",
                "CO,CC,'1020304050'",
                "CO,NIT,'9002158108'",
                "DE,STEUER_ID,'84159263274'",
                "DE,UST_ID_NR,'894753402'",
                "ES,CIF,'B7905597F'",
                "ES,DNI,'12345678Z'",
                "ES,NIE,'X1234567L'",
                "FR,SIREN,'934461799'",
                "FR,SIRET,'82247920095500'",
                "FR,SPI,'3023217600053'",
                "GB,NINO,'AB123456C'",
                "GB,VAT,'385599877'",
                "IL,COMPANY_NUMBER,'521387803'",
                "IL,TEUDAT_ZEHUT,'219472156'",
                "IN,GSTIN,'10GXMFD3255G2ZB'",
                "IN,PAN,'ABCPE1234F'",
                "IT,CODICE_FISCALE,'XUFIAX24L20X122G'",
                "IT,PARTITA_IVA,'55396190013'",
                "JP,CORPORATE_NUMBER,'7785935252181'",
                "JP,MY_NUMBER,'695017077064'",
                "KR,BRN,'1348672683'",
                "KR,RRN,'5605231067971'",
                "MX,RFC,'GODE561231GR8'",
                "NL,BSN,'174559434'",
                "NL,KVK,'69599084'",
                "NO,FODSELSNUMMER,'19045128175'",
                "NO,ORGANISASJONSNUMMER,'913885821'",
                "PE,DNI,'99530552'",
                "PE,RUC,'20654444634'",
                "PK,CNIC,'45504-4185771-3'",
                "PL,NIP,'2234567895'",
                "PL,PESEL,'02070803628'",
                "PT,NIF,'335429106'",
                "PT,NIPC,'690798016'",
                "PY,CI,'1234567'",
                "PY,RUC,'2899059-5'",
                "RU,INN_INDIVIDUAL,'246964567008'",
                "RU,INN_LEGAL_ENTITY,'0305773929'",
                "SE,ORGANISATIONSNUMMER,'2836185831'",
                "SE,PERSONNUMMER,'811211-2225'",
                "SI,DAVCNA_STEVILKA,'15012557'",
                "TH,NATIONAL_ID,'1101230000001'",
                "UA,RNOKPP,'3006321856'",
                "US,EIN,'01-1234567'",
                "US,ITIN,'912-70-1234'",
                "US,SSN,'123-45-6789'",
                "UY,CI,'1.234.567-2'",
                "UY,RUT,'164121540017'",
                "ZA,CIPC,'2015/123456/07'",
                "ZA,ID_NUMBER,'5810164397017'"
            ]
        )
        fun `given a valid document of its country and type should accept it`(
            country: String,
            type: String,
            document: String
        ) {
            val node = ValidationNode(path = "taxId", value = document).apply { taxId(country = country, type = type) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a document in lowercase country and type should accept it`() {
            val node = ValidationNode(path = "taxId", value = "20-30824628-1").apply {
                taxId(country = "ar", type = "cuit")
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @CsvSource(
            value = [
                "AE_EMIRATES_ID,'784196865703050'",
                "AE_TRADE_LICENSE,'CN-1234567'",
                "AR_CUIL,'20308246281'",
                "AR_CUIT,'33301661318'",
                "AU_ABN,'53710731177'",
                "AU_TFN,'468344386'",
                "BE_BCE,'0661488530'",
                "BE_NATIONAL_NUMBER,'74022569427'",
                "BG_EGN,'7501010010'",
                "BR_CNPJ,'11.222.333/0001-81'",
                "BR_CPF,'52998224725'",
                "CA_BN,'235396504'",
                "CA_SIN,'521719666'",
                "CH_AHV,'7564699338286'",
                "CH_UID,'CHE785278205'",
                "CL_RUT,'84268465-K'",
                "CN_USCC,'91927469EKP84HN3YJ'",
                "CO_CC,'1020304050'",
                "CO_NIT,'9002158108'",
                "DE_STEUER_ID,'84159263274'",
                "DE_UST_ID_NR,'894753402'",
                "ES_CIF,'B7905597F'",
                "ES_DNI,'12345678Z'",
                "ES_NIE,'X1234567L'",
                "FR_SIREN,'934461799'",
                "FR_SIRET,'82247920095500'",
                "FR_SPI,'3023217600053'",
                "GB_NINO,'AB123456C'",
                "GB_VAT,'385599877'",
                "IL_COMPANY_NUMBER,'521387803'",
                "IL_TEUDAT_ZEHUT,'219472156'",
                "IN_GSTIN,'10GXMFD3255G2ZB'",
                "IN_PAN,'ABCPE1234F'",
                "IT_CODICE_FISCALE,'XUFIAX24L20X122G'",
                "IT_PARTITA_IVA,'55396190013'",
                "JP_CORPORATE_NUMBER,'7785935252181'",
                "JP_MY_NUMBER,'695017077064'",
                "KR_BRN,'1348672683'",
                "KR_RRN,'5605231067971'",
                "MX_RFC,'GODE561231GR8'",
                "NL_BSN,'174559434'",
                "NL_KVK,'69599084'",
                "NO_FODSELSNUMMER,'19045128175'",
                "NO_ORGANISASJONSNUMMER,'913885821'",
                "PE_DNI,'99530552'",
                "PE_RUC,'20654444634'",
                "PK_CNIC,'45504-4185771-3'",
                "PL_NIP,'2234567895'",
                "PL_PESEL,'02070803628'",
                "PT_NIF,'335429106'",
                "PT_NIPC,'690798016'",
                "PY_CI,'1234567'",
                "PY_RUC,'2899059-5'",
                "RU_INN_INDIVIDUAL,'246964567008'",
                "RU_INN_LEGAL_ENTITY,'0305773929'",
                "SE_ORGANISATIONSNUMMER,'2836185831'",
                "SE_PERSONNUMMER,'811211-2225'",
                "SI_DAVCNA_STEVILKA,'15012557'",
                "TH_NATIONAL_ID,'1101230000001'",
                "UA_RNOKPP,'3006321856'",
                "US_EIN,'01-1234567'",
                "US_ITIN,'912-70-1234'",
                "US_SSN,'123-45-6789'",
                "UY_CI,'1.234.567-2'",
                "UY_RUT,'164121540017'",
                "ZA_CIPC,'2015/123456/07'",
                "ZA_ID_NUMBER,'5810164397017'"
            ]
        )
        fun `given a valid document of its type should accept it`(
            type: TaxIdType,
            document: String
        ) {
            val node = ValidationNode(path = "taxId", value = document).apply { taxId(type) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @ParameterizedTest
        @CsvSource(
            value = [
                "AR,CUIT,'20-30824628-0'",
                "AR,CUIL,'30091390996'",
                "BR,CPF,'52998224724'",
                "PT,NIF,'690798016'",
                "IT,PARTITA_IVA,'00743110158'",
                "US,SSN,'666-12-3456'"
            ]
        )
        fun `given an invalid document of its country and type should report it`(
            country: String,
            type: String,
            document: String
        ) {
            val node = ValidationNode(path = "taxId", value = document).apply { taxId(country = country, type = type) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "taxId", message = "must be a valid $type for $country", code = "taxId")
            )
        }

        @Test
        fun `given a document of another country should report it`() {
            val node = ValidationNode(path = "taxId", value = "52998224725").apply {
                taxId(country = "AR", type = "CUIT")
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "taxId", message = "must be a valid CUIT for AR", code = "taxId")
            )
        }

        @ParameterizedTest
        @EnumSource(TaxIdType::class)
        fun `given any type and a document that is not one should report it`(
            type: TaxIdType
        ) {
            val node = ValidationNode(path = "taxId", value = "?").apply { taxId(type) }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "taxId", message = "must be a valid ${type.type} for ${type.country}", code = "taxId")
            )
        }

        @Test
        fun `given no country should skip the check`() {
            val node = ValidationNode(path = "taxId", value = "anything").apply { taxId(country = null, type = "CUIT") }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no type should skip the check`() {
            val node = ValidationNode(path = "taxId", value = "anything").apply { taxId(country = "AR", type = null) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a type the country does not have should skip the check`() {
            val node = ValidationNode(path = "taxId", value = "anything").apply { taxId(country = "AR", type = "CPF") }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a country without known documents should skip the check`() {
            val node = ValidationNode(path = "taxId", value = "anything").apply { taxId(country = "AQ", type = "CUIT") }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given no type of document should skip the check`() {
            val node = ValidationNode(path = "taxId", value = "anything").apply { taxId(type = null) }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given an unknown pair inside not should not count as a rule`() {
            val node = ValidationNode(path = "taxId", value = "anything").apply {
                not { taxId(country = "AQ", type = "CUIT") }
            }

            val violations = node.validate()

            violations shouldBe emptyList()
        }

        @Test
        fun `given a message function should build it from the value, the country and the type`() {
            val node = ValidationNode(path = "taxId", value = "123").apply {
                taxId(country = "AR", type = "CUIT") { country, type -> "$this is not a $type of $country" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "taxId", message = "123 is not a CUIT of AR", code = "taxId")
            )
        }

        @Test
        fun `given a message function and a type should build it from the value and the type`() {
            val node = ValidationNode(path = "taxId", value = "123").apply {
                taxId(TaxIdType.BR_CPF) { type -> "$this is not a ${type.type} of ${type.country}" }
            }

            val violations = node.validate()

            violations shouldBe listOf(
                Violation(path = "taxId", message = "123 is not a CPF of BR", code = "taxId")
            )
        }
    }
}
