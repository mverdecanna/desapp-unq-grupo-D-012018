import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';


import { AppComponent } from './app.component';
import { UsersComponent } from './users/users.component';
import { VehiclesComponent } from './vehicles/vehicles.component';
import { TransactionsComponent } from './transactions/transactions.component';
import { OrchestratorComponent } from './orchestrator/orchestrator.component';
import { RentalsComponent } from './rentals/rentals.component';
import { ScoreComponent } from './score/score.component';

import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [
    AppComponent,
    UsersComponent,
    VehiclesComponent,
    TransactionsComponent,
    OrchestratorComponent,
    RentalsComponent,
    ScoreComponent
  ],
  imports: [
    BrowserModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
