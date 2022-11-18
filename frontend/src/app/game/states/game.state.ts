import { Injectable } from '@angular/core';
import { Action, Selector, State, StateContext } from '@ngxs/store';
import { switchMap } from 'rxjs';
import { GameInputDto, GameResultDto, GameService, Pageable, PageGameResultDto } from 'src/app/generated/backend';
import { defaultFormStateModel, FormStateModel } from 'src/app/utils/form-state';
import { ChangePageAction, DisplayResultAction, LoadedHistoryAction, LoadHistoryAction, PlayAgainAction, SelectNameAction, SelectOptionAction } from './game.actions';

interface PlayerNameFormModel {
    name: string;
}

interface GameStateModel {
    name?: string;
    historyPage?: PageGameResultDto;
    historyPageable: Pageable;
    currentResult?: GameResultDto;
    playerNameForm: FormStateModel<PlayerNameFormModel>;
    resultInProgress: boolean;
}

@State<GameStateModel>({
    name: 'Game',
    defaults: {
        name: undefined,
        historyPage: undefined,
        historyPageable: { page: 0, size: 10 },
        currentResult: undefined,
        playerNameForm: defaultFormStateModel(),
        resultInProgress: false,
    },
})
@Injectable()
export class GameState {
    @Selector()
    static historyPage(state: GameStateModel): PageGameResultDto | undefined {
        return state.historyPage;
    }

    @Selector()
    static historyPageable(state: GameStateModel): Pageable {
        return state.historyPageable;
    }

    @Selector()
    static displayGame(state: GameStateModel): boolean {
        return !state.currentResult && !state.resultInProgress;
    }

    @Selector()
    static displayResult(state: GameStateModel): boolean {
        return !!state.currentResult && !state.resultInProgress;
    }

    @Selector()
    static resultInProgress(state: GameStateModel): boolean {
        return state.resultInProgress;
    }

    @Selector()
    static currentResult(state: GameStateModel): GameResultDto | undefined {
        return state.currentResult;
    }

    @Selector()
    static hasPlayerNameSelected(state: GameStateModel): boolean {
        return !!state.name;
    }

    @Selector()
    static playerNameFormInvalid(state: GameStateModel): boolean {
        return state.playerNameForm?.status !== 'VALID';
    }

    @Selector()
    static playerNameFormDirty(state: GameStateModel): boolean {
        return state.playerNameForm?.dirty;
    }

    constructor(private gameService: GameService) {}

    @Action(LoadHistoryAction)
    loadHistory(ctx: StateContext<GameStateModel>): any {
        const state: GameStateModel = ctx.getState();

        return this.gameService
            .getResults(state.historyPageable) //
            .pipe(switchMap((page) => ctx.dispatch(new LoadedHistoryAction(page))));
    }

    @Action(LoadedHistoryAction)
    loadedHistory(ctx: StateContext<GameStateModel>, action: LoadedHistoryAction) {
        ctx.patchState({
            historyPage: action.history,
            historyPageable: {
                page: action.history.pageable?.pageNumber,
                size: action.history.pageable?.pageSize,
            },
        });
    }

    @Action(SelectNameAction)
    selectName(ctx: StateContext<GameStateModel>) {
        const state: GameStateModel = ctx.getState();
        ctx.patchState({
            name: state.playerNameForm.model?.name,
        });
    }

    @Action(SelectOptionAction)
    selectOption(ctx: StateContext<GameStateModel>, action: SelectOptionAction) {
        const state: GameStateModel = ctx.getState();

        const input: GameInputDto = {
            name: state.name || 'Unnamed Player',
            option: action.option,
        };

        ctx.patchState({
            resultInProgress: true,
        });

        return this.gameService
            .play(input) //
            .pipe(switchMap((result) => ctx.dispatch(new DisplayResultAction(result))));
    }

    @Action(DisplayResultAction)
    displayResult(ctx: StateContext<GameStateModel>, action: DisplayResultAction) {
        ctx.patchState({
            resultInProgress: false,
            currentResult: action.result,
        });

        // reload history as we can assume it changed
        return ctx.dispatch(new LoadHistoryAction());
    }

    @Action(ChangePageAction)
    changePage(ctx: StateContext<GameStateModel>, action: ChangePageAction): any {
        ctx.patchState({
            historyPageable: {
                page: action.page,
                size: action.pageSize,
            },
        });

        return ctx.dispatch(new LoadHistoryAction());
    }

    @Action(PlayAgainAction)
    playAgain(ctx: StateContext<GameStateModel>) {
        ctx.patchState({
            resultInProgress: false,
            currentResult: undefined,
        });
    }
}
