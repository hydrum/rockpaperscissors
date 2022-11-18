import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { NgxsModule } from '@ngxs/store';
import { GameState } from '../states/game.state';

import { GameContainerComponent } from './game-container.component';
import { PlayerNameSelectionComponent } from '../player-name-selection/player-name-selection.component';
import { PlayComponent } from '../play/play.component';
import { HistoryComponent } from '../history/history.component';
import { MatCardModule } from '@angular/material/card';

describe('GameContainerComponent', () => {
    let component: GameContainerComponent;
    let fixture: ComponentFixture<GameContainerComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [GameContainerComponent, PlayerNameSelectionComponent, PlayComponent, HistoryComponent],
            imports: [
                NgxsModule.forRoot([GameState]), //
                HttpClientTestingModule,
                MatCardModule,
            ],
        }).compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(GameContainerComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
