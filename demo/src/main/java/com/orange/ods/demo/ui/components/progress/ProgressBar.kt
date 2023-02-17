/*
 *
 *  Copyright 2021 Orange
 *
 *  Use of this source code is governed by an MIT-style
 *  license that can be found in the LICENSE file or at
 *  https://opensource.org/licenses/MIT.
 * /
 */

package com.orange.ods.demo.ui.components.progress

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.orange.ods.compose.component.OdsComponent
import com.orange.ods.compose.component.chip.OdsChoiceChip
import com.orange.ods.compose.component.chip.OdsChoiceChipsFlowRow
import com.orange.ods.compose.component.list.OdsListItem
import com.orange.ods.compose.component.list.OdsSwitchTrailing
import com.orange.ods.compose.component.progressindicator.OdsLinearProgressIndicator
import com.orange.ods.demo.R
import com.orange.ods.demo.ui.components.utilities.ComponentCustomizationBottomSheetScaffold
import com.orange.ods.demo.ui.utilities.composable.CodeImplementationColumn
import com.orange.ods.demo.ui.utilities.composable.CommonTechnicalTextColumn
import com.orange.ods.demo.ui.utilities.composable.Subtitle
import com.orange.ods.demo.ui.utilities.composable.TechnicalText

private const val DeterminateProgressTargetValue = 0.9f
private const val DeterminateProgressAnimDuration = 5000

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ProgressBar() {

    val customizationState = rememberProgressCustomizationState()
    var determinateProgressValue by remember { mutableStateOf(0f) }
    val determinateProgressAnimation by animateFloatAsState(
        targetValue = determinateProgressValue,
        animationSpec = tween(durationMillis = DeterminateProgressAnimDuration, easing = FastOutSlowInEasing)
    )

    with(customizationState) {
        ComponentCustomizationBottomSheetScaffold(
            bottomSheetScaffoldState = rememberBottomSheetScaffoldState(),
            bottomSheetContent = {
                OdsChoiceChipsFlowRow(
                    selectedChip = value,
                    modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.spacing_m)),
                    outlinedChips = true
                ) {
                    Subtitle(textRes = R.string.component_progress_type)
                    OdsChoiceChip(textRes = R.string.component_progress_bar_determinate, value = ProgressCustomizationState.Value.Determinate)
                    OdsChoiceChip(textRes = R.string.component_progress_bar_indeterminate, value = ProgressCustomizationState.Value.Indeterminate)
                }
                OdsListItem(
                    text = stringResource(id = R.string.component_element_label),
                    trailing = OdsSwitchTrailing(checked = label)
                )
                OdsListItem(
                    text = stringResource(id = R.string.component_element_icon),
                    trailing = OdsSwitchTrailing(checked = icon)
                )

                if (value.value == ProgressCustomizationState.Value.Determinate) {
                    OdsListItem(
                        text = stringResource(id = R.string.component_progress_bar_value),
                        trailing = OdsSwitchTrailing(checked = currentValue),
                    )
                } else {
                    OdsListItem(
                        text = stringResource(id = R.string.component_progress_bar_value),
                        trailing = OdsSwitchTrailing(checked = currentValue, enabled = false),
                    )
                }

            }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                val text = stringResource(id = R.string.component_progress_circular_download)
                val currentValue = stringResource(id = R.string.component_progress_current_value)
                OdsLinearProgressIndicator(
                    progress = if (value.value == ProgressCustomizationState.Value.Determinate) determinateProgressAnimation else null,
                    label = if (hasLabel) text else null,
                    currentValue = if (hasCurrentValue) currentValue else null,
                    icon = if (hasIcon) painterResource(id = R.drawable.ic_arrow_down) else null,
                    modifier = Modifier
                        .padding(top = dimensionResource(id = R.dimen.spacing_m))
                        .fillMaxWidth()
                )
                if (value.value == ProgressCustomizationState.Value.Determinate) {
                    LaunchedEffect(DeterminateProgressTargetValue) {
                        determinateProgressValue = DeterminateProgressTargetValue
                    }
                }
                CodeImplementationColumn {
                    CommonTechnicalTextColumn(
                        componentName = OdsComponent.OdsLinearProgressIndicator.name
                    ) {
                        if (value.value == ProgressCustomizationState.Value.Determinate) TechnicalText(text = " progress = $determinateProgressValue")
                        if (hasLabel) TechnicalText(text = " label = \"$text\"")
                        if (hasCurrentValue && value.value == ProgressCustomizationState.Value.Determinate) TechnicalText(text = " currentValue = \"$currentValue\"")
                        if (hasIcon) TechnicalText(text = " icon = painterResource(id = R.drawable.ic_arrow_down)")
                        TechnicalText(text = "  // add your content here")
                    }
                }
            }

        }
    }
}