import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HistoryComponent } from './history/history.component';
import { PlayComponent } from './play/play.component';
import { GameContainerComponent } from './game-container/game-container.component';
import { NgxsModule } from '@ngxs/store';
import { GameState } from './states/game.state';
import { PlayerNameSelectionComponent } from './player-name-selection/player-name-selection.component';
import { GameRoutingModule } from './game-routing.module';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { NgxsFormPluginModule } from '@ngxs/form-plugin';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HistoryTableComponent } from './history-table/history-table.component';
import { MatFormFieldModule } from '@angular/material/form-field';

@NgModule({
    declarations: [
        PlayerNameSelectionComponent, //
        HistoryComponent,
        PlayComponent,
        GameContainerComponent,
        HistoryTableComponent,
    ],
    imports: [
        GameRoutingModule, //
        CommonModule,
        NgxsModule.forFeature([GameState]),
        NgxsFormPluginModule,
        ReactiveFormsModule,
        FormsModule,
        MatInputModule,
        MatFormFieldModule,
        MatButtonModule,
        MatTableModule,
        MatPaginatorModule,
        MatProgressSpinnerModule,
        MatCardModule,
        MatIconModule,
    ],
})
export class GameModule {}
