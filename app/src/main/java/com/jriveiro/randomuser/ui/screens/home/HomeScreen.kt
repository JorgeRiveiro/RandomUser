package com.jriveiro.randomuser.ui.screens.home

import android.Manifest
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jriveiro.randomuser.R
import com.jriveiro.randomuser.User
import com.jriveiro.randomuser.ui.common.PermissionRequest
import com.jriveiro.randomuser.ui.common.getRegion
import com.jriveiro.randomuser.ui.screens.Screen
import com.jriveiro.randomuser.users
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onUserClick: (User) -> Unit){
    val appName = stringResource(id = R.string.app_name)
    var appBarTitle by remember { mutableStateOf(appName) }
    val ctx = LocalContext.current.applicationContext
    val coroutineScope = rememberCoroutineScope()

    PermissionRequest(permission = Manifest.permission.ACCESS_COARSE_LOCATION) { granted ->
        if (granted) {
            coroutineScope.launch {
                val region = ctx.getRegion()
                appBarTitle = "$appBarTitle ($region)"
            }
        } else {
            appBarTitle = "$appBarTitle (Permission denied)"
        }
    }
    Screen {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = appBarTitle) },
                    scrollBehavior = scrollBehavior
                )
            },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            contentWindowInsets = WindowInsets.safeDrawing
            ) { padding ->
            LazyVerticalGrid(
                columns = GridCells.Adaptive(120.dp),
                contentPadding = padding,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(horizontal = 4.dp)
            ) {
                items(users, key = { it.id }) {
                    UserItem(user = it){onUserClick(it)}
                }
            }
        }
    }
}

@Composable
fun UserItem(user: User, onClick: () -> Unit) {
    Column (
        modifier = Modifier.clickable(onClick = onClick)
    ){
        AsyncImage(
            model = user.profileImage,
            contentDescription = user.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2 / 3f)
                .clip(MaterialTheme.shapes.small)
                .background(MaterialTheme.colorScheme.primaryContainer)
        )
        Text(
            text = user.title,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            modifier = Modifier.padding(8.dp)
        )
    }
}