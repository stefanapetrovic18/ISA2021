import {Component, Inject, Input, OnInit, ViewChild} from '@angular/core';
import {PharmacyService} from '../../../../service/business/pharmacy.service';
import {PharmacistService} from '../../../../service/user/pharmacist.service';
import {DermatologistService} from '../../../../service/user/dermatologist.service';
import {MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import {Router} from '@angular/router';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {MatTableDataSource} from '@angular/material/table';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {Pharmacy} from '../../../../model/business/pharmacy';
import {Pharmacist} from '../../../../model/user/pharmacist';
import {Consultation} from '../../../../model/business/consultation';
import {ConsultationService} from '../../../../service/business/consultation.service';

@Component({
  selector: 'app-consultation-add',
  templateUrl: './consultation-add.component.html',
  styleUrls: ['./consultation-add.component.css']
})
export class ConsultationAddComponent implements OnInit {
  date = new Date();
  duration = 0;
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  thirdFormGroup: FormGroup;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  pharmacistDataSource: MatTableDataSource<any>;
  pharmacyDataSource: MatTableDataSource<any>;
  pharmacies: Pharmacy[];
  pharmacists: Pharmacist[];
  pharmacy: Pharmacy;
  pharmacist: Pharmacist;
  @Input() pharmacyID: number;
  pharmacyColumns = ['name', 'address', 'rating', 'pricelist.consultationPrice'];
  pharmacyActions = ['odaberi'];
  pharmacyDisplayedColumns = [...this.pharmacyColumns, ...this.pharmacyActions];
  pharmacistColumns = ['forename', 'surname', 'rating'];
  pharmacistActions = ['odaberi'];
  pharmacistDisplayedColumns = [...this.pharmacistColumns, ...this.pharmacistActions];

  constructor(private router: Router, /*private dialogRef: MatDialogRef<ConsultationAddComponent>,*/
              private formBuilder: FormBuilder, private pharmacyService: PharmacyService,
              private pharmacistService: PharmacistService, private consultationService: ConsultationService,
              @Inject(MAT_DIALOG_DATA) public data: {pharmacyID: number}) { }

  ngOnInit() {
    this.firstFormGroup = this.formBuilder.group({
      firstCtrl: ['', Validators.required],
      fourthCtrl: ['', Validators.required]
    });
    this.secondFormGroup = this.formBuilder.group({
      secondCtrl: ''
    });
    this.thirdFormGroup = this.formBuilder.group({
      thirdCtrl: ''
    });
    if (this.data !== undefined && this.data.pharmacyID !== undefined) {
      this.pharmacyID = this.data.pharmacyID;
    }
  }

  search() {
    this.router.navigateByUrl('apoteka?localDateTime=' + this.date.toString());
  }

  close() {
    // this.dialogRef.close();
  }
  getPharmacies() {
    this.pharmacyService.findAllByPharmacistFreeTerm(this.date, this.duration).subscribe(
    // this.pharmacyService.findAll().subscribe(
      data => {
        this.pharmacies = data;
        if (this.pharmacies !== undefined && this.pharmacies.length > 0) {
          this.pharmacyDataSource = new MatTableDataSource(this.pharmacies);
          this.pharmacyDataSource.paginator = this.paginator;
          this.pharmacyDataSource.sort = this.sort;
          console.log('ID: ' + this.pharmacyID);
          if (this.pharmacyID !== undefined) {
            this.pharmacies.forEach(p => {
              if (p.id === this.pharmacyID) {
                this.setPharmacy(p);
              }
            });
          }
        } else {
          window.alert('Podaci ne postoje!');
        }
      }, error => {
        window.alert('Podaci ne postoje!');
      }
    );
  }
  getPharmacists() {
    this.pharmacistService.findAllByPharmacistFreeAt(this.pharmacyID, this.date).subscribe(
    data => {
      this.pharmacists = data;
      console.log(data);
      if (this.pharmacists !== undefined && this.pharmacists.length > 0) {
        this.pharmacistDataSource = new MatTableDataSource(this.pharmacists);
        console.log(this.pharmacistDataSource);
        this.pharmacistDataSource.paginator = this.paginator;
        this.pharmacistDataSource.sort = this.sort;
      }
      }, error => {
        window.alert('Podaci ne postoje!');
      }
    );
  }
  setPharmacy(input: Pharmacy) {
    console.log(input);
    this.pharmacyID = input.id;
    this.pharmacy = input;
  }
  unsetPharmacy() {
    this.pharmacyID = undefined;
    this.pharmacy = undefined;
  }
  setPharmacist(input: Pharmacist) {
    this.pharmacist = input;
  }
  unsetPharmacist() {
    this.pharmacist = undefined;
  }
  reserve() {
    const c = new Consultation();
    c.pharmacist = this.pharmacist;
    c.pharmacy = this.pharmacy;
    c.consultationDate = this.date;
    c.duration = this.duration;
    this.consultationService.reserve(c).subscribe(
      data => {
        window.alert('Dodavanje je uspelo!');
      }, error => {
        window.alert('Dodavanje nije uspelo!');
      }
    );
  }

}
