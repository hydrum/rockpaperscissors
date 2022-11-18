import { GameOption, GameResultDto, PageGameResultDto } from 'src/app/generated/backend';

export class LoadHistoryAction {
    static readonly type = '[Game] LoadHistoryAction';
}

export class LoadedHistoryAction {
    static readonly type = '[Game] LoadedHistoryAction';

    constructor(public history: PageGameResultDto) {}
}

export class ChangePageAction {
    static readonly type = '[Game] ChangePageAction';

    constructor(public page: number, public pageSize: number) {}
}

export class SelectOptionAction {
    static readonly type = '[Game] SelectOption';

    constructor(public option: GameOption) {}
}

export class DisplayResultAction {
    static readonly type = '[Game] DisplayResultAction';

    constructor(public result: GameResultDto) {}
}

export class PlayAgainAction {
    static readonly type = '[Game] PlayAgainAction';
}

export class SelectNameAction {
    static readonly type = '[Game] SelectNameAction';
}
