import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { Select, Store } from '@ngxs/store';
import { Observable } from 'rxjs';
import { Pageable, PageGameResultDto } from 'src/app/generated/backend';
import { ChangePageAction, LoadHistoryAction } from '../states/game.actions';
import { GameState } from '../states/game.state';

@Component({
    selector: 'app-history',
    templateUrl: './history.component.html',
    styleUrls: ['./history.component.scss'],
})
export class HistoryComponent implements OnInit {
    public pageSizeOptions: number[] = [10, 25, 50];

    @Select(GameState.historyPage)
    public page!: Observable<PageGameResultDto>;

    @Select(GameState.historyPageable)
    public pageable!: Observable<Pageable>;

    constructor(private store: Store) {}

    ngOnInit(): void {
        this.store.dispatch(new LoadHistoryAction()).subscribe();
    }

    public onPageChanged(event: PageEvent) {
        this.store.dispatch(new ChangePageAction(event.pageIndex, event.pageSize));
    }
}
