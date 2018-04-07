import { Component, OnInit } from '@angular/core';
import { Vehicle } from '../vehicle';

import { VEHICLES } from '../mock-vehicles';

@Component({
  selector: 'app-vehicles',
  templateUrl: './vehicles.component.html',
  styleUrls: ['./vehicles.component.css']
})
export class VehiclesComponent implements OnInit {
  vehicles = VEHICLES;

  vehicle: Vehicle = {
    id: 1,
    type:'moto',
    brand:'kawa'
  };
  constructor() { }

  ngOnInit() {
  }

}
