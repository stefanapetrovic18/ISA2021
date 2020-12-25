import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ConfirmComponent } from './components/confirm/confirm.component';
import { RequestTokenComponent } from './components/request-token/request-token.component';


const routes: Routes = [
  {
    path: 'request-token',
    component: RequestTokenComponent
  },
  {
    path: 'confirm',
    component: ConfirmComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
