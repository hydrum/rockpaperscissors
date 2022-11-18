import { Component, Input, OnInit } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { GameOutcome, GameResultDto } from 'src/app/generated/backend';

@Component({
    selector: 'app-history-table',
    templateUrl: './history-table.component.html',
    styleUrls: ['./history-table.component.scss'],
})
export class HistoryTableComponent {
    dataSource: MatTableDataSource<GameResultDto> = new MatTableDataSource<GameResultDto>([]);
    displayedColumns: string[] = ['name', 'playerSelection', 'cpuSelection', 'outcome'];

    @Input()
    public set history(value: GameResultDto[] | undefined) {
        if (value) {
            this.dataSource.data = value;
        }
    }

    public translateOutcome(outcome: GameOutcome): string {
        switch (outcome) {
            case GameOutcome.PlayerWin:
                return 'WIN';
            case GameOutcome.CpuWin:
                return 'LOSS';
            case GameOutcome.Draw:
                return 'DRAW';
            default:
                return 'UNKNOWN';
        }
    }
}
