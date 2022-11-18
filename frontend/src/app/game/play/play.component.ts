import { Component } from '@angular/core';
import { Select, Store } from '@ngxs/store';
import { Observable } from 'rxjs';
import { GameOption, GameOutcome, GameResultDto } from 'src/app/generated/backend';
import { PlayAgainAction, SelectOptionAction } from '../states/game.actions';
import { GameState } from '../states/game.state';

@Component({
    selector: 'app-play',
    templateUrl: './play.component.html',
    styleUrls: ['./play.component.scss'],
})
export class PlayComponent {
    @Select(GameState.displayGame)
    public displayGame!: Observable<boolean>;

    @Select(GameState.displayResult)
    public displayResult!: Observable<boolean>;

    @Select(GameState.resultInProgress)
    public resultInProgress!: Observable<boolean>;

    @Select(GameState.currentResult)
    public result!: Observable<GameResultDto>;

    resultPlayer: GameOutcome = GameOutcome.PlayerWin;
    resultDraw: GameOutcome = GameOutcome.Draw;
    resultCpu: GameOutcome = GameOutcome.CpuWin;

    constructor(private store: Store) {}

    public onPlayAgain() {
        this.store.dispatch(new PlayAgainAction());
    }

    public onRockSelection() {
        this.onSelection(GameOption.Rock);
    }

    public onPaperSelection() {
        this.onSelection(GameOption.Paper);
    }

    public onScissorsSelection() {
        this.onSelection(GameOption.Scissors);
    }

    private onSelection(option: GameOption) {
        this.store.dispatch(new SelectOptionAction(option));
    }

    public getIcon(option: GameOption | undefined): string {
        switch (option) {
            case GameOption.Paper:
                return 'back_hand';
            case GameOption.Rock:
                return 'landslide';
            case GameOption.Scissors:
                return 'content_cut';
            default:
                return '';
        }
    }
}
