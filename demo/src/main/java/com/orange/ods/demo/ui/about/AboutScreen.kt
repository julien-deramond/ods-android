/*
 *
 *  Copyright 2021 Orange
 *
 *  Use of this source code is governed by an MIT-style
 *  license that can be found in the LICENSE file or at
 *  https://opensource.org/licenses/MIT.
 * /
 */

package com.orange.ods.demo.ui.about

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import com.orange.ods.compose.component.list.OdsListItem
import com.orange.ods.compose.text.OdsTextCaption
import com.orange.ods.compose.text.OdsTextH4
import com.orange.ods.compose.theme.OdsTheme
import com.orange.ods.demo.R
import com.orange.ods.demo.ui.LocalMainThemeManager
import com.orange.ods.demo.ui.LocalMainTopAppBarManager
import com.orange.ods.demo.ui.utilities.composable.RadioButtonListItem
import com.orange.ods.demo.ui.utilities.extension.versionCode
import com.orange.ods.utilities.extension.orElse

@Composable
fun AboutScreen(onAboutItemClick: (Long) -> Unit) {
    LocalMainTopAppBarManager.current.updateTopAppBarTitle(R.string.navigation_item_about)

    val mainThemeManager = LocalMainThemeManager.current
    var dialogVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(bottom = dimensionResource(id = R.dimen.screen_vertical_margin))
    ) {
        val context = LocalContext.current
        Image(
            modifier = Modifier
                .fillMaxWidth(),
            painter = painterResource(id = R.drawable.il_about),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
        Column(Modifier.padding(horizontal = dimensionResource(id = R.dimen.screen_horizontal_margin))) {
            OdsTextH4(
                text = stringResource(id = R.string.about_app_name),
                modifier = Modifier.padding(top = dimensionResource(id = R.dimen.spacing_xl))
            )
            OdsTextCaption(
                text = getVersion(context),
                modifier = Modifier.padding(top = dimensionResource(id = R.dimen.spacing_xs))
            )
            OdsTextCaption(text = stringResource(id = R.string.about_copyright))
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_m)))

        OdsListItem(text = stringResource(id = R.string.about_menu_theme), modifier = Modifier.clickable {
            dialogVisible = true
        })
        for (aboutItem in aboutItems) {
            OdsListItem(text = stringResource(id = aboutItem.titleRes), modifier = Modifier.clickable {
                onAboutItemClick(aboutItem.id)
            })
        }
    }


    if (dialogVisible) {
        val selectedRadio = remember { mutableStateOf(mainThemeManager.currentThemeConfiguration.name) }

        Dialog(onDismissRequest = { dialogVisible = false }) {
            Column(modifier = Modifier.background(OdsTheme.colors.surface)) {
                mainThemeManager.themeConfigurations.forEach { themeConfiguration ->
                    RadioButtonListItem(
                        label = themeConfiguration.name,
                        selectedRadio = selectedRadio,
                        currentRadio = themeConfiguration.name,
                        onClick = { mainThemeManager.currentThemeConfiguration = themeConfiguration }
                    )
                }
            }
        }
    }

}

private fun getVersion(context: Context): String {
    val packageInfo = context.packageName?.let { packageName ->
        context.packageManager?.getPackageInfo(packageName, 0)
    }
    return packageInfo?.let {
        String.format(context.resources.getString(R.string.about_app_version), packageInfo.versionName, packageInfo.versionCode())
    }.orElse {
        context.resources.getString(R.string.about_app_version)
    }
}