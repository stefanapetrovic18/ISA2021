import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Complaint } from 'src/app/model/business/complaint';
import { Pharmacy } from 'src/app/model/business/pharmacy';
import { Dermatologist } from 'src/app/model/user/dermatologist';
import { Pharmacist } from 'src/app/model/user/pharmacist';
import { ComplaintService } from 'src/app/service/business/complaint.service';
import { PharmacyService } from 'src/app/service/business/pharmacy.service';
import { DermatologistService } from 'src/app/service/user/dermatologist.service';
import { PharmacistService } from 'src/app/service/user/pharmacist.service';

@Component({
  selector: 'app-complaint-add',
  templateUrl: './complaint-add.component.html',
  styleUrls: ['./complaint-add.component.scss']
})
export class ComplaintAddComponent implements OnInit {
  data = new Complaint();
  dermatologists: Dermatologist[];
  pharmacists: Pharmacist[];
  pharmacies: Pharmacy[];
  type: string;
  id: number;
  constructor(private complaintService: ComplaintService, private dialogRef: MatDialogRef<ComplaintAddComponent>,
    private pharmacyService: PharmacyService, private dermatologistService: DermatologistService,
    private pharmacistService: PharmacistService) { }

  ngOnInit() {
    this.pharmacyService.findAll().subscribe(
      data => {
        this.pharmacies = data;
      }, error => {
        window.alert('Nisu dobavljene apoteke!');
      }
    );
    this.pharmacistService.findAll().subscribe(
      data => {
        this.pharmacists = data;
      }, error => {
        window.alert('Nisu dobavljeni farmaceuti!');
      }
    );
    this.dermatologistService.findAll().subscribe(
      data => {
        this.dermatologists = data;
      }, error => {
        window.alert('Nisu dobavljeni dermatolozi!');
      }
    );
  }
  add() {
    if (this.type === 'PHARMACY') {
      this.complaintService.submitPharmacy(this.data, this.id).subscribe(
        data => {
          window.alert('Žalba je kreirana!');
        }, error => {
          window.alert('Žalba nije kreirana!');
        }
      );
    } else if (this.type === 'PHARMACIST') {
      this.complaintService.submitPharmacist(this.data, this.id).subscribe(
        data => {
          window.alert('Žalba je kreirana!');
        }, error => {
          window.alert('Žalba nije kreirana!');
        }
      );
    } else if (this.type === 'DERMATOLOGIST') {
      this.complaintService.submitDermatologist(this.data, this.id).subscribe(
        data => {
          window.alert('Žalba je kreirana!');
        }, error => {
          window.alert('Žalba nije kreirana!');
        }
      );
    } else {
      window.alert('Izabrana opcija nije validna!');
    }
  }
  close() {
    this.dialogRef.close();
  }

}
