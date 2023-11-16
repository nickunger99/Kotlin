package br.com.alura.alugames.principal

import br.com.alura.alugames.modelo.Jogo
import br.com.alura.alugames.servicos.ConsumoApi
import java.util.*


fun main(args: Array<String>) {
    val leitura = Scanner(System.`in`)
    print("Digite o codigo do jogo: ")

    val busca = leitura.nextLine()

    val buscaApi = ConsumoApi()

    var meuJogo: Jogo? = null
    val resultado = runCatching {
        val informacaoJogo = buscaApi.buscaJogo(busca)
        if (informacaoJogo != null) {
            meuJogo = Jogo(informacaoJogo.info.title, informacaoJogo.info.thumb)
        }

    }
    resultado.onFailure {
        println("Jogo inexistente. Tente outro id.")
    }

    resultado.onSuccess {
        println("Deseja inserir uma descrição personalizada? S/N")
        val opcao = leitura.nextLine()
        if (opcao.equals("S", ignoreCase = true)) {
            println("Insira a descrição personalizada: ")
            val descricaoPersonalizada = leitura.nextLine()
            meuJogo?.descricao = descricaoPersonalizada
        } else {
            meuJogo?.descricao = meuJogo?.titulo
        }
        println(meuJogo)
    }
}