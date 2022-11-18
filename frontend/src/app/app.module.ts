import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgxsModule } from '@ngxs/store';
import { NgxsFormPluginModule } from '@ngxs/form-plugin';
import { ApiModule } from './generated/backend';

@NgModule({
    declarations: [AppComponent],
    imports: [
        BrowserModule, //
        AppRoutingModule,
        BrowserAnimationsModule,
        HttpClientModule,
        NgxsModule.forRoot([]),
        NgxsFormPluginModule.forRoot(),
        ApiModule,
    ],
    providers: [],
    bootstrap: [AppComponent],
})
export class AppModule {}
