import { Component, OnInit } from '@angular/core';
import {COMMA, ENTER} from '@angular/cdk/keycodes';
import {MatChipInputEvent} from '@angular/material/chips';
import {Medicine} from '../../../../model/business/medicine';
import {MatDialogRef} from '@angular/material/dialog';
import {MedicineService} from '../../../../service/business/medicine.service';

@Component({
  selector: 'app-medicine-add',
  templateUrl: './medicine-add.component.html',
  styleUrls: ['./medicine-add.component.css']
})
export class MedicineAddComponent implements OnInit {
  data = new Medicine();
  visible = true;
  selectable = true;
  removable = true;
  addOnBlur = true;
  medicine: Medicine[];
  readonly separatorKeysCodes: number[] = [ENTER, COMMA];
  constructor(private dialogRef: MatDialogRef<MedicineAddComponent>, private medicineService: MedicineService) { }

  ngOnInit() {
    this.medicineService.findAll().subscribe(
      data => {
        this.medicine = data;
      }
    );
    this.data.ingredients = [];
    this.data.prescriptionNecessary = false;
  }
  add() {
    this.medicineService.create(this.data).subscribe(
      data => {
        window.alert('Lek dodat!');
      }, error => {
        window.alert('Lek nije dodat!');
      }
    );
  }
  push(event: MatChipInputEvent): void {
    const input = event.input;
    const value = event.value;
    console.log(event);
    console.log(this.data.ingredients);

    // Add our fruit
    if ((value || '').trim()) {
      this.data.ingredients.push(value.trim());
    }

    // Reset the input value
    if (input) {
      input.value = '';
    }
  }

  remove(ingredient: string): void {
    const index = this.data.ingredients.indexOf(ingredient);

    if (index >= 0) {
      this.data.ingredients.splice(index, 1);
    }
  }
  close() {
    this.dialogRef.close();
  }
}
