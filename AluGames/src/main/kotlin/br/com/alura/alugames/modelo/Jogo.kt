package br.com.alura.alugames.modelo
data class Jogo(val titulo: String, val capa: String) {
    var descricao:String? = ""


    override fun toString(): String {
        return "Jogo:\n"  +
                "Titulo: $titulo\n" +
                "Capa: $capa\n" +
                "Descricao: $descricao"
    }


}