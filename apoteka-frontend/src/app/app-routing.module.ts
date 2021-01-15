import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ChangePasswordComponent } from './components/change-password/change-password.component';
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
  },
  {
    path: 'change-password',
    component: ChangePasswordComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
