import { AfterViewInit, Component, Inject, OnInit } from '@angular/core';
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
import { ActivatedRoute } from '@angular/router';
import { PharmacyService } from 'src/app/service/business/pharmacy.service';

import * as L from 'leaflet';

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
  selector: 'app-pharmacy-view',
  templateUrl: './pharmacy-view.component.html',
  styleUrls: ['./pharmacy-view.component.css'],
})
export class PharmacyViewComponent implements OnInit, AfterViewInit {
  pharmacy: Pharmacy;
  pharmacists: Pharmacist[];
  dermatologists: Dermatologist[];
  items: Item[];
  patients: Patient[];
  patient: Patient;
  reservation: Reservation = new Reservation();
  private map;
  lat: number;
  lon: number;
  patLat: number;
  patLon: number;

  constructor(
    @Inject(MAT_DIALOG_DATA) public data: any,
    public dialogRef: MatDialogRef<any>,
    private pharmacistService: PharmacistService,
    private dermatologistService: DermatologistService,
    private pharmacyService: PharmacyService,
    private tokenStorageService: TokenStorageService,
    private patientService: PatientService,
    private reservationService: ReservationService,
    private route: ActivatedRoute
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
    this.dialogRef.updateSize('90%', '90%');
    this.pharmacy = this.data;
    console.log('Pharmacy: ' + this.pharmacy);
    if (this.pharmacy === undefined || this.pharmacy === null) {
      let id;
      this.route.queryParams.subscribe(params => {
        id = params['id'];
      });
      if (id !== undefined) {
        this.pharmacyService.getOne(id).subscribe(
          data => {
            this.pharmacy = this.data;
            this.items = this.pharmacy.pricelist.items;
            this.getDermatologists();
            this.getAllPharmacists();
            this.findAllPatients();
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
    } else {
      this.items = this.pharmacy.pricelist.items;
      this.getDermatologists();
      this.getAllPharmacists();
      this.findAllPatients();
      console.log(this.pharmacy);
      this.getCoords();
      // this.pharmacyService.getCoordinatesFromAddress(this.pharmacy.address).subscribe(
      //   data => {
      //     this.lat = data.lat;
      //     this.lon = data.lon;
      //     const marker = L.marker([this.lat, this.lon]);
      //     marker.addTo(this.map);
      //   }
      // );
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
        const marker = L.marker([this.lat, this.lon], {icon: greenIcon});
        marker.bindPopup(this.makePharmacyPopup(this.pharmacy));
        marker.addTo(this.map);
      }
    );
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
      this.patients.forEach((p) => {
        if (p.username === this.tokenStorageService.getUsername()) {
          this.patient = p;
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
