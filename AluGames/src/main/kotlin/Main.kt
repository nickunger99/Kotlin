import com.google.gson.Gson
import java.lang.Exception
import java.lang.NullPointerException
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.net.http.HttpResponse.BodyHandlers
import java.util.Scanner


fun main(args: Array<String>) {
    val leitura = Scanner(System.`in`)
    print("Digite o codigo do jogo: ")

    val busca = leitura.nextLine();
    val endereco = "https://www.cheapshark.com/api/1.0/games?id=$busca"
    val client: HttpClient = HttpClient.newHttpClient()
    val request = HttpRequest.newBuilder()
        .uri(URI.create(endereco))
        .build()
//    client.sendAsync(request, BodyHandlers.ofString())
//        .thenApply { obj: HttpResponse<*> -> obj.body() }
//        .thenAccept(System.out::println)
//        .join()
    val response = client
        .send(request, BodyHandlers.ofString())

    val json = response.body()
    println(json)

    try {
        val gson = Gson()
        val meuInfoJogo = gson.fromJson(json, InfoJogo::class.java)
        val meuJogo = Jogo(meuInfoJogo.info.title, meuInfoJogo.info.thumb)
        println(meuJogo)
    } catch (ex: Exception){
        println("Jogo inexistente. Tente outro id.")
    }
}