 package com.example.restauranteapp

 import android.os.Bundle
 import androidx.activity.ComponentActivity
 import androidx.activity.compose.setContent
 import androidx.compose.runtime.Composable
 import androidx.compose.runtime.collectAsState
 import androidx.lifecycle.viewmodel.compose.viewModel
 import androidx.navigation.NavType
 import androidx.navigation.compose.NavHost
 import androidx.navigation.compose.composable
 import androidx.navigation.compose.rememberNavController
 import androidx.navigation.navArgument
 import com.example.restauranteapp.models.MenuViewModel
 import com.example.restauranteapp.ui.theme.RestauranteAppTheme

 class MainActivity : ComponentActivity() {
     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         setContent {
             RestauranteAppTheme {
                 MyAppNavigation()
             }
         }
     }
 }

 @Composable
 fun MyAppNavigation() {
     val navController = rememberNavController()
     val viewModel: MenuViewModel = viewModel()

     NavHost(navController = navController, startDestination = "menuScreen") {
         composable("menuScreen") {
             MenuScreen(
                 viewModel = viewModel,
                 onAddClick = {
                     navController.navigate("addEditScreen")
                 },
                 onEditClick = { itemId ->
                     navController.navigate("addEditScreen?itemId=$itemId")
                 }
             )
         }
         composable(
             route = "addEditScreen?itemId={itemId}",
             arguments = listOf(navArgument("itemId") {
                 type = NavType.LongType
                 defaultValue = 0L
             })
         ) { backStackEntry ->
             val itemId = backStackEntry.arguments?.getLong("itemId") ?: 0L
             val menuItem = viewModel.menuItems.collectAsState().value.find { it.id == itemId }

             AddEditScreen(
                 viewModel = viewModel,
                 menuItem = menuItem,
                 onItemSaved = {
                     navController.popBackStack()
                 }
             )
         }
     }
 }