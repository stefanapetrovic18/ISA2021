import { AfterViewInit, Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
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
import { ActivatedRoute, Router } from '@angular/router';
import { PharmacyService } from 'src/app/service/business/pharmacy.service';

import * as L from 'leaflet';
import { MedicineService } from 'src/app/service/business/medicine.service';
import { ExaminationService } from 'src/app/service/business/examination.service';
import { Examination } from 'src/app/model/business/examination';
import { APP_BASE_HREF } from '@angular/common';
import { ConsultationAddComponent } from '../../consultation/consultation-add/consultation-add.component';
import { ReservationAddComponent } from '../../reservation/reservation-add/reservation-add.component';
import { RatingService } from 'src/app/service/business/rating.service';
import { Rating } from 'src/app/model/business/rating';

const iconRetinaUrl = 'assets/marker-icon-2x.png';
const iconUrl = 'assets/marker-icon.png';
const shadowUrl = 'assets/marker-shadow.png';
const iconDefault = L.icon({
  iconRetinaUrl,
  iconUrl,
  shadowUrl,
  iconSize: [25, 41],
  iconAnchor: [12, 41],
  popupAnchor: [1, -34],
  tooltipAnchor: [16, -28],
  shadowSize: [41, 41]
});
L.Marker.prototype.options.icon = iconDefault;

@Component({
  selector: 'app-pharmacy-page',
  templateUrl: './pharmacy-page.component.html',
  styleUrls: ['./pharmacy-page.component.scss'],
})
export class PharmacyPageComponent implements OnInit, AfterViewInit {
  public data: Pharmacy;
  subscribed = false;
  pharmacy: Pharmacy;
  pharmacists: Pharmacist[];
  dermatologists: Dermatologist[];
  examinations: Examination[];
  medicine: Medicine[];
  items: Item[];
  patients: Patient[];
  patient: Patient;
  reservation: Reservation = new Reservation();
  private map;
  lat: number;
  lon: number;
  patLat: number;
  patLon: number;
  rating = 0;
  yourRating: Rating;
  editRating = false;
  dermatologistDisplayedColumns: string[] = ['dermatologistName', 'dermatologistRating'];
  pharmacistDisplayedColumns: string[] = ['pharmacistName', 'pharmacistRating'];
  medicineDisplayedColumns: string[] = ['medicineName', 'medicineRating', 'medicineReserve'];
  examinationDisplayedColumns: string[] = ['examinationDate', 'examinationDermatologist', 'examinationDuration', 'examinationPrice', 'examinationReserve'];

  constructor(
    private dialog: MatDialog,
    private pharmacistService: PharmacistService,
    private dermatologistService: DermatologistService,
    private pharmacyService: PharmacyService,
    private tokenStorageService: TokenStorageService,
    private patientService: PatientService,
    private reservationService: ReservationService,
    private medicineService: MedicineService,
    private examinationService: ExaminationService,
    private ratingService: RatingService,
    private route: ActivatedRoute,
    private router: Router
  ) {}
  ngAfterViewInit(): void {
    this.initMap();
  }

  initMap() {
    this.map = L.map('map', {
      center: [ 44.0165, 21.0059 ],
      zoom: 8
    });
    const tiles = L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 18,
      minZoom: 3,
      attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    });
    tiles.addTo(this.map);
    // console.log('Lat: ' + this.lat);
    // const marker = L.marker([this.lat, this.lon]);
    // marker.bindPopup(this.makePharmacyPopup(this.pharmacy));
    // marker.addTo(this.map);
    // const patMarker = L.marker([this.patLat, this.patLon]);
    // patMarker.bindPopup(this.makePatientPopup(this.patient));
    // patMarker.addTo(this.map);
  }

  makePharmacyPopup(pharmacy: Pharmacy): string {
    return `` +
      `<div>Naziv: ${ pharmacy.name }</div>` +
      `<div>Adresa: ${ pharmacy.address }</div>` +
      `<div>Ocena: ${ pharmacy.rating }</div>`
  }

  makePatientPopup(patient: Patient): string {
    return `` +
      `<div>Naziv: ${ patient.forename + ' ' + patient.surname }</div>` +
      `<div>Adresa: ${ patient.address }</div>` +
      `<div>Grad: ${ patient.city }</div>`
  }

  ngOnInit() {
    let id;
    this.route.queryParams.subscribe(params => {
      id = params['id'];
    });
    console.log(id);
    if (id !== undefined) {
      this.pharmacyService.getOne(id).subscribe(
        data => {
          this.pharmacy = data;
          this.items = this.pharmacy.pricelist.items;
          this.getDermatologists();
          this.getAllPharmacists();
          this.findAllPatients();
          this.getMedicine();
          this.getExaminations();
          console.log(this.pharmacy);
          this.getCoords();
          // this.pharmacyService.getCoordinatesFromAddress(this.pharmacy.address).subscribe(
          //   data => {
          //     this.lat = data.lat;
          //     this.lon = data.lon;
          //     // const marker = L.marker([this.lat, this.lon]);
          //     // marker.addTo(this.map);
          //   }
          // );
        }
      );
    }

  }

  getCoords() {
    this.pharmacyService.getCoordinatesFromAddress(this.pharmacy.address).subscribe(
      data => {
        console.log(data);
        this.lat = data[0].lat;
        this.lon = data[0].lon;
        console.log('Lat: ' + this.lat);
        var greenIcon = new L.Icon({
          iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-2x-green.png',
          shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
          iconSize: [25, 41],
          iconAnchor: [12, 41],
          popupAnchor: [1, -34],
          shadowSize: [41, 41]
        });
        this.map.flyTo([this.lat, this.lon], 12);
        const marker = L.marker([this.lat, this.lon], {icon: greenIcon});
        marker.bindPopup(this.makePharmacyPopup(this.pharmacy));
        marker.addTo(this.map);
      }
    );
  }

  getAllPharmacists() {
    this.pharmacistService.findAll().subscribe((data) => {

      console.log(data);
      this.pharmacists = data.filter((p) => p.pharmacy !== null && p.pharmacy.id === this.pharmacy.id);
    });
  }

  getDermatologists() {
    this.dermatologistService
      .findAllByPharmaciesContaining(this.pharmacy.id)
      .subscribe((data) => {
        this.dermatologists = data;
        console.log(data);
      });
  }

  getMedicine() {
    this.medicineService.findAllByPharmacyID(this.pharmacy.id).subscribe(
      data => {
        this.medicine = data;
        console.log(data);
      }, error => {
        window.alert('Greška u dobavljanju podataka o lekovima.');
      }
    )
  }

  getExaminations() {
    this.examinationService.findAllByPharmacy(this.pharmacy.id).subscribe(
      data => {
        this.examinations = data.filter(e => e.patient === null);
        console.log(data);
      }, error => {
        window.alert('Greška u dobavljanju podataka o slobodnim pregledima.');
      }
    );
  }

  findAllPatients() {
    this.patientService.findAll().subscribe((data) => {
      this.patients = data;
      this.patients.forEach((p) => {
        if (p.username === this.tokenStorageService.getUsername()) {
          this.patient = p;
          this.checkSubscriptionStatus();
          this.getRating();
          console.log(this.patient);
            this.pharmacyService.getCoordinatesFromAddress(this.patient.address + ', ' + this.patient.city).subscribe(
              data => {
                this.patLat = data[0].lat;
                this.patLon = data[0].lon;
                var redIcon = new L.Icon({
                  iconUrl: 'https://raw.githubusercontent.com/pointhi/leaflet-color-markers/master/img/marker-icon-red.png',
                  shadowUrl: 'https://cdnjs.cloudflare.com/ajax/libs/leaflet/0.7.7/images/marker-shadow.png',
                  iconSize: [12, 20],
                  iconAnchor: [12, 41],
                  popupAnchor: [1, -34],
                  shadowSize: [20, 20]
                });
                const patMarker = L.marker([this.patLat, this.patLon], {icon: redIcon});
                patMarker.bindPopup(this.makePatientPopup(this.patient));
                patMarker.addTo(this.map);
              }
            );
        }
      });
      // this.getPatient();
    });
  }

  getRating() {
    this.pharmacyService.getRating(this.pharmacy.id).subscribe(
      data => {
        this.yourRating = data;
        this.rating = data.rating;
      }, error => {
        console.log(error.message);
      }
    );
  }

  getAveragePharmacistRating() {
    let sum = 0;
    this.pharmacists.forEach(r => {
      sum += r.rating;
    });
    return sum / this.pharmacists.length;
  }

  getAverageDermatologistRating() {
    let sum = 0;
    this.dermatologists.forEach(r => {
      sum += r.rating;
    });
    return sum / this.dermatologists.length;
  }

  getAverageMedicineRating() {
    let sum = 0;
    this.medicine.forEach(r => {
      sum += r.rating;
    });
    return sum / this.medicine.length;
  }

  getPatient() {
    this.patients.forEach((p) => {
      if (p.username === this.tokenStorageService.getUsername()) {
        this.patient = p;
      }
    });
  }

  checkSubscriptionStatus() {
    this.pharmacyService.checkSubStatus(this.pharmacy.id).subscribe(
      data => {
        console.log(data);
        this.subscribed = data;
      }, error => {
        console.log(error.message);
      }
    )
  }

  subscribe() {
    this.pharmacyService.subscribe(this.pharmacy.id).subscribe(
      data => {
        window.alert("Pretplata uspešna!");
        this.subscribed = true;
      }, error => {
        window.alert("Pretplata nije uspešna!");
      }
    );
  }

  unsubscribe() {
    this.pharmacyService.unsubscribe(this.pharmacy.id).subscribe(
      data => {
        window.alert("Otkazivanje pretplate uspešno!");
        this.subscribed = false;
      }, error => {
        window.alert("Otkazivanje pretplate nije uspešno!");
      }
    );
  }

  reserveExamination(input: Examination) {
    this.examinationService.quickReserve(input).subscribe(
      data => {
        window.alert('Uspešna rezervacija! Molimo vas da proverite vaš email za potvrdu.');
        location.reload();
      }, error => {
        window.alert('Neuspešna rezervacija!');
      }
    );
  }

  cancelExamination(input: Examination) {
    this.examinationService.cancel(input).subscribe(
      data => {
        window.alert('Uspešno otkazivanje!');
        location.reload();
      }, error => {
        window.alert('Neuspešno otkazivanje!');
      }
    );
  }

  reserveConsultation() {
    this.dialog.open(ConsultationAddComponent, {data: {pharmacyID: this.pharmacy.id}});
  }

  rate() {

  }

  ratePharmacy(rating: number) {
    let r = new Rating();
    r.rating = rating;
    r.patient = this.patient;
    this.ratingService.ratePharmacy(r, this.pharmacy.id).subscribe(
      data => {
        window.alert('Uspešno ocenjivanje!');
        location.reload();
      }, error => {
        window.alert('Neuspešno ocenjivanje!');
      }
    )
  }

  reserveMedicine(medicine: Medicine) {
    this.reservation.reservationNumber = Math.random().toString();
    this.reservation.medicine = medicine;
    this.reservation.patient = this.patient;
    this.reservation.pharmacy = this.pharmacy;
    this.reservation.reservationDate = new Date();
    const dialogRef = this.dialog.open(ReservationAddComponent, {data: {pharmacy: this.pharmacy}});
    dialogRef.afterClosed().subscribe(
      data => {
        this.reservation.collectionDate = data.date;
        this.reservationService.reserve(this.reservation).subscribe((data) => {
          alert('Uspešno ste rezervisali lek.');
          location.reload();
        });
      }
    );
  }

  step = 0;

  setStep(index: number) {
    this.step = index;
  }

  nextStep() {
    this.step++;
  }

  prevStep() {
    this.step--;
  }
}
