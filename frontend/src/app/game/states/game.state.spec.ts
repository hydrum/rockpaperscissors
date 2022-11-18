import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { NgxsModule } from '@ngxs/store';

import { GameState } from './game.state';

describe('GameState', () => {
    let service: GameState;

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [
                NgxsModule.forRoot([GameState]), //
                HttpClientTestingModule,
            ],
        });
        service = TestBed.inject(GameState);
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });
});
