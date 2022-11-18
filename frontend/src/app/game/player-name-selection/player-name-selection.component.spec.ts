import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { NgxsFormPluginModule } from '@ngxs/form-plugin';
import { NgxsModule } from '@ngxs/store';
import { AppModule } from 'src/app/app.module';
import { GameState } from '../states/game.state';

import { PlayerNameSelectionComponent } from './player-name-selection.component';

describe('PlayerNameSelectionComponent', () => {
    let component: PlayerNameSelectionComponent;
    let fixture: ComponentFixture<PlayerNameSelectionComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [PlayerNameSelectionComponent],
            imports: [
                NgxsModule.forRoot([GameState]), //
                HttpClientTestingModule,
                MatInputModule,
                MatButtonModule,
                MatCardModule,
                NgxsFormPluginModule,
                ReactiveFormsModule,
                FormsModule,
                AppModule,
                MatFormFieldModule,
            ],
        }).compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(PlayerNameSelectionComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
