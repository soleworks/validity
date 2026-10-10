package io.github.soleworks.validity.constraints.brazil

import io.github.soleworks.validity.ValidationNode
import io.github.soleworks.validity.Violation
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class BrazilConstraintsTest {
    @Nested
    @DisplayName("When cpf is called")
    inner class Cpf {
        @ParameterizedTest
        @ValueSource(strings = ["52998224725", "529.982.247-25"])
        fun `given a CPF with valid check digits should accept it`(cpf: String) {
            val node = ValidationNode("cpf", cpf).apply { cpf() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["529.982.247-26", "111.111.111-11", "529982247-25", "5299822472", "529.982.247-2A"])
        fun `given a CPF with wrong check digits, repeated digits or another format should report it`(cpf: String) {
            val node = ValidationNode("cpf", cpf).apply { cpf() }

            node.validate() shouldBe listOf(Violation("cpf", "must be a valid CPF", "cpf"))
        }
    }

    @Nested
    @DisplayName("When cnpj is called")
    inner class Cnpj {
        @ParameterizedTest
        @ValueSource(strings = ["11222333000181", "11.222.333/0001-81", "12ABC34501DE35", "12.ABC.345/01DE-35"])
        fun `given a numeric or alphanumeric CNPJ with valid check digits should accept it`(cnpj: String) {
            val node = ValidationNode("cnpj", cnpj).apply { cnpj() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(
            strings = [
                "11.222.333/0001-82",
                "12ABC34501DE36",
                "00000000000000",
                "12abc34501de35",
                "11.222.333/000181"
            ]
        )
        fun `given a CNPJ with wrong check digits, only zeros or another format should report it`(cnpj: String) {
            val node = ValidationNode("cnpj", cnpj).apply { cnpj() }

            node.validate() shouldBe listOf(Violation("cnpj", "must be a valid CNPJ", "cnpj"))
        }
    }

    @Nested
    @DisplayName("When cnh is called")
    inner class Cnh {
        @ParameterizedTest
        @ValueSource(strings = ["12345678900", "98765432109", "10000000108"])
        fun `given a CNH with valid check digits should accept it`(cnh: String) {
            val node = ValidationNode("cnh", cnh).apply { cnh() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["12345678901", "11111111111", "1234567890", "123456789-00"])
        fun `given a CNH with wrong check digits, repeated digits or another format should report it`(cnh: String) {
            val node = ValidationNode("cnh", cnh).apply { cnh() }

            node.validate() shouldBe listOf(Violation("cnh", "must be a valid CNH", "cnh"))
        }
    }

    @Nested
    @DisplayName("When pis is called")
    inner class Pis {
        @ParameterizedTest
        @ValueSource(strings = ["12037173832", "120.37173.83-2", "17000000005"])
        fun `given a PIS with a valid check digit should accept it`(pis: String) {
            val node = ValidationNode("pis", pis).apply { pis() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["12037173833", "11111111111", "120.371.738-32", "1203717383"])
        fun `given a PIS with a wrong check digit, repeated digits or another format should report it`(pis: String) {
            val node = ValidationNode("pis", pis).apply { pis() }

            node.validate() shouldBe listOf(Violation("pis", "must be a valid PIS", "pis"))
        }
    }

    @Nested
    @DisplayName("When tituloEleitoral is called")
    inner class TituloEleitoral {
        @ParameterizedTest
        @ValueSource(strings = ["102385010671", "004356870906", "100000010116", "100000010302"])
        fun `given a título eleitoral with valid check digits should accept it`(tituloEleitoral: String) {
            val node = ValidationNode("tituloEleitoral", tituloEleitoral).apply { tituloEleitoral() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["102385010672", "100000010016", "100000012906", "1023 8501 0671", "10238501067"])
        fun `given a título eleitoral with wrong check digits, an unknown state or another format should report it`(
            tituloEleitoral: String
        ) {
            val node = ValidationNode("tituloEleitoral", tituloEleitoral).apply { tituloEleitoral() }

            node.validate() shouldBe listOf(Violation("tituloEleitoral", "must be a valid título eleitoral", "tituloEleitoral"))
        }
    }

    @Nested
    @DisplayName("When chaveNfe is called")
    inner class ChaveNfe {
        @ParameterizedTest
        @ValueSource(strings = ["35230612345678000190550010000000011000000008"])
        fun `given an NF-e access key with a valid check digit should accept it`(chaveNfe: String) {
            val node = ValidationNode("chaveNfe", chaveNfe).apply { chaveNfe() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(
            strings = [
                "35230612345678000190550010000000011000000009",
                "3523061234567800019055001000000001100000000"
            ]
        )
        fun `given an NF-e access key with a wrong check digit or length should report it`(chaveNfe: String) {
            val node = ValidationNode("chaveNfe", chaveNfe).apply { chaveNfe() }

            node.validate() shouldBe listOf(Violation("chaveNfe", "must be a valid NF-e access key", "chaveNfe"))
        }
    }

    @Nested
    @DisplayName("When cep is called")
    inner class Cep {
        @ParameterizedTest
        @ValueSource(strings = ["01310100", "01310-100"])
        fun `given a CEP with 8 digits should accept it`(cep: String) {
            val node = ValidationNode("cep", cep).apply { cep() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["1310-100", "01310-10", "01.310-100", "0131010A"])
        fun `given a CEP in another format should report it`(cep: String) {
            val node = ValidationNode("cep", cep).apply { cep() }

            node.validate() shouldBe listOf(Violation("cep", "must be a valid CEP", "cep"))
        }
    }

    @Nested
    @DisplayName("When placa is called")
    inner class Placa {
        @ParameterizedTest
        @ValueSource(strings = ["ABC1234", "ABC-1234", "ABC1D23"])
        fun `given an old or Mercosul license plate should accept it`(placa: String) {
            val node = ValidationNode("placa", placa).apply { placa() }

            node.validate() shouldBe emptyList()
        }

        @ParameterizedTest
        @ValueSource(strings = ["AB1234", "abc1234", "ABC12345", "ABCD123"])
        fun `given a license plate in another format should report it`(placa: String) {
            val node = ValidationNode("placa", placa).apply { placa() }

            node.validate() shouldBe listOf(Violation("placa", "must be a valid license plate", "placa"))
        }
    }
}
