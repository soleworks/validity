package io.github.soleworks.validity.languages

import io.github.soleworks.validity.Messages

internal val SPANISH: Messages.() -> Unit = {
    required = "es obligatorio"
    forbidden = "debe ser nulo"
    atLeastOneOf = "al menos uno de {fields} es obligatorio"
    atMostOneOf = "como máximo uno de {fields} puede completarse"
    exactlyOneOf = "exactamente uno de {fields} debe completarse"
    allOrNoneOf = "{fields} deben completarse juntos"
    atLeastOf = "al menos {count} de {fields} deben completarse"
    atMostOf = "como máximo {count} de {fields} pueden completarse"
    exactlyOf = "exactamente {count} de {fields} deben completarse"
    equalTo = "debe ser igual a {other}"
    notEqualTo = "no debe ser igual a {other}"
    oneOf = "debe ser uno de {values}"
    enum = "debe ser uno de {values}"
    noneOf = "no debe ser uno de {values}"
    or = "{left} o {right}"
    and = "{left} y {right}"
    not = "no está permitido"
    convert = "tiene un formato no válido"
    convertToBigDecimal = "debe ser un número válido"
    convertToInt = "debe ser un número entero válido"
    convertToLong = "debe ser un número entero válido"
    convertToBigInteger = "debe ser un número entero válido"
    convertToDouble = "debe ser un número válido"
    convertToBoolean = "debe ser true o false"
    convertToUuid = "debe ser un UUID válido"
    convertToEnum = "debe ser uno de {values}"
    convertToLocalDate = "debe ser una fecha ISO 8601 válida"
    convertToLocalDateTime = "debe ser una fecha y hora ISO 8601 válidas"
    convertToLocalTime = "debe ser una hora ISO 8601 válida"
    convertToOffsetDateTime = "debe ser una fecha y hora ISO 8601 válidas, con desplazamiento UTC"
    convertToInstant = "debe ser un instante ISO 8601 válido"
    convertToYearMonth = "debe ser un año y mes ISO 8601 válidos"
    convertToDuration = "debe ser una duración ISO 8601 válida"
    mustBeTrue = "debe ser verdadero"
    mustBeFalse = "debe ser falso"
    min = "debe ser como mínimo {min}"
    max = "debe ser como máximo {max}"
    greaterThan = "debe ser mayor que {other}"
    lessThan = "debe ser menor que {other}"
    between = "debe estar entre {min} y {max}"
    minLength = "debe tener al menos {min} caracteres"
    maxLength = "debe tener como máximo {max} caracteres"
    length = "debe tener exactamente {length} caracteres"
    lengthBetween = "debe tener entre {min} y {max} caracteres"
    minBytes = "debe tener al menos {min} bytes"
    maxBytes = "debe tener como máximo {max} bytes"
    notEmpty = "no debe estar vacío"
    empty = "debe estar vacío"
    notBlank = "no debe estar en blanco"
    blank = "debe estar en blanco"
    trimmed = "no debe empezar ni terminar con espacios"
    matches = "debe coincidir con {regex}"
    notMatches = "no debe coincidir con {regex}"
    contains = "debe contener {text}"
    notContains = "no debe contener {text}"
    startsWith = "debe empezar con {prefix}"
    notStartsWith = "no debe empezar con {prefix}"
    endsWith = "debe terminar con {suffix}"
    notEndsWith = "no debe terminar con {suffix}"
    uppercase = "debe estar en mayúsculas"
    lowercase = "debe estar en minúsculas"
    letters = "debe contener solo letras"
    digits = "debe contener solo dígitos"
    lettersOrDigits = "debe contener solo letras o dígitos"
    ascii = "debe contener solo caracteres ASCII"
    containsOnly = "debe contener solo los caracteres {characters}"
    normalized = "debe estar en la forma Unicode {form}"
    positive = "debe ser positivo"
    negative = "debe ser negativo"
    positiveOrZero = "debe ser positivo o cero"
    negativeOrZero = "debe ser negativo o cero"
    multipleOf = "debe ser múltiplo de {factor}"
    finite = "debe ser finito"
    maxDecimalPlaces = "debe tener como máximo {max} decimales"
    maxIntegerDigits = "debe tener como máximo {max} dígitos enteros"
    decimalPlaces = "debe tener exactamente {count} decimales"
    minDecimalPlaces = "debe tener al menos {min} decimales"
    minDigits = "debe tener al menos {min} dígitos"
    maxDigits = "debe tener como máximo {max} dígitos"
    digitCount = "debe tener exactamente {count} dígitos"
    past = "debe estar en el pasado"
    future = "debe estar en el futuro"
    pastOrPresent = "debe estar en el pasado o el presente"
    futureOrPresent = "debe estar en el futuro o el presente"
    after = "debe ser posterior a {other}"
    before = "debe ser anterior a {other}"
    afterOrEqual = "debe ser igual o posterior a {other}"
    beforeOrEqual = "debe ser igual o anterior a {other}"
    betweenDates = "debe estar entre {start} y {end}"
    minAge = "debe tener al menos {min} años"
    maxAge = "debe tener como máximo {max} años"
    withinNext = "debe estar dentro de los próximos {duration}"
    withinLast = "debe estar dentro de los últimos {duration}"
    today = "debe ser hoy"
    weekday = "debe ser un día laborable"
    weekend = "debe ser en fin de semana"
    minSize = "debe tener al menos {min} elementos"
    maxSize = "debe tener como máximo {max} elementos"
    size = "debe tener exactamente {size} elementos"
    sizeBetween = "debe tener entre {min} y {max} elementos"
    distinct = "no debe contener elementos repetidos"
    containsElement = "debe contener {element}"
    notContainsElement = "no debe contener {element}"
    containsAll = "debe contener todos los elementos de {elements}"
    containsAny = "debe contener alguno de {elements}"
    containsNone = "no debe contener ninguno de {elements}"
    minEntries = "debe tener al menos {min} entradas"
    maxEntries = "debe tener como máximo {max} entradas"
    entries = "debe tener exactamente {size} entradas"
    entriesBetween = "debe tener entre {min} y {max} entradas"
    containsKey = "debe contener la clave {key}"
    containsKeys = "debe contener las claves {keys}"
    notContainsKey = "no debe contener la clave {key}"
    onlyKeys = "debe contener solo las claves {keys}"
    containsValue = "debe contener el valor {other}"
    email = "debe ser un correo electrónico válido"
    url = "debe ser una URL válida"
    hostname = "debe ser un nombre de host válido"
    domain = "debe ser un nombre de dominio válido"
    port = "debe ser un número de puerto válido"
    ipv4 = "debe ser una dirección IPv4 válida"
    ipv6 = "debe ser una dirección IPv6 válida"
    ip = "debe ser una dirección IP válida"
    cidr = "debe ser un bloque CIDR válido"
    macAddress = "debe ser una dirección MAC válida"
    slug = "debe ser un slug válido"
    jwt = "debe ser un JWT válido"
    dataUri = "debe ser un URI de datos válido"
    mimeType = "debe ser un tipo MIME válido"
    uuid = "debe ser un UUID válido"
    ulid = "debe ser un ULID válido"
    objectId = "debe ser un ObjectId válido"
    semver = "debe ser una versión semántica válida"
    isbn = "debe ser un ISBN válido"
    isbn10 = "debe ser un ISBN-10 válido"
    isbn13 = "debe ser un ISBN-13 válido"
    issn = "debe ser un ISSN válido"
    ean = "debe ser un EAN válido"
    isrc = "debe ser un ISRC válido"
    imei = "debe ser un IMEI válido"
    luhn = "debe tener un dígito de control de Luhn válido"
    hash = "debe ser un hash {algorithm} válido"
    base64 = "debe ser Base64 válido"
    base64Url = "debe ser Base64 válido para URL"
    base32 = "debe ser Base32 válido"
    base58 = "debe ser Base58 válido"
    hexadecimal = "debe ser hexadecimal"
    json = "debe ser JSON válido"
    hexColor = "debe ser un color hexadecimal válido"
    rgbColor = "debe ser un color RGB válido"
    hslColor = "debe ser un color HSL válido"
    creditCard = "debe ser un número de tarjeta de crédito válido"
    iban = "debe ser un IBAN válido"
    bic = "debe ser un BIC válido"
    isin = "debe ser un ISIN válido"
    currencyCode = "debe ser un código de moneda ISO 4217 válido"
    bitcoinAddress = "debe ser una dirección Bitcoin válida"
    ethereumAddress = "debe ser una dirección Ethereum válida"
    isoCountryCode = "debe ser un código de país ISO 3166-1 alfa-2 válido"
    isoCountryCodeAlpha3 = "debe ser un código de país ISO 3166-1 alfa-3 válido"
    languageCode = "debe ser un código de idioma ISO 639-1 válido"
    locale = "debe ser una configuración regional válida"
    timeZone = "debe ser una zona horaria válida"
    latitude = "debe ser una latitud válida"
    longitude = "debe ser una longitud válida"
    coordinates = "debe ser un par de latitud y longitud válido"
    postalCode = "debe ser un código postal válido para {country}"
    taxId = "debe ser un {type} válido para {country}"
    numeric = "debe ser numérico"
    integer = "debe ser un número entero"
    boolean = "debe ser true o false"
    containsUppercase = "debe contener una letra mayúscula"
    containsLowercase = "debe contener una letra minúscula"
    containsDigit = "debe contener un dígito"
    containsSymbol = "debe contener un símbolo"
    isoDate = "debe ser una fecha ISO 8601"
    isoTime = "debe ser una hora ISO 8601"
    isoDateTime = "debe ser una fecha y hora ISO 8601 con desplazamiento UTC"
    isoDuration = "debe ser una duración ISO 8601"
    dateFormat = "debe seguir el formato de fecha {pattern}"

    argentina {
        cuit = "debe ser un CUIT válido"
        cuil = "debe ser un CUIL válido"
    }
    australia {
        abn = "debe ser un ABN válido"
        tfn = "debe ser un TFN válido"
    }
    belgium {
        bce = "debe ser un número de empresa válido"
        nationalNumber = "debe ser un número nacional válido"
    }
    brazil {
        cpf = "debe ser un CPF válido"
        cnpj = "debe ser un CNPJ válido"
        cnh = "debe ser una CNH válida"
        pis = "debe ser un PIS válido"
        cep = "debe ser un CEP válido"
        placa = "debe ser una matrícula válida"
    }
    bulgaria { egn = "debe ser un EGN válido" }
    canada {
        sin = "debe ser un SIN válido"
        bn = "debe ser un número de empresa válido"
    }
    chile { rut = "debe ser un RUT válido" }
    china { uscc = "debe ser un código unificado de crédito social válido" }
    colombia {
        nit = "debe ser un NIT válido"
        cc = "debe ser una CC válida"
    }
    france {
        spi = "debe ser un SPI válido"
        siren = "debe ser un SIREN válido"
        siret = "debe ser un SIRET válido"
    }
    germany {
        ustIdNr = "debe ser un USt-IdNr. válido"
        steuerId = "debe ser un Steuer-ID válido"
    }
    india {
        pan = "debe ser un PAN válido"
        gstin = "debe ser un GSTIN válido"
    }
    israel {
        teudatZehut = "debe ser un teudat zehut válido"
        companyNumber = "debe ser un número de empresa válido"
    }
    italy {
        partitaIva = "debe ser una partita IVA válida"
        codiceFiscale = "debe ser un codice fiscale válido"
    }
    japan {
        corporateNumber = "debe ser un número corporativo válido"
        myNumber = "debe ser un My Number válido"
    }
    mexico { rfc = "debe ser un RFC válido" }
    netherlands {
        bsn = "debe ser un BSN válido"
        kvk = "debe ser un número KvK válido"
    }
    norway {
        organisasjonsnummer = "debe ser un organisasjonsnummer válido"
        fodselsnummer = "debe ser un fødselsnummer válido"
    }
    pakistan { cnic = "debe ser un CNIC válido" }
    paraguay {
        ruc = "debe ser un RUC válido"
        ci = "debe ser una CI válida"
    }
    peru {
        ruc = "debe ser un RUC válido"
        dni = "debe ser un DNI válido"
    }
    poland {
        pesel = "debe ser un PESEL válido"
        nip = "debe ser un NIP válido"
    }
    portugal {
        nif = "debe ser un NIF válido"
        nipc = "debe ser un NIPC válido"
    }
    russia {
        innIndividual = "debe ser un INN de persona física válido"
        innLegalEntity = "debe ser un INN de persona jurídica válido"
    }
    slovenia { davcnaStevilka = "debe ser una davčna številka válida" }
    southAfrica {
        cipc = "debe ser un número de registro de empresa válido"
        idNumber = "debe ser un número de identidad válido"
    }
    southKorea {
        brn = "debe ser un número de registro comercial válido"
        rrn = "debe ser un número de registro de residente válido"
    }
    spain {
        dni = "debe ser un DNI válido"
        nie = "debe ser un NIE válido"
        cif = "debe ser un CIF válido"
    }
    sweden {
        organisationsnummer = "debe ser un organisationsnummer válido"
        personnummer = "debe ser un personnummer válido"
    }
    switzerland {
        uid = "debe ser un UID válido"
        ahv = "debe ser un número AHV válido"
    }
    thailand { nationalId = "debe ser un número de identificación nacional válido" }
    ukraine { rnokpp = "debe ser un RNOKPP válido" }
    unitedArabEmirates {
        tradeLicense = "debe ser un número de licencia comercial válido"
        emiratesId = "debe ser un Emirates ID válido"
    }
    unitedKingdom {
        vat = "debe ser un número de VAT válido"
        nino = "debe ser un número de National Insurance válido"
    }
    unitedStates {
        ein = "debe ser un EIN válido"
        ssn = "debe ser un SSN válido"
        itin = "debe ser un ITIN válido"
    }
    uruguay {
        rut = "debe ser un RUT válido"
        ci = "debe ser una CI válida"
    }
}
