import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { MatPaginatorModule } from '@angular/material/paginator';
import { NgxsModule } from '@ngxs/store';
import { AppModule } from 'src/app/app.module';
import { HistoryTableComponent } from '../history-table/history-table.component';
import { GameState } from '../states/game.state';

import { HistoryComponent } from './history.component';

describe('HistoryComponent', () => {
    let component: HistoryComponent;
    let fixture: ComponentFixture<HistoryComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [HistoryComponent, HistoryTableComponent],
            imports: [
                NgxsModule.forRoot([GameState]), //
                HttpClientTestingModule,
                MatPaginatorModule,
                AppModule,
            ],
        }).compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(HistoryComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
