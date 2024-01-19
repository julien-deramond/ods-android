/*
 * Software Name: Orange Design System
 * SPDX-FileCopyrightText: Copyright (c) Orange SA
 * SPDX-License-Identifier: MIT
 *
 * This software is distributed under the MIT licence,
 * the text of which is available at https://opensource.org/license/MIT/
 * or see the "LICENSE" file for more details.
 *
 * Software description: Android library of reusable graphical components 
 */

package com.orange.ods.app.ui.components.sheets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.onClick
import com.orange.ods.app.R
import com.orange.ods.app.domain.recipes.LocalRecipes
import com.orange.ods.app.ui.components.utilities.ComponentCustomizationBottomSheetScaffold
import com.orange.ods.app.ui.utilities.code.CodeImplementationColumn
import com.orange.ods.app.ui.utilities.code.FunctionCallCode
import com.orange.ods.compose.OdsComposable
import com.orange.ods.compose.component.chip.OdsChoiceChip
import com.orange.ods.compose.component.chip.OdsChoiceChipsFlowRow
import com.orange.ods.compose.component.listitem.OdsListItem
import com.orange.ods.compose.text.OdsText
import com.orange.ods.compose.text.OdsTextStyle


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ComponentSheetsBottom() {

    val customizationState = rememberSheetsBottomCustomizationState()

    with(customizationState) {
        ComponentCustomizationBottomSheetScaffold(
            titleResId = R.string.component_sheet_bottom_recipes,
            bottomSheetScaffoldState = rememberBottomSheetScaffoldState(),
            bottomSheetContent = {
                val isEmpty = content.value == SheetsBottomCustomizationState.Content.Empty
                val recipes = LocalRecipes.current
                recipes.take(3).forEach { recipe ->
                    OdsListItem(
                        modifier = Modifier.alpha(if (isEmpty) 0.0f else 1.0f),
                        leadingIcon = recipe.iconResId?.let { iconRes ->
                            OdsListItem.LeadingIcon(OdsListItem.LeadingIcon.Type.Icon, painterResource(id = iconRes), "")
                        },
                        text = recipe.title
                    )
                }
            }
        ) {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                Column(
                    modifier = Modifier
                        .padding(top = dimensionResource(id = com.orange.ods.R.dimen.screen_vertical_margin))
                        .padding(horizontal = dimensionResource(id = com.orange.ods.R.dimen.screen_horizontal_margin))
                ) {
                    OdsText(text = stringResource(id = R.string.component_sheet_bottom_customize), style = OdsTextStyle.BodyL)
                    OdsText(
                        text = stringResource(id = R.string.component_element_content),
                        modifier = Modifier.padding(top = dimensionResource(id = com.orange.ods.R.dimen.spacing_s)),
                        style = OdsTextStyle.TitleM
                    )
                    val contentEmptyDesc = stringResource(id = R.string.component_sheet_bottom_customize_content_empty_desc)
                    val contentExampleDesc = stringResource(id = R.string.component_sheet_bottom_customize_content_example_desc)
                    OdsChoiceChipsFlowRow(
                        value = content.value,
                        onValueChange = { value -> content.value = value },
                        chips = listOf(
                            OdsChoiceChip(
                                text = stringResource(id = R.string.component_element_empty),
                                value = SheetsBottomCustomizationState.Content.Empty,
                                semantics = {
                                    onClick(contentEmptyDesc, null)
                                }),
                            OdsChoiceChip(
                                text = stringResource(id = R.string.component_element_example),
                                value = SheetsBottomCustomizationState.Content.Example,
                                semantics = {
                                    onClick(contentExampleDesc, null)
                                }
                            )
                        )
                    )
                }

                CodeImplementationColumn(modifier = Modifier.padding(horizontal = dimensionResource(id = com.orange.ods.R.dimen.screen_horizontal_margin))) {
                    FunctionCallCode(
                        name = OdsComposable.OdsBottomSheetScaffold.name,
                        exhaustiveParameters = false,
                        parameters = { lambda("sheetContent") }
                    )
                }
            }
        }
    }
}