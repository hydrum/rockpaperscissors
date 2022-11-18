import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Store } from '@ngxs/store';
import { combineLatest, map, Observable } from 'rxjs';
import { SelectNameAction } from '../states/game.actions';
import { GameState } from '../states/game.state';

@Component({
    selector: 'app-player-name-selection',
    templateUrl: './player-name-selection.component.html',
    styleUrls: ['./player-name-selection.component.scss'],
})
export class PlayerNameSelectionComponent implements OnInit {
    public form: FormGroup = new FormGroup({
        name: new FormControl('', {
            validators: [
                Validators.required, //
                Validators.minLength(1),
                Validators.maxLength(50),
            ],
        }),
    });

    public canSubmit$!: Observable<boolean>;

    constructor(private store: Store) {}

    ngOnInit(): void {
        this.canSubmit$ = combineLatest([
            this.store.select(GameState.playerNameFormDirty), //
            this.store.select(GameState.playerNameFormInvalid),
        ]).pipe(
            map((state) => !state[0] || state[1]) //
        );
    }

    setPlayername() {
        this.store.dispatch(new SelectNameAction());
    }
}
