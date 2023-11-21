package br.com.alura.alugames.principal

import br.com.alura.alugames.modelo.Gamer
import br.com.alura.alugames.modelo.Jogo
import br.com.alura.alugames.servicos.ConsumoApi
import transformarEmIdade
import java.util.*


fun main(args: Array<String>) {
    val leitura = Scanner(System.`in`)
    val gamer = Gamer.criarGamer(leitura)
    println("Cadastro concluido com sucesso. Dados do gamer: ")
    println(gamer)
    println("Idade do gamer: " + gamer.dataNascimento?.transformarEmIdade())
    do {
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
            gamer.jogosBuscados.add(meuJogo)
        }
        println("Deseja buscar um novo jogo? S/N")
        val resposta = leitura.nextLine()
    } while (resposta.equals("s", true))

    println("Jogos buscados: ")
    println(gamer.jogosBuscados)

    println("\n Jogos ordenados por titulo: ")
    gamer.jogosBuscados.sortedBy {
        it?.titulo
    }
    gamer.jogosBuscados.forEach {
        println("Título: " + it?.titulo)
    }
    val jogosFiltrados = gamer.jogosBuscados.filter {
        it?.titulo?.contains("batman", true) ?: false
    }
    println("\n Jogos filtrados")
    println(jogosFiltrados)

    println("Deseja excluir algum jogo da lista original? S/N")
    val opcao = leitura.nextLine()

    if (opcao.equals("s", true)) {
        println(gamer.jogosBuscados)
        println("\n Informe a posição do jogo que deseja excluir: ")
        val pos = leitura.nextInt()
        gamer.jogosBuscados.removeAt(pos)
    }

    println("\nLista atualizada")
    println(gamer.jogosBuscados)

    println("Busca finalizada com sucesso.")
}