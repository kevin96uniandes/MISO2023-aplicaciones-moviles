package com.uniandes.vinyls

enum class RecordLabel(val record: String) {
    SONY("Sony Music"),
    EMI("EMI"),
    FUENTES("Discos Fuentes"),
    ELEKTRA("Elektra"),
    FANIA("Fania Records")
}

enum class Genre(val genre: String){
    CLASSICAL("Classical"),
    SALSA("Salsa"),
    ROCK("Rock"),
    FOLK("Folk");
}

enum class EditTextType(){
    TEXT,
    URL;
}