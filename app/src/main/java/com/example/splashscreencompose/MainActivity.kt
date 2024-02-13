package com.example.splashscreencompose

import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import kotlinx.coroutines.delay

/*
How you can easily create a splash screen in compose with a cool overshoot animation.
https://www.youtube.com/watch?v=GhNwvGePTbY&list=PLQkwcJG4YTCSpJ2NLhDTHhi6XBNfk9WiC&index=20
 */
// Classe que representa a atividade principal da aplicação, estendendo ComponentActivity.
class MainActivity : ComponentActivity() {
    // Sobrescreve o método onCreate para inicializar a atividade quando ela é criada.
    override fun onCreate(savedInstanceState: Bundle?) {
        // Chama a implementação do método onCreate na classe pai.
        super.onCreate(savedInstanceState)

        // Define o conteúdo da atividade usando o Compose, configurando um Surface com cor de fundo e chamando a função Navigation().
        setContent {
            Surface(color = Color(0xFF202020), modifier = Modifier.fillMaxSize()) {
                Navigation()
            }
        }
    }
}

// Função @Composable que define a estrutura de navegação da aplicação.
@Composable
fun Navigation() {
    // Cria um controlador de navegação para gerenciar as transições entre telas.
    val navController = rememberNavController()

    // Define o ponto de início e as rotas para as diferentes telas da aplicação.
    NavHost(navController = navController, startDestination = "splash_screen") {
        // Configura a tela de splash.
        composable("splash_screen") {
            SplashScreen(navController = navController)
        }

        // Configura a tela principal.
        composable("main_screen") {
            // Define a tela principal como um Box com texto centralizado.
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "MAIN SCREEN", color = Color.White)
            }
        }
    }
}

// Função @Composable que representa a tela de splash.
@Composable
fun SplashScreen(navController: NavController) {
    // Cria um valor animável para controlar a escala da imagem na tela de splash.
    val scale = remember {
        Animatable(0f)
    }

    // Efeito lançado para animar a escala da imagem e navegar para a tela principal após um atraso.
    // Um efeito lançado usando a função LaunchedEffect, que é acionado quando a chave (key) especificada é alterada.
    LaunchedEffect(key1 = true) {
        // Inicia uma animação de escala usando o valor animável 'scale'.
        scale.animateTo(
            targetValue = 1.4f, // Define o valor final da escala.
            animationSpec = tween( // Especifica o tipo de animação usando tween (interpolação linear).
                durationMillis = 500, // Define a duração da animação em milissegundos.
                easing = { // Configura a curva de animação usando um interpolador de sobrescotação.
                    OvershootInterpolator(3f).getInterpolation(it)
                }
            )
        )

        // Aguarda um atraso de 3000 milissegundos (3 segundos).
        delay(3000L)

        // Navega para a tela "main_screen" usando o controlador de navegação fornecido.
        navController.navigate("main_screen")
    }


    // Estrutura da tela de splash, exibindo uma imagem com animação de escala.
    // Um contêiner Box em Compose que centraliza seu conteúdo e ocupa todo o espaço disponível.
    Box(
        contentAlignment = Alignment.Center, // Define o alinhamento do conteúdo no centro da Box.
        modifier = Modifier.fillMaxSize() // Define um modificador para ocupar todo o espaço disponível.
    ) {
        // Um componente Image que exibe uma imagem usando um recurso de pintor (painterResource).
        Image(
            painter = painterResource(id = R.drawable.ic_liked), // Define o recurso de imagem a ser exibido.
            contentDescription = "Logo", // Descrição do conteúdo para acessibilidade.
            modifier = Modifier.scale(scale.value) // Aplica uma escala à imagem com base no valor do animável 'scale'.
        )
    }

}
