package com.plcoding.cleanarchitecturenoteapp.feature_note.presentation.notes.components

import android.widget.Space
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.unit.dp
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util.NoteOrder
import com.plcoding.cleanarchitecturenoteapp.feature_note.domain.util.OrderType

@Composable
fun OrderSection(
    modifier: Modifier = Modifier,
    noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    onOrderChange: (NoteOrder) -> Unit

) {
    Column(
        modifier = modifier,

        ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            DefaultRadioBtn(text = "title",
                selected = noteOrder is NoteOrder.Title,
                onSelect = {onOrderChange(NoteOrder.Title(noteOrder.orderType)) }
            )
            
            Spacer(modifier = Modifier.width(8.dp))

            DefaultRadioBtn(text = "date",
                selected = noteOrder is NoteOrder.Date,
                onSelect = {onOrderChange(NoteOrder.Date(noteOrder.orderType)) }
            )

            Spacer(modifier = Modifier.width(8.dp))

            DefaultRadioBtn(text = "color",
                selected = noteOrder is NoteOrder.Color,
                onSelect = {onOrderChange(NoteOrder.Color(noteOrder.orderType)) }
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            DefaultRadioBtn(
                text = "Ascending",
                selected = noteOrder.orderType is OrderType.Ascending,
                onSelect = {noteOrder.copy(OrderType.Ascending) }
            )

            Spacer(modifier = Modifier.width(8.dp))

            DefaultRadioBtn(
                text = "Descending",
                selected = noteOrder.orderType is OrderType.Descending,
                onSelect = {noteOrder.copy(OrderType.Descending) }
            )

        }

    }
}