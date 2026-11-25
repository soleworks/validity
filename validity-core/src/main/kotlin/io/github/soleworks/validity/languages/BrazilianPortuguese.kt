package io.github.soleworks.validity.languages

import io.github.soleworks.validity.Messages

internal val BRAZILIAN_PORTUGUESE: Messages.() -> Unit = {
    required = "é obrigatório"
    forbidden = "deve ser nulo"
    atLeastOneOf = "pelo menos um de {fields} é obrigatório"
    atMostOneOf = "no máximo um de {fields} pode ser preenchido"
    exactlyOneOf = "exatamente um de {fields} deve ser preenchido"
    allOrNoneOf = "{fields} devem ser preenchidos juntos"
    atLeastOf = "pelo menos {count} de {fields} devem ser preenchidos"
    atMostOf = "no máximo {count} de {fields} podem ser preenchidos"
    exactlyOf = "exatamente {count} de {fields} devem ser preenchidos"
    equalTo = "deve ser igual a {other}"
    notEqualTo = "não deve ser igual a {other}"
    oneOf = "deve ser um de {values}"
    enum = "deve ser um de {values}"
    noneOf = "não deve ser um de {values}"
    or = "{left} ou {right}"
    and = "{left} e {right}"
    not = "não é permitido"
    convert = "tem um formato inválido"
    convertToBigDecimal = "deve ser um número válido"
    convertToInt = "deve ser um número inteiro válido"
    convertToLong = "deve ser um número inteiro válido"
    convertToBigInteger = "deve ser um número inteiro válido"
    convertToDouble = "deve ser um número válido"
    convertToBoolean = "deve ser true ou false"
    convertToUuid = "deve ser um UUID válido"
    convertToEnum = "deve ser um de {values}"
    convertToLocalDate = "deve ser uma data ISO 8601 válida"
    convertToLocalDateTime = "deve ser uma data e hora ISO 8601 válidas"
    convertToLocalTime = "deve ser uma hora ISO 8601 válida"
    convertToOffsetDateTime = "deve ser uma data e hora ISO 8601 válidas, com deslocamento UTC"
    convertToInstant = "deve ser um instante ISO 8601 válido"
    convertToYearMonth = "deve ser um ano e mês ISO 8601 válidos"
    convertToDuration = "deve ser uma duração ISO 8601 válida"
    mustBeTrue = "deve ser verdadeiro"
    mustBeFalse = "deve ser falso"
    min = "deve ser no mínimo {min}"
    max = "deve ser no máximo {max}"
    greaterThan = "deve ser maior que {other}"
    lessThan = "deve ser menor que {other}"
    between = "deve estar entre {min} e {max}"
    minLength = "deve ter pelo menos {min} caracteres"
    maxLength = "deve ter no máximo {max} caracteres"
    length = "deve ter exatamente {length} caracteres"
    lengthBetween = "deve ter entre {min} e {max} caracteres"
    minBytes = "deve ter pelo menos {min} bytes"
    maxBytes = "deve ter no máximo {max} bytes"
    notEmpty = "não deve estar vazio"
    empty = "deve estar vazio"
    notBlank = "não deve estar em branco"
    blank = "deve estar em branco"
    trimmed = "não deve começar nem terminar com espaços"
    matches = "deve corresponder a {regex}"
    notMatches = "não deve corresponder a {regex}"
    contains = "deve conter {text}"
    notContains = "não deve conter {text}"
    startsWith = "deve começar com {prefix}"
    notStartsWith = "não deve começar com {prefix}"
    endsWith = "deve terminar com {suffix}"
    notEndsWith = "não deve terminar com {suffix}"
    uppercase = "deve estar em maiúsculas"
    lowercase = "deve estar em minúsculas"
    letters = "deve conter apenas letras"
    digits = "deve conter apenas dígitos"
    lettersOrDigits = "deve conter apenas letras ou dígitos"
    ascii = "deve conter apenas caracteres ASCII"
    containsOnly = "deve conter apenas os caracteres {characters}"
    normalized = "deve estar na forma Unicode {form}"
    positive = "deve ser positivo"
    negative = "deve ser negativo"
    positiveOrZero = "deve ser positivo ou zero"
    negativeOrZero = "deve ser negativo ou zero"
    multipleOf = "deve ser múltiplo de {factor}"
    finite = "deve ser finito"
    maxDecimalPlaces = "deve ter no máximo {max} casas decimais"
    maxIntegerDigits = "deve ter no máximo {max} dígitos inteiros"
    decimalPlaces = "deve ter exatamente {count} casas decimais"
    minDecimalPlaces = "deve ter pelo menos {min} casas decimais"
    minDigits = "deve ter pelo menos {min} dígitos"
    maxDigits = "deve ter no máximo {max} dígitos"
    digitCount = "deve ter exatamente {count} dígitos"
    past = "deve estar no passado"
    future = "deve estar no futuro"
    pastOrPresent = "deve estar no passado ou no presente"
    futureOrPresent = "deve estar no futuro ou no presente"
    after = "deve ser depois de {other}"
    before = "deve ser antes de {other}"
    afterOrEqual = "deve ser igual ou depois de {other}"
    beforeOrEqual = "deve ser igual ou antes de {other}"
    betweenDates = "deve estar entre {start} e {end}"
    minAge = "deve ter pelo menos {min} anos"
    maxAge = "deve ter no máximo {max} anos"
    withinNext = "deve estar dentro dos próximos {duration}"
    withinLast = "deve estar dentro dos últimos {duration}"
    today = "deve ser hoje"
    weekday = "deve ser um dia útil"
    weekend = "deve ser em um fim de semana"
    minSize = "deve ter pelo menos {min} itens"
    maxSize = "deve ter no máximo {max} itens"
    size = "deve ter exatamente {size} itens"
    sizeBetween = "deve ter entre {min} e {max} itens"
    distinct = "não deve conter itens repetidos"
    containsElement = "deve conter {element}"
    notContainsElement = "não deve conter {element}"
    containsAll = "deve conter todos os itens de {elements}"
    containsAny = "deve conter algum item de {elements}"
    containsNone = "não deve conter nenhum item de {elements}"
    minEntries = "deve ter pelo menos {min} entradas"
    maxEntries = "deve ter no máximo {max} entradas"
    entries = "deve ter exatamente {size} entradas"
    entriesBetween = "deve ter entre {min} e {max} entradas"
    containsKey = "deve conter a chave {key}"
    containsKeys = "deve conter as chaves {keys}"
    notContainsKey = "não deve conter a chave {key}"
    onlyKeys = "deve conter apenas as chaves {keys}"
    containsValue = "deve conter o valor {other}"
    email = "deve ser um e-mail válido"
    url = "deve ser uma URL válida"
    hostname = "deve ser um nome de host válido"
    domain = "deve ser um nome de domínio válido"
    port = "deve ser um número de porta válido"
    ipv4 = "deve ser um endereço IPv4 válido"
    ipv6 = "deve ser um endereço IPv6 válido"
    ip = "deve ser um endereço IP válido"
    cidr = "deve ser um bloco CIDR válido"
    macAddress = "deve ser um endereço MAC válido"
    slug = "deve ser um slug válido"
    jwt = "deve ser um JWT válido"
    dataUri = "deve ser um URI de dados válido"
    mimeType = "deve ser um tipo MIME válido"
    uuid = "deve ser um UUID válido"
    ulid = "deve ser um ULID válido"
    objectId = "deve ser um ObjectId válido"
    semver = "deve ser uma versão semântica válida"
    isbn = "deve ser um ISBN válido"
    isbn10 = "deve ser um ISBN-10 válido"
    isbn13 = "deve ser um ISBN-13 válido"
    issn = "deve ser um ISSN válido"
    ean = "deve ser um EAN válido"
    isrc = "deve ser um ISRC válido"
    imei = "deve ser um IMEI válido"
    luhn = "deve ter um dígito verificador de Luhn válido"
    hash = "deve ser um hash {algorithm} válido"
    base64 = "deve ser Base64 válido"
    base64Url = "deve ser Base64 válido para URL"
    base32 = "deve ser Base32 válido"
    base58 = "deve ser Base58 válido"
    hexadecimal = "deve ser hexadecimal"
    json = "deve ser JSON válido"
    hexColor = "deve ser uma cor hexadecimal válida"
    rgbColor = "deve ser uma cor RGB válida"
    hslColor = "deve ser uma cor HSL válida"
    creditCard = "deve ser um número de cartão de crédito válido"
    iban = "deve ser um IBAN válido"
    bic = "deve ser um BIC válido"
    isin = "deve ser um ISIN válido"
    currencyCode = "deve ser um código de moeda ISO 4217 válido"
    bitcoinAddress = "deve ser um endereço Bitcoin válido"
    ethereumAddress = "deve ser um endereço Ethereum válido"
    isoCountryCode = "deve ser um código de país ISO 3166-1 alfa-2 válido"
    isoCountryCodeAlpha3 = "deve ser um código de país ISO 3166-1 alfa-3 válido"
    languageCode = "deve ser um código de idioma ISO 639-1 válido"
    locale = "deve ser uma localidade válida"
    timeZone = "deve ser um fuso horário válido"
    latitude = "deve ser uma latitude válida"
    longitude = "deve ser uma longitude válida"
    coordinates = "deve ser um par de latitude e longitude válido"
    postalCode = "deve ser um código postal válido para {country}"
    taxId = "deve ser um {type} válido para {country}"
    numeric = "deve ser numérico"
    integer = "deve ser um número inteiro"
    boolean = "deve ser true ou false"
    containsUppercase = "deve conter uma letra maiúscula"
    containsLowercase = "deve conter uma letra minúscula"
    containsDigit = "deve conter um dígito"
    containsSymbol = "deve conter um símbolo"
    isoDate = "deve ser uma data ISO 8601"
    isoTime = "deve ser uma hora ISO 8601"
    isoDateTime = "deve ser uma data e hora ISO 8601 com deslocamento UTC"
    isoDuration = "deve ser uma duração ISO 8601"
    dateFormat = "deve seguir o formato de data {pattern}"

    argentina {
        cuit = "deve ser um CUIT válido"
        cuil = "deve ser um CUIL válido"
    }
    australia {
        abn = "deve ser um ABN válido"
        tfn = "deve ser um TFN válido"
    }
    belgium {
        bce = "deve ser um número de empresa válido"
        nationalNumber = "deve ser um número nacional válido"
    }
    brazil {
        cpf = "deve ser um CPF válido"
        cnpj = "deve ser um CNPJ válido"
        cnh = "deve ser uma CNH válida"
        pis = "deve ser um PIS válido"
        cep = "deve ser um CEP válido"
        placa = "deve ser uma placa válida"
    }
    bulgaria { egn = "deve ser um EGN válido" }
    canada {
        sin = "deve ser um SIN válido"
        bn = "deve ser um número de empresa válido"
    }
    chile { rut = "deve ser um RUT válido" }
    china { uscc = "deve ser um código unificado de crédito social válido" }
    colombia {
        nit = "deve ser um NIT válido"
        cc = "deve ser uma CC válida"
    }
    france {
        spi = "deve ser um SPI válido"
        siren = "deve ser um SIREN válido"
        siret = "deve ser um SIRET válido"
    }
    germany {
        ustIdNr = "deve ser um USt-IdNr. válido"
        steuerId = "deve ser um Steuer-ID válido"
    }
    india {
        pan = "deve ser um PAN válido"
        gstin = "deve ser um GSTIN válido"
    }
    israel {
        teudatZehut = "deve ser um teudat zehut válido"
        companyNumber = "deve ser um número de empresa válido"
    }
    italy {
        partitaIva = "deve ser uma partita IVA válida"
        codiceFiscale = "deve ser um codice fiscale válido"
    }
    japan {
        corporateNumber = "deve ser um número corporativo válido"
        myNumber = "deve ser um My Number válido"
    }
    mexico { rfc = "deve ser um RFC válido" }
    netherlands {
        bsn = "deve ser um BSN válido"
        kvk = "deve ser um número KvK válido"
    }
    norway {
        organisasjonsnummer = "deve ser um organisasjonsnummer válido"
        fodselsnummer = "deve ser um fødselsnummer válido"
    }
    pakistan { cnic = "deve ser um CNIC válido" }
    paraguay {
        ruc = "deve ser um RUC válido"
        ci = "deve ser uma CI válida"
    }
    peru {
        ruc = "deve ser um RUC válido"
        dni = "deve ser um DNI válido"
    }
    poland {
        pesel = "deve ser um PESEL válido"
        nip = "deve ser um NIP válido"
    }
    portugal {
        nif = "deve ser um NIF válido"
        nipc = "deve ser um NIPC válido"
    }
    russia {
        innIndividual = "deve ser um INN de pessoa física válido"
        innLegalEntity = "deve ser um INN de pessoa jurídica válido"
    }
    slovenia { davcnaStevilka = "deve ser uma davčna številka válida" }
    southAfrica {
        cipc = "deve ser um número de registro de empresa válido"
        idNumber = "deve ser um número de identidade válido"
    }
    southKorea {
        brn = "deve ser um número de registro comercial válido"
        rrn = "deve ser um número de registro de residente válido"
    }
    spain {
        dni = "deve ser um DNI válido"
        nie = "deve ser um NIE válido"
        cif = "deve ser um CIF válido"
    }
    sweden {
        organisationsnummer = "deve ser um organisationsnummer válido"
        personnummer = "deve ser um personnummer válido"
    }
    switzerland {
        uid = "deve ser um UID válido"
        ahv = "deve ser um número AHV válido"
    }
    thailand { nationalId = "deve ser um número de identificação nacional válido" }
    ukraine { rnokpp = "deve ser um RNOKPP válido" }
    unitedArabEmirates {
        tradeLicense = "deve ser um número de licença comercial válido"
        emiratesId = "deve ser um Emirates ID válido"
    }
    unitedKingdom {
        vat = "deve ser um número de VAT válido"
        nino = "deve ser um número de National Insurance válido"
    }
    unitedStates {
        ein = "deve ser um EIN válido"
        ssn = "deve ser um SSN válido"
        itin = "deve ser um ITIN válido"
    }
    uruguay {
        rut = "deve ser um RUT válido"
        ci = "deve ser uma CI válida"
    }
}
