/*
 *
 *  Copyright 2021 Orange
 *
 *  Use of this source code is governed by an MIT-style
 *  license that can be found in the LICENSE file or at
 *  https://opensource.org/licenses/MIT.
 * /
 */

package com.orange.ods.app.ui.components.buttons

import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.orange.ods.app.R
import com.orange.ods.app.ui.components.Variant
import com.orange.ods.app.ui.components.utilities.ComponentCountRow
import com.orange.ods.app.ui.components.utilities.ComponentCustomizationBottomSheetScaffold
import com.orange.ods.compose.component.list.OdsListItem
import com.orange.ods.compose.component.list.OdsSwitchTrailing

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ComponentButtonsIcons(variant: Variant) {
    val customizationState = rememberButtonIconCustomizationState()

    with(customizationState) {
        ComponentCustomizationBottomSheetScaffold(
            bottomSheetScaffoldState = rememberBottomSheetScaffoldState(),
            bottomSheetContent = {
                if (variant == Variant.ButtonsToggle) {
                    ComponentCountRow(
                        modifier = Modifier.padding(start = dimensionResource(id = R.dimen.screen_horizontal_margin)),
                        title = stringResource(id = R.string.component_buttons_toggle_count),
                        count = toggleCount,
                        minusIconContentDescription = stringResource(id = R.string.component_buttons_toggle_remove),
                        plusIconContentDescription = stringResource(id = R.string.component_buttons_toggle_add),
                        minCount = ButtonIconCustomizationState.MinToggleCount,
                        maxCount = ButtonIconCustomizationState.MaxToggleCount
                    )
                }
                OdsListItem(
                    text = stringResource(id = R.string.component_state_enabled),
                    trailing = OdsSwitchTrailing(checked = enabled)
                )
            }) {

            when (variant) {
                Variant.ButtonsToggle -> ButtonsIconToggle(customizationState)
                Variant.ButtonsIcon -> ButtonsIcon(customizationState)
                else -> {}
            }
        }
    }
}