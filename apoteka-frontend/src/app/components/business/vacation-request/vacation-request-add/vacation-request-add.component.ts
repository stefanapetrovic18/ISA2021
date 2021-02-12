import { Component, OnInit } from '@angular/core';
import {MatDialogRef} from '@angular/material/dialog';
import {MedicineService} from '../../../../service/business/medicine.service';
import {Medicine} from '../../../../model/business/medicine';
import {VacationRequestService} from '../../../../service/business/vacation-request.service';
import {VacationRequest} from '../../../../model/business/vacation-request';
import {PharmacyService} from '../../../../service/business/pharmacy.service';
import {Pharmacy} from '../../../../model/business/pharmacy';
import {TokenStorageService} from '../../../../auth/token-storage.service';

@Component({
  selector: 'app-vacation-request-add',
  templateUrl: './vacation-request-add.component.html',
  styleUrls: ['./vacation-request-add.component.css']
})
export class VacationRequestAddComponent implements OnInit {
  data = new VacationRequest();
  pharmacies: Pharmacy[];
  dermatologist = false;

  constructor(private dialogRef: MatDialogRef<VacationRequestAddComponent>, private pharmacyService: PharmacyService,
              private vacationRequestService: VacationRequestService, private token: TokenStorageService) { }

  ngOnInit() {
    if (this.token.getAuthorities().includes('ROLE_DERMATOLOGIST')) {
      this.dermatologist = true;
    }
    this.pharmacyService.findAll().subscribe(
      data => {
        this.pharmacies = data;
      }
    );
  }
  add() {
    this.vacationRequestService.create(this.data).subscribe(
      data => {
        window.alert('Zahtev podnet!');
      }, error => {
        window.alert('Zahtev nije podnet!');
      }
    );
  }
  close() {
    this.dialogRef.close();
  }

}
