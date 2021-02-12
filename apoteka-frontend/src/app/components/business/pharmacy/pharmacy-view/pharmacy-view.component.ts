import { Component, Inject, OnInit } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { TokenStorageService } from 'src/app/auth/token-storage.service';
import { Item } from 'src/app/model/business/item';
import { Medicine } from 'src/app/model/business/medicine';
import { Pharmacy } from 'src/app/model/business/pharmacy';
import { Reservation } from 'src/app/model/business/reservation';
import { Dermatologist } from 'src/app/model/user/dermatologist';
import { Patient } from 'src/app/model/user/patient';
import { Pharmacist } from 'src/app/model/user/pharmacist';
import { ReservationService } from 'src/app/service/business/reservation.service';
import { DermatologistService } from 'src/app/service/user/dermatologist.service';
import { PatientService } from 'src/app/service/user/patient.service';
import { PharmacistService } from 'src/app/service/user/pharmacist.service';

@Component({
  selector: 'app-pharmacy-view',
  templateUrl: './pharmacy-view.component.html',
  styleUrls: ['./pharmacy-view.component.css'],
})
export class PharmacyViewComponent implements OnInit {
  pharmacy: Pharmacy;
  pharmacists: Pharmacist[];
  dermatologists: Dermatologist[];
  items: Item[];
  patients: Patient[];
  patient: Patient;
  reservation: Reservation = new Reservation();

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<any>,
    private pharmacistService: PharmacistService,
    private dermatologistService: DermatologistService,
    private tokenStorageService: TokenStorageService,
    private patientService: PatientService,
    private reservationService: ReservationService
  ) {}

  ngOnInit() {
    this.dialogRef.updateSize('90%', '90%');
    this.pharmacy = this.data;
    this.items = this.pharmacy.pricelist.items;
    this.getDermatologists();
    this.getAllPharmacists();
    this.findAllPatients();
    console.log(this.pharmacy);
  }

  getAllPharmacists() {
    this.pharmacistService.findAll().subscribe((data) => {
      this.pharmacists = data.filter((p) => p.pharmacy.id === this.pharmacy.id);
    });
  }

  getDermatologists() {
    this.dermatologistService
      .findAllByPharmaciesContaining(this.pharmacy.id)
      .subscribe((data) => {
        this.dermatologists = data;
      });
  }

  findAllPatients() {
    this.patientService.findAll().subscribe((data) => {
      this.patients = data;
      this.getPatient();
    });
  }

  getPatient() {
    this.patients.forEach((p) => {
      if (p.username === this.tokenStorageService.getUsername()) {
        this.patient = p;
      }
    });
  }

  reserveMedicine(medicine: Medicine) {
    this.reservation.reservationNumber = Math.random().toString();
    this.reservation.medicine = medicine;
    this.reservation.patient = this.patient;
    this.reservation.pharmacy = this.pharmacy;
    this.reservation.reservationDate = new Date();
    this.reservationService.create(this.reservation).subscribe((data) => {
      alert('Uspesno ste rezervisali lek');
    });
  }
}
