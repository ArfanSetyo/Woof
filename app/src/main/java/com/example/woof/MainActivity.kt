
package com.example.woof

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.woof.data.Dog
import com.example.woof.data.dogs
import com.example.woof.ui.theme.WoofTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WoofTheme {
                WoofApp()
            }
        }
    }
}


//Komposisi yang menampilkan panel aplikasi dan daftar anjing.

@Composable
fun WoofApp() {
    Scaffold(
        topBar = {
            WoofTopAppBar()
        }
    ) {
        LazyColumn(modifier = Modifier.background(MaterialTheme.colors.background)) {
            items(dogs) {
                DogItem(dog = it)
            }
        }
    }
}

/**
 * Dapat disusun yang menampilkan item daftar yang berisi ikon anjing dan informasinya.
 *
 * @param dog berisi data yang mengisi item daftar
 * Pengubah @param pengubah untuk disetel ke composable ini
 */
@Composable
fun DogItem(dog: Dog, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        elevation = 4.dp,
        modifier = modifier.padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                DogIcon(dog.imageResourceId)
                DogInformation(dog.name, dog.age)
                Spacer(Modifier.weight(1f))
                DogItemButton(
                    expanded = expanded,
                    onClick = { expanded = !expanded },
                )
            }
            if (expanded) {
                DogHobby(dog.hobbies)
            }
        }
    }
}

/*
* Composable yang menampilkan tombol yang dapat diklik dan menampilkan perluasan lebih banyak atau lebih sedikit perluasan
* ikon.
*
* @param yang diperluas menunjukkan apakah ikon perluas lebih banyak atau lebih sedikit yang terlihat
* @param onClick adalah aksi yang terjadi saat tombol diklik
* Pengubah @param pengubah untuk disetel ke composable ini
*/
@Composable
private fun DogItemButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            tint = MaterialTheme.colors.secondary,
            contentDescription = stringResource(R.string.expand_button_content_description),
        )
    }
}

/**
 * Composable yang menampilkan Top Bar dengan ikon dan teks.
 *
 * Pengubah @param pengubah untuk disetel ke composable ini
 */
@Composable
fun WoofTopAppBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.primary),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            modifier = modifier
                .size(64.dp)
                .padding(8.dp),
            painter = painterResource(R.drawable.ic_woof_logo),
            /*
             * Deskripsi Konten tidak diperlukan di sini - gambar bersifat dekoratif, dan menetapkan nol
             * deskripsi konten memungkinkan layanan aksesibilitas melewati elemen ini selama
             * navigasi.
             */
            contentDescription = null
        )
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.h1
        )
    }
}

/**
 * Komposisi yang menampilkan foto seekor anjing.
 *
 * @param dogIcon adalah ID sumber daya untuk gambar anjing
 * Pengubah @param pengubah untuk disetel ke composable ini
 */
@Composable
fun DogIcon(@DrawableRes dogIcon: Int, modifier: Modifier = Modifier) {
    Image(
        modifier = modifier
            .size(64.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(50)),
        contentScale = ContentScale.Crop,
        painter = painterResource(dogIcon),
        /*
          * Deskripsi Konten tidak diperlukan di sini - gambar bersifat dekoratif, dan menyetel konten null
          * deskripsi memungkinkan layanan aksesibilitas melewati elemen ini selama navigasi.
          */
        contentDescription = null
    )
}

/**
 * Komposisi yang menampilkan nama dan usia anjing.
 *
 * @param dogName adalah ID sumber daya untuk string nama anjing
 * @param dogAge adalah Int yang merepresentasikan usia anjing
 * Pengubah @param pengubah untuk disetel ke composable ini
 */
@Composable
fun DogInformation(@StringRes dogName: Int, dogAge: Int, modifier: Modifier = Modifier) {
    Column {
        Text(
            text = stringResource(dogName),
            style = MaterialTheme.typography.h2,
            modifier = modifier.padding(top = 8.dp)
        )
        Text(
            text = stringResource(R.string.years_old, dogAge),
            style = MaterialTheme.typography.body1
        )
    }
}

/**
 * Komposisi yang menampilkan hobi anjing.
 *
 * @param dogHobby adalah ID sumber daya untuk string teks hobi yang akan ditampilkan
 * Pengubah @param pengubah untuk disetel ke composable ini
 */
@Composable
fun DogHobby(@StringRes dogHobby: Int, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(
            start = 16.dp,
            top = 8.dp,
            bottom = 16.dp,
            end = 16.dp
        )
    ) {
        Text(
            text = stringResource(R.string.about),
            style = MaterialTheme.typography.h3
        )
        Text(
            text = stringResource(dogHobby),
            style = MaterialTheme.typography.body1
        )
    }
}

/**
 * Komposisi yang menampilkan tampilan UI aplikasi dalam tema ringan di tab desain.
 */
@Preview
@Composable
fun WoofPreview() {
    WoofTheme(darkTheme = false) {
        WoofApp()
    }
}

/**
 * Komposisi yang menampilkan tampilan UI aplikasi dalam tema gelap di tab desain.
 */
@Preview
@Composable
fun WoofDarkThemePreview() {
    WoofTheme(darkTheme = true) {
        WoofApp()
    }
}
