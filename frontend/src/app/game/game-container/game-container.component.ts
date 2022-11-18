import { Component } from '@angular/core';
import { Select } from '@ngxs/store';
import { Observable } from 'rxjs';
import { GameState } from '../states/game.state';

@Component({
    selector: 'app-game-container',
    templateUrl: './game-container.component.html',
    styleUrls: ['./game-container.component.scss'],
})
export class GameContainerComponent {
    @Select(GameState.hasPlayerNameSelected)
    hasPlayerNameSelected$!: Observable<boolean>;
}
